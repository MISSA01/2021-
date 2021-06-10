package pers.may.assist.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pers.may.assist.pojo.Chat;
import pers.may.assist.service.ChatService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * websocket连接接口
 * @description websocket连接接口
 * @author May
 */
@Controller
@ServerEndpoint(value = "/websocket/{phoneNum}")
public class ChatSocketController {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, ChatSocketController> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收phoneNum*/
    private String phoneNum="";

    private static ChatService chatService;

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    //静态注入ChatService
    @Autowired
    public void setChatService(ChatService chatService){
        ChatSocketController.chatService = chatService;
    }


    /**
     *
     * 建立连接时回调的方法，无论用户是否注册，是否在服务器上都可以连接
     *
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("phoneNum") String phoneNum) {
        this.session = session;
        this.phoneNum=phoneNum;
        if(webSocketMap.containsKey(phoneNum)){
            //如果在服务器里就先去除
            webSocketMap.remove(phoneNum);
            //加入set中
            webSocketMap.put(phoneNum,this);
        }else{
            //加入set中
            webSocketMap.put(phoneNum,this);
            //在线数加1
            addOnlineCount();
        }

        System.out.println("用户连接:"+phoneNum+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("{\"msg\":\"连接成功\"}");
        } catch (IOException e) {
            System.out.println("用户:"+phoneNum+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(phoneNum)){
            webSocketMap.remove(phoneNum);
            //从set中删除
            subOnlineCount();
        }
        System.out.println("用户退出:"+phoneNum+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        Runnable t = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //可以群发消息
                //消息保存到数据库权重2
                if(StringUtils.isNotBlank(message)){
                    try {
                        //解析发送的报文
                        JSONObject jsonObject = JSON.parseObject(message);
                        //追加发送人(防止串改)
                        jsonObject.put("fromPhoneNum",phoneNum);
                        String toPhoneNum=jsonObject.getString("toPhoneNum");

                        //判断该订单是否还存在
                        if (!chatService.ifExistOrderByOderId(Integer.parseInt(jsonObject.getString("orderId")))){
                            //JSON类型设为0，意味着非正常发送消息
                            jsonObject.put("resultType",0);
                            jsonObject.put("error","该订单已被取消！");
                            System.out.println(jsonObject.getString("orderId")+"===该订单已被取消");
                            webSocketMap.get(jsonObject.getString("fromPhoneNum")).sendMessage(jsonObject.toJSONString());
                            return;
                        }


                        //该用户存在
                        if (StringUtils.isNotBlank(toPhoneNum)&&ChatSocketController.chatService.ifExistUser(toPhoneNum)){

                            //无论该用户是否存在服务器上，都向数据库插入一条未读消息
                            Chat chat = new Chat();
                            chat.setSentTime(new Date());
                            chat.setFromPhoneNum(jsonObject.getString("fromPhoneNum"));
                            chat.setToPhoneNum(jsonObject.getString("toPhoneNum"));
                            chat.setChatContent(jsonObject.getString("chatContent"));
                            chat.setChatType(jsonObject.getInteger("chatType"));
                            chat.setOrderId(jsonObject.getInteger("orderId"));
                            String newContent = ChatSocketController.chatService.addSingleUnreadChat(chat);
                            jsonObject.put("chatContent",newContent);

                            System.out.println("用户消息:"+phoneNum+",报文:"+jsonObject.getString("chatContent"));
                            //该用户存在并且在服务器上
                            if (webSocketMap.containsKey(toPhoneNum)){
                                //JSON类型设为1，意味着正常发送消息
                                jsonObject.put("resultType",1);
                                webSocketMap.get(toPhoneNum).sendMessage(jsonObject.toJSONString());

                            }else {//该用户存在但不在服务器上
                                System.out.println("请求的phoneNum:"+toPhoneNum+"不在该服务器上");
                            }
                        }else{
                            System.out.println("请求的phoneNum:"+toPhoneNum+"未注册");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };

        //cachedThreadPool.submit(t);
        fixedThreadPool.submit(t);

    }

    /**
     *
     * 出错回调方法
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("用户错误:"+this.phoneNum+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        synchronized (session) {
            this.session.getBasicRemote().sendText(message);
        }

    }


//    /**
//     * 发送自定义消息
//     * */
//    public static void sendInfo(String message,@PathParam("phoneNum") String phoneNum) throws IOException {
//        //System.out.println("发送消息到:"+phoneNum+"，报文:"+message);
//        if(StringUtils.isNotBlank(phoneNum)&&webSocketMap.containsKey(phoneNum)){
//            webSocketMap.get(phoneNum).sendMessage(message);
//        }else{
//            //System.out.println("用户"+phoneNum+",不在线！");
//        }
//    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChatSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChatSocketController.onlineCount--;
    }
}


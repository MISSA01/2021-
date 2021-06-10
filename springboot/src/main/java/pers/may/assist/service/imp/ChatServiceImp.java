package pers.may.assist.service.imp;

import org.springframework.stereotype.Service;
import pers.may.assist.mapper.ChatMapper;
import pers.may.assist.mapper.OrdersDao;
import pers.may.assist.mapper.UserMapper;
import pers.may.assist.pojo.Chat;
import pers.may.assist.pojo.User;
import pers.may.assist.service.ChatService;
import pers.may.assist.utils.Base64toImage;

import javax.annotation.Resource;
import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * 订单服务接口
 * @author May
 *
 */
@Service("OrderService")
public class ChatServiceImp implements ChatService {

    @Resource
    ChatMapper chatMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    OrdersDao ordersDao;

    @Override
    public Integer getMaxChatNum(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId) {
        Integer maxChatNum = chatMapper.selMaxChatNum(fromUserPhoneNum,toUserPhoneNum,orderId);
        return maxChatNum;
    }

    @Override
    public String addSingleUnreadChat(Chat chat) {
        //填入当前时刻
        Date currentDate = new Date();
        chat.setSentTime(currentDate);
        //检查消息内容的类型，如果是文字则直接存储，如果是图片则取出图片单独存储，并填入图片地址。
        System.out.println(chat.getChatType());
        switch (chat.getChatType()){
            case 2:
                //二维码类型直接存储
                chatMapper.insertSingleUnreadChat(chat);
                return chat.getChatContent();
            case 1:
                //文字类型直接存储
                chatMapper.insertSingleUnreadChat(chat);
                return chat.getChatContent();

            case 0:
                //图片类型
                //取出图片字节码，并进行处理存储
                String originCode = chat.getChatContent();
                //生成唯一的图片名字
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String filename = chat.getFromPhoneNum()+"_"+chat.getToPhoneNum()+"_"+sdf.format(chat.getSentTime())+".jpg";
                //转存图片地址
                chat.setChatContent("/assist/img/chat/"+filename);
                //存入数据库
                chatMapper.insertSingleUnreadChat(chat);

                //保存图片文件
                Base64toImage base64toImage = new Base64toImage();
                File fileDir = base64toImage.getImgDirFile("chat");
                //base64toImage.generateImage(base64toImage.fixBase64Code(originCode), fileDir.getAbsolutePath() + "\\" + filename); //windows
                base64toImage.generateImage(base64toImage.fixBase64Code(originCode), fileDir.getAbsolutePath() + "/" + filename);//linux
                return "/assist/img/chat/"+filename;
            default:
                return null;

        }

    }



    @Override
    public List<Chat> getAllChat(String userPhoneNum1,String userPhoneNum2,Integer orderId) {
        List<Chat> chats1 = chatMapper.selAllChat(userPhoneNum1,userPhoneNum2,orderId);
        List<Chat> chats2 = chatMapper.selAllChat(userPhoneNum2,userPhoneNum1,orderId);
        chats1.addAll(chats2);
        // 按照时间进行升序排序
        Collections.sort(chats1, new Comparator<Chat>() {

            @Override
            public int compare(Chat chat1, Chat chat2) {
                //如果cha1>char2,key>0
                //chat1<char2,key<0
                //chat1==char2,key=0

                int key = chat1.getSentTime().compareTo(chat2.getSentTime());

                if (key>0) {
                    return 1;
                }else if (key==0) {
                    return 0;
                }else{
                    return -1;
                }

            }
        });
        return chats1;
    }

    @Override
    public List<Chat> getAllChatOfPage(String fromUserPhoneNum,String toUserPhoneNum, Integer pageNum, Integer pageSize,Integer orderId) {
        List<Chat> chats = chatMapper.selAllChatOfPage(fromUserPhoneNum,toUserPhoneNum,pageNum,pageSize,orderId);
        return chats;
    }


    @Override
    public List<Chat> getAllIndividualChat(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId) {
        List<Chat> chats = chatMapper.selAllChat(fromUserPhoneNum,toUserPhoneNum,orderId);
        return chats;
    }

    @Override
    public int getNewChatNumOfOne(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId) {
        Integer newNum = chatMapper.selNewChatNumOfOne(fromUserPhoneNum,toUserPhoneNum,orderId);
        return newNum;
    }

    @Override
    public int getAllNewChatNum(String phoneNum) {
        Integer newNum = chatMapper.selAllNewChatNum(phoneNum);
        return newNum;
    }

    @Override
    public void updChatState(String phoneNum,String anotherUserPhoneNum,Integer orderId) {
        chatMapper.updChatState(phoneNum,anotherUserPhoneNum,orderId);
    }

    @Override
    public boolean ifExistUser(String phoneNumber) {
        if (userMapper.selSingleUserByPhoneNum(phoneNumber)==null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean ifExistOrderByOderId(Integer orderId) {
        if (ordersDao.getOrderByOrderId(orderId) != null)
            return true;
        else
            return false;
    }


}

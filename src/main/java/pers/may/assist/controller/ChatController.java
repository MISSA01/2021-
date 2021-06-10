package pers.may.assist.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.may.assist.pojo.Chat;
import pers.may.assist.service.ChatService;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 * 聊天模块
 * @author May
 *
 *
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Resource
    ChatService chatService;


    /**
     * getMaxChatNum
     * @description 查询一方发送给另一方的所有聊天信息的总条数(一个订单中)。
     * @param fromUserPhoneNum 发送人手机号码
     * @param toUserPhoneNum 接收人手机号码
     * @param orderId 订单ID
     * @author May
     * @return
     */
    @ResponseBody
    @PostMapping("/getMaxChatNum")
    public Integer getMaxChatNum(@NotNull String fromUserPhoneNum,@NotNull String toUserPhoneNum,@NotNull Integer orderId){
        return chatService.getMaxChatNum(fromUserPhoneNum,toUserPhoneNum,orderId);
    }

    /**
     * addSingleUnreadChat
     * @description 插入一条文字或图片聊天消息，该消息的状态为未读,聊天图片命名为 "fromPhoneNum_toPhoneNum_时间戳",聊天图片存储路径为/assist/img/chat/。
     * @param chat
     * @author May
     */
    @ResponseBody
    @PostMapping("/addSingleUnreadChat")
    public void addSingleUnreadChat(@NotNull Chat chat){
        chatService.addSingleUnreadChat(chat);
    }


    /**
     * getAllChat
     * @description 查询两人之间的所有聊天信息，时间顺序排序（双向，一个订单中)
     * @param phoneNum1 其中一个人的手机号码
     * @param phoneNum2 另一个人的手机号码
     * @param orderId 订单ID
     * @return
     * @author May
     */
    @ResponseBody
    @PostMapping("/getAllChat")
    public List<Chat> getAllChat(@NotNull String phoneNum1,@NotNull String phoneNum2,@NotNull Integer orderId){
        return chatService.getAllChat(phoneNum1,phoneNum2,orderId);
    }


    /**
     * getAllChatOfPage
     * @description 分页查询一方发送给另一方的所有聊天信息，时间顺序排序（单向，一个订单中),如果页数超过最大，则返回null，
     * @param phoneNum1 其中一个人的手机号码
     * @param phoneNum2 另一个人的手机号码
     * @param pageNum 页码（从第1页开始）
     * @param pageSize 每页数
     * @param orderId 订单ID
     * @return
     * @author May
     */
    @ResponseBody
    @PostMapping("/getAllChatOfPage")
    public List<Chat> getAllChatOfPage(@NotNull String phoneNum1,@NotNull String phoneNum2, @NotNull Integer pageNum, @NotNull Integer pageSize,@NotNull Integer orderId){
        return chatService.getAllChatOfPage(phoneNum1,phoneNum2,pageNum,pageSize,orderId);
    }


    /**
     * getAllIndividualChat
     * @description 查询一方发送给另一方的所有聊天信息，时间顺序排序（单向，一个订单中)。
     * @param fromUserPhoneNum 发送人手机号码
     * @param toUserPhoneNum 接收人手机号码
     * @param orderId 订单ID
     * @return
     * @author May
     */
    @ResponseBody
    @PostMapping("/getAllIndividualChat")
    public List<Chat> getAllIndividualChat(@NotNull String fromUserPhoneNum,@NotNull String toUserPhoneNum,@NotNull Integer orderId){
        return chatService.getAllIndividualChat(fromUserPhoneNum,toUserPhoneNum,orderId);
    }

    /**
     * getNewChatNumOfOne
     * @description 查询某个用户关于一个用户的未读信息数量(一个订单中)
     * @param fromUserPhoneNum 新消息发送人手机号码
     * @param toUserPhoneNum 新消息接收人手机号码
     * @param orderId 订单ID
     * @return
     * @author May
     */
    @ResponseBody
    @PostMapping("/getNewChatNumOfOne")
    public Integer getNewChatNumOfOne(@NotNull String fromUserPhoneNum,@NotNull String toUserPhoneNum,@NotNull Integer orderId){
        return chatService.getNewChatNumOfOne(fromUserPhoneNum,toUserPhoneNum,orderId);
    }

    /**
     * getAllNewChatNum
     * @description 查询某个用户关于所有用户的未读信息数量
     * @param phoneNum 手机号
     * @return
     * @author May
     */
    @ResponseBody
    @PostMapping("/getAllNewChatNum")
    public Integer getAllNewChatNum(@NotNull String phoneNum){
        return chatService.getAllNewChatNum(phoneNum);
    }

    /**
     * updChatState
     * @description 将某个用户与另一个用户的聊天信息全部置为已读。比如：A打开了和B的聊天界面，则A所接受的B发送的消息全变为了已读(一个订单中)
     * @param phoneNum 该用户手机号
     * @param anotherUserPhoneNum 另一个用户手机号
     * @param orderId 订单ID
     * @author May
     */
    @ResponseBody
    @PostMapping("/updChatState")
    public void updChatState(@NotNull String phoneNum,@NotNull String anotherUserPhoneNum,@NotNull Integer orderId){
        chatService.updChatState(phoneNum,anotherUserPhoneNum,orderId);
    }




}

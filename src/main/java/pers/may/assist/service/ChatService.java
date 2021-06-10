package pers.may.assist.service;

import pers.may.assist.pojo.Chat;
import pers.may.assist.pojo.User;

import java.util.List;

public interface ChatService {

    /**
     * getMaxChatNum
     * @description 查询一方发送给另一方的所有聊天信息的总条数。
     * @param fromUserPhoneNum
     * @param toUserPhoneNum
     * @author May
     * @return
     */
    public Integer getMaxChatNum(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     *
     * 插入一条文字或图片聊天消息，该消息的状态为未读
     * 聊天图片命名为 "fromPhoneNum_toPhoneNum_时间戳"
     * 聊天图片存储路径为/assist/img/chat/
     * @param chat
     * @author May
     * @return 消息内容
     */
    public String addSingleUnreadChat(Chat chat);


    /**
     * getAllChat
     * @description 查询一方发送给另一方的所有聊天信息，时间顺序排序（单向）。
     * @param fromUserPhoneNum 发送人手机号码
     * @param toUserPhoneNum 接收人手机号码
     * @return
     * @author May
     */
    public List<Chat> getAllChat(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     * getAllChatOfPage
     * @description 分页查询一方发送给另一方的所有聊天信息，时间顺序排序（单向),如果页数超过最大，则返回null
     * @param fromUserPhoneNum 发送人手机号码
     * @param toUserPhoneNum 接收人手机号码
     * @param pageNum 页码（从第1页开始）
     * @param pageSize 每页数
     * @return
     * @author May
     */
    public List<Chat> getAllChatOfPage(String fromUserPhoneNum,String toUserPhoneNum,Integer pageNum,Integer pageSize,Integer orderId);


    /**
     * getAllIndividualChat
     * @description 查询两人之间的所有聊天信息，时间顺序排序（双向）
     * @param fromUserPhoneNum
     * @param toUserPhoneNum
     * @return
     * @author May
     */
    public List<Chat> getAllIndividualChat(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     * getNewChatNumOfOne
     * @description 查询某个用户关于一个用户的未读信息数量
     * @param fromUserPhoneNum 新消息发送人手机号码
     * @param toUserPhoneNum 新消息接收人手机号码
     * @return
     * @author May
     */
    public int getNewChatNumOfOne(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     * getAllNewChatNum
     * @description 查询某个用户关于所有用户的未读信息数量
     * @param phoneNum
     * @return
     * @author May
     */
    public int getAllNewChatNum(String phoneNum);

    /**
     * updChatState
     * @description 将某个用户与另一个用户的聊天信息全部置为已读。比如：A打开了和B的聊天界面，则A所接受的B发送的消息全变为了已读
     * @param phoneNum 该用户所接受的消息的发送者
     * @param anotherUserPhoneNum 该用户
     * @author May
     */
    public void updChatState(String phoneNum,String anotherUserPhoneNum,Integer orderId);

    /**
     * ifExistUser
     * @description 判断是否存在用户
     * @param phoneNumber 用户手机号
     * @return 存在返回true
     * @author May
     */
    public boolean ifExistUser(String phoneNumber);

    /**
     * ifExistOrderByOderId
     * @description 判断是否存在订单
     * @param orderId
     * @return
     */
    public boolean ifExistOrderByOderId(Integer orderId);



}

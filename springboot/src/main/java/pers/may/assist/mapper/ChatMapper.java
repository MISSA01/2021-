package pers.may.assist.mapper;

import org.apache.ibatis.annotations.Param;
import pers.may.assist.pojo.Chat;
import pers.may.assist.pojo.User;

import java.util.List;

public interface ChatMapper {
    /**
     *
     * 查询一方发送给另一方的所有聊天信息的总条数
     * @param fromUserPhoneNum
     * @param toUserPhoneNum
     * @param orderId
     * @return
     */
    public Integer selMaxChatNum(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     * 向数据库插入一条文字或图片聊天消息,该消息为未读
     * @param chat
     *
     */
    public void insertSingleUnreadChat(Chat chat);

    /**
     * 通过发送人和接受人查询所有聊天消息，按照时间顺序排列
     * @param fromUserPhoneNum 消息发送者
     * @param toUserPhoneNum 消息接受者
     * @param orderId
     * @return
     */
    public List<Chat> selAllChat(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     * 分页查询一方发送给另一方的所有聊天信息，时间顺序排序（单向）
     * @param fromUserPhoneNum 发送人
     * @param toUserPhoneNum 接收人
     * @param pageNum 页码（从第1页开始）
     * @param pageSize 每页数
     * @param orderId
     * @return
     */
    public List<Chat> selAllChatOfPage(String fromUserPhoneNum,String toUserPhoneNum,Integer pageNum,Integer pageSize,Integer orderId);


    /**
     * 查询某个用户关于一个用户的未读信息数量
     * @param fromUserPhoneNum 新消息接收人
     * @param toUserPhoneNum 新消息发送人
     * @param orderId
     * @return
     */
    public int selNewChatNumOfOne(String fromUserPhoneNum,String toUserPhoneNum,Integer orderId);

    /**
     * 查询某个用户关于所有用户的未读信息数量
     * @param phoneNum
     * @return
     */
    public int selAllNewChatNum(String phoneNum);

    /**
     * 将某个用户与另一个用户的聊天信息全部置为已读
     * 比如：A打开了和B的聊天界面，则A所接受的B发送的消息全变为了已读
     * @param phoneNum 该用户所接受的消息的发送者
     * @param anotherUserPhoneNum 该用户
     * @param orderId
     */
    public void updChatState(String phoneNum,String anotherUserPhoneNum,Integer orderId);

}

package pers.may.assist.pojo;



import com.sun.istack.internal.NotNull;

import java.util.Date;

public class Chat {
    private Integer chatId;//聊天消息的自增id
    @NotNull
    private String fromPhoneNum;//消息发送人手机号
    @NotNull
    private String toPhoneNum;//消息接收人手机号
    private Date sentTime;//发送时间
    @NotNull
    private String chatContent;//消息内容

    private Integer chatState;//消息状态（1-已读，0-未读）
    @NotNull
    private Integer chatType;//消息类型（1-文字，0-图片，2是二维码）
    @NotNull
    private Integer orderId; //订单id

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getFromPhoneNum() {
        return fromPhoneNum;
    }

    public void setFromPhoneNum(String fromPhoneNum) {
        this.fromPhoneNum = fromPhoneNum;
    }

    public String getToPhoneNum() {
        return toPhoneNum;
    }

    public void setToPhoneNum(String toPhoneNum) {
        this.toPhoneNum = toPhoneNum;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public Integer getChatState() {
        return chatState;
    }

    public void setChatState(Integer chatState) {
        this.chatState = chatState;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", fromPhoneNum='" + fromPhoneNum + '\'' +
                ", toPhoneNum='" + toPhoneNum + '\'' +
                ", sentTime=" + sentTime +
                ", chatContent='" + chatContent + '\'' +
                ", chatState=" + chatState +
                ", chatType=" + chatType +
                ", orderId=" + orderId +
                '}';
    }
}

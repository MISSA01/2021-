package pers.may.assist.pojo;

import java.util.Date;

public class Orders {
    private Integer orderId;//自增主键
    private Integer taskId;//对应任务ID
    private String phoneNum;//接任务的人的电话
    private Date receiveTime;//接受时间
    private Date finishTime;//完成时间
    private Task task;//本订单对应的任务的具体属性

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", taskId=" + taskId +
                ", phoneNum='" + phoneNum + '\'' +
                ", receiveTime=" + receiveTime +
                ", finishTime=" + finishTime +
                ", task=" + task +
                '}';
    }
}

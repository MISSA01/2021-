package pers.may.assist.pojo;

import com.sun.istack.internal.NotNull;

import java.util.Date;

public class Task {
    private Integer taskId; //自增主键
    @NotNull
    private String phoneNum; //任务发起者电话
    @NotNull
    private String taskTitle;//任务标题
    @NotNull
    private Integer taskType;//任务类型
    @NotNull
    private float taskReward;//任务奖励
    @NotNull
    private String taskContent;//任务内容
    @NotNull
    private Date taskInTime;//任务发布时间
    @NotNull
    private Date taskOutTime;//任务截止时间
    @NotNull
    private Integer taskState;//任务状态
    @NotNull
    private Integer taskPeoNum;//任务人数
    @NotNull
    private Integer taskAvaPeoNum;//任务人数

    public Integer getTaskAvaPeoNum() {
        return taskAvaPeoNum;
    }

    public void setTaskAvaPeoNum(Integer taskAvaPeoNum) {
        this.taskAvaPeoNum = taskAvaPeoNum;
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

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public float getTaskReward() {
        return taskReward;
    }

    public void setTaskReward(float taskReward) {
        this.taskReward = taskReward;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Date getTaskInTime() {
        return taskInTime;
    }

    public void setTaskInTime(Date taskInTime) {
        this.taskInTime = taskInTime;
    }

    public Date getTaskOutTime() {
        return taskOutTime;
    }

    public void setTaskOutTime(Date taskOutTime) {
        this.taskOutTime = taskOutTime;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Integer getTaskPeoNum() {
        return taskPeoNum;
    }

    public void setTaskPeoNum(Integer taskPeoNum) {
        this.taskPeoNum = taskPeoNum;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", phoneNum='" + phoneNum + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskType=" + taskType +
                ", taskReward=" + taskReward +
                ", taskContent='" + taskContent + '\'' +
                ", taskInTime=" + taskInTime +
                ", taskOutTime=" + taskOutTime +
                ", taskState=" + taskState +
                ", taskPeoNum=" + taskPeoNum +
                ", taskAvaPeoNum=" + taskAvaPeoNum +
                '}';
    }

}

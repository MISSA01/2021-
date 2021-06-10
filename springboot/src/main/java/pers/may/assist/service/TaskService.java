package pers.may.assist.service;



import org.apache.ibatis.annotations.Update;
import pers.may.assist.pojo.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTask();//获取全部任务
    //获取不同类型的任务
    List<Task> getTask(Integer taskType);
//    List<Task> getZeroTask();
//    List<Task> getOneTask();
//    List<Task> getTwoTask();
//    List<Task> getThreeTask();
//    List<Task> getFourTask();

    //获取自己发布的不同类型的任务
    List<Task> getTaskByPhoneNum(String phoneNum);
    List<Task> getAllTaskForPage(Integer page,Integer num);//分页拿任务
    List<Task> getTaskByPhoneNum(String phoneNum , Integer taskType);
    //分页获取不同类型的任务
    List<Task> getTaskForPage(Integer taskType,Integer page,Integer num);
    //将任务设置成不可被接取
    void lockTaskByTaskId(Integer taskId);
//    List<Task> getZeroTaskByPhoneNum(String phoneNum);
//
//    List<Task> getOneTaskByPhoneNum(String phoneNum);
//
//    List<Task> getTwoTaskByPhoneNum(String phoneNum);
//
//
//    List<Task> getThreeTaskByPhoneNum(String phoneNum);
//
//    List<Task> getFourTaskByPhoneNum(String phoneNum);
    //添加修改任务
    void addOneTask(Task task);
    void updOneTask(Task task);
    //根据ID获取对应没有超时的任务
    Task getTaskByTaskId(Integer taskId);
    //接取任务
    void pickupTaskByTaskId(Integer taskId);
    //完成任务
    void finishTaskByTaskId(Integer taskId);
    //一个人发布的还没被接取的任务
    List<Task> getTaskingByPhoneNum(String phoneNum);
    //放弃任务
    void GiveUpTaskByTaskId(Integer taskId);
    //增加可用人数
    void addAvaPeoNumByTaskId(Integer taskId);
    void setTaskStateByTaskId(Integer taskId);

}

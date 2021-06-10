package pers.may.assist.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import pers.may.assist.pojo.Task;

import java.util.List;

@Mapper
@Repository
public interface TaskDao {
    //0-外卖类型，1-快递类型，2-食堂带饭类型，3-求队友类型，4-其他类型
    //返回全部可以被接取的任务
    @Select("select * from task where  taskState = 0 and taskAvaPeoNum > 0 and taskOutTime > now() ")
    List<Task> getAllTask();

    //返回全部可以被接取的任务--分页限制从第n页的x条数据
    @Select("select * from task where  taskState = 0 and taskAvaPeoNum > 0 and taskOutTime > now() limit #{page} , #{num} ")
    List<Task> getAllTaskForPage(Integer page,Integer num);

    //一个人发布的还没被接取的任务
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now()  and `phoneNum` = #{phoneNum}")
    List<Task> getTaskingByPhoneNum(String phoneNum);

    //返回全部可以被接取的0-外卖类型任务
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 0  ")
    List<Task> getZeroTask();
    //返回全部可以被接取的1-快递类型任务
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 1  ")
    List<Task> getOneTask();
    //返回全部可以被接取的2-食堂带饭类型任务
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 2  ")
    List<Task> getTwoTask();
    //返回全部可以被接取的3-求队友类型任务
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 3  ")
    List<Task> getThreeTask();
    //返回全部可以被接取的4-其他类型任务
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 4  ")
    List<Task> getFourTask();


    //对以上任务进行分页
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 0  limit #{page} , #{num}")
    List<Task> getZeroTaskForPage(Integer page,Integer num);
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 1  limit #{page} , #{num}")
    List<Task> getOneTaskForPage(Integer page,Integer num);
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 2  limit #{page} , #{num}")
    List<Task> getTwoTaskForPage(Integer page,Integer num);

    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 3  limit #{page} , #{num}")
    List<Task> getThreeTaskForPage(Integer page,Integer num);
    @Select("select * from task where taskState = 0  and taskAvaPeoNum > 0 and taskOutTime > now() and taskType = 4  limit #{page} , #{num}")
    List<Task> getFourTaskForPage(Integer page,Integer num);
    //返回个人发布的任务，总体-分支
    //总体
    @Select("select * from task where `phoneNum` = #{phoneNum}")
    List<Task> getTaskByPhoneNum(String phoneNum);

    @Select("select * from task where `phoneNum` = #{phoneNum} and `taskType` = 0")
    List<Task> getZeroTaskByPhoneNum(String phoneNum);

    @Select("select * from task where `phoneNum` = #{phoneNum} and `taskType` = 1")
    List<Task> getOneTaskByPhoneNum(String phoneNum);

    @Select("select * from task where `phoneNum` = #{phoneNum} and `taskType` = 2")
    List<Task> getTwoTaskByPhoneNum(String phoneNum);

    @Select("select * from task where `phoneNum` = #{phoneNum} and `taskType` = 3")
    List<Task> getThreeTaskByPhoneNum(String phoneNum);

    @Select("select * from task where `phoneNum` = #{phoneNum} and `taskType` = 4")
    List<Task> getFourTaskByPhoneNum(String phoneNum);

    //添加任务
    @Insert("insert into task(`phoneNum`,`taskTitle`,`taskType`,`taskReward`,`taskContent`,`taskInTime`,`taskOutTime`,`taskState`,`taskPeoNum`,`taskAvaPeoNum`) values(#{phoneNum},#{taskTitle},#{taskType},#{taskReward},#{taskContent},#{taskInTime},#{taskOutTime},#{taskState},#{taskPeoNum},#{taskAvaPeoNum})")
    void addOneTask(Task task);

    //修改任务
    @Update("update task set `taskTitle` = #{taskTitle},`taskType` = #{taskType},`taskReward` = #{taskReward},`taskContent` = #{taskContent},`taskPeoNum` = #{taskPeoNum},`taskAvaPeoNum` = #{taskAvaPeoNum} where `taskId` = #{taskId}")
    void updOneTask(Task task);

    //根据任务Id找任务
    @Select("select * from task where `taskId` = #{taskId} ")
    Task getTaskByTaskId(Integer taskId);

    //接取任务,根据任务Id
    @Update("update task set `taskAvaPeoNum` = `taskAvaPeoNum` - 1 where `taskId` = #{taskId}")
    void pickupTaskByTaskId(Integer taskId);

    //完成任务
    @Update("update task set `taskState` = 1 where `taskId` = #{taskId}")
    void finishTaskByTaskId(Integer taskId);
    //将任务设置成不可被接取
    @Update("update task set `taskState` = 1 where `taskId` = #{taskId}")
    void lockTaskByTaskId(Integer taskId);

    //放弃任务
    @Update("update task set `taskState` = 1 where `taskId` = #{taskId}")
    void GiveUpTaskByTaskId(Integer taskId);
    //放弃订单的操作，可用人员+1
    @Update("update task set taskAvaPeoNum = taskAvaPeoNum + 1 where `taskId` = #{taskId}")
    void addAvaPeoNumByTaskId(Integer taskId);
    //将任务设置成可以被接取
    @Update("update task set taskState = 0  where `taskId` = #{taskId}")
    void setTaskStateByTaskId(Integer taskId);

}

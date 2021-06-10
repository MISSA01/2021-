package pers.may.assist.mapper;

import pers.may.assist.pojo.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersDao {
    //生成订单
    @Insert("insert into orders(`taskId`,`phoneNum`,`receiveTime`) values(#{taskId},#{phoneNum},#{receiveTime})")
    void addOrders(Orders orders);
    //完成订单
    @Update("update orders set `finishTime` = now() where `orderId` = #{orderId}")
    void finishOrdersByOrderId(Integer orderId);
    //查询某个用户正在进行的订单--全部
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is null order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
            one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getOrderingByPhoneNum(String phoneNum);

    //查询某个用户正在进行的不同的订单--01234
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 0) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getZeroOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 1) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getOneOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 2) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getTwoOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 3) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getThreeOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 4) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getFourOrderingByPhoneNum(String phoneNum);

    //查询某个用户已经完成的订单--全部
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is not null order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
            one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")

    })
    List<Orders> getOrderedByPhoneNum(String phoneNum);

    //查询某个用户已经完成的订单01234
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is not null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 0) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")

    })
    List<Orders> getZeroOrderedByPhoneNum(String phoneNum);

    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is not null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 1) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")

    })
    List<Orders> getOneOrderedByPhoneNum(String phoneNum);
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is not null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 2) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")

    })
    List<Orders> getTwoOrderedByPhoneNum(String phoneNum);
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is not null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 3) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")

    })
    List<Orders> getThreeOrderedByPhoneNum(String phoneNum);
    @Select("select * from orders where `phoneNum` = #{phoneNum} and `finishTime` is not null and" +
            "`taskId` in (select `taskId` from task where `taskType` = 4) order by finishTime desc")
    @Results({
            @Result(property = "task",column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")

    })
    List<Orders> getFourOrderedByPhoneNum(String phoneNum);

    //根据taskId,phoneNum返回订单,目的是从中找到对应人和对应任务已经有的订单
    @Select("select * from orders where `taskId` = #{taskId} and `phoneNum` = #{phoneNum}")
    Orders getOrderByPhoneNumAndTaskId(String phoneNum,Integer taskId);

    //根据taskId返回task对应的所有订单
    @Select("select * from orders where `taskId` = #{taskId}")
    List<Orders> getOrdersByTaskId(Integer taskId);

    //查询某个用户发布的被接取的订单--全部订单（未完成）
    @Select("select * from orders where `finishTime` is null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum} ) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
            one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getPublishOrderingByPhoneNum(String phoneNum);

    //从发布者的角度，查询我发布的任务对应的所有订单中类型是01234的（未完成）
    @Select("select * from orders where `finishTime` is null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 0) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getZeroPublishOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 1) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getOnePublishOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 2) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getTwoPublishOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum} and  `taskType` = 3) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getThreePublishOrderingByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 4) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getFourPublishOrderingByPhoneNum(String phoneNum);

    //查询某个用户发布的被接取的订单--全部订单（已完成）
    @Select("select * from orders where `finishTime` is not null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum} ) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getPublishOrderedByPhoneNum(String phoneNum);

    //从发布者的角度，查询我发布的任务对应的所有订单中类型是01234的（已完成）
    @Select("select * from orders where `finishTime` is not null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 0) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getZeroPublishOrderedByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is not null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 1) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getOnePublishOrderedByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is not null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 2) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getTwoPublishOrderedByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is not null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 3) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getThreePublishOrderedByPhoneNum(String phoneNum);

    @Select("select * from orders where `finishTime` is not null and `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum}  and `taskType` = 4) order by finishTime desc")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getFourPublishOrderedByPhoneNum(String phoneNum);

    @Select("select * from orders where `orderId` = #{orderId}")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    Orders getOrderByOrderId(Integer orderId);

    @Select("select * from orders where `phoneNum` = #{phoneNum}")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getAllOrderByPhoneNum(String phoneNum);

    @Select("select * from orders where `taskId` in (" +
            "select `taskId` from task where `phoneNum` = #{phoneNum})")
    @Results({
            @Result(property = "task" , column = "taskId",
                    one = @One(select = "pers.may.assist.mapper.TaskDao.getTaskByTaskId")),
            @Result(property = "taskId",column = "taskId")
    })
    List<Orders> getAllPublishOrderByPhoneNum(String phoneNum);

    //放弃订单
    @Delete("delete from orders where `orderId` = #{orderId}")
    void GiveUpOrdersByOrderId(Integer orderId);
    //根据phoneNum和taskId找orderId
    @Select("select orderId from orders where phoneNum = #{phoneNum} and taskId = #{taskId}")
    Integer getOrderIdByPhoneNumAndTaskId(String phoneNum,Integer taskId);

}

package pers.may.assist.service;



import pers.may.assist.pojo.Orders;

import java.util.List;

public interface OrdersService {
    //生成订单
    void addOrders(Orders orders);

    //完成订单
    void finishOrdersByOrderId(Integer orderId);

    //查询某个用户正在进行的订单
    List<Orders> getOrderingByPhoneNum(String phoneNum);

    //查询某个用户正在进行的不同的订单--01234
    List<Orders> getOrderingByPhoneNum(String phoneNum,Integer taskType);

//    List<Orders> getZeroOrderingByPhoneNum(String phoneNum);
//
//
//    List<Orders> getOneOrderingByPhoneNum(String phoneNum);
//
//
//    List<Orders> getTwoOrderingByPhoneNum(String phoneNum);
//
//
//    List<Orders> getThreeOrderingByPhoneNum(String phoneNum);
//
//
//    List<Orders> getFourOrderingByPhoneNum(String phoneNum);

    //查询某个用户已经完成的订单
    List<Orders> getOrderedByPhoneNum(String phoneNum);

    //查询某个用户已经完成的订单01234
    List<Orders> getOrderedByPhoneNum(String phoneNum,Integer taskType);

//    List<Orders> getZeroOrderedByPhoneNum(String phoneNum);
//
//
//    List<Orders> getOneOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getTwoOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getThreeOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getFourOrderedByPhoneNum(String phoneNum);

    //查询某个用户发布的被接取的订单（未完成）
    List<Orders> getPublishOrderingByPhoneNum(String phoneNum);

    //从发布者的角度，查询我发布的任务对应的所有订单中类型是01234的（未完成）
    List<Orders> getPublishOrderingByPhoneNum(String phoneNum,Integer taskType);

//    List<Orders> getZeroPublishOrderingByPhoneNum(String phoneNum);
//
//    List<Orders> getOnePublishOrderingByPhoneNum(String phoneNum);
//
//    List<Orders> getTwoPublishOrderingByPhoneNum(String phoneNum);
//
//    List<Orders> getThreePublishOrderingByPhoneNum(String phoneNum);
//
//    List<Orders> getFourPublishOrderingByPhoneNum(String phoneNum);

    //查询某个用户发布的被接取的订单--全部订单（已完成）
    List<Orders> getPublishOrderedByPhoneNum(String phoneNum);

    //从发布者的角度，查询我发布的任务对应的所有订单中类型是01234的（已完成）
    List<Orders> getPublishOrderedByPhoneNum(String phoneNum,Integer taskType);

//    List<Orders> getZeroPublishOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getOnePublishOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getTwoPublishOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getThreePublishOrderedByPhoneNum(String phoneNum);
//
//    List<Orders> getFourPublishOrderedByPhoneNum(String phoneNum);

    //根据taskId和phoneNum返回唯一的订单
    Orders getOrderByPhoneNumAndTaskId(String phoneNum,Integer taskId);

    //根据taskId返回task对应的所有订单
    List<Orders> getOrdersByTaskId(Integer taskId);

    //根据orderId返回订单
    Orders getOrderByOrderId(Integer orderId);

    //获取一个人的全部的已接取的订单
    List<Orders> getAllOrderByPhoneNum(String phoneNum);

    //获取一个人的发布的全部的订单
    List<Orders> getAllPublishOrderByPhoneNum(String phoneNum);
    //放弃订单
    void GiveUpOrdersByOrderId(Integer orderId);
    Integer getOrderIdByPhoneNumAndTaskId(String phoneNum,Integer taskId);



}

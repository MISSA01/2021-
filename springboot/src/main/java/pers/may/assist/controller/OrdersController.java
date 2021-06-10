package pers.may.assist.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.may.assist.pojo.Orders;
import pers.may.assist.service.OrdersService;
import pers.may.assist.service.TaskService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
/**
 *订单接口
 **/
@RestController
public class OrdersController {//因为订单的产生和完成都是伴随着任务进行的
                                //所以在这里只要进行订单的查询就可以了
    @Resource
    OrdersService ordersService;

    @Resource
    TaskService taskService;

    //查询一个人发布的任务的接取订单，从任务发起者的角度,分为已经完成和没有完成
    //正在进行
    /**
     *
     * getAllPublishOrderingByPhoneNum
     * @description 任务发起者的角度，发布的被接取的正在进行的全部任务订单
     * @param phoneNum 用户手机号
     * @return 任务发布者发布的被接取的正在进行中的全部订单
     * @author MISSA
     */
    @GetMapping("/orders/getAllPublishOrderingByPhoneNum")
    public List<Orders> getPublishOrderingByPhoneNum(String phoneNum){
        return ordersService.getPublishOrderingByPhoneNum(phoneNum);
    }

    /**
     * getPublishOrderingByPhoneNum
     *@description 任务发起者的角度，发布的被接取的正在进行的不同类型订单
     * @param phoneNum 用户手机号
     * @param taskType 任务类型
     * @return 任务发布者发布的被接取的正在进行中的不同类型订单
     * @author MISSA
     */
    @GetMapping("/orders/getPublishOrderingByPhoneNum")
    public List<Orders> getPublishOrderingByPhoneNum(String phoneNum , int taskType){
        return ordersService.getPublishOrderingByPhoneNum(phoneNum,taskType);
    }


    //已经完成

    /**
     * getAllPublishOrderedByPhoneNum
     * @description 任务发起者的角度，发布的被接取的已经完成的全部订单
     * @param phoneNum 用户手机号
     * @return 任务发布者发布的被接取的已完成的全部订单
     * @author MISSA
     */
    @GetMapping("/orders/getAllPublishOrderedByPhoneNum")
    public List<Orders> getPublishOrderedByPhoneNum(String phoneNum){
        return ordersService.getPublishOrderedByPhoneNum(phoneNum);
    }

    /**
     * getPublishOrderedByPhoneNum
     * @description 任务发起者的角度，发布的被接取的已经完成的不同类型订单
     * @param phoneNum 用户手机号
     * @param taskType 任务类型
     * @return 任务发布者发布的被接取的已完成的不同类型订单
     * @author MISSA
     */
    @GetMapping("/orders/getPublishOrderedByPhoneNum")
    public List<Orders> getPublishOrderedByPhoneNum(String phoneNum,int taskType){
        return ordersService.getPublishOrderedByPhoneNum(phoneNum,taskType);
    }



    //查询一个人接取的不同类型订单,分为已经完成和没有完成
    //正在进行

    /**
     * getAllOrderingByPhoneNum
     * @description 任务接取者的角度，接取的正在进行的全部订单
     * @param phoneNum 用户手机号
     * @return 任务接取者接取的正在进行的全部订单
     * @author MISSA
     */
    @GetMapping("/orders/getAllOrderingByPhoneNum")
    public List<Orders> getOrderingByPhoneNum(String phoneNum){
        return ordersService.getOrderingByPhoneNum(phoneNum);
    }

    /**
     * getOrderingByPhoneNum
     * @description 任务接取者的角度，接取的正在进行的不同类型订单
     * @param phoneNum 用户手机号
     * @param taskType 任务类型
     * @return 任务接取者接取的正在进行的不同类型订单
     * @author MISSA
     */
    @GetMapping("/orders/getOrderingByPhoneNum")
    public List<Orders> getOrderingByPhoneNum(String phoneNum,int taskType){
        return ordersService.getOrderingByPhoneNum(phoneNum,taskType);
    }


    //已经完成

    /**
     * getAllOrderedByPhoneNum
     * @description 任务接取者角度，接取的已经完成的全部订单
     * @param phoneNum 用户手机号
     * @return 任务接取者接取的已完成的全部订单
     * @author MISSA
     */
    @GetMapping("/orders/getAllOrderedByPhoneNum")
    public List<Orders> getOrderedByPhoneNum(String phoneNum){
        return ordersService.getOrderedByPhoneNum(phoneNum);
    }

    /**
     * getOrderedByPhoneNum
     * @description 任务接取者角度，接取的已经完成的不同类型订单
     * @param phoneNum 用户手机号
     * @param taskType 任务类型
     * @return 任务接取者接取的已完成的不同类型订单
     * @author MISSA
     */
    @GetMapping("/orders/getOrderedByPhoneNum")
    public List<Orders> getOrderedByPhoneNum(String phoneNum , int taskType){
        return ordersService.getOrderedByPhoneNum(phoneNum,taskType);
    }



    /**
     * finishOrderByOrderId
     * @description 完成订单
     * @param orderId 订单自增主键
     * @return "success"
     * @author MISSA
     */
    //完成订单，根据订单号
    @PostMapping("/orders/finishOrderByOrderId")
    public String finishOrderByOrderId(Integer orderId){
        ordersService.finishOrdersByOrderId(orderId);
        return "success";
    }

    /**
     * getOrdersByOrderId
     * @description 根据订单ID获取订单
     * @param orderId 订单Id
     * @return 订单对象
     * @author MISSA
     */
    @GetMapping("/orders/getOrdersByOrderId")
    public Orders getOrdersByOrderId(Integer orderId){
        return ordersService.getOrderByOrderId(orderId);
    }

    /**
     * getAllOrdersByPhoneNum
     * @description 获取一个人的接取的全部订单
     * @param phoneNum 接取人的手机号
     * @return 订单列表
     * @author MISSA
     */
    @GetMapping("/orders/getAllOrdersByPhoneNum")
    public List<Orders> getAllOrdersByPhoneNum(String phoneNum){
        List<Orders> orders = ordersService.getOrderingByPhoneNum(phoneNum);
        orders.addAll(ordersService.getOrderedByPhoneNum(phoneNum));
        return orders;
    }

    /**
     * getAllPublishOrdersByPhoneNum
     * @description 获取一个人的发布的任务形成的全部订单
     * @param phoneNum 发布者手机号
     * @return 订单列表
     * @author MISSA
     */
    @GetMapping("/orders/getAllPublishOrdersByPhoneNum")
    public List<Orders> getAllPublishOrdersByPhoneNum(String phoneNum){
        List<Orders> orders = ordersService.getPublishOrderingByPhoneNum(phoneNum);
        orders.addAll(ordersService.getPublishOrderedByPhoneNum(phoneNum));
        return orders;
    }

    /**
     * getPublishOrdersByPhoneNum
     * @description 获取发布者的不同类型的任务生成的订单
     * @param phoneNum 发布者手机号
     * @param taskType 任务类型
     * @return 订单列表
     * @author MISSA
     */
    @GetMapping("/orders/getPublishOrdersByPhoneNum")
    public List<Orders> getPublishOrdersByPhoneNum(String phoneNum,Integer taskType){
        List<Orders> orders = ordersService.getPublishOrderingByPhoneNum(phoneNum,taskType);
        orders.addAll(ordersService.getPublishOrderedByPhoneNum(phoneNum,taskType));
        return orders;
    }

    /**
     * getOrdersByPhoneNum
     * @description 获取接取者的不同类型的全部订单
     * @param phoneNum 接取者的手机号
     * @param taskType 任务类型
     * @return 订单列表
     * @author MISSA
     */
    @GetMapping("/orders/getOrdersByPhoneNum")
    public List<Orders> getOrdersByPhoneNum(String phoneNum,Integer taskType){
        List<Orders> orders = ordersService.getOrderingByPhoneNum(phoneNum,taskType);
        orders.addAll(ordersService.getOrderedByPhoneNum(phoneNum,taskType));
        return orders;

    }

    /**
     * getPeoAllOrdersByPhoneNum
     * @description 返回一个人的全部订单包括接取的和发布的
     * @param phoneNum 用户手机号
     * @return 订单列表
     * @author MISSA
     */
    @GetMapping("/orders/getPeoAllOrdersByPhoneNum")
    public List<Orders> getPeoAllOrdersByPhoneNum(String phoneNum){
        List<Orders> orders = ordersService.getOrderingByPhoneNum(phoneNum);

        orders.addAll(ordersService.getPublishOrderingByPhoneNum(phoneNum));
        orders.addAll(ordersService.getOrderedByPhoneNum(phoneNum));

        orders.addAll(ordersService.getPublishOrderedByPhoneNum(phoneNum));
        return orders;
    }

    /**
     * getPeoOrdersByPhoneNum
     * @description 一个人的不同类型的全部订单包括发布的和接取的
     * @param phoneNum 用户手机号
     * @param taskType 任务类型
     * @return 订单列表
     * @author MISSA
     */
    @GetMapping("/orders/getPeoOrdersByPhoneNum")
    public List<Orders> getPeoOrdersByPhoneNum(String phoneNum,Integer taskType){
        List<Orders> orders = ordersService.getOrderingByPhoneNum(phoneNum,taskType);

        orders.addAll(ordersService.getPublishOrderingByPhoneNum(phoneNum,taskType));
        orders.addAll(ordersService.getOrderedByPhoneNum(phoneNum,taskType));

        orders.addAll(ordersService.getPublishOrderedByPhoneNum(phoneNum,taskType));
        return orders;
    }


    /**
     * giveUpOrderByOrderId
     * @description 放弃订单
     * @param orderId 订单ID
     * @author MISSA
     */
    @PostMapping("/orders/giveUpOrderByOrderId")
    public String giveUpOrderByOrderId(Integer orderId){

        Integer taskId = ordersService.getOrderByOrderId(orderId).getTaskId();
        taskService.addAvaPeoNumByTaskId(taskId);
        taskService.setTaskStateByTaskId(taskId);
        ordersService.GiveUpOrdersByOrderId(orderId);
        return "success";
    }
}

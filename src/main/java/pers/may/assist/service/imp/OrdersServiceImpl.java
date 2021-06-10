package pers.may.assist.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.may.assist.mapper.OrdersDao;
import pers.may.assist.pojo.Orders;
import pers.may.assist.service.OrdersService;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersDao ordersDao;

    @Override
    public void addOrders(Orders orders) {
        ordersDao.addOrders(orders);
    }

    @Override
    public void finishOrdersByOrderId(Integer orderId) {
        ordersDao.finishOrdersByOrderId(orderId);
    }

    @Override
    public List<Orders> getOrderingByPhoneNum(String phoneNum) {
        return ordersDao.getOrderingByPhoneNum(phoneNum);
    }

    @Override
    public List<Orders> getOrderingByPhoneNum(String phoneNum, Integer taskType) {
        if(taskType == 0){
            return ordersDao.getZeroOrderingByPhoneNum(phoneNum);
        }else if (taskType == 1){
            return ordersDao.getOneOrderingByPhoneNum(phoneNum);
        }else if (taskType == 2){
            return ordersDao.getTwoOrderingByPhoneNum(phoneNum);
        }else if (taskType == 3){
            return ordersDao.getThreeOrderingByPhoneNum(phoneNum);
        }else if (taskType == 4){
            return ordersDao.getFourOrderingByPhoneNum(phoneNum);
        }else {
            return null;
        }
    }

    @Override
    public List<Orders> getOrderedByPhoneNum(String phoneNum) {
        return ordersDao.getOrderedByPhoneNum(phoneNum);
    }

    @Override
    public List<Orders> getOrderedByPhoneNum(String phoneNum, Integer taskType) {
        if(taskType == 0){
            return ordersDao.getZeroOrderedByPhoneNum(phoneNum);
        }else if (taskType == 1){
            return ordersDao.getOneOrderedByPhoneNum(phoneNum);
        }else if (taskType == 2){
            return ordersDao.getTwoOrderedByPhoneNum(phoneNum);
        }else if (taskType == 3){
            return ordersDao.getThreeOrderedByPhoneNum(phoneNum);
        }else if (taskType == 4){
            return ordersDao.getFourOrderedByPhoneNum(phoneNum);
        }else {
            return null;
        }
    }




    @Override
    public List<Orders> getPublishOrderingByPhoneNum(String phoneNum) {
        return ordersDao.getPublishOrderingByPhoneNum(phoneNum);
    }

    @Override
    public List<Orders> getPublishOrderingByPhoneNum(String phoneNum, Integer taskType) {
        if(taskType == 0){
            return ordersDao.getZeroPublishOrderingByPhoneNum(phoneNum);
        }else if (taskType == 1){
            return ordersDao.getOnePublishOrderingByPhoneNum(phoneNum);
        }else if (taskType == 2){
            return ordersDao.getTwoPublishOrderingByPhoneNum(phoneNum);
        }else if (taskType == 3){
            return ordersDao.getThreePublishOrderingByPhoneNum(phoneNum);
        }else if (taskType == 4){
            return ordersDao.getFourPublishOrderingByPhoneNum(phoneNum);
        }else {
            return null;
        }
    }



    @Override
    public List<Orders> getPublishOrderedByPhoneNum(String phoneNum) {
        return ordersDao.getPublishOrderedByPhoneNum(phoneNum);
    }

    @Override
    public List<Orders> getPublishOrderedByPhoneNum(String phoneNum, Integer taskType) {
        if(taskType == 0){
            return ordersDao.getZeroPublishOrderedByPhoneNum(phoneNum);
        }else if (taskType == 1){
            return ordersDao.getOnePublishOrderedByPhoneNum(phoneNum);
        }else if (taskType == 2){
            return ordersDao.getTwoPublishOrderedByPhoneNum(phoneNum);
        }else if (taskType == 3){
            return ordersDao.getThreePublishOrderedByPhoneNum(phoneNum);
        }else if (taskType == 4){
            return ordersDao.getFourPublishOrderedByPhoneNum(phoneNum);
        }else {
            return null;
        }
    }



    @Override
    public Orders getOrderByPhoneNumAndTaskId(String phoneNum,Integer taskId) {
        return ordersDao.getOrderByPhoneNumAndTaskId(phoneNum,taskId);
    }

    @Override
    public List<Orders> getOrdersByTaskId(Integer taskId) {
        return ordersDao.getOrdersByTaskId(taskId);
    }

    @Override
    public Orders getOrderByOrderId(Integer orderId) {
        return ordersDao.getOrderByOrderId(orderId);
    }

    @Override
    public List<Orders> getAllOrderByPhoneNum(String phoneNum) {
        return ordersDao.getAllOrderByPhoneNum(phoneNum);
    }

    @Override
    public List<Orders> getAllPublishOrderByPhoneNum(String phoneNum) {
        return ordersDao.getAllPublishOrderByPhoneNum(phoneNum);
    }

    @Override
    public void GiveUpOrdersByOrderId(Integer orderId) {
        ordersDao.GiveUpOrdersByOrderId(orderId);
    }

    @Override
    public Integer getOrderIdByPhoneNumAndTaskId(String phoneNum, Integer taskId) {
        return ordersDao.getOrderIdByPhoneNumAndTaskId(phoneNum,taskId);
    }
}

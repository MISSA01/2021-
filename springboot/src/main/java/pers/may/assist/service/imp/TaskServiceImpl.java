package pers.may.assist.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.may.assist.mapper.TaskDao;
import pers.may.assist.pojo.Task;
import pers.may.assist.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;
    @Override
    public List<Task> getAllTask() {
        return taskDao.getAllTask();
    }

    @Override
    public List<Task> getTask(Integer taskType) {
        if(taskType == 0){
            return taskDao.getZeroTask();
        }else if(taskType == 1){
            return taskDao.getOneTask();
        }else if(taskType == 2){
            return taskDao.getTwoTask();
        }else if(taskType ==3){
            return taskDao.getThreeTask();
        }else if(taskType == 4){
            return taskDao.getFourTask();
        }else {
            return null;
        }
    }
    @Override
    public List<Task> getAllTaskForPage(Integer page, Integer num) {
        return taskDao.getAllTaskForPage(page,num);
    }
    @Override
    public List<Task> getTaskForPage(Integer taskType, Integer page, Integer num) {
        if(taskType == 0){
            return taskDao.getZeroTaskForPage(page,num);
        }else if(taskType == 1){
            return taskDao.getOneTaskForPage(page,num);
        }else if(taskType == 2){
            return taskDao.getTwoTaskForPage(page,num);
        }else if(taskType ==3){
            return taskDao.getThreeTaskForPage(page,num);
        }else if(taskType == 4){
            return taskDao.getFourTaskForPage(page,num);
        }else {
            return null;
        }
    }

    @Override
    public void lockTaskByTaskId(Integer taskId) {
        taskDao.lockTaskByTaskId(taskId);
    }


    @Override
    public List<Task> getTaskByPhoneNum(String phoneNum) {
        return taskDao.getTaskByPhoneNum(phoneNum);
    }

    @Override
    public List<Task> getTaskByPhoneNum(String phoneNum, Integer taskType) {
        if(taskType == 0){
            return taskDao.getZeroTaskByPhoneNum(phoneNum);
        }else if(taskType == 1){
            return taskDao.getOneTaskByPhoneNum(phoneNum);
        }else if(taskType == 2){
            return taskDao.getTwoTaskByPhoneNum(phoneNum);
        }else if(taskType ==3){
            return taskDao.getThreeTaskByPhoneNum(phoneNum);
        }else if(taskType == 4){
            return taskDao.getFourTaskByPhoneNum(phoneNum);
        }else {
            return null;
        }
    }



    @Override
    public void addOneTask(Task task) {
        taskDao.addOneTask(task);
    }

    @Override
    public void updOneTask(Task task) {
        taskDao.updOneTask(task);
    }

    @Override
    public Task getTaskByTaskId(Integer taskId) {
        return taskDao.getTaskByTaskId(taskId);
    }

    @Override
    public void pickupTaskByTaskId(Integer taskId) {
        taskDao.pickupTaskByTaskId(taskId);
    }

    @Override
    public void finishTaskByTaskId(Integer taskId) {
        taskDao.finishTaskByTaskId(taskId);
    }

    @Override
    public List<Task> getTaskingByPhoneNum(String phoneNum) {
        return taskDao.getTaskingByPhoneNum(phoneNum);
    }

    @Override
    public void GiveUpTaskByTaskId(Integer taskId) {
        taskDao.GiveUpTaskByTaskId(taskId);
    }

    @Override
    public void addAvaPeoNumByTaskId(Integer taskId) {
        taskDao.addAvaPeoNumByTaskId(taskId);
    }

    @Override
    public void setTaskStateByTaskId(Integer taskId) {
        taskDao.setTaskStateByTaskId(taskId);
    }
}

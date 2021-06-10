package pers.may.assist.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.may.assist.pojo.Orders;
import pers.may.assist.pojo.Task;
import pers.may.assist.service.OrdersService;
import pers.may.assist.service.TaskService;

import javax.annotation.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *任务接口
 **/
@RestController
public class TaskController {
    @Resource
    private TaskService taskService;
    @Resource
    private OrdersService ordersService;
    //查询任务--总体任务查询

    /**
     * getAllTask
     * @description 查询全部可以接取的任务
     * @return 全部可以被接取的任务
     * @author MISSA
     */
    @GetMapping("/task/getAllTask")
    public List<Task> getAllTask(){
        return taskService.getAllTask();
    }
    /**
     * getAllTaskForPage
     * @description 分页返回全部可以接取的任务
     * @param page 第几页
     * @param num  多少个
     * @return 任务列表
     * @author MISSA
     */
    @GetMapping("/task/getAllTaskForPage")
    public List<Task> getAllTaskForPage(Integer page, Integer num){
        page = (page-1)*num;
        return taskService.getAllTaskForPage(page,num);
    }

    /**
     * getTask
     * @description 查询可以被接取的不同类型任务
     * @param  taskType 任务类型
     * @return 所有可以被接取的不同类型任务
     * @author MISSA
     */
    @GetMapping("/task/getTask")
    public List<Task> getTask(int taskType){
        return taskService.getTask(taskType);
    }



    /**
     * addOneTask
     * @description 发布任务
     * @param taskTime 任务持续时长，单位分钟
     * @param task 添加的任务对象
     * @return ”success“
     * @author MISSA
     */
    @PostMapping("/task/addOneTask")//发布任务
    public String addOneTask( String taskTime, Task task) throws ParseException {
        //System.out.println(timeLength);
        //System.out.println(task);
        task.setTaskState(0);
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        task.setTaskInTime(date);
        //System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date = sdf.parse(taskTime);
        task.setTaskOutTime(date);
        //System.out.println(date);
        task.setTaskAvaPeoNum(task.getTaskPeoNum());
        taskService.addOneTask(task);
        return "success";
    }
    //查询个人发布的任务 总体-分支

    /**
     * getAllTaskByPhoneNum
     * @description 查询一个人发布的全部任务
     * @param phoneNum 发布者手机号
     * @return 发布人发布的全部任务
     * @author MISSA
     */
    @GetMapping("/task/getAllTaskByPhoneNum")
    public List<Task> getTaskByPhoneNum(String phoneNum){
        return taskService.getTaskByPhoneNum(phoneNum);

    }

    /**
     * getTaskByPhoneNum
     * @description 查询一个人发布的不同类型任务
     * @param phoneNum 发布者手机号
     * @param taskType 任务类型
     * @return 发布人发布的不同类型任务
     * @author MISSA
     */
    @GetMapping("/task/getTaskByPhoneNum")
    public List<Task> getTaskByPhoneNum(String phoneNum ,int taskType){
        return taskService.getTaskByPhoneNum(phoneNum,taskType);
    }

    /**
     * updTaskByTaskId
     * @description 修改任务
     * @param task 修改之后的任务对象
     * @return 返回success或者error
     * @author MISSA
     */
    @PostMapping("/task/updTaskByTaskId")//修改任务
    public String updOneTask(Task task){
        Task task1 = taskService.getTaskByTaskId(task.getTaskId());
        Date date = new Date();
        if(task1.getTaskOutTime().before(date)){
            return "OutTimeError";
        }else {
            taskService.updOneTask(task);
            return "success";
        }
    }

    /**
     * pickupTaskByTaskId
     *@description 接取任务
     * @param phoneNum 接取者手机号
     * @param taskId 接取的任务ID
     * @return success或者是error
     * @author MISSA
     */
    @PostMapping("/task/pickupTaskByTaskId")//接取任务
    public String pickupTaskByTaskId(String phoneNum,Integer taskId){
        Task task = taskService.getTaskByTaskId(taskId);
        Date date = new Date();
        if(task.getTaskOutTime().before(date)){
            //判断任务是否已经超时
            return "outTimeError";
        }else if (task.getTaskAvaPeoNum()<1){
            //判断任务在这个时候是否没有名额了
            return "outPeoNumError";
        }else {
            //在这里要判断一下是否是重复接取任务--牵扯到多人任务
            Orders orders = ordersService.getOrderByPhoneNumAndTaskId(phoneNum,taskId);
            if(orders == null){
            taskService.pickupTaskByTaskId(taskId);
            Date date1 = new Date();
            Orders orders1 = new Orders();
            orders1.setTaskId(taskId);
            orders1.setPhoneNum(phoneNum);
            orders1.setReceiveTime(date1);
            ordersService.addOrders(orders1);
            Integer orderId = ordersService.getOrderIdByPhoneNumAndTaskId(phoneNum,taskId);
            if(taskService.getTaskByTaskId(taskId).getTaskAvaPeoNum()<1){
                    taskService.lockTaskByTaskId(taskId);
            }
            return ""+orderId;
            }else {
                return "duplicateError";//返回重复错误
            }
        }
    }

    /**
     * finishTaskByTaskId
     * @description 完成任务
     * @param taskId 要完成的任务ID
     * @return success或者是error
     * @author MISSA
     */
    @PostMapping("/task/finishTaskByTaskId")//完成任务，同时检查有没有没有完成的订单
    public String finishTaskByTaskId(Integer taskId){
        boolean flag = false;
        List<Orders> orders = ordersService.getOrdersByTaskId(taskId);
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getFinishTime() == null){
                flag = true;
            }
        }
        if (flag){
            return "OrderingError";
        }else {
            taskService.finishTaskByTaskId(taskId);
            return "success";
        }
    }

    /**
     * getTaskByTaskId
     * @description 根据taskId拿任务
     * @param taskId 任务Id
     * @return 对应任务对象
     * @author MISSA
     */
    @GetMapping("/task/getTaskByTaskId")
    public Task getTaskByTaskId(int taskId){
        return taskService.getTaskByTaskId(taskId);
    }

    /**
     * getSelectTask
     * @description 一次查询多种类型任务
     * @param types 任务类型的数组
     * @return 任务列表
     * @author MISSA
     */
    @GetMapping("/task/getSelectTask")
    public List<Task> getSelectTask(@RequestParam(value = "types[]") String types){
        List<Task> tasks = new ArrayList<Task>();
        int length = types.length();
        for (int i = 0; i < length; i++) {
            switch ((int)types.charAt(i)){
                case 48:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 49:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 50:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 51:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 52:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
            }
        }
        return tasks;
    }
    /**
     * getSelectTaskForPage
     * @description 分页一次查询多种任务
     * @param types 任务类型的数组
     * @param page 第几页
     * @param num 多少个
     * @return 任务列表
     * @author MISSA
     */
    @GetMapping("/task/getSelectTaskForPage")
    public List<Task> getSelectTask(@RequestParam(value = "types[]") String types,Integer page,Integer num){
        List<Task> tasks = new ArrayList<Task>();
        int length = types.length();
        int last;
        for (int i = 0; i < length; i++) {
            switch ((int)types.charAt(i)){
                case 48:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 49:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 50:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 51:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
                case 52:
                    tasks.addAll(taskService.getTask((int)types.charAt(i)-48));
                    break;
            }
        }
        page = (page -1)*num;
        Integer len = tasks.size();
        if (page > len){
            return null;
        }
        if (page+num>len){
            last = len;
        }else {
            last = page+num;
        }
        try{
            tasks = tasks.subList(page,last);
        }catch (java.lang.IndexOutOfBoundsException e){
            return null;
        }

        return tasks;
    }
    /**
     * getCommendPrice
     * @description 一获取推荐价格
     * @param task 添加的任务对象
     * @return 推荐价格
     * @author MISSA
     */
    @PostMapping("/task/getCommendPrice")//获取推荐价格
    public String getCommendPrice(  Task task) throws ParseException {
        File projectFile = new File(System.getProperty("user.dir"));
        System.out.println("current user.dir:" + projectFile);
        String fileDirPath = projectFile.toString() +"/pyFile/recommendAlg/"; //linux
        System.out.println("current fileDirPath:" + fileDirPath);
        String executer = "python3";
        // python绝对路径        String jsonStr = "{'taskType':"+task.getTaskType()+taskContent.substring(1, contentLen - 1) + "}";
        String file_path = fileDirPath + "amountEstimate.py";
        System.out.println("current python file path:" + file_path);
        // String[] command_line = new String[]{executer, file_path, "param1", "param2", ...};
        List<String> list = new LinkedList<>();
        list.add(executer);
        list.add(file_path);

        String taskContent = task.getTaskContent();
        int contentLen = taskContent.length();
        String jsonStr = "{'taskType':"+task.getTaskType()+","+taskContent.substring(1, contentLen - 1) + "}";
        String newJsonStr = jsonStr.replaceAll("\"", "'");
        System.out.println(newJsonStr);
        // !!!!!!!!!!!!!!!!!!!!!!
        list.add(newJsonStr);
        // !!!!!!!!!!!!!!!!!!!!!!

        String[] command_line = list.toArray(new String[0]);
        List<String> result = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec(command_line);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result.add(line);
            }
            in.close();
            // java代码中的 process.waitFor() 返回值（和我们通常意义上见到的0与1定义正好相反）
            // 返回值为0 - 表示调用python脚本成功；
            // 返回值为1 - 表示调用python脚本失败。
            int re = process.waitFor();
            System.out.println("调用 python 脚本是否成功(0为成功/1为不成功)：" + re);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
//        System.out.println("java"+result);
        //输出返回的内容
        Iterator<String> it = result.iterator();
//        System.out.println("result"+result.get(0));


//        while (it.hasNext()){
//            System.out.println(it.next());
//        }

        return it.next();


    }
    /**
     * getCommendTask
     * @description 一获取推荐任务
     * @return 返回任务列表
     * @author MISSA
     */
    @GetMapping("/task/getCommendTask")
    public List<Task> getCommendTask(String phoneNumber){
        File projectFile = new File(System.getProperty("user.dir"));
        String fileDirPath = projectFile.toString() +"/pyFile/recommendAlg/"; //linux

        String executer = "python3";
        // python绝对路径
        String file_path = fileDirPath+"priorityEstimate.py";
        // String[] command_line = new String[]{executer, file_path, "param1", "param2", ...};

        List<String> list = new LinkedList<>();
        list.add(executer);
        list.add(file_path);

        list.add(phoneNumber);

        String[] command_line = list.toArray(new String[0]);


        List<String> result = new LinkedList<>();
        try {

            Process process = Runtime.getRuntime().exec(command_line);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
//                result.add(line);
                result.add(new String(line.getBytes("GBK"),"utf8"));
            }

            in.close();
            // java代码中的 process.waitFor() 返回值（和我们通常意义上见到的0与1定义正好相反）
            // 返回值为0 - 表示调用python脚本成功；
            // 返回值为1 - 表示调用python脚本失败。

            int re = process.waitFor();
            System.out.println("调用 python 脚本是否成功(0为成功/1为不成功)：" + re);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }


        //输出返回的内容
        Iterator<String> it = result.iterator();
        List<Task> tasks = new ArrayList<Task>();
        while (it.hasNext()){
            Integer taskId = Integer.parseInt(it.next());
            tasks.add(taskService.getTaskByTaskId(taskId));
        }
        return tasks;
    }
    /**
     * getNotPickUpTaskByPhoneNum
     * @description 一个人发布的未被接取的任务
     * @param phoneNum 发布者手机号
     * @return 任务列表
     * @author MISSA
     */
    @GetMapping("/task/getNotPickUpTaskByPhoneNum")
    public List<Task> getNotPickUpTaskByPhoneNum(String phoneNum){
        return taskService.getTaskingByPhoneNum(phoneNum);
    }

    /**
     * giveUpTaskByTaskId
     * @description 放弃已经发布的任务
     * @param taskId 任务Id
     * @author MISSA
     */
    @PostMapping("/task/giveUpTaskByTaskId")
    public String giveUpTaskByTaskId(Integer taskId){
        taskService.GiveUpTaskByTaskId(taskId);
        return "success";
    }

    /**
     * judgePickUpTaskOrNot
     * @description 判断是否已经接取了任务
     * @param taskId 任务ID
     * @param phoneNum 用户手机号
     * @return 已经接取，true 没有接取，false
     * @author MISSA
     */
    @GetMapping("/task/judgePickUpTaskOrNot")
    public String judgePickUpTaskOrNot(Integer taskId,String phoneNum){
        Orders orders = ordersService.getOrderByPhoneNumAndTaskId(phoneNum,taskId);
        if(orders == null){
            return "false";
        } else {
            return "true";
        }
    }





}

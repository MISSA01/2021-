package pers.may.assist.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.may.assist.pojo.User;
import pers.may.assist.service.EntranceService;

import javax.annotation.Resource;

/**
 *
 * 登录注册
 * @author May
 * @description 登录注册模块
 *
 */
@Controller
@RequestMapping("/entrance")
public class EntranceController {

    @Resource
    EntranceService entranceService;

    /**
     * ifNewUser
     * @description 判断是否是新用户
     * @param code 微信code码
     * @return 是新用户返回true，不是返回false
     * @author May
     */
    @ResponseBody
    @PostMapping("/ifNewUser")
    public boolean ifNewUser(@NotNull String code){
        return entranceService.ifNewUser(code);
    }

    /**
     * login
     * @description 登录
     * @param code 登录用户的微信code码
     * @return 用户详细信息
     * @author May
     */
    @ResponseBody
    @PostMapping("/login")
    public User login(@NotNull String code){
        return entranceService.login(code);
    }

    /**
     * register
     * @description 注册新用户
     * @param phoneNumber 手机号
     * @param userName  用户名
     * @param studentId 学号
     * @param dormNum   宿舍号
     * @param gender    性别（1-男，0-女，2-未知）
     * @param avatar 头像图片的Base64字节码
     * @param code 微信code
     * @return 返回用户全部信息，如果手机号已经被注册，返回null
     * @author May
     */
    @ResponseBody
    @PostMapping("/register")
    public User register(@NotNull String phoneNumber,@NotNull String userName,@NotNull String studentId,Integer dormNum,Integer gender,@NotNull String avatar,@NotNull String code){

        User user = new User();
        user.setPhoneNum(phoneNumber);
        user.setUserName(userName);
        user.setStudentId(studentId);
        user.setDormNum(dormNum);
        if (gender==null||gender!=1||gender!=0||gender!=2){
            user.setGender(2);
        }else {
            user.setGender(gender);
        }
        user.setAvatar(avatar);

        return entranceService.register(user,code);
    }

}

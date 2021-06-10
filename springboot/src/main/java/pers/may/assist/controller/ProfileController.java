package pers.may.assist.controller;


import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.may.assist.pojo.Credit;
import pers.may.assist.pojo.User;
import pers.may.assist.service.ProfileService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 *
 * 个人中心
 * @author May
 * @description 个人信息模块
 *
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Resource
    ProfileService profileService;

    /**
     * getUserDetails
     * @description 查询用户详细信息
     * @param phoneNumber 用户手机号
     * @return 用户的具体信息
     * @author May
     */
    @ResponseBody
    @PostMapping("/getUserDetails")
    public User getUserDetailsByPhoneNum(@NotNull String phoneNumber){

        return profileService.getUserDetailsByPhoneNum(phoneNumber);
    }

    /**
     * getUserDetailsByCode
     * @description 查询用户详细信息,如果是登录，请使用Entrance模块中的login方法
     * @param code 微信code
     * @return 用户的具体信息
     * @author May
     */
    @ResponseBody
    @PostMapping("/getUserDetailsByCode")
    public User getUserDetailsByCode(@NotNull String code){
        return profileService.getUserDetailsByCode(code);
    }

    /**
     * deductScore
     * @description 扣除用户分数
     * @param phoneNumber 被扣除用户的手机号
     * @param creditContent 被扣除原因描述
     * @param creditScore 扣除的分值
     * @author May
     */
    @ResponseBody
    @PostMapping("/deductScore")
    public void deductScore(@NotNull String phoneNumber,@NotNull String creditContent,@NotNull Integer creditScore) {
        Credit credit = new Credit();
        credit.setPhoneNum(phoneNumber);
        credit.setCreditContent(creditContent);
        credit.setCreditScore(creditScore);
        credit.setCreditDate(new Date());
        profileService.deductScore(credit);
    }

    /**
     * getAllItemByPhoneNumber
     * @description 获取某个用户的所有不良信息记录，按时间降序
     * @param phoneNumber 用户手机号
     * @return 按时间降序排列的不良信息列表
     * @author May
     */
    @ResponseBody
    @PostMapping("/getAllItemByPhoneNumber")
    public List<Credit> getAllItemByPhoneNumber(@NotNull String phoneNumber) {

        return profileService.getAllItemByPhoneNumber(phoneNumber);
    }

    /**
     * getAllItemByPhoneNumber
     * @description 获取某个用户的所有不良信息记录,分页排序，按时间降序
     * @param phoneNumber 用户手机号
     * @param pageNum 页数，从第1页开始
     * @param pageSize 每页大小
     * @return 按时间降序排列的不良信息列表
     * @author May
     *
     */
    @ResponseBody
    @PostMapping("/getAllItemByPhoneNumberOfPage")
    public List<Credit> getAllItemByPhoneNumberOfPage(@NotNull String phoneNumber, @NotNull Integer pageNum, @NotNull Integer pageSize) {
        return profileService.getAllItemByPhoneNumberOfPage(phoneNumber,pageNum,pageSize);
    }



    /**
     * updAvatar
     * @description 修改头像
     * @param phoneNumber 用户手机号
     * @param imgCode 新的头像图形code
     * @author May
     */
    @ResponseBody
    @PostMapping("/updAvatar")
    public void updAvatar(@NotNull String phoneNumber, @NotNull String imgCode) {
        profileService.updAvatar(phoneNumber,imgCode);
    }


    /**
     * updStudentId
     * @description 修改学号
     * @param phoneNumber 用户手机号
     * @param studentId 新的学号
     * @author May
     */
    @ResponseBody
    @PostMapping("/updStudentId")
    public void updStudentId(@NotNull String phoneNumber, @NotNull String studentId) {
        profileService.updStudentId(phoneNumber,studentId);
    }



    /**
     * updDormNum
     * @description 修改宿舍楼号
     * @param phoneNumber 用户手机号
     * @param dormNum 新的宿舍楼号
     * @author May
     */
    @ResponseBody
    @PostMapping("/updDormNum")
    public void updDormNum(@NotNull String phoneNumber, @NotNull Integer dormNum) {
        profileService.updDormNum(phoneNumber,dormNum);
    }



    /**
     * updGender
     * @description 修改性别
     * @param phoneNumber 用户手机号
     * @param gender 新的性别（1-男性，0-女性，3-保密）
     * @author May
     */
    @ResponseBody
    @PostMapping("/updGender")
    public void updGender(@NotNull String phoneNumber, @NotNull Integer gender) {
        profileService.updGender(phoneNumber,gender);
    }

    /**
     * updUser
     * @description 修改用户信息
     * @param phoneNumber 手机号
     * @param userName  用户名
     * @param studentId 用户学号
     * @param dormNum   宿舍号
     * @param gender    性别（1-男，0-女）
     * @author May
     */
    @ResponseBody
    @PostMapping("/updUser")
    public void updUser(@NotNull String phoneNumber,@NotNull String userName,@NotNull String studentId,@NotNull Integer dormNum,@NotNull Integer gender){
        User user = new User();
        user.setPhoneNum(phoneNumber);
        user.setUserName(userName);
        user.setStudentId(studentId);
        user.setDormNum(dormNum);
        user.setGender(gender);
        profileService.updUser(user);
    }

    /**
     * getAvatarByPhoneNumber
     * @description 根据用户手机号获取用户头像URL
     * @param phoneNumber 用户手机号
     * @return 头像路径
     * @author May
     */
    @ResponseBody
    @PostMapping("/getAvatarByPhoneNumber")
    public String getAvatarByPhoneNumber(@NotNull String phoneNumber){
        return profileService.getAvatarByPhoneNumber(phoneNumber);
    }

    /**
     * getNameByPhoneNumber
     * @description 根据用户手机号获取用户昵称
     * @param phoneNumber 用户手机号
     * @return 头像路径
     * @author May
     */
    @ResponseBody
    @PostMapping("/getNameByPhoneNumber")
    public String getNameByPhoneNumber(@NotNull String phoneNumber){
        return profileService.getNameByPhoneNumber(phoneNumber);
    }

    /**
     * getScoreByPhoneNumber
     * @description 根据用户手机号获取用户信誉分
     * @param phoneNumber 用户手机号
     * @return 信誉分
     * @author May
     */
    @ResponseBody
    @PostMapping("/getScoreByPhoneNumber")
    public Integer getScoreByPhoneNumber(@NotNull String phoneNumber){
        return profileService.getScoreByPhoneNumber(phoneNumber);
    }


    /**
     * addSingleAdvice
     * @description 插入一条新的反馈
     * @param phoneNum 用户手机号
     * @param content 反馈内容
     */
    @ResponseBody
    @PostMapping("/addSingleAdvice")
    public void addSingleAdvice(@NotNull String phoneNum,@NotNull String content){
        profileService.addSingleAdvice(phoneNum,content);
    }


    /**
     * updRewardCode
     * @description 修改收款码,返回修改后的URL
     * @param phoneNumber 用户手机号
     * @param imgCode 新的收款码图形code
     * @author May
     */
    @ResponseBody
    @PostMapping("/updRewardCode")
    public String updRewardCode(@NotNull String phoneNumber, @NotNull String imgCode){
        return profileService.updRewardCode(phoneNumber,imgCode);
    }

    /**
     * getRewardCodeByPhoneNumber
     * @description 根据用户手机号获取用户收款码图片URL,如果没有收款码，返回NULL
     * @param phoneNumber 用户手机号
     * @return 收款码图片URL
     * @author May
     */
    @ResponseBody
    @PostMapping("/getRewardCodeByPhoneNumber")
    public String getRewardCodeByPhoneNumber(String phoneNumber){
        return profileService.getRewardCodeByPhoneNumber(phoneNumber);
    }
}

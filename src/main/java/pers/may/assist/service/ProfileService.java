package pers.may.assist.service;

import com.sun.istack.internal.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.may.assist.pojo.Advice;
import pers.may.assist.pojo.Credit;
import pers.may.assist.pojo.User;

import java.util.List;

public interface ProfileService {

    /**
     *
     * 查询用户详细信息
     * @param phoneNumber 用户手机号
     * @return 用户的具体信息
     * @author May
     */
    public User getUserDetailsByPhoneNum(String phoneNumber);

    /**
     *
     * 查询用户详细信息
     * @param code 微信code
     * @return 用户的具体信息
     * @author May
     */
    public User getUserDetailsByCode(String code);

    /**
     *
     * 扣除用户分数
     * @param credit 不良记录信息
     * @author May
     */
    public void deductScore(Credit credit);

    /**
     *获取某个用户的所有不良信息记录，按时间降序
     * @param phoneNumber 用户手机号
     * @return 按时间降序排列的不良信息列表
     * @author May
     */
    public List<Credit> getAllItemByPhoneNumber(String phoneNumber);

    /**
     * 获取某个用户的所有不良信息记录,分页排序，按时间降序
     * @param phoneNumber 用户手机号
     * @param pageNum 页数，从第1页开始
     * @param pageSize 每页大小
     * @return 按时间降序排列的不良信息列表
     * @author May
     *
     */
    public List<Credit> getAllItemByPhoneNumberOfPage(String phoneNumber,Integer pageNum,Integer pageSize);

    /**
     * 修改图像
     * @param phoneNumber 用户手机号
     * @param imgCode 新的头像图形code
     * @author May
     */
    public void updAvatar(String phoneNumber,String imgCode);

    /**
     * updAvatar
     * @description 修改学号
     * @param phoneNumber 用户手机号
     * @param studentId 新的学号
     * @author May
     */

    public void updStudentId(@NotNull String phoneNumber, @NotNull String studentId);



    /**
     * updAvatar
     * @description 修改宿舍楼号
     * @param phoneNumber 用户手机号
     * @param dormNum 新的宿舍楼号
     * @author May
     */
    public void updDormNum(@NotNull String phoneNumber, @NotNull Integer dormNum);


    /**
     * updAvatar
     * @description 修改性别
     * @param phoneNumber 用户手机号
     * @param gender 新的性别（1-男性，0-女性，3-保密）
     * @author May
     */

    public void updGender(@NotNull String phoneNumber, @NotNull Integer gender);


    /**
     * updRewardCode
     * @description 修改收款码
     * @param phoneNumber 用户手机号
     * @param imgCode 新的收款码图形code
     * @author May
     */
    public String updRewardCode(@NotNull String phoneNumber, @NotNull String imgCode);

    /**
     * 修改用户信息
     * @param user 用户对象
     * @author May
     */
    public void updUser(User user);

    /**
     * 根据用户手机号获取用户头像URL
     * @param phoneNumber 用户手机号
     * @return 头像路径
     * @author May
     */
    public String getAvatarByPhoneNumber(String phoneNumber);

    /**
     * 根据用户手机号获取用户昵称
     * @param phoneNumber 用户手机号
     * @return 头像路径
     * @author May
     */
    public String getNameByPhoneNumber(String phoneNumber);


    /**
     * 根据用户手机号获取用户收款码图片URL
     * @param phoneNumber 用户手机号
     * @return 收款码图片URL
     * @author May
     */
    public String getRewardCodeByPhoneNumber(String phoneNumber);

    /**
     * getScoreByPhoneNumber
     * @description 根据用户手机号获取用户信誉分
     * @param phoneNumber 用户手机号
     * @return 信誉分
     * @author May
     */
    public Integer getScoreByPhoneNumber(String phoneNumber);


    /**
     * addSingleAdvice
     * @description 插入一条新的反馈
     * @param phoneNum 用户手机号
     * @param content 反馈内容
     */
    public void addSingleAdvice(String phoneNum,String content);


//    /**
//     * selAllAdvice
//     * @description 查询所有意见
//     * @return
//     */
//    public Advice selAllAdvice();

}

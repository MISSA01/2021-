package pers.may.assist.mapper;

import com.sun.istack.internal.NotNull;
import pers.may.assist.pojo.User;

import java.util.Date;
import java.util.List;

/**
 * 用户数据库连接层
 */
public interface UserMapper {

    /**
     * 查询user表，返回所有用户列表
     * @return 所有用户列表
     *
     */
    public List<User> selAllUser();

    /**
     *
     * 通过用户手机号主键，返回唯一的用户对象
     * @param phoneNum
     * @return 用户对象，没有时返回Null
     */
    public User selSingleUserByPhoneNum(String phoneNum);

    /**
     *
     * 通过用户openId，返回唯一的用户对象
     * @param openId
     * @return 用户对象，没有时返回Null
     */
    public User selSingleUserByOpenId(String openId);

    /**
     * 插入新用户
     * @param user 准备插入的新用户对象
     */
    public void insertNewUserByUser(User user);

    /**
     *
     * 插入新用户的openid
     * @param openId 微信的openid
     * @param phoneNum 用户手机号
     */
    public void insertNewUserOpenId(String openId,String phoneNum);


    /**
     * 修改用户信息
     * @param user 准备修改的用户对象
     */
    public void updUserByUser(User user);

    /**
     * 查询是否是新用户
     * @param openId
     * @return
     */
    public String selOpenId(String openId);

    /**
     * 插入登录日志
     * @param phoneNum
     * @param loginDate
     */
    public void insertNewLoginLog(String phoneNum, Date loginDate);

    /**
     * 通过手机号获取用户openid
     * @param phoneNum
     * @return
     */
    public String selOpenIdByPhoneNum(String phoneNum);


    /**
     * 通过手机号获取用户收款码
     * @param phoneNum
     * @return
     */
    public String selRewardCodeByPhoneNum(String phoneNum);

    /**
     * @param phoneNum 用户手机号
     * @param rewardCode 新的收款码URL
     * @author May
     */
    public void updRewardCodeByPhoneNum(String phoneNum,String rewardCode);


}

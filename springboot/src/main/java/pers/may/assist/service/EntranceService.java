package pers.may.assist.service;

import pers.may.assist.pojo.User;

public interface EntranceService {

    /**
     * ifNewUser
     * @description 判断是否是新用户
     * @param code 微信code码
     * @return 是新用户返回true，不是返回false
     * @author May
     */
    public boolean ifNewUser(String code);

    /**
     * register
     * @description 注册新用户
     * @param user 用户对象
     * @param code 微信code码
     * @author May
     */
    public User register(User user,String code);


    /**
     * login
     * @description 登录
     * @param code 微信code码
     * @author May
     * @return 返回登录用户信息
     */
    public User login(String code);

}

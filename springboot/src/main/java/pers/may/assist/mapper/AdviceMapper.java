package pers.may.assist.mapper;

import pers.may.assist.pojo.Advice;
import pers.may.assist.pojo.User;

import java.util.Date;
import java.util.List;

/**
 * 用户反馈数据库连接层
 */
public interface AdviceMapper {


    /**
     * 查询所有的建议
     * @return 包含所有反馈的列表
     */
    public Advice selAllAdvice();

    /**
     * 插入一条新的建议
     * @param phoneNum 手机号
     * @param content 反馈内容
     * @param adviceDate 反馈时间
     *
     */
    public void insertNewAdvice(String phoneNum, String content, Date adviceDate);








}

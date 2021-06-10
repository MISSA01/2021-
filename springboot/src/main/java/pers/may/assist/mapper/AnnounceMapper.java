package pers.may.assist.mapper;

import pers.may.assist.pojo.Advice;
import pers.may.assist.pojo.Announce;

import java.util.Date;
import java.util.List;

/**
 * 公告数据库连接层
 */
public interface AnnounceMapper {


    /**
     * 获取最近的几条公告
     * @param announceNum 要取的公告的数量(按照时间降序排序)
     * @return 返回公告列表
     */
    public List<Announce> selLatelyAnnounce(int announceNum);

}

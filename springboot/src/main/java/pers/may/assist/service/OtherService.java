package pers.may.assist.service;

import pers.may.assist.pojo.Announce;
import pers.may.assist.pojo.Chat;
import pers.may.assist.utils.Base64toImage;

import java.util.List;

public interface OtherService {

    /**
     * 获取最近的几条公告
     * @param announceNum 要取的公告的数量(按照时间降序排序)
     * @return 返回公告列表
     */
    public List<Announce> getLatelyAnnounce(int announceNum);


    /**
     * 判断内容是否包含敏感词汇
     * @param content 待测试文本内容
     * @return 如果包含敏感词汇返回true，否则返回false
     */
    public boolean ifHasSensitiveWord(String content);

    /**
     * 发送通知
     * @param orderId
     * @param noticeType 1-任务确认通知，2-任务完成通知，3-任务验收通知
     */
    public boolean weiXinNotify(Integer orderId,Integer noticeType);

    /**
     * 获取新手指引图片的URL列表
     * @return
     */
    public List<String> getManualPhotos();

    /**
     * 获取新手指引图片的URL列表(备用)
     * @return
     */
    List<String> getManualSparePhotos();
}

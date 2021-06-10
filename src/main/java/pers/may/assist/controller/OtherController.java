package pers.may.assist.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.may.assist.pojo.Announce;
import pers.may.assist.pojo.Chat;
import pers.may.assist.service.ChatService;
import pers.may.assist.service.OtherService;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 * 其他杂项模块
 * @author May
 *
 *
 */
@Controller
@RequestMapping("/other")
public class OtherController {

    @Resource
    OtherService otherService;


    /**
     * getLatelyAnnounce
     * @description 获取最近的几条公告
     * @author May
     * @return 返回公告列表
     */
    @ResponseBody
    @PostMapping("/getLatelyAnnounce")
    public List<Announce> getLatelyAnnounce(){
        return otherService.getLatelyAnnounce(5);
    }


    /**
     * ifHasSensitiveWord
     *  @description 判断内容是否包含敏感词汇,包含返回true，否则返回false
     * @param content 待测试文本内容
     * @return 如果包含敏感词汇返回true，否则返回false
     * @author May
     */
    @ResponseBody
    @PostMapping("/ifHasSensitiveWord")
    public boolean ifHasSensitiveWord(String content){
        return otherService.ifHasSensitiveWord(content);
    }


    /**
     * weiXinNotify
     * @description 发送微信订阅消息
     * @param orderId 订单id
     * @param noticeType 1-任务确认通知，2-任务完成通知，3-任务代付款通知
     * @return
     */
    @ResponseBody
    @PostMapping("/weiXinNotify")
    public boolean weiXinNotify(@NotNull Integer orderId,@NotNull Integer noticeType){
        return otherService.weiXinNotify(orderId,noticeType);
    }


    /**
     * getManualPhotos
     * @description 获取新手指引图片的URL列表
     * @return
     */
    @ResponseBody
    @PostMapping("/getManualPhotos")
    public List<String> getManualPhotos(){
        return otherService.getManualPhotos();
    }

    /**
     * getManualSparePhotos
     * @description 获取新手指引图片的URL列表
     * @return
     */
    @ResponseBody
    @PostMapping("/getManualSparePhotos")
    public List<String> getManualSparePhotos(){
        return otherService.getManualSparePhotos();
    }

    /**
     * getRealFace
     * @description 判断是否是真的界面,真的返回true
     * @return
     */
    @ResponseBody
    @PostMapping("/getRealFace")
    public boolean getRealFace(){
        return false;
    }


    /**
     * getRealSpareFace
     * @description 判断是否是真的界面,真的返回true(备用)
     * @return
     */
    @ResponseBody
    @PostMapping("/getRealSpareFace")
    public boolean getRealSpareFace(){
        return true;
    }



}

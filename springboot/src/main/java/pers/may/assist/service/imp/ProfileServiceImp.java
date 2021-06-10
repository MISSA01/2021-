package pers.may.assist.service.imp;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.may.assist.mapper.AdviceMapper;
import pers.may.assist.mapper.CreditMapper;
import pers.may.assist.mapper.UserMapper;
import pers.may.assist.pojo.Constant;
import pers.may.assist.pojo.Credit;
import pers.may.assist.pojo.User;
import pers.may.assist.service.ProfileService;
import pers.may.assist.utils.Base64toImage;
import pers.may.assist.utils.MyTools;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 *
 * 个人中心服务接口
 * @author May
 *
 */
@Service("ProfileService")
public class ProfileServiceImp implements ProfileService {
    @Resource
    UserMapper userMapper;

    @Resource
    CreditMapper creditMapper;

    @Resource
    Constant constant;

    @Resource
    AdviceMapper adviceMapper;


    @Override
    public User getUserDetailsByPhoneNum(String phoneNumber) {
        return userMapper.selSingleUserByPhoneNum(phoneNumber);
    }

    @Override
    public User getUserDetailsByCode(String code) {

        JSONObject param = new JSONObject();
        param.put("appid",constant.getAppId());
        param.put("secret",constant.getAppSecret());
        param.put("grant_type","authorization_code");
        param.put("js_code",code);
        String openId = "";

        String resultStr = MyTools.send(constant.getOpenIdUrl(),param,"utf-8",1);
        JSONObject resultJson  = JSONObject.parseObject(resultStr);
        openId = resultJson.getString("openid");



        if (openId != null||openId != "")
            return userMapper.selSingleUserByOpenId(openId);
        else
            return null;
    }

    @Override
    public void deductScore(Credit credit) {
        //更改用户信息中的荣誉分数
        //获取用户信息
        User user = userMapper.selSingleUserByPhoneNum(credit.getPhoneNum());
        //用户原分数-扣除分数=新分数
        int newScore = user.getUserScore()-credit.getCreditScore();
        //如果用户分数扣过了0分，则最低是0分
        if (newScore>=0){
            user.setUserScore(user.getUserScore()-credit.getCreditScore());
        }else {
            user.setUserScore(0);
        }
        userMapper.updUserByUser(user);
        //插入不良信息记录
        creditMapper.insertItem(credit);
    }

    @Override
    public List<Credit> getAllItemByPhoneNumber(String phoneNumber) {

        return creditMapper.selAllItemByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Credit> getAllItemByPhoneNumberOfPage(String phoneNumber, Integer pageNum, Integer pageSize) {
        return creditMapper.selAllItemByPhoneNumberOfPage(phoneNumber,pageNum,pageSize);
    }

    @Override
    public void updAvatar(String phoneNumber, String imgCode) {
        String filename = phoneNumber+".jpg";
        //保存图片文件
        Base64toImage base64toImage = new Base64toImage();
        File fileDir = base64toImage.getImgDirFile("avatar");
        //base64toImage.generateImage(base64toImage.fixBase64Code(imgCode), fileDir.getAbsolutePath() + "\\" + filename); //windows
        base64toImage.generateImage(base64toImage.fixBase64Code(imgCode), fileDir.getAbsolutePath() + "/" + filename); //linux
    }

    @Override
    public void updStudentId(String phoneNumber, String studentId) {
        User user = userMapper.selSingleUserByPhoneNum(phoneNumber);
        user.setStudentId(studentId);
        userMapper.updUserByUser(user);
    }

    @Override
    public void updDormNum(String phoneNumber, Integer dormNum) {
        User user = userMapper.selSingleUserByPhoneNum(phoneNumber);
        user.setDormNum(dormNum);
        userMapper.updUserByUser(user);
    }

    @Override
    public void updGender(String phoneNumber, Integer gender) {
        User user = userMapper.selSingleUserByPhoneNum(phoneNumber);
        user.setGender(gender);
        userMapper.updUserByUser(user);
    }

    @Override
    public String updRewardCode(String phoneNumber, String imgCode) {

        imgCode = imgCode.substring(0,imgCode.length()-11);
        String filename = phoneNumber+"_reward_code.jpg";
        //保存图片文件
        Base64toImage base64toImage = new Base64toImage();
        File fileDir = base64toImage.getImgDirFile("rewardCode");
        //base64toImage.generateImage(base64toImage.fixBase64Code(imgCode), fileDir.getAbsolutePath() + "\\" + filename); //windows
        base64toImage.generateImage(base64toImage.fixBase64Code(imgCode), fileDir.getAbsolutePath() + "/" + filename); //linux

        if (userMapper.selRewardCodeByPhoneNum(phoneNumber) == null){
            userMapper.updRewardCodeByPhoneNum(phoneNumber,"/assist/img/rewardCode/"+filename);
        }

        return "/assist/img/rewardCode/"+filename;

    }

    @Override
    public void updUser(User user) {
        userMapper.updUserByUser(user);
    }

    @Override
    public String getAvatarByPhoneNumber(String phoneNumber) {
        User user = userMapper.selSingleUserByPhoneNum(phoneNumber);
        return user.getAvatar();
    }

    @Override
    public String getNameByPhoneNumber(String phoneNumber) {
        User user = userMapper.selSingleUserByPhoneNum(phoneNumber);
        return user.getUserName();
    }

    @Override
    public String getRewardCodeByPhoneNumber(String phoneNumber) {
        return userMapper.selRewardCodeByPhoneNum(phoneNumber);
    }

    @Override
    public Integer getScoreByPhoneNumber(String phoneNumber) {
        User user = userMapper.selSingleUserByPhoneNum(phoneNumber);
        return user.getUserScore();
    }

    @Override
    public void addSingleAdvice(String phoneNum, String content) {
        Date date = new Date();
        adviceMapper.insertNewAdvice(phoneNum,content,date);
    }


}

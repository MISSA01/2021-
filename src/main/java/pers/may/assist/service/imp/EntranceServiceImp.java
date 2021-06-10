package pers.may.assist.service.imp;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.may.assist.mapper.UserMapper;
import pers.may.assist.pojo.Constant;
import pers.may.assist.pojo.User;
import pers.may.assist.service.EntranceService;
import pers.may.assist.utils.Base64toImage;
import pers.may.assist.utils.MyTools;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * 登录注册服务接口
 * @author May
 *
 */
@Service("EntranceService")
public class EntranceServiceImp implements EntranceService {

    @Resource
    UserMapper userMapper;

    @Resource
    Constant constant;

    @Override
    public boolean ifNewUser(String code) {

        JSONObject param = new JSONObject();
        param.put("appid",constant.getAppId());
        param.put("secret",constant.getAppSecret());
        param.put("grant_type","authorization_code");
        param.put("js_code",code);
        String result = "";

        String resultStr = MyTools.send(constant.getOpenIdUrl(),param,"utf-8",1);
        JSONObject resultJson  = JSONObject.parseObject(resultStr);
        String openId = resultJson.getString("openid");
        result = userMapper.selOpenId(openId);


        if (result == null||result == "")
            return true;
        else
            return false;
    }

    @Override
    public User register(User user,String code) {


        if (userMapper.selSingleUserByPhoneNum(user.getPhoneNum())!=null){
            return null;
        }

        //图片类型
        //取出图片字节码，并进行处理存储
        String originCode = user.getAvatar();

        String filename = user.getPhoneNum()+".jpg";
        //转存图片地址
        user.setAvatar("/assist/img/avatar/"+filename);
        //存入数据库
        userMapper.insertNewUserByUser(user);


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
            userMapper.insertNewUserOpenId(openId,user.getPhoneNum());
        else
            return null;


        //保存图片文件
        Base64toImage base64toImage = new Base64toImage();
        File fileDir = base64toImage.getImgDirFile("avatar");

        //base64toImage.generateImage(base64toImage.fixBase64Code(originCode), fileDir.getAbsolutePath() + "\\" + filename); //windows
        base64toImage.generateImage(base64toImage.fixBase64Code(originCode), fileDir.getAbsolutePath() + "/" + filename); //linux

        userMapper.insertNewLoginLog(user.getPhoneNum(),new Date());
        return user;

    }

    @Override
    public User login(String code) {
        JSONObject param = new JSONObject();
        param.put("appid",constant.getAppId());
        param.put("secret",constant.getAppSecret());
        param.put("grant_type","authorization_code");
        param.put("js_code",code);
        String openId = "";

        String resultStr = MyTools.send(constant.getOpenIdUrl(),param,"utf-8",1);
        JSONObject resultJson  = JSONObject.parseObject(resultStr);
        openId = resultJson.getString("openid");

        if (openId != null||openId != "") {
            User user = userMapper.selSingleUserByOpenId(openId);
            userMapper.insertNewLoginLog(user.getPhoneNum(),new Date());
            return user;
        }else
            return null;
    }

}

package pers.may.assist.service.imp;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.may.assist.mapper.AnnounceMapper;
import pers.may.assist.mapper.UserMapper;
import pers.may.assist.pojo.*;
import pers.may.assist.service.OrdersService;
import pers.may.assist.service.OtherService;
import pers.may.assist.utils.MyTools;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 其他杂项服务接口
 * @author May
 *
 */
@Service("OtherService")
public class OtherServiceImp implements OtherService {

    @Resource
    AnnounceMapper announceMapper;

    @Resource
    Constant constant;

    @Resource
    OrdersService ordersService;

    @Resource
    UserMapper userMapper;

    @Override
    public List<Announce> getLatelyAnnounce(int announceNum) {
        return announceMapper.selLatelyAnnounce(announceNum);
    }

    @Override
    public boolean ifHasSensitiveWord(String content) {

        HttpPost httpPost = new HttpPost(constant.getMsgCheckUrl()+"?access_token="+constant.getAccessToken());
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("content", content);
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.create("application/json", "utf-8")));

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            String resultStr = null;
            resultStr = EntityUtils.toString(httpEntity, "utf-8");

            JSONObject resultJson  = JSONObject.parseObject(resultStr);
            switch (resultJson.getString("errcode")){
                case "0":
                    return false;
                case "87014":
                    return true;
                case "40001":
                    new MyTools().resetToken(constant);
                    return ifHasSensitiveWord(content);

                default:
                    return false;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean weiXinNotify(Integer orderId, Integer noticeType) {
        System.out.println(orderId+"========="+noticeType);

        Orders orders = ordersService.getOrderByOrderId(orderId);

        WxMssVo wxMssVo = new WxMssVo();
        Map<String, TemplateData> paramMap = new HashMap<String, TemplateData>();
        wxMssVo.setPage("pages/order/order");
        wxMssVo.setTouser(userMapper.selOpenIdByPhoneNum(orders.getTask().getPhoneNum()));
        wxMssVo.setMiniprogram_state("formal");
        wxMssVo.setData(paramMap);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity;

        switch (noticeType){
            //1-任务确认通知
            case 1:
                wxMssVo.setTemplate_id(constant.getTemplateId1());
                paramMap.put("thing1",new TemplateData(orders.getTask().getTaskTitle()));
                paramMap.put("time3",new TemplateData(formatter.format(orders.getReceiveTime())));
                paramMap.put("thing4",new TemplateData(JSONObject.parseObject(orders.getTask().getTaskContent()).getString("taskDestination")));
                paramMap.put("thing2",new TemplateData("您的任务已被接取！"));
                responseEntity = restTemplate.postForEntity(constant.getNoticeUrl()+"?access_token="+constant.getAccessToken(), wxMssVo, String.class);
                break;
            //2-任务完成通知
            case 2:
                wxMssVo.setTemplate_id(constant.getTemplateId2());
                paramMap.put("thing1",new TemplateData(orders.getTask().getTaskTitle()));
                paramMap.put("time5",new TemplateData(formatter.format(orders.getFinishTime())));
                paramMap.put("thing6",new TemplateData("您的任务已被完成，点击查看详情！"));
                responseEntity = restTemplate.postForEntity(constant.getNoticeUrl()+"?access_token="+constant.getAccessToken(), wxMssVo, String.class);
                break;
            //3-任务付款通知
            case 3:
                wxMssVo.setTemplate_id(constant.getTemplateId3());
                paramMap.put("thing6",new TemplateData(orders.getTask().getTaskTitle()));
                paramMap.put("date3",new TemplateData(formatter.format(orders.getReceiveTime())));
                paramMap.put("amount2",new TemplateData(String.valueOf(orders.getTask().getTaskReward())+"元"));
                paramMap.put("thing4",new TemplateData("请前往订单聊天页面扫码付款！"));
                responseEntity = restTemplate.postForEntity(constant.getNoticeUrl()+"?access_token="+constant.getAccessToken(), wxMssVo, String.class);
                break;
            default:
                return false;
        }

        JSONObject reJson  = JSONObject.parseObject(responseEntity.getBody());
        System.out.println(responseEntity.getBody());
        switch (reJson.getString("errcode")){
            case "43101":
                System.out.println("用户拒绝接收消息");
                return false;
            case "42001":
                new MyTools().resetToken(constant);
                return weiXinNotify(orderId,noticeType);

            case "0":
                return true;
            default:
                return false;
        }

    }

    @Override
    public List<String> getManualPhotos() {
        List<String> list = new ArrayList();
//        list.add("/assist/img/manual/greenhand_1.jpg");
//        list.add("/assist/img/manual/greenhand_2.jpg");
//        list.add("/assist/img/manual/greenhand_3.jpg");
//        list.add("/assist/img/manual/greenhand_4.jpg");
//        list.add("/assist/img/manual/greenhand_5.jpg");
//        list.add("/assist/img/manual/greenhand_6.jpg");
//        list.add("/assist/img/manual/greenhand_7.jpg");
//        list.add("/assist/img/manual/greenhand_8.jpg");
//        list.add("/assist/img/manual/greenhand_9.jpg");
//        list.add("/assist/img/manual/greenhand_10.jpg");
//        list.add("/assist/img/manual/greenhand_11.jpg");
//        list.add("/assist/img/manual/greenhand_12.jpg");
//        list.add("/assist/img/manual/greenhand_13.jpg");

        list.add("/assist/img/manualSpare/greenhand_1.jpg");
        list.add("/assist/img/manualSpare/greenhand_2.jpg");
        list.add("/assist/img/manualSpare/greenhand_3.jpg");
        list.add("/assist/img/manualSpare/greenhand_4.jpg");
        return list;
    }


    @Override
    public List<String> getManualSparePhotos() {
        List<String> list = new ArrayList();

//        list.add("/assist/img/manualSpare/greenhand_1.jpg");
//        list.add("/assist/img/manualSpare/greenhand_2.jpg");
//        list.add("/assist/img/manualSpare/greenhand_3.jpg");
//        list.add("/assist/img/manualSpare/greenhand_4.jpg");
        list.add("/assist/img/manual/greenhand_1.jpg");
        list.add("/assist/img/manual/greenhand_2.jpg");
        list.add("/assist/img/manual/greenhand_3.jpg");
        list.add("/assist/img/manual/greenhand_4.jpg");
        list.add("/assist/img/manual/greenhand_5.jpg");
        list.add("/assist/img/manual/greenhand_6.jpg");
        list.add("/assist/img/manual/greenhand_7.jpg");
        list.add("/assist/img/manual/greenhand_8.jpg");
        list.add("/assist/img/manual/greenhand_9.jpg");
        list.add("/assist/img/manual/greenhand_10.jpg");
        list.add("/assist/img/manual/greenhand_11.jpg");
        list.add("/assist/img/manual/greenhand_12.jpg");
        list.add("/assist/img/manual/greenhand_13.jpg");
        return list;
    }


}

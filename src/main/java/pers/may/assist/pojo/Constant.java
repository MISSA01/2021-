package pers.may.assist.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//注册到Spring容器
@Component
//指定常量资源路径
@PropertySource("classpath:constant.properties")
public class Constant {
    //创建相应属性
    //直接使用注解获取常量值

    @Value("${constant.appId}")
    private String appId;

    @Value("${constant.appSecret}")
    private String appSecret;

    @Value("${constant.openIdUrl}")
    private String openIdUrl;

    @Value("${constant.tokenUrl}")
    private String tokenUrl;

    @Value("${constant.msgCheckUrl}")
    private String msgCheckUrl;

    @Value("${constant.noticeUrl}")
    private String noticeUrl;

    @Value("${constant.templateId1}")
    private String templateId1;

    @Value("${constant.templateId2}")
    private String templateId2;

    @Value("${constant.templateId3}")
    private String templateId3;

    private String accessToken;

    public String getOpenIdUrl() {
        return openIdUrl;
    }

    public void setOpenIdUrl(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getMsgCheckUrl() {
        return msgCheckUrl;
    }

    public void setMsgCheckUrl(String msgCheckUrl) {
        this.msgCheckUrl = msgCheckUrl;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getTemplateId1() {
        return templateId1;
    }

    public void setTemplateId1(String templateId1) {
        this.templateId1 = templateId1;
    }

    public String getTemplateId2() {
        return templateId2;
    }

    public void setTemplateId2(String templateId2) {
        this.templateId2 = templateId2;
    }

    public String getTemplateId3() {
        return templateId3;
    }

    public void setTemplateId3(String templateId3) {
        this.templateId3 = templateId3;
    }

    @Override
    public String toString() {
        return "Constant{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", openIdUrl='" + openIdUrl + '\'' +
                ", tokenUrl='" + tokenUrl + '\'' +
                ", msgCheckUrl='" + msgCheckUrl + '\'' +
                ", noticeUrl='" + noticeUrl + '\'' +
                ", templateId1='" + templateId1 + '\'' +
                ", templateId2='" + templateId2 + '\'' +
                ", templateId3='" + templateId3 + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}

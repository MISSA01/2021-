package pers.may.assist.pojo;

import java.util.Date;

public class Advice {

    private Integer adviceId;
    private String phoneNum;
    private String content;
    private Date adviceDate;

    public Integer getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Integer adviceId) {
        this.adviceId = adviceId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String toString() {
        return "Advice{" +
                "adviceId=" + adviceId +
                ", phoneNum='" + phoneNum + '\'' +
                ", content='" + content + '\'' +
                ", adviceDate=" + adviceDate +
                '}';
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAdviceDate() {
        return adviceDate;
    }

    public void setAdviceDate(Date adviceDate) {
        this.adviceDate = adviceDate;
    }

}

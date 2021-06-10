package pers.may.assist.pojo;

import com.sun.istack.internal.NotNull;

import java.util.Date;

public class Credit {
    private Integer creditId;//扣分记录ID
    @NotNull
    private String phoneNum;//扣分用户的手机号
    @NotNull
    private String creditContent;//扣分行为描述
    @NotNull
    private Date creditDate;//扣分时间
    @NotNull
    private Integer creditScore;//扣分分值

    public Integer getCreditId() {
        return creditId;
    }

    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCreditContent() {
        return creditContent;
    }

    public void setCreditContent(String creditContent) {
        this.creditContent = creditContent;
    }

    public Date getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(Date creditDate) {
        this.creditDate = creditDate;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "Credit=" + creditId +
                ", phoneNum='" + phoneNum + '\'' +
                ", creditContent='" + creditContent + '\'' +
                ", creditDate=" + creditDate +
                ", creditScore=" + creditScore +
                '}';
    }
}

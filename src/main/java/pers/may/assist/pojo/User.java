package pers.may.assist.pojo;

import com.sun.istack.internal.NotNull;

public class User {

    @NotNull
    private String phoneNum; //电话号码
    @NotNull
    private String userName; //用户名
    @NotNull
    private String studentId;//学号
    private Integer dormNum;//宿舍号
    private Integer gender;//性别（1-男，0-女）
    private Integer userScore;//信誉分（满分10）
    private String avatar;//头像URL
//    private String openId;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getDormNum() {
        return dormNum;
    }

    public void setDormNum(Integer dormNum) {
        this.dormNum = dormNum;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

//    public String getOpenId() {
//        return openId;
//    }
//
//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }

    @Override
    public String toString() {
        return "User{" +
                "phoneNum='" + phoneNum + '\'' +
                ", userName='" + userName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", dormNum=" + dormNum +
                ", gender=" + gender +
                ", userScore=" + userScore +
                ", avatar='" + avatar + '\'' +
//                ", openId='" + openId + '\'' +
                '}';
    }
}

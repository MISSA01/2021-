package pers.may.assist.pojo;

import com.sun.istack.internal.NotNull;

public class Manager {
    @NotNull
    private String managerId;//管理员ID
    @NotNull
    private String managerPassword;//管理员密码

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId='" + managerId + '\'' +
                ", managerPassword='" + managerPassword + '\'' +
                '}';
    }
}

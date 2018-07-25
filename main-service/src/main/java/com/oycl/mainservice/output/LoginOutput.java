package com.oycl.mainservice.output;

import com.oycl.mainservice.model.UserInfo;

public class LoginOutput {
    private UserInfo userInfo;
    private String reslutCode;
    private String reslutMessage;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getReslutCode() {
        return reslutCode;
    }

    public void setReslutCode(String reslutCode) {
        this.reslutCode = reslutCode;
    }

    public String getReslutMessage() {
        return reslutMessage;
    }

    public void setReslutMessage(String reslutMessage) {
        this.reslutMessage = reslutMessage;
    }
}

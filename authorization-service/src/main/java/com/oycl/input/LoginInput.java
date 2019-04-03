package com.oycl.input;

import com.oycl.common.base.BaseInput;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginInput extends BaseInput {

    /**
     * ip地址
     */
    @NotNull
    @NotEmpty
    private String loginId;

    /**
     * 负责人
     */
    @NotNull
    @NotEmpty
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

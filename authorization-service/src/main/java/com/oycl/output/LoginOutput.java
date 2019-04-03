package com.oycl.output;


import com.oycl.common.base.BaseOutput;

public class LoginOutput extends BaseOutput {
    /**
     * 凭证
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

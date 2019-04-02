package com.oycl.output;


import com.oycl.common.base.BaseOutput;

public class RegisterOutput extends BaseOutput {
    /**
     * 密码
     */
    private String apiSecret;

    /**
     * 唯一识别码
     */
    private String apiId;

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }
}

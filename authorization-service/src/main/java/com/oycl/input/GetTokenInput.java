package com.oycl.input;

import com.oycl.common.base.BaseInput;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GetTokenInput extends BaseInput {

    /**
     * ip地址
     */
    @NotNull
    @NotEmpty
    private String apiId;

    /**
     * 负责人
     */
    @NotNull
    @NotEmpty
    private String secret;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

package com.oycl.input;

import com.oycl.common.base.BaseInput;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegisterInput extends BaseInput {

    /**
     * ip地址
     */
    @NotNull
    @NotEmpty
    private String ipAddress;

    /**
     * 负责人
     */
    @NotNull
    @NotEmpty
    private String principal;


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}

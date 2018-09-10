package cango.scf.com.input;

import cango.scf.com.common.base.BaseInput;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VerifyTokenInput extends BaseInput {

    /**
     * ip地址
     */
    @NotNull
    @NotEmpty
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

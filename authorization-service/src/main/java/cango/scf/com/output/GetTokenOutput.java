package cango.scf.com.output;

import cango.scf.com.common.base.BaseOutput;

public class GetTokenOutput extends BaseOutput{
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

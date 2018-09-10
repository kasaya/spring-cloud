package cango.scf.com.output;

import cango.scf.com.common.base.BaseOutput;

public class VerifyTokenOutput extends BaseOutput{
    /**
     * 验证结果
     */
    private boolean verifyResult;

    /**
     * 失效时间（时间戳）
     */
    private long expireTime;

    /**
     * ip地址
     */
    private String ipAddress;

    //服务信息

    public boolean isVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}

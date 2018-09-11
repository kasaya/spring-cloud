package com.oycl.orm.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cango
 * @since 2018-09-06
 */
public class TAuthBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ip地址
     */
    @TableId("ip_address")
    private String ipAddress;
    /**
     * 责任人
     */
    private String principal;
    @TableField("api_id")
    private String apiId;
    private String secret;
    /**
     * 新增日期时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createDateTime;
    /**
     * 新增人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 更新日期时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;


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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "TAuthBase{" +
        "ipAddress=" + ipAddress +
        ", principal=" + principal +
        ", apiId=" + apiId +
        ", secret=" + secret +
        ", createDateTime=" + createDateTime +
        ", createUser=" + createUser +
        ", lastUpdateTime=" + lastUpdateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}

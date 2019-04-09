package com.oycl.orm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  用户信息
 * </p>
 */
public class TUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ip地址
     */

    private String userId;
    /**
     * 责任人
     */
    private String userName;


    private List<String> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

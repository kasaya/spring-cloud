package com.oycl.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 基础用户信息
 * @author kasaya
 */
@Getter
@Setter
public class UserInfo {

    /**
     * 用户名称
     */
    private String name;

    /**
     * 登录名称
     */
    private String loginId;

    /**
     * 登录密码
     */
    private String password;


    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 角色
     **/
    private List<RoleEntity> role;


}

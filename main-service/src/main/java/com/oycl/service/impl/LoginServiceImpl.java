package com.oycl.service.impl;


import com.google.gson.Gson;
import com.oycl.common.RoleEntity;
import com.oycl.common.UserInfo;
import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.jwt.JwtTokenUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private Gson gson;

    @Override
    public LoginOutput login(LoginInput input) {
        LoginOutput result = new  LoginOutput();


        //模仿数据库/redis取值
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId("zzz");
        userInfo.setName("卡萨亚");
        userInfo.setUserId("1000001");
        RoleEntity entity = new RoleEntity();
        entity.setRoleId("001");
        entity.setRoleName("系统管理员");
        List<RoleEntity> roleEntities = new ArrayList<>();
        roleEntities.add(entity);
        userInfo.setRole(roleEntities);
        // 设定用户信息
        result.setUserInfo(userInfo);
        //生成JWTtoken
        result.setToken(JwtTokenUtil.INSTENS.generateToken(gson.toJson(userInfo)));
        return result;
    }


}



package com.oycl.mainservice.service.impl;

import com.oycl.mainservice.input.LoginInput;
import com.oycl.mainservice.model.UserInfo;
import com.oycl.mainservice.output.LoginOutput;
import com.oycl.mainservice.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LoginServiceImpl implements LoginService{
    @Override
    public LoginOutput login(LoginInput input) {
       //模拟数据库取值
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("001");
        userInfo.setUserName("KASAYA");
        userInfo.setRole(Arrays.asList("001","002","003"));

        //TODO redis 存储
        LoginOutput result = new LoginOutput();
        result.setUserInfo(userInfo);
        return result;
    }
}

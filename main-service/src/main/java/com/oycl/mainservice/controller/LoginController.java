package com.oycl.mainservice.controller;

import com.oycl.mainservice.input.LoginInput;
import com.oycl.mainservice.output.LoginOutput;
import com.oycl.mainservice.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value="/test", tags="测试接口模块")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @CrossOrigin
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    public LoginOutput login(@ModelAttribute  LoginInput input) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }
}

package com.oycl.mainservice.controller;

import com.oycl.mainservice.input.LoginInput;
import com.oycl.mainservice.output.LoginOutput;
import com.oycl.mainservice.service.LoginService;
import com.oycl.mainservice.service.impl.RetryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value="/test", tags="测试接口模块")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RetryServiceImpl retryService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    public LoginOutput login(@RequestBody  LoginInput input) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }

    @RequestMapping(value = "/retry",method = RequestMethod.POST)
    @ApiOperation(value="重试请求测试", notes="根据url的id来获取用户详细信息")
    public void retry(@RequestBody  LoginInput input) {
        retryService.excute();
    }
}

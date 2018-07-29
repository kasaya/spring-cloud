package com.oycl.controller;


import com.oycl.service.LoginInput;
import com.oycl.service.LoginOutput;
import com.oycl.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value="/test", tags="测试接口模块")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    //@CrossOrigin
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    public LoginOutput login(@RequestBody LoginInput input, HttpRequest request) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }
}

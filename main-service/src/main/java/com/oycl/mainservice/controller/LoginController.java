package com.oycl.mainservice.controller;


import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.mainservice.service.LoginService;
import com.oycl.mainservice.service.impl.RetryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RetryServiceImpl retryService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginOutput login(@RequestBody LoginInput input) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }

    @RequestMapping(value = "/retry",method = RequestMethod.POST)
    public void retry(@RequestBody  LoginInput input) {
        retryService.excute();
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public LoginOutput test(@RequestBody  LoginInput input) {
        LoginOutput result = new LoginOutput();
        result.setUserInfo(input.getUserInfo());
        return result;
    }
}

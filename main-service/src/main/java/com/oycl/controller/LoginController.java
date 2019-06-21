package com.oycl.controller;


import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.LoginService;
import com.oycl.service.impl.RetryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;

@RefreshScope
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RetryServiceImpl retryService;

    @Value("${user.mate}")
    private String value;

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginOutput login(@RequestBody LoginInput input) {
        LoginOutput reslut =  loginService.login(input);
        reslut.setResultMessage("success");
        return reslut;
    }

    @PostMapping(value = "/retry",produces = MediaType.APPLICATION_JSON_VALUE)
    public void retry(@RequestBody  LoginInput input) {
        retryService.excute();
    }

    @PostMapping(value = "/test")
    public String test(){
        return  value;
    }
}

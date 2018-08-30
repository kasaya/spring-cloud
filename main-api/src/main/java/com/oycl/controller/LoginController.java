package com.oycl.controller;



import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public LoginOutput login(@RequestBody LoginInput input) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }

    @PostMapping(value = "/test")
    public LoginOutput otherReques(@RequestBody LoginInput input) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }
}

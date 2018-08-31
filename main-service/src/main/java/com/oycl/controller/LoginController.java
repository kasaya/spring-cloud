package com.oycl.controller;


import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.InterfaceService;
import com.oycl.service.LoginService;
import com.oycl.service.impl.RetryServiceImpl;
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

    @Autowired
    private InterfaceService interfaceService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginOutput login(@RequestBody LoginInput input) {
        LoginOutput reslut = loginService.login(input);
        return reslut;
    }

    @RequestMapping(value = "/retry",method = RequestMethod.POST)
    public void retry(@RequestBody  LoginInput input) {
        retryService.excute();
    }


    @RequestMapping(value = "/getMcode",method = RequestMethod.POST)
    public GetMcodeOutPut test(@RequestBody GetMcodeInput input) {
        GetMcodeOutPut outPut = interfaceService.getMcode(input);
        return outPut;
    }
}

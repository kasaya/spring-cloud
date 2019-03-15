package com.oycl.controller;

import com.oycl.api.MainApi;
import com.oycl.compment.log.LogEnable;
import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.LogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InterfaceController implements LogEnable {

    @Autowired
    LogicService logicService;

    @Autowired
    MainApi mainApi;


    @PostMapping(value = "/getmcode")
    public GetMcodeOutPut getMcode(@RequestBody GetMcodeInput input){
        return logicService.getMcode(input);
    }

    @GetMapping(value = "/cross")
    public String cross(){
        LoginInput input = new LoginInput();
        LoginOutput output =  mainApi.login(input);
         return output.getResultMessage();
    }
}

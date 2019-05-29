package com.oycl.controller;

import com.oycl.api.MainApi;
import com.oycl.compment.log.LogEnable;
import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.LogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;


@RestController
public class InterfaceController implements LogEnable {

    @Autowired
    LogicService logicService;

    @Autowired
    MainApi mainApi;

    @RolesAllowed("R01")
    //@PreAuthorize("hasRole('R01')")
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

    @PostMapping(value = "/test")
    public String test(){
        return "success";
    }

    @PostMapping(value = "/setDay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String setDay(@RequestBody String param) {
        return "setDay success ！param：" + param;
    }

    @PostMapping(value = "/decreaseDay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String decreaseDay(@RequestBody String param) {
        return "decreaseDay success！param：" +param;
    }
}

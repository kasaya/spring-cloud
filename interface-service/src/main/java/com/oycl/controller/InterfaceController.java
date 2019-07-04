package com.oycl.controller;

import com.oycl.compment.log.annotation.CustomLog;
import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.mainserver.input.LoginInput;
import com.oycl.service.LogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@CustomLog
@RestController
public class InterfaceController  {

    @Autowired
    LogicService logicService;

    @RolesAllowed("R01")
    //@PreAuthorize("hasRole('R01')")
    @PostMapping(value = "/getmcode")
    public GetMcodeOutPut getMcode(@RequestBody GetMcodeInput input){
        return logicService.getMcode(input);
    }

    /*@GetMapping(value = "/cross")
    public String cross(){
        LoginInput input = new LoginInput();
        LoginOutput output =  mainApi.login(input);
         return output.getResultMessage();
    }*/

    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginInput test(){
        LoginInput input = new LoginInput();
        input.setLoginCd("success");
        return input;
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

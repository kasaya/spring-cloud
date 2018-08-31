package com.oycl.controller;

import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.service.LogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterfaceController {

    @Autowired
    LogicService logicService;

    @PostMapping(value = "/getmcode")
    public GetMcodeOutPut getMcode(@RequestBody GetMcodeInput input){
        return logicService.getMcode(input);
    }
}

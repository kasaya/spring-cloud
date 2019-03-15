package com.oycl.interfaceserver.api;


import com.oycl.interfaceserver.fallback.Fallback;
import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "interface-service", fallback = Fallback.class)
public interface InterfaceService {

    @RequestMapping(value = "/interface/getMcode" , method = RequestMethod.POST)
    GetMcodeOutPut getMcode(@RequestBody GetMcodeInput input);

}

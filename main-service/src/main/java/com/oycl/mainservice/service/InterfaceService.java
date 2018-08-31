package com.oycl.mainservice.service;


import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.mainservice.service.fallback.Fallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "interface-service", fallback = Fallback.class)
public interface InterfaceService {

    @RequestMapping(value = "/interface/getMcode" , method = RequestMethod.POST)
    LoginOutput login(@RequestBody LoginInput input);

}

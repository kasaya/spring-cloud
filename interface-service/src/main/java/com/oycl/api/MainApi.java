package com.oycl.api;

import com.oycl.compment.log.LogEnable;
import com.oycl.config.FeignConfig;
import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(serviceId = "main-service" ,configuration = FeignConfig.class, fallback = MainFallBack.class)
public interface MainApi extends LogEnable{


    @RequestMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginOutput login(@RequestBody LoginInput input);

}

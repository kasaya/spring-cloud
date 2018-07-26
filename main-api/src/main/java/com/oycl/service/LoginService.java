package com.oycl.service;

import com.oycl.fallback.Fallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "main-service", fallback = Fallback.class)
public interface LoginService {

    @RequestMapping(value = "/main/login" , method = RequestMethod.POST)
    LoginOutput login(@RequestBody LoginInput input);
}

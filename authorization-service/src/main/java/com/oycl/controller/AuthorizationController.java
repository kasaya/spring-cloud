package com.oycl.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    /**
     * 注册服务
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(){

    }

    /**
     * 取得token
     */
    @PostMapping(value = "/gettoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getToken(){

    }


    /**
     * 验证token
     */
    @PostMapping(value = "/verifytoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public void verifyToken(){

    }


}

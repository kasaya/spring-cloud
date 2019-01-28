package com.oycl.service.impl;

import exception.NormalExciton;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import util.retry.InterfaceRetryService;

import java.util.Random;

@Service
public class RetryServiceImpl implements InterfaceRetryService {

    @Retryable(value= RemoteAccessException.class ,maxAttempts=2)
    @Override
    public void excute() {
        System.out.println("调用excute方法");
        throw new NormalExciton("E003","业务异常");
    }

    @Override
    @Recover
    public void recover(NormalExciton e) {

        System.out.println("调用recover方法");
    }
}

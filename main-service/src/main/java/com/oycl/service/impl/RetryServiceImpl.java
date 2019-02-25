package com.oycl.service.impl;

import com.oycl.exception.NormalExciton;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryServiceImpl {

    @Retryable(value= RemoteAccessException.class ,maxAttempts=2)

    public void excute() {
        System.out.println("调用excute方法");
        throw new NormalExciton("E003","业务异常");
    }

    @Recover
    public void recover(NormalExciton e) {

        System.out.println("调用recover方法");
    }
}

package com.oycl.fallback;


import com.oycl.service.LoginInput;
import com.oycl.service.LoginOutput;
import com.oycl.service.LoginService;
import org.springframework.stereotype.Component;

@Component
public class Fallback implements LoginService
{
    @Override
    public LoginOutput login(LoginInput input) {
        LoginOutput result = new LoginOutput();
        result.setReslutCode("400");
        result.setReslutMessage("熔断异常");
        return result;
    }
}

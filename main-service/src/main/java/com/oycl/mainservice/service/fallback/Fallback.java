package com.oycl.mainservice.service.fallback;



import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.mainservice.service.InterfaceService;

import org.springframework.stereotype.Component;

@Component
public class Fallback implements InterfaceService
{
    @Override
    public LoginOutput login(LoginInput input) {
        LoginOutput result = new LoginOutput();
        result.setResultCode("400");
        result.setResultMessage("interface熔断异常");
        return result;
    }

}

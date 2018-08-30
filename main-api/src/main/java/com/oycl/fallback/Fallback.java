package com.oycl.fallback;



import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import com.oycl.service.LoginService;
import org.springframework.stereotype.Component;

@Component
public class Fallback implements LoginService
{
    @Override
    public LoginOutput login(LoginInput input) {
        LoginOutput result = new LoginOutput();
        result.setResultCode("400");
        result.setResultMessage("熔断异常");
        return result;
    }

    @Override
    public LoginOutput test(LoginInput input) {
        LoginOutput result = new LoginOutput();
        result.setResultCode("400");
        result.setResultMessage("熔断异常");
        return result;
    }
}

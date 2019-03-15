package com.oycl.api;

import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;
import org.springframework.stereotype.Component;

@Component
public class MainFallBack implements MainApi{
    @Override
    public LoginOutput login(LoginInput input) {
        LoginOutput output = new LoginOutput();
        output.setResultMessage("error");
        return output;
    }
}

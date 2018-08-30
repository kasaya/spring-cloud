package com.oycl.mainservice.service;


import com.oycl.mainserver.input.LoginInput;
import com.oycl.mainserver.output.LoginOutput;

public interface LoginService {

    LoginOutput login(LoginInput input);
}

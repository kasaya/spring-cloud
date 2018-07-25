package com.oycl.mainservice.service;

import com.oycl.mainservice.input.LoginInput;
import com.oycl.mainservice.model.UserInfo;
import com.oycl.mainservice.output.LoginOutput;

public interface LoginService {

    LoginOutput login(LoginInput input);
}

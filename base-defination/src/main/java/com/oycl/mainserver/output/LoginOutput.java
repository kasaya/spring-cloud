package com.oycl.mainserver.output;

import com.oycl.base.BaseOutput;
import com.oycl.common.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginOutput extends BaseOutput {
    private UserInfo userInfo;
    private String token;
}

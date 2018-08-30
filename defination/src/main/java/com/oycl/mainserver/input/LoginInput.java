package com.oycl.mainserver.input;

import com.oycl.base.BaseInput;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kasaya
 */
@Getter
@Setter
public class LoginInput extends BaseInput {

    private String loginCd;
    private String passWord;

}

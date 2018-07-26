package cango.scf.com.api.service;



import cango.scf.com.constants.ResultCode;
import cango.scf.com.qo.GetTokenQO;


import cango.scf.com.vo.LoginVO;
import org.springframework.stereotype.Component;

@Component
public class MainServiceFallBack implements MainService{


    @Override
    public LoginVO getUserByToken(GetTokenQO input) {
        LoginVO result = new LoginVO();
        result.setResultCode(ResultCode.AUTH_ERR);
        result.setResultMessage("登录服务器认证失败");
        return result;
    }
}

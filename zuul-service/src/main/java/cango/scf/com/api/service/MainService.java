package cango.scf.com.api.service;



import cango.scf.com.qo.GetTokenQO;
import cango.scf.maincenter.vo.login.LoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value="main-service", fallback = MainServiceFallBack.class)
public interface MainService {

    /**
     * 取得token
     * @param input
     * @return
     */
    @RequestMapping(value="/main/user/tokenCheck" , method = RequestMethod.POST)
    LoginVO getUserByToken(@RequestBody(required = false) GetTokenQO input);

}

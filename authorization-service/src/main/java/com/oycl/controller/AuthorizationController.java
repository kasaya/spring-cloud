package com.oycl.controller;

import cango.scf.com.common.base.BaseOutput;
import cango.scf.com.common.definitions.Constants;
import cango.scf.com.common.exception.CangoAplException;
import cango.scf.com.input.GetTokenInput;
import com.oycl.input.RegisterInput;
import com.oycl.input.VerifyTokenInput;
import cango.scf.com.output.GetTokenOutput;
import com.oycl.output.RegisterOutput;
import com.oycl.output.VerifyTokenOutput;
import com.oycl.service.AuthroizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    @Autowired
    AuthroizationService authroizationService;

    /**
     * 注册服务
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterOutput register(@RequestBody @Validated RegisterInput input, BindingResult bindingResult) throws Exception {
        paramaterVidation(bindingResult);
        RegisterOutput result = authroizationService.register(input);
        setSuccess(result);
        return result;
    }

    /**
     * 取得token
     */
    @PostMapping(value = "/gettoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTokenOutput getToken(@RequestBody @Validated GetTokenInput input, BindingResult bindingResult) throws Exception {
        paramaterVidation(bindingResult);
        GetTokenOutput result = authroizationService.getToken(input);
        setSuccess(result);
        return result;
    }


    /**
     * 验证token
     */
    @PostMapping(value = "/verifytoken", produces = MediaType.APPLICATION_JSON_VALUE)
    public VerifyTokenOutput verifyToken(@RequestBody @Validated VerifyTokenInput input, BindingResult bindingResult) throws Exception{
        paramaterVidation(bindingResult);
        VerifyTokenOutput result = authroizationService.verifyToken(input);
        setSuccess(result);
        return result;
    }

    /**
     * 处理参数异常
     *
     * @param bindingResult
     */
    private void paramaterVidation(BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMesssage = new StringBuilder("参数异常:");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMesssage.append(fieldError.getField());
                errorMesssage.append(":");
                errorMesssage.append(fieldError.getDefaultMessage());
                errorMesssage.append("; ");
            }
            throw new CangoAplException(Constants.ResultCode.PARAM_ERR, errorMesssage.toString());
        }
    }

    private void setSuccess(BaseOutput outPutBean) {
        outPutBean.setResultCode(Constants.ResultCode.SUCCESS);
    }
}

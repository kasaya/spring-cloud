package com.oycl.service;


import com.oycl.input.GetTokenInput;
import com.oycl.input.RegisterInput;
import com.oycl.input.VerifyTokenInput;

import com.oycl.output.GetTokenOutput;
import com.oycl.output.RegisterOutput;
import com.oycl.output.VerifyTokenOutput;

/**
 * 验证处理逻辑
 */
public interface AuthroizationService {
    /**
     * 注册
     *
     * @param input
     * @return
     */
    RegisterOutput register(RegisterInput input) throws Exception;


    /**
     * 取得token
     *
     * @param input 请求参数
     * @return
     * @throws Exception
     */
    GetTokenOutput getToken(GetTokenInput input) throws Exception;


    /**
     * 验证token有效性
     *
     * @param input 请求参数
     * @return 是否有效
     * @throws Exception
     */
    VerifyTokenOutput verifyToken(VerifyTokenInput input) throws Exception;

}

package com.oycl.exception;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 服务异常定义.
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;
    private static MessageSource messageSource;


    public BusinessException(String code, String ... params) {
        //TODO: 取的错误信息
        super(code);
    }


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, Object[] params, Throwable cause) {
        super(code);
    }



}

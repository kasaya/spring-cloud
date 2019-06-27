package com.oycl.exception;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 服务异常定义.
 */
public class ActivityException extends BaseException {

    private static final long serialVersionUID = 1L;
    private static MessageSource messageSource;


    public ActivityException(String code, String ... params) {
        super(code);
    }

    public ActivityException(String code, Object[] params, Throwable cause) {
        super(code);
    }



}

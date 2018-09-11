package com.oycl.common.exception;


import cango.scf.com.common.definitions.Constants;

public class CangoAplException extends Exception {

	/**错误CODE*/
    private String errorCode;

    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设定指定的Error
     * @param errorCode
     * @param message
     *
     */
    public CangoAplException(String errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }


    /**
     * 默认系统异常 204
     * @param message
     */
    public CangoAplException(String message) {
        this.message = message;
        this.errorCode = Constants.ResultCode.BUSS_ERR;
    }

}

package com.oycl.exception;

/**
 * 系统异常
 */
public class NormalExciton extends RuntimeException {

    private String code;

    private String customerMessage;

    {
        this.code = "E001";
        this.customerMessage = "系统异常";
    }

    public NormalExciton(){
        this.code = "E001";
        this.customerMessage = "系统异常";
    }

    public NormalExciton(String code, String customerMessage){
        this.code = code;
        this.customerMessage = customerMessage;
    }

    public NormalExciton(String customerMessage){
        this.code = "E001";
        this.customerMessage = customerMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }
}

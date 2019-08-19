package com.oycl.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutputParam {

    private boolean result;
    private String message;
    /**返回参数*/
    private Object resultObj;


}

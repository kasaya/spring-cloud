package com.oycl.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InputParam {

    /**业务Key*/
    private String businessKey;
    private String comment;

    private String userId;
    private String group;
    private String taskId;
    /** 流程ID （默认最新版本）*/
    private String processDefinitionKey;
    /**流程定义ID （带版本号）*/
    private String processDefinitionId;
    private String processInstanceId;

    private String jsonParam;
    private String actionParam;


}


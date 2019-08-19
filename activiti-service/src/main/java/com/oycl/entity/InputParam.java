package com.oycl.entity;

import com.oycl.entity.model.SearchParamModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InputParam {

    /**业务Key*/
    private String businessKey;
    /**审批意见*/
    private String comment;
    /**用户ID*/
    private String userId;
    /**任务组*/
    private String group;
    /**用户类型*/
    private String userType;
    /**任务ID*/
    private String taskId;
    /** 流程ID （默认最新版本）*/
    private String processDefinitionKey;
    /** 流程ID （默认最新版本）*/
    private List<String> processDefinitionKeys;
    /**流程定义ID （带版本号）*/
    private String processDefinitionId;
    /**流程实例ID*/
    private String processInstanceId;
    /**全局参数*/
    private String consumerParam;
    /**查询param*/
    private List<SearchParamModel> searchParam;
    /**被委派人*/
    private String targetUserId;
    /**操作人*/
    private String operationUserId;
    /**是否完成*/
    private String finishFlg;

    /**任务ID*/
    private List<String> taskIdList;

}


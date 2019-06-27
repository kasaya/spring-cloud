package com.oycl.controller;


import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import com.oycl.service.JobService;
import com.oycl.service.ShowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TaskController {
    @Autowired
    JobService jobService;

    @Autowired
    ShowTaskService showTaskService;


    /**
     * 任务确认
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/approve", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Object complete(@RequestBody  InputParam inputParam)throws Exception{
        return jobService.complete(inputParam);
    }

    /**
     * 显示用户/用户组任务
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/showTask", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Object showTask(@RequestBody InputParam inputParam) throws Exception{
        return jobService.showTask(inputParam.getUserId(), inputParam.getGroup());
    }

    /**
     * 显示任务详细
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/showTaskDetail", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Object showTaskDetail(@RequestBody InputParam inputParam) throws Exception {
        return jobService.showTaskDetail(inputParam.getTaskId());
    }

    /**
     * 认领任务
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/claimTask", produces =  MediaType.APPLICATION_JSON_VALUE)
    public boolean claim(@RequestBody InputParam inputParam)throws Exception{
        return jobService.claim(inputParam);
    }

    /**
     * 撤销认领
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/cancelClaim", produces =  MediaType.APPLICATION_JSON_VALUE)
    public boolean cancelClaim(@RequestBody InputParam inputParam) throws Exception{
        return jobService.cancelClaim(inputParam);
    }

    /**
     * 当前流程所在节点查询
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/showCurrentPoint", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Object showCurrentPoint(@RequestBody  InputParam inputParam) throws Exception{
        return jobService.showCurrentPoint(inputParam);
    }

    /**
     * 展示审批流水
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/showFullHistory", produces =  MediaType.APPLICATION_JSON_VALUE)
    public String showFullHistory(@RequestBody InputParam inputParam){
        return showTaskService.showFullHistory(inputParam.getProcessInstanceId());
    }
}

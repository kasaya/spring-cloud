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

    /**
     * 开启流程
     *
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutputParam startProcess(@RequestBody InputParam inputParam) {
        return jobService.startJob(inputParam);
    }

    /**
     * 全流程展示(最新)
     */

    @PostMapping(value = "/showProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showProcessList() {
        return jobService.showLatestProcessList();
    }

    /**
     * 全流程展示(全部)
     *
     * @return
     */
    @PostMapping(value = "/showAllProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showAllProcessList() {
        return jobService.showAllProcessList();
    }


    /**
     * 发布流程
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/deployProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutputParam deployProcess(MultipartFile file) {
        return jobService.deployment(file);
    }

    /**
     * 删除流程对象
     *
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/deleteProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutputParam deleteProcessInstance(@RequestBody InputParam inputParam) {
        return jobService.stopProcessInstance(inputParam);
    }

    /**
     * 用户所属流程
     *
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/showUserProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showUserProcess(@RequestBody InputParam inputParam) {
        return jobService.showUserProcess(inputParam);
    }
}

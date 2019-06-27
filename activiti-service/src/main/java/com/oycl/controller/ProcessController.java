package com.oycl.controller;


import com.google.gson.JsonObject;
import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import com.oycl.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProcessController {
    @Autowired
    JobService jobService;

    /**
     * 开启流程
     *
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonObject startProcess(@RequestBody InputParam inputParam) throws Exception {
        return jobService.startJob(inputParam);
    }

    /**
     * 全流程展示(最新)
     */

    @PostMapping(value = "/showProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showProcessList() throws Exception {
        return jobService.showLatestProcessList();
    }

    /**
     * 全流程展示(全部)
     *
     * @return
     */
    @PostMapping(value = "/showAllProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showAllProcessList() throws Exception  {
        return jobService.showAllProcessList();
    }


    /**
     * 发布流程
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/deployProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deployProcess(MultipartFile[] file) throws Exception {
        return jobService.deployment(file);
    }

    /**
     * 删除流程对象
     *
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/deleteProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteProcessInstance(@RequestBody InputParam inputParam) throws Exception {
        return jobService.deleteProcessInstance(inputParam);
    }

    /**
     * 用户所属流程
     *
     * @param inputParam
     * @return
     */
    @PostMapping(value = "/showUserProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showUserProcess(@RequestBody InputParam inputParam) throws Exception {
        return jobService.showUserProcess(inputParam);
    }


}

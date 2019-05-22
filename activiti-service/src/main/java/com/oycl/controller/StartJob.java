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

@RestController
public class StartJob {

    @Autowired
    JobService jobService;
    @Autowired
    ShowTaskService showTaskService;

    @PostMapping(value = "/start", produces =  MediaType.APPLICATION_JSON_VALUE)
    public OutputParam startJob(@RequestBody InputParam inputParam){
        return jobService.startJob(inputParam);
    }

    @PostMapping(value = "/approve", produces =  MediaType.APPLICATION_JSON_VALUE)
    public OutputParam approve(@RequestBody InputParam inputParam){
        return jobService.complete(inputParam);
    }
    @PostMapping(value = "/showTask", produces =  MediaType.APPLICATION_JSON_VALUE)
    public String showTask(@RequestBody InputParam inputParam){
        return jobService.showTask(inputParam.getUserId(), inputParam.getGroup());
    }
    @PostMapping(value = "/claimTask", produces =  MediaType.APPLICATION_JSON_VALUE)
    public OutputParam claim(@RequestBody InputParam inputParam){
        return jobService.claim(inputParam);
    }
}

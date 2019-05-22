package com.oycl.definition;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作业
 */
public enum Job {

    /**
     * 作业
     */
    JOB;

    private final Map<String ,String> jobType = new LinkedHashMap<>();

    Job(){
        jobType.put("10","day_off");
        jobType.put("11","ask_day_off");
        jobType.put("12","reimburse");
    }

    public String getJobKey(String jobId){
        return jobType.get(jobId);
    }

    public void addJob(String jobId, String key){
        jobType.put(jobId,key);
    }

}

package com.oycl.service;


import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;

public interface JobService {

    /**
     * 启动任务
     * @param inputParam
     * @return
     */
    OutputParam startJob(InputParam inputParam);


    String showTask(String user, String group);

    OutputParam complete(InputParam inputParam);

    /**
     * 认领任务
     * @param inputParam
     * @return
     */
    OutputParam claim(InputParam inputParam);
}

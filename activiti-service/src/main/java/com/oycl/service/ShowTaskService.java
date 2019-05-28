package com.oycl.service;


import java.io.InputStream;

public interface ShowTaskService {
    /**
     * 展示流程图
     * @param instanceId
     * @return
     */
    InputStream ShowImg(String instanceId);

    /**
     * 展示流水
     * @param instanceId
     * @return
     */
    String showFullHistory(String instanceId);

}

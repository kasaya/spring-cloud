package com.oycl.service;


import com.oycl.entity.ProcessHistoryModel;

import java.io.InputStream;
import java.util.List;

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
    List<ProcessHistoryModel> showFullHistory(String instanceId);

}

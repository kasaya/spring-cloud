package com.oycl.service;

import com.oycl.entity.DayOffInput;
import com.oycl.entity.DayOffOutPut;
import com.oycl.entity.TaskModel;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import java.util.List;

public interface DayOffService {

    DayOffOutPut startDayoff(DayOffInput input);

    String  showTask(String group);

    DayOffOutPut employeeApply(DayOffInput input);

    String mgApprove(DayOffInput input);


}

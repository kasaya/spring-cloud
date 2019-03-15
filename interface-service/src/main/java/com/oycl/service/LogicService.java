package com.oycl.service;


import com.oycl.compment.log.LogEnable;
import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 业务服务
 * @author cango
 */

public interface LogicService extends LogEnable {
    GetMcodeOutPut getMcode(@RequestBody GetMcodeInput input);
}

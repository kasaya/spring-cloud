package com.oycl.service.fallback;



import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.service.InterfaceService;

import org.springframework.stereotype.Component;

@Component
public class Fallback implements InterfaceService
{
    @Override
    public GetMcodeOutPut getMcode(GetMcodeInput input) {
        GetMcodeOutPut result = new GetMcodeOutPut();
        result.setResultCode("400");
        result.setResultMessage("interface熔断异常");
        return result;
    }
}

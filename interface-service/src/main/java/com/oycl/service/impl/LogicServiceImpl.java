package com.oycl.service.impl;

import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.service.LogicService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogicServiceImpl implements LogicService {
    @Override
    public GetMcodeOutPut getMcode(GetMcodeInput input) {

        //模拟业务处理
        GetMcodeOutPut result= new GetMcodeOutPut();
        List<GetMcodeOutPut.McodeEntity> list = new ArrayList<>();
        GetMcodeOutPut.McodeEntity entity = new GetMcodeOutPut.McodeEntity();
        entity.setClassCd("CD0001");
        entity.setItemCd("ID01");
        entity.setItemValue("电影1号");
        list.add(entity);
        result.setMcodeEntity(list);
        return result;
    }
}

package com.oycl.service.impl;

import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.model.MCodeBase;
import com.oycl.service.LogicService;
import com.oycl.util.cache.McodeCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LogicServiceImpl implements LogicService {

//    @Autowired
//    McodeCache mcodeCache;


    @Override
    public GetMcodeOutPut getMcode(GetMcodeInput input) {

        //List<MCodeBase> mCodeBaseList =  mcodeCache.getMcode(input.getClassCd());
        GetMcodeOutPut result= new GetMcodeOutPut();
//        final List<GetMcodeOutPut.McodeEntity> list = new ArrayList<>();
//        mCodeBaseList.forEach(item->{
//            GetMcodeOutPut.McodeEntity entity = new GetMcodeOutPut.McodeEntity();
//            BeanUtils.copyProperties(item,entity);
//            list.add(entity);
//        });
//        //模拟业务处理
//        result.setMcodeEntity(list);
        return result;
    }
}

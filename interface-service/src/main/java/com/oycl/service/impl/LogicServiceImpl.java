package com.oycl.service.impl;


import com.oycl.interfaceserver.input.GetMcodeInput;
import com.oycl.interfaceserver.output.GetMcodeOutPut;
import com.oycl.model.MCode;
import com.oycl.service.LogicService;
import com.oycl.orm.dao.MCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogicServiceImpl implements LogicService {

    @Autowired
    MCodeMapper mCodeMapper;


    @Override
    public GetMcodeOutPut getMcode(GetMcodeInput input) {

        //List<MCode> mCodeBaseList =  mcodeCache.getMcode(input.getClassCd());
        GetMcodeOutPut result= new GetMcodeOutPut();
        MCode mCode = new MCode();
        mCode.setClassCd("999");
        mCode.setItemCd("01");
        mCode.setItemValue("2");
        mCode.setItemContent("1222");
        mCode.setItemInfo("1");
        mCodeMapper.updateByIdNotNull(mCode);
        result.setResultMessage("success");
        return result;
    }
}

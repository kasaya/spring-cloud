package com.oycl;

import com.oycl.compment.log.LogEnable;
import com.oycl.dao.MCodeMapper;
import com.oycl.model.MCodeBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Mycontroller implements LogEnable {


    @Autowired
    MCodeMapper mCodeMapper;




    @GetMapping(value = "/codeTest")
    public String cross(){
      MCodeBase mCodeBase = mCodeMapper.getById(1);
      return "success";
    }
}

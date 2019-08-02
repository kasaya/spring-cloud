package com.oycl;

import com.oycl.dao.MCodeMapper;
import com.oycl.model.MCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Mycontroller {


    @Autowired
    MCodeMapper mCodeMapper;




    @GetMapping(value = "/codeTest")
    public String cross(){
      MCode mCodeBase = mCodeMapper.getById(1);
      return "success";
    }
}

package com.oycl.starter.service;

import com.google.gson.Gson;
import com.oycl.starter.properties.ExceptionAutoConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理service
 * @author kasaya
 */
public class ExceptionService {

    private ExceptionAutoConfigProperty property;

    @Autowired
    Gson gson;

    private Map<String, String> map;

    public ExceptionService(ExceptionAutoConfigProperty exceptionAutoConfigProperty){
        property = exceptionAutoConfigProperty;
        init();
    }


    private void init(){
        if(null != property.getConfigUrl() && "".equals(property.getConfigUrl())){

            map = convert2Map(property.getConfigUrl());
        }else{
            map = property.getErrorMap();
        }
    }


    public String getMessage(String ecode){
        return property.getErrorMap().get(ecode);
    }

    private Map<String, String> convert2Map(String path){
        Map<String, String> result = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(ResourceUtils.getFile(path));
            if(path.endsWith(".json")){
                result = gson.fromJson(gson.newJsonReader(fileReader),HashMap.class);

            }
            if(path.endsWith(".properties")){

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}

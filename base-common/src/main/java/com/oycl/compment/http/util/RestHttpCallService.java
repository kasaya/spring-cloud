package com.oycl.compment.http.util;

import com.oycl.compment.http.RestHttpHystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Function;

public class RestHttpCallService {

    private RestTemplate restTemplate;

    private String url;

    public RestHttpCallService(RestTemplate restTemplate, String url){
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Object RestHttpCall(Object[] uriVariables){
        Function <Object, Object> function = null;
        if(uriVariables != null && uriVariables.length > 0){
            function = param-> restTemplate.postForObject(url, param, Object.class, uriVariables);
        }else{
            function = param-> restTemplate.postForObject(url, param, Object.class);
        }
        return new RestHttpHystrixCommand<Object>("user-client", null ,function);
    }


}

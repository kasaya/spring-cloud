package com.oycl.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MainApi {

    public String callInterface(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String > responseEntity = restTemplate.postForEntity("http://localhost:/", null, String.class);
        String body =responseEntity.getBody();
        return body;
    }

}

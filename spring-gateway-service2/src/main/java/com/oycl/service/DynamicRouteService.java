package com.oycl.service;

import com.oycl.config.FileRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Endpoint(id = "refresh-rr")
public class DynamicRouteService implements ApplicationEventPublisherAware{

    private ApplicationEventPublisher publisher;

    @Autowired
    private FileRouteDefinitionRepository fileRouteDefinitionRepository;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @WriteOperation
    public void notifyChanged(){
        fileRouteDefinitionRepository.initRoute();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}

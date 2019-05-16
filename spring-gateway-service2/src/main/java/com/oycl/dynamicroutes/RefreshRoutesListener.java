package com.oycl.dynamicroutes;

import com.oycl.config.FileRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesApplicationEvent> {


    @Autowired
    private FileRouteDefinitionRepository fileRouteDefinitionRepository;

    @Override
    public void onApplicationEvent(RefreshRoutesApplicationEvent event) {
        fileRouteDefinitionRepository.initRoute();
        System.out.println("RefreshRoutesListener.onApplicationEvent");
    }

}

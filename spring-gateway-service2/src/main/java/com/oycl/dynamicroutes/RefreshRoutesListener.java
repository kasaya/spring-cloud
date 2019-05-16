package com.oycl.dynamicroutes;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesApplicationEvent>, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void onApplicationEvent(RefreshRoutesApplicationEvent event) {
        System.out.println(event.getOriginService());
        System.out.println("RefreshRoutesListener.onApplicationEvent");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}

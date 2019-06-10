package com.oycl.dynamicroutes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 继承spring cloud bus 的抽象基类RemoteApplicationEvent
 *
 */
@Component
public class RefreshRoutesApplicationEvent extends RemoteApplicationEvent {

    @Autowired
    private FileRouteDefinitionRepository fileRouteDefinitionRepository;

    @EventListener
    public void onApplicationEvent(RefreshRoutesApplicationEvent event) {
        System.out.println(event.getId());
        System.out.println(event.getDestinationService());
        System.out.println(event.getOriginService());
        fileRouteDefinitionRepository.initRoute();
        System.out.println("RefreshRoutesListener.onApplicationEvent");
        System.out.println("********");
    }

}

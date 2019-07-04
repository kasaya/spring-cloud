package com.oycl.dynamicroutes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 监听刷新gateway-server的事件
 */

public class RefreshRoutesListener {/*implements ApplicationListener<RemoteApplicationEvent> {

    @Autowired
    private FileRouteDefinitionRepository fileRouteDefinitionRepository;


    @Override
    public void onApplicationEvent(RemoteApplicationEvent event) {
        System.out.println(event.getId());
        System.out.println(event.getDestinationService());
        System.out.println(event.getOriginService());
        fileRouteDefinitionRepository.initRoute();
        System.out.println("RefreshRoutesListener.onApplicationEvent");
        System.out.println("********");
    }*/

}

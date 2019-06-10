package com.oycl.dynamicroutes;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 自定义一个新的端点用于刷新gateway的route配置， 通过调用/actuator/refresh-gateway实现刷新
 */

@Endpoint(id = "refresh-rr")
public class DynamicRouteBusEndpoint extends AbstractBusEndpoint{

    public DynamicRouteBusEndpoint(ApplicationEventPublisher context, String appId) {
        super(context, appId);
    }

    @WriteOperation
    public void notifyChanged(){

        this.publish(new RefreshRoutesApplicationEvent(this,getInstanceId()));
        System.out.println("notifyChanged");
    }
}

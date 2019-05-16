package com.oycl.dynamicroutes;

import com.oycl.config.FileRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Endpoint(id = "refresh-gateway")
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

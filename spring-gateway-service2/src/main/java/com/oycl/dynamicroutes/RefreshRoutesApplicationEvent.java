package com.oycl.dynamicroutes;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class RefreshRoutesApplicationEvent extends RemoteApplicationEvent {

    public RefreshRoutesApplicationEvent() {
    }

    public RefreshRoutesApplicationEvent(Object source, String orginService){
        super(source, orginService, "gateway-server");
    }


}

package com.oycl.dynamicroutes;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 继承spring cloud bus 的抽象基类RemoteApplicationEvent
 *
 */
@Component
public class RefreshRoutesApplicationEvent extends RemoteApplicationEvent {

    public RefreshRoutesApplicationEvent() {
    }

    public RefreshRoutesApplicationEvent(Object source, String orginService){
        //在RemoteApplicationEvent中定义了主要的三个通用属性事件的来源originService、事件的目的服务destinationService和随机生成的全局id。
        super(source, orginService, "gateway-server");
    }


}

package com.oycl.definition;


import com.oycl.extention.SystemPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(name="systemConfig", value = {"classpath:/systemConfig-${spring.profiles.active}.properties"}, factory = SystemPropertySourceFactory.class)
public class SystemProperties {


    //机器ID 0~31
    @Value("${workerId}")
    private long workerId;

    //数据中心ID
    @Value("${datacenterId}")
    private long datacenterId;

    //用户TOKEN过期时间 一个半小时
    @Value("${useTokenExpire}")
    private long useTokenExpire;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long getUseTokenExpire() {
        return useTokenExpire;
    }

    public void setUseTokenExpire(long useTokenExpire) {
        this.useTokenExpire = useTokenExpire;
    }
}

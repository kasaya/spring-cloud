package com.oycl.starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "exception")
public class ExceptionAutoConfigProperty {
    /**
     * 配置文件路径
     */
    private String configUrl;

    private Map<String, String> errorMap;

}

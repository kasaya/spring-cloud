package com.oycl.compment.http.util;

import com.oycl.compment.http.annotaiton.RestHttpCall;
import com.oycl.util.spring.SpringContextUtil;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestHttpCallRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(RestHttpCall.class.getName(), true);
        if (attrs != null && attrs.containsKey("value")) {
            AnnotationAttributes[] clients = (AnnotationAttributes[]) attrs.get("value");
            for(AnnotationAttributes attributes: clients){
                registerConfiguration(registry,getUrl(attributes));
            }
        }
    }

    private String getUrl(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String value = (String) client.get("value");
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("name");
        }
        if (StringUtils.hasText(value)) {
            return value;
        }
        throw new IllegalStateException(
                "Either 'name' or 'value' must be provided in @RestHttpCall");
    }


    private void registerConfiguration(BeanDefinitionRegistry registry, String url){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RestHttpCallService.class);

        RestTemplate restTemplate = (RestTemplate)SpringContextUtil.getBean("restHttpCallRestTemplate");
        builder.addConstructorArgValue(restTemplate);
        builder.addConstructorArgValue(url);
        registry.registerBeanDefinition("restHttpCall",builder.getBeanDefinition());

    }


}

package com.oycl.extention;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 用户处理systemConfi-dev,test.properties 分环境读取多环境配置文件
 */
@Configuration
public class SystemPropertySourceFactory implements PropertySourceFactory {


    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        //取得当前活动的环境名称（ spring.profiles.active 取得失败）
        String[] fileproperty = encodedResource.getResource().getFilename().split("\\.");
        String[] actives = fileproperty[0].replace(name + "-", "").split(",");
        //如果只有一个，就直接返回
        if (actives.length <= 1) {
            return (name != null ? new ResourcePropertySource(name, encodedResource) : new ResourcePropertySource(encodedResource));
        }
        //如果是多个
        List<InputStream> inputStreamList = new ArrayList<>();
        String suffix = fileproperty[1];
        //遍历后把所有环境的url全部抓取到list中
        Arrays.stream(actives).forEach(active -> {
            InputStream in = this.getClass().getResourceAsStream("/" + name.concat("-" + active).concat(".").concat(suffix));
            if (in != null) {
                inputStreamList.add(in);
            }
        });
        if (inputStreamList != null && inputStreamList.size() > 0) {
            //串行流
            SequenceInputStream inputStream = new SequenceInputStream(Collections.enumeration(inputStreamList));
            //转成resource
            InputStreamResource resource = new InputStreamResource(inputStream);
            return (name != null ? new ResourcePropertySource(name, new EncodedResource(resource)) : new ResourcePropertySource(new EncodedResource(resource)));
        } else {
            return (name != null ? new ResourcePropertySource(name, encodedResource) : new ResourcePropertySource(encodedResource));
        }
    }

}

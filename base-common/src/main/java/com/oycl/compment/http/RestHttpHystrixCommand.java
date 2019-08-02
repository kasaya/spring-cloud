package com.oycl.compment.http;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.Builder;
import lombok.Singular;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

public class RestHttpHystrixCommand<Object> extends HystrixCommand<Object>{


    private final Function<Object, Object> function;

    private final Object param;


    public RestHttpHystrixCommand(String group, Object param, Function function){
        super(HystrixCommandGroupKey.Factory.asKey(group),1000);
        this.function = function;
        this.param = param;
    }


    @Override
    protected Object run() throws Exception {
        return function.apply(param);
    }

    @Override
    protected Object getFallback() {
        return super.getFallback();
    }


}

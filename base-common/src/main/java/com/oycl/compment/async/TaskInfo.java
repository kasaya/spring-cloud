package com.oycl.compment.async;

import java.util.function.Consumer;

/**
 * 异步处理信息载体
 *
 * @param <I> 请求入参
 * @author oycl
 */
public class TaskInfo<I> {

    /**
     * 请求参数
     */
    private I params;

    /**
     * 处理方法
     */
    private Consumer<I> consumer;

    public I getParams() {
        return params;
    }

    public void setParams(I params) {
        this.params = params;
    }

    public Consumer<I> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<I> consumer) {
        this.consumer = consumer;
    }
}

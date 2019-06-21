package com.oycl.compment.async;

import java.util.function.Consumer;

/**
 * 调用异步server执行类
 * 例子:
 *
 *    //实际业务处理
 *    ExampleService exampleService;
 *     //返回参数
 *   final DeferredResult<Object> result = DeferredResultFactory.INSTENSE.createResult();
      // 使用方法，item= param = 传入参数。
     AsyncUtil.run(item->{
            result.setResult(exampleService.task3(item));
     }, param);

 @author oycl
 */
public class AsyncUtil {

    private static BlockingTaskManager blockingTaskManager;


    public static void setBlockingTaskManager(BlockingTaskManager blockingTaskManager) {
        AsyncUtil.blockingTaskManager = blockingTaskManager;
    }

    public static <I> void run(Consumer<I> tConsumer, I param){
        TaskInfo<I> taskInfo = new TaskInfo<>();
        taskInfo.setParams(param);
        taskInfo.setConsumer(tConsumer);
        blockingTaskManager.push(taskInfo);
    }
}

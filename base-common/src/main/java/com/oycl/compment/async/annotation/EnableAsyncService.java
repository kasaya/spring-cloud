package com.oycl.compment.async.annotation;




import com.oycl.compment.async.AsyncTaskConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启异步service处理逻辑
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AsyncTaskConfig.class)
public @interface EnableAsyncService {
}

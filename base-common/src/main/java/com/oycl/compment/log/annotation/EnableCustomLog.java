package com.oycl.compment.log.annotation;



import com.oycl.compment.log.partner.LogConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 用于开启自定义Log
 * 使用方法：
 * 在你的applicatin上开启注解
 * @EnableCustomLog
   public class AsynbootDemoApplication {}

   然后在需要输出Log类上加上注解 @CustomLog
   log中会输出该类的对应方法的执行时间。
 *
 *
 *
 *
 *
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(LogConfig.class)
public @interface EnableCustomLog {
}

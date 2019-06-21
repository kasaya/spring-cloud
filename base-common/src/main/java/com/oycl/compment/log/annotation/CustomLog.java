package com.oycl.compment.log.annotation;


import java.lang.annotation.*;

/**
 * 被注解的类下的所有方法，将会输出自定义log
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface CustomLog {
}

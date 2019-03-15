package com.oycl.compment.log.annotation;

import com.oycl.compment.log.LogConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(LogConfig.class)
public @interface EnableCustomLog {
}

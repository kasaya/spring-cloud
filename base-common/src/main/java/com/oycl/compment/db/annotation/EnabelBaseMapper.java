package com.oycl.compment.db.annotation;

import com.oycl.compment.db.config.MybatisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(MybatisConfig.class)
public @interface EnabelBaseMapper {

}

package com.oycl.compment.http.annotaiton;

import com.oycl.compment.http.util.RestHttpCallRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Configuration
@Import(RestHttpCallRegistrar.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestHttpCall {

    /**
     * Synonym for name (the name of the client)
     *
     * @see #name()
     */
    String value() default "";

    /**
     * The name of the ribbon client, uniquely identifying a set of client resources,
     * including a load balancer.
     */
    String name() default "";
}

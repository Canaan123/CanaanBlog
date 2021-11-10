package com.example.cananblog.log;

import java.lang.annotation.*;


@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String operator() default "";
    String detail() default "";
}

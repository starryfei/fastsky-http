package com.starry.fastsky.annotation;

import java.lang.annotation.*;

/**
 * ClassName: Before
 * Description: 定义在方法上面的切面注解
 *
 * @author: starryfei
 * @date: 2019-01-18 14:03
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Before {
    /**
     * The pointcut expression where to bind the advice
     */
    String value();

    /**
     * When compiling without debug info, or when interpreting pointcuts at runtime,
     * the names of any arguments used in the advice declaration are not available.
     * Under these circumstances only, it is necessary to provide the arg names in
     * the annotation - these MUST duplicate the names used in the annotated method.
     * Format is a simple comma-separated list.
     */
    String argNames() default "";

}

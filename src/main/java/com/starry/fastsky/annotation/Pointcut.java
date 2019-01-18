package com.starry.fastsky.annotation;

import java.lang.annotation.*;

/**
 * ClassName: Pointcut
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-18 14:13
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pointcut {
    /**
     * The pointcut expression
     * 指定某个类的所有方法
     */
    String value() default "";

    /**
     * When compiling without debug info, or when interpreting pointcuts at runtime,
     * the names of any arguments used in the pointcut are not available.
     * Under these circumstances only, it is necessary to provide the arg names in
     * the annotation - these MUST duplicate the names used in the annotated method.
     * Format is a simple comma-separated list.
     */
    String argNames() default "";
}

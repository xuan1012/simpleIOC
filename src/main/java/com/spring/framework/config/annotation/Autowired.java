package com.spring.framework.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//保留在哪个阶段，源码 | 类 | 运行时
@Retention(RetentionPolicy.RUNTIME)
//应用于哪种目标类型：  TYPE 类 | METHOD 方法 | FIELD 属性
@Target(value={ElementType.METHOD,ElementType.FIELD})
public @interface Autowired {

    boolean required() default true;

}

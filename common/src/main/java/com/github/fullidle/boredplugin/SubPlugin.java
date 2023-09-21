package com.github.fullidle.boredplugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 指定注解可以应用于的目标类型
public @interface SubPlugin {
    String load() default "";
    String enable() default "";
    String disable() default "";
}

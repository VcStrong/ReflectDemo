package com.dingtao.reflectdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//注解存活的时间
@Target(ElementType.FIELD)//注解在哪些地方
public @interface BindView {

    int id();
    String name();
}
package com.dingtao.reflectdemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author dingtao
 * @date 2018/12/25 15:35
 * qq:1940870847
 */
public class Test {
    public static void main(String[] args) {
        try {
            Class personClass = Class.forName("com.dingtao.reflectdemo.Person");
            Constructor constructor = personClass.getDeclaredConstructor();
            constructor.setAccessible(true);//访问权限，默认false:不修改访问作用域
            Object o = constructor.newInstance();//构造方法new对象
            Method method = personClass.getDeclaredMethod("setAge", int.class);

            method.invoke(o,18);

            Method method1 = personClass.getDeclaredMethod("getAge");
            method1.setAccessible(true);//访问权限
            int ageValue = (int)method1.invoke(o);
            System.out.println(ageValue);

            Field field = personClass.getDeclaredField("name");
            field.setAccessible(true);//访问权限
            field.set(o,"aaaa");
            System.out.println(field.get(o));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

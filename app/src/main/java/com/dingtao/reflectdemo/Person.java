package com.dingtao.reflectdemo;

/**
 * @author dingtao
 * @date 2018/12/25 15:35
 * qq:1940870847
 */
public class Person {
    private Person(){

    }
    private int age;
    private String name;

    public void setAge(int age) {
        this.age = age;
    }

    private int getAge() {
        return age;
    }
}

package com.wuzhsh.dubbo.test3;

import org.apache.dubbo.common.extension.Adaptive;

@Adaptive
public class Cat implements Animal {

    @Override
    public void say() {
        System.out.println("Cat");
    }
}

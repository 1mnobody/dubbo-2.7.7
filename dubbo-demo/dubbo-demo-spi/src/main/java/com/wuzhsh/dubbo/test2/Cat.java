package com.wuzhsh.dubbo.test2;

import org.apache.dubbo.common.URL;

public class Cat implements Animal {

    @Override
    public void say(URL url) {
        System.out.println("Cat");
    }
}

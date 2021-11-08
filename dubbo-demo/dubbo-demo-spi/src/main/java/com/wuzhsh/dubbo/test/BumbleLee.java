package com.wuzhsh.dubbo.test;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

public class BumbleLee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumble Lee");
    }

    @Override
    public void name(URL url) {
        System.out.println("Bumble Lee");
    }
}

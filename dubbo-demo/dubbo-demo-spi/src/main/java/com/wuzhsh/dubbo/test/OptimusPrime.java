package com.wuzhsh.dubbo.test;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

public class OptimusPrime implements Robot {
    @Override
    public void sayHello() {
        System.out.println("hello ,i am Optimus Prime");
    }

    @Override
    public void name(URL url) {
        System.out.println("Optimus");
    }
}

package com.wuzhsh.dubbo.test;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;


@SPI
public interface Robot {

    void sayHello();

    @Adaptive
    void name(URL url);
}

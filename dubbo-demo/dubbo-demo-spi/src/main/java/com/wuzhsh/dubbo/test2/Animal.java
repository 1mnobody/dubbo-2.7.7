package com.wuzhsh.dubbo.test2;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Animal {

    @Adaptive("friend")
    void say(URL url);
}

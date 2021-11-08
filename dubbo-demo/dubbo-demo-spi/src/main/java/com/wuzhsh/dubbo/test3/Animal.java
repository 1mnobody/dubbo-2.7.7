package com.wuzhsh.dubbo.test3;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Animal {

    void say();
}

package com.wuzhsh.dubbo.test;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class SPITest {
    public static void main(String[] args) {
        // 总体来说就是 配置文件 + 反射来得到 要创建的类
        // 首先通过 java 本身提供的 SPI 加载了三个 Strategy：
        // org.apache.dubbo.common.extension.DubboInternalLoadingStrategy
        // org.apache.dubbo.common.extension.DubboLoadingStrategy
        // org.apache.dubbo.common.extension.ServicesLoadingStrategy
        // 三个 Strategy 对应三个目录，扫描目录下的配置文件，进行加载，由于配置文件是 kv 的形式，所以getExtension可以通过name（key） 获取到
        // 要反射的类信息，进而创建出对应的实例
        // ExtensionLoader.getExtensionLoader(Robot.class); 这个方法会加载 AdaptiveExtensionFactory，加载
        // 这个Factory的同时会加载 SpiExtensionFactory（因为这两个 ExtensionFactory 配置在同一个文件中）
        // ExtensionLoader<ExtensionFactory.class> 的 cachedClasses 的 key只有一个 spi (先加载 AdaptiveExtensionFactory，再加载SpiExtensionFactory）
        // ExtensionLoader<Robot.class> 的 cachedClasses key 包含了 robot1 和 robot2
//        ExtensionLoader<Robot> loader = ExtensionLoader.getExtensionLoader(Robot.class);
//        Robot robot1 = loader.getExtension("robot1");
//        Robot robot2 = loader.getExtension("robot2");
//        robot1.sayHello();
//        robot2.sayHello();

        URL url = URL.valueOf("dubbo://192.168.0.101:20880?robot=robot1");
        // r 是一个代理
        // AdaptiveExtension 的本质逻辑（生成的代码）实际上就是根据 url中的参数（如此例中的 robot 指定为 robot1了），去再调用一次 ExtensionLoader.getExtension("robot1")
        // 得到的Robot实例，并且调用的就是这个 Robot 实例的 name方法
        Robot r  = ExtensionLoader.getExtensionLoader(Robot.class).getAdaptiveExtension();
        r.name(url);
        r.sayHello();
    }
}

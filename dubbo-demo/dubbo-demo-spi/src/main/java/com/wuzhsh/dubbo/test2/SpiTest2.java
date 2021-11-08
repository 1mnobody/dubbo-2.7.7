package com.wuzhsh.dubbo.test2;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class SpiTest2 {

    public static void main(String[] args) {
        ExtensionLoader<Animal> loader = ExtensionLoader.getExtensionLoader(Animal.class);
        // brid 实例中已经初始化了 friend（在创建bird对象时，会遍历其中的setter方法，如果这些setter满足条件[如 为public方法等]，会执行这些setter方法）
        // 注意 ExtensionLoader.getExtension 过程中，调用 对象的setter方法时，是通过 ExtensionFactory.getExtension 方法来查找要set进去的属性的。
        // 比如，此例中的 bird 的 setter，要set一个Animal对象，这个Animal对象就是通过 ExtensionFactory.getExtension 来查找的，而在 SpiExtensionFactory 中
        // 只会 查找 adaptiveExtension，因此必须要结合 Adaptive 注解来使用 dubbo 提供的 IOC 功能
        // 在使用url参数版本的Adaptive注解时，adaptiveExtension实际上还是生成了一个 Adaptive代理，这个代理被注入到了Bird实例中，最终方法调用还是使用URL来查找实际的对象
        Animal bird = loader.getExtension("bird");
        bird.say(URL.valueOf("localhost:8080/test?friend=cat"));
    }

}

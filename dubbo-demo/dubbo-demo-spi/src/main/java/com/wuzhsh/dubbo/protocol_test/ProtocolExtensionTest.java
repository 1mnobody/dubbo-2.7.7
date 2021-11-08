package com.wuzhsh.dubbo.protocol_test;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

public class ProtocolExtensionTest {

    public static void main(String[] args) {
        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol adaptiveExtension = extensionLoader.getAdaptiveExtension();
        // Protocol 的 SPI 注解中的默认 value 为 dubbo，这个值在 getAdaptiveExtension 时，会在 cacheDefaultExtensionName 方法中，
        // 被赋值到 ExtensionLoader 的 cachedDefaultName 属性中，这个属性在后续生成 Protocol$Adaptive 的源码时，会被写入源码中：
        // String extName = ( url.getProtocol() == null ? "dubbo" : url.getProtocol() );
        // 上面这句代码中的 dubbo 就是从 cachedDefaultName 里拿来的数据。因此 url 在没有配置 protocol 时，默认使用的是 dubbo 对应的 Protocol 实现
//        adaptiveExtension.refer(Protocol.class, URL.valueOf("mock://127.0.0.1:21808"));
        adaptiveExtension.refer(Protocol.class, URL.valueOf("127.0.0.1:21808"));
        // 注意：Protocol 对应的 SPI配置文件 在 dubbo-rpc-api 以及 dubbo-rpc-dubbo 两个工程的 resources目录下均有，所以两者都会加载
        // 如果没有依赖 dubbo-rpc-dubbo， 显然会找不到 "dubbo" 对应的 extension： No such extension org.apache.dubbo.rpc.Protocol by name dubbo
    }
}

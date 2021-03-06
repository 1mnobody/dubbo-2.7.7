/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.RegistryConstants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.consumer.comp.DemoServiceComponent;

import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Application {
    /**
     * In order to make sure multicast registry works, need to specify '-Djava.net.preferIPv4Stack=true' before
     * launch the application
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExtensionLoader<RegistryFactory> extensionLoader = ExtensionLoader.getExtensionLoader(RegistryFactory.class);
        Registry registry = extensionLoader.getAdaptiveExtension().getRegistry(URL.valueOf("zookeeper://127.0.0.1:2181"));
        // ??????DemoService??? Consumer Url?????????????????????????????? url ?????????????????????????????????
        registry.subscribe(URL.valueOf("consumer://192.168.1.5/" + DemoService.class.getName()
                + "?" + RegistryConstants.CATEGORY_KEY + "=" + RegistryConstants.CONSUMERS_CATEGORY), (urls) -> {
            for (URL url : urls) {
                System.out.println(" -------- " + url);
            }
        });

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        DemoService service = context.getBean("demoServiceComponent", DemoServiceComponent.class);
        String hello = service.sayHello("world");
        System.out.println("result :" + hello);

        CompletableFuture<String> res = service.sayHelloAsync("hahah");
        System.out.println(res.get());
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.demo.consumer.comp")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.demo.consumer.comp"})
    static class ConsumerConfiguration {

    }
}

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
package org.apache.dubbo.demo.provider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        // 这个bean是通过 DubboConfigConfigurationRegistrar 中的 Single（Multiple）注入进来的
        ApplicationConfig bean = (ApplicationConfig)context.getBean("org.apache.dubbo.config.ApplicationConfig#0");
        System.out.println(bean.getName());
        System.in.read();
    }

    // 借助这个Configuration进入到 ConfigurationClassPostProcessor 来处理Import进来的 DubboConfigConfigurationRegistrar，以此注册dubbo的相关类。
    // ConfigurationClassPostProcessor 是Spring自身实现的PostProcessor，用来处理 Configuration 注解的类
    // 上面的这个类，会调用 ConfigurationClassParser#parse 方法，该方法会递归解析注解，得到 Import 注解的相关信息，
    // 并处理 Import 注解中的相关内容(递归的处理逻辑在 ConfigurationClassParser#collectImports 方法中）
    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.demo.provider")
    @PropertySource("classpath:/spring/dubbo-provider.properties")
    static class ProviderConfiguration {
        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress("zookeeper://127.0.0.1:2181");
            return registryConfig;
        }
    }
}

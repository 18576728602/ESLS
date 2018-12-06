package com.datagroup.ESLS;

import com.datagroup.ESLS.netty.executor.TaskThreadPoolConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class} ) // 开启配置属性支持
@EnableBatchProcessing //springbatch
@EnableScheduling//定时器
public class ESLSApplication {

    public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        // 启动SpringBoot，运行这行代码后才开始Bean注入
        ApplicationContext context = SpringApplication.run(ESLSApplication.class, args);
//        NettyServer nettyServer = context.getBean(NettyServer.class);
//        nettyServer.run();
    }
}

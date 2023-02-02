package com.js.provider.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CuratorConfig {
    //读取环境变量的实例 application.yml
    @Autowired
    private Environment env;

    //自定义注入Bean-ZooKeeper高度封装过的客户端Curator实例
    @Bean
    public CuratorFramework curatorFramework(){
        //创建CuratorFramework实例
        //（1）创建的方式是采用工厂模式进行创建；
        //（2）指定了客户端连接到ZooKeeper服务端的策略：这里是采用重试的机制(5次，每次间隔1s)
        CuratorFramework curatorFramework=
                CuratorFrameworkFactory.builder().connectString(env.getProperty("zk.host")).
                        //从application.yml文件中读取
                                namespace(env.getProperty("zk.namespace"))
                        //重连策略（重连重试次数，重连间隔毫秒
                        .retryPolicy(new RetryNTimes(5,1000)).build();
        curatorFramework.start();
        //返回CuratorFramework实例
        return curatorFramework;
    }
}

package com.js.provider.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonConfig {
    @Autowired
    Environment environment;
    @Bean
    public RedissonClient config(){
        Config config=new Config();
        //这里设置为单一服务器，分布式则使用集群useSentinelServers()
        //读出配置文档中的IP地址和端口
        config.useSingleServer()
                .setAddress(environment.getProperty("redisson.host.config"))
                .setKeepAlive(true)
                .setDatabase(Integer.valueOf(environment.getProperty("redisson.database")));
        return Redisson.create(config);
    }
}

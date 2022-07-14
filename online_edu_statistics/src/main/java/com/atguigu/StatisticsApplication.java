package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Date:2022/7/8
 *
 * @author:yz
 */
//让注册中心能发现我们的这个服务
@EnableDiscoveryClient
//开启feign远程调用
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.atguigu.mapper")
public class StatisticsApplication {
    public static void main(String[] args){
        SpringApplication.run(StatisticsApplication.class,args);
    }

}

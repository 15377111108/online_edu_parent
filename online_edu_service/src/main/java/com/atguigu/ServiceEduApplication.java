package com.atguigu;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Date:2022/6/28
 *
 * @author:yz
 */
@SpringBootApplication
//开启注册中心开关
@EnableDiscoveryClient
//开启远程调用
@EnableFeignClients
@MapperScan("com.atguigu.mapper")
public class ServiceEduApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceEduApplication.class,args);
    }
}

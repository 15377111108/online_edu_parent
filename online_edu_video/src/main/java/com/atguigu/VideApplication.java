package com.atguigu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * Date:2022/7/5
 *
 * @author:yz
 */
//开启注册中心开关
@EnableDiscoveryClient
@SpringBootApplication
public class VideApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideApplication.class,args);
    }
}

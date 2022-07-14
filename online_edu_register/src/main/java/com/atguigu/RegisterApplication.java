package com.atguigu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
//开启Eureka开关
@EnableEurekaServer
@SpringBootApplication
public class RegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class,args);
    }
}

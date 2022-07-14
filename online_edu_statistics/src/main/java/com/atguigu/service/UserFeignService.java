package com.atguigu.service;

import com.atguigu.response.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Date:2022/7/8
 *
 * @author:yz
 */
//声明要调用的那个微服务
@FeignClient(value = "EDU-USER")
public interface UserFeignService {
    @GetMapping("/member/center/queryRegisterNum/{day}")
    public RetVal queryRegisterNum(@PathVariable String day);
}

package com.atguigu.controller;

import com.atguigu.response.RetVal;
import org.springframework.web.bind.annotation.*;

/**
 * Date:2022/6/29
 *
 * @author:yz
 */
@RestController
@RequestMapping("/edu")
//跨域问题
@CrossOrigin
public class EntranceController {
    //1.登录接口
    @PostMapping("/user/login")
    public RetVal login()  {
        return RetVal.success().data("token","admin");
    }
    //2.用户信息
    @GetMapping("/user/info")
    public RetVal info()  {
        return RetVal.success()
                .data("name","admin")
                .data("roles","[admin]")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
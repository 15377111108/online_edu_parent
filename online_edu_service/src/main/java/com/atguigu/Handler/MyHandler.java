package com.atguigu.Handler;

import com.atguigu.config.MyException;
import com.atguigu.response.RetVal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Date:2022/6/29
 *
 * @author:yz
 */
//只要出现了异常,就用这个类去处理
@ControllerAdvice
public class MyHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody//字符串响应成json
    public RetVal error(Exception e){
        //全局异常应该做很多事情
        e.printStackTrace();
        System.out.println("接收到了全局异常");
        return RetVal.error().message("全局异常出现了:"+e.getMessage());
    }
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//字符串响应成json
    public RetVal error(ArithmeticException e){
        //全局异常应该做很多事情
        e.printStackTrace();
        System.out.println("接收到了特殊异常");
        return RetVal.error().message("特殊异常出现了:"+e.getMessage());
    }
    @ExceptionHandler(MyException.class)
    @ResponseBody//字符串响应成json
    public RetVal error(MyException e){
        //全局异常应该做很多事情
        e.printStackTrace();
        System.out.println("接收到了自定义异常");
        return RetVal.error().message(e.getMessage());
    }
}

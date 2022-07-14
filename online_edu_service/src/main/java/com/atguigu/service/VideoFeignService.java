package com.atguigu.service;
import com.atguigu.Handler.VideoServiceHandler;
import com.atguigu.response.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * Date:2022/7/7
 *
 * @author:yz
 */
@FeignClient(value = "EDU-VIDEO",fallback= VideoServiceHandler.class)
public interface VideoFeignService {
    //2.删除单个视频
    @GetMapping("/aliyun/deleteSingleVideo/{videoId}")
    public RetVal deleteSingleVideo(@PathVariable String videoId);
    //3.删除多个视频  RequestParam将请求参数区数据映射到功能处理方法的参数上
    @DeleteMapping("/aliyun/deleteMultiVideo")
    public RetVal deleteMultiVideo(@RequestParam("videoIdList") List<String> videoIdList);
}

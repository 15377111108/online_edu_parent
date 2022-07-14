package com.atguigu.Handler;

import com.atguigu.response.RetVal;
import com.atguigu.service.VideoFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Date:2022/7/7
 *
 * @author:yz
 */
@Component
public class VideoServiceHandler implements VideoFeignService {

    @Override
    public RetVal deleteSingleVideo(String videoId) {
        return RetVal.error().message("返回兜底方法");
    }

    @Override
    public RetVal deleteMultiVideo(List<String> videoIdList) {
        return RetVal.error().message("返回兜底方法");
    }
}

package com.atguigu.controller;


import com.atguigu.response.RetVal;
import com.atguigu.service.DailyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/daily")
@CrossOrigin
public class DailyStatisticsController {
    @Autowired
    private DailyStatisticsService dailyStatisticsService;

    /**
     * 获取指定时期的数据
     * @param day
     * @return
     */
    @GetMapping("generateData/{day}")
    public RetVal generateData(@PathVariable String day){
        dailyStatisticsService.generateData(day);
        return RetVal.success();
    }

    /**
     * 将查到的数据返回
     * @return
     */
    @GetMapping("showStatistics/{dataType}/{beginTime}/{endTime}")
    public RetVal showStatistics(@PathVariable String dataType,
                                 @PathVariable String beginTime,
                                 @PathVariable String endTime){
        Map<String, Object> map = dailyStatisticsService.showStatistics(dataType,beginTime,endTime);
        return RetVal.success().data(map);
  }
}


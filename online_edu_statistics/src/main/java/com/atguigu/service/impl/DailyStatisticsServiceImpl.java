package com.atguigu.service.impl;

import com.atguigu.entity.DailyStatistics;
import com.atguigu.mapper.DailyStatisticsMapper;
import com.atguigu.response.RetVal;
import com.atguigu.service.DailyStatisticsService;
import com.atguigu.service.UserFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-08
 */
@Service
public class DailyStatisticsServiceImpl extends ServiceImpl<DailyStatisticsMapper, DailyStatistics> implements DailyStatisticsService {
    @Autowired
    private UserFeignService userFeignService;
    /**
     * 获取指定时间的数据
     * @param day
     */
    @Override
    public void generateData(String day) {
        //调用微服务查询当天注册数据
        RetVal retVal = userFeignService.queryRegisterNum(day);
        //拿到当天注册的人数
        Integer registerNum = (Integer) retVal.getData().get("registerNum");
        //每日数据统计对象
        DailyStatistics statistics = new DailyStatistics();
        //设置统计日期
        statistics.setDateCalculated(day);
        //写死登录人数
        statistics.setLoginNum(111);
        //视屏播放数
        statistics.setVideoViewNum(222);
        //每日新增课程数
        statistics.setCourseNum(333);
        //就这个调用微服务查到的注册人户
        statistics.setRegisterNum(registerNum);
        //将查到的数据保存到数据库
        baseMapper.insert(statistics);
    }

    /**
     * 将查到的数据返回
     * @param dataType
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Map<String, Object> showStatistics(String dataType, String beginTime, String endTime) {
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<>();
        //当天时间
        wrapper.between("date_calculated", beginTime, endTime);
        //查找当天的数据
        List<DailyStatistics> dailyStatistics = baseMapper.selectList(wrapper);
        //x轴信息(日期) y轴信息(日期所对应的数据)
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (DailyStatistics dailyStatistic : dailyStatistics) {
            //拿到当天的日期
            String singleDay = dailyStatistic.getDateCalculated();
            //将当天的日期加到x轴
            xData.add(singleDay);
            switch (dataType) {
                case "register_num":
                    Integer registerNum = dailyStatistic.getRegisterNum();
                    yData.add(registerNum);
                    break;
                case "login_num":
                    Integer loginNum = dailyStatistic.getLoginNum();
                    yData.add(loginNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = dailyStatistic.getVideoViewNum();
                    yData.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = dailyStatistic.getCourseNum();
                    yData.add(courseNum);
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("xData",xData);
        retMap.put("yData",yData);
        return retMap;
    }
}

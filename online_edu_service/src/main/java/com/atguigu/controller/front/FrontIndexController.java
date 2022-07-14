package com.atguigu.controller.front;

import com.atguigu.entity.EduBanner;
import com.atguigu.entity.EduCourse;
import com.atguigu.entity.EduTeacher;
import com.atguigu.service.EduBannerService;
import com.atguigu.service.EduCourseService;
import com.atguigu.service.EduTeacherService;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/edu/front/")
@CrossOrigin
public class FrontIndexController {
    @Resource
    private EduBannerService bannerService;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
//    @Autowired
//    private RedisTemplate redisTemplate;

    //1.首页banner
//    @GetMapping("getAllBanner")
//    public RetVal getAllBanner() {
//        StringRedisSerializer keySerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(keySerializer);
//
//        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jacksonSerializer.setObjectMapper(om);
//        redisTemplate.setValueSerializer(jacksonSerializer);
//        List<EduBanner> bannerList =(List<EduBanner>) redisTemplate.opsForValue().get("index::banner");
//        if(bannerList==null){
//            bannerList = bannerService.list(null);
//            redisTemplate.opsForValue().set("index::banner",bannerList);
//        }
//        return RetVal.success().data("bannerList",bannerList);
//    }
    //1.首页banner
    //轮回图
    @Cacheable(value = "index",key="'banner'")
    @GetMapping("getAllBanner")
    public RetVal getAllBanner() {
        List<EduBanner> bannerList = bannerService.list(null);
        return RetVal.success().data("bannerList",bannerList);
    }

    //2.热门课程和名师大咖
    @Cacheable(value = "index",key="'teacherCourse'")
    @GetMapping("queryTeacherAndCourse")
    public RetVal queryTeacherAndCourse() {
        //拿到热门课程前8个
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //根据点击数进行降序
        wrapper.orderByDesc("view_count");
        //拿到前8个
        wrapper.last("limit 8");
        //根据这个条件查找
        List<EduCourse> courseList = courseService.list(wrapper);
        //拿到热门老师前4个
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        //根据讲师相序排序
        teacherWrapper.orderByDesc("sort");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);
        return RetVal.success()
                .data("courseList",courseList)
                .data("teacherList",teacherList);
    }

}


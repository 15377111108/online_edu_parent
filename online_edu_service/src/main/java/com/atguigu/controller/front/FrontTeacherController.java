package com.atguigu.controller.front;

import com.atguigu.entity.EduCourse;
import com.atguigu.entity.EduTeacher;
import com.atguigu.response.RetVal;
import com.atguigu.service.EduCourseService;
import com.atguigu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Date:2022/7/9
 *
 * @author:yz
 */
@RestController
@RequestMapping("/edu/front/teacher")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    /**
     * 讲师分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("queryTeacherPage/{pageNum}/{pageSize}")
    public RetVal queryTeacherPage(@PathVariable long pageNum,
                                   @PathVariable long pageSize) {
        Page<EduTeacher> page=new Page(pageNum,pageSize);
        Map<String, Object> retMap=teacherService.queryTeacherPage(page);
        return RetVal.success().data(retMap);
    }

    /**
     * 讲师详情信息查询
     * @param teacherId
     * @return
     */
    @GetMapping("queryTeaherDetailById/{teacherId}")
    public RetVal queryTeaherDetailById(@PathVariable String teacherId) {
        //a.讲师的基本信息
        EduTeacher teacher = teacherService.getById(teacherId);
        //b.讲师授予课程列表
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return RetVal.success().data("teacher",teacher).data("courseList",courseList);
    }

}

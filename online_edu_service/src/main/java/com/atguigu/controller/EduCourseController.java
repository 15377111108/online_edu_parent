package com.atguigu.controller;


import com.atguigu.entity.EduCourse;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.request.CourseCondition;
import com.atguigu.response.CourseConfirmVO;
import com.atguigu.response.RetVal;
import com.atguigu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 保存课程信息
     * @param courseInfo
     * @return
     */
    @PostMapping("saveCourseInfo")
    public RetVal saveCourseInfo(CourseInfoVO courseInfo){
        courseService.saveCourseInfo(courseInfo);
        return RetVal.success();
    }

    /**
     * 根据条件查询课程信息
     * @param pageNum
     * @param pageSize
     * @param courseCondition
     * @return
     */
    @GetMapping("queryCoursePageByCondition/{pageNum}/{pageSize}")
    public RetVal queryCoursePageByCondition(
            @PathVariable("pageNum") long pageNum,
            @PathVariable("pageSize") long pageSize,
            CourseCondition courseCondition) {
        Page<EduCourse> coursePage = new Page<>(pageNum, pageSize);
        courseService.queryCoursePageByCondition(coursePage, courseCondition);
        long total = coursePage.getTotal();
        List<EduCourse> courseList = coursePage.getRecords();
        return RetVal.success().data("total", total).data("courseList", courseList);
    }
    //3.根据课程id查询课程信息
    @GetMapping("{id}")
    public RetVal getCourseById(@PathVariable String id){
        CourseInfoVO courseInfoVo=courseService.getCourseById(id);
        return RetVal.success().data("courseInfoVo",courseInfoVo);
    }
    //4.执行修改课程
    @PutMapping("updateCourseInfo")
    public RetVal updateCourseInfo(CourseInfoVO courseInfoVO){
        courseService.updateCourseInfo(courseInfoVO);
        return RetVal.success();
    }
    //5.查询课程发布确认信息
    @GetMapping("queryCourseConfirmInfo/{courseId}")
    public RetVal queryCourseConfirmInfo(@PathVariable String courseId){
        CourseConfirmVO courseConfirmVo=courseService.queryCourseConfirmInfo(courseId);
        return RetVal.success().data("courseConfirmVo",courseConfirmVo);
    }
    //6.课程的发布
    @GetMapping("publishCourse/{courseId}")
    public RetVal publishCourse(@PathVariable String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return RetVal.success();
    }
    //7.删除课程信息
    @DeleteMapping("deleteCourseById/{courseId}")
    public RetVal deleteCourseById(@PathVariable String courseId){
        courseService.deleleCourse(courseId);
        return RetVal.success();
    }

}


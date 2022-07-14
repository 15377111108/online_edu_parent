package com.atguigu.service;

import com.atguigu.entity.EduCourse;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.request.CourseCondition;
import com.atguigu.response.CourseConfirmVO;
import com.atguigu.response.CourseDetailInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVO courseInfo);

    void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition);

    CourseInfoVO getCourseById(String id);

    void updateCourseInfo(CourseInfoVO courseInfoVO);

    CourseConfirmVO queryCourseConfirmInfo(String courseId);

    void deleleCourse(String courseId);

    CourseDetailInfoVo queryCourseDetailById(String courseId);
}

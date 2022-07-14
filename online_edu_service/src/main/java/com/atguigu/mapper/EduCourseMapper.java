package com.atguigu.mapper;

import com.atguigu.entity.EduCourse;
import com.atguigu.response.CourseConfirmVO;
import com.atguigu.response.CourseDetailInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseConfirmVO queryCourseConfirmInfo(String courseId);

    CourseDetailInfoVo queryCourseDetailById(String courseId);
}

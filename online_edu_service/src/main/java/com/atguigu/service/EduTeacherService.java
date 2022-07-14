package com.atguigu.service;

import com.atguigu.request.TeacherConditionVO;
import com.atguigu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yz
 * @since 2022-06-27
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void queryTeacherPageByCondition(Page<EduTeacher> teacherPage, TeacherConditionVO teacherConditionVO);

    Map<String, Object> queryTeacherPage(Page<EduTeacher> page);
}

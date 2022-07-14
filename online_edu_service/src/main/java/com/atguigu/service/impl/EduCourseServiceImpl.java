package com.atguigu.service.impl;

import com.atguigu.entity.EduChapter;
import com.atguigu.entity.EduCourse;
import com.atguigu.entity.EduCourseDescription;
import com.atguigu.mapper.EduCourseMapper;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.request.CourseCondition;
import com.atguigu.response.CourseConfirmVO;
import com.atguigu.response.CourseDetailInfoVo;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduCourseDescriptionService;
import com.atguigu.service.EduCourseService;
import com.atguigu.service.EduSectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduSectionService sectionService;
    /**
     * 保存课程的基本信息
     * @param courseInfo
     */
    @Transactional
    @Override
    public void saveCourseInfo(CourseInfoVO courseInfo) {

        //c.保存课程基本信息
        //创建课程对象
        EduCourse course = new EduCourse();
        //批量设置前端传过来的属性给创建的课程对象
        BeanUtils.copyProperties(courseInfo,course);
        baseMapper.insert(course);
        //d.保存课程描述信息
        EduCourseDescription description = new EduCourseDescription();
        // e.课程基本信息和课程描述表共用一个主键
        description.setId(course.getId());
        //保存前端的课程简介
        description.setDescription(courseInfo.getDescription());
        descriptionService.save(description);
    }

    /**
     * 带条件查询信息
     * @param coursePage
     * @param courseCondition
     */
    @Override
    public void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition) {
        //获取每个查询参数
        String title = courseCondition.getTitle();
        String status = courseCondition.getStatus();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断以上传递过来的参数是否为空
        if(StringUtils.isNotEmpty(title)){
            wrapper.like("title",title);
        }

        if(StringUtils.isNotEmpty(status)){
            wrapper.ge("status",status);
        }
        baseMapper.selectPage(coursePage,wrapper);
    }

    /**
     * 根据id查询课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoVO getCourseById(String id) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        //课程基本信息
        EduCourse course = baseMapper.selectById(id);
        BeanUtils.copyProperties(course,courseInfoVO);
        //课程详情信息
        EduCourseDescription description = descriptionService.getById(id);
        if(description!=null){
            courseInfoVO.setDescription(description.getDescription());
        }
        return courseInfoVO;
    }

    /**
     * 修改课程信息
     * @param courseInfoVO
     */
    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        //c.保存课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,course);
        baseMapper.updateById(course);
        //d.保存课程描述信息
        EduCourseDescription description = new EduCourseDescription();
        //e.课程基本信息和课程描述表共用一个主键
        description.setId(courseInfoVO.getId());
        description.setDescription(courseInfoVO.getDescription());
        descriptionService.updateById(description);
    }

    /**
     * 查询课程发布信息
     * @param courseId
     * @return
     */
    @Override
    public CourseConfirmVO queryCourseConfirmInfo(String courseId) {
        return baseMapper.queryCourseConfirmInfo(courseId);
    }

    /**
     * 删除课程信息
     * @param courseId
     */
    @Override
    public void deleleCourse(String courseId) {
        //删除章节
        chapterService.deleteChapterByCourseId(courseId);
        //删除小节
        sectionService.deleteSectionByCourseId(courseId);
        //删除课程的基本信息
        baseMapper.deleteById(courseId);
        //删除课程的描述信息
        descriptionService.removeById(courseId);

    }
    /**
     * 课程列表和课程详情信息
     * @param courseId
     * @return
     */
    @Override
    public CourseDetailInfoVo queryCourseDetailById(String courseId) {
        return baseMapper.queryCourseDetailById(courseId);
    }
}

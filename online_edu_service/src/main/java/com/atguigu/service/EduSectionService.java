package com.atguigu.service;

import com.atguigu.entity.EduSection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程小节 服务类
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
public interface EduSectionService extends IService<EduSection> {

    void addSection(EduSection section);

    void deleteSection(String id);

    void deleteSectionByCourseId(String courseId);
}

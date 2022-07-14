package com.atguigu.service;

import com.atguigu.entity.EduSubject;
import com.atguigu.response.SubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yz
 * @since 2022-07-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void uploadSubject(MultipartFile file) throws IOException;

    EduSubject existSubject(String parentSubjectName, String parentId);


    List<SubjectVO> getAllSubject();

    boolean deleteSubjectById(String id);

    boolean saveParentSubject(EduSubject subject);

    boolean saveChildSubject(EduSubject subject);
}

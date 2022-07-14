package com.atguigu.controller;


import com.atguigu.entity.EduSubject;
import com.atguigu.response.SubjectVO;
import com.atguigu.service.EduSubjectService;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-07-01
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("uploadSubject")
    public RetVal uplaodSubject(MultipartFile file) throws IOException {
        eduSubjectService.uploadSubject(file);
        return RetVal.success();
    }
    /**
     * 展示文件
     */
    @GetMapping("getAllSubject")
    public RetVal getAllSubject() throws IOException {
        List<SubjectVO> subjectVOList = eduSubjectService.getAllSubject();
        return RetVal.success().data("subjectVOList",subjectVOList);
    }
    /**
     * 删除文件
     */
    @DeleteMapping ("{id}")
    public RetVal deleteSubjectById(@PathVariable String id) throws IOException {
        boolean flag = eduSubjectService.deleteSubjectById(id);
        if (flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
    //4.保存一级分类信息
    @PostMapping("saveParentSubject")
    public RetVal saveParentSubject(EduSubject subject) {
        boolean flag=eduSubjectService.saveParentSubject(subject);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
    //5.保存二级分类信息
    @PostMapping("saveChildSubject")
    public RetVal saveChildSubject(EduSubject subject) {
        boolean flag=eduSubjectService.saveChildSubject(subject);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
}


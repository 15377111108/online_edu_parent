package com.atguigu.controller;


import com.atguigu.entity.EduSection;
import com.atguigu.response.RetVal;
import com.atguigu.service.EduSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程简介 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/edu-course-description")
public class EduCourseDescriptionController {
    @Autowired
    private EduSectionService sectionService;
    //添加小节
    @PostMapping
    public RetVal addSection(EduSection section){
        sectionService.addSection(section);
        return RetVal.success();
    }
    // 删除小节
    @DeleteMapping("{id}")
    public RetVal deleteSection(@PathVariable String id){
        sectionService.deleteSection(id);
        return RetVal.success();
    }
    //更新小节
    @PutMapping
    public RetVal updateSection(EduSection section){
        sectionService.updateById(section);
        return RetVal.success();
    }
    //根据id查询小节
    @GetMapping("{id}")
    public RetVal getSectionById(@PathVariable String id){
        EduSection section = sectionService.getById(id);
        return RetVal.success().data("section",section);
    }
}


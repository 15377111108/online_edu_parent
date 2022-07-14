package com.atguigu.controller;
import com.atguigu.request.TeacherConditionVO;
import com.atguigu.entity.EduTeacher;
import com.atguigu.service.EduTeacherService;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yz
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有教师
     * @return
     */
    @GetMapping
    public RetVal getAllTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        //返回同意封装的结果集
        //返回成功的显示和数据
        return RetVal.success().data("teacherList",teacherList);
    }
    /**
     * 删除教师
     */
    @DeleteMapping("{id}")
    //@ApiParam:告诉swagger这个id是对应的什么
    // required = true必须要有的
    public RetVal deleteTeacherById(@ApiParam(name = "id",value = "讲师id",required = true)
                                    @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }
    /**
     * 讲师列表分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("queryTeacherByPage/{pageNum}/{pageSize}")
    public RetVal queryTeacherByPage(@PathVariable Long pageNum,
                                     @PathVariable Long pageSize){
        Page<EduTeacher> teacherPage = new Page<>(pageNum,pageSize);
        eduTeacherService.page(teacherPage,null);
        //查询结果
        List<EduTeacher> teacherList = teacherPage.getRecords();
        //总记录数
        long total = teacherPage.getTotal();
        //返回成功的显示和数据
        return RetVal.success().data("teacherList",teacherList).
                data("total",total);
    }

    /**
     * 讲师列表分页查询带条件
     * 根据查询条件把条件封装到一个类里面,放到参数里面接收
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("queryTeacherPageByCondition/{pageNum}/{pageSize}")
    public RetVal queryTeacherPageByCondition(@PathVariable Long pageNum,
                                              @PathVariable Long pageSize,
                                              TeacherConditionVO teacherConditionVO){
        Page<EduTeacher> teacherPage = new Page<>(pageNum,pageSize);
        eduTeacherService.queryTeacherPageByCondition(teacherPage,teacherConditionVO);
        //查到的结果
        List<EduTeacher> teacherList = teacherPage.getRecords();
        //总记录数
        long total = teacherPage.getTotal();

        return RetVal.success().data("teacherList",teacherList).
                data("total",total);
    }

    /**
     * 添加教师
     * @param teacher
     * @return
     */
    @PostMapping
    public RetVal saveTeacher(EduTeacher teacher){
        boolean flag = eduTeacherService.save(teacher);
        if (flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }

    /**
     * 修改教师
     */
    @PutMapping
    public RetVal updateTeacher(EduTeacher teacher) {
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return RetVal.success();
        }else {
            return RetVal.error();
        }
    }

    /**
     * 根据id查询教师
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public RetVal queryTeacherById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        //返回成功的显示和数据
        return RetVal.success().data("teacher",teacher);
    }
}


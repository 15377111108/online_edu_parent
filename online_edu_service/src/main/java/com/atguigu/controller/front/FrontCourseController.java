package com.atguigu.controller.front;

import com.atguigu.response.ChapterVO;
import com.atguigu.response.CourseDetailInfoVo;
import com.atguigu.response.RetVal;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Date:2022/7/10
 *
 * @author:yz
 */
@RestController
@RequestMapping("/edu/front/course")
@CrossOrigin
public class FrontCourseController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    /**
     * 课程列表和课程详情信息
     * @param courseId
     * @return
     */
    @GetMapping("queryCourseDetailById/{courseId}")
    public RetVal queryCourseDetailById(@PathVariable String courseId) {
        //a.章节小节信息
        List<ChapterVO> chapterAndSectionList = chapterService.getChapterAndSection(courseId);
        //b.课程详情其余信息
        CourseDetailInfoVo courseDetailInfoVo=courseService.queryCourseDetailById(courseId);
        return RetVal.success()
                .data("chapterAndSectionList",chapterAndSectionList)
                .data("courseDetailInfoVo",courseDetailInfoVo);
    }
}

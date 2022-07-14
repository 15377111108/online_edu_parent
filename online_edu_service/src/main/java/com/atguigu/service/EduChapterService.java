package com.atguigu.service;

import com.atguigu.entity.EduChapter;
import com.atguigu.response.ChapterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
public interface EduChapterService extends IService<EduChapter> {

    boolean saveChapter(EduChapter chapter);

    boolean deleteChapter(String chapterId);

    List<ChapterVO> getChapterAndSection(String courseId);

    void deleteChapterByCourseId(String courseId);

}

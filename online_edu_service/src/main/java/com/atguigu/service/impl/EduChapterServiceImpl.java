package com.atguigu.service.impl;

import com.atguigu.config.MyException;
import com.atguigu.entity.EduChapter;
import com.atguigu.entity.EduSection;
import com.atguigu.mapper.EduChapterMapper;
import com.atguigu.response.ChapterVO;
import com.atguigu.response.SectionVO;
import com.atguigu.service.EduChapterService;
import com.atguigu.service.EduSectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduSectionService sectionService;

    /**
     * 保存章节信息
     * @param chapter
     * @return
     */
    @Override
    public boolean saveChapter(EduChapter chapter) {
        //判断是否存在
        EduChapter existChapter = existChapter(chapter.getCourseId(), chapter.getTitle());
        if(existChapter==null){
            int insert = baseMapper.insert(chapter);
            return insert>0;
        }else{
            throw new MyException(20001,"章节已经重复");
        }
    }

    public EduChapter existChapter(String courseId, String chapterName) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("title", chapterName);
        EduChapter chapter = baseMapper.selectOne(queryWrapper);
        return chapter;

    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //判断是否有小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = sectionService.count(queryWrapper);
        //没有小节
        if(count==0){
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }else{
            throw new MyException(20001,"该章节存在小节");
        }
    }

    @Override
    public List<ChapterVO> getChapterAndSection(String courseId) {
        //b.查询所有的章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapper);
        //c.查询所有的小节
        QueryWrapper<EduSection> sectionWrapper = new QueryWrapper<>();
        sectionWrapper.eq("course_id",courseId);
        List<EduSection> sectionList = sectionService.list(sectionWrapper);
        //d.把小节封装到章节里面去
        List<ChapterVO> chapterVoList=new ArrayList<>();
        for (EduChapter chapter : chapterList) {
            ChapterVO chapterVo = new ChapterVO();
            BeanUtils.copyProperties(chapter,chapterVo);
            //判断条件 section里面的chapter_id==chapter.id
            for (EduSection section : sectionList) {
                SectionVO sectionVo = new SectionVO();
                BeanUtils.copyProperties(section,sectionVo);
                if(chapter.getId().equals(section.getChapterId())){
                    chapterVo.getChildren().add(sectionVo);
                }
            }
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}

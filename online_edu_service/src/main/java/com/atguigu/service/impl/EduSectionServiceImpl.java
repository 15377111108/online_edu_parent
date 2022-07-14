package com.atguigu.service.impl;

import com.atguigu.config.MyException;
import com.atguigu.entity.EduSection;
import com.atguigu.mapper.EduSectionMapper;
import com.atguigu.service.EduSectionService;
import com.atguigu.service.VideoFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
@Service
public class EduSectionServiceImpl extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {
//    @Qualifier("com.atguigu.service.VideoFeignService")
    @Resource
   private VideoFeignService videoFeignService;
    //添加小节
    @Override
    public void addSection(EduSection section) {
        //1.判断是否存在小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",section.getCourseId());
        queryWrapper.eq("chapter_id",section.getChapterId());
        queryWrapper.eq("title",section.getTitle());
        EduSection existSection = baseMapper.selectOne(queryWrapper);
        if(existSection==null){
            baseMapper.insert(section);
        }else{
            throw new MyException(20001,"存在重复的小节");
        }
    }
    //删除小节
    @Override
    public void deleteSection(String id) {
        //根据小节id拿到视频id
        EduSection section = baseMapper.selectById(id);
        String videoSourceId = section.getVideoSourceId();
        if (StringUtils.isNotEmpty(videoSourceId)){
            //删除视频调用微服务删除视频
            videoFeignService.deleteSingleVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }
    //删除课程
    @Override
    public void deleteSectionByCourseId(String courseId) {
        QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        //根据id查找该课程所有的小节
        List<EduSection> sectionList = baseMapper.selectList(wrapper);
        //装小节视频的id集合
        ArrayList<String> list = new ArrayList<>();
        for (EduSection section : sectionList) {
            //遍历小节,拿到所有小节的视频id
            String videoSourceId = section.getVideoSourceId();
            //如果id不为空,就添加到集合
            if (StringUtils.isNotEmpty(videoSourceId)){
              list.add(videoSourceId);
            }
            //删除多个视频调用的微服务
            videoFeignService.deleteMultiVideo(list);
            //最后删除小节信息
            baseMapper.delete(wrapper);
        }
    }
}

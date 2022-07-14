package com.atguigu.service.impl;

import com.atguigu.request.TeacherConditionVO;
import com.atguigu.entity.EduTeacher;
import com.atguigu.mapper.EduTeacherMapper;
import com.atguigu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author yz
 * @since 2022-06-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void queryTeacherPageByCondition(Page<EduTeacher> teacherPage,
                                            TeacherConditionVO teacherConditionVO) {
        //查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        /**
         * 查询的条件
         * teacherName:教师姓名
         * level:头衔
         * beginTime:开始时间
         * endTime:结束时间
         */
        String teacherName = teacherConditionVO.getName();
        Integer level = teacherConditionVO.getLevel();
        String beginTime = teacherConditionVO.getBeginTime();
        String endTime = teacherConditionVO.getEndTime();
        //判断条件是否为空
        if(StringUtils.isNotEmpty(teacherName)){
            wrapper.like("name", teacherName);
        }
        if(level!=null){
            wrapper.eq("level",level);
        }
        //大于
        if(StringUtils.isNotEmpty(beginTime)){
            wrapper.ge("gmt_create", beginTime);
        }
        //小于
        if(StringUtils.isNotEmpty(endTime)){
            wrapper.le("gmt_create", endTime);
        }
        wrapper.orderByDesc("gmt_create");
        //根据条件查询,展开分页
        baseMapper.selectPage(teacherPage,wrapper);
    }

    /**
     * 根据条件分页查询
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> queryTeacherPage(Page<EduTeacher> teacherPage) {
        baseMapper.selectPage(teacherPage,null);
        /**
         * 前端需要展示的数据
         * hasPrevious:有没有上一页
         * hasNext:有没有下一页
         * getCurrent:当前页
         * getSize:每页数据长度
         * getTotal:总计数据
         * getRecords:所有记录
         */
        boolean hasPrevious = teacherPage.hasPrevious();
        boolean hasNext = teacherPage.hasNext();
        long currentPage = teacherPage.getCurrent();
        long totalPages = teacherPage.getPages();
        long size = teacherPage.getSize();
        long total = teacherPage.getTotal();
        List<EduTeacher> teacherList = teacherPage.getRecords();

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("teacherList", teacherList);
        retMap.put("pages", totalPages);
        retMap.put("total", total);
        retMap.put("currentPage", currentPage);
        retMap.put("size", size);
        retMap.put("hasNext", hasNext);
        retMap.put("hasPrevious", hasPrevious);
        return retMap;
    }


}

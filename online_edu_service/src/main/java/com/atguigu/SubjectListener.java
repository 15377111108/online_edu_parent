package com.atguigu;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.entity.EduSubject;
import com.atguigu.entity.SubjectExcel;
import com.atguigu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Date:2022/7/2
 *
 * @author:yz
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectExcel> {
    @Autowired
    private EduSubjectService subjectService;
    /**
     * 一行一行的读
     * @param subjectExcel
     * @param analysisContext
     */
    @Transactional
    @Override
    public void invoke(SubjectExcel subjectExcel, AnalysisContext analysisContext) {
        //拿到一级分类
        String parentSubjectName = subjectExcel.getParentSubjectName();
        //根据条件查询
        /**
         * 保存一级分类
         */
        EduSubject parentSubject = subjectService.existSubject(parentSubjectName, "0");
        //判断该分类是否存在
        if (parentSubject==null){
            parentSubject= new EduSubject();
            parentSubject.setTitle(parentSubjectName);
            parentSubject.setParentId("0");
            subjectService.save(parentSubject);
        }
        String childSubjectName = subjectExcel.getChildSubjectName();
        EduSubject childSubject = subjectService.existSubject(childSubjectName, parentSubject.getId());
        /**
         * 保存二级分类
         */
        if (childSubject==null){
            childSubject=new EduSubject();
            childSubject.setTitle(childSubjectName);
            childSubject.setParentId(parentSubject.getId());
            subjectService.save(childSubject);
        }
    }

    /**
     * 读完后执行什么操作
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

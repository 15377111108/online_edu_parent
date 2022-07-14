package com.atguigu.excel_test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
public class ReadExcelListener extends AnalysisEventListener<Student> {
    /**
     * 一行一行的读
     * @param student
     * @param analysisContext
     */
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println(student);
    }

    /**
     * 读了之后进行什么操作
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("已读完毕!!!");
    }
}

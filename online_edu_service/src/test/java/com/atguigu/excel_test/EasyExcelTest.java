package com.atguigu.excel_test;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;


import java.util.ArrayList;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
public class EasyExcelTest {
    /**
     * 写入代码
     */
    @Test
    public void writeTest() {
        //表格路径
        String path ="F:\\01.xls";
        ArrayList<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setId(i+1);
            student.setName("袁振"+i);
            students.add(student);
        }
        //c.往那个表单上面写数据
        EasyExcel.write(path,Student.class).sheet("sheet1").doWrite(students);
    }
    /**
     * 读表格
     */
    @Test
    public void readTest() {
        //读哪个表格
        String path ="F:\\01.xls";
        EasyExcel.read(path,Student.class,new ReadExcelListener()).doReadAll();
    }
}

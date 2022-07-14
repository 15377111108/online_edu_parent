package com.atguigu.excel_test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Date:2022/7/1
 *
 * @author:yz
 */
@Data
public class Student {
    //设置表格字段和先后顺序
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer id;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}

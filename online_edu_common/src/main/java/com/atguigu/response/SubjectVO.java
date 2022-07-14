package com.atguigu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2022/7/3
 *
 * @author:yz
 */
@Data
public class SubjectVO {
    @ApiModelProperty(value = "课程类别ID")
    private String id;
    @ApiModelProperty(value = "课程分类名称")
    private String title;
    @ApiModelProperty(value = "课程分类子集合")
    private List<SubjectVO> children=new ArrayList<>();
}
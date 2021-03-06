package com.atguigu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2022/7/4
 *
 * @author:yz
 */
@Data
public class ChapterVO {
    @ApiModelProperty(value = "章节ID")
    private String id;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "章节里面的小节")
    private List<SectionVO> children=new ArrayList<>();
}

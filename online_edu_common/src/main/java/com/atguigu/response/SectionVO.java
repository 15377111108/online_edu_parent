package com.atguigu.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Date:2022/7/4
 *
 * @author:yz
 */
@Data
public class SectionVO {
    @ApiModelProperty(value = "小节ID")
    private String id;

    @ApiModelProperty(value = "小节名称")
    private String title;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;

    @ApiModelProperty(value = "冗余字段")
    private String videoOriginalName;
}

package com.atguigu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 课程小节
 * </p>
 *
 * @author yz
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_section")
@ApiModel(value="EduSection对象", description="课程小节")
public class EduSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小节ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "播放次数")
    private Long playCount;

    @ApiModelProperty(value = "是否可以试听：0免费 1收费")
    private Integer isFree;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;
    @ApiModelProperty(value = "冗余字段")
    private String videoOriginalName;

    @ApiModelProperty(value = "视频时长（秒）")
    private Float duration;

    @ApiModelProperty(value = "视频状态:见阿里云文档")
    private String status;

    @ApiModelProperty(value = "视频源文件大小（字节）")
    private Long size;

    @ApiModelProperty(value = "乐观锁")
    private Long version;
    @TableField(fill= FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @TableField(fill= FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;




}

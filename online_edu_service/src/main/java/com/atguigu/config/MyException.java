package com.atguigu.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date:2022/6/29
 *
 * @author:yz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException {
    @ApiModelProperty(value = "异常coe")
    private Integer code;
    @ApiModelProperty(value = "异常信息")
    private String message;
}

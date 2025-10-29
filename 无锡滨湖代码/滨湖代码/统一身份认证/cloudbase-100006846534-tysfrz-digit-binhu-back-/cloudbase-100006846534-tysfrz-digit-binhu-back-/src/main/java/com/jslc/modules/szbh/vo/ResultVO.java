package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 实时数据实体类
 *
 * @author BladeX
 * @since 2020-10-20
 */
@Data
@ApiModel(value = "状态码与信息VO", description = "状态码与信息VO")
public class ResultVO {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    String code;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息")
    String message;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    Object data;
}

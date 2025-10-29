package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "名称值 通用map实体", description = "名称值 通用map实体")
public class NVMapVO {



    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;


    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private Integer value;


}

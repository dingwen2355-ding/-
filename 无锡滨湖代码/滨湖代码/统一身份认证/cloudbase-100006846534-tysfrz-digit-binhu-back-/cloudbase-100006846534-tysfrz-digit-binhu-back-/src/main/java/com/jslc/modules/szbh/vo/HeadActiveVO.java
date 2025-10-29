package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "活跃趋势", description = "活跃趋势")
public class HeadActiveVO {



    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private List time;


    /**
     * 新增数量
     */
    @ApiModelProperty(value = "新增数量")
    private List createUserNum;

    /**
     * 新增数量
     */
    @ApiModelProperty(value = "新增数量")
    private List loginNum;


}

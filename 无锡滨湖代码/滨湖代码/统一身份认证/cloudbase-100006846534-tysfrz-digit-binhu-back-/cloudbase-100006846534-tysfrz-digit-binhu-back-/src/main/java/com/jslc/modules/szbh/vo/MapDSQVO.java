package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "都市圈通用map", description = "都市圈通用map")
public class MapDSQVO {



    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private String num;


}

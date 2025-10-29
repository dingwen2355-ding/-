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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "首页", description = "首页")
public class SmsNumberVO {



    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer number;


}

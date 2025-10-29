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
@ApiModel(value = "应用分类统计", description = "应用分类统计")
public class AppClassificationStatisticsVO {



    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 应用数量
     */
    @ApiModelProperty(value = "应用数量")
    private Integer appNumber;


    /**
     * 访问次数
     */
    @ApiModelProperty(value = "访问次数")
    private Integer accessNumber;

}

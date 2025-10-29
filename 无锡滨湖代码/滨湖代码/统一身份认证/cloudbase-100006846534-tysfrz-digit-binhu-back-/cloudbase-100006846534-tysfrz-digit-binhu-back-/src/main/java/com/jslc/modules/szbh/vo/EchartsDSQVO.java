package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "都市圈echarts图表", description = "都市圈echarts图表")
public class EchartsDSQVO {


    /**
     * x轴
     */
    @ApiModelProperty(value = "x轴")
    private List x;
    /**
     * y轴
     */
    @ApiModelProperty(value = "y轴")
    private List y;




}

package cn.wxgis.jc.screen.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventPointsAvgVo {

    @ApiModelProperty(value = "点位名称")
    private String pointName;

    @ApiModelProperty(value = "事件总数")
    private BigDecimal count;

    @ApiModelProperty(value = "整改数")
    private BigDecimal endCount;

    @ApiModelProperty(value = "整改率")
    private BigDecimal avgCount;

    @ApiModelProperty(value = "区划")
    private String regionName;
}

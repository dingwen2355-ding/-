package cn.wxgis.jc.screen.domain.vo.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value = "EventHeatMapVo", description = "事件热力图信息")
@Data
public class EventHeatMapVo {

    @ApiModelProperty(value = "事件id")
    private String id;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

}

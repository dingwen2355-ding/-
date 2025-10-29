package cn.wxgis.jc.screen.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventTypeAvgVo {

    @ApiModelProperty(value = "事件类型名称")
    private String eventType;

    @ApiModelProperty(value = "占比")
    private BigDecimal avg;
}

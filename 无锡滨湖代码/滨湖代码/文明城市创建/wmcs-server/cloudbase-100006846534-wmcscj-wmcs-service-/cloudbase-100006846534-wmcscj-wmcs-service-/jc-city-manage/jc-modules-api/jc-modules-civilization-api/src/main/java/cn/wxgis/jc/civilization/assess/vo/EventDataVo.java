package cn.wxgis.jc.civilization.assess.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EventDataVo {

    @ApiModelProperty(value = "总监测")
    List<EventTypeCountVo> eventType;

    @ApiModelProperty(value = "趋势")
    Map<String, List<EventTypeCountVo>> cycleMap;
}

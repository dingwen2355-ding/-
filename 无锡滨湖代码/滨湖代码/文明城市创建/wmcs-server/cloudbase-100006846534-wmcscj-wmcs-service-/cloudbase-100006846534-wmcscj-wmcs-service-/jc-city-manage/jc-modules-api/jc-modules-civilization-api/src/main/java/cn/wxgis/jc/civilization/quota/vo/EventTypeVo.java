package cn.wxgis.jc.civilization.quota.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventTypeVo {

    @ApiModelProperty(value = "告警类型")
    private String warnType;

    @ApiModelProperty(value = "事件类型")
    private String eventType;

    @ApiModelProperty(value = "事件类型名称")
    private String eventTypeName;

    @ApiModelProperty(value = "事件小类")
    private String eventTypeTwo;

    @ApiModelProperty(value = "事件小类名称")
    private String eventTypeTwoName;

    @ApiModelProperty(value = "事件大类")
    private String eventTypeOne;

    @ApiModelProperty(value = "事件大类名称")
    private String eventTypeOneName;
}

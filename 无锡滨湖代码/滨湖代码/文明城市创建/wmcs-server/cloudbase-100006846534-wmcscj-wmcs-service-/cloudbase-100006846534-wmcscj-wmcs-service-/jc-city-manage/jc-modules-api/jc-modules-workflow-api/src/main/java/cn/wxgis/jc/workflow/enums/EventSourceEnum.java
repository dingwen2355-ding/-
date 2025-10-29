package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventSourceEnum {

    REPORT(1,"日常巡查"),
    AI(2,"智能AI"),
    SPECIAL(3,"专班英文"),
    CITY(4,"市级下发"),
    HISTORY(5,"历史工单");

    private Integer code;
    private String value;

}

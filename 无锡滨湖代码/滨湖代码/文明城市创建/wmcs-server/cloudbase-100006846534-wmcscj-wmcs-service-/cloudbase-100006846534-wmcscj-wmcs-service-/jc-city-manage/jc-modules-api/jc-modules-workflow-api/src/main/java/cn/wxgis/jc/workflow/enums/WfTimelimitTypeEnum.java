package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点处置时限类型
 */
@Getter
@AllArgsConstructor
public enum WfTimelimitTypeEnum {

    MINUTE(0,"按分"),
    DAY(1,"按日"),
    HOUR(2,"按时");

    private Integer code;
    private String value;

}

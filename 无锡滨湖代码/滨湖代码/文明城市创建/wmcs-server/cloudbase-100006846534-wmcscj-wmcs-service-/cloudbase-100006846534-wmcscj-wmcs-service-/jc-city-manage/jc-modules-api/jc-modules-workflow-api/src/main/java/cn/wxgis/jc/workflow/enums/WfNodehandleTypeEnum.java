package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点处理类型
 */
@Getter
@AllArgsConstructor
public enum WfNodehandleTypeEnum {

    SINGLE_SIGNOFF(1,"单一签核"),
    MULTIPLE_SINGLE_SIGNOFF(2,"多人单一签核"),
    MULTIPLE_PROCESSING(3,"多人处理"),
    MULTIPLE_PROCESSING_ORDER(4,"多人顺序");

    private Integer code;
    private String value;
}

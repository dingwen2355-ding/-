package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 延期状态枚举类
 */
@Getter
@AllArgsConstructor
public enum WfEventExamineStatusEnum {

    DEFAULT(0,"默认"),
    PASS(1,"通过"),
    REJECT(2, "驳回");

    private Integer code;
    private String value;

}

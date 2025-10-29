package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点类型
 */
@Getter
@AllArgsConstructor
public enum WfRouteTypeEnum {

    SUBMIT(1,"提交"),
    REJECT(2,"驳回");

    private Integer code;
    private String value;
}

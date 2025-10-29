package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点类型
 */
@Getter
@AllArgsConstructor
public enum EventInfoFlagEnum {

    SUCCESS("1","成功"),
    SELECT_USER("2","选人"),
    ERROR("3","流程错误");

    private String code;
    private String value;
}

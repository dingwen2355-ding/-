package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点类型
 */
@Getter
@AllArgsConstructor
public enum WfNodeTypeEnum {

    START(1,"开始"),
    PROCESS(2,"过程"),
    END(3,"结束");

    private Integer code;
    private String value;

}

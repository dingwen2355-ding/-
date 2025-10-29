package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 节点类型
 */
@Getter
@AllArgsConstructor
public enum WfOperateauthorEnum {

    SUBMIT(1,"提交"),
    REJECT(2,"退回"),
    GETBACK(3,"拿回");

    private Integer code;
    private String value;

}

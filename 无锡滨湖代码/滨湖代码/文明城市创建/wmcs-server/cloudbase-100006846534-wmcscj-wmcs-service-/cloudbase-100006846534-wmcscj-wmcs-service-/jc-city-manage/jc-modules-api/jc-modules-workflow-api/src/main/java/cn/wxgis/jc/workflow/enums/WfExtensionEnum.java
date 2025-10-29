package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WfExtensionEnum {

    DEFAULT(0,"默认/已拒绝"),
    APPLY(1,"申请中"),
    PASS(2, "已通过");

    private Integer code;
    private String value;

}

package cn.wxgis.jc.workflow.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WfEndTypeEnum {

    PEOPLE(1,"人工办结"),
    AUTO(2,"自动办结");

    private Integer code;
    private String value;

}


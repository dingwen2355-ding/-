package cn.wxgis.jc.workflow.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventOptTagEnum {

    SHOULI("shouli","受理"),
    CHUZHI("chuzhi","处置"),
    HECHA("hecha","核查");

    private String code;
    private String value;
}

package cn.wxgis.jc.common.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单所属平台
 */
@Getter
@AllArgsConstructor
public enum MenuPlatformEnum {

    PC(1, "PC"),
    APP(2, "APP");

    private final Integer code;
    private final String value;

}

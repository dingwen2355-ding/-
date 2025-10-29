package cn.wxgis.jc.system.auth.enums;

import cn.wxgis.jc.common.enums.system.MenuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 鉴权类型
 */
@Getter
@AllArgsConstructor
public enum AuthorizationTypeEnum {

    PC(1, "PC"),
    SCREEN(2, "大屏");

    private final Integer code;
    private final String value;

}

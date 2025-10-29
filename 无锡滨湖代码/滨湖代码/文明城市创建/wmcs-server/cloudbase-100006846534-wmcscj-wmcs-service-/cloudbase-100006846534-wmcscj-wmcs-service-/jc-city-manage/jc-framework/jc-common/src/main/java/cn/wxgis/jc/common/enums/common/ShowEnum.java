package cn.wxgis.jc.common.enums.common;

import cn.wxgis.jc.common.enums.system.MenuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * 是否显示
 */
@Getter
@AllArgsConstructor
public enum ShowEnum {

    /**
     * 显示
     */
    SHOW(0, "显示"),
    /**
     * 不显示
     */
    UNSHOW(1, "不显示");

    private final Integer code;
    private final String value;

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> list = new ArrayList<>();
        for (MenuTypeEnum menuType: EnumSet.allOf(MenuTypeEnum.class)) {
            Map<String, String> map = new HashMap<>();
            map.put("code", menuType.getCode());
            map.put("value", menuType.getValue());
            list.add(map);
        }
        return list;
    }

}

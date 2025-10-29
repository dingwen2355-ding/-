package cn.wxgis.jc.common.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * 菜单类型
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    CATALOG("M", "目录"),
    MENU("C", "菜单"),
    DELETED("F", "功能");

    private final String code;
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

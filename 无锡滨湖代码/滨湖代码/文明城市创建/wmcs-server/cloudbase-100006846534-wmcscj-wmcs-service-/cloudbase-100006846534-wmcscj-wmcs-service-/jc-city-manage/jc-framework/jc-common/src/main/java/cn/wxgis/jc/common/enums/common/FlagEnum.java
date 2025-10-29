package cn.wxgis.jc.common.enums.common;

import cn.wxgis.jc.common.enums.system.MenuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * 开启枚举类
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {

    FALSE(0, "否"),
    TRUE(1, "是");

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

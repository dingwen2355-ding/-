package cn.wxgis.jc.common.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum DeptTypeEnum {

    DEPT(1, "职能部门"),
    REGION(2, "区划部门");

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

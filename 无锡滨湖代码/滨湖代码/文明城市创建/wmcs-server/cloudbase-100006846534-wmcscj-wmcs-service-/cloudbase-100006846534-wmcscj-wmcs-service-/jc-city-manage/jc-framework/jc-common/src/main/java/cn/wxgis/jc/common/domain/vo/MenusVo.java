package cn.wxgis.jc.common.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MenusVo {

    /**
     * 枚举类名称
     */
    private String name;

    /**
     * 枚举类key
     */
    private String menuKey;

    /**
     * 枚举类值
     */
    private List<Map<String, String>> menuValue;

    public MenusVo(){}
    public MenusVo(String name, String menuKey, List<Map<String, String>> menuValue) {
        this.name = name;
        this.menuKey = menuKey;
        this.menuValue = menuValue;
    }

}

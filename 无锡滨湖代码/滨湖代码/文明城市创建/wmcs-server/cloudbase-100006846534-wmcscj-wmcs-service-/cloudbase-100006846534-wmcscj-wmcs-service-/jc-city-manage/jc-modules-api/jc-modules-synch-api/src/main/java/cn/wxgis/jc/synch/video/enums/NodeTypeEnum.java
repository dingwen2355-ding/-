package cn.wxgis.jc.synch.video.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeTypeEnum {

    /**
     * 正常
     */
    TREE(1, "组织"),
    /**
     * 删除
     */
    DEVICE(2,"设备"),

    CHANNEL(3, "通道");

    private final Integer code;
    private final String value;
}

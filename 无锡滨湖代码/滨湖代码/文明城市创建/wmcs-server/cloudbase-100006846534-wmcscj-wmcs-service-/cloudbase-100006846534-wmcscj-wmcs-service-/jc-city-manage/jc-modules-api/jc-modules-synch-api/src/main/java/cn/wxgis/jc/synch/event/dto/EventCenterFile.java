package cn.wxgis.jc.synch.event.dto;

import lombok.Data;

/**
 * 文件实体类
 */
@Data
public class EventCenterFile {

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件地址
     */
    private String content;

    /**
     * 文件后缀
     */
    private String suffix;
}

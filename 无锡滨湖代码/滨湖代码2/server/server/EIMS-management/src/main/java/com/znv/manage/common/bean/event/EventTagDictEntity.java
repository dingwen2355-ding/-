package com.znv.manage.common.bean.event;

import lombok.Data;

/**
 * @author SunJH
 * @description t_dict_event_tag
 * @date 2023/1/10
 */
@Data
public class EventTagDictEntity {

    /**
     * 标签id
     */
    private String tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 父级标签id
     */
    private String pTagId;
    /**
     * 标签级别
     */
    private Integer tagLevel;
    /**
     * 排序
     */
    private Integer sort;

}

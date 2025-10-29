package com.znv.manage.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TCfgDict)实体类
 *
 * @author makejava
 * @since 2022-11-11 13:40:06
 */
@Data
public class Dict implements Serializable {
    private static final long serialVersionUID = 728774996410164695L;

    private Integer id;

    private String dictName;

    private Integer dictOrder;

    private String dictType;

    private Integer parentId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}

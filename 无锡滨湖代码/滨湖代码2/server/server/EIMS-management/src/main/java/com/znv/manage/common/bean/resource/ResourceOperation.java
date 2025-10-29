package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TCfgResourceOperation)实体类
 *
 * @author makejava
 * @since 2022-11-10 15:30:26
 */
@Data
public class ResourceOperation implements Serializable {
    private static final long serialVersionUID = 537757398335352233L;

    private Integer id;

    private String rsId;

    private String type;

    private Double num;

    private String info;

    private String note;

    private String phone;

    private String outboundType;

    private String idNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String typeName;

    private String stuffUnit;

    private String stuffName;

}

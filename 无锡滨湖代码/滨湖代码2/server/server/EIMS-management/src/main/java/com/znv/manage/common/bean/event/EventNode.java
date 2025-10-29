package com.znv.manage.common.bean.event;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TCfgEventNode)实体类
 *
 * @author makejava
 * @since 2022-12-09 16:32:20
 */
@Data
public class EventNode implements Serializable {
    private static final long serialVersionUID = 641248886680236622L;

    private Integer id;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

package com.znv.manage.common.bean.event;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * 事件核查表(TCfgEventCheck)实体类
 *
 * @author makejava
 * @since 2023-05-18 15:14:36
 */
@Data
public class EventCheck implements Serializable {
    private static final long serialVersionUID = 931516474388907293L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 关联事件id
     */
    @ApiModelProperty(value = "关联事件id")
    private Integer eventId;
    /**
     * 核查人名称
     */
    @ApiModelProperty(value = "核查人名称")
    private String checkPersonName;
    /**
     * 核查人id(用户表)
     */
    @ApiModelProperty(value = "核查人id(用户表)")
    private Integer checkUserId;
    /**
     * 下发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下发时间")
    private Date issueTime;
    /**
     * 要求
     */
    @ApiModelProperty(value = "要求")
    private String requirement;

}

package com.znv.manage.common.bean;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * 同步对接车辆(TCfgSyncCar)实体类
 *
 * @author makejava
 * @since 2023-01-10 16:11:04
 */
@Data
public class SyncCar implements Serializable {
    private static final long serialVersionUID = -88951874268386171L;
    /**
     * 车牌
     */
    @ApiModelProperty(value = "车牌")
    private String license;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String dept;

    @ApiModelProperty(value = "$column.comment")
    private String gpsx;

    @ApiModelProperty(value = "$column.comment")
    private String gpsy;
    /**
     * 是否在线
     */
    @ApiModelProperty(value = "是否在线")
    private String online;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "$column.comment")
    private Date time;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;
    /**
     * 地址编码
     */
    @ApiModelProperty(value = "地址编码")
    private String districtCode;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

}

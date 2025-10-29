package com.znv.manage.common.bean;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;import io.swagger.annotations.ApiModelProperty;

/**
 * 城市固件(TCityFirmware)实体类
 *
 * @author makejava
 * @since 2023-03-01 14:45:11
 */
@Data
public class CityFirmware implements Serializable {
    private static final long serialVersionUID = -82780570492683372L;
         
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 固件名称
     */     
    @ApiModelProperty(value = "部件名称")
    private String name;
    /**
     * 大类
     */     
    @ApiModelProperty(value = "大类")
    private String parentType;
    /**
     * 小类
     */     
    @ApiModelProperty(value = "小类")
    private String type;
    /**
     * 经度
     */     
    @ApiModelProperty(value = "经度")
    private String gpsx;
    /**
     * 纬度
     */     
    @ApiModelProperty(value = "纬度")
    private String gpsy;
         
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;

}

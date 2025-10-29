package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@TableName("sys_param")
@ApiModel(
   value = "SysParam",
   description = "系统全局参数码表"
)
public class SysParam implements Serializable {
   private static final long serialVersionUID = 1L;
   @ApiModelProperty("参数名称")
   @TableField("name")
   private String name;
   @ApiModelProperty("参数值")
   @TableField("value")
   private String value;
   @ApiModelProperty("值类型（字符串，整型，数组，时间，布尔，JSON）")
   @TableField("vtype")
   private String vtype;
   @ApiModelProperty("状态（1：可效，0：下线）")
   @TableField("status")
   private Integer status;
   @ApiModelProperty("编码类型(1：产品编码，2：业务编码)")
   @TableField("ctype")
   private String ctype;
   @ApiModelProperty("备注")
   @TableField("remark")
   private String remark;
   @ApiModelProperty("创建时间")
   @TableField("create_time")
   private LocalDateTime createTime;

}

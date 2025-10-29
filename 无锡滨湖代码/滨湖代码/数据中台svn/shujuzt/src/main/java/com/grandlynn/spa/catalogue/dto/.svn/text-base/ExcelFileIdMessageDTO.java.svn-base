package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class ExcelFileIdMessageDTO extends AbstractObject implements Serializable {
   private static final long serialVersionUID = -1L;
   @ApiModelProperty("租户")
   private String tenantId;
   @ApiModelProperty("appId")
   private Long appId;
   @ApiModelProperty("文件id")
   private Long fileId;
   @ApiModelProperty("文件来源")
   private Integer operateFrom;
   @ApiModelProperty("导入位置：1.组织导入ORG;2.系统管理导入SYS_MANAGE;3.目录编制导入CATALOGUE_MANAGE;4.数据源接入管理导入DATA")
   private String importPosition;
   @ApiModelProperty("是否做重复验证， 1是 检查是否已有重复，有的话更新，否则新增；0否 导入数据直接新增 。 ")
   private Integer checkAdd = 1;
   @ApiModelProperty("用户")
   private SysAdminUser user;
}

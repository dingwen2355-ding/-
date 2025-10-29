package com.grandlynn.spa.catalogue.dto;

import com.grandlynn.spa.catalogue.extensions.annotation.ExcelNotNull;
import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class ExportDatasourceTemplateDTO extends AbstractObject implements Serializable {
   private static final long serialVersionUID = -1L;
   @ExcelNotNull
   @ApiModelProperty("组织机构代码")
   private String orgCode;
   @ExcelNotNull
   @ApiModelProperty("组织名称(全称)")
   private String orgName;
   @ExcelNotNull
   @ApiModelProperty("系统名称(全称)")
   private String sysName;
   @ExcelNotNull
   @ApiModelProperty("数据源名称")
   private String name;
   @ExcelNotNull
   @ApiModelProperty("数据库类型(下拉)")
   private String databaseType;
   @ApiModelProperty("数据源实例(Oracle必填)")
   private String example;
   @ApiModelProperty("数据源实例名称(Oracle必填)")
   private String exampleName;
   @ExcelNotNull
   @ApiModelProperty("数据库发布IP")
   private String ip;
   @ExcelNotNull
   @ApiModelProperty("数据库发布端口")
   private String port;
   @ExcelNotNull
   @ApiModelProperty("数据库开放用户名")
   private String username;
   @ApiModelProperty("字符集")
   private String character;
   @ExcelNotNull
   @ApiModelProperty("数据库名称")
   private String databaseName;
   @ExcelNotNull
   @ApiModelProperty("数据库访问密码")
   private String databasePwd;
   @ApiModelProperty("数据源采集版本")
   private String databaseVersion;
   @ApiModelProperty("联系人")
   private String linkMan;
   @ApiModelProperty("联系人单位")
   private String linkOrg;
   @ApiModelProperty("联系人电话")
   private String linkPhone;
   @ExcelNotNull
   @ApiModelProperty("前置机归属类型")
   private String affiliationType;
   @ApiModelProperty("数据库备注")
   private String remark;
   @ApiModelProperty("错误信息")
   private String warn;
}

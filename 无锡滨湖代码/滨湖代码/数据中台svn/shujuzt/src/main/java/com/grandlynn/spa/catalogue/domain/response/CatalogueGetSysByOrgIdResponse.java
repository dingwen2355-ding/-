package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueGetSysByOrgIdResponse extends BaseResponse {
   @ApiModelProperty("系统名称")
   private String sysName;
   @ApiModelProperty("是否启用：1 启用，0 停用")
   private Integer enable;
   @ApiModelProperty("组织代码")
   private String nodeNo;
   @ApiModelProperty("组织全称")
   private String nodeName;
   @ApiModelProperty("系统简称")
   private String sysNameSimplify;
   @ApiModelProperty("【建设层级】-> 应用层级")
   private String buildLevel;
   @ApiModelProperty("平台名称")
   private String platformName;
   @ApiModelProperty("部署方式")
   private String deployType;
   @ApiModelProperty("建设状况")
   private String buildStatus;
   @ApiModelProperty("系统状态")
   private String sysStatus;
   @ApiModelProperty("功能描述")
   private String functionDesc;
   @ApiModelProperty("系统类型")
   private String sysType;
   @ApiModelProperty("【使用范围】-> 服务对象")
   private String useRange;
   @ApiModelProperty("使用范围")
   private String useRangeDesc;
   @ApiModelProperty("上线时间")
   private String upTime;
   @ApiModelProperty("建成时间")
   private String buildTime;
   @ApiModelProperty("【信息安全等保级别】-> 等保定级")
   private String securityLevel;
   @ApiModelProperty("系统备注")
   private String sysRemark;
   @ApiModelProperty("建设单位联系人")
   private String adminStaff;
   @ApiModelProperty("建设单位联系电话")
   private String adminPhone;
   @ApiModelProperty("【建设厂商】->建设单位")
   private String contractCompany;
   @ApiModelProperty("厂商接口人")
   private String contractStaff;
   @ApiModelProperty("厂商联系方式")
   private String contractPhone;
   @ApiModelProperty("关联父级系统")
   private String parentSys;
   @ApiModelProperty("系统类型备注")
   private String sysTypeDesc;
   @ApiModelProperty("【垂管（国垂、否）】-> 是否垂管")
   private String verticalType;
   @ApiModelProperty("终端情况(设备类型)")
   private String deviceType;
   @ApiModelProperty("终端情况描述")
   private String deviceTypeDesc;
   @ApiModelProperty("整合情况")
   private String integration;
   @ApiModelProperty("系统网址")
   private String sysUrl;
   @ApiModelProperty("实施单位")
   private String implementCompany;
   @ApiModelProperty("实施单位联系人")
   private String implementStaff;
   @ApiModelProperty("实施单位联系电话")
   private String implementPhone;
   @ApiModelProperty("运维单位")
   private String operationCompany;
   @ApiModelProperty("运维人员")
   private String operationStaff;
   @ApiModelProperty("运维联系人电话")
   private String operationPhone;
   @ApiModelProperty("【系统部署位置】->部署环境")
   private String deployLocation;
   @ApiModelProperty("系统部署位置备注")
   private String deployLocationDesc;
   @ApiModelProperty("【接入网络类型】->承载网络")
   private String netType;
   @ApiModelProperty("接入网络类型备注")
   private String netTypeDesc;
   @ApiModelProperty("信息安全登报级别备注")
   private String securityLevelDesc;
   @ApiModelProperty("前置机数据库类型")
   private String fortDatabaseType;
   @ApiModelProperty("编写人")
   private String writer;
   @ApiModelProperty("对应项目名称")
   private String projectName;
   @ApiModelProperty("对应项目编码")
   private String projectCode;
   @ApiModelProperty("建设资金（万元）")
   private String constructionFund;
   @ApiModelProperty("年运维费（万元）")
   private String operationFee;
   @ApiModelProperty(" 数据储存量（GB）")
   private String dataCapacity;
   @ApiModelProperty("政府采购编码")
   private String purchaseCode;
   @ApiModelProperty("财政预算编码")
   private String budgetCode;
   @ApiModelProperty("信息化全流程编码")
   private String informationFlowCode;

}

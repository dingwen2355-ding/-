package com.grandlynn.spa.catalogue.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import lombok.Data;

@Data
@TableName("catalogue_org_sys")
public class CatalogueOrgSysDO extends BaseEntity {
   @TableField("sys_name")
   private String sysName;
   @TableField("enable")
   private Integer enable;
   @TableField(
      exist = false
   )
   private Long orgId;
   @TableField("node_no")
   private String nodeNo;
   @TableField("node_name")
   private String nodeName;
   @TableField("sys_name_simplify")
   private String sysNameSimplify;
   @TableField("build_level")
   private String buildLevel;
   @TableField("platform_name")
   private String platformName;
   @TableField("deploy_type")
   private String deployType;
   @TableField("build_status")
   private String buildStatus;
   @TableField("sys_status")
   private String sysStatus;
   @TableField("function_desc")
   private String functionDesc;
   @TableField("sys_type")
   private String sysType;
   @TableField("use_range")
   private String useRange;
   @TableField("use_range_desc")
   private String useRangeDesc;
   @TableField("up_time")
   private String upTime;
   @TableField("build_time")
   private String buildTime;
   @TableField("security_level")
   private String securityLevel;
   @TableField("sys_remark")
   private String sysRemark;
   @TableField("admin_staff")
   private String adminStaff;
   @TableField("admin_phone")
   private String adminPhone;
   @TableField("contract_company")
   private String contractCompany;
   @TableField("contract_staff")
   private String contractStaff;
   @TableField("contract_phone")
   private String contractPhone;
   @TableField("intermediate")
   private Integer intermediate;
   @TableField("parent_sys")
   private String parentSys;
   @TableField("sys_type_desc")
   private String sysTypeDesc;
   @TableField("vertical_type")
   private String verticalType;
   @TableField("device_type")
   private String deviceType;
   @TableField("device_type_desc")
   private String deviceTypeDesc;
   @TableField("integration")
   private String integration;
   @TableField("sys_url")
   private String sysUrl;
   @TableField("implement_company")
   private String implementCompany;
   @TableField("implement_staff")
   private String implementStaff;
   @TableField("implement_phone")
   private String implementPhone;
   @TableField("operation_company")
   private String operationCompany;
   @TableField("operation_staff")
   private String operationStaff;
   @TableField("operation_phone")
   private String operationPhone;
   @TableField("deploy_location")
   private String deployLocation;
   @TableField("deploy_location_desc")
   private String deployLocationDesc;
   @TableField("net_type")
   private String netType;
   @TableField("net_type_desc")
   private String netTypeDesc;
   @TableField("security_level_desc")
   private String securityLevelDesc;
   @TableField("fort_database_type")
   private String fortDatabaseType;
   @TableField("version_no")
   private String versionNo;
   @TableField("writer")
   private String writer;
   @TableField("project_name")
   private String projectName;
   @TableField("project_code")
   private String projectCode;
   @TableField("construction_fund")
   private String constructionFund;
   @TableField("operation_fee")
   private String operationFee;
   @TableField("data_capacity")
   private String dataCapacity;
   @TableField("purchase_code")
   private String purchaseCode;
   @TableField("budget_code")
   private String budgetCode;
   @TableField("information_flow_code")
   private String informationFlowCode;
   @TableField("external_id")
   private String externalId;

}

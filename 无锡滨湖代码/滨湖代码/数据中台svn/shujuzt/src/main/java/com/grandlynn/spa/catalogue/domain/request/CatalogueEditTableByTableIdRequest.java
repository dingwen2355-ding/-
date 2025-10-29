package com.grandlynn.spa.catalogue.domain.request;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CatalogueEditTableByTableIdRequest extends BaseRequest {
   @ApiModelProperty(
      value = "表id",
      required = true
   )
   private @NotNull(
   message = "表id不能为空"
) Long id;
   @ApiModelProperty(
      value = "组织系统表id",
      required = true
   )
   private @NotNull(
   message = "组织系统表id不能为空"
) Long orgSysId;
   @ApiModelProperty("共享类型")
   private String tableShareType;
   @ApiModelProperty("是否启用：1 启用，0 停用（table级别启用停用）")
   private Integer enable;
   @ApiModelProperty("目录编号")
   private String tableCode;
   @ApiModelProperty("表（目录）状态：1待发布；2.待审核；3审核中；4.启用中；5.已停用；6已驳回；")
   private Integer tableState;
   @ApiModelProperty("系统全称")
   private String sysName;
   @ApiModelProperty("数据资源名称")
   private String dataResourceName;
   @ApiModelProperty("数据资源摘要")
   private String dataResourceAbstract;
   @ApiModelProperty("数据资源存储方式")
   private String dataResourceSaveType;
   @ApiModelProperty("数据库表名称")
   private String tableName;
   @ApiModelProperty("共享条件说明")
   private String tableShareDesc;
   @ApiModelProperty("法律法规依据自定义说明")
   private String lawDepency;
   @ApiModelProperty("法律法规依据附件")
   private String lawDepencyAttach;
   @ApiModelProperty("共享方式")
   private String shareStatus;
   @ApiModelProperty("开放类型")
   private String openType;
   @ApiModelProperty("开放条件")
   private String openCondition;
   @ApiModelProperty("开放依据自定义说明")
   private String openDepency;
   @ApiModelProperty("开放依据附件")
   private String openDepencyAttach;
   @ApiModelProperty("坐标地址字段")
   private String position;
   @ApiModelProperty("单位名称")
   private String administrationName;
   @ApiModelProperty("表备注")
   private String tableRemark;
   @ApiModelProperty("资源编码")
   private String resourceCode;
   @ApiModelProperty("更新周期（每年每半年每季度每月每周每日）")
   private String updatePeriod;
   @ApiModelProperty("数据库类型Oracle/Mysql/SQLServer/PostgreSQL/db2/dbase/sysbase/dbf")
   private String databaseType;
   @ApiModelProperty("数据领域")
   private String dataDomain;
   @ApiModelProperty("资源分类")
   private String resourceType;
   @ApiModelProperty("数据创建时间字段")
   private String tableCreateTime;
   @ApiModelProperty("数据修改时间字段")
   private String tableUpdateTime;
   @ApiModelProperty("api名称")
   private String apiAlias;
   @ApiModelProperty("api请求类型")
   private Integer apiType;
   @ApiModelProperty("api请求地址")
   private String apiUrl;
   @ApiModelProperty("api请求body")
   private String apiBody;
   @ApiModelProperty("api请求头")
   private String apiHeader;
   @ApiModelProperty("api请求返回")
   private String apiResponse;
   @ApiModelProperty("api备注")
   private String remark;
   @ApiModelProperty("主题分类")
   private String subjectCategory;
   @ApiModelProperty("类id")
   private Long categoryId;
   @ApiModelProperty("项id")
   private Long itemId;
   @ApiModelProperty("目id")
   private Long catalogueId;
   @ApiModelProperty("数据库名称")
   private String databaseName;
   @ApiModelProperty("部门id")
   private Long orgId;
   @ApiModelProperty("资源格式分类")
   private String resourceFormatType;
   @ApiModelProperty("信息资源格式分类补充")
   private String resourceFormatTypeDesc;
   @ApiModelProperty("共享类型说明")
   private String tableShareTypeDesc;
   @ApiModelProperty("目录来源类型：1:基于系统梳理，2:基于政务事项梳理")
   private Integer sourceType;
   @ApiModelProperty("目录格式分类")
   private String resourceFormatCategory;
   @ApiModelProperty("数据范围")
   private String dataRange;
   @ApiModelProperty("目录分级")
   private String catalogueLevel;
   @ApiModelProperty("目录简称")
   private String simplify;
   @ApiModelProperty("事项关联")
   private String matter;
   @ApiModelProperty("事项目录编码")
   private String matterCode;
   @ApiModelProperty("事项基本目录编码")
   private String matterBasicCode;
   @ApiModelProperty("实施清单编码")
   private String activityListCode;
   @ApiModelProperty("业务办理项编码")
   private String businessForCode;
   @ApiModelProperty("提供渠道")
   private String deliverChannel;
   @ApiModelProperty("是否为电子证照：1:是，2:否")
   private Integer isElectronicCertificate;
   @ApiModelProperty("应用场景")
   private String applicationScene;
   @ApiModelProperty("是否挂载资源：1:是，2:否")
   private Integer isMountResource;
   @ApiModelProperty("数据资源所注册平台")
   private String registerPlatform;
   @ApiModelProperty("审批结果")
   private String approveResult;
   @ApiModelProperty("共享方式分类")
   private String shareWayCategory;
   @ApiModelProperty("共享方式类型")
   private String shareWayType;
   @ApiModelProperty("所属领域编码")
   private String domainCode;
   @ApiModelProperty("行业门类编码")
   private String industryCategoryCode;
   @ApiModelProperty("应用场景编码")
   private String applicationSceneCode;
   @ApiModelProperty("基础分类")
   private String basicCategory;
   @ApiModelProperty("服务对象分类")
   private String serviceObjectCategory;
   @ApiModelProperty("目录标签：1企业经营")
   private Integer categoryTag;
   @ApiModelProperty("管理方式：1:手工，2:系统")
   private Integer manageWay;
   @ApiModelProperty("目录形态分类：1:电子化资源，2:非电子化资源")
   private Integer morphologyCategory;
   @ApiModelProperty("提供方内部部门")
   private String sourceDeptName;
   @ApiModelProperty("部门编码")
   private String nodeCode;
   @ApiModelProperty("数据资源格式")
   private String resourceFormat;
   @ApiModelProperty("政务服务事项目录所属领域")
   private String matterDomain;
   @ApiModelProperty("其他应用场景")
   private String applicationSceneDesc;
   @ApiModelProperty("其他政务服务事项目录所属领域")
   private String matterDomainDesc;
   @ApiModelProperty("更新周期描述")
   private String updatePeriodDesc;
   @ApiModelProperty("数据量")
   private Long dataSize;
   @ApiModelProperty("事项关联的ID")
   private String rowGuId;

}

package com.grandlynn.spa.catalogue.domain.response;

import com.grandlynn.spa.catalogue.base.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CatalogueTableFieldDetailResponse extends BaseResponse {
   @ApiModelProperty("系统目录表id")
   private Long sysTableId;
   @ApiModelProperty("信息项编码如GZJH")
   private String fieldName;
   @ApiModelProperty("信息项名称如工作计划次数")
   private String fieldAlias;
   @ApiModelProperty("信息项类型：string；integer；")
   private String fieldType;
   @ApiModelProperty("信息项长度")
   private String fieldLength;
   @ApiModelProperty("字段版本")
   private String fieldVersion;
   @ApiModelProperty("字段状态：1待提交；3、4待审批；8、9已退回；")
   private Integer fieldState;
   @ApiModelProperty("字段标记：1正常；2新增；3编辑；4删除;")
   private Integer fieldFlag;
   @ApiModelProperty("是否加密：是，否")
   private String fieldEncoded;
   @ApiModelProperty("是否脱敏：是，否")
   private String fieldObtuse;
   @ApiModelProperty("是否主键：是，否")
   private String fieldPrimaryKey;
   @ApiModelProperty("是否坐标、地址")
   private String fieldPosition;
   @ApiModelProperty("字段描述")
   private String fieldDesc;
   @ApiModelProperty("数据记录总数（万条）")
   private String fieldTotalLog;
   @ApiModelProperty("数据存储总量（GB）")
   private String fieldTotalMemory;
   @ApiModelProperty("字段中文名称")
   private String fieldNameCh;
   @ApiModelProperty("是否字典")
   private String dictState;
   @ApiModelProperty("对应字典表")
   private String dictTable;
   @ApiModelProperty("字典采用标准")
   private String dictStandard;
   @ApiModelProperty("共享类型")
   private String shareType;
   @ApiModelProperty("共享条件")
   private String shareCondition;
   @ApiModelProperty("法律法规依据自定义说明")
   private String lawDependency;
   @ApiModelProperty("法律法规依据附件")
   private String lawDependencyAttach;
   @ApiModelProperty("允许为空（是、否）")
   private String allowNull;
   @ApiModelProperty("是否索引（是、否）")
   private String allowIndex;
   @ApiModelProperty("数据精度")
   private Integer fieldPrecision;
   @ApiModelProperty("数据分级")
   private Integer dataLevel;
   @ApiModelProperty("是否符合统一数据标准1:是，0否")
   private Integer isNorm;

}

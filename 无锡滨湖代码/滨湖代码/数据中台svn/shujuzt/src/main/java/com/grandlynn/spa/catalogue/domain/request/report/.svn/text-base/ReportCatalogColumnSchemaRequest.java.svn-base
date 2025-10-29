package com.grandlynn.spa.catalogue.domain.request.report;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel
public class ReportCatalogColumnSchemaRequest extends AbstractObject implements Serializable {
   @ApiModelProperty("信息项标识，uuid,不可重复")
   private String columnid;
   @ApiModelProperty("信息项名称")
   private String namecn;
   @ApiModelProperty("数据类型编码（见附录数据字典.数据类型）")
   private String dataformat;
   @ApiModelProperty("数据长度")
   private Integer length;
   @ApiModelProperty("数值类型选填精度")
   private Integer precision;
   @ApiModelProperty("排序编号")
   private Integer order_id;
   @ApiModelProperty("更新时间")
   private String updatetime;
   @ApiModelProperty("见数据字典:国家平台扩展信息-数据敏感级别")
   private String sensitive_level;
   @ApiModelProperty("见数据字典:国家平台扩展信息-信息项共享类型")
   private String share_type;
   @ApiModelProperty("不共享原因")
   private String not_share_reason;


}

package com.grandlynn.spa.catalogue.domain.request.report;

import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel
public class ReportCatalogueRequest extends AbstractObject implements Serializable {
   @ApiModelProperty("市共享平台返回的目录id，uuid")
   private String cata_id;
   @ApiModelProperty("市共享平台返回的目录分类code")
   private String group_id;
   @ApiModelProperty("信息资源目录名称")
   private String cata_title;
   @ApiModelProperty("目录类型，1基于系统梳理，2基于事项梳理")
   private String combType;
   @ApiModelProperty("目录基于系统梳理必填:市平台返回的信息系统guid")
   private String systemGuid;
   @ApiModelProperty("资源目录提供方部门的统一信用代码,该部门必须先通过机构注册上报到市平台")
   private String credit_code;
   @ApiModelProperty("提供方内部部门名称")
   private String internalorgan;
   @ApiModelProperty("信息资源摘要")
   private String description;
   @ApiModelProperty("数据字典：信息资源格式分类")
   private String resourceformat;
   @ApiModelProperty("信息项列表")
   private List<ReportCatalogColumnSchemaRequest> columnlist;
   @ApiModelProperty("信息资源目录发布时间，格式为：yyyy-MM-dd HH:mm:ss")
   private String publishedtime;
   @ApiModelProperty("数据字典：共享类型")
   private String shared_type;
   @ApiModelProperty("共享条件")
   private String shared_condition;
   @ApiModelProperty("“shared_type”为“2”可不填，其他必填:数据字典：共享方式，多个以逗号隔开，总长度不超过50")
   private String sharedway;
   @ApiModelProperty("向社会开放类型（数据字典：开放类型）")
   private Integer open_type;
   @ApiModelProperty("“open_type”为“1”时必填:开放条件描述")
   private String open_condition;
   @ApiModelProperty("更新时间，格式为：yyyy-MM-dd HH:mm:ss")
   private String updatetime;
   @ApiModelProperty("数据字典：更新周期")
   private String update_cycle;
   @ApiModelProperty("“update_cycle”为“8”时必填:其他更新周期")
   private String other_update_cycle;
   @ApiModelProperty("“open_type”为“1”时必填:见数据字典：向社会开放条件")
   private String opencondition;
   @ApiModelProperty("不向社会开放理由依据")
   private String useDemand;
   @ApiModelProperty("见数据字典：应用场景")
   private String usescene;
   @ApiModelProperty("见数据字典：行业门类")
   private String belongindustry;
   @ApiModelProperty("见数据字典:所属领域")
   private String resourceSubject;
   @ApiModelProperty("目录简称")
   private String resourcesShortName;
   @ApiModelProperty("市平台下发各区县的授权码")
   private String appkey;
   @ApiModelProperty("目录关联的事项的rowguid，关联多个以|符号隔开")
   private String task_guid;
   @ApiModelProperty("见数据字典:国家平台扩展信息-提供渠道")
   private String nettype;
   @ApiModelProperty("见数据字典:国家平台扩展信息-是否电子证照")
   private String certification_type;
   @ApiModelProperty("见数据字典:国家平台扩展信息-应用场景")
   private String govusescene;
   @ApiModelProperty("“govusescene”为“0”时必填：其他应用场景")
   private String otherusescene;
   @ApiModelProperty("见数据字典:国家平台扩展信息-所属领域")
   private String fieldtype;
   @ApiModelProperty("“fieldtype”为“23”时必填：其他所属领域")
   private String other_fieldtype;
   @ApiModelProperty("见数据字典:国家平台扩展信息-是否已注册数据资源")
   private String res_registered;
   @ApiModelProperty("“res_registered”为“1”时必填：数据资源所注册的平台")
   private String res_platform;
   @ApiModelProperty("政务服务数据提供方")
   private String org_name;
   @ApiModelProperty("提供方代码")
   private String org_code;
   @ApiModelProperty("审批结果(关联事项办理产生的结果名称)，多个结果以|符号隔开")
   private String approval_results;
   @ApiModelProperty("行政区划代码")
   private String region_code;
   @ApiModelProperty("目录分级")
   private String basicinfoLevel;
   @ApiModelProperty("目录分级")
   private String resourceType;

}

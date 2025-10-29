package com.grandlynn.spa.catalogue.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.utils.PgJson2ObjTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@TableName(
   value = "catalogue_data_source_api",
   autoResultMap = true
)
public class CatalogueDataSourceApiDO extends BaseEntity {
   @ApiModelProperty("数据源主表id")
   private Long dataSourceId;
   @ApiModelProperty("数据源API别名")
   private String apiAlias;
   @ApiModelProperty("数据源API地址")
   private @NotNull(
   message = "API地址不能为空"
) String apiUrl;
   @ApiModelProperty("请求类型1GET；2POST；3PUT；4DELETE")
   private @NotNull(
   message = "请求类型不能为空"
) Integer apiType;
   @ApiModelProperty("请求体body")
   @TableField(
      typeHandler = PgJson2ObjTypeHandler.class
   )
   private JSONObject apiBody;
   @ApiModelProperty("请求头Header")
   private String apiHeader;
   @ApiModelProperty("响应示例（直接返回页面未解析的json数据内容）")
   @TableField(
      typeHandler = PgJson2ObjTypeHandler.class
   )
   private JSONObject apiResponse;

}

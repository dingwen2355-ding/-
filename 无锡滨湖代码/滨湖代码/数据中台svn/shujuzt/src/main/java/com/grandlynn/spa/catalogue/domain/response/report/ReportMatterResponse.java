package com.grandlynn.spa.catalogue.domain.response.report;

import com.alibaba.fastjson.JSONArray;
import com.grandlynn.util.pojo.AbstractObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("上报市平台事项返回")
public class ReportMatterResponse extends AbstractObject implements Serializable {
   private String code;
   private JSONArray data;
   private String msg;
   private Integer totalCount;
   private Integer totalPage;
   private Integer pageSize;
   private Integer currentPage;

}

package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.base.PageRequest;
import com.grandlynn.spa.catalogue.domain.request.AnalysisCatalogAmountTrendRequest;
import com.grandlynn.spa.catalogue.domain.request.AnalysisCatalogueRankingRequest;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogAmountTrendResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueRankingResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueStatisticsResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisTotalResponse;
import com.grandlynn.spa.catalogue.dto.OrgStatisticsDTO;
import com.grandlynn.spa.catalogue.entity.TodoBusinessDo;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.AnalysisService;
import com.grandlynn.spa.catalogue.service.TodoBusinessService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Authorize
@RestController
@RequestMapping({"home"})
@Api(
   tags = {"首页"}
)
public class HomePageController {
   @Resource
   private AnalysisService analysisService;
   @Resource
   private TodoBusinessService todoBusinessService;

   @GetMapping({"total"})
   @ApiOperation("部门,系统,目录,数据项总量统计")
   public Payload<AnalysisTotalResponse> total() {
      AnalysisTotalResponse analysisTotalResponse = this.analysisService.total();
      return new Payload(analysisTotalResponse);
   }

   @GetMapping({"catalog/statistics"})
   @ApiOperation("部门下系统,目录,字段和清单总数统计")
   public Payload<AnalysisCatalogueStatisticsResponse> catalogueStatistics(@RequestParam(required = false) Long orgId) {
      return new Payload(this.analysisService.catalogue(orgId));
   }

   @PostMapping({"todo/businesses"})
   @ApiOperation("待处理业务")
   public Payload<PageBean<TodoBusinessDo>> todoBusinesses(@RequestBody PageRequest request) {
      return new Payload(this.todoBusinessService.pendingList(request));
   }

   @PostMapping({"catalog/ranking"})
   @ApiOperation("部门目录排名")
   public Payload<List<AnalysisCatalogueRankingResponse>> catalogRanking(@RequestBody AnalysisCatalogueRankingRequest request) {
      return new Payload(this.analysisService.catalogRanking(request));
   }

   @GetMapping({"catalog/table"})
   @ApiOperation("部门目录数统计")
   public Payload<List<AnalysisCatalogueRankingResponse>> catalogTablestatistics() {
      return new Payload(this.analysisService.catalogTableStatistics());
   }

   @PostMapping({"catalog/amount"})
   @ApiOperation("改成总量趋势")
   public Payload<AnalysisCatalogAmountTrendResponse> catalogAmountTrend(@RequestBody AnalysisCatalogAmountTrendRequest request) {
      return new Payload(this.analysisService.catalogAmountTrend(request));
   }

   @GetMapping({"catalog/resourceStatistics"})
   @ApiOperation("首页资源数量")
   public Payload<List<OrgStatisticsDTO>> resourceStatistics() {
      return new Payload(this.analysisService.resourceStatistics());
   }
}

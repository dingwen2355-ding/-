package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.domain.request.SysOperationLogRequest;
import com.grandlynn.spa.catalogue.domain.response.SysOperationLogAnalysisResponse;
import com.grandlynn.spa.catalogue.domain.response.SysOperationLogResponse;
import com.grandlynn.spa.catalogue.service.SysOperationLogService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"日志管理模块"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/sys-log"})
public class SysOperationLogController {
   private static final Logger log = LoggerFactory.getLogger(SysOperationLogController.class);
   @Resource
   private SysOperationLogService operationLogService;

   @ApiOperation(
      value = "根据表id查询表字段信息列表（支持分页）",
      nickname = "findPage"
   )
   @GetMapping({"findPage"})
   public Payload<PageBean<SysOperationLogResponse>> getTableFieldsByTaleId(@Valid SysOperationLogRequest request) {
      return new Payload(this.operationLogService.getPage(request));
   }

   @ApiOperation(
      value = "查询时间段内日志分析",
      nickname = "findAnalysis"
   )
   @GetMapping({"findAnalysis"})
   public Payload<SysOperationLogAnalysisResponse> findAnalysis(@Valid SysOperationLogRequest request) {
      return new Payload(this.operationLogService.findAnalysis(request));
   }
}

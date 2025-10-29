package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.log.OperationLog;
import com.grandlynn.spa.catalogue.domain.request.ApprovePassAllRequest;
import com.grandlynn.spa.catalogue.domain.request.ApproveRequest;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CataloguePublishCheckService;
import com.grandlynn.util.config.Payload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Authorize
@RestController
@RequestMapping({"approve"})
@Api(
   tags = {"审批管理"}
)
public class ApproveController {
   @Resource
   CataloguePublishCheckService service;

   @OperationLog(
      title = "目录",
      businessType = "目录初审通过"
   )
   @PostMapping({"first/pass"})
   @ApiOperation("初审通过")
   public Payload<Void> firstPass(@RequestBody @Valid ApproveRequest request) {
      this.service.approve(request, ApproveStateEnum.RECHECK);
      return new Payload();
   }

   @OperationLog(
      title = "目录",
      businessType = "目录初审驳回"
   )
   @PostMapping({"first/reject"})
   @ApiOperation("初审驳回")
   public Payload<Void> firstReject(@RequestBody @Valid ApproveRequest request) {
      this.service.approve(request, ApproveStateEnum.F_REJECT);
      return new Payload();
   }

   @OperationLog(
      title = "目录",
      businessType = "目录复审通过"
   )
   @PostMapping({"recheck/pass"})
   @ApiOperation("复审通过")
   public Payload<Void> recheckPass(@RequestBody @Valid ApproveRequest request) {
      this.service.approve(request, ApproveStateEnum.UN_PUBLISHED);
      return new Payload();
   }

   @OperationLog(
      title = "目录",
      businessType = "目录复审驳回"
   )
   @PostMapping({"recheck/reject"})
   @ApiOperation("复审驳回")
   public Payload<Void> recheckReject(@RequestBody @Valid ApproveRequest request) {
      this.service.approve(request, ApproveStateEnum.R_REJECT);
      return new Payload();
   }

   @OperationLog(
      title = "目录",
      businessType = "目录审批一键通过"
   )
   @PostMapping({"passAll"})
   @ApiOperation("审批通过全部")
   public Payload<Void> passAll(@RequestBody @Valid ApprovePassAllRequest request) {
      this.service.passAll(request);
      return new Payload();
   }

   @OperationLog(
      title = "目录",
      businessType = "目录发布"
   )
   @PostMapping({"publish"})
   @ApiOperation("发布")
   public Payload<Void> publish(@RequestBody @Valid BaseRequest request) {
      this.service.publish(request);
      return new Payload();
   }
}

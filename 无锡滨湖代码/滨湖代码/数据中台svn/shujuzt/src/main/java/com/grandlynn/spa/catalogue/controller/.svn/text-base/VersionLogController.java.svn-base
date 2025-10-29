package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldDetailByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldVersionByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionDetailByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetVersionsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgVersionQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetTableResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetVersionsResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueTableFieldDetailResponse;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"版本管理模块"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/versionLog"})
public class VersionLogController {
   private static final Logger log = LoggerFactory.getLogger(VersionLogController.class);
   @Resource
   private CatalogueVersionLogService catalogueVersionLogService;
   @Resource
   private CatalogueOrgVersionService orgVersionService;
   @Resource
   private CatalogueOrgSysVersionService orgSysVersionService;

   @ApiOperation(
      value = "版本列表查询（支持分页）",
      nickname = "getVersions"
   )
   @PostMapping({"getVersions"})
   public Payload<PageBean<CatalogueGetVersionsResponse>> getVersions(@RequestBody @Valid CatalogueGetVersionsRequest request) {
      return new Payload(this.catalogueVersionLogService.getVersions(request));
   }

   @ApiOperation(
      value = "查询当前最新的版本号",
      nickname = "getMaxVersionInfo"
   )
   @PostMapping({"getMaxVersionInfo"})
   public Payload<String> getMaxVersionNo(@RequestBody @Valid BaseRequest request) {
      return new Payload(this.catalogueVersionLogService.getMaxVersionNo(request));
   }

   @ApiOperation(
      value = "根据版本号查询当前版本记录详情",
      nickname = "getVersionInfoByVersionNo"
   )
   @PostMapping({"getVersionInfoByVersionNo"})
   public Payload<CatalogueGetVersionsResponse> getVersionInfoByVersionNo(@RequestParam("versionNo") @Valid String versionNo) {
      return new Payload(this.catalogueVersionLogService.getVersionInfoByVersionNo(versionNo));
   }

   @ApiOperation(
      value = "生成版本号",
      nickname = "generateVersionInfo"
   )
   @PostMapping({"generateVersionInfo"})
   public Payload<String> generateVersionInfo(@RequestBody @Valid BaseRequest request) {
      return new Payload(this.catalogueVersionLogService.generateVersionInfo(request));
   }

   @ApiOperation(
      value = "根据版本号查询组织树",
      nickname = "queryOrgTreeByVersion"
   )
   @PostMapping({"/queryOrgTreeByVersion"})
   @Authorize
   public Payload<CatalogueOrgQueryOrgTreeResponse> queryOrgTreeByVersion(@RequestBody @Valid CatalogueOrgVersionQueryOrgTreeRequest request) {
      return new Payload(this.orgVersionService.queryOrgVersionTree(request, request.getVersionNo()));
   }

   @ApiOperation(
      value = "版本号查询表数据列表（支持分页）",
      nickname = "getTablesVersion"
   )
   @PostMapping({"getTablesVersion"})
   @Authorize
   public Payload<PageBean<CatalogueGetTableResponse>> getTablesVersion(@RequestBody @Valid CatalogueGetTableVersionRequest request) {
      return new Payload(this.orgSysVersionService.getTablesVersion(request));
   }

   @ApiOperation(
      value = "根据版本号和表id查询表详情信息",
      nickname = "getTableVersionDetailByTableId"
   )
   @PostMapping({"getTableVersionDetailByTableId"})
   public Payload<CatalogueGetTableResponse> getTableVersionDetailByTableId(@RequestBody @Valid CatalogueGetTableVersionDetailByTableIdRequest request) {
      return new Payload(this.orgSysVersionService.getTableVersionByTableId(request));
   }

   @ApiOperation(
      value = "根据版本号和表id查询表字段信息列表（支持分页）",
      nickname = "getTableFieldsVersionByTaleId"
   )
   @PostMapping({"getTableFieldsVersionByTaleId"})
   public Payload<PageBean<CatalogueTableFieldDetailResponse>> getTableFieldsVersionByTaleId(@RequestBody @Valid CatalogueGetTableFieldVersionByTableIdRequest request) {
      return new Payload(this.orgSysVersionService.getTableFieldsVersionByTaleId(request));
   }

   @ApiOperation(
      value = "通过版本号和字段版本表id获取表字段信息详情",
      nickname = "getFieldVersionDetailByFieldId"
   )
   @PostMapping({"getFieldVersionDetailByFieldId"})
   public Payload<CatalogueTableFieldDetailResponse> getFieldVersionDetailByFieldId(@RequestBody @Valid CatalogueGetFieldDetailByFieldIdRequest request) {
      return new Payload(this.orgSysVersionService.getFieldDetailByFieldId(request));
   }
}

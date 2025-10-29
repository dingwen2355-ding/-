package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.log.OperationLog;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueCancelCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDeleteTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDisableTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEnableTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldByTableIdAndFieldNameRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldDetailByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOperatorTableFieldByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CataloguePublishAllCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.CataloguePublishCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.DisableExportRequest;
import com.grandlynn.spa.catalogue.domain.request.TableMatterRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueAddTableBySysIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSystemsByOrgIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetTableResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueTableFieldDetailResponse;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TableMatterDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceCategoryDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Authorize
@RestController
@Api(
   tags = {"目录编制模块"}
)
@RequestMapping({"/data-catalogue/v1/org/catalogue"})
public class CatalogueController {
   private static final Logger log = LoggerFactory.getLogger(CatalogueController.class);
   @Resource
   private CatalogueOrgSysService catalogueOrgSysService;
   @Resource
   private CatalogueTableFieldService catalogueTableFieldService;
   @Resource
   private CatalogueSysTableService catalogueSysTableService;

   @ApiOperation(
      value = "根据组织节点id数组查询系统列表",
      nickname = "getSystemsByOrgIds"
   )
   @PostMapping({"getSystemsByOrgIds"})
   public Payload<List<SystemsDTO>> getSystemsByOrgIds(@RequestBody @Valid CatalogueGetSystemsByOrgIdsRequest request) {
      return new Payload(this.catalogueOrgSysService.getSystemsByOrgIds(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据组织节点id查询组织节点id下挂载的系统列表",
      nickname = "getSystemsByOrgId"
   )
   @PostMapping({"getSystemsByOrgId"})
   public Payload<CatalogueGetSystemsByOrgIdResponse> getSystemsByOrgId(@RequestBody @Valid CatalogueGetSystemsByOrgIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getSystemsByOrgId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "查询表数据列表（支持分页）",
      nickname = "getTables"
   )
   @PostMapping({"getTables"})
   @Authorize
   public Payload<PageBean<CatalogueGetTableResponse>> getTables(@RequestBody @Valid CatalogueGetTableRequest request) {
      return new Payload(this.catalogueOrgSysService.getTables(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @OperationLog(
      title = "目录",
      businessType = "新增目录"
   )
   @ApiOperation(
      value = "新增指定系统下的表信息(根据系统id增加表信息)",
      nickname = "addTableBySysId"
   )
   @PostMapping({"addTableBySysId"})
   public Payload<CatalogueAddTableBySysIdResponse> addTableBySysId(@RequestBody @Valid CatalogueAddTableBySysIdRequest request) {
      return new Payload(this.catalogueOrgSysService.addTableBySysId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @OperationLog(
      title = "目录",
      businessType = "查看目录详情"
   )
   @ApiOperation(
      value = "根据表id查询表详情信息",
      nickname = "getTableDetailByTableId"
   )
   @PostMapping({"getTableDetailByTableId"})
   public Payload<CatalogueGetTableResponse> getTableDetailByTableId(@RequestBody @Valid CatalogueGetTableByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getTableByTableId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据表id查询表详情信息",
      nickname = "getTableHistorylByTableId"
   )
   @PostMapping({"getTableHistorylByTableId"})
   public Payload<CatalogueGetTableResponse> getTableHistorylByTableId(@RequestBody @Valid CatalogueGetTableByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getTableHistorylByTableId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @OperationLog(
      title = "目录",
      businessType = "编辑目录"
   )
   @ApiOperation(
      value = "根据表id编辑表内容",
      nickname = "editTableByTableId"
   )
   @PutMapping({"editTableByTableId"})
   public Payload<CatalogueGetTableResponse> editTableByTableId(@RequestBody @Valid CatalogueEditTableByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.editTableByTableId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @OperationLog(
      title = "目录",
      businessType = "删除目录"
   )
   @ApiOperation(
      value = "根据表id删除表内容",
      nickname = "deleteTableByTableId"
   )
   @DeleteMapping({"deleteTableByTableId"})
   public Payload<Boolean> deleteTableByTableId(@RequestBody @Valid CatalogueDeleteTableByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.deleteTableByTableId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据表id启用表内容",
      nickname = "enableTableByTableId"
   )
   @PostMapping({"/enable"})
   public Payload<Boolean> enableTableByTableId(@RequestBody @Valid CatalogueEnableTableByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.enableTableByTableId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据表id停用表内容",
      nickname = "disableTableByTableId"
   )
   @PostMapping({"/disable"})
   public Payload<Boolean> disableTableByTableId(@RequestBody @Valid CatalogueDisableTableByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.disableTableByTableId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据表id查询表字段信息列表（支持分页）",
      nickname = "getTableFieldByTaleId"
   )
   @PostMapping({"getTableFieldsByTaleId"})
   public Payload<PageBean<CatalogueTableFieldDetailResponse>> getTableFieldsByTaleId(@RequestBody @Valid CatalogueGetTableFieldByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getTableFieldsByTaleId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据表id增加表字段信息",
      nickname = "addTableFieldByTaleId"
   )
   @PostMapping({"addTableFieldByTaleId"})
   public Payload<CatalogueTableFieldDetailResponse> addTableFieldByTaleId(@RequestBody @Valid CatalogueAddTableFieldByTableIdRequest request) {
      Assert.notNull(request.getSysTableId(), "表id不能为空");
      return new Payload(this.catalogueOrgSysService.addTableFieldByTaleId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "通过表id删除表字段信息",
      nickname = "deleteTableFieldByTaleId"
   )
   @DeleteMapping({"deleteTableFieldByTaleId"})
   public Payload<Boolean> deleteTableFieldByTaleId(@RequestBody @Valid CatalogueOperatorTableFieldByFieldIdRequest request) {
      return new Payload(this.catalogueOrgSysService.deleteTableFieldByTaleId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "通过表id编辑表字段信息",
      nickname = "editTableFieldByTaleId"
   )
   @PutMapping({"editTableFieldByTaleId"})
   public Payload<CatalogueTableFieldDetailResponse> enableTableFieldByTaleId(@RequestBody @Valid CatalogueEditTableFieldByTableIdRequest request) {
      return new Payload(this.catalogueOrgSysService.editTableFieldByTaleId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "通过表id停用表字段",
      nickname = "disableTableFieldByTaleId"
   )
   @PostMapping({"disableTableFieldByTaleId"})
   public Payload<Boolean> disableTableFieldByTaleId(@RequestBody @Valid CatalogueOperatorTableFieldByFieldIdRequest request) {
      return new Payload(this.catalogueOrgSysService.disanbleTableFieldByTaleId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "通过表id启用表字段",
      nickname = "enableTableFieldByTaleId"
   )
   @PostMapping({"enableTableFieldByTaleId"})
   public Payload<Boolean> enableTableFieldByTaleId(@RequestBody @Valid CatalogueOperatorTableFieldByFieldIdRequest request) {
      return new Payload(this.catalogueOrgSysService.enableTableFieldByTaleId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "通过字段表id获取表字段信息详情",
      nickname = "getFieldDetailByFieldId"
   )
   @PostMapping({"getFieldDetailByFieldId"})
   public Payload<CatalogueTableFieldDetailResponse> getFieldDetailByFieldId(@RequestBody @Valid CatalogueGetFieldDetailByFieldIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getFieldDetailByFieldId(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据字段名和表id查询字段表信息",
      nickname = "selectFieldByTableIdAndFieldName"
   )
   @PostMapping({"selectFieldByTableIdAndFieldName"})
   public Payload<CatalogueTableFieldDO> selectFieldByTableIdAndFieldName(@RequestBody @Valid CatalogueGetFieldByTableIdAndFieldNameRequest request) {
      return new Payload(this.catalogueTableFieldService.selectFieldByTableIdAndFieldName(request));
   }

   @OperationLog(
      title = "目录",
      businessType = "提交目录申请"
   )
   @ApiOperation(
      value = "发布目录编制",
      nickname = "publishCatalogue"
   )
   @PostMapping({"publishCatalogue"})
   public Payload<Boolean> publishCatalogue(@RequestBody @Valid CataloguePublishCatalogueRequest request) {
      return new Payload(this.catalogueOrgSysService.publishCatalogue(request));
   }

   @OperationLog(
      title = "目录",
      businessType = "一键提交目录申请"
   )
   @ApiOperation(
      value = "一键发布",
      nickname = "publishAll",
      hidden = true
   )
   @PostMapping({"publishAll"})
   public Payload<Boolean> publishAll(@RequestBody @Valid CataloguePublishAllCatalogueRequest request) {
      return new Payload(this.catalogueOrgSysService.publishAll(request));
   }

   @ApiOperation(
      value = "取消目录编制",
      nickname = "cancelCatalogue",
      hidden = true
   )
   @PostMapping({"cancelCatalogue"})
   public Payload<Boolean> cancelCatalogue(@RequestBody @Valid CatalogueCancelCatalogueRequest request) {
      return new Payload(false);
   }

   @ApiOperation(
      value = "禁用启用导出",
      nickname = "disableExport"
   )
   @PostMapping({"disable/export"})
   public Payload<Boolean> disableExport(@RequestBody @Valid DisableExportRequest request) {
      return new Payload(this.catalogueOrgSysService.disableExport(request));
   }

   @ApiOperation(
      value = "获取类项目",
      nickname = "findCategoryItemCatalogue"
   )
   @GetMapping({"findCategoryItemCatalogue"})
   public Payload<List<CatalogueResourceCategoryDO>> findCategoryItemCatalogue() {
      return new Payload(this.catalogueSysTableService.findCategoryItemCatalogue());
   }

   @ApiOperation(
      value = "获取事项列",
      nickname = "findMatter"
   )
   @PostMapping({"findMatter"})
   public Payload<PageBean<TableMatterDTO>> findMatter(@RequestBody @Valid TableMatterRequest request) {
      return new Payload(this.catalogueSysTableService.findMatter(request));
   }

   @ApiOperation(
      value = "手动处理映射rowId",
      nickname = "refresh"
   )
   @PostMapping({"refresh/uuids"})
   public Payload<Boolean> refresh() {
      return new Payload(this.catalogueSysTableService.refresh());
   }
}

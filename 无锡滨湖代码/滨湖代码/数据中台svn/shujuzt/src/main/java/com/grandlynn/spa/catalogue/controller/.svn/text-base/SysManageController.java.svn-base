package com.grandlynn.spa.catalogue.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddSysByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDeleteSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsPageByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSysByOrgIdResponse;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.dto.TablesDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.ForeignSysDO;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.ForeignSysService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"系统管理模块"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/sysManage"})
public class SysManageController {
   @Resource
   private CatalogueOrgSysService catalogueOrgSysService;
   @Resource
   private ForeignSysService foreignSysService;

   @ApiOperation(
      value = "查询组织节点id下挂载的系统列表(支持分页)",
      nickname = "getSystemsByOrgId"
   )
   @PostMapping({"getSystemsByOrgId"})
   @Authorize
   public Payload<PageBean<SysContentDTO>> getSystemsPageByOrgId(@RequestBody @Valid CatalogueGetSystemsPageByOrgIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getSystemsPageByOrgId(request, 1));
   }

   @ApiOperation(
      value = "新增指定组织节点下的系统信息(根据组织节点id增加系统信息)",
      nickname = "addSysByOrgId"
   )
   @PostMapping({"addSysByOrgId"})
   public Payload<CatalogueGetSysByOrgIdResponse> addSysByOrgId(@RequestBody @Valid CatalogueAddSysByOrgIdRequest request) {
      return new Payload(this.catalogueOrgSysService.addSysByOrgId(request, 1));
   }

   @ApiOperation(
      value = "根据系统id查询系统详情",
      nickname = "getSysDetailBySysId"
   )
   @PostMapping({"getSysDetailBySysId"})
   public Payload<CatalogueGetSysByOrgIdResponse> getSysDetailBySysId(@RequestBody @Valid CatalogueGetSysBySysIdRequest request) {
      return new Payload(this.catalogueOrgSysService.getSysDetailBySysId(request, 1));
   }

   @ApiOperation(
      value = "根据系统id编辑系统内容",
      nickname = "editSysBySysId"
   )
   @PutMapping({"editSysBySysId"})
   public Payload<CatalogueGetSysByOrgIdResponse> editSysBySysId(@RequestBody @Valid CatalogueEditSysBySysIdRequest request) {
      return new Payload(this.catalogueOrgSysService.editSysBySysId(request, 1));
   }

   @ApiOperation(
      value = "根据系统id删除系统内容",
      nickname = "deleteSysBySysId"
   )
   @DeleteMapping({"deleteSysBySysId"})
   public Payload<Boolean> deleteSysBySysId(@RequestBody @Valid CatalogueDeleteSysBySysIdRequest request) {
      return new Payload(this.catalogueOrgSysService.deleteSysBySysId(request, 1));
   }

   @ApiOperation(
      value = "根据名称查询编目系统",
      nickname = "getCatalogueSysByName"
   )
   @GetMapping({"getCatalogueSysByName"})
   public Payload<List<CatalogueOrgSysDO>> getCatalogueSysByName(@RequestParam String name) {
      return new Payload(this.catalogueOrgSysService.list((new LambdaQueryWrapper<CatalogueOrgSysDO>()).like(CatalogueOrgSysDO::getSysName, name)));
   }

   @ApiOperation(
      value = "根据名称查询外部系统",
      nickname = "getForeignByName"
   )
   @GetMapping({"getForeignSysByName"})
   public Payload<List<ForeignSysDO>> getForeignSysByName(@RequestParam String name) {
      return new Payload(this.foreignSysService.list((new LambdaQueryWrapper<ForeignSysDO>()).like(ForeignSysDO::getName, name)));
   }

   @ApiOperation(
      value = "根据系统ids查询tables信息",
      nickname = "getTablesBySysIds"
   )
   @PostMapping({"getTablesBySysIds"})
   public Payload<PageBean<TablesDTO>> getTablesBySysIds(@RequestBody CatalogueGetTablesBySysIdsRequest request) {
      return new Payload(this.catalogueOrgSysService.getTablesBySysIds(request));
   }

}

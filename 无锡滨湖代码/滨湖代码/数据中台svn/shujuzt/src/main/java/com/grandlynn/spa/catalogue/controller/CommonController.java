package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.domain.request.CatalogueGetDropDownListRequest;
import com.grandlynn.spa.catalogue.dto.DropDownDataDTO;
import com.grandlynn.spa.catalogue.dto.DropDownWuXiDataDTO;
import com.grandlynn.spa.catalogue.dto.EnumsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceApplicationSceneDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceDomainDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceIndustryCategoryDO;
import com.grandlynn.spa.catalogue.entity.ForeignCityResourceDomainDO;
import com.grandlynn.spa.catalogue.service.CatalogueCommonService;
import com.grandlynn.util.config.Payload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"公共数据模块"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/common"})
public class CommonController {
   private static final Logger log = LoggerFactory.getLogger(CommonController.class);
   @Resource
   private CatalogueCommonService catalogueCommonService;

   @ApiOperation(
      value = "查询整体枚举映射",
      nickname = "getEnums"
   )
   @PostMapping({"getEnums"})
   public Payload<List<EnumsDTO>> getEnums() {
      return new Payload(this.catalogueCommonService.getEnums());
   }

   @ApiOperation(
      value = "枚举数据列表查询",
      nickname = "getDropDownList"
   )
   @PostMapping({"getDropDownList"})
   public Payload<List<EnumsDTO>> getDropDownList(@RequestBody @Valid CatalogueGetDropDownListRequest request) {
      return new Payload(this.catalogueCommonService.getDropDownList(request));
   }

   @ApiOperation(
      value = "枚举数据列表查询",
      nickname = "getAllDropDownData"
   )
   @PostMapping({"getAllDropDownData"})
   public Payload<List<DropDownDataDTO>> getAllDropDownData() {
      return new Payload(this.catalogueCommonService.getAllDropDownData());
   }

   @ApiOperation(
      value = "获取数据领域和资源分类",
      nickname = "domain"
   )
   @GetMapping({"domain"})
   public Payload<List<ForeignCityResourceDomainDO>> getDomain() {
      return new Payload(this.catalogueCommonService.getDomain());
   }

   @ApiOperation(
      value = "获取资源应用范围",
      nickname = "domain"
   )
   @GetMapping({"resourceRange"})
   public Payload<List<EnumsDTO>> getResourceRangeEnums() {
      return new Payload(this.catalogueCommonService.getResourceRangeEnums());
   }

   @ApiOperation(
      value = "无锡枚举数据列表查询",
      nickname = "getWuXiAllDropDownData"
   )
   @PostMapping({"getWuXiAllDropDownData"})
   public Payload<List<DropDownWuXiDataDTO>> getWuXiAllDropDownData() {
      return new Payload(this.catalogueCommonService.getWuXiAllDropDownData());
   }

   @ApiOperation(
      value = "获取数据领域和资源分类",
      nickname = "domain"
   )
   @GetMapping({"resource-domain"})
   public Payload<List<CatalogueResourceDomainDO>> getResourceDomain() {
      return new Payload(this.catalogueCommonService.getResourceDomain());
   }

   @ApiOperation(
      value = "获取目录分类（按行业）",
      nickname = "domain"
   )
   @GetMapping({"industry-category"})
   public Payload<List<CatalogueResourceIndustryCategoryDO>> getIndustryCategory() {
      return new Payload(this.catalogueCommonService.getIndustryCategory());
   }

   @ApiOperation(
      value = "获取目录分类（按应用场景）",
      nickname = "domain"
   )
   @GetMapping({"application-scene"})
   public Payload<List<CatalogueResourceApplicationSceneDO>> getApplicationScene() {
      return new Payload(this.catalogueCommonService.getApplicationScene());
   }
}

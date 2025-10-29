package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgTypeRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgTypeDO;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueOrgTypeService;
import com.grandlynn.util.config.Payload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"组织分类管理模块"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/sysType"})
public class OrgTypeManageController {
   @Resource
   private CatalogueOrgTypeService catalogueSysTypeService;

   @ApiOperation(
      value = "组织分类管理列表",
      nickname = "queryOrgTypeList"
   )
   @GetMapping({"/queryOrgTypeList"})
   @Authorize
   public Payload<List<CatalogueOrgTypeDO>> queryOrgTypeList() {
      return new Payload(this.catalogueSysTypeService.listBy());
   }

   @ApiOperation(
      value = "保存组织分类",
      nickname = "saveOrgType"
   )
   @PostMapping({"/saveOrgType"})
   @Authorize
   public Payload<Boolean> saveOrgType(@RequestBody CatalogueOrgTypeRequest catalogueOrgTypeRequest) {
      return new Payload(this.catalogueSysTypeService.saveOrgType(catalogueOrgTypeRequest));
   }

   @ApiOperation(
      value = "修改组织分类",
      nickname = "editOrgType"
   )
   @PutMapping({"/editOrgType"})
   @Authorize
   public Payload<Boolean> editOrgType(@RequestBody CatalogueOrgTypeRequest catalogueOrgTypeRequest) {
      return new Payload(this.catalogueSysTypeService.editOrgType(catalogueOrgTypeRequest));
   }

   @ApiOperation(
      value = "删除组织分类",
      nickname = "deleteOrgType"
   )
   @DeleteMapping({"/deleteOrgType/{id}"})
   @Authorize
   public Payload<Boolean> deleteOrgType(@PathVariable("id") Long id) {
      return new Payload(this.catalogueSysTypeService.removeByOrgTypeId(id));
   }
}

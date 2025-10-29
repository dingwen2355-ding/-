package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.enums.OrgAddEnum;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgAddChildOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgAddEqualOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgAddOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgDeleteOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgEditOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgEnableOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgGetDetailRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgDetailResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgEditOrgResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.util.config.Payload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"组织管理模块"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/catalogueOrg"})
public class OrgController {
   private static final Logger log = LoggerFactory.getLogger(OrgController.class);
   @Resource
   private CatalogueOrgService catalogueOrgService;

   @ApiOperation(
      value = "组织为空时新增组织节点",
      nickname = "addOrg"
   )
   @PostMapping({"addOrg"})
   public Payload<Boolean> addOrg(@RequestBody @Valid CatalogueOrgAddOrgRequest request) {
      return new Payload(this.catalogueOrgService.addOrg(request, OrgAddEnum.ORG_ADD_NEW, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据节点代码或节点编码或节点全称查询节点信息",
      nickname = "findOrgByNodeCodeOrNodeNo"
   )
   @PostMapping({"findOrgByNodeCodeOrNodeNo"})
   public Payload<Boolean> findOrgByNodeCodeOrNodeNo(@RequestParam String tenantId, @RequestParam Long appId, @RequestParam(required = false) String nodeCode, @RequestParam(required = false) String nodeNo, @RequestParam(required = false) String nodeName) {
      BaseRequest request = new BaseRequest();
      request.setTenantId(tenantId);
      request.setAppId(appId);
      return new Payload(this.catalogueOrgService.findOrgByNodeCodeOrNodeNoOrNodeName(request, nodeCode, nodeNo, IntermediateEnum.INTERMEDIATE.getCode(), nodeName));
   }

   @ApiOperation(
      value = "添加同级",
      nickname = "addEqualOrg"
   )
   @PostMapping({"addEqualOrg"})
   public Payload<Boolean> addEqualOrg(@RequestBody @Valid CatalogueOrgAddEqualOrgRequest request) {
      return new Payload(this.catalogueOrgService.addOrg(request, OrgAddEnum.ORG_ADD_EQUAL, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "添加子级",
      nickname = "addChildOrg"
   )
   @PostMapping({"addChildOrg"})
   public Payload<Boolean> addChildOrg(@RequestBody @Valid CatalogueOrgAddChildOrgRequest request) {
      return new Payload(this.catalogueOrgService.addOrg(request, OrgAddEnum.ORG_ADD_CHILD, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "删除一个组织节点（下面存在子级不允许删除）",
      nickname = "deleteOrgById"
   )
   @DeleteMapping({"deleteOrgById"})
   public Payload<Boolean> deleteOrgById(@RequestBody @Valid CatalogueOrgDeleteOrgRequest request) {
      return new Payload(this.catalogueOrgService.deleteOrgById(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "编辑一个组织节点（修改组织节点）",
      nickname = "editOrgById"
   )
   @PutMapping({"editOrgById"})
   public Payload<CatalogueOrgEditOrgResponse> editOrgById(@RequestBody @Valid CatalogueOrgEditOrgRequest request) {
      return new Payload(this.catalogueOrgService.editOrgById(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "启用/禁用组织节点",
      nickname = "enableOrg"
   )
   @PutMapping({"enableOrg"})
   public Payload<Boolean> enableOrg(@RequestBody @Valid CatalogueOrgEnableOrgRequest request) {
      return new Payload(this.catalogueOrgService.enableOrg(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "根据id查询详情",
      nickname = "getDetailById"
   )
   @PostMapping({"getDetailById"})
   public Payload<CatalogueOrgDetailResponse> getDetailById(@RequestBody @Valid CatalogueOrgGetDetailRequest request) {
      return new Payload(this.catalogueOrgService.getDetailById(request, IntermediateEnum.INTERMEDIATE.getCode()));
   }

   @ApiOperation(
      value = "组织列表查询（默认查询全部）",
      nickname = "queryOrgTree"
   )
   @PostMapping({"/queryOrgTree"})
   @Authorize
   public Payload<CatalogueOrgQueryOrgTreeResponse> queryOrgTree(@RequestBody @Valid CatalogueOrgQueryOrgTreeRequest request) {
      return new Payload(this.catalogueOrgService.queryOrgTree(request, IntermediateEnum.INTERMEDIATE.getCode(), true));
   }

   @ApiOperation(
      value = "组织列表并且表数量查询（查询全部）",
      nickname = "queryOrgListAndTableCount"
   )
   @PostMapping({"/queryOrgListAndTableCount"})
   @Authorize
   public Payload<List<OrgNode>> queryOrgListAndTableCount() {
      return new Payload(this.catalogueOrgService.queryOrgListAndTableCount());
   }

   @ApiOperation(
      value = "组织列表查询（默认查询全部）",
      nickname = "queryOrgTreeUnLimit"
   )
   @PostMapping({"/queryOrgTree/unLimit"})
   @Authorize
   public Payload<CatalogueOrgQueryOrgTreeResponse> queryOrgTreeUnLimit(@RequestBody @Valid CatalogueOrgQueryOrgTreeRequest request) {
      return new Payload(this.catalogueOrgService.queryOrgTree(request, IntermediateEnum.INTERMEDIATE.getCode(), false));
   }

   @ApiOperation(
      value = "组织列表查询（默认查询全部）",
      nickname = "queryOrgTreeUnLimit"
   )
   @GetMapping({"/queryOrgList"})
   @Authorize
   public Payload<List<CatalogueOrgDetailResponse>> queryOrgList() {
      return new Payload(this.catalogueOrgService.queryOrgList(0L));
   }

   @ApiOperation(
      value = "根据id查询信息",
      nickname = "getById"
   )
   @GetMapping({"/getNodeNameSimplifyById/{id}"})
   public Payload<String> getNodeNameSimplifyById(@PathVariable Long id) {
      return new Payload(this.catalogueOrgService.getNodeNameSimplifyById(id));
   }

   @ApiOperation(
      value = "排除登录人自己的组织部门（需求申请使用））",
      nickname = "queryUnOwnOrgList"
   )
   @PostMapping({"/queryUnOwnOrgList"})
   @Authorize
   public Payload<CatalogueOrgQueryOrgTreeResponse> queryUnOwnOrgList(@RequestBody @Valid CatalogueOrgQueryOrgTreeRequest request) {
      return new Payload(this.catalogueOrgService.queryUnOwnOrgList(request));
   }

   @ApiOperation(
      value = "1级组织部门",
      nickname = "queryOrgList"
   )
   @PostMapping({"/queryOrgList"})
   @Authorize
   public Payload<CatalogueOrgQueryOrgTreeResponse> queryOrgList(@RequestBody @Valid CatalogueOrgQueryOrgTreeRequest request) {
      request.setContentType(1);
      return new Payload(this.catalogueOrgService.queryUnOwnOrgList(request));
   }
}

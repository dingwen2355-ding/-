package com.grandlynn.spa.catalogue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.TotalCompareRequest;
import com.grandlynn.spa.catalogue.domain.response.TotalCompareTableLogBySysResponse;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.TotalCompareFieldLogDO;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.TotalCompareTableLogService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.lang.invoke.SerializedLambda;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"totalCompare"})
@Api(
   tags = {"全量比对"}
)
public class TotalCompareController {
   @Resource
   TotalCompareTableLogService service;
   @Resource
   CatalogueOrgSysService catalogueOrgSysService;
   @Resource
   CatalogueSysTableService catalogueSysTableService;

   @PostMapping({"table/page"})
   @ApiOperation("目录结果(分页)")
   public Payload<PageBean<TotalCompareTableLogBySysResponse>> tablePage(@RequestBody @Valid TotalCompareRequest request) {
      return new Payload(this.service.getResult(request));
   }

   @PostMapping({"field"})
   @ApiOperation("字段结果")
   public Payload<List<TotalCompareFieldLogDO>> fieldList(@RequestBody @Valid TotalCompareRequest request) {
      return new Payload(this.service.getResultByTableLogId(request));
   }

   @PostMapping({"field/page"})
   @ApiOperation("字段结果(分页)")
   public Payload<PageBean<TotalCompareFieldLogDO>> fieldPage(@RequestBody @Valid TotalCompareRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      return new Payload(new PageBean(this.service.getResultByTableLogId(request)));
   }

   @PostMapping({"test"})
   @ApiOperation("测试全量比对-指定目录生成比对结果")
   @ApiImplicitParam(
      value = "tableIds",
      name = "目录id",
      required = true
   )
   public Payload<Void> calculate(Long[] tableIds) {
      Long[] var2 = tableIds;
      int var3 = tableIds.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Long tableId = var2[var4];
         this.service.calculate(tableId);
      }

      return new Payload();
   }

   @PostMapping({"test/unMatch"})
   @ApiOperation("测试全量比对-未比对成功的目录")
   public Payload<Void> unMatch() {
      this.service.testUnmatch();
      return new Payload();
   }

   @PostMapping({"test/all"})
   @ApiOperation("测试全量比对-所有目录生成比对结果")
   public Payload<Void> calculateAll() {
      Iterator var1 = this.catalogueOrgSysService.list().iterator();

      while(var1.hasNext()) {
         CatalogueOrgSysDO sys = (CatalogueOrgSysDO)var1.next();
         LambdaQueryWrapper<CatalogueSysTableDO> wp = (new LambdaQueryWrapper<CatalogueSysTableDO>()).eq(CatalogueSysTableDO::getOrgSysId, sys.getId());
         List<CatalogueSysTableDO> tables = this.catalogueSysTableService.list(wp);
         tables.stream().forEach((t) -> {
            this.service.calculate(t.getId());
         });
      }

      return new Payload();
   }


}

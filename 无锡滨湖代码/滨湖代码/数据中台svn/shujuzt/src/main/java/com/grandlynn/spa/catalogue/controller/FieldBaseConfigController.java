package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.domain.request.CatalogueBaseConfigQueryRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueFieldBaseConfigDO;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueFieldBaseConfigService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"catalogueFieldBaseConfig"})
@Api(
   tags = {"参数管理-数据字段参数"}
)
public class FieldBaseConfigController {
   @Resource
   private CatalogueFieldBaseConfigService service;

   @GetMapping({"selectOne"})
   @ApiOperation("单条数据查询")
   public Payload<CatalogueFieldBaseConfigDO> selectOne(Long id) {
      return new Payload(this.service.getById(id));
   }

   @PostMapping({"query"})
   @ApiOperation("分页条件查询")
   public Payload<PageBean<CatalogueFieldBaseConfigDO>> page(@RequestBody CatalogueBaseConfigQueryRequest request) {
      return new Payload(this.service.query(request));
   }

   @Authorize
   @DeleteMapping
   @ApiOperation("单条数据删除")
   public Payload<Void> delete(Long id) {
      this.service.removeById(id);
      return new Payload();
   }

   @Authorize
   @PutMapping
   @ApiOperation("单条数据保存")
   public Payload<Void> save(@RequestBody @Valid CatalogueFieldBaseConfigDO configDO) {
      this.service.saveOrUpdate(configDO);
      return new Payload();
   }
}

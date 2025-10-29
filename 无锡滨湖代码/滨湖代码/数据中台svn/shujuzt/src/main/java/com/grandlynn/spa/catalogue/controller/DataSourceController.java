package com.grandlynn.spa.catalogue.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.datasource.DatasourcePluginTypeEnum;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDatasourceQueryRequest;
import com.grandlynn.spa.catalogue.dto.ExportDatasourceTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceApiDO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDatabaseDO;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceApiService;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceDatabaseService;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Authorize
@RestController
@Api(
   tags = {"数源管理-主表"}
)
@RequestMapping({"/catalogueDataSource"})
public class DataSourceController {
   private static final Logger log = LoggerFactory.getLogger(DataSourceController.class);
   @Resource
   private CatalogueDataSourceService service;
   @Resource
   private CatalogueDataSourceApiService apiService;
   @Resource
   private CatalogueDataSourceDatabaseService dbService;

   @PostMapping({"query"})
   @ApiOperation("分页条件查询")
   public Payload<PageBean<CatalogueDataSourceDO>> page(@RequestBody @Valid CatalogueDatasourceQueryRequest request) {
      return new Payload(this.service.page(request));
   }

   @GetMapping({"get"})
   @ApiOperation("明细数据查询")
   public Payload<CatalogueDataSourceDO> get(Long id) {
      CatalogueDataSourceDO datasource = (CatalogueDataSourceDO)this.service.getById(id);
      if (null == datasource) {
         return new Payload(datasource);
      } else {
         List apis;
         if (DatasourcePluginTypeEnum.API.getCode().equals(datasource.getPluginType())) {
            LambdaQueryWrapper<CatalogueDataSourceApiDO> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(CatalogueDataSourceApiDO::getDataSourceId, datasource.getId());
            apis = this.apiService.list(queryWrapper.orderByDesc(BaseEntity::getUpdatedTime));
            datasource.setApi(CollUtil.isEmpty(apis) ? null : (CatalogueDataSourceApiDO)apis.get(0));
         } else {
            LambdaQueryWrapper<CatalogueDataSourceDatabaseDO> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(CatalogueDataSourceDatabaseDO::getDataSourceId, datasource.getId());
            apis = this.dbService.list(queryWrapper.orderByDesc(BaseEntity::getUpdatedTime));
            datasource.setDatabase(CollUtil.isEmpty(apis) ? null : (CatalogueDataSourceDatabaseDO)apis.get(0));
         }

         return new Payload(datasource);
      }
   }

   @GetMapping({"nameCheck"})
   @ApiOperation("名称可用检查(是否可用:true/false)")
   public Payload<Boolean> nameCheck(String name) {
      LambdaQueryWrapper<CatalogueDataSourceDO> wp = (new LambdaQueryWrapper<CatalogueDataSourceDO>()).eq(CatalogueDataSourceDO::getName, name);
      return new Payload(CollUtil.isEmpty(this.service.list(wp)));
   }

   @PostMapping({"connectivityCheck"})
   @ApiOperation("连通性检查(是否联通:true/false)")
   public Payload<Boolean> connectivityCheck(@RequestParam Long id) {
      return new Payload(this.service.connectivityCheck(id));
   }

   @Authorize
   @PutMapping
   @ApiOperation("单条数据保存")
   public Payload<Void> put(@RequestBody @Valid CatalogueDataSourceDO datasource) {
      this.service.saveOrUpdate(datasource);
      if (DatasourcePluginTypeEnum.API.getCode().equals(datasource.getPluginType())) {
         CatalogueDataSourceApiDO api = datasource.getApi();
         api.setDataSourceId(datasource.getId());
         api.setApiAlias(datasource.getName());
         this.apiService.saveOrUpdate(api);
      } else {
         CatalogueDataSourceDatabaseDO db = datasource.getDatabase();
         db.setDataSourceId(datasource.getId());
         this.dbService.saveOrUpdate(db);
      }

      return new Payload();
   }

   @Authorize
   @DeleteMapping
   @ApiOperation("单条数据删除")
   public Payload<Void> delete(Long id) {
      this.service.removeById(id);
      return new Payload();
   }

   @PostMapping({"templateTest"})
   @ApiOperation("模板测试")
   public Payload<Void> templateTest(@RequestBody @Valid ExportDatasourceTemplateDTO dto) {
      this.service.templateTest(dto);
      return new Payload();
   }

}

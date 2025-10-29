package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.utils.CheckDbConnectUtil;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDatasourceQueryRequest;
import com.grandlynn.spa.catalogue.dto.ExportDatasourceTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDatabaseDO;
import com.grandlynn.spa.catalogue.entity.CatalogueFileOperationLogDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.excel.listener.DataSourceExcelListener;
import com.grandlynn.spa.catalogue.mapper.CatalogueDataSourceMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceDatabaseService;
import com.grandlynn.spa.catalogue.service.CatalogueDataSourceService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("catalogueDataSourceService")
public class CatalogueDataSourceServiceImpl extends ServiceImpl<CatalogueDataSourceMapper, CatalogueDataSourceDO> implements CatalogueDataSourceService {
   @Resource
   private CatalogueOrgService orgService;
   @Resource
   private CatalogueOrgSysService sysService;
   @Resource
   private CatalogueDataSourceDatabaseService dataSourceDatabaseService;

   public PageBean<CatalogueDataSourceDO> page(CatalogueDatasourceQueryRequest request) {
      SysAdminUser user = SecurityUtil.currentUser();
      Map<Long, String> orgMap = (Map)this.orgService.list().stream().collect(Collectors.toMap(BaseEntity::getId, CatalogueOrgDO::getNodeName));
      Map<Long, String> sysMap = (Map)this.sysService.list().stream().collect(Collectors.toMap(BaseEntity::getId, CatalogueOrgSysDO::getSysName));
      LambdaQueryWrapper<CatalogueDataSourceDO> queryWrapper = (new QueryWrapper()).lambda();
      queryWrapper.orderByDesc(BaseEntity::getUpdatedTime);
      queryWrapper.like(StrUtil.isNotBlank(request.getCode()), CatalogueDataSourceDO::getCode, request.getCode());
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueDataSourceDO::getName, request.getName());
      queryWrapper.eq(null != request.getOrgId(), CatalogueDataSourceDO::getOrgId, request.getOrgId());
      queryWrapper.eq(null != request.getPluginType(), CatalogueDataSourceDO::getPluginType, request.getPluginType());
      queryWrapper.eq(null != request.getState(), CatalogueDataSourceDO::getState, request.getState());
      queryWrapper.ge(null != request.getBeginTime(), BaseEntity::getCreatedTime, request.getBeginTime());
      queryWrapper.le(null != request.getEndTime(), BaseEntity::getCreatedTime, request.getEndTime());
      queryWrapper.in(user.getOrgLimit(), CatalogueDataSourceDO::getOrgId, user.getOrgids());
      PageUtil.page(request.getPage(), request.getSize());
      List<CatalogueDataSourceDO> datasource = ((CatalogueDataSourceMapper)this.baseMapper).selectList(queryWrapper);
      datasource.stream().forEach((d) -> {
         d.setOrgName((String)orgMap.get(d.getOrgId()));
         d.setSysName((String)sysMap.get(d.getSysId()));
      });
      return new PageBean(datasource);
   }

   public void templateTest(ExportDatasourceTemplateDTO dto) {
      DataSourceExcelListener listener = new DataSourceExcelListener((FastFileStorageClient)null, this, this.dataSourceDatabaseService, this.orgService, this.sysService, (CatalogueFileOperationLogDO)null, this.constructBaseRequest());
      listener.invoke((ExportDatasourceTemplateDTO)dto, (AnalysisContext)null);
   }

   public Boolean connectivityCheck(Long id) {
      LambdaQueryWrapper<CatalogueDataSourceDatabaseDO> queryWp = new LambdaQueryWrapper();
      queryWp.eq(CatalogueDataSourceDatabaseDO::getDataSourceId, id);
      queryWp.last("limit 1");
      CatalogueDataSourceDatabaseDO ent = this.dataSourceDatabaseService.getOne(queryWp);
      boolean pass = false;
      boolean var11 = false;

      Boolean var5;
      try {
         var11 = true;
         CheckDbConnectUtil.check(ent.getDatabaseType(), ent.getDatabaseName(), ent.getPort(), ent.getIp(), ent.getUsername(), ent.getDatabasePwd());
         pass = true;
         var5 = true;
         var11 = false;
      } catch (Exception var12) {
         throw new ApplicationException(var12.getMessage());
      } finally {
         if (var11) {
            LambdaUpdateWrapper<CatalogueDataSourceDO> updateWp = new LambdaUpdateWrapper();
            updateWp.eq(BaseEntity::getId, id);
            updateWp.set(CatalogueDataSourceDO::getState, pass ? 1 : 2);
            this.update(updateWp);
         }
      }

      LambdaUpdateWrapper<CatalogueDataSourceDO> updateWp = new LambdaUpdateWrapper();
      updateWp.eq(BaseEntity::getId, id);
      updateWp.set(CatalogueDataSourceDO::getState, pass ? 1 : 2);
      this.update(updateWp);
      return var5;
   }

   private BaseRequest constructBaseRequest() {
      BaseRequest baseRequest = new BaseRequest();
      baseRequest.setAppId(11L);
      baseRequest.setTenantId("11");
      return baseRequest;
   }

}

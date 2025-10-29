package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.VersionStateEnum;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldDetailByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldVersionByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionDetailByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetTableResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetVersionsResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueTableFieldDetailResponse;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldVersionDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgSysVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueSysTableVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldVersionMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.util.pageHelper.PageBean;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import java.lang.invoke.SerializedLambda;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("catalogueOrgSysVersionService")
public class CatalogueOrgSysVersionServiceImpl extends ServiceImpl<CatalogueOrgSysVersionMapper, CatalogueOrgSysVersionDO> implements CatalogueOrgSysVersionService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueOrgSysVersionServiceImpl.class);
   @Resource
   private CatalogueTableFieldVersionMapper catalogueTableFieldVersionMapper;
   @Resource
   private CatalogueSysTableVersionMapper catalogueSysTableVersionMapper;
   @Resource
   private CatalogueOrgSysVersionMapper catalogueOrgSysVersionMapper;
   @Resource
   private CatalogueOrgContentRelationVersionMapper catalogueOrgContentRelationVersionMapper;
   @Resource
   private CatalogueVersionLogService versionLogService;

   public PageBean<CatalogueGetTableResponse> getTablesVersion(CatalogueGetTableVersionRequest request) {
      SysAdminUser user = SecurityUtil.currentUser();
      if (null == request.getOrgId() && user.getOrgLimit()) {
         request.setLimitOrgIds(user.getOrgids());
      }

      CatalogueGetVersionsResponse versionsResponse = this.versionLogService.getVersionInfoByVersionNo(request.getVersionNo());
      log.info("====>versionsResponse:{}", versionsResponse);
      Integer versionState = versionsResponse.getVersionState();
      PageUtil.page(request.getPage(), request.getSize());
      List<CatalogueSysTableVersionDO> list = this.catalogueSysTableVersionMapper.getTablesVersionBySysId(request);
      Iterator var6 = list.iterator();

      while(var6.hasNext()) {
         CatalogueSysTableVersionDO tableVersionDO = (CatalogueSysTableVersionDO)var6.next();
         tableVersionDO.setId(tableVersionDO.getOldId());
         if (VersionStateEnum.DISABLED.getCode().equals(versionState)) {
            tableVersionDO.setTableState(ApproveStateEnum.BLOCK.getCode());
         }
      }

      this.constructFortDataBaseType(list, request.getVersionNo());
      return ObjectCloneUtils.convertPageBean(new PageBean(list), CatalogueGetTableResponse.class);
   }

   private List<CatalogueSysTableVersionDO> constructFortDataBaseType(List<CatalogueSysTableVersionDO> list, String versionNo) {
      Set<Long> ids = new HashSet();
      Iterator var4 = list.iterator();

      while(var4.hasNext()) {
         CatalogueSysTableVersionDO tableDO = (CatalogueSysTableVersionDO)var4.next();
         ids.add(tableDO.getOrgSysId());
      }

      List<SysContentDTO> sysContentDTOList = this.catalogueOrgContentRelationVersionMapper.findSysContentByOrgId((Long)null, 1, versionNo);
      Map<Long, String> fortDataBaseMap = new HashMap();
      Iterator var6 = sysContentDTOList.iterator();

      while(var6.hasNext()) {
         SysContentDTO dto = (SysContentDTO)var6.next();
         fortDataBaseMap.put(dto.getSoldId(), dto.getFortDatabaseType());
      }

      var6 = list.iterator();

      while(var6.hasNext()) {
         CatalogueSysTableVersionDO tableDO = (CatalogueSysTableVersionDO)var6.next();
         Long sysId = tableDO.getOrgSysId();
         String fortType = (String)fortDataBaseMap.get(sysId);
         tableDO.setFortDatabaseType(fortType);
      }

      return list;
   }

   public List<SystemsDTO> getSystemsByOrgIds(CatalogueGetSystemsByOrgIdsRequest request) {
      return this.catalogueOrgContentRelationVersionMapper.findSystemsByOrgIds(request.getOrgIds(), request.getVersionNo());
   }

   public CatalogueGetTableResponse getTableVersionByTableId(CatalogueGetTableVersionDetailByTableIdRequest request) {
      CatalogueGetTableResponse response = new CatalogueGetTableResponse();
      QueryWrapper<CatalogueSysTableVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(CatalogueSysTableVersionDO::getOldId, request.getId())
              .le(CatalogueSysTableVersionDO::getVersionNo, request.getVersionNo())
              .orderByDesc(CatalogueSysTableVersionDO::getVersionNo)
              .last("limit 1");
      CatalogueSysTableVersionDO tableDO = (CatalogueSysTableVersionDO)this.catalogueSysTableVersionMapper.selectOne(queryWrapper);
      if (tableDO != null) {
         this.constructFortDataBaseType(tableDO);
         response = (CatalogueGetTableResponse)tableDO.clone(CatalogueGetTableResponse.class);
      }

      return response;
   }

   private CatalogueSysTableVersionDO constructFortDataBaseType(CatalogueSysTableVersionDO tableVersionDO) {
      QueryWrapper<CatalogueOrgSysVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.<CatalogueOrgSysVersionDO>lambda().eq(CatalogueOrgSysVersionDO::getOldId, tableVersionDO.getOrgSysId()).eq(CatalogueOrgSysVersionDO::getVersionNo, tableVersionDO.getVersionNo());
      List<CatalogueOrgSysVersionDO> list = this.catalogueOrgSysVersionMapper.selectList(queryWrapper);
      if (!list.isEmpty()) {
         CatalogueOrgSysVersionDO versionDO = (CatalogueOrgSysVersionDO)list.get(0);
         String fortDatabaseType = versionDO.getFortDatabaseType();
         tableVersionDO.setFortDatabaseType(fortDatabaseType);
      }

      return tableVersionDO;
   }

   public PageBean<CatalogueTableFieldDetailResponse> getTableFieldsVersionByTaleId(CatalogueGetTableFieldVersionByTableIdRequest request) {
      CatalogueGetVersionsResponse versionsResponse = this.versionLogService.getVersionInfoByVersionNo(request.getVersionNo());
      log.info("====>versionsResponse:{}", versionsResponse);
      Integer versionState = versionsResponse.getVersionState();
      PageUtil.page(request.getPage(), request.getSize());
      List<CatalogueTableFieldVersionDO> list = this.catalogueTableFieldVersionMapper.getTableFieldsVersionByTaleId(request);
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         CatalogueTableFieldVersionDO fieldVersionDO = (CatalogueTableFieldVersionDO)var5.next();
         if (VersionStateEnum.DISABLED.getCode().equals(versionState)) {
            fieldVersionDO.setFieldState(ApproveStateEnum.BLOCK.getCode());
         }
      }

      return ObjectCloneUtils.convertPageBean(new PageBean(list), CatalogueTableFieldDetailResponse.class);
   }

   public CatalogueTableFieldDetailResponse getFieldDetailByFieldId(CatalogueGetFieldDetailByFieldIdRequest request) {
      QueryWrapper<CatalogueTableFieldVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(BaseEntity::getId, request.getId());
      CatalogueTableFieldVersionDO fieldDO = (CatalogueTableFieldVersionDO)this.catalogueTableFieldVersionMapper.selectOne(queryWrapper);
      return (CatalogueTableFieldDetailResponse)fieldDO.clone(CatalogueTableFieldDetailResponse.class);
   }

   public List<TablesAndFieldsDTO> getTablesAndFieldsBySysId(CatalogueGetTablesAndFieldsBySysIdRequest request) {
      return this.catalogueSysTableVersionMapper.getTablesAndFieldsBySysId(request);
   }

}

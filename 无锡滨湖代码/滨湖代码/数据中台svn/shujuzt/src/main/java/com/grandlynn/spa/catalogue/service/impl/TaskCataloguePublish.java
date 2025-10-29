package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.enums.StateEnum;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueSysTableMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import java.lang.invoke.SerializedLambda;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskCataloguePublish {
   private static final Logger log = LoggerFactory.getLogger(TaskCataloguePublish.class);
   @Resource
   private CatalogueSysTableMapper catalogueSysTableMapper;
   @Resource
   private CatalogueTableFieldMapper catalogueTableFieldMapper;

   @Async("asyncExecutorService")
   public void updateTableState(BaseRequest request, Long tableId, Long userId) {
      CatalogueSysTableDO tableDO = new CatalogueSysTableDO();
      tableDO.setId(tableId);
      tableDO.setPublisher(userId);
      tableDO.setTableState(ApproveStateEnum.FIRST.getCode());
      tableDO.setTableChanged(StateEnum.NO.getCode());
      tableDO.setTableOptioin("");
      this.catalogueSysTableMapper.updateById(tableDO);
   }

   public void updateAllTableState(BaseRequest request) {
      LambdaUpdateWrapper<CatalogueSysTableDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(CatalogueSysTableDO::getIntermediate, IntermediateEnum.INTERMEDIATE.getCode())
              .eq(CatalogueSysTableDO::getTableState, ApproveStateEnum.UN_COMMIT.getCode())
              .eq(SecurityUtil.currentUser().getOrgLimit(), CatalogueSysTableDO::getOrgId, SecurityUtil.currentUser().getOrgids()[0])
              .set(CatalogueSysTableDO::getPublisher, SecurityUtil.currentUserId())
              .set(CatalogueSysTableDO::getTableState, ApproveStateEnum.FIRST.getCode())
              .set(CatalogueSysTableDO::getTableOptioin, "")
              .set(CatalogueSysTableDO::getTableChanged, StateEnum.NO.getCode());
      this.catalogueSysTableMapper.update(null, updateWrapper);
   }

   @Async("asyncExecutorService")
   public void updateTableFieldState(BaseRequest request, Long tableId, Long userId) {
      LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(CatalogueTableFieldDO::getIntermediate, 1)
              .ne(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.BLOCK.getCode())
              .eq(CatalogueTableFieldDO::getSysTableId, tableId)
              .set(CatalogueTableFieldDO::getPublisher, userId)
              .set(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.FIRST.getCode());
      this.catalogueTableFieldMapper.update(null, updateWrapper);
   }

   public void updateAllTableFieldState(BaseRequest request) {
      LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(CatalogueTableFieldDO::getIntermediate, 1)
               .eq(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.UN_COMMIT.getCode())
              .set(CatalogueTableFieldDO::getPublisher, SecurityUtil.currentUserId())
              .set(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.FIRST.getCode());
      this.catalogueTableFieldMapper.update(null, updateWrapper);
   }

}

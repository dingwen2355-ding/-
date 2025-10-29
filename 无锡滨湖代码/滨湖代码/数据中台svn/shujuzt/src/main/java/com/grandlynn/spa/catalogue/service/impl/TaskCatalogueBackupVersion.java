package com.grandlynn.spa.catalogue.service.impl;

import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.dto.ApproveTableVersionMessageDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldVersionDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgSysVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueSysTableVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldVersionMapper;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskCatalogueBackupVersion {
   private static final Logger log = LoggerFactory.getLogger(TaskCatalogueBackupVersion.class);
   @Resource
   private CatalogueOrgVersionMapper orgVersionMapper;
   @Resource
   private CatalogueOrgContentRelationVersionMapper contentRelationVersionMapper;
   @Resource
   private CatalogueOrgSysVersionMapper sysVersionMapper;
   @Resource
   private CatalogueSysTableVersionMapper tableVersionMapper;
   @Resource
   private CatalogueTableFieldVersionMapper fieldVersionMapper;

   @Async("asyncExecutorService")
   public void backupOrgVersion(List<CatalogueOrgDO> orgDOS, ApproveTableVersionMessageDTO messageDto) {
      Iterator var3 = orgDOS.iterator();

      while(var3.hasNext()) {
         CatalogueOrgDO orgDO = (CatalogueOrgDO)var3.next();
         CatalogueOrgVersionDO orgVersionDO = (CatalogueOrgVersionDO)orgDO.clone(CatalogueOrgVersionDO.class);
         orgVersionDO.setId((Long)null);
         orgVersionDO.setOldId(orgDO.getId());
         orgVersionDO.setIntermediate(IntermediateEnum.DIS_INTERMEDIATE.getCode());
         orgVersionDO.setVersionNo(messageDto.getVersionNo());
         this.orgVersionMapper.insert(orgVersionDO);
      }

   }

   @Async("asyncExecutorService")
   public void backupContentRelationVersion(CatalogueOrgContentRelationDO contentRelationDO, ApproveTableVersionMessageDTO messageDto) {
      CatalogueOrgContentRelationVersionDO contentVersionDO = (CatalogueOrgContentRelationVersionDO)contentRelationDO.clone(CatalogueOrgContentRelationVersionDO.class);
      contentVersionDO.setId((Long)null);
      contentVersionDO.setOldId(contentRelationDO.getId());
      contentVersionDO.setIntermediate(IntermediateEnum.DIS_INTERMEDIATE.getCode());
      log.debug("==>backupContentRelationVersion===>contentVersionDO:{}", contentVersionDO);
      this.contentRelationVersionMapper.insert(contentVersionDO);
   }

   @Async("asyncExecutorService")
   public void backupSysVersion(CatalogueOrgSysDO sysDO) {
      log.debug("==>backupSysVersion===>sysDO:{}", sysDO);
      CatalogueOrgSysVersionDO sysVersionDO = (CatalogueOrgSysVersionDO)sysDO.clone(CatalogueOrgSysVersionDO.class);
      sysVersionDO.setId((Long)null);
      sysVersionDO.setOldId(sysDO.getId());
      sysVersionDO.setIntermediate(IntermediateEnum.DIS_INTERMEDIATE.getCode());
      this.sysVersionMapper.insert(sysVersionDO);
   }

   @Async("asyncExecutorService")
   public void backupTableVersion(CatalogueSysTableDO tableDO) {
      log.debug("==>backupTableVersion===>tableDO:{}", tableDO);
      CatalogueSysTableVersionDO tableVersionDO = (CatalogueSysTableVersionDO)tableDO.clone(CatalogueSysTableVersionDO.class);
      tableVersionDO.setId((Long)null);
      tableVersionDO.setOldId(tableDO.getId());
      tableVersionDO.setIntermediate(IntermediateEnum.DIS_INTERMEDIATE.getCode());
      this.tableVersionMapper.insert(tableVersionDO);
   }

   @Async("asyncExecutorService")
   public void backupFieldVersion(CatalogueTableFieldDO fieldDO) {
      log.debug("==>backupFieldVersion===>fieldDO:{}", fieldDO);
      CatalogueTableFieldVersionDO fieldVersionDO = (CatalogueTableFieldVersionDO)fieldDO.clone(CatalogueTableFieldVersionDO.class);
      fieldVersionDO.setId((Long)null);
      fieldVersionDO.setOldId(fieldDO.getId());
      fieldVersionDO.setIntermediate(IntermediateEnum.DIS_INTERMEDIATE.getCode());
      this.fieldVersionMapper.insert(fieldVersionDO);
   }
}

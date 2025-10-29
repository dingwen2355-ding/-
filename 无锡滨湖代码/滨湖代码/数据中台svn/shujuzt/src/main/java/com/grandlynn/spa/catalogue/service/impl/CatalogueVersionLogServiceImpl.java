package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.VersionStateEnum;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.common.utils.VersionUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetVersionsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueUpdateVersionUrlRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetVersionsResponse;
import com.grandlynn.spa.catalogue.dto.CatalogueVersionLogTableDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueVersionLogDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueVersionLogMapper;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.util.pageHelper.PageBean;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.grandlynn.util.pojo.ObjectCloneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("catalogueVersionLogService")
public class CatalogueVersionLogServiceImpl extends ServiceImpl<CatalogueVersionLogMapper, CatalogueVersionLogDO> implements CatalogueVersionLogService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueVersionLogServiceImpl.class);

   public PageBean<CatalogueGetVersionsResponse> getVersions(CatalogueGetVersionsRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      QueryWrapper<CatalogueVersionLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(request.getVersionState() != null, CatalogueVersionLogDO::getVersionState, request.getVersionState())
              .like(StringUtils.isNotBlank(request.getVersionNo()), CatalogueVersionLogDO::getVersionNo, request.getVersionNo())
              .orderByDesc(CatalogueVersionLogDO::getVersionSort);
      List<CatalogueVersionLogDO> list = ((CatalogueVersionLogMapper)this.baseMapper).selectList(queryWrapper);
      return ObjectCloneUtils.convertPageBean(new PageBean(list), CatalogueGetVersionsResponse.class);
   }

   public CatalogueVersionLogDO getMaxVersionInfo(BaseRequest request) {
      QueryWrapper<CatalogueVersionLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .orderByDesc(BaseEntity::getId)
              .last("limit 1");
      CatalogueVersionLogDO maxVersionDO = (CatalogueVersionLogDO)((CatalogueVersionLogMapper)this.baseMapper).selectOne(queryWrapper);
      log.info("getMaxVersionInfo==={}", maxVersionDO);
      return maxVersionDO;
   }

   public String getMaxVersionNo(BaseRequest request) {
      CatalogueVersionLogDO versionLogDO = this.getMaxVersionInfo(request);
      return versionLogDO == null ? "" : versionLogDO.getVersionNo();
   }

   public CatalogueGetVersionsResponse getVersionInfoByVersionNo(String versionNo) {
      QueryWrapper<CatalogueVersionLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(CatalogueVersionLogDO::getVersionNo, versionNo);
      CatalogueVersionLogDO maxVersionDO = (CatalogueVersionLogDO)((CatalogueVersionLogMapper)this.baseMapper).selectOne(queryWrapper);
      log.info("getVersionInfoByVersionNo==={}", maxVersionDO);
      return (CatalogueGetVersionsResponse)maxVersionDO.clone(CatalogueGetVersionsResponse.class);
   }

   public String getMaxVersionNo() {
      LambdaQueryWrapper<CatalogueVersionLogDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.orderByDesc(BaseEntity::getId);
      queryWrapper.last("limit 1");
      CatalogueVersionLogDO maxVersionDO = (CatalogueVersionLogDO)((CatalogueVersionLogMapper)this.baseMapper).selectOne(queryWrapper);
      log.info("getMaxVersionInfo==={}", maxVersionDO);
      return maxVersionDO != null ? maxVersionDO.getVersionNo() : "";
   }

   public List<CatalogueVersionLogDO> getVersionNoWord() {
      LambdaQueryWrapper<CatalogueVersionLogDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.isNull(CatalogueVersionLogDO::getVersionUrl);
      queryWrapper.ne(CatalogueVersionLogDO::getVersionState, VersionStateEnum.UNPUBLISHED.getCode());
      queryWrapper.orderByDesc(BaseEntity::getId);
      return ((CatalogueVersionLogMapper)this.baseMapper).selectList(queryWrapper);
   }

   public String generateVersionInfo(BaseRequest request) {
      LambdaQueryWrapper<CatalogueVersionLogDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.orderByDesc(BaseEntity::getId);
      queryWrapper.last("limit 1");
      CatalogueVersionLogDO catalogueVersionLogDO = (CatalogueVersionLogDO)this.getOne(queryWrapper);
      if (null == catalogueVersionLogDO) {
         return this.createVersionLog(request);
      } else if (VersionStateEnum.UNPUBLISHED.getCode().equals(catalogueVersionLogDO.getVersionState())) {
         return catalogueVersionLogDO.getVersionNo();
      } else {
         this.disableElseVersion(request);
         return this.createVersionLog(request);
      }
   }

   private void disableElseVersion(BaseRequest request) {
      LambdaUpdateWrapper<CatalogueVersionLogDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      updateWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      updateWrapper.eq(CatalogueVersionLogDO::getVersionState, VersionStateEnum.ENABLED.getCode());
      updateWrapper.set(CatalogueVersionLogDO::getVersionState, VersionStateEnum.DISABLED.getCode());
      this.update(updateWrapper);
   }

   public List<CatalogueVersionLogDO> findVersionLogs(BaseRequest request, Date startDate, Date endDate) {
      QueryWrapper<CatalogueVersionLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .between(BaseEntity::getCreatedTime, startDate, endDate);
      return ((CatalogueVersionLogMapper)this.baseMapper).selectList(queryWrapper);
   }

   public CatalogueVersionLogDO findVersionLogByVersionNo(BaseRequest request, String versionNo) {
      QueryWrapper<CatalogueVersionLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(CatalogueVersionLogDO::getVersionNo, versionNo);
      return (CatalogueVersionLogDO)((CatalogueVersionLogMapper)this.baseMapper).selectOne(queryWrapper);
   }

   public boolean updateVersionUrl(CatalogueUpdateVersionUrlRequest request) {
      LambdaUpdateWrapper<CatalogueVersionLogDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      updateWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      updateWrapper.eq(CatalogueVersionLogDO::getVersionNo, request.getVersionNo());
      updateWrapper.set(CatalogueVersionLogDO::getVersionUrl, request.getFullPath());
      return ((CatalogueVersionLogMapper)this.baseMapper).update(null, updateWrapper) > 0 ? Boolean.TRUE : Boolean.FALSE;
   }

   public List<CatalogueVersionLogDO> findAllVersionLogs(BaseRequest request) {
      QueryWrapper<CatalogueVersionLogDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .orderByDesc(BaseEntity::getCreatedTime);
      return ((CatalogueVersionLogMapper)this.baseMapper).selectList(queryWrapper);
   }

   public List<CatalogueVersionLogTableDTO> findAllOrgPublish(Timestamp beginTime, Timestamp endTime) {
      return ((CatalogueVersionLogMapper)this.baseMapper).findAllOrgPublish(beginTime, endTime);
   }

   private String createVersionLog(BaseRequest request) {
      CatalogueVersionLogDO versionLogDO = (CatalogueVersionLogDO)request.clone(CatalogueVersionLogDO.class);
      long versionSort = VersionUtil.generateVersionSort();
      versionLogDO.setVersionSort(versionSort);
      versionLogDO.setVersionNo(VersionUtil.generateVersionNo(versionSort));
      this.save(versionLogDO);
      return versionLogDO.getVersionNo();
   }

}

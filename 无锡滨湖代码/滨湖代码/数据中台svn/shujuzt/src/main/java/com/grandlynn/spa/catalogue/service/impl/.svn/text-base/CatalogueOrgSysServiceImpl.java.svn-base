package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.ContentTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.TableFlagEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.ShareStatusEnum;
import com.grandlynn.spa.catalogue.common.enums.sysmanage.DatabaseTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.sysmanage.UseRangeEnum;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddSysByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDeleteSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDeleteTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDisableTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEnableTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldDetailByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetOrgSysRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysAndOrgNameBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysByOrgIdAndSysNameRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysBySysNameRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsPageByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionDetailByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOperatorTableFieldByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CataloguePublishAllCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.CataloguePublishCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.DisableExportRequest;
import com.grandlynn.spa.catalogue.domain.request.GetResourceCodeRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportPresSystemRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueAddTableBySysIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSysByOrgIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSystemsByOrgIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetTableResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueTableFieldDetailResponse;
import com.grandlynn.spa.catalogue.dto.OrgDsServiceDTO;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.TablesDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceApiDO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableVersionDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.entity.OrgDsServiceDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.mapper.CatalogueDataSourceApiMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueDataSourceMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgSysMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueSysTableMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldMapper;
import com.grandlynn.spa.catalogue.mapper.OrgDsServiceMapper;
import com.grandlynn.spa.catalogue.mq.producer.SendOrgDsServiceProducer;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgSysVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldService;
import com.grandlynn.spa.catalogue.service.ReportCatalogueService;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;

import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catalogueOrgSysService")
public class CatalogueOrgSysServiceImpl extends ServiceImpl<CatalogueOrgSysMapper, CatalogueOrgSysDO> implements CatalogueOrgSysService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueOrgSysServiceImpl.class);
   @Resource
   private CatalogueOrgContentRelationMapper catalogueOrgContentRelationMapper;
   @Resource
   private CatalogueOrgSysMapper catalogueOrgSysMapper;
   @Resource
   private CatalogueSysTableMapper catalogueSysTableMapper;
   @Resource
   private CatalogueSysTableService catalogueSysTableService;
   @Resource
   private CatalogueTableFieldMapper catalogueTableFieldMapper;
   @Resource
   private CatalogueTableFieldService catalogueTableFieldService;
   @Resource
   private TaskCataloguePublish taskCataloguePublish;
   @Resource
   private CatalogueOrgSysVersionService catalogueOrgSysVersionService;
   @Resource
   private CatalogueSysTableVersionService catalogueSysTableVersionService;
   @Resource
   private CatalogueOrgMapper catalogueOrgMapper;
   @Resource
   private OrgDsServiceMapper orgDsServiceMapper;
   @Resource
   private CatalogueDataSourceMapper catalogueDataSourceMapper;
   @Resource
   private CatalogueDataSourceApiMapper catalogueDataSourceApiMapper;
   @Resource
   private SendOrgDsServiceProducer sendOrgDsServiceProducer;
   @Resource
   private SysAdminUserService sysAdminUserService;
   @Resource
   private ReportCatalogueService reportCatalogueService;

   public List<SystemsDTO> getSystemsByOrgIds(CatalogueGetSystemsByOrgIdsRequest request, int intermediate) {
      List<SystemsDTO> systemsDTOS = this.catalogueOrgContentRelationMapper.findSystemsByOrgIds(request.getOrgIds(), intermediate);
      return systemsDTOS;
   }

   public CatalogueGetSystemsByOrgIdResponse getSystemsByOrgId(CatalogueGetSystemsByOrgIdRequest request, int intermediate) {
      CatalogueGetSystemsByOrgIdResponse response = new CatalogueGetSystemsByOrgIdResponse();
      List<SysContentDTO> sysContentDTOList = this.catalogueOrgContentRelationMapper.findSysContentByOrgId(null == request.getOrgId() ? new Long[0] : new Long[]{request.getOrgId()}, ContentTypeEnum.CONTENT_TYPE_ORG.getCode(), request.getSysName(), intermediate, request.getSysStatus(), request.getSysType(), request.getNetType());
      if (sysContentDTOList != null && !sysContentDTOList.isEmpty()) {
         response.setSysContent(sysContentDTOList);
      }

      return response;
   }

   public PageBean<SysContentDTO> getSystemsPageByOrgId(CatalogueGetSystemsPageByOrgIdRequest request, int intermediate) {
      SysAdminUser user = SecurityUtil.currentUser();
      Long[] orgids = this.orgLimit(request.getOrgId(), user);
      PageUtil.page(request.getPage(), request.getSize());
      List<SysContentDTO> sysContentDTOList = this.catalogueOrgContentRelationMapper.findSysContentByOrgId(orgids, 1, request.getSysName(), intermediate, request.getSysStatus(), request.getSysType(), request.getNetType());
      return new PageBean(sysContentDTOList);
   }

   public CatalogueGetSysByOrgIdResponse addSysByOrgId(CatalogueAddSysByOrgIdRequest request, int intermediate) {
      CatalogueGetSysByOrgIdAndSysNameRequest getSysByOrgIdAndSysNameRequest = (CatalogueGetSysByOrgIdAndSysNameRequest)request.clone(CatalogueGetSysByOrgIdAndSysNameRequest.class);
      List<SystemsDTO> list = this.catalogueOrgSysMapper.getSysByOrgIdAndSysName(getSysByOrgIdAndSysNameRequest);
      if (list != null && !list.isEmpty()) {
         throw new ApplicationException("该组织下的系统全称" + request.getSysName() + "已存在！");
      } else {
         CatalogueOrgSysDO sysDO = (CatalogueOrgSysDO)request.clone(CatalogueOrgSysDO.class);
         this.catalogueOrgSysMapper.insert(sysDO);
         Long sysId = sysDO.getId();
         CatalogueOrgContentRelationDO orgContentRelationDO = (CatalogueOrgContentRelationDO)sysDO.clone(CatalogueOrgContentRelationDO.class);
         orgContentRelationDO.setOrgId(request.getOrgId());
         orgContentRelationDO.setOrgNo(request.getNodeNo());
         orgContentRelationDO.setContentId(sysId);
         this.catalogueOrgContentRelationMapper.insert(orgContentRelationDO);
         ReportPresSystemRequest systemRequest = this.convertToReportPresSystemRequest(sysDO);

         try {
            String cascadeguid = this.reportCatalogueService.presSystemRegister(systemRequest);
            if (StringUtils.isNotBlank(cascadeguid)) {
               this.catalogueOrgSysMapper.update(null, Wrappers.<CatalogueOrgSysDO>lambdaUpdate().set(CatalogueOrgSysDO::getExternalId, cascadeguid).eq(BaseEntity::getId, sysDO.getId()));
            }
         } catch (Exception var10) {
            log.info("上报信息系统注册数据失败：{}", JSON.toJSONString(systemRequest));
         }

         return (CatalogueGetSysByOrgIdResponse)sysDO.clone(CatalogueGetSysByOrgIdResponse.class);
      }
   }

   public CatalogueGetSysByOrgIdResponse getSysDetailBySysId(CatalogueGetSysBySysIdRequest request, int intermediate) {
      QueryWrapper<CatalogueOrgSysDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(BaseEntity::getId, request.getId()).eq(CatalogueOrgSysDO::getIntermediate, intermediate);
      CatalogueOrgSysDO sysDO = this.catalogueOrgSysMapper.selectOne(queryWrapper);
      return sysDO.clone(CatalogueGetSysByOrgIdResponse.class);
   }

   public CatalogueGetSysByOrgIdResponse editSysBySysId(CatalogueEditSysBySysIdRequest request, int intermediate) {
      CatalogueGetSysByOrgIdAndSysNameRequest getSysByOrgIdAndSysNameRequest = request.clone(CatalogueGetSysByOrgIdAndSysNameRequest.class);
      List<SystemsDTO> list = this.catalogueOrgSysMapper.getSysByOrgIdAndSysName(getSysByOrgIdAndSysNameRequest);
      String warnMsg = "";
      boolean exist = false;
      Iterator var7 = list.iterator();

      while(var7.hasNext()) {
         SystemsDTO systemsDTO = (SystemsDTO)var7.next();
         if (!systemsDTO.getSysId().equals(request.getId())) {
            warnMsg = "该节点下已经存在系统全称:" + systemsDTO.getSysName();
            exist = true;
            break;
         }
      }

      if (!exist) {
         CatalogueOrgSysDO sysDO = (CatalogueOrgSysDO)request.clone(CatalogueOrgSysDO.class);
         this.catalogueOrgSysMapper.updateById(sysDO);
         CatalogueOrgSysDO orgSysInfo = (CatalogueOrgSysDO)((CatalogueOrgSysMapper)this.baseMapper).selectById(request.getId());
         sysDO.setExternalId(orgSysInfo.getExternalId());
         ReportPresSystemRequest systemRequest = this.convertToReportPresSystemRequest(sysDO);
         if (StringUtils.isBlank(sysDO.getExternalId())) {
            try {
               String cascadeguid = this.reportCatalogueService.presSystemRegister(systemRequest);
               if (StringUtils.isNotBlank(cascadeguid)) {
                  this.catalogueOrgSysMapper.update(null,Wrappers.<CatalogueOrgSysDO>lambdaUpdate().set(CatalogueOrgSysDO::getExternalId, cascadeguid).eq(BaseEntity::getId, sysDO.getId()));
               }
            } catch (Exception var12) {
               log.info("上报信息系统注册数据失败：{}", JSON.toJSONString(systemRequest));
            }
         } else {
            try {
               this.reportCatalogueService.presSystemModify(systemRequest);
            } catch (Exception var11) {
               log.info("上报信息系统变更数据失败：{}", JSON.toJSONString(systemRequest));
            }
         }

         return (CatalogueGetSysByOrgIdResponse)sysDO.clone(CatalogueGetSysByOrgIdResponse.class);
      } else {
         log.error("节点:" + request.getOrgId() + warnMsg);
         throw new ApplicationException(warnMsg);
      }
   }

   public Boolean deleteSysBySysId(CatalogueDeleteSysBySysIdRequest request, int intermediate) {
      QueryWrapper<CatalogueSysTableDO> tableDOQueryWrapper = new QueryWrapper();
      tableDOQueryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(CatalogueSysTableDO::getOrgSysId, request.getId()).eq(CatalogueSysTableDO::getIntermediate, intermediate);
      List<CatalogueSysTableDO> tables = this.catalogueSysTableMapper.selectList(tableDOQueryWrapper);
      if (tables != null && !tables.isEmpty()) {
         throw new ApplicationException("当前系统存在目录编制！请勿删除！");
      } else {
         QueryWrapper<CatalogueOrgSysDO> queryWrapper = new QueryWrapper();
         queryWrapper.lambda()
                 .eq(BaseEntity::getTenantId, request.getTenantId())
                 .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
                 .eq(BaseEntity::getId, request.getId())
                 .eq(CatalogueOrgSysDO::getIntermediate, intermediate);
         return this.catalogueOrgSysMapper.delete(queryWrapper) > 0;
      }
   }

   public List<SysContentDTO> getSysByOrgId(CatalogueGetOrgSysRequest request, int intermediate) {
      return this.catalogueOrgContentRelationMapper.findSysContentByOrgId(new Long[]{request.getOrgId()}, ContentTypeEnum.CONTENT_TYPE_ORG.getCode(), (String)null, intermediate, (String)null, (String)null, (String)null);
   }

   public SysContentDTO getSysBySysNameAndNodeName(CatalogueGetSysBySysNameRequest request, int intermediate) {
      QueryWrapper<CatalogueOrgSysDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(CatalogueOrgSysDO::getSysName, request.getSysName()).eq(CatalogueOrgSysDO::getIntermediate, intermediate).eq(CatalogueOrgSysDO::getNodeName, request.getNodeName());
      CatalogueOrgSysDO orgSysDO =this.baseMapper.selectOne(queryWrapper);
      return null == orgSysDO ? null : orgSysDO.clone(SysContentDTO.class);
   }

   public PageBean<CatalogueGetTableResponse> getTables(CatalogueGetTableRequest request, int intermediate) {
      SysAdminUser user = SecurityUtil.currentUser();
      Long[] orgId = this.orgLimit(request.getOrgId(), user);
      LambdaQueryWrapper<CatalogueSysTableDO> wp = new LambdaQueryWrapper();
      wp.eq(BaseEntity::getTenantId, request.getTenantId());
      wp.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      wp.eq(CatalogueSysTableDO::getIntermediate, intermediate);
      wp.in(request.getTableState() != null, CatalogueSysTableDO::getTableState, request.getTableState());
      wp.like(StringUtils.isNotBlank(request.getDataResourceName()), CatalogueSysTableDO::getDataResourceName, request.getDataResourceName());
      wp.like(StringUtils.isNotBlank(request.getTableName()), CatalogueSysTableDO::getTableName, request.getTableName());
      wp.eq(null != request.getOrgSysId() && 0L != request.getOrgSysId(), CatalogueSysTableDO::getOrgSysId, request.getOrgSysId());
      wp.in(null != orgId, CatalogueSysTableDO::getOrgId, orgId);
      wp.isNull(null != request.getOrgSysId() && 0L == request.getOrgSysId(), CatalogueSysTableDO::getOrgSysId);
      wp.like(StringUtils.isNotBlank(request.getResourceCode()), CatalogueSysTableDO::getResourceCode, request.getResourceCode());
      wp.orderByDesc(BaseEntity::getUpdatedTime);
      PageUtil.page(request.getPage(), request.getSize());
      List<CatalogueSysTableDO> list = this.catalogueSysTableMapper.selectList(wp);
      this.constructFortDataBaseType(list, request.getOrgSysId());
      List<CatalogueOrgDO> orgDOList = this.catalogueOrgMapper.selectList(Wrappers.query());
      list.stream().forEach((item) -> {
         if (null != item.getOrgId()) {
            Optional<CatalogueOrgDO> orgOptional = orgDOList.stream().filter((org) -> {
               return org.getId().equals(item.getOrgId());
            }).findFirst();
            if (orgOptional.isPresent()) {
               item.setNodeName(((CatalogueOrgDO)orgOptional.get()).getNodeName());
            }
         }

      });
      return ObjectCloneUtils.convertPageBean(new PageBean(list), CatalogueGetTableResponse.class);
   }

   private Long[] orgLimit(Long orgId, SysAdminUser user) {
      if (null == orgId) {
         return user.getOrgLimit() ? user.getOrgids() : null;
      } else if (user.getOrgLimit()) {
         return Arrays.asList(user.getOrgids()).contains(orgId) ? this.sysAdminUserService.getOrgIdRecursive(orgId) : new Long[]{0L};
      } else {
         return this.sysAdminUserService.getOrgIdRecursive(orgId);
      }
   }

   private List<Long> getSysIdByOrgId(Long[] orgIds) {
      if (null != orgIds && orgIds.length >= 1) {
         LambdaQueryWrapper<CatalogueOrgContentRelationDO> wp = new LambdaQueryWrapper();
         wp.eq(CatalogueOrgContentRelationDO::getContentType, ContentTypeEnum.CONTENT_TYPE_ORG.getCode());
         wp.in(CatalogueOrgContentRelationDO::getOrgId, orgIds);
         List<CatalogueOrgContentRelationDO> relations = this.catalogueOrgContentRelationMapper.selectList(wp);
         return (List)relations.stream().map(CatalogueOrgContentRelationDO::getContentId).collect(Collectors.toList());
      } else {
         return null;
      }
   }

   private List<CatalogueSysTableVersionDO> constructFortDataBaseVersionType(List<CatalogueSysTableVersionDO> list, Long orgSysId) {
      if (orgSysId != null) {
         CatalogueOrgSysDO sysDO = (CatalogueOrgSysDO)this.catalogueOrgSysMapper.selectById(orgSysId);
         if (sysDO != null) {
            String fortDatabaseType = sysDO.getFortDatabaseType();
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
               CatalogueSysTableVersionDO tableDO = (CatalogueSysTableVersionDO)var5.next();
               tableDO.setFortDatabaseType(fortDatabaseType);
            }
         }
      } else {
         Set<Long> ids = new HashSet();
         Iterator var12 = list.iterator();

         while(var12.hasNext()) {
            CatalogueSysTableVersionDO tableDO = (CatalogueSysTableVersionDO)var12.next();
            ids.add(tableDO.getOrgSysId());
         }

         if (!ids.isEmpty()) {
            QueryWrapper<CatalogueOrgSysDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().in(BaseEntity::getId, ids);
            List<CatalogueOrgSysDO> sysDOList = this.catalogueOrgSysMapper.selectList(queryWrapper);
            Map<Long, String> fortDataBaseMap = new HashMap();
            Iterator var7 = sysDOList.iterator();

            while(var7.hasNext()) {
               CatalogueOrgSysDO sysDO = (CatalogueOrgSysDO)var7.next();
               fortDataBaseMap.put(sysDO.getId(), sysDO.getFortDatabaseType());
            }

            var7 = list.iterator();

            while(var7.hasNext()) {
               CatalogueSysTableVersionDO tableDO = (CatalogueSysTableVersionDO)var7.next();
               Long sysId = tableDO.getOrgSysId();
               String fortType = (String)fortDataBaseMap.get(sysId);
               tableDO.setFortDatabaseType(fortType);
            }
         }
      }

      return list;
   }

   private List<CatalogueSysTableDO> constructFortDataBaseType(List<CatalogueSysTableDO> list, Long orgSysId) {
      if (orgSysId != null) {
         CatalogueOrgSysDO sysDO = (CatalogueOrgSysDO)this.catalogueOrgSysMapper.selectById(orgSysId);
         if (sysDO != null) {
            String fortDatabaseType = sysDO.getFortDatabaseType();
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
               CatalogueSysTableDO tableDO = (CatalogueSysTableDO)var5.next();
               tableDO.setFortDatabaseType(fortDatabaseType);
            }
         }
      } else {
         Set<Long> ids = new HashSet();
         Iterator var12 = list.iterator();

         while(var12.hasNext()) {
            CatalogueSysTableDO tableDO = (CatalogueSysTableDO)var12.next();
            ids.add(tableDO.getOrgSysId());
         }

         if (!ids.isEmpty()) {
            QueryWrapper<CatalogueOrgSysDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().in(BaseEntity::getId, ids);
            List<CatalogueOrgSysDO> sysDOList = this.catalogueOrgSysMapper.selectList(queryWrapper);
            Map<Long, String> fortDataBaseMap = new HashMap();
            Iterator var7 = sysDOList.iterator();

            while(var7.hasNext()) {
               CatalogueOrgSysDO sysDO = (CatalogueOrgSysDO)var7.next();
               fortDataBaseMap.put(sysDO.getId(), sysDO.getFortDatabaseType());
            }

            var7 = list.iterator();

            while(var7.hasNext()) {
               CatalogueSysTableDO tableDO = (CatalogueSysTableDO)var7.next();
               Long sysId = tableDO.getOrgSysId();
               String fortType = (String)fortDataBaseMap.get(sysId);
               tableDO.setFortDatabaseType(fortType);
            }
         }
      }

      return list;
   }

   public List<TablesAndFieldsDTO> getTablesAndFieldsByTableIds(CatalogueGetTableBySysIdsRequest request, int intermediate) {
      PageUtil.page(request.getPage(), request.getSize());
      return this.catalogueSysTableMapper.getTablesAndFieldsBySysIds(request);
   }

   public List<TablesAndFieldsDTO> getTablesAndFieldsBySysId(CatalogueGetTablesAndFieldsBySysIdRequest request) {
      return this.catalogueSysTableMapper.getTablesAndFieldsBySysId(request);
   }

   public PageBean<TablesDTO> getTablesBySysIds(CatalogueGetTablesBySysIdsRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      List<TablesDTO> list = this.catalogueSysTableMapper.getTablesBySysIds(request);
      return new PageBean(list);
   }

   public CatalogueGetTableResponse getTableByTableId(CatalogueGetTableByTableIdRequest request, int intermediate) {
      QueryWrapper<CatalogueSysTableDO> queryWrapper = new QueryWrapper();
     queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(BaseEntity::getId, request.getId()).eq(CatalogueSysTableDO::getIntermediate, intermediate);
      CatalogueSysTableDO tableDO = (CatalogueSysTableDO)this.catalogueSysTableMapper.selectOne(queryWrapper);
      CatalogueGetTableResponse catalogueGetTableResponse = null;
      if (tableDO != null) {
         catalogueGetTableResponse = tableDO.clone(CatalogueGetTableResponse.class);
         List<CatalogueSysTableDO> list = new ArrayList();
         list.add(tableDO);
         this.constructFortDataBaseType(list, tableDO.getOrgSysId());
         CatalogueDataSourceApiDO dataSourceApiDO = this.catalogueDataSourceApiMapper.selectByTableId(request.getId());
         if (dataSourceApiDO != null) {
            catalogueGetTableResponse.setApiAlias(dataSourceApiDO.getApiAlias());
            if (dataSourceApiDO.getApiBody() != null) {
               catalogueGetTableResponse.setApiBody(dataSourceApiDO.getApiBody().toJSONString());
            }

            catalogueGetTableResponse.setApiHeader(dataSourceApiDO.getApiHeader());
            if (dataSourceApiDO.getApiResponse() != null) {
               catalogueGetTableResponse.setApiResponse(dataSourceApiDO.getApiResponse().toJSONString());
            }

            catalogueGetTableResponse.setApiAlias(dataSourceApiDO.getApiAlias());
            catalogueGetTableResponse.setApiType(dataSourceApiDO.getApiType());
            catalogueGetTableResponse.setApiUrl(dataSourceApiDO.getApiUrl());
            catalogueGetTableResponse.setRemark(dataSourceApiDO.getRemark());
         }
      }

      return catalogueGetTableResponse;
   }

   public CatalogueGetTableResponse getTableHistorylByTableId(CatalogueGetTableByTableIdRequest request, int intermediate) {
      CatalogueSysTableDO tableDO = this.catalogueSysTableMapper.selectById(request.getId());
      QueryWrapper<CatalogueSysTableVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(CatalogueSysTableVersionDO::getResourceCode, tableDO.getResourceCode()).orderByDesc(BaseEntity::getId).last(" LIMIT 1");
      CatalogueSysTableVersionDO tableVersionDO = this.catalogueSysTableVersionService.getOne(queryWrapper);
      if (tableVersionDO != null) {
         List<CatalogueSysTableVersionDO> list = new ArrayList();
         list.add(tableVersionDO);
         this.constructFortDataBaseVersionType(list, tableVersionDO.getOrgSysId());
      }

      return tableVersionDO != null ? (CatalogueGetTableResponse)tableVersionDO.clone(CatalogueGetTableResponse.class) : new CatalogueGetTableResponse();
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public CatalogueAddTableBySysIdResponse addTableBySysId(CatalogueAddTableBySysIdRequest request, int intermediate) {
      if (request.getOrgSysId() == 0L) {
         request.setOrgSysId((Long)null);
         request.setSysName((String)null);
      }

      List<CatalogueSysTableDO> list = this.catalogueSysTableService.getTablesBySysIdNew(request, request.getOrgSysId(), request.getOrgId(), request.getDataResourceName());
      if (list.stream().anyMatch((a) -> {
         return StringUtils.defaultString(a.getDataResourceName(), "").equals(StringUtils.defaultString(request.getDataResourceName(), ""));
      })) {
         throw new ApplicationException("该组织系统下资源已经存在" + request.getDataResourceName());
      } else {
         CatalogueSysTableDO tableDO = (CatalogueSysTableDO)request.clone(CatalogueSysTableDO.class);
         int level = 0;
         OptionalInt optionalInt = request.getItems().stream().mapToInt((item) -> {
            return Integer.parseInt(item.getDataLevel() == null ? "0" : item.getDataLevel().toString());
         }).max();
         if (optionalInt.isPresent()) {
            level = optionalInt.getAsInt();
         }

         tableDO.setTableFlag(TableFlagEnum.INSERT.getCode());
         if (level > 0 && level > Integer.parseInt(tableDO.getCatalogueLevel())) {
            tableDO.setCatalogueLevel(level + "");
         }

         this.catalogueSysTableMapper.insert(tableDO);
         if (CollectionUtil.isNotEmpty(request.getItems())) {
            List<CatalogueTableFieldDO> fieldDOS = (List)request.getItems().stream().map((item) -> {
               CatalogueTableFieldDO fieldDO = (CatalogueTableFieldDO)item.clone(CatalogueTableFieldDO.class);
               fieldDO.setSysTableId(tableDO.getId());
               return fieldDO;
            }).collect(Collectors.toList());
            this.catalogueTableFieldService.saveBatch(fieldDOS);
         }

         OrgDsServiceDO dsService = this.convertOrgDsService(tableDO);
         this.orgDsServiceMapper.insert(dsService);
         if (ApproveStateEnum.FIRST.getCode().equals(request.getTableState())) {
            log.info("开始异步更新待发布数据状态,入参:{}", request);
            Long userId = SecurityUtil.currentUserId();
            this.taskCataloguePublish.updateTableState(request, tableDO.getId(), userId);
            this.taskCataloguePublish.updateTableFieldState(request, tableDO.getId(), userId);
         }

         if (ShareStatusEnum.API_STATUS.getCode().toString().equals(request.getShareStatus())) {
         }

         return (CatalogueAddTableBySysIdResponse)tableDO.clone(CatalogueAddTableBySysIdResponse.class);
      }
   }

   public OrgDsServiceDO convertOrgDsService(CatalogueSysTableDO table) {
      if (Objects.isNull(table)) {
         return null;
      } else {
         OrgDsServiceDO service = new OrgDsServiceDO();
         service.setSystemId(table.getOrgSysId());
         service.setTableEnName(table.getTableName());
         CatalogueOrgSysDO var10000 = (CatalogueOrgSysDO)this.catalogueOrgSysMapper.selectById(table.getOrgSysId());
         CatalogueOrgDO org = (CatalogueOrgDO)this.catalogueOrgMapper.selectById(table.getOrgId());
         if (org != null) {
            service.setDeptId(org.getId());
            service.setDeptName(org.getNodeName());
         }

         service.setSystemName(table.getSysName());
         service.setTableId(table.getId());
         service.setTableCnName(table.getDataResourceName());
         service.setCycle(StrUtil.isBlank(table.getUpdatePeriod()) ? null : Integer.parseInt(table.getUpdatePeriod()));
         service.setShareType(StrUtil.isBlank(table.getTableShareType()) ? null : Integer.parseInt(table.getTableShareType()));
         service.setShareMode(table.getShareWayType());
         service.setDatabaseType((Integer)Optional.ofNullable(DatabaseTypeEnum.getCodeByName(table.getDatabaseType())).map(DatabaseTypeEnum::getCode).orElse(null));
         service.setDescribe(table.getTableRemark());
         service.setState(table.getTableState());
         service.setCreatedBy(table.getCreatedBy());
         service.setCreatedAt(Objects.isNull(table.getCreatedTime()) ? null : table.getCreatedTime().getTime());
         service.setUpdatedBy(table.getUpdatedBy());
         service.setUpdatedAt(Objects.isNull(table.getUpdatedTime()) ? null : table.getUpdatedTime().getTime());
         service.setResourceVersion((Long)null);
         service.setTempIdentification(1L);
         service.setDetyCode((String)null);
         service.setBrowse(0);
         service.setResourceCatalog(2);
         service.setOpenType(table.getOpenType());
         service.setCategoryId(table.getCategoryId());
         service.setItemId(table.getItemId());
         service.setCatalogueId(table.getCatalogueId());
         service.setDatabaseName(table.getDatabaseName());
         service.setDomainCode(table.getDomainCode());
         service.setIndustryCategoryCode(table.getIndustryCategoryCode());
         service.setApplicationSceneCode(table.getApplicationSceneCode());
         return service;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public CatalogueGetTableResponse editTableByTableId(CatalogueEditTableByTableIdRequest request, int intermediate) {
      CatalogueSysTableDO tableDO = (CatalogueSysTableDO)this.catalogueSysTableService.getById(request.getId());
      if (ApproveStateEnum.BLOCK.getCode().equals(tableDO.getTableState())) {
         throw new ApplicationException("已停用, 请先申请启用");
      } else {
         if (request.getOrgSysId() == 0L) {
            request.setOrgSysId((Long)null);
            request.setSysName((String)null);
         }

         List<CatalogueSysTableDO> list = this.catalogueSysTableService.getTablesBySysIdNew(request, request.getOrgSysId(), request.getOrgId(), request.getDataResourceName());
         if (list.stream().anyMatch((a) -> {
            return !a.getId().equals(request.getId()) && StringUtils.defaultString(a.getDataResourceName(), "").equals(StringUtils.defaultString(request.getDataResourceName(), ""));
         })) {
            throw new ApplicationException("该组织系统下资源已经存在" + request.getTableName());
         } else {
            Integer state = ApproveStateEnum.UN_COMMIT.getCode();
            Integer flag = TableFlagEnum.UPDATE.getCode();
            if (TableFlagEnum.DISABLE.getCode().equals(tableDO.getTableFlag()) || TableFlagEnum.ENABLE.getCode().equals(tableDO.getTableFlag()) || ApproveStateEnum.R_REJECT.getCode().equals(tableDO.getTableState()) || ApproveStateEnum.F_REJECT.getCode().equals(tableDO.getTableState())) {
               flag = null;
            }

            if (TableFlagEnum.INSERT.getCode().equals(tableDO.getTableFlag())) {
               flag = TableFlagEnum.INSERT.getCode();
            }

            tableDO = (CatalogueSysTableDO)request.clone(CatalogueSysTableDO.class);
            tableDO.setTableState(state);
            tableDO.setTableFlag(flag);
            if (this.resourceTypeChanged(tableDO)) {
            }

            this.catalogueSysTableMapper.updateById(tableDO);
            OrgDsServiceDO dsService = this.convertOrgDsService(tableDO);
            long existCount = this.orgDsServiceMapper.selectCount(new LambdaQueryWrapper<OrgDsServiceDO>().eq(OrgDsServiceDO::getTableId, dsService.getTableId()).last("limit 1"));
            if (existCount > 0) {
               this.orgDsServiceMapper.update(dsService, new LambdaQueryWrapper<OrgDsServiceDO>().eq(OrgDsServiceDO::getTableId, dsService.getTableId()));
            }

            CatalogueDataSourceApiDO dataSourceApiDO = this.catalogueDataSourceApiMapper.selectByTableId(request.getId());
            if (dataSourceApiDO != null) {
               dataSourceApiDO.setApiAlias(request.getApiAlias());

               try {
                  dataSourceApiDO.setApiBody(JSON.parseObject(request.getApiBody()));
               } catch (Exception var12) {
                  throw new ApplicationException("请求参数(Body) JSON转换异常，请输入正确的格式");
               }

               dataSourceApiDO.setApiHeader(request.getApiHeader());
               if (StringUtils.isNotEmpty(request.getApiResponse())) {
                  try {
                     dataSourceApiDO.setApiResponse(JSON.parseObject(request.getApiResponse()));
                  } catch (Exception var11) {
                     throw new ApplicationException("响应示例 JSON转换异常，请输入正确的格式");
                  }
               }

               dataSourceApiDO.setApiAlias(request.getApiAlias());
               dataSourceApiDO.setRemark(request.getRemark());
               this.catalogueDataSourceApiMapper.updateById(dataSourceApiDO);
            } else {
               this.saveDataSourceApi(tableDO, request.getApiType(), request.getApiUrl(), request.getApiAlias(), request.getApiBody(), request.getApiHeader(), request.getApiResponse(), request.getRemark());
            }

            return (CatalogueGetTableResponse)tableDO.clone(CatalogueGetTableResponse.class);
         }
      }
   }

   private void saveDataSourceApi(CatalogueSysTableDO tableDO, Integer apiType, String apiUrl, String apiAlias, String apiBody, String apiHeader, String apiResponse, String remark) {
      if (StringUtils.isNotEmpty(apiAlias) && StringUtils.isNotEmpty(apiUrl) && StringUtils.isNotEmpty(apiBody) && StringUtils.isNotEmpty(apiHeader)) {
         CatalogueDataSourceDO catalogueDataSourceDO = new CatalogueDataSourceDO();
         catalogueDataSourceDO.setSysId(tableDO.getOrgSysId());
         catalogueDataSourceDO.setSysName(tableDO.getSysName());
         catalogueDataSourceDO.setTableId(tableDO.getId());
         catalogueDataSourceDO.setState(0);
         this.catalogueDataSourceMapper.insert(catalogueDataSourceDO);
         CatalogueDataSourceApiDO catalogueDataSourceApiDO = new CatalogueDataSourceApiDO();
         catalogueDataSourceApiDO.setApiAlias(apiAlias);

         try {
            catalogueDataSourceApiDO.setApiBody(JSON.parseObject(apiBody));
         } catch (Exception var13) {
            throw new ApplicationException("请求参数(Body) JSON转换异常，请输入正确的格式");
         }

         catalogueDataSourceApiDO.setApiHeader(apiHeader);
         if (StringUtils.isNotEmpty(apiResponse)) {
            try {
               catalogueDataSourceApiDO.setApiResponse(JSON.parseObject(apiResponse));
            } catch (Exception var12) {
               throw new ApplicationException("响应示例 JSON转换异常，请输入正确的格式");
            }
         }

         catalogueDataSourceApiDO.setApiAlias(apiAlias);
         catalogueDataSourceApiDO.setRemark(remark);
         catalogueDataSourceApiDO.setApiType(apiType);
         catalogueDataSourceApiDO.setApiUrl(apiUrl);
         catalogueDataSourceApiDO.setDataSourceId(catalogueDataSourceDO.getId());
         this.catalogueDataSourceApiMapper.insert(catalogueDataSourceApiDO);
      }

   }

   public Boolean deleteTableByTableId(CatalogueDeleteTableByTableIdRequest request, int intermediate) {
      CatalogueSysTableDO var10000 = (CatalogueSysTableDO)this.catalogueSysTableService.getById(request.getId());
      boolean remove = this.catalogueSysTableService.removeById(request.getId());
      if (remove) {
         this.catalogueTableFieldService.remove(Wrappers.<CatalogueTableFieldDO>lambdaQuery().eq(CatalogueTableFieldDO::getSysTableId, request.getId()));
         OrgDsServiceDO dsService = (OrgDsServiceDO)this.orgDsServiceMapper.selectOne(new LambdaQueryWrapper<OrgDsServiceDO>().eq(OrgDsServiceDO::getTableId, request.getId()));
         if (dsService != null) {
            OrgDsServiceDTO dsServiceDTO = new OrgDsServiceDTO();
            BeanUtils.copyProperties(dsService, dsServiceDTO);
            this.orgDsServiceMapper.deleteById(dsService.getServiceId());
            dsServiceDTO.setIsDeleted(1);
            this.sendOrgDsServiceProducer.sendOrgDsServiceMessage(dsServiceDTO);
         }

         this.catalogueSysTableVersionService.removeVersionById(request.getId());
      }

      return remove;
   }

   public Boolean disableTableByTableId(CatalogueDisableTableByTableIdRequest request, int intermediate) {
      CatalogueSysTableDO tableDO = (CatalogueSysTableDO)this.catalogueSysTableService.getById(request.getId());
      if (TableFlagEnum.INSERT.getCode().equals(tableDO.getTableFlag())) {
         throw new ApplicationException("目录未发布, 无需停用!");
      } else {
         CatalogueSysTableDO table = new CatalogueSysTableDO(request.getId());
         table.setTableFlag(TableFlagEnum.DISABLE.getCode());
         table.setTableState(ApproveStateEnum.UN_COMMIT.getCode());
         table.setTableRemark(request.getRemark());
         this.catalogueSysTableService.updateById(table);
         LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapper = new LambdaUpdateWrapper();
         updateWrapper.eq(CatalogueTableFieldDO::getSysTableId, table.getId());
         updateWrapper.set(CatalogueTableFieldDO::getFieldFlag, TableFlagEnum.DISABLE.getCode());
         updateWrapper.set(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.UN_COMMIT.getCode());
         return this.catalogueTableFieldService.update(updateWrapper);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean enableTableByTableId(@Valid CatalogueEnableTableByTableIdRequest request, int intermediate) {
      CatalogueSysTableDO tableDO = (CatalogueSysTableDO)this.catalogueSysTableService.getById(request.getId());
      if (!ApproveStateEnum.BLOCK.getCode().equals(tableDO.getTableState())) {
         throw new ApplicationException("目录未停用, 无法启用!");
      } else {
         CatalogueSysTableDO table = new CatalogueSysTableDO(request.getId());
         table.setTableFlag(TableFlagEnum.ENABLE.getCode());
         table.setTableState(ApproveStateEnum.UN_COMMIT.getCode());
         this.catalogueSysTableService.updateById(table);
         LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapper = new LambdaUpdateWrapper();
         updateWrapper.eq(CatalogueTableFieldDO::getSysTableId, table.getId());
         updateWrapper.set(CatalogueTableFieldDO::getFieldFlag, TableFlagEnum.ENABLE.getCode());
         updateWrapper.set(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.UN_COMMIT.getCode());
         return this.catalogueTableFieldService.update(updateWrapper);
      }
   }

   public PageBean<CatalogueTableFieldDetailResponse> getTableFieldsByTaleId(CatalogueGetTableFieldByTableIdRequest request, int intermediate) {
      PageUtil.page(request.getPage(), request.getSize());
      QueryWrapper<CatalogueTableFieldDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(CatalogueTableFieldDO::getSysTableId, request.getSysTableId())
              .like(StringUtils.isNotBlank(request.getFieldNameCh()), CatalogueTableFieldDO::getFieldNameCh, request.getFieldNameCh())
              .like(StringUtils.isNotBlank(request.getFieldName()), CatalogueTableFieldDO::getFieldName, request.getFieldName())
              .eq(null != request.getFieldState(), CatalogueTableFieldDO::getFieldState, request.getFieldState())
              .eq(CatalogueTableFieldDO::getIntermediate, intermediate);
      List<CatalogueTableFieldDO> list = this.catalogueTableFieldMapper.selectList(queryWrapper);
      return ObjectCloneUtils.convertPageBean(new PageBean(list), CatalogueTableFieldDetailResponse.class);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public CatalogueTableFieldDetailResponse addTableFieldByTaleId(CatalogueAddTableFieldByTableIdRequest request, int intermediate) {
      CatalogueSysTableDO tableDO = (CatalogueSysTableDO)this.catalogueSysTableService.getById(request.getSysTableId());
      if (ApproveStateEnum.BLOCK.getCode().equals(tableDO.getTableState())) {
         throw new ApplicationException("目录已停用, 请先申请启用");
      } else {
         List<CatalogueTableFieldDO> list = this.catalogueTableFieldMapper.selectList(Wrappers.<CatalogueTableFieldDO>lambdaQuery().eq(CatalogueTableFieldDO::getSysTableId, tableDO.getId()).and(query ->
            query.eq(CatalogueTableFieldDO::getFieldNameCh, request.getFieldNameCh()).or().eq(CatalogueTableFieldDO::getFieldName, request.getFieldName())
         ));
         if (list.stream().anyMatch((a) -> {
            return a.getFieldNameCh().equals(request.getFieldNameCh());
         })) {
            throw new ApplicationException("该资源下已经存在信息项中文名称" + request.getFieldNameCh());
         } else if (list.stream().anyMatch((a) -> {
            return a.getFieldName().equals(request.getFieldName());
         })) {
            throw new ApplicationException("该资源下已经存在信息项英文名称" + request.getFieldName());
         } else {
            CatalogueTableFieldDO fieldDO = (CatalogueTableFieldDO)request.clone(CatalogueTableFieldDO.class);
            fieldDO.setFieldAlias(request.getFieldNameCh());
            fieldDO.setFieldFlag(TableFlagEnum.INSERT.getCode());
            fieldDO.setFieldState(ApproveStateEnum.UN_COMMIT.getCode());
            this.catalogueTableFieldMapper.insert(fieldDO);
            String level = null;
            if (null != fieldDO.getDataLevel() && fieldDO.getDataLevel() > Integer.parseInt(tableDO.getCatalogueLevel())) {
               level = fieldDO.getDataLevel().toString();
            }

            this.catalogueSysTableService.updateSysTableUnpublished(request, request.getSysTableId(), level);
            return (CatalogueTableFieldDetailResponse)fieldDO.clone(CatalogueTableFieldDetailResponse.class);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public CatalogueTableFieldDetailResponse editTableFieldByTaleId(CatalogueEditTableFieldByTableIdRequest request, int intermediate) {
      CatalogueTableFieldDO field = (CatalogueTableFieldDO)this.catalogueTableFieldService.getById(request.getId());
      CatalogueSysTableDO tableDO = (CatalogueSysTableDO)this.catalogueSysTableService.getById(field.getSysTableId());
      request.clone(CatalogueTableFieldDO.class);
      if (ApproveStateEnum.BLOCK.getCode().equals(field.getFieldState())) {
         throw new ApplicationException("已停用, 请先申请启用");
      } else {
         List<CatalogueTableFieldDO> list = this.catalogueTableFieldMapper.selectList(Wrappers.<CatalogueTableFieldDO>lambdaQuery().eq(CatalogueTableFieldDO::getSysTableId, field.getSysTableId()).and((query) ->
            query.eq(CatalogueTableFieldDO::getFieldNameCh, request.getFieldNameCh()).or()).eq(CatalogueTableFieldDO::getFieldName, request.getFieldName())
         );
         if (list.stream().anyMatch((a) -> {
            return !a.getId().equals(request.getId()) && a.getFieldNameCh().equals(request.getFieldNameCh());
         })) {
            throw new ApplicationException("该资源下已经存在信息项中文名称" + request.getFieldNameCh());
         } else if (list.stream().anyMatch((a) -> {
            return !a.getId().equals(request.getId()) && a.getFieldName().equals(request.getFieldName());
         })) {
            throw new ApplicationException("该资源下已经存在信息项英文名称" + request.getFieldName());
         } else {
            Integer state = ApproveStateEnum.UN_COMMIT.getCode();
            Integer flag = TableFlagEnum.UPDATE.getCode();
            if (TableFlagEnum.DISABLE.getCode().equals(field.getFieldFlag()) || TableFlagEnum.ENABLE.getCode().equals(field.getFieldFlag()) || ApproveStateEnum.F_REJECT.getCode().equals(field.getFieldState()) || ApproveStateEnum.R_REJECT.getCode().equals(field.getFieldState())) {
               flag = null;
            }

            if (TableFlagEnum.INSERT.getCode().equals(field.getFieldFlag())) {
               flag = TableFlagEnum.INSERT.getCode();
            }

            field = (CatalogueTableFieldDO)request.clone(CatalogueTableFieldDO.class);
            field.setFieldState(state);
            field.setFieldFlag(flag);
            this.catalogueTableFieldMapper.updateById(field);
            String level = null;
            if (null != field.getDataLevel() && field.getDataLevel() > Integer.parseInt(tableDO.getCatalogueLevel())) {
               level = field.getDataLevel().toString();
            }

            this.catalogueSysTableService.updateSysTableUnpublished(request, request.getSysTableId(), level);
            return (CatalogueTableFieldDetailResponse)field.clone(CatalogueTableFieldDetailResponse.class);
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean deleteTableFieldByTaleId(CatalogueOperatorTableFieldByFieldIdRequest request, int intermediate) {
      CatalogueTableFieldDO field = (CatalogueTableFieldDO)this.catalogueTableFieldMapper.selectById(request.getId());
      long count = this.catalogueTableFieldMapper.selectCount(Wrappers.<CatalogueTableFieldDO>lambdaQuery().eq(CatalogueTableFieldDO::getSysTableId, field.getSysTableId()));
      if (count <= 1) {
         throw new ApplicationException("删除失败，目录的信息项不能为空，至少保留一个信息项!");
      } else if (!TableFlagEnum.INSERT.getCode().equals(field.getFieldFlag())) {
         throw new ApplicationException("字段已发布, 删除失败!");
      } else {
         this.catalogueTableFieldService.removeById(request.getId());
         return this.catalogueSysTableService.updateSysTableUnpublished(request, field.getSysTableId(), (String)null);
      }
   }

   public Boolean disanbleTableFieldByTaleId(CatalogueOperatorTableFieldByFieldIdRequest request, int intermediate) {
      CatalogueTableFieldDO field = (CatalogueTableFieldDO)this.catalogueTableFieldMapper.selectById(request.getId());
      if (TableFlagEnum.INSERT.getCode().equals(field.getFieldFlag())) {
         throw new ApplicationException("字段未发布, 无需停用!");
      } else {
         field.setFieldState(ApproveStateEnum.UN_COMMIT.getCode());
         field.setFieldFlag(TableFlagEnum.DISABLE.getCode());
         field.setRemark(request.getRemark());
         this.catalogueTableFieldMapper.updateById(field);
         return this.catalogueSysTableService.updateSysTableUnpublished(request, field.getSysTableId(), (String)null);
      }
   }

   public Boolean enableTableFieldByTaleId(CatalogueOperatorTableFieldByFieldIdRequest request, int intermediate) {
      CatalogueTableFieldDO field = (CatalogueTableFieldDO)this.catalogueTableFieldMapper.selectById(request.getId());
      if (!ApproveStateEnum.BLOCK.getCode().equals(field.getFieldState())) {
         throw new ApplicationException("字段未停用, 无法启用!");
      } else {
         field.setFieldState(ApproveStateEnum.UN_COMMIT.getCode());
         field.setFieldFlag(TableFlagEnum.ENABLE.getCode());
         this.catalogueTableFieldMapper.updateById(field);
         LambdaUpdateWrapper<CatalogueSysTableDO> updateWrapper = new LambdaUpdateWrapper();
         updateWrapper.eq(BaseEntity::getId, field.getSysTableId());
         updateWrapper.set(CatalogueSysTableDO::getTableState, ApproveStateEnum.UN_COMMIT.getCode());
         updateWrapper.set(CatalogueSysTableDO::getTableFlag, TableFlagEnum.ENABLE.getCode());
         return this.catalogueSysTableService.update(updateWrapper);
      }
   }

   public CatalogueTableFieldDetailResponse getFieldDetailByFieldId(CatalogueGetFieldDetailByFieldIdRequest request, int intermediate) {
      QueryWrapper<CatalogueTableFieldDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(BaseEntity::getId, request.getId()).eq(CatalogueTableFieldDO::getIntermediate, intermediate);
      CatalogueTableFieldDO fieldDO = (CatalogueTableFieldDO)this.catalogueTableFieldMapper.selectOne(queryWrapper);
      return (CatalogueTableFieldDetailResponse)fieldDO.clone(CatalogueTableFieldDetailResponse.class);
   }

   public Boolean publishCatalogue(CataloguePublishCatalogueRequest request) {
      log.info("开始异步更新待发布数据状态,入参:{}", request);
      Long[] tableIds = request.getTableIds();
      Long userId = SecurityUtil.currentUserId();
      Long[] var4 = tableIds;
      int var5 = tableIds.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Long tableId = var4[var6];
         log.info("tableId={}", tableId);
         this.taskCataloguePublish.updateTableState(request, tableId, userId);
         this.taskCataloguePublish.updateTableFieldState(request, tableId, userId);
      }

      return Boolean.TRUE;
   }

   /** @deprecated */
   @Deprecated
   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean publishAll(CataloguePublishAllCatalogueRequest request) {
      log.info("开始一键发布,入参:{}", request);
      this.taskCataloguePublish.updateAllTableState(request);
      this.taskCataloguePublish.updateAllTableFieldState(request);
      return Boolean.TRUE;
   }

   public List<SystemsDTO> getSysByOrgIdAndSysName(CatalogueGetSysByOrgIdAndSysNameRequest request) {
      return this.catalogueOrgSysMapper.getSysByOrgIdAndSysName(request);
   }

   public List<SystemsDTO> getSysAndOrgNameBySysIds(CatalogueGetSysAndOrgNameBySysIdsRequest request) {
      return this.catalogueOrgSysMapper.getSysAndOrgNameBySysIds(request);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean disableExport(DisableExportRequest request) {
      LambdaUpdateWrapper<CatalogueSysTableDO> updateWp = new LambdaUpdateWrapper();
      updateWp.set(CatalogueSysTableDO::getCanExport, request.getCanExport());
      updateWp.eq(BaseEntity::getId, request.getTableId());
      this.catalogueSysTableService.update(updateWp);
      CatalogueGetTableVersionDetailByTableIdRequest request2 = (CatalogueGetTableVersionDetailByTableIdRequest)request.clone(CatalogueGetTableVersionDetailByTableIdRequest.class);
      request2.setId(request.getTableId());
      request2.setVersionNo(request.getVersionNO());
      CatalogueGetTableResponse rsp = this.catalogueOrgSysVersionService.getTableVersionByTableId(request2);
      if (null == rsp) {
         return false;
      } else {
         CatalogueSysTableVersionDO vdo = new CatalogueSysTableVersionDO();
         vdo.setId(rsp.getId());
         vdo.setCanExport(request.getCanExport());
         return this.catalogueSysTableVersionService.updateById(vdo);
      }
   }

   private String getResourceCode(CatalogueAddTableBySysIdRequest request) {
      CatalogueGetSysAndOrgNameBySysIdsRequest sysReq = (CatalogueGetSysAndOrgNameBySysIdsRequest)request.clone(CatalogueGetSysAndOrgNameBySysIdsRequest.class);
      sysReq.setSysIds(new Long[]{request.getOrgSysId()});
      List<SystemsDTO> systemsDTOS = this.getSysAndOrgNameBySysIds(sysReq);
      if (CollUtil.isEmpty(systemsDTOS)) {
         throw new ApplicationException(String.format("未能通过系统ID[%s]获取到组织机构", request.getOrgSysId()));
      } else {
         GetResourceCodeRequest rcRequest = new GetResourceCodeRequest(request.getDataDomain(), request.getResourceType(), ((SystemsDTO)systemsDTOS.get(0)).getNodeNo());
         return this.catalogueSysTableService.getResourceCode(rcRequest);
      }
   }

   private String getResourceCode(CatalogueEditTableByTableIdRequest request) {
      CatalogueGetSysAndOrgNameBySysIdsRequest sysReq = (CatalogueGetSysAndOrgNameBySysIdsRequest)request.clone(CatalogueGetSysAndOrgNameBySysIdsRequest.class);
      sysReq.setSysIds(new Long[]{request.getOrgSysId()});
      List<SystemsDTO> systemsDTOS = this.getSysAndOrgNameBySysIds(sysReq);
      if (CollUtil.isEmpty(systemsDTOS)) {
         throw new ApplicationException(String.format("未能通过系统ID[%s]获取到组织机构", request.getOrgSysId()));
      } else {
         GetResourceCodeRequest rcRequest = new GetResourceCodeRequest(request.getDataDomain(), request.getResourceType(), ((SystemsDTO)systemsDTOS.get(0)).getNodeNo());
         return this.catalogueSysTableService.getResourceCode(rcRequest);
      }
   }

   private boolean resourceTypeChanged(CatalogueSysTableDO tableDO) {
      CatalogueSysTableDO old = (CatalogueSysTableDO)this.catalogueSysTableService.getById(tableDO.getId());
      return !StringUtils.equals(old.getDataDomain(), tableDO.getDataDomain()) || !StringUtils.equals(old.getResourceType(), tableDO.getResourceType());
   }

   public ReportPresSystemRequest convertToReportPresSystemRequest(CatalogueOrgSysDO orgSysDO) {
      ReportPresSystemRequest request = new ReportPresSystemRequest();
      request.setSystemGuid(orgSysDO.getExternalId());
      request.setSystemName(orgSysDO.getSysName());
      request.setCredit_code(orgSysDO.getNodeNo());
      request.setProjectName(orgSysDO.getProjectName());
      request.setProjectNo(orgSysDO.getProjectCode());
      request.setIslineManage(orgSysDO.getVerticalType());
      request.setSystemType(orgSysDO.getSysType());
      request.setOtherSystemType(orgSysDO.getSysTypeDesc());
      request.setSystemStatus(orgSysDO.getSysStatus());
      request.setAppLevel(orgSysDO.getBuildLevel());
      request.setServerObj(UseRangeEnum.DEPT.getCode().toString());
      request.setNetwork(orgSysDO.getNetType());
      request.setOtherObj(orgSysDO.getUseRangeDesc());
      request.setOtherNetWork(orgSysDO.getNetTypeDesc());
      request.setEnvironment(orgSysDO.getDeployLocation());
      request.setBulidInvest(StringUtils.isNotBlank(orgSysDO.getConstructionFund()) ? Double.valueOf(orgSysDO.getConstructionFund()) : null);
      request.setDevopsInvest(StringUtils.isNotBlank(orgSysDO.getOperationFee()) ? Double.valueOf(orgSysDO.getOperationFee()) : null);
      request.setStorage(StringUtils.isNotBlank(orgSysDO.getDataCapacity()) ? Double.valueOf(orgSysDO.getDataCapacity()) : null);
      request.setZfcgbm(orgSysDO.getPurchaseCode());
      request.setCzysbm(orgSysDO.getBudgetCode());
      request.setXxhqlcbm(orgSysDO.getInformationFlowCode());
      request.setNote(orgSysDO.getRemark());
      return request;
   }

}

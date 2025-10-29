package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.enums.OrgDsServiceStatusEnum;
import com.grandlynn.spa.catalogue.common.enums.StateEnum;
import com.grandlynn.spa.catalogue.common.enums.TableFlagEnum;
import com.grandlynn.spa.catalogue.common.enums.VersionStateEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.ApplicationSceneEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.MatterDomainEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.OpenTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.ShareTypeEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.UpdatePeriodEnum;
import com.grandlynn.spa.catalogue.common.enums.cataloguemanage.YesOrNoEnum;
import com.grandlynn.spa.catalogue.domain.request.ApprovePassAllRequest;
import com.grandlynn.spa.catalogue.domain.request.ApproveRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysAndOrgNameBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogColumnSchemaRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogueRequest;
import com.grandlynn.spa.catalogue.dto.ApproveTableVersionMessageDTO;
import com.grandlynn.spa.catalogue.dto.CatalogueTableFieldDTO;
import com.grandlynn.spa.catalogue.dto.OrgDsServiceDTO;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.CataloguePublishCheckDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceCatalogueDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceCategoryDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceItemDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableApproveRecordDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.entity.CatalogueVersionLogDO;
import com.grandlynn.spa.catalogue.entity.OrgDsServiceDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgSysMapper;
import com.grandlynn.spa.catalogue.mapper.CataloguePublishCheckMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceCatalogueMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceCategoryMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceItemMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldMapper;
import com.grandlynn.spa.catalogue.mapper.OrgDsServiceMapper;
import com.grandlynn.spa.catalogue.mq.producer.SendApproveTableVersionProducer;
import com.grandlynn.spa.catalogue.mq.producer.SendMessageProducer;
import com.grandlynn.spa.catalogue.mq.producer.SendOrgDsServiceProducer;
import com.grandlynn.spa.catalogue.service.CataloguePublishCheckService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.CatalogueTableApproveRecordService;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.spa.catalogue.service.ReportCatalogueService;


import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.DateUtils;
import com.grandlynn.util.StringUtil;
import com.grandlynn.util.extension.ApplicationException;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cataloguePublishCheckService")
public class CataloguePublishCheckServiceImpl extends ServiceImpl<CataloguePublishCheckMapper, CataloguePublishCheckDO> implements CataloguePublishCheckService {
   private static final Logger log = LoggerFactory.getLogger(CataloguePublishCheckServiceImpl.class);
   @Resource
   private CatalogueOrgContentRelationMapper contentRelationMapper;
   @Resource
   private CatalogueOrgSysMapper sysMapper;
   @Resource
   private CatalogueOrgMapper orgMapper;
   @Resource
   private CatalogueSysTableService tableService;
   @Resource
   private CatalogueTableFieldService tableFieldService;
   @Resource
   private CatalogueVersionLogService versionLogService;
   @Resource
   private SendApproveTableVersionProducer tableVersionProducer;
   @Resource
   private OrgDsServiceMapper orgDsServiceMapper;
   @Resource
   private SendOrgDsServiceProducer sendOrgDsServiceProducer;
   @Resource
   private CatalogueTableFieldMapper catalogueTableFieldMapper;
   @Resource
   private CatalogueTableApproveRecordService catalogueTableApproveRecordService;
   @Resource
   private CatalogueResourceCategoryMapper resourceCategoryMapper;
   @Resource
   private CatalogueResourceItemMapper resourceItemMapper;
   @Resource
   private CatalogueResourceCatalogueMapper resourceCatalogueMapper;
   @Resource
   private ReportCatalogueService reportCatalogueService;
   @Resource
   private SendMessageProducer sendMessageProducer;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void approve(ApproveRequest request, ApproveStateEnum state) {
      List<CatalogueSysTableDO> tables = this.tableService.getAllTables(request, request.getIds(), this.getLastState(state));
      if (CollUtil.isEmpty(tables)) {
         throw new ApplicationException("没有待审批的目录");
      } else {
         List<Long> tableIds = (List)tables.stream().map(BaseEntity::getId).collect(Collectors.toList());
         LambdaUpdateWrapper<CatalogueSysTableDO> tableUpdateWrapper = this.buildUpdateTableWrapper(tableIds, (String)null, request.getReason());
         tableUpdateWrapper.set(CatalogueSysTableDO::getTableState, state.getCode());
         this.tableService.update(tableUpdateWrapper);
         LambdaUpdateWrapper<CatalogueTableFieldDO> fieldUpdateWrapper = this.buildUpdateFieldWrapper(tableIds, (String)null, request.getReason());
         fieldUpdateWrapper.set(CatalogueTableFieldDO::getFieldState, state.getCode());
         fieldUpdateWrapper.eq(CatalogueTableFieldDO::getFieldState, this.getLastState(state));
         this.tableFieldService.update(fieldUpdateWrapper);

         try {
            List<CatalogueTableApproveRecordDO> recordDOList = (List)tables.stream().map((item) -> {
               CatalogueTableApproveRecordDO recordDO = new CatalogueTableApproveRecordDO();
               recordDO.setSysTableId(item.getId());
               recordDO.setState(state.getCode());
               recordDO.setOption(request.getReason());
               return recordDO;
            }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(recordDOList)) {
               this.catalogueTableApproveRecordService.saveBatch(recordDOList);
            }
         } catch (Exception var8) {
            log.info("保存操作记录失败");
         }

      }
   }

   public Boolean passAll(ApprovePassAllRequest request) {
      ApproveRequest approveRequest = (ApproveRequest)request.clone(ApproveRequest.class);
      approveRequest.setReason(request.getPassMsg());
      if (ApproveStateEnum.RECHECK.getCode().equals(request.getPassCode())) {
         this.approve(approveRequest, ApproveStateEnum.RECHECK);
      } else {
         this.approve(approveRequest, ApproveStateEnum.UN_PUBLISHED);
      }

      return true;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void publish(BaseRequest request) {
      List<CatalogueSysTableDO> tables = this.tableService.getAllTables(request, (Long[])null, ApproveStateEnum.UN_PUBLISHED.getCode());
      if (CollUtil.isEmpty(tables)) {
         throw new ApplicationException("没有待发布的目录");
      } else {
         List<CatalogueResourceCategoryDO> resourceCategoryDOS = this.resourceCategoryMapper.selectList(Wrappers.query());
         List<CatalogueResourceItemDO> resourceItemDOS = this.resourceItemMapper.selectList(Wrappers.query());
         List<CatalogueResourceCatalogueDO> resourceCatalogueDOS = this.resourceCatalogueMapper.selectList(Wrappers.query());
         Map<String, Boolean> isNewCatalogue = new HashMap();
         tables.stream().filter((e) -> {
            return StringUtil.isBlank(e.getResourceCode());
         }).forEach((e) -> {
            CatalogueAddTableBySysIdRequest cataQuery = new CatalogueAddTableBySysIdRequest();
            cataQuery.setOrgId(e.getOrgId());
            if (null == e.getCategoryId()) {
               cataQuery.setCategoryCode("3");
               cataQuery.setItemCode("07");
               cataQuery.setCatalogueCode("320");
            } else {
               CatalogueResourceCategoryDO categoryDO = (CatalogueResourceCategoryDO)resourceCategoryDOS.stream().filter((category) -> {
                  return category.getId().equals(e.getCategoryId());
               }).findAny().orElse(null);
               if (Objects.nonNull(categoryDO)) {
                  cataQuery.setCategoryCode(categoryDO.getCode());
               }

               CatalogueResourceItemDO itemDO = (CatalogueResourceItemDO)resourceItemDOS.stream().filter((item) -> {
                  return item.getId().equals(e.getItemId());
               }).findAny().orElse(null);
               if (Objects.nonNull(itemDO)) {
                  cataQuery.setItemCode(itemDO.getCode());
               }

               CatalogueResourceCatalogueDO catalogueDO = (CatalogueResourceCatalogueDO)resourceCatalogueDOS.stream().filter((category) -> {
                  return category.getId().equals(e.getCatalogueId());
               }).findAny().orElse(null);
               if (Objects.nonNull(catalogueDO)) {
                  cataQuery.setCatalogueCode(catalogueDO.getCode());
               }
            }

            e.setResourceCode(this.getResourceCode(cataQuery));
            isNewCatalogue.put(e.getResourceCode(), true);
         });
         this.tableService.updateBatchById(tables);
         String versionNo = this.versionLogService.generateVersionInfo(request);
         Set<Long> orgSysId = ((Map)tables.stream().collect(Collectors.toMap(CatalogueSysTableDO::getOrgSysId, (a) -> {
            return a;
         }, (k1, k2) -> {
            return k1;
         }))).keySet();
         List<CatalogueOrgDO> orgDOS = this.orgMapper.selectList(Wrappers.query());
         List<CatalogueOrgSysDO> orgSysDOS = this.sysMapper.selectList(Wrappers.query());
         this.updateVersionForContentRelation(request, orgSysId, versionNo);
         this.updateVersionForSys(request, orgSysId, versionNo);
         List<Long> tableIds = (List)tables.stream().map(BaseEntity::getId).collect(Collectors.toList());
         this.publishTable(tableIds, ApproveStateEnum.PUBLISHED, request, versionNo, (String)null);
         this.publishField(tableIds, ApproveStateEnum.PUBLISHED, request, versionNo, (String)null);

         try {
            List<CatalogueTableApproveRecordDO> recordDOList = (List)tables.stream().map((item) -> {
               CatalogueTableApproveRecordDO recordDO = new CatalogueTableApproveRecordDO();
               recordDO.setSysTableId(item.getId());
               recordDO.setState(ApproveStateEnum.PUBLISHED.getCode());
               recordDO.setOption("发布目录");
               return recordDO;
            }).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(recordDOList)) {
               this.catalogueTableApproveRecordService.saveBatch(recordDOList);
            }
         } catch (Exception var17) {
            log.info("保存发布记录失败");
         }

         ApproveTableVersionMessageDTO messageDTO = (ApproveTableVersionMessageDTO)request.clone(ApproveTableVersionMessageDTO.class);
         messageDTO.setVersionNo(versionNo);
         this.tableVersionProducer.sendTableVersionMessage(messageDTO);
         List<OrgDsServiceDO> orgDsServiceList = this.orgDsServiceMapper.selectList((new LambdaQueryWrapper<OrgDsServiceDO>()).in(OrgDsServiceDO::getTableId, tableIds));
         Map<Long, CatalogueSysTableDO> catalogueSysTableDOMap = (Map)tables.stream().collect(Collectors.toMap(BaseEntity::getId, (t) -> {
            return t;
         }));

         orgDsServiceList.forEach((dsService) -> {
            CatalogueSysTableDO catalogueSysTableDO = (CatalogueSysTableDO)catalogueSysTableDOMap.get(dsService.getTableId());
            dsService.setResourceCode(catalogueSysTableDO.getResourceCode());
            dsService.setState(OrgDsServiceStatusEnum.ENABLE.getCode());
            if (TableFlagEnum.DISABLE.getCode().equals(catalogueSysTableDO.getTableFlag())) {
               dsService.setState(OrgDsServiceStatusEnum.DISABLE.getCode());
            }
            this.orgDsServiceMapper.updateById(dsService);
            OrgDsServiceDTO dsServiceDTO = new OrgDsServiceDTO();
            BeanUtils.copyProperties(dsService, dsServiceDTO);
            dsServiceDTO.setNodeNo(((CatalogueOrgDO)orgDOS.stream().filter((item) -> {
               return item.getId().equals(dsService.getDeptId());
            }).findAny().orElse(new CatalogueOrgDO())).getNodeNo());
            QueryWrapper<CatalogueTableFieldDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(CatalogueTableFieldDO::getSysTableId, dsService.getTableId());
            List<CatalogueTableFieldDO> catalogueTableFieldDOs = this.catalogueTableFieldMapper.selectList(queryWrapper);
            ArrayList categoryRequest;
            if (catalogueTableFieldDOs.size() > 0) {
               categoryRequest = new ArrayList();
               Iterator var12 = catalogueTableFieldDOs.iterator();

               while(var12.hasNext()) {
                  CatalogueTableFieldDO catalogueTableFieldDO = (CatalogueTableFieldDO)var12.next();
                  CatalogueTableFieldDTO catalogueTableFieldDTO = new CatalogueTableFieldDTO();
                  BeanUtils.copyProperties(catalogueTableFieldDO, catalogueTableFieldDTO);
                  categoryRequest.add(catalogueTableFieldDTO);
               }

               dsServiceDTO.setCatalogueTableFieldList(categoryRequest);
            }

            this.sendOrgDsServiceProducer.sendOrgDsServiceMessage(dsServiceDTO);
            catalogueSysTableDO.setNodeName(((CatalogueOrgDO)orgDOS.stream().filter((item) -> {
               return item.getId().equals(catalogueSysTableDO.getOrgId());
            }).findAny().orElse(new CatalogueOrgDO())).getNodeName());
            catalogueSysTableDO.setOrgExternalCode(((CatalogueOrgDO)orgDOS.stream().filter((item) -> {
               return item.getId().equals(catalogueSysTableDO.getOrgId());
            }).findAny().orElse(new CatalogueOrgDO())).getExternalCode());
            catalogueSysTableDO.setOrgSysExternalId(((CatalogueOrgSysDO)orgSysDOS.stream().filter((item) -> {
               return item.getId().equals(catalogueSysTableDO.getOrgSysId());
            }).findAny().orElse(new CatalogueOrgSysDO())).getExternalId());
            categoryRequest = null;

            try {
               ReportCatalogueRequest categoryRequestx = this.convertToReportCatalogueInfoRequest(catalogueSysTableDO, catalogueTableFieldDOs);
               if (!isNewCatalogue.containsKey(catalogueSysTableDO.getResourceCode()) && !StringUtils.isBlank(catalogueSysTableDO.getExternalId())) {
                  if (catalogueSysTableDO.getEnable().equals(0)) {
                     this.reportCatalogueService.catalogueCancel(categoryRequestx);
                     this.tableService.update(Wrappers.<CatalogueSysTableDO>lambdaUpdate().set(CatalogueSysTableDO::getExternalId, "").eq(BaseEntity::getId, catalogueSysTableDO.getId()));
                  } else {
                     this.reportCatalogueService.catalogueModify(categoryRequestx);
                  }
               } else if (catalogueSysTableDO.getEnable().equals(1)) {
                  String cascadeguid = this.reportCatalogueService.catalogueRegister(categoryRequestx);
                  if (StringUtils.isNotBlank(cascadeguid)) {
                     this.tableService.update(Wrappers.<CatalogueSysTableDO>lambdaUpdate().set(CatalogueSysTableDO::getExternalId, cascadeguid).eq(BaseEntity::getId, catalogueSysTableDO.getId()));
                  }
               }
            } catch (Exception var15) {
               log.info("上报目录数据失败：{}", JSON.toJSONString(categoryRequest));
            }

         });
         this.versionLogService.update(Wrappers.<CatalogueVersionLogDO>lambdaUpdate().set(CatalogueVersionLogDO::getVersionState, VersionStateEnum.ENABLED.getCode()).eq(CatalogueVersionLogDO::getVersionNo, versionNo));
      }
   }

   private boolean updateVersionForSys(BaseRequest request, Set<Long> table, String versionNo) {
      UpdateWrapper<CatalogueOrgSysDO> updateWrapper = new UpdateWrapper();
      updateWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .in(BaseEntity::getId, table.toArray())
              .set(CatalogueOrgSysDO::getVersionNo, versionNo);
      return this.sysMapper.update(null, updateWrapper) > 0;
   }

   private boolean updateVersionForContentRelation(BaseRequest request, Set<Long> tables, String versionNo) {
      UpdateWrapper<CatalogueOrgContentRelationDO> updateWrapper = new UpdateWrapper();
      updateWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .in(CatalogueOrgContentRelationDO::getContentId, tables.toArray())
              .set(CatalogueOrgContentRelationDO::getVersionNo, versionNo);
      return this.contentRelationMapper.update(null, updateWrapper) > 0;
   }

   private Integer getLastState(ApproveStateEnum state) {
      switch (state) {
         case RECHECK:
            return ApproveStateEnum.FIRST.getCode();
         case F_REJECT:
            return ApproveStateEnum.FIRST.getCode();
         case UN_PUBLISHED:
            return ApproveStateEnum.RECHECK.getCode();
         case R_REJECT:
            return ApproveStateEnum.RECHECK.getCode();
         case PUBLISHED:
            return ApproveStateEnum.UN_PUBLISHED.getCode();
         default:
            return state.getCode();
      }
   }

   private void publishTable(List<Long> tables, ApproveStateEnum state, BaseRequest request, String versionNo, String reason) {
      LambdaUpdateWrapper<CatalogueSysTableDO> updateWrapperNormal = this.buildUpdateTableWrapper(tables, versionNo, reason);
      updateWrapperNormal.ne(CatalogueSysTableDO::getTableFlag, TableFlagEnum.DISABLE.getCode());
      updateWrapperNormal.set(CatalogueSysTableDO::getTableState, ApproveStateEnum.PUBLISHED.getCode());
      updateWrapperNormal.set(CatalogueSysTableDO::getTableFlag, TableFlagEnum.NORMAL.getCode());
      this.tableService.update(updateWrapperNormal);
      LambdaUpdateWrapper<CatalogueSysTableDO> updateWrapperDisable = this.buildUpdateTableWrapper(tables, versionNo, reason);
      updateWrapperDisable.eq(CatalogueSysTableDO::getTableFlag, TableFlagEnum.DISABLE.getCode());
      updateWrapperDisable.set(CatalogueSysTableDO::getTableState, ApproveStateEnum.BLOCK.getCode());
      updateWrapperDisable.set(CatalogueSysTableDO::getTableFlag, TableFlagEnum.NORMAL.getCode());
      this.tableService.update(updateWrapperDisable);
   }

   private void publishField(List<Long> tables, ApproveStateEnum state, BaseRequest request, String versionNo, String reason) {
      LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapperNormal = this.buildUpdateFieldWrapper(tables, versionNo, reason);
      updateWrapperNormal.ne(CatalogueTableFieldDO::getFieldFlag, TableFlagEnum.DISABLE.getCode());
      updateWrapperNormal.eq(CatalogueTableFieldDO::getFieldState, this.getLastState(state));
      updateWrapperNormal.set(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.PUBLISHED.getCode());
      updateWrapperNormal.set(CatalogueTableFieldDO::getFieldFlag, TableFlagEnum.NORMAL.getCode());
      this.tableFieldService.update(updateWrapperNormal);
      LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapperDisable = this.buildUpdateFieldWrapper(tables, versionNo, reason);
      updateWrapperDisable.eq(CatalogueTableFieldDO::getFieldState, this.getLastState(state));
      updateWrapperDisable.eq(CatalogueTableFieldDO::getFieldFlag, TableFlagEnum.DISABLE.getCode());
      updateWrapperDisable.set(CatalogueTableFieldDO::getFieldState, ApproveStateEnum.BLOCK.getCode());
      updateWrapperDisable.set(CatalogueTableFieldDO::getFieldFlag, TableFlagEnum.NORMAL.getCode());
      this.tableFieldService.update(updateWrapperDisable);
   }

   private LambdaUpdateWrapper<CatalogueSysTableDO> buildUpdateTableWrapper(List<Long> tableIds, String versionNo, String reason) {
      LambdaUpdateWrapper<CatalogueSysTableDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.set(StringUtil.isNotBlank(versionNo), CatalogueSysTableDO::getVersionNo, versionNo);
      updateWrapper.set(CatalogueSysTableDO::getTableChanged, StateEnum.YES.getCode());
      updateWrapper.set(CatalogueSysTableDO::getTableOptioin, reason);
      updateWrapper.in(BaseEntity::getId, tableIds);
      return updateWrapper;
   }

   private LambdaUpdateWrapper<CatalogueTableFieldDO> buildUpdateFieldWrapper(List<Long> tableIds, String versionNo, String reason) {
      LambdaUpdateWrapper<CatalogueTableFieldDO> updateWrapper = new LambdaUpdateWrapper();
      updateWrapper.set(StringUtil.isNotBlank(versionNo), CatalogueTableFieldDO::getVersionNo, versionNo);
      updateWrapper.in(CatalogueTableFieldDO::getSysTableId, tableIds);
      updateWrapper.eq(CatalogueTableFieldDO::getIntermediate, IntermediateEnum.INTERMEDIATE.getCode());
      return updateWrapper;
   }

   private String getResourceCode(CatalogueAddTableBySysIdRequest request) {
      CatalogueGetSysAndOrgNameBySysIdsRequest sysReq = (CatalogueGetSysAndOrgNameBySysIdsRequest)request.clone(CatalogueGetSysAndOrgNameBySysIdsRequest.class);
      List<SystemsDTO> systemsDTOS = this.sysMapper.getSysAndOrgNameBySysIds(sysReq);
      if (CollUtil.isEmpty(systemsDTOS)) {
         throw new ApplicationException(String.format("未能通过系统ID[%s]获取到组织机构", request.getOrgSysId()));
      } else {
         String nodeNo = ((SystemsDTO)systemsDTOS.get(0)).getNodeNo();
         String areaCode = "0211";
         String code = request.getCategoryCode() + "" + request.getItemCode() + "" + request.getCatalogueCode() + "" + areaCode + "" + (nodeNo.length() >= 9 ? nodeNo.substring(nodeNo.length() - 9) : nodeNo);
         long num = this.redisTemplate.opsForValue().increment("ResourceCodeRule:" + code);
         return code + "/" + String.format("%06d", num);
      }
   }

   private ReportCatalogueRequest convertToReportCatalogueInfoRequest(CatalogueSysTableDO sysTableDO, List<CatalogueTableFieldDO> fieldDOList) {
      ReportCatalogueRequest request = new ReportCatalogueRequest();
      request.setCata_id(sysTableDO.getExternalId());
      request.setGroup_id(sysTableDO.getOrgExternalCode());
      request.setCata_title(sysTableDO.getDataResourceName());
      request.setCombType(sysTableDO.getSourceType().toString());
      request.setSystemGuid(sysTableDO.getOrgSysExternalId());
      request.setCredit_code(sysTableDO.getNodeCode());
      request.setInternalorgan(sysTableDO.getNodeName());
      request.setDescription(sysTableDO.getDataResourceAbstract());
      request.setResourceformat(sysTableDO.getResourceFormatType());
      request.setPublishedtime(LocalDateTimeUtil.now().format(DatePattern.NORM_DATETIME_FORMATTER));
      request.setShared_type(sysTableDO.getTableShareType());
      if (!ShareTypeEnum.WITHOUT_CONDITION_TYPE.getCode().equals(sysTableDO.getTableShareType())) {
         request.setShared_condition(StringUtils.defaultString(sysTableDO.getTableShareTypeDesc(), "无条件共享"));
      }

      request.setSharedway(sysTableDO.getShareWayType());
      request.setOpen_type(Integer.parseInt(sysTableDO.getOpenType()));
      if (OpenTypeEnum.FORBID_OPEN_TYPE.getCode().toString().equals(sysTableDO.getOpenType())) {
         request.setUseDemand(sysTableDO.getOpenDepency());
      }

      if (!ShareTypeEnum.WITHOUT_CONDITION_TYPE.getCode().equals(sysTableDO.getTableShareType()) && !OpenTypeEnum.WITHOUT_CONDITION_TYPE.getCode().toString().equals(sysTableDO.getOpenType())) {
         String defaultCondition = "不予开放";
         if (OpenTypeEnum.CONDITION_OPEN_TYPE.getCode().toString().equals(sysTableDO.getOpenType())) {
            defaultCondition = "有条件开放";
         }

         request.setOpen_condition(StringUtils.defaultString(sysTableDO.getOpenDepency(), defaultCondition));
      }

      request.setUpdatetime(LocalDateTimeUtil.now().format(DatePattern.NORM_DATETIME_FORMATTER));
      request.setUpdate_cycle(sysTableDO.getUpdatePeriod());
      if (UpdatePeriodEnum.getNameByCode(sysTableDO.getUpdatePeriod()) != null && UpdatePeriodEnum.OTHER_PERIOD.getCode().equals(sysTableDO.getUpdatePeriod())) {
         request.setOther_update_cycle(sysTableDO.getUpdatePeriodDesc());
      }

      request.setUsescene(sysTableDO.getApplicationSceneCode().substring(0, 2));
      request.setBelongindustry(sysTableDO.getIndustryCategoryCode());
      request.setResourceSubject(sysTableDO.getDomainCode());
      request.setResourcesShortName(sysTableDO.getSimplify());
      request.setResourcesShortName(sysTableDO.getDomainCode());
      if (sysTableDO.getSourceType().equals(2)) {
         request.setTask_guid(sysTableDO.getRowGuId());
      }

      if ("2".equals(sysTableDO.getSourceType().toString())) {
         request.setNettype(sysTableDO.getDeliverChannel());
         request.setCertification_type(null != sysTableDO.getIsElectronicCertificate() ? sysTableDO.getIsElectronicCertificate().toString() : null);
         request.setGovusescene(sysTableDO.getApplicationScene());
         if (sysTableDO.getApplicationScene().equals(ApplicationSceneEnum.OTHER.getCode())) {
            request.setOtherusescene(sysTableDO.getApplicationSceneDesc());
         }

         request.setFieldtype(sysTableDO.getMatterDomain());
         if (sysTableDO.getMatterDomain().equals(MatterDomainEnum.OTHER.getCode())) {
            request.setOther_fieldtype(sysTableDO.getMatterDomainDesc());
         }

         request.setRes_registered(null != sysTableDO.getIsMountResource() ? sysTableDO.getIsMountResource().toString() : null);
         if (sysTableDO.getIsMountResource().equals(YesOrNoEnum.REAL_TIME_PERIOD.getCode())) {
            request.setRes_platform(sysTableDO.getRegisterPlatform());
         }

         request.setOrg_name(sysTableDO.getNodeName());
         request.setOrg_code(sysTableDO.getNodeCode());
         if (StringUtils.isBlank(sysTableDO.getApproveResult())) {
            request.setApproval_results("无");
         } else {
            request.setApproval_results(sysTableDO.getApproveResult());
         }
      }

      request.setBasicinfoLevel(sysTableDO.getCatalogueLevel());
      if ("2".equals(sysTableDO.getResourceFormat())) {
         request.setResourceType("0");
      } else if ("0".equals(sysTableDO.getResourceFormat())) {
         request.setResourceType("1");
      } else {
         request.setResourceType("2");
      }

      List<ReportCatalogColumnSchemaRequest> columnSchemaRequests = (List)fieldDOList.stream().map((field) -> {
         ReportCatalogColumnSchemaRequest columnSchemaRequest = new ReportCatalogColumnSchemaRequest();
         columnSchemaRequest.setColumnid(field.getColumnId());
         columnSchemaRequest.setNamecn(field.getFieldNameCh());
         columnSchemaRequest.setDataformat(field.getFieldType());
         columnSchemaRequest.setLength(Integer.valueOf(StringUtils.defaultString(field.getFieldLength(), "0")));
         columnSchemaRequest.setPrecision(null != field.getFieldPrecision() ? field.getFieldPrecision() : 0);
         columnSchemaRequest.setOrder_id(0);
         columnSchemaRequest.setUpdatetime(DateUtils.format(field.getUpdatedTime(), "yyyy-MM-dd HH:mm:ss"));
         columnSchemaRequest.setSensitive_level("3");
         columnSchemaRequest.setShare_type(Integer.parseInt(sysTableDO.getTableShareType()) + 1 + "");
         if (columnSchemaRequest.getShare_type().equals("3")) {
            columnSchemaRequest.setNot_share_reason(sysTableDO.getTableShareTypeDesc());
         }

         return columnSchemaRequest;
      }).collect(Collectors.toList());
      request.setColumnlist(columnSchemaRequests);
      return request;
   }

}

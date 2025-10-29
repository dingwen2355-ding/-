package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.TableFlagEnum;
import com.grandlynn.spa.catalogue.common.exception.GenerateResourceCodeException;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableBySysNameAndTableNameRequest;
import com.grandlynn.spa.catalogue.domain.request.GetResourceCodeRequest;
import com.grandlynn.spa.catalogue.domain.request.GetTablesByStatesRequest;
import com.grandlynn.spa.catalogue.domain.request.TableMatterRequest;
import com.grandlynn.spa.catalogue.dto.TableCountDTO;
import com.grandlynn.spa.catalogue.dto.TableMatterDTO;
import com.grandlynn.spa.catalogue.entity.*;
import com.grandlynn.spa.catalogue.mapper.*;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableResourceSerialService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.StringUtil;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service("catalogueSysTableService")
public class CatalogueSysTableServiceImpl extends ServiceImpl<CatalogueSysTableMapper, CatalogueSysTableDO> implements CatalogueSysTableService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueSysTableServiceImpl.class);
   @Resource
   private CatalogueSysTableResourceSerialService resourceSerialService;
   @Resource
   private ForeignCityResourceDomainMapper domainMapper;
   @Resource
   private ForeignCityResourceTypeMapper typeMapper;
   @Resource
   private ForeignCityResourceOrgMapper orgMapper;
   @Value("${shunde.code}")
   private String cityResourceCode;
   @Resource
   private CatalogueResourceCategoryMapper resourceCategoryMapper;
   @Resource
   private CatalogueResourceItemMapper resourceItemMapper;
   @Resource
   private CatalogueResourceCatalogueMapper resourceCatalogueMapper;
   @Resource
   private TableMatterMapper tableMatterMapper;
   private List<ForeignCityResourceDomainDO> domainList = null;
   private List<ForeignCityResourceTypeDO> typeList = null;
   private List<ForeignCityResourceOrgDO> orgList = null;

   public CatalogueSysTableDO saveWithDisableState(CatalogueSysTableDO sysTableDO, CatalogueGetTableBySysNameAndTableNameRequest request) {
      QueryWrapper<CatalogueSysTableDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(CatalogueSysTableDO::getDataResourceName, request.getDataResourceName())
              .eq(CatalogueSysTableDO::getOrgId, sysTableDO.getOrgId())
              .eq(StringUtil.isNotBlank(request.getSysName()), CatalogueSysTableDO::getSysName, request.getSysName())
              .eq(Objects.nonNull(sysTableDO.getOrgSysId()), CatalogueSysTableDO::getOrgSysId, sysTableDO.getOrgSysId())
              .isNull(StringUtil.isBlank(request.getSysName()), CatalogueSysTableDO::getSysName)
              .isNull(Objects.isNull(sysTableDO.getOrgSysId()), CatalogueSysTableDO::getOrgSysId)
              .last("limit 1");
      CatalogueSysTableDO sysTable = (CatalogueSysTableDO)this.getOne(queryWrapper);
      if (null == sysTable) {
         sysTableDO.setTableFlag(TableFlagEnum.INSERT.getCode());
         sysTableDO.setTableState(ApproveStateEnum.UN_COMMIT.getCode());
         this.save(sysTableDO);
         return sysTableDO;
      } else {
         sysTableDO.setId(sysTable.getId());
         sysTableDO.setCatalogueLevel(sysTable.getCatalogueLevel());
         if (!ApproveStateEnum.BLOCK.getCode().equals(sysTable.getTableState()) && !TableFlagEnum.DISABLE.getCode().equals(sysTable.getTableFlag()) && !TableFlagEnum.ENABLE.getCode().equals(sysTable.getTableFlag())) {
            sysTableDO.setTableFlag(TableFlagEnum.UPDATE.getCode());
            sysTableDO.setTableState(ApproveStateEnum.UN_COMMIT.getCode());
            this.updateById(sysTableDO);
            return sysTableDO;
         } else {
            this.updateById(sysTableDO);
            return sysTableDO;
         }
      }
   }

   public Map<Long, Integer> getTableCount(BaseRequest request) {
      List<TableCountDTO> list = ((CatalogueSysTableMapper)this.baseMapper).getTableCount(request.getTenantId(), request.getAppId());
      Map<Long, Integer> map = new HashMap();
      if (list != null && !list.isEmpty()) {
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            TableCountDTO tableCountDTO = (TableCountDTO)var4.next();
            map.put(tableCountDTO.getSysId(), tableCountDTO.getTableCount());
         }
      }

      return map;
   }

   public boolean updateSysTableUnpublished(BaseRequest request, Long sysTableId, String level) {
      UpdateWrapper<CatalogueSysTableDO> updateWrapper = new UpdateWrapper();
      updateWrapper.lambda()
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getId, sysTableId)
              .set(CatalogueSysTableDO::getTableState, ApproveStateEnum.UN_COMMIT.getCode())
              .set(StringUtil.isNotBlank(level), CatalogueSysTableDO::getCatalogueLevel, level);
      return ((CatalogueSysTableMapper)this.baseMapper).update(null, updateWrapper) > 0;
   }

   public List<CatalogueSysTableDO> getTablesBySysId(BaseRequest request, Long sysId, Long orgId, String tableName, String dataResourceName, String databaseName) {
      Long[] orgids = SecurityUtil.currentUser().getOrgids();
      if (orgId == null) {
         orgId = null != orgids && orgids.length >= 1 ? orgids[0] : null;
      } else if (SecurityUtil.currentUser().getOrgLimit() && !Arrays.asList(orgids).contains(orgId)) {
         throw new ApplicationException("无权限操作其他组织的编目");
      }

      QueryWrapper<CatalogueSysTableDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(Objects.nonNull(sysId), CatalogueSysTableDO::getOrgSysId, sysId)
              .isNull(Objects.isNull(sysId), CatalogueSysTableDO::getOrgSysId)
              .eq(Objects.nonNull(orgId), CatalogueSysTableDO::getOrgId, orgId)
              .and(query
                      -> query.eq(CatalogueSysTableDO::getDataResourceName, dataResourceName).or(o ->
                           o.eq(StringUtil.isNotBlank(databaseName), CatalogueSysTableDO::getDatabaseName, databaseName)
                                   .eq(StringUtil.isNotBlank(tableName), CatalogueSysTableDO::getTableName, tableName)
         )
      );
      return this.baseMapper.selectList(queryWrapper);
   }

   public List<CatalogueSysTableDO> getTablesBySysIdNew(BaseRequest request, Long sysId, Long orgId, String dataResourceName) {
      Long[] orgids = SecurityUtil.currentUser().getOrgids();
      if (orgId == null) {
         orgId = null != orgids && orgids.length >= 1 ? orgids[0] : null;
      } else if (SecurityUtil.currentUser().getOrgLimit() && !Arrays.asList(orgids).contains(orgId)) {
         throw new ApplicationException("无权限操作其他组织的编目");
      }

      QueryWrapper<CatalogueSysTableDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(Objects.nonNull(sysId), CatalogueSysTableDO::getOrgSysId, sysId)
              .isNull(Objects.isNull(sysId), CatalogueSysTableDO::getOrgSysId)
              .eq(Objects.nonNull(orgId), CatalogueSysTableDO::getOrgId, orgId)
              .and(query -> query.eq(CatalogueSysTableDO::getDataResourceName, dataResourceName));
      return this.baseMapper.selectList(queryWrapper);
   }

   public List<CatalogueSysTableDO> getAllTables(BaseRequest request, Long[] ids, Integer tableState) {
      QueryWrapper<CatalogueSysTableDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .in(null != ids && ids.length > 0, BaseEntity::getId, ids)
              .eq(SecurityUtil.currentUser().getOrgLimit(), CatalogueSysTableDO::getOrgId, SecurityUtil.currentUser().getOrgids()[0])
              .eq(CatalogueSysTableDO::getTableState, tableState);
      return ((CatalogueSysTableMapper)this.baseMapper).selectList(queryWrapper);
   }

   public String getResourceCode(GetResourceCodeRequest request) throws GenerateResourceCodeException {
      StringBuilder sb = new StringBuilder();
      if (this.domainList == null) {
         this.domainList = this.domainMapper.selectList(null);
      }

      ForeignCityResourceDomainDO domainDO = null;

      for(int i = 0; i < this.domainList.size(); ++i) {
         if (Objects.equals(((ForeignCityResourceDomainDO)this.domainList.get(i)).getName(), request.getDomain())) {
            domainDO = (ForeignCityResourceDomainDO)this.domainList.get(i);
            break;
         }
      }

      if (domainDO == null) {
         throw new GenerateResourceCodeException("未匹配的数据领域");
      } else {
         sb.append(domainDO.getCode());
         if (this.typeList == null) {
            this.typeList = this.typeMapper.selectList(null);
         }

         ForeignCityResourceTypeDO typeDO = null;

         for(int i = 0; i < this.typeList.size(); ++i) {
            if (Objects.equals(((ForeignCityResourceTypeDO)this.typeList.get(i)).getName(), request.getType())) {
               typeDO = (ForeignCityResourceTypeDO)this.typeList.get(i);
               break;
            }
         }

         if (typeDO == null) {
            throw new GenerateResourceCodeException("未配置的资源分类");
         } else {
            sb.append(typeDO.getCode());
            log.info("滨湖区机构代码[{}]", this.cityResourceCode);
            sb.append(this.cityResourceCode);
            if (this.orgList == null) {
               this.orgList = this.orgMapper.selectList(null);
            }

            ForeignCityResourceOrgDO orgDO = null;

            for(int i = 0; i < this.orgList.size(); ++i) {
               if (Objects.equals(((ForeignCityResourceOrgDO)this.orgList.get(i)).getNodeNo(), request.getNodeNo())) {
                  orgDO = (ForeignCityResourceOrgDO)this.orgList.get(i);
                  break;
               }
            }

            if (orgDO == null) {
               log.warn("未能匹配到部门[{}],返回999", request.getNodeNo());
               sb.append("999");
            } else {
               sb.append(orgDO.getCode());
            }

            return this.resourceSerialService.getResourceSerialByOrg(sb.toString(), request.getNodeNo());
         }
      }
   }

   public List<CatalogueSysTableDO> getTablesByStates(GetTablesByStatesRequest gtRequest) {
      return ((CatalogueSysTableMapper)this.baseMapper).getTablesByStates(gtRequest);
   }

   public void clearForeignCityList() {
      this.domainList = null;
      this.typeList = null;
      this.orgList = null;
   }

   public List<CatalogueResourceCategoryDO> findCategoryItemCatalogue() {
      List<CatalogueResourceCategoryDO> resourceCategoryDOS = this.resourceCategoryMapper.selectList(Wrappers.query());
      List<CatalogueResourceItemDO> resourceItemDOS = this.resourceItemMapper.selectList(Wrappers.query());
      List<CatalogueResourceCatalogueDO> resourceCatalogueDOS = this.resourceCatalogueMapper.selectList(Wrappers.query());
      Map<Long, List<CatalogueResourceItemDO>> resourceItemDOSMap = (Map)resourceItemDOS.stream().collect(Collectors.groupingBy(CatalogueResourceItemDO::getCategoryId));
      Map<Long, List<CatalogueResourceCatalogueDO>> resourceCatalogueDOSMap = (Map)resourceCatalogueDOS.stream().collect(Collectors.groupingBy(CatalogueResourceCatalogueDO::getItemId));
      resourceCategoryDOS.forEach((category) -> {
         category.setItemDOS((List)resourceItemDOSMap.get(category.getId()));
         category.getItemDOS().forEach((item) -> {
            item.setCatalogueDOS((List)resourceCatalogueDOSMap.get(item.getId()));
         });
      });
      return resourceCategoryDOS;
   }

   public PageBean<TableMatterDTO> findMatter(TableMatterRequest request) {
      LambdaQueryWrapper<TableMatterDO> wp = new LambdaQueryWrapper();
      wp.like(StringUtil.isNotBlank(request.getTASK_NAME()), TableMatterDO::getTaskName, request.getTASK_NAME());
      wp.like(StringUtil.isNotBlank(request.getCATALOG_CODE()), TableMatterDO::getCatalogCode, request.getCATALOG_CODE());
      wp.like(StringUtil.isNotBlank(request.getYWCODE()), TableMatterDO::getYwcode, request.getYWCODE());
      wp.like(StringUtil.isNotBlank(request.getTASK_CODE()), TableMatterDO::getTaskCode, request.getTASK_CODE());
      wp.like(StringUtil.isNotBlank(request.getDEPT_CODE()), TableMatterDO::getDeptCode, request.getDEPT_CODE());
      wp.like(StringUtil.isNotBlank(request.getDEPT_NAME()), TableMatterDO::getDeptName, request.getDEPT_NAME());
      wp.like(StringUtil.isNotBlank(request.getTASK_STATE()), TableMatterDO::getTaskState, request.getTASK_STATE());
      wp.eq(TableMatterDO::getIsHistory, "0");
      PageUtil.page(request.getPage(), request.getSize());
      List<TableMatterDO> matterDOS = this.tableMatterMapper.selectList(wp);
      PageBean<TableMatterDTO> pageBeanDOS = ObjectCloneUtils.convertPageBean(new PageBean(matterDOS), TableMatterDTO.class);
      List<TableMatterDTO> matterDTOS = (List)matterDOS.stream().map((item) -> {
         TableMatterDTO dto = new TableMatterDTO();
         dto.setId(item.getRowguid());
         dto.setROWGUID(item.getRowguid());
         dto.setTASK_NAME(item.getTaskName());
         dto.setCATALOG_CODE(item.getCatalogCode());
         dto.setYWCODE(item.getYwcode());
         dto.setTASK_CODE(item.getTaskCode());
         dto.setDEPT_CODE(item.getDeptCode());
         dto.setDEPT_NAME(item.getDeptName());
         dto.setTASK_STATE(item.getTaskState());
         dto.setIS_HISTORY(item.getIsHistory());
         return dto;
      }).collect(Collectors.toList());
      pageBeanDOS.setContent(matterDTOS);
      return pageBeanDOS;
   }

   public Boolean refresh() {
      List<CatalogueSysTableDO> fieldDOS = this.list(Wrappers.<CatalogueSysTableDO>lambdaQuery().like(CatalogueSysTableDO::getBusinessForCode, "|"));
      fieldDOS.stream().forEach((item) -> {
         String[] businessForCode = new String[0];
         if (StringUtils.isNotBlank(item.getBusinessForCode())) {
            businessForCode = item.getBusinessForCode().split("\\|");
         }

         if (businessForCode.length > 0) {
            List<TableMatterDO> matterDOList = this.tableMatterMapper.selectList(Wrappers.<TableMatterDO>lambdaQuery().in(TableMatterDO::getYwcode, Arrays.asList(businessForCode)));
            if (CollectionUtil.isNotEmpty(matterDOList)) {
               List<TableMatterDO> newList = new ArrayList();
               matterDOList.stream().filter(distinctByKey(TableMatterDO::getYwcode)).forEach(newList::add);
               item.setRowGuId(String.join("|", (Iterable)newList.stream().map(TableMatterDO::getYwcode).collect(Collectors.toList())));
            }
         }

      });
      this.updateBatchById(fieldDOS);
      return null;
   }

   static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
      Map<Object, Boolean> seen = new ConcurrentHashMap();
      return (t) -> {
         return seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
      };
   }

}

package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsAndVersionNo;
import com.grandlynn.spa.catalogue.dto.TableCountDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.WordFirstModelData;
import com.grandlynn.spa.catalogue.dto.WordFirstModelOrgData;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableVersionDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueSysTableVersionMapper;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableVersionService;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("catalogueSysTableVersionService")
public class CatalogueSysTableVersionServiceImpl extends ServiceImpl<CatalogueSysTableVersionMapper, CatalogueSysTableVersionDO> implements CatalogueSysTableVersionService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueSysTableVersionServiceImpl.class);

   public WordFirstModelData getTablesBySysIdsAndVersionNo(CatalogueGetTablesBySysIdsAndVersionNo request, Map<Long, Long> sysOrgMap, Map<Long, String> orgMap, List<Integer> cellList) {
      WordFirstModelData modelData = new WordFirstModelData();
      List<WordFirstModelOrgData> orgList = new ArrayList();
      List<WordFirstModelOrgData> list = ((CatalogueSysTableVersionMapper)this.baseMapper).getTablesBySysIdsAndVersionNo(request);
      Iterator var8 = list.iterator();

      while(var8.hasNext()) {
         WordFirstModelOrgData orgData = (WordFirstModelOrgData)var8.next();
         Long sysId = orgData.getSysId();
         Long orgId = (Long)sysOrgMap.get(sysId);
         String orgName = (String)orgMap.get(orgId);
         log.info("====>sysId:{};orgId:{};orgName:{}", new Object[]{sysId, orgId, orgName});
         orgData.setOrgId(orgId);
         orgData.setOrgName(orgName);
      }

      int i = 1;
      if (list != null && !list.isEmpty()) {
         int cellIndex = 1;
         Map<Long, List<WordFirstModelOrgData>> map = (Map)list.stream().collect(Collectors.groupingBy(WordFirstModelOrgData::getOrgId));
         if (map.size() > 0) {
            cellList.add(cellIndex);
         }

         for(Iterator var20 = map.entrySet().iterator(); var20.hasNext(); ++i) {
            Map.Entry<Long, List<WordFirstModelOrgData>> entry = (Map.Entry)var20.next();
            Long orgId = (Long)entry.getKey();
            log.info("====>getTablesBySysIdsAndVersionNo==>orgId:{}", orgId);
            List<WordFirstModelOrgData> tables = (List)entry.getValue();
            Iterator var15 = tables.iterator();

            while(var15.hasNext()) {
               WordFirstModelOrgData table = (WordFirstModelOrgData)var15.next();
               table.setIndex(i);
            }

            cellIndex += tables.size();
            cellList.add(cellIndex);
            orgList.addAll(tables);
         }

         if (cellList != null && cellList.size() > 1) {
            cellList.remove(cellList.size() - 1);
         }
      }

      modelData.setOrgList(orgList);
      return modelData;
   }

   public Map<Long, Integer> getTableCount(BaseRequest request, String versionNo) {
      log.info("====>getTableCount===>入参：{}", request);
      List<TableCountDTO> list = ((CatalogueSysTableVersionMapper)this.baseMapper).getTableCount(request.getTenantId(), request.getAppId(), versionNo);
      Map<Long, Integer> map = new HashMap();
      if (list != null && !list.isEmpty()) {
         Iterator var5 = list.iterator();

         while(var5.hasNext()) {
            TableCountDTO tableCountDTO = (TableCountDTO)var5.next();
            log.info("=====>tableCountDTO:{}", tableCountDTO);
            map.put(tableCountDTO.getSysId(), tableCountDTO.getTableCount());
         }
      }

      return map;
   }

   public TablesAndFieldsDTO getTaleVersionById(BaseRequest request, Long id) {
      QueryWrapper<CatalogueSysTableVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(BaseEntity::getId, id);
      CatalogueSysTableVersionDO tableVersionDO = (CatalogueSysTableVersionDO)((CatalogueSysTableVersionMapper)this.baseMapper).selectOne(queryWrapper);
      log.info("==>tableVersionDO:{}", tableVersionDO);
      TablesAndFieldsDTO tablesAndFieldsDTO = (TablesAndFieldsDTO)tableVersionDO.clone(TablesAndFieldsDTO.class);
      return tablesAndFieldsDTO;
   }

   public void removeVersionById(Long id) {
      LambdaQueryWrapper<CatalogueSysTableVersionDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(CatalogueSysTableVersionDO::getOldId, id);
      queryWrapper.orderByDesc(BaseEntity::getCreatedTime);
      queryWrapper.last("limit 1");
      CatalogueSysTableVersionDO catalogueSysTableVersionDO = (CatalogueSysTableVersionDO)((CatalogueSysTableVersionMapper)this.baseMapper).selectOne(queryWrapper);
      if (ObjectUtil.isNotEmpty(catalogueSysTableVersionDO)) {
         this.removeById(catalogueSysTableVersionDO.getId());
      }

   }

}

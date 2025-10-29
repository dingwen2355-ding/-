package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.grandlynn.spa.catalogue.common.enums.ResourceRangeEnums;
import com.grandlynn.spa.catalogue.common.enums.WholeEnum;
import com.grandlynn.spa.catalogue.common.utils.DropDownFactory;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetDropDownListRequest;
import com.grandlynn.spa.catalogue.dto.DropDownDataDTO;
import com.grandlynn.spa.catalogue.dto.DropDownWuXiDataDTO;
import com.grandlynn.spa.catalogue.dto.EnumsDTO;
import com.grandlynn.spa.catalogue.dto.EnumsWuXiDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceApplicationSceneDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceDomainDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceIndustryCategoryDO;
import com.grandlynn.spa.catalogue.entity.ForeignCityResourceDomainDO;
import com.grandlynn.spa.catalogue.entity.ForeignCityResourceTypeDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceApplicationSceneMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceDomainMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueResourceIndustryCategoryMapper;
import com.grandlynn.spa.catalogue.mapper.ForeignCityResourceDomainMapper;
import com.grandlynn.spa.catalogue.mapper.ForeignCityResourceTypeMapper;
import com.grandlynn.spa.catalogue.service.CatalogueCommonService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CatalogueCommonServiceImpl implements CatalogueCommonService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueCommonServiceImpl.class);
   @Resource
   private ForeignCityResourceDomainMapper domainMapper;
   @Resource
   private ForeignCityResourceTypeMapper typeMapper;
   @Resource
   private CatalogueResourceDomainMapper resourceDomainMapper;
   @Resource
   private CatalogueResourceIndustryCategoryMapper industryCategoryMapper;
   @Resource
   private CatalogueResourceApplicationSceneMapper applicationSceneMapper;

   public List<EnumsDTO> getDropDownList(CatalogueGetDropDownListRequest request) {
      Map<Integer, String> map = DropDownFactory.getEnums(request.getEnumCode());
      return this.parseMap(map);
   }

   public List<EnumsDTO> getEnums() {
      Map<Integer, String> map = WholeEnum.getEnums();
      return this.parseMap(map);
   }

   public List<DropDownDataDTO> getAllDropDownData() {
      List<DropDownDataDTO> result = new ArrayList();
      Map<Integer, String> map = WholeEnum.getEnums();
      if (map.isEmpty()) {
         return Collections.emptyList();
      } else {
         Iterator var3 = map.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry)var3.next();
            DropDownDataDTO dropDownDataDTO = new DropDownDataDTO();
            Integer enumCode = (Integer)entry.getKey();
            String enumName = WholeEnum.findEnumByCode(enumCode).getName();
            Map<Integer, String> dropDownMap = DropDownFactory.getEnums(enumCode);
            if (dropDownMap != null) {
               List<EnumsDTO> enumsDTOS = this.parseMap(dropDownMap);
               dropDownDataDTO.setCode(enumCode);
               dropDownDataDTO.setName(enumName);
               dropDownDataDTO.setEnumsDTOS(enumsDTOS);
               result.add(dropDownDataDTO);
            }
         }

         return result;
      }
   }

   public List<DropDownWuXiDataDTO> getWuXiAllDropDownData() {
      List<DropDownWuXiDataDTO> result = new ArrayList();
      Map<Integer, String> map = WholeEnum.getEnums();
      if (map.isEmpty()) {
         return Collections.emptyList();
      } else {
         Iterator var3 = map.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry)var3.next();
            DropDownWuXiDataDTO dropDownWuXiDataDTO = new DropDownWuXiDataDTO();
            Integer enumCode = (Integer)entry.getKey();
            String enumName = WholeEnum.findEnumByCode(enumCode).getName();
            Map<String, String> dropDownMap = DropDownFactory.getWuXiEnums(enumCode);
            if (dropDownMap != null) {
               List<EnumsWuXiDTO> enumsDTOS = this.parseMapWuXi(dropDownMap);
               dropDownWuXiDataDTO.setCode(enumCode);
               dropDownWuXiDataDTO.setName(enumName);
               dropDownWuXiDataDTO.setEnumsDTOS(enumsDTOS);
               result.add(dropDownWuXiDataDTO);
            }
         }

         return result;
      }
   }

   public List<ForeignCityResourceDomainDO> getDomain() {
      List<ForeignCityResourceDomainDO> domains = this.domainMapper.selectList(null);
      List<ForeignCityResourceTypeDO> types = this.typeMapper.selectList(null);
      Map<String, List<ForeignCityResourceTypeDO>> typeGroups = (Map)types.stream().collect(Collectors.groupingBy((item) -> {
         return item.getType();
      }));
      domains.stream().forEach((d) -> {
         d.setChild((List)typeGroups.get(d.getName()));
      });
      return domains;
   }

   public List<EnumsDTO> getResourceRangeEnums() {
      Map<Integer, String> map = ResourceRangeEnums.getEnums();
      return this.parseMap(map);
   }

   public List<CatalogueResourceDomainDO> getResourceDomain() {
      return this.resourceDomainMapper.selectList(Wrappers.query());
   }

   public List<CatalogueResourceIndustryCategoryDO> getIndustryCategory() {
      return this.industryCategoryMapper.selectList(Wrappers.query());
   }

   public List<CatalogueResourceApplicationSceneDO> getApplicationScene() {
      return this.applicationSceneMapper.selectList(Wrappers.query());
   }

   private List<EnumsDTO> parseMap(Map<Integer, String> map) {
      if (map == null) {
         return Collections.emptyList();
      } else {
         List<EnumsDTO> list = new ArrayList();
         Iterator var3 = map.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry<Integer, String> entry = (Map.Entry)var3.next();
            EnumsDTO enumsDTO = new EnumsDTO();
            enumsDTO.setCode((Integer)entry.getKey());
            enumsDTO.setName((String)entry.getValue());
            list.add(enumsDTO);
         }

         return list;
      }
   }

   private List<EnumsWuXiDTO> parseMapWuXi(Map<String, String> map) {
      if (map == null) {
         return Collections.emptyList();
      } else {
         List<EnumsWuXiDTO> list = new ArrayList();
         Iterator var3 = map.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var3.next();
            EnumsWuXiDTO enumsDTO = new EnumsWuXiDTO();
            enumsDTO.setCode((String)entry.getKey());
            enumsDTO.setName((String)entry.getValue());
            list.add(enumsDTO);
         }

         return list;
      }
   }
}

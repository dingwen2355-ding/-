package com.grandlynn.spa.catalogue.service;

import com.grandlynn.spa.catalogue.domain.request.CatalogueGetDropDownListRequest;
import com.grandlynn.spa.catalogue.dto.DropDownDataDTO;
import com.grandlynn.spa.catalogue.dto.DropDownWuXiDataDTO;
import com.grandlynn.spa.catalogue.dto.EnumsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceApplicationSceneDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceDomainDO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceIndustryCategoryDO;
import com.grandlynn.spa.catalogue.entity.ForeignCityResourceDomainDO;

import java.util.List;

public interface CatalogueCommonService {
   List<EnumsDTO> getDropDownList(CatalogueGetDropDownListRequest request);

   List<EnumsDTO> getEnums();

   List<DropDownDataDTO> getAllDropDownData();

   List<DropDownWuXiDataDTO> getWuXiAllDropDownData();

   List<ForeignCityResourceDomainDO> getDomain();

   List<EnumsDTO> getResourceRangeEnums();

   List<CatalogueResourceDomainDO> getResourceDomain();

   List<CatalogueResourceIndustryCategoryDO> getIndustryCategory();

   List<CatalogueResourceApplicationSceneDO> getApplicationScene();
}

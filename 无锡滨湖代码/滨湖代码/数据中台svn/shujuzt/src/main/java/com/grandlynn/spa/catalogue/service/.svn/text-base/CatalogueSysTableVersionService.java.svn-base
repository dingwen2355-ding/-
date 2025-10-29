package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsAndVersionNo;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.WordFirstModelData;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableVersionDO;

import java.util.List;
import java.util.Map;

public interface CatalogueSysTableVersionService extends IService<CatalogueSysTableVersionDO> {
   WordFirstModelData getTablesBySysIdsAndVersionNo(CatalogueGetTablesBySysIdsAndVersionNo request, Map<Long, Long> sysOrgMap, Map<Long, String> orgMap, List<Integer> cellList);

   Map<Long, Integer> getTableCount(BaseRequest request, String versionNo);

   TablesAndFieldsDTO getTaleVersionById(BaseRequest request, Long id);

   void removeVersionById(Long id);
}

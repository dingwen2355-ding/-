package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.exception.GenerateResourceCodeException;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableBySysNameAndTableNameRequest;
import com.grandlynn.spa.catalogue.domain.request.GetResourceCodeRequest;
import com.grandlynn.spa.catalogue.domain.request.GetTablesByStatesRequest;
import com.grandlynn.spa.catalogue.domain.request.TableMatterRequest;
import com.grandlynn.spa.catalogue.dto.TableMatterDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueResourceCategoryDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.util.pageHelper.PageBean;

import java.util.List;
import java.util.Map;

public interface CatalogueSysTableService extends IService<CatalogueSysTableDO> {
   CatalogueSysTableDO saveWithDisableState(CatalogueSysTableDO sysTableDO, CatalogueGetTableBySysNameAndTableNameRequest request);

   Map<Long, Integer> getTableCount(BaseRequest request);

   boolean updateSysTableUnpublished(BaseRequest request, Long sysTableId, String level);

   List<CatalogueSysTableDO> getTablesBySysId(BaseRequest request, Long sysId, Long orgId, String tableName, String dataResourceName, String databaseName);

   List<CatalogueSysTableDO> getTablesBySysIdNew(BaseRequest request, Long sysId, Long orgId, String dataResourceName);

   List<CatalogueSysTableDO> getAllTables(BaseRequest request, Long[] ids, Integer tableState);

   String getResourceCode(GetResourceCodeRequest request) throws GenerateResourceCodeException;

   List<CatalogueSysTableDO> getTablesByStates(GetTablesByStatesRequest gtRequest);

   void clearForeignCityList();

   List<CatalogueResourceCategoryDO> findCategoryItemCatalogue();

   PageBean<TableMatterDTO> findMatter(TableMatterRequest request);

   Boolean refresh();
}

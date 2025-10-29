package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsAndVersionNo;
import com.grandlynn.spa.catalogue.dto.TableCountDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.WordFirstModelOrgData;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableVersionDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueSysTableVersionMapper extends BaseMapper<CatalogueSysTableVersionDO> {
   List<CatalogueSysTableVersionDO> getTablesVersionBySysId(@Param("request") CatalogueGetTableVersionRequest request);

   List<TablesAndFieldsDTO> getTablesAndFieldsBySysId(@Param("request") CatalogueGetTablesAndFieldsBySysIdRequest request);

   List<TableCountDTO> getTableCount(@Param("tenantId") String tenantId, @Param("appId") Long appId, @Param("versionNo") String versionNo);

   List<WordFirstModelOrgData> getTablesBySysIdsAndVersionNo(@Param("request") CatalogueGetTablesBySysIdsAndVersionNo request);

   int insertFromOld(@Param("oldDO") CatalogueSysTableDO catalogueSysTableDO);
}

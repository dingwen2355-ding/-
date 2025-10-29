package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.GetTablesByStatesRequest;
import com.grandlynn.spa.catalogue.dto.TableCountDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.TablesDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueSysTableDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueSysTableMapper extends BaseMapper<CatalogueSysTableDO> {
   List<TablesAndFieldsDTO> getTablesAndFieldsBySysIds(@Param("request") CatalogueGetTableBySysIdsRequest request);

   List<TablesAndFieldsDTO> getTablesAndFieldsBySysId(@Param("request") CatalogueGetTablesAndFieldsBySysIdRequest request);

   List<TablesDTO> getTablesBySysIds(@Param("request") CatalogueGetTablesBySysIdsRequest request);

   List<TableCountDTO> getTableCount(@Param("tenantId") String tenantId, @Param("appId") Long appId);

   List<CatalogueSysTableDO> getTablesByStates(@Param("request") GetTablesByStatesRequest gtRequest);
}

package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldDetailByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldVersionByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionDetailByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableVersionRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetTableResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueTableFieldDetailResponse;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysVersionDO;
import com.grandlynn.util.pageHelper.PageBean;

import java.util.List;

public interface CatalogueOrgSysVersionService extends IService<CatalogueOrgSysVersionDO> {
   PageBean<CatalogueGetTableResponse> getTablesVersion(CatalogueGetTableVersionRequest request);

   List<SystemsDTO> getSystemsByOrgIds(CatalogueGetSystemsByOrgIdsRequest request);

   CatalogueGetTableResponse getTableVersionByTableId(CatalogueGetTableVersionDetailByTableIdRequest request);

   PageBean<CatalogueTableFieldDetailResponse> getTableFieldsVersionByTaleId(CatalogueGetTableFieldVersionByTableIdRequest request);

   CatalogueTableFieldDetailResponse getFieldDetailByFieldId(CatalogueGetFieldDetailByFieldIdRequest request);

   List<TablesAndFieldsDTO> getTablesAndFieldsBySysId(CatalogueGetTablesAndFieldsBySysIdRequest request);
}

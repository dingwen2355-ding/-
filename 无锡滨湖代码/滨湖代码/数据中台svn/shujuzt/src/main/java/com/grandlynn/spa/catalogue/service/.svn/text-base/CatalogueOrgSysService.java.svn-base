package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddSysByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueAddTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDeleteSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDeleteTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDisableTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEditTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueEnableTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldDetailByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetOrgSysRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysAndOrgNameBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysByOrgIdAndSysNameRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysBySysNameRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsByOrgIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSystemsPageByOrgIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesAndFieldsBySysIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTablesBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOperatorTableFieldByFieldIdRequest;
import com.grandlynn.spa.catalogue.domain.request.CataloguePublishAllCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.CataloguePublishCatalogueRequest;
import com.grandlynn.spa.catalogue.domain.request.DisableExportRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueAddTableBySysIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSysByOrgIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetSystemsByOrgIdResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueGetTableResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueTableFieldDetailResponse;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.dto.TablesAndFieldsDTO;
import com.grandlynn.spa.catalogue.dto.TablesDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.util.pageHelper.PageBean;

import java.util.List;
import javax.validation.Valid;

public interface CatalogueOrgSysService extends IService<CatalogueOrgSysDO> {
   List<SystemsDTO> getSystemsByOrgIds(CatalogueGetSystemsByOrgIdsRequest request, int intermediate);

   CatalogueGetSystemsByOrgIdResponse getSystemsByOrgId(CatalogueGetSystemsByOrgIdRequest request, int intermediate);

   PageBean<SysContentDTO> getSystemsPageByOrgId(CatalogueGetSystemsPageByOrgIdRequest request, int intermediate);

   CatalogueGetSysByOrgIdResponse addSysByOrgId(CatalogueAddSysByOrgIdRequest request, int intermediate);

   CatalogueGetSysByOrgIdResponse getSysDetailBySysId(CatalogueGetSysBySysIdRequest request, int intermediate);

   CatalogueGetSysByOrgIdResponse editSysBySysId(CatalogueEditSysBySysIdRequest request, int intermediate);

   Boolean deleteSysBySysId(CatalogueDeleteSysBySysIdRequest request, int intermediate);

   List<SysContentDTO> getSysByOrgId(CatalogueGetOrgSysRequest request, int intermediate);

   SysContentDTO getSysBySysNameAndNodeName(CatalogueGetSysBySysNameRequest request, int intermediate);

   PageBean<CatalogueGetTableResponse> getTables(CatalogueGetTableRequest request, int intermediate);

   List<TablesAndFieldsDTO> getTablesAndFieldsByTableIds(CatalogueGetTableBySysIdsRequest request, int intermediate);

   List<TablesAndFieldsDTO> getTablesAndFieldsBySysId(CatalogueGetTablesAndFieldsBySysIdRequest request);

   PageBean<TablesDTO> getTablesBySysIds(CatalogueGetTablesBySysIdsRequest request);

   CatalogueAddTableBySysIdResponse addTableBySysId(CatalogueAddTableBySysIdRequest request, int intermediate);

   CatalogueGetTableResponse getTableByTableId(CatalogueGetTableByTableIdRequest request, int intermediate);

   CatalogueGetTableResponse getTableHistorylByTableId(CatalogueGetTableByTableIdRequest request, int intermediate);

   CatalogueGetTableResponse editTableByTableId(CatalogueEditTableByTableIdRequest request, int intermediate);

   Boolean deleteTableByTableId(CatalogueDeleteTableByTableIdRequest request, int intermediate);

   Boolean disableTableByTableId(CatalogueDisableTableByTableIdRequest request, int intermediate);

   Boolean enableTableByTableId(@Valid CatalogueEnableTableByTableIdRequest request, int intermediate);

   PageBean<CatalogueTableFieldDetailResponse> getTableFieldsByTaleId(CatalogueGetTableFieldByTableIdRequest request, int intermediate);

   CatalogueTableFieldDetailResponse addTableFieldByTaleId(CatalogueAddTableFieldByTableIdRequest request, int intermediate);

   Boolean deleteTableFieldByTaleId(CatalogueOperatorTableFieldByFieldIdRequest request, int intermediate);

   CatalogueTableFieldDetailResponse editTableFieldByTaleId(CatalogueEditTableFieldByTableIdRequest request, int intermediate);

   Boolean disanbleTableFieldByTaleId(CatalogueOperatorTableFieldByFieldIdRequest request, int intermediate);

   Boolean enableTableFieldByTaleId(CatalogueOperatorTableFieldByFieldIdRequest request, int intermediate);

   CatalogueTableFieldDetailResponse getFieldDetailByFieldId(CatalogueGetFieldDetailByFieldIdRequest request, int intermediate);

   Boolean publishCatalogue(CataloguePublishCatalogueRequest request);

   Boolean publishAll(CataloguePublishAllCatalogueRequest request);

   List<SystemsDTO> getSysByOrgIdAndSysName(CatalogueGetSysByOrgIdAndSysNameRequest request);

   List<SystemsDTO> getSysAndOrgNameBySysIds(CatalogueGetSysAndOrgNameBySysIdsRequest request);

   Boolean disableExport(DisableExportRequest request);
}

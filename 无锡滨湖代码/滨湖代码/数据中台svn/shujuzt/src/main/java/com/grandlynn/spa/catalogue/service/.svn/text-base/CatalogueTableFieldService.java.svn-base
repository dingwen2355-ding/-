package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldByTableIdAndFieldNameRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueListRequest;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;

import java.util.List;

public interface CatalogueTableFieldService extends IService<CatalogueTableFieldDO> {
   List<ExportSysCatalogueTemplateDTO> findCatalogueListBySysIds(FindCatalogueListRequest request);

   CatalogueTableFieldDO selectFieldByTableIdAndFieldName(CatalogueGetFieldByTableIdAndFieldNameRequest fieldNameRequest);

   CatalogueTableFieldDO getWithDisableState(CatalogueTableFieldDO tableFieldDO, CatalogueGetFieldByTableIdAndFieldNameRequest fieldNameRequest);
}

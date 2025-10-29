package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldByTableIdAndFieldNameRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueVersionList;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldVersionDO;

import java.util.List;

public interface CatalogueTableFieldVersionService extends IService<CatalogueTableFieldVersionDO> {
   List<ExportSysCatalogueTemplateDTO> findCatalogueListBySysIds(FindCatalogueVersionList request);

   CatalogueTableFieldVersionDO selectFieldByTableIdAndFieldName(CatalogueGetFieldByTableIdAndFieldNameRequest request);
}

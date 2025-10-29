package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueDatasourceQueryRequest;
import com.grandlynn.spa.catalogue.dto.ExportDatasourceTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceDO;
import com.grandlynn.util.pageHelper.PageBean;

public interface CatalogueDataSourceService extends IService<CatalogueDataSourceDO> {
   PageBean<CatalogueDataSourceDO> page(CatalogueDatasourceQueryRequest request);

   void templateTest(ExportDatasourceTemplateDTO dto);

   Boolean connectivityCheck(Long id);
}

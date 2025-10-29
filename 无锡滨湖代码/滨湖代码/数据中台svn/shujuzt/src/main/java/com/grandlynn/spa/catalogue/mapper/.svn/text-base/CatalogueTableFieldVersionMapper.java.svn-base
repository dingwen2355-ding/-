package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetTableFieldVersionByTableIdRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueVersionList;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldVersionDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueTableFieldVersionMapper extends BaseMapper<CatalogueTableFieldVersionDO> {
   List<ExportSysCatalogueTemplateDTO> findCatalogueListBySysIds(@Param("request") FindCatalogueVersionList request);

   List<CatalogueTableFieldVersionDO> getTableFieldsVersionByTaleId(@Param("request") CatalogueGetTableFieldVersionByTableIdRequest request);

   int insertFromOld(@Param("oldDO") CatalogueTableFieldDO catalogueTableFieldDO);
}

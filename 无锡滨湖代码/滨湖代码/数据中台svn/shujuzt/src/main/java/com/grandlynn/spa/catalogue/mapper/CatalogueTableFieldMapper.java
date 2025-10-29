package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueListRequest;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueTableFieldMapper extends BaseMapper<CatalogueTableFieldDO> {
   List<ExportSysCatalogueTemplateDTO> findCatalogueListBySysIds(@Param("request") FindCatalogueListRequest request);
}

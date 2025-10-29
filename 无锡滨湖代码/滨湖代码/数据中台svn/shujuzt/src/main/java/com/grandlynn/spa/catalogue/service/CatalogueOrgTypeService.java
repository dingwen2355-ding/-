package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgTypeRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgTypeDO;
import java.util.List;

public interface CatalogueOrgTypeService extends IService<CatalogueOrgTypeDO> {
   Boolean saveOrgType(CatalogueOrgTypeRequest catalogueOrgTypeRequest);

   Boolean editOrgType(CatalogueOrgTypeRequest catalogueOrgTypeRequest);

   Boolean removeByOrgTypeId(Long id);

   List<CatalogueOrgTypeDO> listBy();

   String getTypeNameById(Long id);
}

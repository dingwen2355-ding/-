package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;

public interface CatalogueOrgContentRelationService extends IService<CatalogueOrgContentRelationDO> {
   CatalogueOrgContentRelationDO getOneByContentId(Long id);
}

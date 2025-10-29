package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgVersionQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgVersionDO;

import java.util.List;

public interface CatalogueOrgVersionService extends IService<CatalogueOrgVersionDO> {
   OrgNode getOrgNodeById(BaseRequest request, Long orgId, String versionNo);

   CatalogueOrgQueryOrgTreeResponse queryOrgVersionTree(CatalogueOrgVersionQueryOrgTreeRequest request, String versionNo);

   List<CatalogueOrgVersionDO> getOrgIdsByLeaf(BaseRequest request, Integer leaf, String versionNo);
}

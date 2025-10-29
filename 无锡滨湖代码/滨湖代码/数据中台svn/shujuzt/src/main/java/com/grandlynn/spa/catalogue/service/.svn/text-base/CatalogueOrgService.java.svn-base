package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.common.enums.OrgAddEnum;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgDeleteOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgEditOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgEnableOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgGetDetailRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgDetailResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgEditOrgResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.dto.OrgStatisticsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;

import java.util.List;

public interface CatalogueOrgService extends IService<CatalogueOrgDO> {
   Boolean addOrg(BaseRequest request, OrgAddEnum orgAddEnum, int intermediate);

   Boolean deleteOrgById(CatalogueOrgDeleteOrgRequest request, int intermediate);

   CatalogueOrgEditOrgResponse editOrgById(CatalogueOrgEditOrgRequest request, int intermediate);

   boolean findOrgByNodeCodeOrNodeNoOrNodeName(BaseRequest request, String nodeCode, String nodeNo, int intermediate, String nodeName);

   boolean findOrgByNodeNoAndNodeName(BaseRequest request, String nodeNo, String nodeName, int intermediate);

   CatalogueOrgDO selectOrgByNodeNoAndNodeName(BaseRequest request, String nodeNo, String nodeName, int intermediate, SysAdminUser user);

   List<CatalogueOrgDO> selectByNodeCodeOrNodeNoOrNodeName(BaseRequest request, String nodeCode, String nodeNo, int intermediate, String nodeName);

   Boolean enableOrg(CatalogueOrgEnableOrgRequest request, int intermediate);

   CatalogueOrgDetailResponse getDetailById(CatalogueOrgGetDetailRequest request, int intermediate);

   OrgNode getOrgNodeById(BaseRequest request, Long orgId, int intermediate);

   CatalogueOrgQueryOrgTreeResponse queryOrgTree(CatalogueOrgQueryOrgTreeRequest request, int intermediate, boolean limit);

   CatalogueOrgDO getByNodeName(BaseRequest request, String nodeName);

   List<CatalogueOrgDO> getListByIds(Long[] orgids);

   List<OrgStatisticsDTO> resourceStatistics();

   Long selectByOrgName(String name);

   List<OrgNode> queryOrgListAndTableCount();

   List<CatalogueOrgDetailResponse> queryOrgList(Long getParentId);

   String getNodeNameSimplifyById(Long id);

   CatalogueOrgQueryOrgTreeResponse queryUnOwnOrgList(CatalogueOrgQueryOrgTreeRequest request);

   Long setOrgTypeId(String orgTypeName);
}

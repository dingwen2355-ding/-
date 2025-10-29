package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgVersionQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgVersionDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationVersionMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgVersionMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueOrgVersionService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableVersionService;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("catalogueOrgVersionService")
public class CatalogueOrgVersionServiceImpl extends ServiceImpl<CatalogueOrgVersionMapper, CatalogueOrgVersionDO> implements CatalogueOrgVersionService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueOrgVersionServiceImpl.class);
   @Resource
   private CatalogueOrgContentRelationVersionMapper contentRelationVersionMapper;
   @Resource
   private CatalogueSysTableVersionService sysTableVersionService;

   public OrgNode getOrgNodeById(BaseRequest request, Long orgId, String versionNo) {
      LambdaQueryWrapper<CatalogueOrgVersionDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      queryWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      queryWrapper.eq(BaseEntity::getId, orgId);
      queryWrapper.eq(CatalogueOrgVersionDO::getVersionNo, versionNo);
      CatalogueOrgVersionDO orgDO = this.baseMapper.selectOne(queryWrapper);
      return orgDO.clone(OrgNode.class);
   }

   public CatalogueOrgQueryOrgTreeResponse queryOrgVersionTree(CatalogueOrgVersionQueryOrgTreeRequest request, String versionNo) {
      SysAdminUser user = SecurityUtil.currentUser();
      LambdaQueryWrapper<CatalogueOrgVersionDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      queryWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      queryWrapper.eq(CatalogueOrgVersionDO::getVersionNo, versionNo);
      queryWrapper.orderByAsc(CatalogueOrgVersionDO::getNodeCode);
      queryWrapper.in(user.getOrgLimit(), CatalogueOrgVersionDO::getOldId, user.getOrgids());
      List<CatalogueOrgVersionDO> allOrgDOS = this.baseMapper.selectList(queryWrapper);
      List queryOrgDOS;
      if (StringUtils.isBlank(request.getNodeName()) && request.getId() == null) {
         queryWrapper.eq(CatalogueOrgVersionDO::getParentId, 0);
         queryOrgDOS = this.baseMapper.selectList(queryWrapper);
      } else {
         queryWrapper.eq(request.getId() != null, CatalogueOrgVersionDO::getParentId, request.getId());
         queryWrapper.like(StringUtils.isNotBlank(request.getNodeName()), CatalogueOrgVersionDO::getNodeName, request.getNodeName());
         queryOrgDOS = this.baseMapper.selectList(queryWrapper);
      }

      CatalogueOrgQueryOrgTreeResponse response = new CatalogueOrgQueryOrgTreeResponse();
      if (queryOrgDOS != null && !queryOrgDOS.isEmpty()) {
         List<OrgNode> allOrgS = ObjectCloneUtils.convertList(allOrgDOS, OrgNode.class);
         List<OrgNode> queryOrgS = ObjectCloneUtils.convertList(queryOrgDOS, OrgNode.class);
         Map<Long, Integer> tableCountMap = this.sysTableVersionService.getTableCount(request, request.getVersionNo());
         List<OrgNode> finalTree = this.compositeChild(allOrgS, queryOrgS, request.getContentType(), versionNo, tableCountMap);
         response.setOrgList(finalTree);
      }

      return response;
   }

   public List<CatalogueOrgVersionDO> getOrgIdsByLeaf(BaseRequest request, Integer leaf, String versionNo) {
      QueryWrapper<CatalogueOrgVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId()).eq(CatalogueOrgVersionDO::getVersionNo, versionNo)
               .eq(leaf != null, CatalogueOrgVersionDO::getLeaf, leaf + "");
      return this.baseMapper.selectList(queryWrapper);
   }

   private List<OrgNode> compositeChild(List<OrgNode> allOrgS, List<OrgNode> queryOrgS, Integer contentType, String versionNo, Map<Long, Integer> tableCountMap) {
      Iterator var6 = queryOrgS.iterator();

      while(var6.hasNext()) {
         OrgNode orgNode = (OrgNode)var6.next();
         orgNode.setChildOrgList(this.getChild(allOrgS, orgNode.getOldId(), contentType, versionNo, tableCountMap));
         orgNode.setCount(orgNode.getChildOrgList().size());
         if (contentType != null) {
         }
      }

      return queryOrgS;
   }

   private List<OrgNode> getChild(List<OrgNode> allOrgS, Long id, Integer contentType, String versionNo, Map<Long, Integer> tableCountMap) {
      List<OrgNode> tempChild = new ArrayList();
      Iterator var7 = allOrgS.iterator();

      OrgNode orgNode;
      while(var7.hasNext()) {
         orgNode = (OrgNode)var7.next();
         Long parentId = orgNode.getParentId();
         if (parentId.equals(id)) {
            tempChild.add(orgNode);
         }
      }

      var7 = tempChild.iterator();

      while(var7.hasNext()) {
         orgNode = (OrgNode)var7.next();
         orgNode.setChildOrgList(this.getChild(allOrgS, orgNode.getOldId(), contentType, versionNo, tableCountMap));
         orgNode.setCount(orgNode.getChildOrgList().size());
         if (contentType != null) {
         }
      }

      if (tempChild.isEmpty()) {
         return Collections.emptyList();
      } else {
         return tempChild;
      }
   }

}

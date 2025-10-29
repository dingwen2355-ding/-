package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.CreditCodeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.base.BaseRequest;
import com.grandlynn.spa.catalogue.base.BaseResponse;
import com.grandlynn.spa.catalogue.common.enums.IntermediateEnum;
import com.grandlynn.spa.catalogue.common.enums.OrgAddEnum;
import com.grandlynn.spa.catalogue.common.utils.HttpUtils;
import com.grandlynn.spa.catalogue.common.utils.OrgCodeUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgAddChildOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgAddEqualOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgAddOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgDeleteOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgEditOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgEnableOrgRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgGetDetailRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgQueryOrgTreeRequest;
import com.grandlynn.spa.catalogue.domain.request.report.ReportCatalogueCategoryRequest;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgDetailResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgEditOrgResponse;
import com.grandlynn.spa.catalogue.domain.response.CatalogueOrgQueryOrgTreeResponse;
import com.grandlynn.spa.catalogue.dto.OrgNode;
import com.grandlynn.spa.catalogue.dto.OrgStatisticsDTO;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgTypeDO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationMapper;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgTypeService;
import com.grandlynn.spa.catalogue.service.CatalogueSysTableService;
import com.grandlynn.spa.catalogue.service.ReportCatalogueService;

import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("catalogueOrgService")
public class CatalogueOrgServiceImpl extends ServiceImpl<CatalogueOrgMapper, CatalogueOrgDO> implements CatalogueOrgService {
   private static final Logger log = LoggerFactory.getLogger(CatalogueOrgServiceImpl.class);
   @Resource
   private CatalogueOrgContentRelationMapper contentRelationMapper;
   @Resource
   private CatalogueSysTableService sysTableService;
   @Value("${SPA_GATEWAY_URI}")
   private String spa_gateway_uri;
   @Value("${SOL_OR_API}")
   private String sol_or_api;
   @Autowired
   private HttpUtils httpUtils;
   @Resource
   private CatalogueOrgTypeService catalogueOrgTypeService;
   @Resource
   private ReportCatalogueService reportCatalogueService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean addOrg(BaseRequest request, OrgAddEnum orgAddEnum, int intermediate) {
      CatalogueOrgDO catalogueOrgDO = (CatalogueOrgDO)request.clone(CatalogueOrgDO.class);
      Boolean result = Boolean.FALSE;
      switch (orgAddEnum) {
         case ORG_ADD_NEW:
            CatalogueOrgAddOrgRequest addOrgRequest = (CatalogueOrgAddOrgRequest)request;
            result = this.addNewOrg(addOrgRequest, catalogueOrgDO, intermediate);
            break;
         case ORG_ADD_EQUAL:
            CatalogueOrgAddEqualOrgRequest addEqualOrgRequest = (CatalogueOrgAddEqualOrgRequest)request;
            result = this.addEqualOrg(addEqualOrgRequest, catalogueOrgDO, intermediate);
            break;
         case ORG_ADD_CHILD:
            CatalogueOrgAddChildOrgRequest addChildOrgRequest = (CatalogueOrgAddChildOrgRequest)request;
            result = this.addChildOrg(addChildOrgRequest, catalogueOrgDO, intermediate);
      }

      return result;
   }

   private Boolean addNewOrg(CatalogueOrgAddOrgRequest request, CatalogueOrgDO catalogueOrgDO, int intermediate) {
      if (StrUtil.isEmpty(request.getNodeNo())) {
         throw new ApplicationException("统一社会信用代码不能为空");
      } else if (!CreditCodeUtil.isCreditCode(request.getNodeNo())) {
         throw new ApplicationException("统一社会信用代码无效");
      } else {
         String orgCode = null;
         if (((CatalogueOrgMapper)this.baseMapper).selectCount(null) > 0) {
            orgCode = this.calOrgCode(request, 0L, intermediate);
         } else {
            orgCode = OrgCodeUtil.generateOrgCode((String)null);
         }

         log.info("addNewOrg===>{}", orgCode);
         catalogueOrgDO.setNodeCode(orgCode);
         boolean exist = this.findOrgByNodeCodeOrNodeNoOrNodeName(request, orgCode, request.getNodeNo(), intermediate, request.getNodeName());
         if (!exist) {
            int row =this.baseMapper.insert(catalogueOrgDO);
            if (row > 0) {
               ReportCatalogueCategoryRequest categoryRequest = this.convertToReportCatalogueCategoryRequest(catalogueOrgDO);

               try {
                  String code = this.reportCatalogueService.catalogueCategoryRegister(categoryRequest);
                  if (StringUtils.isNotBlank(code)) {
                     this.baseMapper.update(null, Wrappers.<CatalogueOrgDO>lambdaUpdate().set(CatalogueOrgDO::getExternalCode, code).eq(BaseEntity::getId, catalogueOrgDO.getId()));
                  }
               } catch (Exception var9) {
                  log.info("上报目录注册数据失败：{}", JSON.toJSONString(categoryRequest));
               }

               return true;
            } else {
               return false;
            }
         } else {
            log.error("存在重复的orgCode:[{}];nodeNo:[{}];nodeName:[{}]", new Object[]{orgCode, request.getNodeNo(), request.getNodeName()});
            throw new ApplicationException("请检查是否存在重复的组织机构代码、组织全称！");
         }
      }
   }

   private Boolean addEqualOrg(CatalogueOrgAddEqualOrgRequest request, CatalogueOrgDO catalogueOrgDO, int intermediate) {
      String orgCode = this.calOrgCode(request, request.getParentId(), intermediate);
      log.info("addEqualOrg===>orgCode={}", orgCode);
      catalogueOrgDO.setNodeCode(orgCode);
      boolean exist = this.findOrgByNodeCodeOrNodeNoOrNodeName(request, orgCode, request.getNodeNo(), intermediate, request.getNodeName());
      if (!exist) {
         int row = this.baseMapper.insert(catalogueOrgDO);
         if (row > 0) {
            ReportCatalogueCategoryRequest categoryRequest = this.convertToReportCatalogueCategoryRequest(catalogueOrgDO);

            try {
               String code = this.reportCatalogueService.catalogueCategoryRegister(categoryRequest);
               if (StringUtils.isNotBlank(code)) {
                  this.baseMapper.update(null, Wrappers.<CatalogueOrgDO>lambdaUpdate().set(CatalogueOrgDO::getExternalCode, code).eq(BaseEntity::getId, catalogueOrgDO.getId()));
               }
            } catch (Exception var9) {
               log.info("上报目录注册数据失败：{}", JSON.toJSONString(categoryRequest));
            }

            return true;
         } else {
            return false;
         }
      } else {
         log.error("存在重复的orgCode{}或nodeNo{};nodeName:{}", new Object[]{orgCode, request.getNodeNo(), request.getNodeName()});
         throw new ApplicationException("请检查是否存在重复的组织机构代码、组织全称！");
      }
   }

   private Boolean addChildOrg(CatalogueOrgAddChildOrgRequest request, CatalogueOrgDO catalogueOrgDO, int intermediate) {
      catalogueOrgDO.setParentId(request.getId());
      catalogueOrgDO.setId((Long)null);
      String orgCode = this.calOrgCode(request, request.getId(), intermediate);
      log.info("addChildOrg===>{}", orgCode);
      catalogueOrgDO.setNodeCode(orgCode);
      boolean exist = this.findOrgByNodeCodeOrNodeNoOrNodeName(request, orgCode, request.getNodeNo(), intermediate, request.getNodeName());
      if (!exist) {
         return this.baseMapper.insert(catalogueOrgDO) > 0;
      } else {
         log.error("存在重复的orgCode{}或nodeNo{};nodeName:{}", new Object[]{orgCode, request.getNodeNo(), request.getNodeName()});
         throw new ApplicationException("请检查是否存在重复的组织机构代码、组织全称！");
      }
   }

   private synchronized String calOrgCode(BaseRequest request, Long parentId, int intermediate) {
      String orgCode = "A001";
      List<CatalogueOrgDO> results = this.findOrgByParentId(request, parentId, intermediate);
      String initCode;
      if (results != null && !results.isEmpty()) {
         List<String> codeList = new ArrayList();
         initCode = results.get(0).getNodeCode();
         Iterator<CatalogueOrgDO> var8 = results.iterator();

         while(var8.hasNext()) {
            CatalogueOrgDO result = var8.next();
            String tempNodeCode = result.getNodeCode();
            String lastCode = OrgCodeUtil.getLastCode(tempNodeCode);
            codeList.add(lastCode);
         }

         String maxCode = OrgCodeUtil.getMaxCode(codeList);
         String otherCode = OrgCodeUtil.getOtherCode(initCode);
         log.info("系统生成编码maxCode={};otherCode={}", maxCode, otherCode);
         orgCode = otherCode + OrgCodeUtil.generateOrgCode(maxCode);
      } else {
         CatalogueOrgDO catalogueOrgDO = this.getDetailById(request, parentId, intermediate);
         initCode = catalogueOrgDO.getNodeCode();
         orgCode = OrgCodeUtil.constructNodeCode(initCode);
         log.info("添加子级，constructNodeCode之后的节点编码{}", orgCode);
      }

      log.info("系统编码计算:calOrgCode={}", orgCode);
      return orgCode;
   }

   public boolean findOrgByNodeCodeOrNodeNoOrNodeName(BaseRequest request, String nodeCode, String nodeNo, int intermediate, String nodeName) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(CatalogueOrgDO::getNodeCode, nodeCode)
              .eq(CatalogueOrgDO::getIntermediate, IntermediateEnum.INTERMEDIATE.getCode())
              .or()
              .eq(CatalogueOrgDO::getNodeNo, nodeNo)
              .or()
              .eq(nodeName != null, CatalogueOrgDO::getNodeName, nodeName);
      List<CatalogueOrgDO> list = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
      boolean exist = list != null && !list.isEmpty();
      log.info("===>findOrgByNodeCodeOrNodeNo===>存在数据:{};list:{}", exist, list);
      return exist;
   }

   public boolean findOrgByNodeNoAndNodeName(BaseRequest request, String nodeNo, String nodeName, int intermediate) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId()).eq(CatalogueOrgDO::getNodeCode, nodeNo)
              .eq(CatalogueOrgDO::getNodeName, nodeName);
      List<CatalogueOrgDO> list = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
      return list != null && !list.isEmpty();
   }

   public CatalogueOrgDO selectOrgByNodeNoAndNodeName(BaseRequest request, String nodeNo, String nodeName, int intermediate, SysAdminUser user) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId()).eq(CatalogueOrgDO::getNodeNo, nodeNo)
              .eq(CatalogueOrgDO::getNodeName, nodeName)
              .in(user.getOrgLimit(), BaseEntity::getId, user.getOrgids());
      return this.baseMapper.selectOne(queryWrapper);
   }

   public List<CatalogueOrgDO> selectByNodeCodeOrNodeNoOrNodeName(BaseRequest request, String nodeCode, String nodeNo, int intermediate, String nodeName) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              // TODO: 2024/5/17  sql可能有逻辑问题
//              .and(qw -> qw.eq(CatalogueOrgDO::getNodeCode, nodeCode).or().eq(CatalogueOrgDO::getNodeNo, nodeNo).or()
//                      .eq(nodeName != null, CatalogueOrgDO::getNodeName, nodeName))
              .eq(CatalogueOrgDO::getNodeCode, nodeCode)
              .or()
              .eq(CatalogueOrgDO::getNodeNo, nodeNo)
              .or()
              .eq(nodeName != null, CatalogueOrgDO::getNodeName, nodeName);
      return this.baseMapper.selectList(queryWrapper);
   }

   private List<CatalogueOrgDO> findOrgByNodeNoOrNodeName(BaseRequest request, String nodeNo, int intermediate, String nodeName) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(nodeNo != null, CatalogueOrgDO::getNodeNo, nodeNo)
              .or()
              .eq(nodeName != null, CatalogueOrgDO::getNodeName, nodeName);
      return ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
   }

   private List<CatalogueOrgDO> findOrgByParentId(BaseRequest request, Long parentId, int intermediate) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(CatalogueOrgDO::getParentId, parentId)
              .eq(CatalogueOrgDO::getIntermediate, 1);
      return ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean deleteOrgById(CatalogueOrgDeleteOrgRequest request, int intermediate) {
      List<CatalogueOrgDO> list = this.findOrgByParentId(request, request.getId(), intermediate);
      List<SysContentDTO> sysDTOList = this.contentRelationMapper.findSysContentByOrgId(new Long[]{request.getId()}, 1, (String)null, intermediate, (String)null, (String)null, (String)null);
      if ((list == null || list.isEmpty()) && (sysDTOList == null || sysDTOList.isEmpty())) {
         CatalogueOrgDO orgDO = (CatalogueOrgDO)((CatalogueOrgMapper)this.baseMapper).selectById(request.getId());
         QueryWrapper<CatalogueOrgDO> delWrapper = new QueryWrapper();
         delWrapper.lambda()
                 .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
                 .eq(BaseEntity::getTenantId, request.getTenantId())
                 .eq(BaseEntity::getId, request.getId());
         int row = ((CatalogueOrgMapper)this.baseMapper).delete(delWrapper);
         if (row > 0) {
            ReportCatalogueCategoryRequest categoryRequest = this.convertToReportCatalogueCategoryRequest(orgDO);

            try {
               this.reportCatalogueService.catalogueCategoryCancel(categoryRequest);
            } catch (Exception var10) {
               log.info("上报目录取消数据失败：{}", JSON.toJSONString(categoryRequest));
            }

            return true;
         } else {
            return false;
         }
      } else {
         throw new ApplicationException(list != null && !list.isEmpty() ? "存在子节点！" : "节点有关联系统！");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public CatalogueOrgEditOrgResponse editOrgById(CatalogueOrgEditOrgRequest request, int intermediate) {
      boolean exist = false;
      String warnMsg = "";
      List<CatalogueOrgDO> list = this.findOrgByNodeNoOrNodeName(request, request.getNodeNo(), intermediate, request.getNodeName());
      Iterator var6 = list.iterator();

      CatalogueOrgDO catalogueOrgDO;
      while(var6.hasNext()) {
         catalogueOrgDO = (CatalogueOrgDO)var6.next();
         if (!request.getId().equals(catalogueOrgDO.getId())) {
            String tempNodeNo = catalogueOrgDO.getNodeNo();
            String tempNodeName = catalogueOrgDO.getNodeName();
            if (tempNodeNo.equals(request.getNodeNo())) {
               warnMsg = "该组织代码" + tempNodeNo + "已存在！";
            }

            if (tempNodeName.equals(request.getNodeName())) {
               warnMsg = warnMsg + "该组织全称" + tempNodeName + "已存在!";
            }

            exist = true;
            break;
         }
      }

      if (!exist) {
         boolean validOaId = this.validOaId(request.getId(), request.getOaId());
         if (validOaId) {
            catalogueOrgDO = (CatalogueOrgDO)request.clone(CatalogueOrgDO.class);
            ((CatalogueOrgMapper)this.baseMapper).updateById(catalogueOrgDO);
            CatalogueOrgDO catalogueOrgInfo = (CatalogueOrgDO)((CatalogueOrgMapper)this.baseMapper).selectById(request.getId());
            catalogueOrgDO.setExternalCode(catalogueOrgInfo.getExternalCode());
            ReportCatalogueCategoryRequest categoryRequest = this.convertToReportCatalogueCategoryRequest(catalogueOrgDO);
            if (StringUtils.isBlank(catalogueOrgDO.getExternalCode())) {
               try {
                  String code = this.reportCatalogueService.catalogueCategoryRegister(categoryRequest);
                  if (StringUtils.isNotBlank(code)) {
                     this.baseMapper
                             .update(null, Wrappers.<CatalogueOrgDO>lambdaUpdate()
                                     .set(CatalogueOrgDO::getExternalCode, code)
                                     .eq(BaseEntity::getId, catalogueOrgDO.getId()));
                  }
               } catch (Exception var12) {
                  log.info("上报目录注册数据失败：{}", JSON.toJSONString(categoryRequest));
               }
            } else {
               try {
                  this.reportCatalogueService.catalogueCategoryModify(categoryRequest);
               } catch (Exception var11) {
                  log.info("上报目录变更数据失败：{}", JSON.toJSONString(categoryRequest));
               }
            }

            return (CatalogueOrgEditOrgResponse)catalogueOrgDO.clone(CatalogueOrgEditOrgResponse.class);
         } else {
            log.error("oaId已经存在：{}", request.getOaId());
            throw new ApplicationException("组织ID已经存在!");
         }
      } else {
         log.error("重复的组织代码或组织全称：{}", warnMsg);
         throw new ApplicationException(warnMsg);
      }
   }

   private boolean validOaId(Long id, Long oaId) {
      if (oaId == null) {
         return true;
      } else {
         QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
         queryWrapper.lambda().eq(CatalogueOrgDO::getOaId, oaId);
         List<CatalogueOrgDO> list = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
         boolean validOaId = true;
         Iterator var6 = list.iterator();

         while(var6.hasNext()) {
            CatalogueOrgDO catalogueOrgDO = (CatalogueOrgDO)var6.next();
            if (!id.equals(catalogueOrgDO.getId())) {
               validOaId = false;
               break;
            }
         }

         return validOaId;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean enableOrg(CatalogueOrgEnableOrgRequest request, int intermediate) {
      CatalogueOrgDO catalogueOrgDO = (CatalogueOrgDO)request.clone(CatalogueOrgDO.class);
      return ((CatalogueOrgMapper)this.baseMapper).updateById(catalogueOrgDO) > 0;
   }

   public CatalogueOrgDetailResponse getDetailById(CatalogueOrgGetDetailRequest request, int intermediate) {
      CatalogueOrgDO catalogueOrgDO = this.getDetailById(request, request.getId(), intermediate);
      CatalogueOrgDetailResponse response = new CatalogueOrgDetailResponse();
      if (catalogueOrgDO != null) {
         response = catalogueOrgDO.clone(CatalogueOrgDetailResponse.class);
      }

      return response;
   }

   public OrgNode getOrgNodeById(BaseRequest request, Long orgId, int intermediate) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(BaseEntity::getId, orgId)
              .eq(CatalogueOrgDO::getIntermediate, intermediate);
      CatalogueOrgDO orgDO = this.baseMapper.selectOne(queryWrapper);
      return orgDO.clone(OrgNode.class);
   }

   private CatalogueOrgDO getDetailById(BaseRequest request, Long id, int intermediate) {
      QueryWrapper<CatalogueOrgDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda()
              .eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId())
              .eq(BaseEntity::getTenantId, request.getTenantId())
              .eq(BaseEntity::getId, id)
              .eq(CatalogueOrgDO::getIntermediate, intermediate);
      return this.baseMapper.selectOne(queryWrapper);
   }

   public CatalogueOrgQueryOrgTreeResponse queryOrgTree(CatalogueOrgQueryOrgTreeRequest request, int intermediate, boolean limit) {
      SysAdminUser user = SecurityUtil.currentUser();
      LambdaQueryWrapper<CatalogueOrgDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      queryWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      queryWrapper.eq(CatalogueOrgDO::getIntermediate, intermediate);
      queryWrapper.orderByAsc(CatalogueOrgDO::getSort);
      if (user.getOrgLimit()) {
         queryWrapper.in(limit && user.getOrgLimit(), BaseEntity::getId, user.getOrgids());
      }

      List<CatalogueOrgDO> allOrgDOS = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
      List queryOrgDOS;
      if (StringUtils.isBlank(request.getNodeName()) && StringUtils.isBlank(request.getNodeNo()) && request.getId() == null && ObjectUtil.isEmpty(request.getOrgTypeId()) && StrUtil.isBlank(request.getNodeNameSimplify())) {
         queryWrapper.eq(CatalogueOrgDO::getParentId, 0);
         queryOrgDOS = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
      } else {
         queryWrapper.eq(request.getId() != null, CatalogueOrgDO::getParentId, request.getId());
         queryWrapper.like(StringUtils.isNotBlank(request.getNodeName()), CatalogueOrgDO::getNodeName, request.getNodeName());
         queryWrapper.like(StringUtils.isNotBlank(request.getNodeNo()), CatalogueOrgDO::getNodeNo, request.getNodeNo());
         queryWrapper.eq(ObjectUtil.isNotEmpty(request.getOrgTypeId()), CatalogueOrgDO::getOrgTypeId, request.getOrgTypeId());
         queryWrapper.like(StrUtil.isNotEmpty(request.getNodeNameSimplify()), CatalogueOrgDO::getNodeNameSimplify, request.getNodeNameSimplify());
         queryOrgDOS = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
      }

      CatalogueOrgQueryOrgTreeResponse response = new CatalogueOrgQueryOrgTreeResponse();
      if (queryOrgDOS != null && !queryOrgDOS.isEmpty()) {
         List<OrgNode> allOrgS = ObjectCloneUtils.convertList(allOrgDOS, OrgNode.class);
         List<OrgNode> queryOrgS = ObjectCloneUtils.convertList(queryOrgDOS, OrgNode.class);
         Map<Long, Integer> tableCountMap = this.sysTableService.getTableCount(request);
         List<OrgNode> finalTree = this.compositeChild(allOrgS, queryOrgS, request.getContentType(), intermediate, tableCountMap);
         response.setOrgList(finalTree);
      }

      return response;
   }

   public CatalogueOrgDO getByNodeName(BaseRequest request, String nodeName) {
      if (nodeName == null) {
         return null;
      } else {
         LambdaQueryWrapper<CatalogueOrgDO> qw = new LambdaQueryWrapper();
         qw.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
         qw.eq(BaseEntity::getTenantId, request.getTenantId());
         qw.eq(nodeName != null, CatalogueOrgDO::getNodeName, nodeName);
         List<CatalogueOrgDO> list = ((CatalogueOrgMapper)this.baseMapper).selectList(qw);
         return CollUtil.isEmpty(list) ? null : (CatalogueOrgDO)list.get(0);
      }
   }

   public List<CatalogueOrgDO> getListByIds(Long[] orgids) {
      if (orgids != null && orgids.length != 0) {
         LambdaQueryWrapper<CatalogueOrgDO> qw = new LambdaQueryWrapper();
         qw.in(BaseEntity::getId, orgids);
         qw.orderByAsc(CatalogueOrgDO::getParentId);
         return ((CatalogueOrgMapper)this.baseMapper).selectList(qw);
      } else {
         return new ArrayList();
      }
   }

   public List<OrgStatisticsDTO> resourceStatistics() {
      List<OrgStatisticsDTO> statisticsDTOList = ((CatalogueOrgMapper)this.baseMapper).resourceStatistics();
      List<Long> orgIds = (List)statisticsDTOList.stream().map(OrgStatisticsDTO::getOrgId).collect(Collectors.toList());
      Map<String, Integer> map = this.getDataSizeByOrgIds(orgIds);
      List<OrgStatisticsDTO> statisticsDTOListSort = (List)statisticsDTOList.stream().peek((item) -> {
         if (CollectionUtil.isNotEmpty(map) && map.containsKey(item.getOrgId().toString())) {
            item.setDataSize(((Integer)map.get(item.getOrgId().toString())).longValue());
         } else {
            item.setDataSize(0L);
         }

      }).filter((item) -> {
         return item.getDataSize() > 0L;
      }).sorted(Comparator.comparing(OrgStatisticsDTO::getDataSize).reversed()).collect(Collectors.toList());
      return statisticsDTOListSort;
   }

   public Long selectByOrgName(String name) {
      LambdaQueryWrapper<CatalogueOrgDO> qw = new LambdaQueryWrapper();
      qw.select(BaseEntity::getId)
              .eq(CatalogueOrgDO::getNodeName, name).or()
              .eq(CatalogueOrgDO::getNodeNameSimplify, name)
              .last("limit 1");
      CatalogueOrgDO catalogueOrgDO = (CatalogueOrgDO)((CatalogueOrgMapper)this.baseMapper).selectOne(qw);
      return catalogueOrgDO != null ? catalogueOrgDO.getId() : null;
   }

   public List<OrgNode> queryOrgListAndTableCount() {
      return ((CatalogueOrgMapper)this.baseMapper).queryOrgListAndTableCount();
   }

   public List<CatalogueOrgDetailResponse> queryOrgList(Long getParentId) {
      LambdaQueryWrapper<CatalogueOrgDO> qw = new LambdaQueryWrapper();
      qw.eq(CatalogueOrgDO::getParentId, 0);
      List<CatalogueOrgDO> catalogueOrgDOS = ((CatalogueOrgMapper)this.baseMapper).selectList(qw);
      List<CatalogueOrgDetailResponse> orgDetailResponses = new ArrayList();
      catalogueOrgDOS.stream().forEach((catalogueOrgDO) -> {
         CatalogueOrgDetailResponse response = new CatalogueOrgDetailResponse();
         catalogueOrgDO.clone(CatalogueOrgDetailResponse.class);
         orgDetailResponses.add(response);
      });
      return orgDetailResponses;
   }

   public String getNodeNameSimplifyById(Long id) {
      return ((CatalogueOrgDO)this.getById(id)).getNodeNameSimplify();
   }

   public CatalogueOrgQueryOrgTreeResponse queryUnOwnOrgList(CatalogueOrgQueryOrgTreeRequest request) {
      SysAdminUser user = SecurityUtil.currentUser();
      if (user == null) {
         throw new ApplicationException("登录信息异常");
      } else {
         LambdaQueryWrapper<CatalogueOrgDO> queryWrapper = new LambdaQueryWrapper();
         queryWrapper.orderByAsc(CatalogueOrgDO::getSort);
         queryWrapper.eq(CatalogueOrgDO::getParentId, 0);
         queryWrapper.eq(BaseEntity::getDeleted, 0);
         if (request.getContentType() == null) {
            Long[] userOrgIds = user.getOrgids();
            if (userOrgIds != null && userOrgIds.length > 0) {
               queryWrapper.ne(BaseEntity::getId, userOrgIds[0]);
            }
         }

         List<CatalogueOrgDO> catalogueOrgList = ((CatalogueOrgMapper)this.baseMapper).selectList(queryWrapper);
         List<OrgNode> orgNodeList = ObjectCloneUtils.convertList(catalogueOrgList, OrgNode.class);
         CatalogueOrgQueryOrgTreeResponse orgQueryOrgTreeResponse = new CatalogueOrgQueryOrgTreeResponse();
         orgQueryOrgTreeResponse.setOrgList(orgNodeList);
         return orgQueryOrgTreeResponse;
      }
   }

   public Long setOrgTypeId(String orgTypeName) {
      LambdaQueryWrapper<CatalogueOrgTypeDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(CatalogueOrgTypeDO::getTypeName, orgTypeName).last("limit 1");
      CatalogueOrgTypeDO catalogueOrgTypeDO = (CatalogueOrgTypeDO)this.catalogueOrgTypeService.getOne(queryWrapper);
      if (ObjectUtil.isEmpty(catalogueOrgTypeDO)) {
         CatalogueOrgTypeDO catalogueOrgType = new CatalogueOrgTypeDO();
         catalogueOrgType.setTypeName(orgTypeName);
         this.catalogueOrgTypeService.save(catalogueOrgType);
         return catalogueOrgType.getId();
      } else {
         return catalogueOrgTypeDO.getId();
      }
   }

   private List<OrgNode> compositeChild(List<OrgNode> allOrgS, List<OrgNode> queryOrgS, Integer contentType, int intermediate, Map<Long, Integer> tableCountMap) {
        Map<Long, List<SysContentDTO>> sysContentDTOListMap = getSysContentListMap(allOrgS, contentType, intermediate);
        Map<Long, String> orgTypeNameMap = getOrgTypeName(allOrgS);
        for (OrgNode orgNode : queryOrgS) {
             orgNode.setChildOrgList(getChild(allOrgS, orgNode.getId(), contentType, intermediate, tableCountMap, sysContentDTOListMap));
             orgNode.setCount(orgNode.getChildOrgList().size());
             if (!orgTypeNameMap.isEmpty() && ObjectUtil.isNotEmpty(orgNode.getOrgTypeId()))
                  orgNode.setOrgTypeName(orgTypeNameMap.get(orgNode.getOrgTypeId()));
             if (contentType != null && !sysContentDTOListMap.isEmpty()) {
                  List<SysContentDTO> sysContentDTOList = sysContentDTOListMap.get(orgNode.getId());
                  if (sysContentDTOList != null && !sysContentDTOList.isEmpty()) {
                       for (SysContentDTO sysContentDTO : sysContentDTOList) {
                            Integer tableCount = tableCountMap.get(sysContentDTO.getContentId());
                            sysContentDTO.setCount(Integer.valueOf((tableCount == null) ? 0 : tableCount.intValue()));
                            sysContentDTO.setNodeNameSimplify(orgNode.getNodeNameSimplify());
                          }
                       orgNode.setSysContent(sysContentDTOList);
                       orgNode.setCount(sysContentDTOList.size());
                     }
                }
           }
        return queryOrgS;
      }

   private Map<Long, String> getOrgTypeName(List<OrgNode> allOrgS) {
      Map<Long, String> orgTypeNameMap = new HashMap();
      Set<Long> orgTypeIds = (Set)allOrgS.stream().map(OrgNode::getOrgTypeId).collect(Collectors.toSet());
      if (orgTypeIds.size() > 0) {
         LambdaQueryWrapper<CatalogueOrgTypeDO> queryWrapper = new LambdaQueryWrapper();
         queryWrapper.in(BaseEntity::getId, orgTypeIds);
         List<CatalogueOrgTypeDO> orgTypeList = this.catalogueOrgTypeService.list(queryWrapper);
         if (orgTypeList.size() > 0) {
            orgTypeNameMap = (Map)orgTypeList.stream().collect(Collectors.toMap(BaseEntity::getId, CatalogueOrgTypeDO::getTypeName));
         }
      }

      return (Map)orgTypeNameMap;
   }

   private List<OrgNode> getChild(List<OrgNode> allOrgS, Long id, Integer contentType, int intermediate, Map<Long, Integer> tableCountMap, Map<Long, List<SysContentDTO>> sysContentDTOListMap) {
        List<OrgNode> tempChild = new ArrayList<>();
        for (OrgNode catalogueOrgDO : allOrgS) {
             Long parentId = catalogueOrgDO.getParentId();
             if (parentId.equals(id))
                  tempChild.add(catalogueOrgDO);
           }
        if (tempChild.size() == 0)
             return Collections.emptyList();
        if (sysContentDTOListMap.isEmpty())
             sysContentDTOListMap = getSysContentListMap(allOrgS, contentType, intermediate);
        Map<Long, String> childOrgTypeNameMap = getOrgTypeName(tempChild);
        for (OrgNode orgNode : tempChild) {
             orgNode.setChildOrgList(getChild(allOrgS, orgNode.getId(), contentType, intermediate, tableCountMap, sysContentDTOListMap));
             orgNode.setCount(orgNode.getChildOrgList().size());
             if (!childOrgTypeNameMap.isEmpty() && ObjectUtil.isNotEmpty(orgNode.getOrgTypeId()))
                  orgNode.setOrgTypeName(childOrgTypeNameMap.get(orgNode.getOrgTypeId()));
             if (contentType != null && !sysContentDTOListMap.isEmpty()) {
                  List<SysContentDTO> sysContentDTOList = sysContentDTOListMap.get(orgNode.getOaId());
                  if (sysContentDTOList != null && !sysContentDTOList.isEmpty()) {
                       for (SysContentDTO sysContentDTO : sysContentDTOList) {
                            Integer tableCount = tableCountMap.get(sysContentDTO.getContentId());
                            sysContentDTO.setCount(Integer.valueOf((tableCount == null) ? 0 : tableCount.intValue()));
                          }
                       orgNode.setSysContent(sysContentDTOList);
                       orgNode.setCount(sysContentDTOList.size());
                     }
                }
           }
        return tempChild;
      }
   private Map<Long, List<SysContentDTO>> getSysContentListMap(List<OrgNode> allOrgS, Integer contentType, int intermediate) {
        Map<Long, List<SysContentDTO>> sysContentDTOListMap = new HashMap<>();
        Long[] orgIds = (Long[])allOrgS.stream().map(BaseResponse::getId).toArray(x$0 -> new Long[x$0]);
        List<SysContentDTO> sysContentDTOList = this.contentRelationMapper.findSysContentByOrgId(orgIds, contentType, null, intermediate, null, null, null);
        if (sysContentDTOList.size() > 0)
             sysContentDTOListMap = sysContentDTOList.stream().collect(Collectors.groupingBy(SysContentDTO::getOrgId));
        return sysContentDTOListMap;
   }

   public Map<String, Integer> getDataSizeByOrgIds(List<Long> orgIds) {
      if (CollectionUtil.isEmpty(orgIds)) {
         return null;
      } else {
         String orgIdStr = StringUtils.join(orgIds, ",", 0, orgIds.size());
         String url = this.spa_gateway_uri + this.sol_or_api + "/admin-api/v1/orgDsService/getDataSizeByOrgIds/" + orgIdStr;
         JSONObject result = JSON.parseObject(this.httpUtils.sendGet(url));
         return null == result.get("payload") ? null : (Map)JSONObject.parseObject(result.get("payload").toString(), Map.class);
      }
   }

   public ReportCatalogueCategoryRequest convertToReportCatalogueCategoryRequest(CatalogueOrgDO catalogueOrgDO) {
      ReportCatalogueCategoryRequest request = new ReportCatalogueCategoryRequest();
      request.setGroup_id(catalogueOrgDO.getExternalCode());
      request.setGroup_name(catalogueOrgDO.getNodeName());
      request.setCredit_code(catalogueOrgDO.getNodeNo());
      request.setRemark(catalogueOrgDO.getRemark());
      request.setRelease_time(LocalDateTimeUtil.now().format(DatePattern.NORM_DATETIME_FORMATTER));
      request.setOrder_num(catalogueOrgDO.getSort());
      return request;
   }

}

package com.grandlynn.spa.sol.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.grandlynn.spa.commons.data.PageBean;
import com.grandlynn.spa.commons.exception.ApplicationException;
import com.grandlynn.spa.commons.exception.EntityNotFoundException;
import org.springframework.util.CollectionUtils;
import com.grandlynn.spa.commons.utils.StringUtils;
import com.grandlynn.spa.sol.constants.CommonConstants;
import com.grandlynn.spa.sol.constants.OrgDsApplyConstants;
import com.grandlynn.spa.sol.dao.ApproveNodeDAO;
import com.grandlynn.spa.sol.dao.InterfaceDAO;
import com.grandlynn.spa.sol.dao.OrgDsApplyListDAO;
import com.grandlynn.spa.sol.dao.OrgDsAuditDAO;
import com.grandlynn.spa.sol.dao.OrgDsServiceDAO;
import com.grandlynn.spa.sol.domain.dto.ApplyDTO;
import com.grandlynn.spa.sol.domain.dto.InterfaceBaseDTO;
import com.grandlynn.spa.sol.domain.dto.InterfaceDTO;
import com.grandlynn.spa.sol.domain.dto.InterfaceDetailsDTO;
import com.grandlynn.spa.sol.domain.dto.InterfaceUpdateLogDetailDTO;
import com.grandlynn.spa.sol.domain.dto.OntologyFieldMapDTO;
import com.grandlynn.spa.sol.domain.dto.OntologyModelNodeHistoryDTO;
import com.grandlynn.spa.sol.domain.dto.UserInfoDTO;
import com.grandlynn.spa.sol.domain.entity.ApproveConfig;
import com.grandlynn.spa.sol.domain.entity.ApproveNode;
import com.grandlynn.spa.sol.domain.entity.DataSharingService;
import com.grandlynn.spa.sol.domain.entity.Interface;
import com.grandlynn.spa.sol.domain.entity.InterfaceParamIndex;
import com.grandlynn.spa.sol.domain.entity.OrgDsApplyList;
import com.grandlynn.spa.sol.domain.entity.OrgDsService;
import com.grandlynn.spa.sol.domain.query.ApiModelDataQuery;
import com.grandlynn.spa.sol.domain.query.DataSharingServicePageQuery;
import com.grandlynn.spa.sol.domain.query.InterfaceModelRequestQuery;
import com.grandlynn.spa.sol.domain.query.InterfacesQuery;
import com.grandlynn.spa.sol.domain.query.OrgDsApplyListQuery;
import com.grandlynn.spa.sol.domain.request.CatalogueGetTableRequest;
import com.grandlynn.spa.sol.domain.vo.ApplyInterfaceVO;
import com.grandlynn.spa.sol.domain.vo.ApplyResourceDeptRankVO;
import com.grandlynn.spa.sol.domain.vo.DataSharingServiceVO;
import com.grandlynn.spa.sol.domain.vo.DataTotalSizeRankVO;
import com.grandlynn.spa.sol.domain.vo.DeptApplyResourceVO;
import com.grandlynn.spa.sol.domain.vo.HotApplyCatelogueRankVO;
import com.grandlynn.spa.sol.domain.vo.IndexInterfaceVO;
import com.grandlynn.spa.sol.domain.vo.InterfaceApiListVO;
import com.grandlynn.spa.sol.domain.vo.InterfaceVO;
import com.grandlynn.spa.sol.domain.vo.OntologyModelApiVO;
import com.grandlynn.spa.sol.domain.vo.OntologyModelParamVO;
import com.grandlynn.spa.sol.domain.vo.ResourceDirShareOpenTypeRateVO;
import com.grandlynn.spa.sol.domain.vo.ResourceShareVO;
import com.grandlynn.spa.sol.domain.vo.TodoWorkVO;
import com.grandlynn.spa.sol.enums.ApproveStatusEnum;
import com.grandlynn.spa.sol.enums.InterfaceEnum;
import com.grandlynn.spa.sol.enums.InterfaceErrorEnum;
import com.grandlynn.spa.sol.enums.InterfaceQueryEnum;
import com.grandlynn.spa.sol.enums.OrgDsApplyTypeEnum;
import com.grandlynn.spa.sol.enums.ServiceTypeEnum;
import com.grandlynn.spa.sol.mapper.ApproveConfigMapper;
import com.grandlynn.spa.sol.service.ApplyService;
import com.grandlynn.spa.sol.service.DataQueryService;
import com.grandlynn.spa.sol.service.DataSharingServiceService;
import com.grandlynn.spa.sol.service.InterfaceService;
import com.grandlynn.spa.sol.service.InterfaceUpdateDetailLogService;
import com.grandlynn.spa.sol.service.OntologyFieldMapService;
import com.grandlynn.spa.sol.service.OntologyModelNodeHistoryService;
import com.grandlynn.spa.sol.service.OntologyModelService;
import com.grandlynn.spa.sol.service.OrgDsApplyListService;
import com.grandlynn.spa.sol.util.ConverterUtils;
import com.grandlynn.spa.sol.util.HttpUtil;
import com.grandlynn.spa.sol.util.HttpUtils;
import com.grandlynn.spa.sol.util.JsonUtil;
import com.grandlynn.spa.security.utils.SecurityUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterfaceServiceImpl implements InterfaceService {
   private static final Logger log = LoggerFactory.getLogger(InterfaceServiceImpl.class);
   @Value("${SPA_GATEWAY_URI}")
   private String spa_gateway_uri;
   @Autowired
   private HttpUtils httpUtils;
   @Autowired
   private InterfaceDAO interfaceDAO;
   @Autowired
   private ApplyService applyService;
   @Autowired
   private OrgDsAuditDAO orgDsAuditDAO;
   @Autowired
   private OrgDsServiceDAO orgDsServiceDAO;
   private static final String CUSTOMIZE_PREFIX = "/open-api/v1/interfaces/customize";
   private static final String NORMAL_PREFIX = "/open-api/v1/interfaces/normal";
   @Value("${open-api.protocol}")
   private String protocol;
   @Value("${open-api.host}")
   private String host;
   @Value("${open-api.appName}")
   private String appName;
   @Autowired
   private OntologyModelService ontologyModelService;
   @Autowired
   private RedisTemplate redisTemplate;
   @Autowired
   private InterfaceUpdateDetailLogService updateLogService;
   @Autowired
   private OntologyFieldMapService ontologyFieldMapService;
   @Autowired
   private DataQueryService dataQueryService;
   @Autowired
   private OntologyModelNodeHistoryService ontologyModelNodeHistoryService;
   @Autowired
   private OrgDsApplyListDAO orgDsApplyListDAO;
   @Autowired
   private OrgDsApplyListService orgDsApplyListService;
   @Autowired
   private DataSharingServiceService dataSharingServiceService;
   @Autowired
   private ApproveNodeDAO approveNodeDAO;
   @Autowired
   private ApproveConfigMapper approveConfigMapper;
   private static final String INTERFACE_KEY = "INTERFACE_KEY:";
   private static final String REQUEST_METHOD = "GET";
   private static final int MAX_SIZE = 200;
   private static final int EXPIRE_TIME = 300;

   public TodoWorkVO countTodoWorkNumberByDeptId(Integer handleUserId, List<Integer> roleIds, Map<String, String> header) {
      TodoWorkVO todoWorkVO = new TodoWorkVO();
      todoWorkVO.setDirectoryPowerFlag(false);
      todoWorkVO.setApplyAndDemandPowerFlag(false);
      List<ApproveConfig> approveConfigs = this.approveConfigMapper.selectList(new QueryWrapper());
      HashMap<Long, String> approveConfigMap = new HashMap();
      if (approveConfigs != null && approveConfigs.size() > 0) {
         for (ApproveConfig approveConfig : approveConfigs) {
            approveConfigMap.put(approveConfig.getApproveConfigId(), approveConfig.getName());
          }
      }

      List<ApproveNode> approveNodeS = this.approveNodeDAO.listByUserIdOrRoleId(handleUserId, roleIds);
      if (approveNodeS != null && approveNodeS.size() > 0) {
          for (ApproveNode approveNode : approveNodeS) {
               Long userId = approveNode.getUserId();
               Long approveNodeRoleId = approveNode.getRoleId();
               Long approveConfigId = approveNode.getApproveConfigId();
               String approveConfigName = approveConfigMap.get(approveConfigId);
               String type = approveNode.getType();
               if (type.equals("1")) {
                    if (userId != null && handleUserId != null && userId.equals(Long.valueOf(handleUserId.intValue()))) {
                         if (approveConfigName != null && approveConfigName.equals("资源目录服务审批")) {
                              todoWorkVO.setDirectoryPowerFlag(Boolean.valueOf(true)); continue;
                            }  if (approveConfigName != null && approveConfigName.equals("需求清单审批"))
                              todoWorkVO.setApplyAndDemandPowerFlag(Boolean.valueOf(true));
                       }  continue;
                  }
               if (type.equals("2") &&
                         approveNodeRoleId != null && roleIds != null && roleIds.size() > 0 && roleIds.contains(Integer.valueOf(Math.toIntExact(approveNodeRoleId.longValue())))) {
                    if (approveConfigName != null && approveConfigName.equals("资源目录服务审批")) {
                         todoWorkVO.setDirectoryPowerFlag(Boolean.valueOf(true)); continue;
                       }  if (approveConfigName != null && approveConfigName.equals("需求清单审批")) {
                         todoWorkVO.setApplyAndDemandPowerFlag(Boolean.valueOf(true));
                       }
                  }
             }
              }

      OrgDsApplyListQuery query = new OrgDsApplyListQuery();
      if (query.getUserId() == null || query.getUserId().equals("")) {
         query.setUserId(String.valueOf(handleUserId));
      }

      query.setApplyType(OrgDsApplyTypeEnum.APPLY.getCode());
      query.setPage(1);
      query.setSize(Integer.MAX_VALUE);
      query.setStatus(1);
      IPage<OrgDsApplyList> applyIPage = this.orgDsApplyListDAO.getPage(query);
      query.setApplyType(OrgDsApplyTypeEnum.DEMAND.getCode());
      IPage<OrgDsApplyList> demandIPage = this.orgDsApplyListDAO.getPage(query);
      List<OrgDsApplyList> applyList = applyIPage.getRecords();
      List<OrgDsApplyList> demandList = demandIPage.getRecords();
      todoWorkVO.setTodoDsApplyListNumber(applyList.size());
      todoWorkVO.setTodoDemandListNumber(demandList.size());
      DataSharingServicePageQuery dataSharingServicePageQuery = new DataSharingServicePageQuery();
      dataSharingServicePageQuery.setPage(1);
      dataSharingServicePageQuery.setSize(Integer.MAX_VALUE);
      dataSharingServicePageQuery.setStatus(0);
      dataSharingServicePageQuery.setUserId(String.valueOf(handleUserId));
      IPage<DataSharingService> shareIPage = this.dataSharingServiceService.getList(dataSharingServicePageQuery);
      List<DataSharingService> shareList = shareIPage.getRecords();
      todoWorkVO.setShareWorkNumber(shareList.size());
      todoWorkVO.setShareWorkPowerFlag(false);
      String url = this.spa_gateway_uri + "data-catalogue-api" + "/sysAdminUser/findMenuAuthority?menuId=" + CommonConstants.CATALOGUE_SHARE_MENU_ID;

      try {
         HttpUtil.initHttpClient(true, 5);
         HashMap<String, String> headers = new HashMap();
         headers.put("auth-token", header.get("auth-token"));
         String resultStr = HttpUtil.get(url, headers);
         JSONObject result = JSONObject.parseObject(resultStr);
         if (result == null || null == result.get("payload")) {
            log.info("配置角色人员url:{}", url);
         }

         List<UserInfoDTO> userInfoDTOS = JSONArray.parseArray(result.get("payload").toString(), UserInfoDTO.class);
         List<Long> collectHaveMenuIds = (List)userInfoDTOS.stream().map((e) -> {
            return e.getId();
         }).collect(Collectors.toList());
         if (collectHaveMenuIds.contains((long)handleUserId)) {
            todoWorkVO.setShareWorkPowerFlag(true);
         }
      } catch (Exception var22) {
         log.info("调用异常url：{},错误：{}", url, var22);
      }

      return todoWorkVO;
   }

   public DeptApplyResourceVO countCurrentDeptApplyResource(Integer deptId) {
      QueryWrapper<OrgDsApplyList> orgDsApplyListQueryWrapper = new QueryWrapper();
      orgDsApplyListQueryWrapper.eq("user_dept_id", deptId);
      orgDsApplyListQueryWrapper.eq("apply_type", OrgDsApplyTypeEnum.APPLY.getCode());
      List<OrgDsApplyList> orgDsApplyLists = this.orgDsApplyListDAO.list(orgDsApplyListQueryWrapper);
      if (orgDsApplyLists != null && orgDsApplyLists.size() > 0) {
         List<OrgDsApplyList> applyProcessResourceList = (List)orgDsApplyLists.stream().filter((e) -> {
            return e.getStatus().equals(1);
         }).collect(Collectors.toList());
         List<OrgDsApplyList> pendingResourceList = (List)orgDsApplyLists.stream().filter((e) -> {
            return e.getStatus().equals(14);
         }).collect(Collectors.toList());
         List<OrgDsApplyList> completeResourceList = (List)orgDsApplyLists.stream().filter((e) -> {
            return e.getStatus().equals(2);
         }).collect(Collectors.toList());
         List<OrgDsApplyList> regressResourceList = (List)orgDsApplyLists.stream().filter((e) -> {
            return e.getStatus().equals(3);
         }).collect(Collectors.toList());
         List<OrgDsApplyList> cancelResourceList = (List)orgDsApplyLists.stream().filter((e) -> {
            return e.getStatus().equals(4);
         }).collect(Collectors.toList());
         DeptApplyResourceVO deptApplyResourceVO = new DeptApplyResourceVO();
         deptApplyResourceVO.setSubmitResourceNumber(orgDsApplyLists.size());
         deptApplyResourceVO.setApprovalProcessResourceNumber(applyProcessResourceList.size() + pendingResourceList.size());
         deptApplyResourceVO.setCompletedResourceNumber(completeResourceList.size());
         deptApplyResourceVO.setRegressResourceNumber(regressResourceList.size());
         deptApplyResourceVO.setCancelResourceNumber(cancelResourceList.size());
         return deptApplyResourceVO;
      } else {
         DeptApplyResourceVO deptApplyResourceVO = new DeptApplyResourceVO();
         deptApplyResourceVO.setSubmitResourceNumber(0);
         deptApplyResourceVO.setApprovalProcessResourceNumber(0);
         deptApplyResourceVO.setCompletedResourceNumber(0);
         deptApplyResourceVO.setRegressResourceNumber(0);
         return deptApplyResourceVO;
      }
   }

   public List<ApplyResourceDeptRankVO> rankResourceApplyDept(Integer deptId) {
      List<ApplyResourceDeptRankVO> applyResourceDeptRankVOS = this.orgDsApplyListService.rankResourceApplyDept(deptId);
      return applyResourceDeptRankVOS;
   }

   public List<HotApplyCatelogueRankVO> rankHotApplyCatelogueRankVO(Integer deptId, Integer day) {
      List<HotApplyCatelogueRankVO> hotApplyCatelogueRankVOS = this.orgDsServiceDAO.rankHotApplyCatelogueRankVO(deptId, day);
      return hotApplyCatelogueRankVOS;
   }

   public List<DataTotalSizeRankVO> rankDataTotalSize(Integer deptId) {
      List<DataTotalSizeRankVO> dataTotalSizeRankVOS = this.orgDsServiceDAO.rankDataTotalSize(deptId);
      return dataTotalSizeRankVOS;
   }

   public List<ResourceDirShareOpenTypeRateVO> calResourceDirShareOpenTypeRate(Integer deptId) {
      List<ResourceDirShareOpenTypeRateVO> resourceDirShareOpenTypeRateVOS = this.orgDsServiceDAO.calResourceDirShareOpenTypeRate(deptId);
      if (resourceDirShareOpenTypeRateVOS != null && resourceDirShareOpenTypeRateVOS.size() > 0) {
         ResourceDirShareOpenTypeRateVO resourceDirShareOpenTypeRateVO = resourceDirShareOpenTypeRateVOS.get(0);
         Integer shareTotal = resourceDirShareOpenTypeRateVO.getUnconditionalShare() + resourceDirShareOpenTypeRateVO.getConditionalShare() + resourceDirShareOpenTypeRateVO.getNotAllowShare();
         if (!shareTotal.equals(0)) {
            resourceDirShareOpenTypeRateVO.setUnconditionalShareRate(Double.valueOf((double)resourceDirShareOpenTypeRateVO.getUnconditionalShare()) / Double.valueOf((double)shareTotal));
            resourceDirShareOpenTypeRateVO.setConditionalShareRate(Double.valueOf((double)resourceDirShareOpenTypeRateVO.getConditionalShare()) / Double.valueOf((double)shareTotal));
            resourceDirShareOpenTypeRateVO.setNotAllowShareRate(Double.valueOf((double)resourceDirShareOpenTypeRateVO.getNotAllowShare()) / Double.valueOf((double)shareTotal));
         }

         Double openTotal = (double)(resourceDirShareOpenTypeRateVO.getUnconditionalOpen() + resourceDirShareOpenTypeRateVO.getConditionalOpen() + resourceDirShareOpenTypeRateVO.getNotAllowOpen());
         if (!openTotal.equals(0.0)) {
            resourceDirShareOpenTypeRateVO.setUnconditionalOpenRate((double)resourceDirShareOpenTypeRateVO.getUnconditionalOpen() / openTotal);
            resourceDirShareOpenTypeRateVO.setConditionalOpenRate(Double.valueOf((double)resourceDirShareOpenTypeRateVO.getConditionalOpen()) / openTotal);
            resourceDirShareOpenTypeRateVO.setNotAllowOpenRate(Double.valueOf((double)resourceDirShareOpenTypeRateVO.getNotAllowOpen()) / openTotal);
         }
      }

      return resourceDirShareOpenTypeRateVOS;
   }

   public ResourceShareVO getResourceShare(Integer deptId) {
      List<DataSharingServiceVO> dataSharingResourceList = this.dataSharingServiceService.getDataSharingResourceList(deptId, 1);
      Integer interfaceShareNumber = 0;
      Integer dBTableShareNumber = 0;
      Integer fileShareNumber = 0;
      Set<String> resourceNameSet = new HashSet();
      if (dataSharingResourceList != null && dataSharingResourceList.size() > 0) {
          for (DataSharingServiceVO dataSharingServiceVO : dataSharingResourceList) {
               resourceNameSet.add(dataSharingServiceVO.getResourceCode());
               if (dataSharingServiceVO.getShareMode().equals(Integer.valueOf(0))) {
                    dBTableShareNumber = Integer.valueOf(dBTableShareNumber.intValue() + dataSharingServiceVO.getShareNum().intValue());
                  }
               if (dataSharingServiceVO.getShareMode().equals(Integer.valueOf(1))) {
                    interfaceShareNumber = Integer.valueOf(interfaceShareNumber.intValue() + dataSharingServiceVO.getShareNum().intValue());
                  }
               if (dataSharingServiceVO.getShareMode().equals(Integer.valueOf(2))) {
                    fileShareNumber = Integer.valueOf(fileShareNumber.intValue() + dataSharingServiceVO.getShareNum().intValue());
                  }
             }
        }

      QueryWrapper<OrgDsService> orgDsServiceQueryWrapper = new QueryWrapper();
      orgDsServiceQueryWrapper.eq("dept_id", deptId);
      Long totalResourceCount = this.orgDsServiceDAO.count(orgDsServiceQueryWrapper);
      ResourceShareVO resourceShareVO = new ResourceShareVO();
      resourceShareVO.setTotalShareResourceCatalogueNumber(totalResourceCount);
      resourceShareVO.setShareResourceCatalogueNumber(resourceNameSet.size());
      resourceShareVO.setInterfaceShareNumber(interfaceShareNumber);
      resourceShareVO.setDBTableShareNumber(dBTableShareNumber);
      resourceShareVO.setFileShareNumber(fileShareNumber);
      Double originRate = Double.valueOf((double)resourceShareVO.getShareResourceCatalogueNumber()) / Double.valueOf((double)resourceShareVO.getTotalShareResourceCatalogueNumber());
      String formatRate = String.format("%.4f", originRate);
      resourceShareVO.setShareResourceCatalogueRate(Double.parseDouble(formatRate));
      return resourceShareVO;
   }

   public TodoWorkVO countTodoWorkNumberByDeptId() {
      TodoWorkVO vo = new TodoWorkVO();
      Integer firstReviewNumber = this.getUsersByOrgRoleId(CommonConstants.CATALOGUE_APPROVE_FIRST_REVIEW_MENU_ID);
      Integer reviewNumber = this.getUsersByOrgRoleId(CommonConstants.CATALOGUE_APPROVE_REVIEW_PUBLISH_MENU_ID);
      int catalogueAuditNumber = 0;
      Boolean catalogueAuditNumberFlag = false;
      if (null != firstReviewNumber) {
         catalogueAuditNumber = firstReviewNumber;
         catalogueAuditNumberFlag = true;
      }

      if (null != reviewNumber) {
         catalogueAuditNumber += reviewNumber;
         catalogueAuditNumberFlag = true;
      }

      vo.setCatalogueAuditPowerFlag(catalogueAuditNumberFlag);
      if (catalogueAuditNumberFlag) {
         vo.setCatalogueAuditNumber(catalogueAuditNumber);
      }

      return vo;
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public boolean insert(Integer interfaceType, InterfaceDetailsDTO interfaceDetailsDTO) throws IOException {
      List<InterfaceParamIndex> requestParams = interfaceDetailsDTO.getRequestParams();
      List<OntologyModelParamVO> responseParams = interfaceDetailsDTO.getResponseParams();
      interfaceDetailsDTO.setInterfaceType(interfaceType);
      interfaceDetailsDTO.setRequest(JsonUtil.toJSON(requestParams));
      interfaceDetailsDTO.setResponse(JsonUtil.toJSON(responseParams));
      String url = "/" + this.appName + (InterfaceEnum.CUSTOMIZE.getCode() == interfaceDetailsDTO.getInterfaceType() ? "/open-api/v1/interfaces/customize" : "/open-api/v1/interfaces/normal");
      interfaceDetailsDTO.setUrl(url);
      Interface anInterface = (Interface)InterfaceDetailsDTO.DEFAULT_DETAILS_CONVERTER.toDest(interfaceDetailsDTO);
      boolean save = this.interfaceDAO.save(anInterface);
      interfaceDetailsDTO.setInterfaceId(anInterface.getInterfaceId());
      if (interfaceDetailsDTO.getInterfaceType().equals(InterfaceEnum.CUSTOMIZE.getCode())) {
         this.saveApply(interfaceDetailsDTO);
      }

      this.redisTemplate.opsForValue().set("INTERFACE_KEY:" + interfaceDetailsDTO.getInterfaceId(), anInterface);
      this.createUpdateLog(false, anInterface.getInterfaceId(), interfaceDetailsDTO, (InterfaceDTO)null);
      return save;
   }

   private boolean saveApply(InterfaceDetailsDTO interfaceDetailsDTO) {
      ApplyDTO applyDTO = new ApplyDTO();
      applyDTO.setInterfaceId(interfaceDetailsDTO.getInterfaceId());
      applyDTO.setEffectiveTime(interfaceDetailsDTO.getEffectiveTime());
      applyDTO.setPurpose(interfaceDetailsDTO.getPurpose());
      applyDTO.setUserId(interfaceDetailsDTO.getUserId());
      applyDTO.setUserName(interfaceDetailsDTO.getUserName());
      applyDTO.setPurpose(interfaceDetailsDTO.getPurpose());
      applyDTO.setEffectiveTime(interfaceDetailsDTO.getEffectiveTime());
      applyDTO.setInterfaceName(interfaceDetailsDTO.getName());
      applyDTO.setApproveStatus(ApproveStatusEnum.PREPARATION.getCode());
      applyDTO.setDeptId(interfaceDetailsDTO.getDeptId());
      applyDTO.setDeptName(interfaceDetailsDTO.getDeptName());
      applyDTO.setInterfaceType(interfaceDetailsDTO.getInterfaceType());
      return this.applyService.doApply(applyDTO);
   }

   public int updateBaseInfo(InterfaceBaseDTO interfaceBaseDTO) {
      InterfaceDTO baseInfo = this.getById(interfaceBaseDTO.getInterfaceId());
      interfaceBaseDTO.setResourceVersion(baseInfo.getResourceVersion());
      return this.interfaceDAO.updateBaseInfo((Interface)InterfaceDTO.DEFAULT_BASE_CONVERTER.toDest(interfaceBaseDTO));
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public int updateDetails(InterfaceDetailsDTO interfaceDetailsDTO) throws IOException {
      InterfaceDTO beforeData = this.getById(interfaceDetailsDTO.getInterfaceId());
      if (beforeData.getInterfaceType() == InterfaceEnum.CUSTOMIZE.getCode()) {
         throw new ApplicationException("自定义接口不支持编辑操作！");
      } else {
         List<InterfaceParamIndex> requestParams = interfaceDetailsDTO.getRequestParams();
         List<OntologyModelParamVO> responseParams = interfaceDetailsDTO.getResponseParams();
         interfaceDetailsDTO.setRequest(JsonUtil.toJSON(requestParams));
         interfaceDetailsDTO.setResponse(JsonUtil.toJSON(responseParams));
         Interface anInterface = (Interface)InterfaceDetailsDTO.DEFAULT_DETAILS_CONVERTER.toDest(interfaceDetailsDTO);
         int result = this.interfaceDAO.updateDetails((Interface)InterfaceDetailsDTO.DEFAULT_DETAILS_CONVERTER.toDest(interfaceDetailsDTO));
         this.redisTemplate.opsForValue().set("INTERFACE_KEY:" + interfaceDetailsDTO.getInterfaceId(), anInterface);
         this.createUpdateLog(true, interfaceDetailsDTO.getInterfaceId(), interfaceDetailsDTO, beforeData);
         return result;
      }
   }

   public boolean removeById(Long interfaceId) {
      InterfaceDTO interfaceDTO = this.getById(interfaceId);
      if (interfaceDTO.getStatus()) {
         throw new ApplicationException("启用状态不能删除");
      } else {
         Interface interfaces = new Interface();
         interfaces.setInterfaceId(interfaceId);
         interfaces.setIsDelete(true);
         return this.interfaceDAO.updateById(interfaces);
      }
   }

   public InterfaceDTO getById(Long interfaceId) {
      return (InterfaceDTO)InterfaceDTO.DEFAULT_CONVERTER.fromDest(this.interfaceDAO.getById(interfaceId).orElseThrow(() -> {
         return new EntityNotFoundException(interfaceId);
      }));
   }

   public PageBean<InterfaceVO> queryList(InterfacesQuery interfacesQuery) {
      IPage page;
      if (interfacesQuery.getInterfaceType() == InterfaceEnum.CUSTOMIZE.getCode()) {
         page = this.interfaceDAO.queryCustomizationList(interfacesQuery);
         return this.IPage2PageBean(page);
      } else {
         page = this.interfaceDAO.queryNormalList(interfacesQuery);
         return this.IPage2PageBean(page);
      }
   }

   public InterfaceDetailsDTO queryDetails(InterfacesQuery interfacesQuery, boolean isHierarchy) throws IOException {
      InterfaceDetailsDTO detailsDTO = this.interfaceDAO.queryDetails(interfacesQuery);
      if (detailsDTO == null) {
         throw new ApplicationException("未查询到该接口详情信息");
      } else {
         List<InterfaceParamIndex> paramIndexList = (List)JsonUtil.decode(detailsDTO.getRequest(), new TypeReference<List<InterfaceParamIndex>>() {
         });
         detailsDTO.setRequestParams(paramIndexList);
         List<OntologyModelParamVO> responseList = (List)JsonUtil.decode(detailsDTO.getResponse(), new TypeReference<List<OntologyModelParamVO>>() {
         });
         if (isHierarchy && detailsDTO.getInterfaceType().equals(InterfaceEnum.NORMAL.getCode())) {
            detailsDTO.setResponseParams(this.buildHierarchy(detailsDTO, responseList));
         } else {
            detailsDTO.setResponseParams(responseList);
         }

         HashMap<String, Object> map = new HashMap();
         if (!CollectionUtils.isEmpty(responseList)) {
            Integer type = detailsDTO.getType();
            if (type.equals(1)) {
               this.createData(map, (OntologyModelParamVO)responseList.get(0));
               log.info("data:{}", JsonUtil.toJSON(map));
            } else {
               this.createDetail(map, (OntologyModelParamVO)responseList.get(0));
               log.info("data:{}", JsonUtil.toJSON(map));
            }

            map.put("code", "OK");
            map.put("msg", "OK");
            detailsDTO.setReturnExample(JsonUtil.toJSON(map));
         }

         detailsDTO.setProtocol(this.protocol);
         detailsDTO.setRequestMode("GET");
         StringBuilder sb = null;
        for (InterfaceParamIndex paramIndex : paramIndexList) {
      
             if (paramIndex.getRequired() != null && paramIndex.getRequired().intValue() == 1) {
                  if (sb == null) {
                       sb = new StringBuilder();
                       sb.append("?").append(paramIndex.getEnName()).append("=XXXX&"); continue;
                     }
                  sb.append(paramIndex.getEnName()).append("=XXXX&");
                }
           }

         if (sb != null) {
            int end = sb.toString().lastIndexOf("&");
            String substring = StringUtils.substring(sb.toString(), 0, end);
            detailsDTO.setRequestExample(detailsDTO.getUrl() + substring + "&appId=XXXX");
         } else {
            detailsDTO.setRequestExample(detailsDTO.getUrl() + "?appId=XXXX");
         }

         detailsDTO.setResponse((String)null);
         detailsDTO.setRequest((String)null);
         detailsDTO.setErrorCodes(InterfaceErrorEnum.toList());
         return detailsDTO;
      }
   }

   private List<OntologyModelParamVO> buildHierarchy(InterfaceDetailsDTO detailsDTO, List<OntologyModelParamVO> responseList) {
      List<OntologyModelParamVO> result = null;
      InterfaceModelRequestQuery ontologyQuery = new InterfaceModelRequestQuery();
      ontologyQuery.setOntologyModelId(detailsDTO.getOntologyModelId());
      ontologyQuery.setType(detailsDTO.getType());
      OntologyModelApiVO ontologyModel = this.getOntologyModel(ontologyQuery);
      List<OntologyModelParamVO> responseParams = ontologyModel.getResponseParams();
      List<OntologyModelParamVO> beforeData = new ArrayList();
      List<OntologyModelParamVO> beforeParent = new ArrayList();
      this.splitNodes(responseList, beforeData, beforeParent);
      beforeData.addAll(beforeParent);
      List<OntologyModelParamVO> allData = new ArrayList();
      List<OntologyModelParamVO> parent = new ArrayList();
      this.splitNodes(responseParams, allData, parent);
      allData.addAll(parent);
     for (OntologyModelParamVO all : allData) {
          for (OntologyModelParamVO before : beforeData) {
               if (all.getOntologyModelNodeId().equals(before.getOntologyModelNodeId())) {
                    all.setChecked(Boolean.valueOf(true));
                  }
             }
        }

      result = this.formatTreeData(allData, detailsDTO.getOntologyModelId());
      return result;
   }

   public PageBean<InterfaceApiListVO> openApiQueryByPage(InterfacesQuery interfacesQuery) {
      IPage<InterfaceApiListVO> page = this.interfaceDAO.openApiQueryByPage(interfacesQuery);
      PageBean<InterfaceApiListVO> result = new PageBean();
      result.setTotalElements(page.getTotal());
      result.setNumber((int)page.getCurrent());
      result.setSize((int)page.getSize());
      result.setTotalPages((int)page.getPages());
      result.setNumberOfElements(page.getRecords().size());
      result.setContent(page.getRecords());
      return result;
   }

   public PageBean<InterfaceVO> personalApplys(InterfacesQuery query) {
      IPage<InterfaceVO> resultPage = this.interfaceDAO.personalApplys(query);
      return this.IPage2PageBean(resultPage);
   }

   public InterfaceVO apiDocument(InterfacesQuery interfacesQuery) {
      return this.interfaceDAO.apiDocument(interfacesQuery);
   }

   public OntologyModelApiVO getOntologyModel(InterfaceModelRequestQuery query) {
      ApiModelDataQuery apiModelDataQuery = new ApiModelDataQuery();
      apiModelDataQuery.setOntologyModelId(query.getOntologyModelId());
      apiModelDataQuery.setType(query.getType());
      OntologyModelApiVO apiModelData = this.ontologyModelService.getApiModelData(apiModelDataQuery);
      if (ServiceTypeEnum.ACCURATE.getCode().equals(query.getServiceType())) {
         InterfaceParamIndex paramIndex = new InterfaceParamIndex();
         paramIndex.setOntologyModelId(apiModelData.getOntologyModelId());
         paramIndex.setDataType(apiModelData.getDataType());
         paramIndex.setEnName(apiModelData.getParamNameEn());
         paramIndex.setName(apiModelData.getParamName());
         ArrayList<InterfaceParamIndex> list = Lists.newArrayList();
         list.add(paramIndex);
         apiModelData.setRequestParams(list);
      }

      return apiModelData;
   }

   public List<Map<String, Object>> getNormalData(String token, String params) throws IOException {
      log.info("request{},{}", token, params);
      HashMap<String, Object> map = new HashMap();
      InterfaceDetailsDTO details = (InterfaceDetailsDTO)this.redisTemplate.opsForValue().get(token);
      if (details == null) {
         details = this.getInterfaceByAppId(token);
         this.redisTemplate.opsForValue().set(token, details, 300L, TimeUnit.SECONDS);
      }

      HashMap<String, Object> reqMap = new HashMap();
      if (details == null) {
         throw new ApplicationException("接口信息不能存在");
      } else if (!details.getApproveStatus().equals(ApproveStatusEnum.PASS.getCode())) {
         throw new ApplicationException("接口使用申请未审核通过");
      } else {
         Map<String, Object> stringObjectMap = JsonUtil.JsonToMap(params);
         String request = details.getRequest();
         List<InterfaceParamIndex> requestParams = (List)JsonUtil.decode(request, new TypeReference<List<InterfaceParamIndex>>() {
         });
         for (InterfaceParamIndex paramIndex : requestParams) {
                   String key = paramIndex.getEnName();
                   if (paramIndex.getRequired() != null && paramIndex.getRequired().intValue() == 1) {
                        String str = paramIndex.getDataType();
                        Object object = stringObjectMap.get(paramIndex.getEnName());
                        if (object == null) {
                             throw new ApplicationException(paramIndex.getEnName() + ":必填参数不能为空");
                           }
                        extracted(reqMap, key, str, object); continue;
                      }
                   String dataType = paramIndex.getDataType();
                   Object o = stringObjectMap.get(paramIndex.getEnName());
                   extracted(reqMap, key, dataType, o);
                 }
         
              log.info("data:{}", reqMap);
         
              Long currentTimeMillis = Long.valueOf(System.currentTimeMillis());
              if (details.getEffectiveTime() != null && currentTimeMillis.compareTo(details.getEffectiveTime()) > 0) {
                   throw new ApplicationException("接口使用期限已过");
                 }
         
              String response = details.getResponse();
              List<OntologyModelParamVO> responseList = (List<OntologyModelParamVO>)JsonUtil.decode(response, new TypeReference<OntologyModelParamVO>() {
              });
         
              List<Long> result = Lists.newArrayList();
              splitNodes2ModelNodeId(responseList, result);
         
              InterfaceModelRequestQuery ontologyQuery = new InterfaceModelRequestQuery();
              ontologyQuery.setOntologyModelId(details.getOntologyModelId());
              ontologyQuery.setType(details.getType());
              OntologyModelApiVO ontologyModel = getOntologyModel(ontologyQuery);
              if (ontologyModel == null) {
                   throw new ApplicationException("获取模型信息失败");
                 }
         
              if (!CollectionUtils.isEmpty(responseList)) {
                   Integer type = details.getType();
                   if (type.equals(Integer.valueOf(1))) {
                        createData(map, responseList.get(0));
                        log.info("data:{}", JsonUtil.toJSON(map));
                      } else {
                        createDetail(map, responseList.get(0));
                        log.info("data:{}", JsonUtil.toJSON(map));
                      }
                 }
         
//         
//              String primaryKey = ontologyModel.getParamNameEn();
//         
//              String mainTable = "";
//         
//         
//              List<OntologyModelNodeHistoryDTO> ontologyModelNodeHistoryDTOS = this.ontologyModelNodeHistoryService.queryDesensitization(result, details.getOntologyModelVersion());
//         
              String sql = "select * from person.info ";
//              log.info("sql:{}", sql);
//         
         
              List<OntologyModelNodeHistoryDTO> ontologyModelNodeHistoryList = this.ontologyModelNodeHistoryService.queryDesensitization(result, details.getOntologyModelVersion());
              Map<String, OntologyModelNodeHistoryDTO> collectMap = (Map<String, OntologyModelNodeHistoryDTO>)ontologyModelNodeHistoryList.stream().collect(Collectors.toMap(OntologyModelNodeHistoryDTO::getFieldName, item -> item, (k1, k2) -> k1));
         
              List<Map<String, Object>> list = this.dataQueryService.queryNormalData(sql);
              for (Map<String, Object> mapObj : list) {
                   Set<Map.Entry<String, Object>> entries = mapObj.entrySet();
                   entries.stream().forEach(item -> {
                            OntologyModelNodeHistoryDTO ontologyModelNodeHistoryDTO = (OntologyModelNodeHistoryDTO)collectMap.get(item.getKey());
               
                            if (ontologyModelNodeHistoryDTO != null) {
                                 Integer sensitiveIndex = ontologyModelNodeHistoryDTO.getSensitiveIndex();
                  
                                 Integer sensitiveLength = ontologyModelNodeHistoryDTO.getSensitiveLength();
                  
                                 Integer sensitiveType = ontologyModelNodeHistoryDTO.getSensitiveType();
                                 String val = item.getValue().toString();
                                 int length = val.length();
                                 String resultVal = contentReplace(sensitiveIndex, sensitiveLength, sensitiveType, val, length);
                                 item.setValue(resultVal);
                               }
                          });
                 }
              return list;
      }
   }

   public List<Map<String, Object>> getCustomizeData(String token, String params) throws IOException {
      HashMap<String, Object> map = new HashMap();
      InterfaceDetailsDTO details = (InterfaceDetailsDTO)this.redisTemplate.opsForValue().get(token);
      if (details == null) {
         details = this.getInterfaceByAppId(token);
         this.redisTemplate.opsForValue().set(token, details, 300L, TimeUnit.SECONDS);
      }

      HashMap<String, Object> reqMap = new HashMap();
      if (details == null) {
         throw new ApplicationException("接口信息不能存在");
      } else if (!details.getApproveStatus().equals(ApproveStatusEnum.PASS.getCode())) {
         throw new ApplicationException("接口使用申请未审核通过");
      } else {
         Map<String, Object> stringObjectMap = JsonUtil.JsonToMap(params);
         String request = details.getRequest();
         List<InterfaceParamIndex> requestParams = (List)JsonUtil.decode(request, new TypeReference<List<InterfaceParamIndex>>() {
         });

         for (InterfaceParamIndex paramIndex : requestParams) {
             String key = paramIndex.getEnName();
             if (paramIndex.getRequired() != null && paramIndex.getRequired().intValue() == 1) {
                  String str = paramIndex.getDataType();
                  Object object = stringObjectMap.get(paramIndex.getEnName());
                  if (object == null) {
                       throw new ApplicationException(paramIndex.getEnName() + ":必填参数值不能为空");
                     }
                  extracted(reqMap, key, str, object); continue;
                }
             String dataType = paramIndex.getDataType();
             Object o = stringObjectMap.get(paramIndex.getEnName());
             extracted(reqMap, key, dataType, o);
           }

        log.info("data:{}", reqMap);


        String response = details.getResponse();

        List<OntologyModelParamVO> responseList = (List<OntologyModelParamVO>)JsonUtil.decode(response, new TypeReference<OntologyModelParamVO>() {
        });

        List<Long> result = Lists.newArrayList();
        splitNodes2ModelNodeId(responseList, result);

        InterfaceModelRequestQuery ontologyQuery = new InterfaceModelRequestQuery();
        ontologyQuery.setOntologyModelId(details.getOntologyModelId());
        ontologyQuery.setType(details.getType());
        OntologyModelApiVO ontologyModel = getOntologyModel(ontologyQuery);
        if (ontologyModel == null) {
          throw new ApplicationException("获取模型信息失败");
        }

        if (!CollectionUtils.isEmpty(responseList)) {
             Integer type = details.getType();
             if (type.equals(Integer.valueOf(1))) {
                  createData(map, responseList.get(0));
                  log.info("data:{}", JsonUtil.toJSON(map));
                } else {
                  createDetail(map, responseList.get(0));
                  log.info("data:{}", JsonUtil.toJSON(map));
                }
           }


        String primaryKey = ontologyModel.getParamNameEn();

        String mainTable = "";


        String sql = " select * from person.info ";
        log.info("sql:{}", sql);


        List<OntologyModelNodeHistoryDTO> ontologyModelNodeHistoryDTOS = this.ontologyModelNodeHistoryService.queryDesensitization(result, details.getOntologyModelVersion());
        Map<String, OntologyModelNodeHistoryDTO> collectMap = (Map<String, OntologyModelNodeHistoryDTO>)ontologyModelNodeHistoryDTOS.stream().collect(Collectors.toMap(OntologyModelNodeHistoryDTO::getFieldName, item -> item, (k1, k2) -> k1));

        List<Map<String, Object>> list = this.dataQueryService.queryNormalData(sql);
        for (Map<String, Object> mapObj : list) {
             Set<Map.Entry<String, Object>> entries = mapObj.entrySet();
             entries.stream().forEach(item -> {
                      OntologyModelNodeHistoryDTO ontologyModelNodeHistoryDTO = (OntologyModelNodeHistoryDTO)collectMap.get(item.getKey());

                      if (ontologyModelNodeHistoryDTO != null) {
                           Integer sensitiveIndex = ontologyModelNodeHistoryDTO.getSensitiveIndex();

                           Integer sensitiveLength = ontologyModelNodeHistoryDTO.getSensitiveLength();

                           Integer sensitiveType = ontologyModelNodeHistoryDTO.getSensitiveType();
                           String val = item.getValue().toString();
                           int length = val.length();
                           String resultVal = contentReplace(sensitiveIndex, sensitiveLength, sensitiveType, val, length);
                           item.setValue(resultVal);
                         }
                    });
           }
        return list;
      }
   }

   private String contentReplace(Integer sensitiveIndex, Integer sensitiveLength, Integer sensitiveType, String val, int length) {
      int start;
      int end;
      String substring;
      String content;
      int a;
      if (2 == sensitiveType) {
         start = sensitiveIndex > length ? 0 : sensitiveIndex;
         end = sensitiveIndex + sensitiveLength > length ? length : sensitiveIndex + sensitiveLength;
         substring = StringUtils.substring(val, start, end);
         content = "";

         for(a = 0; a < substring.length(); ++a) {
            content = content + "*";
         }

         return StringUtils.replace(val, substring, content);
      } else {
         start = sensitiveIndex + sensitiveLength > length ? 0 : length - (sensitiveIndex + sensitiveLength);
         end = sensitiveIndex > length ? length : length - sensitiveIndex + 1;
         substring = StringUtils.substring(val, start, end);
         System.out.println("substring---" + substring);
         content = "";

         for(a = 0; a < substring.length(); ++a) {
            content = content + "*";
         }

         return StringUtils.replace(val, substring, content);
      }
   }

   private void extracted(HashMap<String, Object> reqMap, String key, String dataType, Object o) {
      if ("varchar".equalsIgnoreCase(dataType)) {
         String s = ConverterUtils.toString(o);
         reqMap.put(key, s);
      }

      if ("int".equalsIgnoreCase(dataType)) {
         Integer integer = ConverterUtils.toInt(o);
         reqMap.put(key, integer);
      }

      if ("long".equalsIgnoreCase(dataType)) {
         Long aLong = ConverterUtils.toLong(o);
         reqMap.put(key, aLong);
      }

   }

   public boolean statusChange(InterfaceDTO interfaceDTO) {
      Interface anInterface = (Interface)InterfaceDTO.DEFAULT_CONVERTER.toDest(interfaceDTO);
      return this.interfaceDAO.updateById(anInterface);
   }

   public boolean desensitizationChange(InterfaceDTO interfaceDTO) {
      Interface anInterface = (Interface)InterfaceDTO.DEFAULT_CONVERTER.toDest(interfaceDTO);
      return this.interfaceDAO.updateById(anInterface);
   }

   public boolean apply(InterfaceDetailsDTO interfaceDetailsDTO) {
      InterfaceDTO interfaceDTO = this.getById(interfaceDetailsDTO.getInterfaceId());
      if (!interfaceDTO.getStatus()) {
         throw new ApplicationException("接口已停用，无法申请使用");
      } else if (interfaceDTO.getIsDelete()) {
         throw new ApplicationException("接口已被删除，无法申请使用");
      } else if (interfaceDTO.getInterfaceType() == InterfaceEnum.CUSTOMIZE.getCode()) {
         throw new ApplicationException("自定义接口不能通过通用接口入口申请使用");
      } else {
         boolean flag = this.applyService.checkApply(interfaceDetailsDTO.getInterfaceId(), interfaceDetailsDTO.getUserId());
         if (!flag) {
            throw new ApplicationException("有效时间内同个接口已存在申请，不能重复添加");
         } else {
            interfaceDetailsDTO.setInterfaceType(interfaceDTO.getInterfaceType());
            return this.saveApply(interfaceDetailsDTO);
         }
      }
   }

   public InterfaceDetailsDTO getInterfaceByAppId(String appId) {
      return this.interfaceDAO.getInterfaceByAppId(appId);
   }

   public PageBean<ApplyInterfaceVO> queryApplyInterface(InterfacesQuery interfacesQuery) {
      IPage<ApplyInterfaceVO> applyInterfaceVOIPage = this.interfaceDAO.queryApplyInterface(interfacesQuery);
      return this.data2PageBean(applyInterfaceVOIPage);
   }

   public boolean checkName(InterfacesQuery interfacesQuery) {
      return this.interfaceDAO.checkName(interfacesQuery);
   }

   public IndexInterfaceVO indexData() {
      IndexInterfaceVO vo = new IndexInterfaceVO();
      Long interfaceCount = this.interfaceDAO.queryInterfaceCount();
      Long called = this.interfaceDAO.queryCalled();
      vo.setInterfaceCounts(interfaceCount);
      vo.setCalled(called);
      return vo;
   }

   public List<InterfaceDTO> newest() {
      return this.interfaceDAO.newest();
   }

   public List<InterfaceVO> apiTop10() {
      return this.interfaceDAO.apiTop10();
   }

   public List<InterfaceVO> lastApi() {
      return this.interfaceDAO.lastApi();
   }

   private PageBean<InterfaceVO> IPage2PageBean(IPage<InterfaceVO> page) {
      PageBean<InterfaceVO> result = new PageBean();
      result.setTotalElements(page.getTotal());
      result.setNumber((int)page.getCurrent());
      result.setSize((int)page.getSize());
      result.setTotalPages((int)page.getPages());
      result.setNumberOfElements(page.getRecords().size());
      result.setContent(page.getRecords());
      return result;
   }

   private PageBean<ApplyInterfaceVO> data2PageBean(IPage<ApplyInterfaceVO> page) {
      PageBean<ApplyInterfaceVO> result = new PageBean();
      result.setTotalElements(page.getTotal());
      result.setNumber((int)page.getCurrent());
      result.setSize((int)page.getSize());
      result.setTotalPages((int)page.getPages());
      result.setNumberOfElements(page.getRecords().size());
      result.setContent(page.getRecords());
      return result;
   }

   public boolean createUpdateLog(boolean isUpdate, Long interfaceId, InterfaceDetailsDTO detailsDTO, InterfaceDTO beforeData) {
      InterfaceUpdateLogDetailDTO updateLogDTO = null;
      if (!isUpdate) {
         updateLogDTO = new InterfaceUpdateLogDetailDTO();
         updateLogDTO.setInterfaceId(interfaceId);
         updateLogDTO.setRequestUpdateAfter(detailsDTO.getRequest());
         updateLogDTO.setResponseUpdateAfter(detailsDTO.getResponse());
      } else {
         log.info("beforeData:{}", beforeData.getResponse());
         log.info("after:{}", detailsDTO.getResponse());
         if (!beforeData.getRequest().equals(detailsDTO.getRequest()) || !beforeData.getResponse().equals(detailsDTO.getResponse())) {
            updateLogDTO = new InterfaceUpdateLogDetailDTO();
            updateLogDTO.setInterfaceId(interfaceId);
            updateLogDTO.setRequestUpdateBefore(beforeData.getRequest());
            updateLogDTO.setRequestUpdateAfter(detailsDTO.getRequest());
            updateLogDTO.setResponseUpdateBefore(beforeData.getResponse());
            updateLogDTO.setResponseUpdateAfter(detailsDTO.getResponse());
         }

         if (updateLogDTO != null) {
            return this.updateLogService.saveOrUpdate(updateLogDTO);
         }
      }

      return false;
   }

   private void createData(Object parent, OntologyModelParamVO node) {
      if (this.isCatalog(node)) {
         HashMap<String, Object> value = new HashMap();
         ((HashMap)parent).put(node.getParamNameEn(), value);
         if (!CollectionUtils.isEmpty(node.getChilds())) {
            node.getChilds().stream().forEach((child) -> {
               this.createData(value, child);
            });
         }
      } else {
         ((HashMap)parent).put(node.getParamNameEn(), "");
      }

   }

   private void createDetail(Object parent, OntologyModelParamVO node) {
      if (this.isCatalog(node)) {
         HashMap<String, Object> value = new HashMap();
         ((HashMap)parent).put(node.getParamNameEn(), value);
         if (!CollectionUtils.isEmpty(node.getChilds())) {
            node.getChilds().stream().forEach((child) -> {
               this.createDetail(value, child);
            });
         }
      } else {
         ArrayList<HashMap<String, Object>> dataList = new ArrayList();
         ((HashMap)parent).put(node.getParamNameEn(), dataList);
         if (!CollectionUtils.isEmpty(node.getChilds())) {
            HashMap<String, Object> value = new HashMap();
            node.getChilds().stream().forEach((child) -> {
               value.put(child.getParamNameEn(), "");
            });
            dataList.add(value);
         }
      }

   }

   private boolean isCatalog(OntologyModelParamVO node) {
      return node.getDataShape() == null || node.getDataShape() == 5;
   }

   private void recursionNodes(List<OntologyModelParamVO> parentDTOs, List<OntologyModelParamVO> nodes) {
     for (OntologyModelParamVO nodeDto : parentDTOs) {
          if (nodes.stream().filter(n -> n.getParent().equals(nodeDto.getOntologyModelNodeId())).findFirst().isPresent()) {

               List<OntologyModelParamVO> newNodes = (List<OntologyModelParamVO>)nodes.stream().filter(n -> n.getParent().equals(nodeDto.getOntologyModelNodeId())).collect(Collectors.toList());
               Collections.sort(newNodes);
               nodeDto.setChilds(newNodes);
               recursionNodes(newNodes, nodes);
             }
        }

   }

   private void splitNodes(List<OntologyModelParamVO> dataList, List<OntologyModelParamVO> result, List<OntologyModelParamVO> parent) {
     for (OntologyModelParamVO data : dataList) {
          if (CollectionUtils.isEmpty(data.getChilds())) {
               result.add(data); continue;
             }
          if (!CollectionUtils.isEmpty(data.getChilds())) {
               OntologyModelParamVO vo = new OntologyModelParamVO();
               BeanUtils.copyProperties(data, vo);
               vo.setChilds(null);
               parent.add(vo);
             }
          splitNodes(data.getChilds(), result, parent);
        }

   }

   private List<OntologyModelParamVO> formatTreeData(List<OntologyModelParamVO> nodes, Long ontologyModelId) {
      List<OntologyModelParamVO> parents = (List)nodes.stream().filter((n) -> {
         return n.getParent().equals(new Long(0L));
      }).collect(Collectors.toList());
      Collections.sort(parents);
      this.recursionNodes(parents, nodes);
      return parents;
   }

   private void splitNodes2ModelNodeId(List<OntologyModelParamVO> dataList, List<Long> result) {
     for (OntologyModelParamVO data : dataList) {
          if (CollectionUtils.isEmpty(data.getChilds())) {
               result.add(data.getOntologyModelNodeId()); continue;
             }
          splitNodes2ModelNodeId(data.getChilds(), result);
        }
   }

   public String buildSql(String mainTable, String primaryKey, List<Long> list, Map<String, Object> reqMap, Integer type) {
      List<OntologyFieldMapDTO> ontologyFieldMapDTOS = this.ontologyFieldMapService.queryFieldMap(list);
      if (CollectionUtils.isEmpty(ontologyFieldMapDTOS)) {
         throw new ApplicationException("获取数据资源信息失败");
      } else {
         StringBuilder selects = null;
         StringBuilder from = null;
         for (OntologyFieldMapDTO fieldMapDTO : ontologyFieldMapDTOS) {
          if (StringUtils.isEmpty(selects.toString())) {
               selects = new StringBuilder();
               from = new StringBuilder();
               selects.append(" select ");
               selects.append(fieldMapDTO.getDestSchema() + "." + fieldMapDTO.getDestTableName() + "." + fieldMapDTO.getDestFieldName() + ",");
      
               from.append(" from  ");
               from.append(fieldMapDTO.getDestSchema());
               from.append(".");
               from.append(mainTable);
               from.append(" AS ").append(mainTable); continue;
             }
          selects.append(fieldMapDTO.getDestSchema() + "." + fieldMapDTO.getDestTableName() + "." + fieldMapDTO.getDestFieldName() + ",");
   
          from.append(" left join  " + fieldMapDTO.getDestSchema() + "." + fieldMapDTO.getDestTableName() + " AS ");
          from.append(fieldMapDTO.getDestTableName());
          from.append(" on  ").append(fieldMapDTO.getDestTableName()).append(".").append(primaryKey);
          from.append(" = ").append(mainTable).append(".").append(primaryKey);
        }

         int i = selects.lastIndexOf(",");
         String selectStr = StringUtils.substring(selects.toString(), 0, i);
         StringBuilder where = new StringBuilder();
         where.append(" where ");
         Set<Map.Entry<String, Object>> entries = reqMap.entrySet();
         entries.stream().forEach((item) -> {
            where.append(mainTable).append(".").append((String)item.getKey()).append("=").append(item.getValue()).append(" and ");
         });
         int end = where.lastIndexOf("and");
         String whereStr = StringUtils.substring(where.toString(), 0, end);
         if (type.equals(InterfaceQueryEnum.DETAILS.getCode())) {
            Integer page = (Integer)reqMap.get("page");
            Integer pageSize = (Integer)reqMap.get("pageSize");
            if (pageSize == null) {
               where.append("limit ").append(page).append(page * 50);
            } else {
               where.append("limit ").append(page).append(page * 200);
            }
         }

         String result = selectStr + from + whereStr;
         return result;
      }
   }

   public Integer getUsersByOrgRoleId(Long menuId) {
      String url = this.spa_gateway_uri + "data-catalogue-api" + "/sysAdminUser/findMenuAuthority?menuId=" + menuId + "&userId=" + SecurityUtils.getCurrentUserId();
      JSONObject result = JSON.parseObject(this.httpUtils.sendGet(url));
      if (null == result.get("payload")) {
         log.info("人员菜单信息不存在:{}", url);
         return null;
      } else {
         List<UserInfoDTO> userInfoDTOS = JSONArray.parseArray(result.get("payload").toString(), UserInfoDTO.class);
         if (!CollectionUtils.isEmpty(userInfoDTOS)) {
            String approveListUrl = this.spa_gateway_uri + "data-catalogue-api" + "/data-catalogue/v1/org/catalogue/getTables";
            CatalogueGetTableRequest request = new CatalogueGetTableRequest();
            boolean isAdmin = false;
            List<LinkedHashMap<String, String>> orgs = SecurityUtils.getCurrentUserRoles();
            if (!CollectionUtils.isEmpty(orgs)) {
               for (LinkedHashMap<String, String> map : orgs) {
                 Set<Map.Entry<String, String>> org = map.entrySet();
                 Map.Entry<String, String> item = org.iterator().next();
                 if (OrgDsApplyConstants.ADMIN_ROLE_ID.equals(Long.valueOf(Long.parseLong(item.getKey())))) {
                      isAdmin = true;
                    }
               }
            }

            if (!isAdmin) {
               request.setOrgId(Long.parseLong(SecurityUtils.getDepartmentId().toString()));
            }

            if (CommonConstants.CATALOGUE_APPROVE_FIRST_REVIEW_MENU_ID.equals(menuId)) {
               request.setTableState(new Integer[]{CommonConstants.CATALOGUE_APPROVE_FIRST_REVIEW});
            } else if (CommonConstants.CATALOGUE_APPROVE_REVIEW_PUBLISH_MENU_ID.equals(menuId)) {
               request.setTableState(new Integer[]{CommonConstants.CATALOGUE_APPROVE_REVIEW, CommonConstants.CATALOGUE_APPROVE_PUBLISH});
            }

            JSONObject firstResult = JSON.parseObject(this.httpUtils.sendPost(approveListUrl, (JSONObject)JSONObject.toJSON(request)));
            if (null != firstResult.get("payload")) {
               JSONObject payload = (JSONObject)firstResult.get("payload");
               return Integer.valueOf(payload.get("totalElements").toString());
            }

            log.info("调用初审/复审列表无数据url:{}，参数：{}", url, request);
         }

         return null;
      }
   }
}

package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.StrUtil;
import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import com.grandlynn.spa.catalogue.domain.request.AnalysisCatalogAmountTrendRequest;
import com.grandlynn.spa.catalogue.domain.request.AnalysisCatalogueRankingRequest;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogAmountTrendResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueRankingResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueStatisticsResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisTotalResponse;
import com.grandlynn.spa.catalogue.dto.OrgStatisticsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.mapper.AnalysisMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.AnalysisService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;

import java.util.List;
import javax.annotation.Resource;

import com.grandlynn.util.extension.ApplicationException;
import org.springframework.stereotype.Service;

@Service
public class AnalysisServiceImpl implements AnalysisService {
   @Resource
   private AnalysisMapper analysisMapper;
   @Resource
   private CatalogueOrgService catalogueOrgService;

   public AnalysisTotalResponse total() {
      return this.analysisMapper.totalCount();
   }

   public List<AnalysisCatalogueRankingResponse> catalogRanking(AnalysisCatalogueRankingRequest request) {
      String field = StrUtil.isEmpty(request.getField()) ? "cataNumber" : request.getField();
      String order = StrUtil.isEmpty(request.getOrder()) ? "desc" : request.getOrder();
      return this.analysisMapper.catalogRanking(field, order);
   }

   public AnalysisCatalogueStatisticsResponse catalogue(Long orgid) {
      if (null == orgid) {
         Long[] orgids = SecurityUtil.currentUser().getOrgids();
         orgid = null != orgids && orgids.length >= 1 ? orgids[0] : null;
      }

      if (null == orgid) {
         return null;
      } else {
         AnalysisCatalogueStatisticsResponse rsp = this.analysisMapper.catalogByOrg(orgid);
         CatalogueOrgDO org = (CatalogueOrgDO)this.catalogueOrgService.getById(orgid);
         rsp.setOrgName(null == org ? null : org.getNodeName());
         rsp.setSysItems(this.analysisMapper.catalogSystemItem(orgid));
         rsp.setSysNumber(rsp.getSysItems().size());
         rsp.setTableItems(this.analysisMapper.catalogTableItem(orgid));
         rsp.setTableNumber(rsp.getTableItems().size());
         rsp.setTotalNumber(this.analysisMapper.totalTableNumber(orgid) + rsp.getTableItems().size());
         return rsp;
      }
   }

   public List<AnalysisCatalogueRankingResponse> catalogTableStatistics() {
      return this.analysisMapper.catalogTableStatistics();
   }

   public AnalysisCatalogAmountTrendResponse catalogAmountTrend(AnalysisCatalogAmountTrendRequest request) {
      if (StrUtil.isEmpty(request.getInterval()) && null == request.getBeginTime() && null == request.getEndTime()) {
         throw new ApplicationException(ResultCodeEnum.INVALID_PARAMETER);
      } else {
         List data;
         if (StrUtil.isEmpty(request.getInterval())) {
            data = this.analysisMapper.catalogAmountStatisByRange(request.getBeginTime(), request.getEndTime());
         } else {
            switch (request.getInterval()) {
               case "year":
                  data = this.analysisMapper.catalogAmountStatisByYear();
                  break;
               case "month":
                  data = this.analysisMapper.catalogAmountStatisByMonth();
                  break;
               case "week":
                  data = this.analysisMapper.catalogAmountStatisByWeek();
                  break;
               default:
                  throw new ApplicationException(ResultCodeEnum.INVALID_PARAMETER);
            }
         }

         AnalysisCatalogAmountTrendResponse rsp = new AnalysisCatalogAmountTrendResponse();
         rsp.setData(data);
         return rsp;
      }
   }

   public List<OrgStatisticsDTO> resourceStatistics() {
      return this.catalogueOrgService.resourceStatistics();
   }
}

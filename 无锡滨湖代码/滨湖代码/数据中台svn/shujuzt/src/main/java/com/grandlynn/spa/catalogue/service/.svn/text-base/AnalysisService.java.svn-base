package com.grandlynn.spa.catalogue.service;

import com.grandlynn.spa.catalogue.domain.request.AnalysisCatalogAmountTrendRequest;
import com.grandlynn.spa.catalogue.domain.request.AnalysisCatalogueRankingRequest;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogAmountTrendResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueRankingResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueStatisticsResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisTotalResponse;
import com.grandlynn.spa.catalogue.dto.OrgStatisticsDTO;

import java.util.List;

public interface AnalysisService {
   AnalysisTotalResponse total();

   List<AnalysisCatalogueRankingResponse> catalogRanking(AnalysisCatalogueRankingRequest request);

   AnalysisCatalogueStatisticsResponse catalogue(Long orgid);

   List<AnalysisCatalogueRankingResponse> catalogTableStatistics();

   AnalysisCatalogAmountTrendResponse catalogAmountTrend(AnalysisCatalogAmountTrendRequest request);

   List<OrgStatisticsDTO> resourceStatistics();
}

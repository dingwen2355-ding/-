package com.grandlynn.spa.catalogue.mapper;

import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueRankingResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisCatalogueStatisticsResponse;
import com.grandlynn.spa.catalogue.domain.response.AnalysisTotalResponse;
import com.grandlynn.spa.catalogue.dto.AnalysisListDto;
import com.grandlynn.spa.catalogue.dto.AnalysisTrendDto;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnalysisMapper {
   AnalysisTotalResponse total();

   AnalysisTotalResponse totalCount();

   List<AnalysisCatalogueRankingResponse> catalogRanking(@Param("field") String field, @Param("order") String order);

   AnalysisCatalogueStatisticsResponse catalogByOrg(@Param("orgId") Long orgid);

   Integer totalTableNumber(@Param("orgId") Long orgid);

   List<AnalysisListDto> catalogSystemItem(@Param("orgId") Long orgid);

   List<AnalysisListDto> catalogTableItem(@Param("orgId") Long orgid);

   List<AnalysisCatalogueRankingResponse> catalogTableStatistics();

   List<AnalysisTrendDto> catalogAmountStatisByYear();

   List<AnalysisTrendDto> catalogAmountStatisByMonth();

   List<AnalysisTrendDto> catalogAmountStatisByWeek();

   List<AnalysisTrendDto> catalogAmountStatisByRange(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}

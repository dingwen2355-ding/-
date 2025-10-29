package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.TotalCompareRequest;
import com.grandlynn.spa.catalogue.dto.TotalCompareUnmatchDto;
import com.grandlynn.spa.catalogue.entity.TotalCompareTableLogDO;

import java.util.List;

public interface TotalCompareTableLogMapper extends BaseMapper<TotalCompareTableLogDO> {
   List<TotalCompareTableLogDO> query(TotalCompareRequest request);

   List<TotalCompareUnmatchDto> unmatch();
}

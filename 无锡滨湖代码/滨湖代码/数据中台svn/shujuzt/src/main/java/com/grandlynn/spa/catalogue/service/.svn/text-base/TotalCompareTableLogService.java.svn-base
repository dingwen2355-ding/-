package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.TotalCompareRequest;
import com.grandlynn.spa.catalogue.domain.response.TotalCompareTableLogBySysResponse;
import com.grandlynn.spa.catalogue.entity.TotalCompareFieldLogDO;
import com.grandlynn.spa.catalogue.entity.TotalCompareTableLogDO;
import com.grandlynn.util.pageHelper.PageBean;
import java.util.List;

public interface TotalCompareTableLogService extends IService<TotalCompareTableLogDO> {
   PageBean<TotalCompareTableLogBySysResponse> getResult(TotalCompareRequest request);

   List<TotalCompareFieldLogDO> getResultByTableLogId(TotalCompareRequest request);

   void calculate(Long tableId);

   void testUnmatch();
}

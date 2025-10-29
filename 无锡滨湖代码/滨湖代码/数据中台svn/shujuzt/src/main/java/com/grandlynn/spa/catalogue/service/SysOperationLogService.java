package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.SysOperationLogRequest;
import com.grandlynn.spa.catalogue.domain.response.SysOperationLogAnalysisResponse;
import com.grandlynn.spa.catalogue.domain.response.SysOperationLogResponse;
import com.grandlynn.spa.catalogue.entity.SysOperationLogDO;
import com.grandlynn.util.pageHelper.PageBean;

public interface SysOperationLogService extends IService<SysOperationLogDO> {
   PageBean<SysOperationLogResponse> getPage(SysOperationLogRequest request);

   SysOperationLogAnalysisResponse findAnalysis(SysOperationLogRequest request);

   Boolean sm3Hmac();
}

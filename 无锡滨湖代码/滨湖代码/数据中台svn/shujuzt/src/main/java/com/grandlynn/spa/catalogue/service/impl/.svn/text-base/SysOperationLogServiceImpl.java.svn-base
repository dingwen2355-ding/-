package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.SysOperationLogRequest;
import com.grandlynn.spa.catalogue.domain.response.SysOperationLogAnalysisResponse;
import com.grandlynn.spa.catalogue.domain.response.SysOperationLogResponse;
import com.grandlynn.spa.catalogue.entity.SysOperationLogDO;
import com.grandlynn.spa.catalogue.mapper.SysOperationLogMapper;
import com.grandlynn.spa.catalogue.service.SysOperationLogService;
import com.grandlynn.spa.catalogue.utils.KmsUtils;
import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.StringUtil;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

@Service("sysOperationLogService")
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLogDO> implements SysOperationLogService {
   private static final Logger log = LoggerFactory.getLogger(SysOperationLogServiceImpl.class);
   @Override
   public PageBean<SysOperationLogResponse> getPage(SysOperationLogRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      List<SysOperationLogDO> list = this.list(Wrappers.<SysOperationLogDO>lambdaQuery()
              .between(request.getStartTime() != null && request.getEndTime() != null, SysOperationLogDO::getCreatedTime, request.getStartTime(), request.getEndTime())
              .eq(StringUtil.isNotBlank(request.getTitle()), SysOperationLogDO::getTitle, request.getTitle())
              .like(StringUtil.isNotBlank(request.getUserName()), SysOperationLogDO::getUserName, request.getUserName())
              .eq(StringUtil.isNotBlank(request.getBusinessType()), SysOperationLogDO::getBusinessType, request.getBusinessType())
              .eq(StringUtil.isNotBlank(request.getStatus()), SysOperationLogDO::getStatus, request.getStatus())
              .orderByDesc(SysOperationLogDO::getCreatedTime));
      for (SysOperationLogDO operLog : list) {
         if (StringUtil.isNotEmpty(operLog.getSm3Hmac())) {
            String sm3Hmac = KmsUtils.sm3Hmac(operLog.getUserId() + operLog.getUserName() + operLog.getTitle() + operLog.getBusinessType() + operLog.getStatus() + operLog.getErrorMgs() + operLog.getCreatedTime().getTime());
            if (StringUtils.isNotEmpty(sm3Hmac) && !sm3Hmac.equals(operLog.getSm3Hmac())) {
               log.error("日志数据被篡改,日志id:{},日志数据:{}", operLog.getId(), JSON.toJSONString(operLog));
               throw new ApplicationException("日志数据被篡改,请联系管理员!");
            }
         }
      }
      return ObjectCloneUtils.convertPageBean(new PageBean(list), SysOperationLogResponse.class);
   }
   @Override
   public SysOperationLogAnalysisResponse findAnalysis(SysOperationLogRequest request) {
      SysOperationLogAnalysisResponse response = new SysOperationLogAnalysisResponse();
      int loginNumber = 0;
      List<SysOperationLogDO> logList = this.list(Wrappers.<SysOperationLogDO>lambdaQuery().select(SysOperationLogDO::getUserId)
              .between(request.getStartTime() != null && request.getEndTime() != null, SysOperationLogDO::getCreatedTime, request.getStartTime(), request.getEndTime())
              .in(SysOperationLogDO::getBusinessType, Arrays.asList("统一认证登录", "系统登录"))
              .groupBy(SysOperationLogDO::getUserId));
      if (CollectionUtil.isNotEmpty(logList)) {
         loginNumber = logList.size();
      }

      long submitApplyNumber = this.count(Wrappers.<SysOperationLogDO>lambdaQuery().between(request.getStartTime() != null && request.getEndTime() != null, SysOperationLogDO::getCreatedTime, request.getStartTime(), request.getEndTime()).in(SysOperationLogDO::getBusinessType, Arrays.asList("提交目录申请", "一键提交目录申请", "需求申请", "资源申请", "重新申请")));
      long applyNumber = this.count(Wrappers.<SysOperationLogDO>lambdaQuery().between(request.getStartTime() != null && request.getEndTime() != null, SysOperationLogDO::getCreatedTime, request.getStartTime(), request.getEndTime()).in(SysOperationLogDO::getBusinessType, Arrays.asList("目录初审通过", "目录初审驳回", "目录复审通过", "目录复审驳回", "目录审批一键通过", "资源审批", "审批不通过", "资源审批")));
      long abnormalNumber = this.count(Wrappers.<SysOperationLogDO>lambdaQuery().between(request.getStartTime() != null && request.getEndTime() != null, SysOperationLogDO::getCreatedTime, request.getStartTime(), request.getEndTime()).eq(SysOperationLogDO::getStatus, "500"));
      response.setLoginNumber((long)loginNumber);
      response.setSubmitApplyNumber(submitApplyNumber);
      response.setApplyNumber(applyNumber);
      response.setAbnormalNumber(abnormalNumber);
      double applyRatio = 0.0;
      if (applyNumber == 0) {
         applyRatio = 0.0;
      } else if (submitApplyNumber == 0) {
         applyRatio = 0.0;
      } else {
         applyRatio = (double)applyNumber * 1.0 / ((double)submitApplyNumber * 1.0);
      }

      NumberFormat nf = NumberFormat.getNumberInstance();
      nf.setMaximumFractionDigits(2);
      response.setApplyRatio(Double.valueOf(nf.format(applyRatio * 100.0)));
      return response;
   }

   public Boolean sm3Hmac() {
      List<SysOperationLogDO> list = list(Wrappers.<SysOperationLogDO>lambdaQuery().isNull(SysOperationLogDO::getSm3Hmac));
      if (CollectionUtil.isEmpty(list))
         return Boolean.valueOf(true);
      for (SysOperationLogDO operLog : list)
         operLog.setSm3Hmac(KmsUtils.sm3Hmac(operLog.getUserId() + operLog.getUserName() + operLog.getTitle() + operLog.getBusinessType() + operLog.getStatus() + operLog.getErrorMgs() + operLog.getCreatedTime().getTime()));
      return Boolean.valueOf(updateBatchById(list));
   }
}

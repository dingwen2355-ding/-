package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.SmsQueryRequest;
import com.grandlynn.spa.catalogue.domain.request.SmsRequest;
import com.grandlynn.spa.catalogue.domain.response.SmsResponse;
import com.grandlynn.spa.catalogue.dto.SmsDTO;
import com.grandlynn.spa.catalogue.entity.SmsDO;
import com.grandlynn.util.pageHelper.PageBean;

import java.util.List;

public interface SmsService extends IService<SmsDO> {
   SmsResponse send(SmsRequest smsRequest);

   SmsResponse sendToDept(Integer orgId, String code, List<String> params);
   SmsResponse sendToFlowMessage(SmsRequest smsRequest);

   PageBean<SmsDTO> findPage(SmsQueryRequest request);
}

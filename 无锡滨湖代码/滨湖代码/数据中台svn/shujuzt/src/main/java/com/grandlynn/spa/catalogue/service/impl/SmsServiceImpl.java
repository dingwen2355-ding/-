package com.grandlynn.spa.catalogue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.SmsQueryRequest;
import com.grandlynn.spa.catalogue.domain.request.SmsRequest;
import com.grandlynn.spa.catalogue.domain.response.SmsResponse;
import com.grandlynn.spa.catalogue.dto.SmsDTO;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.SmsDO;
import com.grandlynn.spa.catalogue.mapper.SmsMapper;
import com.grandlynn.spa.catalogue.observer.MessageSubject;
import com.grandlynn.spa.catalogue.observer.impl.SMSObserver;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.SmsService;
import com.grandlynn.spa.catalogue.service.SysRoleService;


import com.grandlynn.util.CollectionUtil;
import com.grandlynn.util.StringUtil;
import com.grandlynn.util.pageHelper.PageBean;
import com.grandlynn.util.pojo.ObjectCloneUtils;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, SmsDO> implements SmsService {
   private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
   @Autowired
   private MessageSubject messageSubject;
   @Autowired
   private SysRoleService sysRoleService;
   @Autowired
   private CatalogueOrgService catalogueOrgService;

   public SmsResponse send(SmsRequest smsRequest) {
      Map<String, String> map = this.messageSubject.notifyObServer(smsRequest);
      Class<SMSObserver> smsObserverClass = SMSObserver.class;
      String sendGet = (String)map.get(smsObserverClass.getSimpleName());
      JSONObject jsonObject = JSONObject.parseObject(sendGet);
      String code = jsonObject.getString("code");
      String message = jsonObject.getString("message");
      String data = jsonObject.getString("data");
      SmsResponse smsResponse = new SmsResponse();
      smsResponse.setCode(code);
      smsResponse.setMessage(message);
      smsResponse.setData(data);
      SmsDO smsDO = new SmsDO();
      smsDO.setMobile(smsRequest.getMobile());
      smsDO.setUserName(smsRequest.getUserName());
      smsDO.setSendStatus(code);
      smsDO.setMessage(smsRequest.getMsg());
      smsDO.setSendTime(new Date());
      this.save(smsDO);
      return smsResponse;
   }

   public SmsResponse sendToDept(Integer orgId, String code, List<String> params) {
      StringBuffer mobileBuffer = new StringBuffer("");
      StringBuffer userNameBuffer = new StringBuffer("");
      List<UserInfoDTO> compileUserInfos = this.sysRoleService.getUsersByOrgRoleId(orgId, 1);
      if (CollectionUtil.isNotEmpty(compileUserInfos)) {
         mobileBuffer.append((String)compileUserInfos.stream().map(UserInfoDTO::getMobile).filter(StringUtil::isNotBlank).collect(Collectors.joining(",")));
         userNameBuffer.append((String)compileUserInfos.stream().map(UserInfoDTO::getUserName).collect(Collectors.joining(",")));
      }

      List<UserInfoDTO> manageUserInfos = this.sysRoleService.getUsersByOrgRoleId(orgId, 2);
      if (CollectionUtil.isNotEmpty(manageUserInfos)) {
         if (StringUtil.isNotBlank(mobileBuffer.toString())) {
            mobileBuffer.append(",");
         }

         if (StringUtil.isNotBlank(userNameBuffer.toString())) {
            userNameBuffer.append(",");
         }

         mobileBuffer.append((String)manageUserInfos.stream().map(UserInfoDTO::getMobile).filter(StringUtil::isNotBlank).collect(Collectors.joining(",")));
         userNameBuffer.append((String)manageUserInfos.stream().map(UserInfoDTO::getUserName).collect(Collectors.joining(",")));
      }

      if (StringUtil.isBlank(mobileBuffer.toString())) {
         log.info("发送部门短信失败，无手机号数据");
         return new SmsResponse();
      } else {
         try {
            SmsRequest request = new SmsRequest();
            request.setMobile(mobileBuffer.toString());
            request.setCode(code);
            request.setUserName(userNameBuffer.toString());
            CatalogueOrgDO orgDO = (CatalogueOrgDO)this.catalogueOrgService.getById(orgId);
            List<String> paramsRebuild = new ArrayList();
            paramsRebuild.add(orgDO.getNodeNameSimplify());
            paramsRebuild.addAll(params);
            request.setParams(paramsRebuild);
            return this.send(request);
         } catch (Exception var11) {
            log.info("发送部门短信失败，参数部门：{}，code：{}，params：{}", new Object[]{orgId, code, params, var11});
            return new SmsResponse();
         }
      }
   }

   @Override
   public SmsResponse sendToFlowMessage(SmsRequest smsRequest) {
      try {
         SmsRequest request = new SmsRequest();
         request.setMobile(smsRequest.getMobile());
         request.setCode(smsRequest.getCode());
         request.setUserName(smsRequest.getUserName());
         CatalogueOrgDO orgDO = this.catalogueOrgService.getById(smsRequest.getOrgId());
         List<String> paramsRebuild = new ArrayList();
         paramsRebuild.add(orgDO.getNodeNameSimplify());
         paramsRebuild.addAll(smsRequest.getParams());
         request.setParams(paramsRebuild);
         return this.send(request);
      } catch (Exception var11) {
         log.info("发送流程短信失败，参数部门：{}，code：{}，params：{}", smsRequest.getOrgId(), smsRequest.getCode(), smsRequest.getParams(), var11);
         return new SmsResponse();
      }

   }

   public PageBean<SmsDTO> findPage(SmsQueryRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      List<SmsDO> list = this.baseMapper.selectList(Wrappers.<SmsDO>lambdaQuery()
              .like(StringUtils.isNotBlank(request.getMobile()), SmsDO::getMobile, request.getMobile())
              .eq(StringUtils.isNotBlank(request.getSendStatus()) && request.getSendStatus().equals(HttpStatus.OK.value() + ""), SmsDO::getSendStatus, request.getSendStatus())
              .ne(StringUtils.isNotBlank(request.getSendStatus()) && !request.getSendStatus().equals(HttpStatus.OK.value() + ""), SmsDO::getSendStatus, HttpStatus.OK.value() + "")
              .orderByDesc(SmsDO::getSendTime));
      return ObjectCloneUtils.convertPageBean(new PageBean(list), SmsDTO.class);
   }

}

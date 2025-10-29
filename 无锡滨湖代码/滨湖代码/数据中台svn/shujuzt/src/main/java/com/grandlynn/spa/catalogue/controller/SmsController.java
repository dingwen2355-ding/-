package com.grandlynn.spa.catalogue.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.grandlynn.spa.catalogue.common.enums.SmsEnum;
import com.grandlynn.spa.catalogue.config.SsoLoginConfig;
import com.grandlynn.spa.catalogue.domain.request.SmsQueryRequest;
import com.grandlynn.spa.catalogue.domain.request.SmsRequest;
import com.grandlynn.spa.catalogue.domain.request.SmsToDeptRequest;
import com.grandlynn.spa.catalogue.domain.response.SmsResponse;
import com.grandlynn.spa.catalogue.dto.CatalogueVersionLogTableDTO;
import com.grandlynn.spa.catalogue.dto.SmsDTO;
import com.grandlynn.spa.catalogue.service.AuthAppUserService;
import com.grandlynn.spa.catalogue.service.CatalogueVersionLogService;
import com.grandlynn.spa.catalogue.service.SmsService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"短信发送"}
)
@RestController
@RequestMapping({"/data-catalogue/v1/org/sms"})
public class SmsController {
   private static final Logger log = LoggerFactory.getLogger(SmsController.class);
   @Resource
   private SsoLoginConfig ssoLoginConfig;
   @Autowired
   private AuthAppUserService authAppUserService;
   @Autowired
   private SmsService smsService;
   @Resource
   private CatalogueVersionLogService catalogueVersionLogService;

   @ApiOperation(
      value = "发送短信",
      nickname = "sendMessage"
   )
   @PostMapping({"/sendMessage"})
   public Payload<SmsResponse> sendMessage(@RequestBody SmsRequest smsRequest) {
      log.info("收到短信内容{}", JSON.toJSONString(smsRequest));
      return new Payload(this.smsService.send(smsRequest));
   }

   @ApiOperation(
      value = "发送部门通知短信",
      nickname = "sendToDeptMessage"
   )
   @PostMapping({"/sendToDeptMessage"})
   public Payload<SmsResponse> sendToDeptMessage(@RequestBody SmsToDeptRequest smsToDeptRequest) {
      log.info("收到短信内容{}", JSON.toJSONString(smsToDeptRequest));
      return new Payload(this.smsService.sendToDept(smsToDeptRequest.getOrgId(), smsToDeptRequest.getCode(), smsToDeptRequest.getParams()));
   }

   @ApiOperation(
           value = "发送流程通知短信",
           nickname = "sendToFlowMessage"
   )
   @PostMapping({"/sendToFlowMessage"})
   public Payload<SmsResponse> sendToFlowMessage(@RequestBody SmsRequest smsRequest) {
      log.info("收到短信内容{}", JSON.toJSONString(smsRequest));
      return new Payload(this.smsService.sendToFlowMessage(smsRequest));
   }

   @ApiOperation(
      value = "查询短信记录列表",
      nickname = "findPage"
   )
   @GetMapping({"/findPage"})
   public Payload<PageBean<SmsDTO>> findPage(SmsQueryRequest request) {
      return new Payload(this.smsService.findPage(request));
   }

   public static String replaceParameters(String url, String[][] replacements) {
      String[][] var2 = replacements;
      int var3 = replacements.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String[] replacement = var2[var4];
         String paramName = replacement[0];
         String paramValue = replacement[1];
         String pattern = "(" + paramName + "=)[^&]+";
         Pattern regex = Pattern.compile(pattern);
         Matcher matcher = regex.matcher(url);
         url = matcher.replaceAll("$1" + paramValue);
      }

      return url;
   }

   @PostMapping({"/sendTest"})
   public Payload<Boolean> sendTest() {
      String beginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00";
      String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 23:59:59";
      List<CatalogueVersionLogTableDTO> orgPublish = this.catalogueVersionLogService.findAllOrgPublish(Timestamp.valueOf(beginTime), Timestamp.valueOf(endTime));
      if (CollUtil.isEmpty(orgPublish)) {
         log.info("===>SendPublishSmsJob==>定时任务=====》没有待发送短信的版本,任务终止==>");
         return new Payload(false);
      } else {
         orgPublish.stream().forEach((item) -> {
            this.smsService.sendToDept(item.getOrgId(), SmsEnum.CATALOGUE_PUBLISH_SMS.getCode(), Collections.singletonList(item.getTableCount().toString()));
         });
         return new Payload(true);
      }
   }
}

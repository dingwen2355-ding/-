package com.grandlynn.spa.catalogue.observer.impl;

import com.grandlynn.spa.catalogue.common.enums.SmsEnum;
import com.grandlynn.spa.catalogue.config.SsoLoginConfig;
import com.grandlynn.spa.catalogue.domain.request.SmsRequest;
import com.grandlynn.spa.catalogue.observer.MessageObserver;
import com.grandlynn.spa.catalogue.service.AuthAppUserService;
import com.grandlynn.spa.catalogue.utils.HttpUtils;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMSObserver implements MessageObserver {
   private static final Logger log = LoggerFactory.getLogger(SMSObserver.class);
   @Resource
   private SsoLoginConfig ssoLoginConfig;
   @Autowired
   private AuthAppUserService authAppUserService;

   public String sendMsg(SmsRequest smsRequest) {
      List<String> smsRequestParams = smsRequest.getParams();
      String smsRequestCode = smsRequest.getCode();
      String templateByCode = SmsEnum.getTemplateByCode(smsRequestCode);
      String format = null;
      if (smsRequestParams != null && smsRequestParams.size() > 0) {
         if (!smsRequestCode.equals("104") && !smsRequestCode.equals("204")) {
            if(smsRequestCode.equals("107")) {
               format = String.format(templateByCode, smsRequestParams.get(2),smsRequestParams.get(0), smsRequestParams.get(1));
            }else {
               if (!smsRequestCode.equals("103") && !smsRequestCode.equals("203") && !smsRequestCode.equals("106")) {
                  if (!smsRequestCode.equals("101") && !smsRequestCode.equals("102") && !smsRequestCode.equals("201") && !smsRequestCode.equals("201")) {
                     if (smsRequestCode.equals(SmsEnum.CATALOGUE_PUBLISH_SMS.getCode())) {
                        format = String.format(templateByCode, smsRequestParams.get(1));
                     } else if (!smsRequestCode.equals(SmsEnum.RESOURCE_DONE_SHARE_SMS_TO_DEPT.getCode()) && !smsRequestCode.equals(SmsEnum.DEMAND_DONE_SHARE_SMS_TO_DEPT.getCode())) {
                        if (smsRequestCode.equals(SmsEnum.RESOURCE_SHARE_SMS.getCode()) || smsRequestCode.equals(SmsEnum.DEMAND_SHARE_SMS.getCode())) {
                           format = String.format(templateByCode, smsRequestParams.get(0), smsRequestParams.get(1));
                        }
                     } else {
                        format = String.format(templateByCode, smsRequestParams.get(0), smsRequestParams.get(1));
                     }
                  } else {
                     format = String.format(templateByCode, smsRequestParams.get(1), smsRequestParams.get(0));
                  }
               } else {
                  format = String.format(templateByCode, smsRequestParams.get(0));
               }
            }
         } else {
            format = String.format(templateByCode, smsRequestParams.get(0), smsRequestParams.get(2));
         }
      }

      smsRequest.setMsg(format);
      // todo 本地测试要注释掉一下代码
      String appId = this.ssoLoginConfig.getClient_id();
      String appToken = this.authAppUserService.getAppToken();
      String urlGet = this.ssoLoginConfig.getApp_url() + "/api/squirrel-szbh/sms/sendSms?appId=aKzRzTZN&token=7b7872227e742825297c677b07674936451451&mobile=17280076267&msg=message";
      String[][] replacements = new String[][]{{"appId", appId}, {"token", appToken}, {"mobile", smsRequest.getMobile()}, {"msg", format}};
      urlGet = replaceParameters(urlGet, replacements);
      HttpUtils.initHttpClient(false, 5);
      log.info("短信发送请求url：{}", urlGet);
      String sendGet = HttpUtils.get(urlGet, (Map)null);
      return sendGet;
//      return "{\"code\":\"200\", \"payload\":\"发送成功\"}";
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
}

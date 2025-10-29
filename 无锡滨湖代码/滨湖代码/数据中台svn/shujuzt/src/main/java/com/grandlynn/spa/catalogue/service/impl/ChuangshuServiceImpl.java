package com.grandlynn.spa.catalogue.service.impl;

import com.grandlynn.spa.catalogue.domain.request.CheckedHouseRequest;
import com.grandlynn.spa.catalogue.service.ChuangshuService;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChuangshuServiceImpl implements ChuangshuService {
   private static final Logger log = LoggerFactory.getLogger(ChuangshuServiceImpl.class);
   @Resource
   private RestTemplate restTemplate;
   private static final String SECRET_KEY = "k7arJbaet4hG+Udd8KiF3g==";
   private static final String APP_NO = "ZL_APIUSER";
   private static final String URL = "https://zfzl.km.org.cn/api/ops/house/verification";

   public String checkedHouse(CheckedHouseRequest request) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json");
      HttpEntity<CheckedHouseRequest> httpEntity = new HttpEntity(request, headers);
      ResponseEntity<String> response = this.restTemplate.exchange("https://zfzl.km.org.cn/api/ops/house/verification", HttpMethod.POST, httpEntity, String.class, new Object[0]);
      log.info((String)response.getBody());
      return (String)response.getBody();
   }
}

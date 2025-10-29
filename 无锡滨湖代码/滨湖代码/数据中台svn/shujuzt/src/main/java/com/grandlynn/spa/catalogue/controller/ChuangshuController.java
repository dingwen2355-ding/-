package com.grandlynn.spa.catalogue.controller;

import com.grandlynn.spa.catalogue.domain.request.CheckedHouseRequest;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.service.ChuangshuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Authorize
@RestController
@Api(
   tags = {"创数POC新加接口"}
)
@RequestMapping({"/data-catalogue/v1/chuangshu"})
public class ChuangshuController {
   private static final Logger log = LoggerFactory.getLogger(ChuangshuController.class);
   @Resource
   private ChuangshuService chuangshuService;

   @ApiOperation(
      value = "房源核验",
      nickname = "checkedHouse"
   )
   @PostMapping({"/houseVerify"})
   public String checkedHouse(@RequestBody CheckedHouseRequest request) {
      return this.chuangshuService.checkedHouse(request);
   }
}

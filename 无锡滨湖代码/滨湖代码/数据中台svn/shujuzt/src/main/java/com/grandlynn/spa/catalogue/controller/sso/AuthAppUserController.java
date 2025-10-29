package com.grandlynn.spa.catalogue.controller.sso;

import com.grandlynn.spa.catalogue.service.AuthAppUserService;
import com.grandlynn.util.config.Payload;
import io.swagger.annotations.Api;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(
   tags = {"子系统应用数据接口"}
)
@RequestMapping({"/app"})
public class AuthAppUserController {
   private static final Logger log = LoggerFactory.getLogger(AuthAppUserController.class);
   @Resource
   private AuthAppUserService authAppUserService;

   @GetMapping({"/appUserList"})
   public Payload appUserList(@RequestParam("depId") String depId) {
      return new Payload(this.authAppUserService.appUserList(depId));
   }

   @GetMapping({"/appUserInfo"})
   public Payload appUserInfo(@RequestParam("account") String account) {
      return new Payload(this.authAppUserService.appUserInfo(account));
   }

   @GetMapping({"/departmentList"})
   public Payload departmentList(@RequestParam(value = "depId",required = false) String depId) {
      return new Payload(this.authAppUserService.departmentList(depId));
   }
}

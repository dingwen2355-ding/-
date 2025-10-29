package com.grandlynn.spa.catalogue.controller.sso;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.grandlynn.spa.catalogue.common.utils.PwdUtil;
import com.grandlynn.spa.catalogue.config.SsoLoginConfig;
import com.grandlynn.spa.catalogue.dto.sso.SsoUserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysOperationLogDO;
import com.grandlynn.spa.catalogue.security.RedisTokenProvider;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.security.TokenProvider;
import com.grandlynn.spa.catalogue.service.*;
import com.grandlynn.spa.catalogue.utils.KmsUtils;
import com.grandlynn.util.extension.ApplicationException;
import io.swagger.annotations.Api;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Api(
   tags = {"统一认证登录"}
)
@RequestMapping({"/auth"})
public class SsoAuthLoginController {
   private static final Logger log = LoggerFactory.getLogger(SsoAuthLoginController.class);
   @Resource
   private SsoLoginConfig ssoLoginConfig;
   @Resource
   private TokenProvider tokenProvider;
   @Resource
   private SysAdminUserService sysAdminUserService;
   @Resource
   private CatalogueOrgService orgService;
   @Resource
   private SysRoleService sysRoleService;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;
   @Resource
   private RedisTokenProvider redisTokenProvider;
   @Resource
   private SysOperationLogService operationLogService;

   private static final String CODE_ROUTE = "/oauth/authorize";
   private static final String TOKEN_ROUTE = "/oauth/token";

   @RequestMapping({"/code"})
   public ModelAndView getCode() {
      ModelAndView modelAndView = new ModelAndView();
      String codeUrl = this.ssoLoginConfig.getCodeUrl();
      log.info("[sso单点登录]获取授权码连接请求：{}", codeUrl);
      modelAndView.setViewName("redirect:" + codeUrl);
      return modelAndView;
   }

   @RequestMapping({"/login"})
   public ModelAndView login(String code, HttpServletRequest request) throws IOException {
      ModelAndView modelAndView = new ModelAndView();
      log.info("[sso单点登录]回调返回授权码：{}", code);
      String redirectUrl = "";
      if (code != null && !"".equals(code)) {
         Object codeToken = this.redisTemplate.opsForValue().get(this.redisTokenProvider.createRedisTokenKey(code));
         if (codeToken != null) {
            redirectUrl = this.ssoLoginConfig.getHome_url() + "/login?code=200&token=" + codeToken;
         } else {
            String loginUrl = this.getLoginUrl(code);
            log.info("[sso单点登录]请求地址：{}", loginUrl);
            HttpRequest rq = HttpRequest.post(loginUrl);
            HttpResponse response = rq.execute();
            String tokenInfo = response.body();
            log.info("[sso单点登录]返回token信息：{}", tokenInfo);
            SsoUserInfoDTO ssoUserInfoDTO = this.getUserInfo(tokenInfo);

            String token;
            try {
               token = this.systemUserLogin(ssoUserInfoDTO);
            } catch (ApplicationException var14) {
               var14.printStackTrace();
               log.info("登录失败，无权限访问系统 :{}", var14.getMessage());
               redirectUrl = this.ssoLoginConfig.getHome_url() + "/login?code=302&msg=" + URLEncoder.encode("无权限访问滨湖区公共数据资源共享门户，请联系管理员", "utf-8");
               modelAndView.setViewName("redirect:" + redirectUrl);
               return modelAndView;
            }

            request.getSession().setAttribute("Auth-Token", token);
            redirectUrl = this.ssoLoginConfig.getHome_url() + "/login?code=200&token=" + token;
            long exp = ssoUserInfoDTO.getData().getExp();
            this.redisTemplate.opsForValue().set(this.redisTokenProvider.createRedisTokenKey(code), token, exp, TimeUnit.SECONDS);
         }
      } else {
         redirectUrl = this.ssoLoginConfig.getCodeUrl();
      }

      log.info("[sso单点登录]重定向地址：{}", redirectUrl);
      modelAndView.setViewName("redirect:" + redirectUrl);
      return modelAndView;
   }

   public String systemUserLogin(SsoUserInfoDTO ssoUserInfoDTO) {
      SsoUserInfoDTO.Payload data = ssoUserInfoDTO.getData();
      SsoUserInfoDTO.SsoUser userInfo = data.getUserInfo();
      SysAdminUser ssoUser = new SysAdminUser();
      ssoUser.setLogin(userInfo.getAccount());
      ssoUser.setName(userInfo.getName());

      try {
         ssoUser.setPassword(PwdUtil.generate(ssoUser.getLogin() + "@1"));
      } catch (NoSuchAlgorithmException var8) {
         var8.printStackTrace();
      }

      ssoUser.setSsoid(String.valueOf(userInfo.getId()));
      ssoUser.setEmail(userInfo.getEmail());
      ssoUser.setTel(userInfo.getPhone());
      ssoUser.setToken(data.getAccessToken());
      SysAdminUser adminUser = this.sysAdminUserService.saveOAUser(ssoUser);
      adminUser.setRoles(this.sysAdminUserService.getUserRole(adminUser.getId()));
      adminUser.setOrgLimit(this.sysRoleService.orgLimitJudge(adminUser.getRoles()));
      adminUser.setOrgids(this.sysAdminUserService.getUserOrgId(adminUser.getId(), true));
      adminUser.setOrgs(this.orgService.getListByIds(adminUser.getOrgids()));
      adminUser.setPassword((String)null);
      SecurityUtil.setCurrentUser(adminUser);

      try {
         SysOperationLogDO operLog = new SysOperationLogDO();
         operLog.setStatus("200");
         operLog.setTitle("登录");
         operLog.setBusinessType("统一认证登录");
         if (adminUser != null) {
            operLog.setUserId(adminUser.getId());
            operLog.setUserName(adminUser.getName());
         }

         this.operationLogService.save(operLog);
         SysOperationLogDO byId = this.operationLogService.getById(operLog.getId());
         byId.setSm3Hmac(KmsUtils.sm3Hmac(operLog.getUserId() + operLog.getUserName() + operLog.getTitle() + operLog.getBusinessType() + operLog.getStatus() + operLog.getErrorMgs() + operLog.getCreatedTime().getTime()));
         this.operationLogService.updateById(byId);
      } catch (Exception var7) {
         log.info("单点登录日志", var7);
      }

      return this.tokenProvider.createTokenExp(adminUser, data.getExp());
   }

   private SsoUserInfoDTO getUserInfo(String tokenInfo) {
      SsoUserInfoDTO ssoUserInfoDTO = (SsoUserInfoDTO)JSONUtil.toBean(tokenInfo, SsoUserInfoDTO.class);
      if (ssoUserInfoDTO == null) {
         throw new ApplicationException("登录失败，获取用户信息失败");
      } else if (ssoUserInfoDTO.getCode() != 200) {
         throw new ApplicationException("登录失败," + ssoUserInfoDTO.getMsg());
      } else {
         return ssoUserInfoDTO;
      }
   }

   private String getCodeUrl() {
      return this.ssoLoginConfig.getSso_url() + CODE_ROUTE + "?client_id=" + this.ssoLoginConfig.getClient_id() + "&client_secret=" + this.ssoLoginConfig.getClient_secret() + "&redirect_uri=" + this.ssoLoginConfig.getRedirect_uri() + "&response_type=code&scope=" + this.ssoLoginConfig.getScope();
   }

   private String getLoginUrl(String code) {
      return this.ssoLoginConfig.getSso_url() + TOKEN_ROUTE + "?grant_type=" + this.ssoLoginConfig.getGrant_type() + "&client_id=" + this.ssoLoginConfig.getClient_id() + "&client_secret=" + this.ssoLoginConfig.getClient_secret() + "&code=" + code + "&redirect_uri=" + this.ssoLoginConfig.getRedirect_uri() + "&scope=" + this.ssoLoginConfig.getScope();
   }
}

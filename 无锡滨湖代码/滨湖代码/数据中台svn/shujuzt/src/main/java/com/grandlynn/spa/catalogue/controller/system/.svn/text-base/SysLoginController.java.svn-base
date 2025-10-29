package com.grandlynn.spa.catalogue.controller.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import com.grandlynn.spa.catalogue.common.enums.StateEnum;
import com.grandlynn.spa.catalogue.common.enums.user.LoginSourceEnum;
import com.grandlynn.spa.catalogue.common.utils.AesUtil;
import com.grandlynn.spa.catalogue.common.utils.LoginToAESUtils;
import com.grandlynn.spa.catalogue.common.utils.PwdUtil;
import com.grandlynn.spa.catalogue.config.SsoLoginConfig;
import com.grandlynn.spa.catalogue.dto.LoginSourceDTO;
import com.grandlynn.spa.catalogue.dto.sso.LogoutDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysOperationLogDO;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.security.AuthorizeUtil;
import com.grandlynn.spa.catalogue.security.RedisTokenProvider;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.security.TokenProvider;
import com.grandlynn.spa.catalogue.service.*;
import com.grandlynn.spa.catalogue.utils.IpUtils;
import com.grandlynn.spa.catalogue.utils.KmsUtils;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.extension.ApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Api(
   tags = {"登录-基础系统"}
)
public class SysLoginController {
   private static final Logger log = LogManager.getLogger(SysLoginController.class);
   @Resource
   private TokenProvider tp;
   @Resource
   private SysAdminUserService userService;
   @Resource
   private CatalogueOrgService orgService;
   @Resource
   private SysRoleService roleService;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;
   @Resource
   private SsoLoginConfig ssoLoginConfig;
   @Resource
   private RedisTokenProvider redisTokenProvider;
   @Value("${server.token.perfix:platform}")
   private String perfix;
   @Resource
   private SysOperationLogService operationLogService;

   @Autowired
   private KmsService kmsService;

   public static final String IP_LOGIN_FAIL = "ip_login_f";
   private static final Integer LOGIN_FAIL_SECOND = 600;
   public static final String REDIS_JWTTOKEN_KEY = "sol-or-token:platform:";
   public static final String SYS_AUTH_ADMIN_TOKEN_KEY = "sys-auth-admin-token:";

   @ApiOperation("用户登录")
   @PostMapping({"/login"})
   public Payload<String> login(@RequestBody SysAdminUser sysAdminUser) throws Exception {
      String ip = sysAdminUser.getLogin();
      Integer loginFail = (Integer)this.redisTemplate.opsForValue().get(AuthorizeUtil.getUserRedisKey(IP_LOGIN_FAIL + ":" + ip));
      if (loginFail != null && loginFail >= 5) {
         return new Payload(ResultCodeEnum.LOGIN_TOO_MUCH);
      } else {
         if (loginFail == null) {
            loginFail = 0;
         }

         loginFail = loginFail + 1;
         SysAdminUser user = this.userService.getOne((new QueryWrapper<SysAdminUser>()).lambda().eq(SysAdminUser::getLogin, sysAdminUser.getLogin()));
         if (user == null) {
            this.redisTemplate.opsForValue().set(AuthorizeUtil.getUserRedisKey(IP_LOGIN_FAIL + ":" + ip), loginFail, (long)LOGIN_FAIL_SECOND, TimeUnit.SECONDS);
            return new Payload(ResultCodeEnum.ERROR_ACCOUNT_OR_PASSWORD);
         } else if (StateEnum.DISABLE.getCode().equals(user.getStatus())) {
            return new Payload(ResultCodeEnum.ACCOUNT_FREEZE);
         } else if (!PwdUtil.verify(this.decodePassword(sysAdminUser.getPassword()), user.getPassword())) {
            this.redisTemplate.opsForValue().set(AuthorizeUtil.getUserRedisKey(IP_LOGIN_FAIL + ":" + ip), loginFail, (long)LOGIN_FAIL_SECOND, TimeUnit.SECONDS);
            return new Payload(ResultCodeEnum.ERROR_ACCOUNT_OR_PASSWORD);
         }
         //todo ----发布需打开 begin-----
         else if (user.getAuthorizeSm3Hmac() == null && !user.getLogin().equals("admin")) {
            return new Payload(ResultCodeEnum.AUTHORIZE_SM3HMAC);
         }
         else if (!this.kmsService.authorizeSm3Hmac(user)) {
            return new Payload(ResultCodeEnum.AUTHORIZE_SM3HMAC);
         }
         //todo ----发布需打开 end-----
         else {
            Object loginKey = this.redisTemplate.opsForValue().get(SYS_AUTH_ADMIN_TOKEN_KEY + this.perfix + ":" + sysAdminUser.getLogin());
            if (ObjectUtil.isNotNull(loginKey)) {
               this.redisTemplate.delete(REDIS_JWTTOKEN_KEY + loginKey.toString());
               this.redisTemplate.delete(SYS_AUTH_ADMIN_TOKEN_KEY + this.perfix + ":login-source:" + loginKey.toString());
            }

            user.setRoles(this.userService.getUserRole(user.getId()));
            user.setOrgids(this.userService.getUserOrgId(user.getId(), true));
            user.setOrgs(this.orgService.getListByIds(user.getOrgids()));
            user.setOrgLimit(this.roleService.orgLimitJudge(user.getRoles()));
            user.setPassword(null);
            if (user.getEmail() != null && !user.getEmail().equals("")) {
               user.setEmail(LoginToAESUtils.encrypt(user.getEmail()));
            }

            if (user.getTel() != null && !user.getTel().equals("")) {
               user.setTel(LoginToAESUtils.encrypt(user.getTel()));
            }

            SecurityUtil.setCurrentUser(user);

            try {
               SysOperationLogDO operLog = new SysOperationLogDO();
               operLog.setStatus("200");
               operLog.setTitle("登录");
               operLog.setBusinessType("系统登录");
               if (user != null) {
                  operLog.setUserId(user.getId());
                  operLog.setUserName(user.getName());
               }

               String sm3Hmac = KmsUtils.sm3Hmac(operLog.getUserId() + operLog.getUserName() + operLog.getTitle() + operLog.getBusinessType() + operLog.getStatus() + operLog.getErrorMgs() + operLog.getCreatedTime().getTime());
               operLog.setSm3Hmac(sm3Hmac);
               this.operationLogService.save(operLog);
            } catch (Exception var10) {
               log.info("登录日志记录", var10);
            }

            try {
               ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
               HttpServletRequest request = attributes.getRequest();
               log.info("登录日志[登录时间：{}],[登录账号:{}],[IP：{}]", DateUtil.now(), sysAdminUser.getLogin(), IpUtils.getIpAddr(request));
            } catch (Exception var9) {
               log.error("记录登录信息异常");
               var9.printStackTrace();
            }

            return new Payload(this.tp.createToken(user));
         }
      }
   }

   private String decodePassword(String loginPassword) {
      String code = null;

      try {
         code = LoginToAESUtils.dencrypt(loginPassword);
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      String[] password = StringUtils.defaultString(code, "").split("_");
      if (null == password[0]) {
         throw new ApplicationException(ResultCodeEnum.USER_PASSWORD_ERROR);
      } else {
         long requestTime = Long.parseLong(password[1]);
         long currTime = System.currentTimeMillis();
         if (currTime - requestTime > 600000L) {
            throw new ApplicationException(ResultCodeEnum.LOGIN_INVALID);
         } else {
            return password[0];
         }
      }
   }

   @GetMapping({"/token/validate"})
   @ApiOperation("验证token是否有效")
   public Payload<Boolean> validateToken() {
      return SecurityUtil.currentUser() != null ? new Payload(true) : new Payload(false);
   }

   @Authorize
   @GetMapping({"/current/user"})
   @ApiOperation("获取当前登录用户的信息")
   public Payload<SysAdminUser> currentUser() {
      SysAdminUser user = (SysAdminUser)this.userService.getOne((new QueryWrapper<SysAdminUser>()).lambda().eq(SysAdminUser::getLogin, SecurityUtil.currentUser().getLogin()));
      user.setRoles(this.userService.getUserRole(user.getId()));
      user.setOrgids(this.userService.getUserOrgId(user.getId(), true));
      user.setOrgs(this.orgService.getListByIds(user.getOrgids()));
      user.setOrgLimit(this.roleService.orgLimitJudge(user.getRoles()));
      user.setPassword((String)null);
      SecurityUtil.setCurrentUser(user);
      return new Payload(SecurityUtil.currentUser());
   }

   @ApiOperation("用户退出")
   @GetMapping({"/logout"})
   public Payload<LogoutDTO> logout(HttpServletRequest request, HttpServletResponse servletResponse) {
      LogoutDTO logoutDTO = new LogoutDTO();

      try {
         String token = request.getHeader("Auth-Token");
         log.info("退出token：{}", token);
         if (token != null) {
            this.redisTokenProvider.clearToken(token);
            this.redisTokenProvider.clearToken(REDIS_JWTTOKEN_KEY + token);
         }

         SysAdminUser sysAdminUser = SecurityUtil.currentUser();
         String redirectUrl;
         if (sysAdminUser != null) {
            try {
               SysOperationLogDO operLog = new SysOperationLogDO();
               operLog.setStatus("200");
               operLog.setTitle("登录");
               operLog.setBusinessType("退出登录");
               if (sysAdminUser != null) {
                  operLog.setUserId(sysAdminUser.getId());
                  operLog.setUserName(sysAdminUser.getName());
               }

               this.operationLogService.save(operLog);
            } catch (Exception var10) {
               log.info("退出登录日志记录", var10);
            }

            redirectUrl = this.redisTokenProvider.createRedisTokenKey(sysAdminUser.getLogin());
            this.redisTemplate.delete(redirectUrl);
         }

         redirectUrl = this.ssoLoginConfig.getLogout();
         if (StrUtil.isNotEmpty(token)) {
            String loginSourceKey = SYS_AUTH_ADMIN_TOKEN_KEY + this.perfix + ":login-source:" + token;
            Object loginSourceObject = this.redisTemplate.opsForValue().get(loginSourceKey);
            if (loginSourceObject != null) {
               LoginSourceDTO loginSourceDTO = (LoginSourceDTO)JSONObject.parseObject(loginSourceObject.toString(), LoginSourceDTO.class);
               logoutDTO.setLoginSource(loginSourceDTO.getLoginSource());
               if (loginSourceDTO.getLoginSource() != null && LoginSourceEnum.UNIFY.getCode().equals(loginSourceDTO.getLoginSource())) {
                  redirectUrl = this.ssoLoginConfig.getLogout_url();
               }
            }
         }

         logoutDTO.setLogoutUrl(redirectUrl);
         logoutDTO.setSsoUrl(redirectUrl);
         log.info("退出登录返参:{}", JSONUtil.toJsonStr(logoutDTO));
         if (token != null) {
            this.redisTemplate.delete(REDIS_JWTTOKEN_KEY + token);
            this.redisTemplate.delete(SYS_AUTH_ADMIN_TOKEN_KEY + this.perfix + ":login-source:" + token);
         }

         return new Payload(logoutDTO);
      } catch (Exception var11) {
         var11.printStackTrace();
         throw new ApplicationException("退出注销失败");
      }
   }

   @RequestMapping({"/index"})
   public ModelAndView getCode() {
      ModelAndView modelAndView = new ModelAndView();
      String codeUrl = this.ssoLoginConfig.getCodeUrl();
      log.info("统一认证获取授权码连接请求：{}", codeUrl);
      modelAndView.setViewName("redirect:" + codeUrl);
      return modelAndView;
   }


}

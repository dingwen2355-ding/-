package com.grandlynn.spa.catalogue.controller.sso;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.grandlynn.spa.catalogue.common.enums.ResultCodeEnum;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.security.TokenProvider;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.SysAdminUserOrgService;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;
import com.grandlynn.spa.catalogue.service.SysRoleService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.extension.ApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(
   tags = {"登录-外部系统"}
)
@RequestMapping({"/sso"})
public class SsoLoginController {
   private static final Logger log = LoggerFactory.getLogger(SsoLoginController.class);
   @Resource
   private TokenProvider tp;
   @Resource
   private SysAdminUserService sysAdminUserService;
   @Resource
   private CatalogueOrgService orgService;
   @Resource
   private SysAdminUserOrgService sysAdminUserOrgService;
   @Resource
   private SysRoleService sysRoleService;
   @Value("${shunde.sso}")
   private String ssoUrl;

   @ApiOperation("用户单点登录")
   @PostMapping({"/login"})
   public Payload<String> ssoLogin(@RequestParam("token") String token) {
      String localToken = null;

      try {
         String url = this.ssoUrl + "&token=" + token;
         log.info("OA系统单点登录,请求:{}", url);
         String rsp = HttpUtil.get(url);
         log.info("OA系统单点登录,xml报文:{}", rsp);
         this.validateXml(rsp);
         SysAdminUser oaUser = this.convert(rsp);
         log.info("OA系统单点登录,xml转换对象:{}", oaUser);
         SysAdminUser adminUser = this.sysAdminUserService.saveOAUser(oaUser);
         adminUser.setOrgids(oaUser.getOrgids());
         this.sysAdminUserOrgService.saveOAUserOrg(adminUser);
         return new Payload(this.localLogin(adminUser));
      } catch (Exception var7) {
         var7.printStackTrace();
         log.error("OA系统单点登录,失败{}", var7.getMessage());
         throw new ApplicationException("OA登陆失败");
      }
   }

   @ApiOperation("用户单点登录(测试)")
   @PostMapping({"/login/test"})
   public Payload<String> ssoLoginTest(@RequestParam("xml") String xml) {
      try {
         this.validateXml(xml);
         SysAdminUser oaUser = this.convert(xml);
         log.info("OA系统单点登录,xml转换对象:{}", oaUser);
         SysAdminUser adminUser = this.sysAdminUserService.saveOAUser(oaUser);
         adminUser.setOrgids(oaUser.getOrgids());
         this.sysAdminUserOrgService.saveOAUserOrg(adminUser);
         return new Payload(this.localLogin(adminUser));
      } catch (Exception var4) {
         var4.printStackTrace();
         log.error("OA系统单点登录,失败{}", var4.getMessage());
         throw new ApplicationException("OA登陆失败");
      }
   }

   private String localLogin(SysAdminUser oaUser) {
      oaUser.setRoles(this.sysAdminUserService.getUserRole(oaUser.getId()));
      oaUser.setOrgLimit(this.sysRoleService.orgLimitJudge(oaUser.getRoles()));
      oaUser.setOrgids(this.sysAdminUserService.getUserOrgId(oaUser.getId(), true));
      oaUser.setOrgs(this.orgService.getListByIds(oaUser.getOrgids()));
      oaUser.setPassword((String)null);
      SecurityUtil.setCurrentUser(oaUser);
      return this.tp.createToken(oaUser);
   }

   private SysAdminUser convert(String rsp) {
      SysAdminUser user = new SysAdminUser();
      JSONObject row = XML.toJSONObject(rsp).getJSONObject("dataset").getJSONObject("userRow");
      user.setSsoid(row.getStr("userId"));
      user.setName(row.getStr("userName"));
      user.setLogin(row.getStr("userId"));
      user.setTel(row.getStr("userMobile"));
      JSONObject orgjson = row.getJSONObject("orgRows");
      Object orgobj = orgjson.get("orgRow");
      if (orgobj instanceof JSONObject) {
         JSONObject org = (JSONObject)orgobj;
         user.setOrgids(new Long[]{org.getLong("orgId")});
      } else if (orgobj instanceof JSONArray) {
         JSONArray org = (JSONArray)orgobj;
         Long[] orgs = new Long[org.size()];

         for(int i = 0; i < org.size(); ++i) {
            orgs[i] = org.getJSONObject(i).getLong("orgId");
         }

         user.setOrgids(orgs);
      } else {
         log.error("OA系统单点登录,xml报文解析失败,部门信息无法识别");
      }

      return user;
   }

   private void validateXml(String rsp) {
      Object dataset = XML.toJSONObject(rsp).get("dataset");
      String dataStr = String.valueOf(null == dataset ? "" : dataset);
      if (dataStr.length() == 0) {
         throw new ApplicationException(ResultCodeEnum.UNAUTHORIZED);
      }
   }
}

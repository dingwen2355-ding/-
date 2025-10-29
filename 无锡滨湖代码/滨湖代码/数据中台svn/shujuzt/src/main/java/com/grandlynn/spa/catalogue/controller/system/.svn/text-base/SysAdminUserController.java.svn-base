package com.grandlynn.spa.catalogue.controller.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grandlynn.spa.catalogue.common.utils.PwdUtil;
import com.grandlynn.spa.catalogue.domain.request.SysAdminUserQueryRequest;
import com.grandlynn.spa.catalogue.dto.PasswordDTO;
import com.grandlynn.spa.catalogue.dto.SysAdminUserDTO;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.security.AuthorizeUtil;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.KmsService;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"管理端账户信息"}
)
@RestController
@RequestMapping({"sysAdminUser"})
public class SysAdminUserController {
   @Resource
   private SysAdminUserService service;
   @Autowired
   private KmsService kmsService;
   @Resource
   private RedisTemplate<String, Object> redisTemplate;
   @Authorize
   @ApiOperation("保存")
   @PutMapping
   public Payload<Void> save(@RequestBody @Valid SysAdminUser user) throws NoSuchAlgorithmException {
      this.service.saveOne(user);
      return new Payload();
   }

   @Authorize
   @ApiOperation("保存当前登陆用户")
   @PutMapping({"/current"})
   public Payload<Void> saveCurrent(@RequestBody @Valid SysAdminUser user) throws NoSuchAlgorithmException {
      Long sysId = SecurityUtil.currentUserId();
      if (sysId != null) {
         user.setId(sysId);
      }

      if (user.getId() != null) {
         if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(PwdUtil.generate(user.getPassword()));
         }

         if (user.getId() != null) {
            this.service.updateById(user);
         }
      }

      return new Payload();
   }

   @Authorize
   @ApiOperation("删除")
   @DeleteMapping
   public Payload<Void> delete(@RequestParam Long id) {
      this.service.removeById(id);
      return new Payload();
   }

   @Authorize
   @ApiOperation("激活,停用")
   @PostMapping({"enable"})
   @ApiImplicitParams({@ApiImplicitParam(
   name = "id",
   value = "id",
   type = "Long"
), @ApiImplicitParam(
   name = "status",
   value = "状态(0启用,1禁用)",
   type = "Integer"
)})
   public Payload<Void> enable(@RequestParam Long id, @RequestParam Integer status) {
      if (SecurityUtil.currentUser().getId().equals(id)) {
         throw new ApplicationException("无法操作本人账户");
      } else {
         SysAdminUser user = new SysAdminUser();
         user.setId(id);
         user.setStatus(status);
         this.service.updateById(user);
         return new Payload();
      }
   }

   @Authorize
   @ApiOperation("重置密码")
   @PostMapping({"reset/pwd"})
   public Payload<Void> resetPassword(@RequestBody @Valid PasswordDTO passwordDTO) throws NoSuchAlgorithmException {
      SysAdminUser user = SecurityUtil.currentUser();
      Assert.notNull(user, "获取用户信息异常");
      Assert.notNull(passwordDTO, "参数不能为空");
      SysAdminUser adminUser = service.getById( passwordDTO.getId());
      Assert.notNull(adminUser, "id参数异常");
      adminUser.setPassword(PwdUtil.generate(passwordDTO.getPassword()));
      this.service.updateById(adminUser);
      redisTemplate.delete(AuthorizeUtil.getUserRedisKey(SysLoginController.IP_LOGIN_FAIL + ":" + adminUser.getLogin()));
      return new Payload();
   }

   @ApiOperation("重置密码")
   @GetMapping({"/resetPwd/{id}"})
   public Payload<Void> resetPwd(@PathVariable("id") Integer id) throws NoSuchAlgorithmException {
      Assert.notNull(id, "参数id不能为空");
      QueryWrapper<SysAdminUser> sysAdminUserQueryWrapper = new QueryWrapper();
      sysAdminUserQueryWrapper.eq("id", id);
      SysAdminUser adminUser = (SysAdminUser)this.service.getOne(sysAdminUserQueryWrapper);
      Assert.notNull(adminUser, "id参数异常");
      adminUser.setPassword(PwdUtil.generate("WX@bigdata0510"));
      this.service.updateById(adminUser);
      redisTemplate.delete(AuthorizeUtil.getUserRedisKey(SysLoginController.IP_LOGIN_FAIL + ":" + adminUser.getLogin()));
      return new Payload();
   }

   @ApiOperation("明细")
   @GetMapping
   public Payload<SysAdminUser> detail(@RequestParam Integer id) {
      SysAdminUser user = (SysAdminUser)this.service.getById(id);
      user.setRoles(this.service.getUserRole(user.getId()));
      user.setOrgids(this.service.getUserOrgId(user.getId(), true));
      return new Payload(user);
   }

   @Authorize
   @ApiOperation("分页查询")
   @PostMapping({"page"})
   public Payload<PageBean<SysAdminUserDTO>> querySysAdminUserByPage(@RequestBody SysAdminUserQueryRequest request) {
      return new Payload(this.service.page(request));
   }

   @ApiOperation(
      value = "根据id查询信息",
      nickname = "getMobileById"
   )
   @GetMapping({"/getMobileById/{id}"})
   public Payload<String> getMobileById(@PathVariable Long id) {
      return new Payload(this.service.getMobileById(id));
   }

   @ApiOperation("查询菜单权限")
   @GetMapping({"findMenuAuthority"})
   public Payload<List<UserInfoDTO>> findMenuAuthority(@RequestParam(value = "userId",required = false) Long userId, @RequestParam("menuId") Long menuId) {
      return new Payload(this.service.findMenuAuthority(userId, menuId));
   }

   @ApiOperation("查询菜单权限")
   @GetMapping({"sm3Hmac"})
   public Payload<Boolean> sm3Hmac() {
      return new Payload(this.kmsService.sm3HmacAllUser());
   }
}

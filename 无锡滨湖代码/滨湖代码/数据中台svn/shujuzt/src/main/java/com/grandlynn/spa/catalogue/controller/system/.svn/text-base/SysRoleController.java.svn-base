package com.grandlynn.spa.catalogue.controller.system;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.SysRoleAuthorizeRequest;
import com.grandlynn.spa.catalogue.domain.request.SysRoleQueryRequest;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysAdminUserRole;
import com.grandlynn.spa.catalogue.entity.SysRole;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.SysAdminUserRoleService;
import com.grandlynn.spa.catalogue.service.SysRoleService;
import com.grandlynn.util.config.Payload;
import com.grandlynn.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
   tags = {"管理端角色信息"}
)
@RestController
@RequestMapping({"sysRole"})
public class SysRoleController {
   @Autowired
   private SysRoleService sysRoleService;
   @Autowired
   private SysAdminUserRoleService sysAdminUserRoleService;

   @Authorize
   @ApiOperation("保存")
   @PutMapping
   public Payload<Void> save(@RequestBody @Valid SysRole sysRole) {
      this.sysRoleService.saveOne(sysRole);
      return new Payload();
   }

   @Authorize
   @ApiOperation("删除")
   @DeleteMapping
   public Payload<Void> delete(@RequestParam Long id) {
      this.sysRoleService.deleteOne(id);
      return new Payload();
   }

   @Authorize
   @ApiOperation("授权")
   @PostMapping({"authorize"})
   public Payload<Void> authorize(@RequestBody @Valid SysRoleAuthorizeRequest request) {
      request.setSolSystem(request.getSolSystem() != null ? request.getSolSystem() : 1);
      this.sysRoleService.authorize(request);
      return new Payload();
   }

   @Authorize
   @ApiOperation("详情查询")
   @GetMapping({"one"})
   public Payload<SysRole> getOne(@RequestParam Integer id) {
      return new Payload(this.sysRoleService.getById(id));
   }

   @Authorize
   @ApiOperation("列表查询")
   @GetMapping({"all"})
   public Payload<List<SysRole>> getAll() {
      return new Payload(this.sysRoleService.list((new QueryWrapper<SysRole>()).lambda().select(BaseEntity::getId, SysRole::getName)));
   }

   @Authorize
   @ApiOperation("分页查询")
   @PostMapping({"page"})
   public Payload<PageBean<SysRole>> page(@RequestBody SysRoleQueryRequest request) {
      PageUtil.page(request.getPage(), request.getSize());
      LambdaQueryWrapper<SysRole> queryWrapper = (new QueryWrapper<SysRole>()).lambda().orderByDesc(SysRole::getCanDelete).orderByDesc(BaseEntity::getUpdatedTime);
      queryWrapper.like(StrUtil.isNotBlank(request.getRoleName()), SysRole::getName, request.getRoleName());
      return new Payload(new PageBean(this.sysRoleService.list(queryWrapper)));
   }

   @Authorize
   @ApiOperation("获取角色下的所有用户信息")
   @GetMapping({"/{roleId}/users"})
   public Payload getUsersByRoleId(@PathVariable("roleId") Integer roleId) {
      return new Payload(this.sysRoleService.getUsersByRoleId(roleId));
   }

   @Authorize
   @ApiOperation("登录用户角色信息")
   @GetMapping({"roles"})
   public Payload<List<SysRole>> getUserRoles() {
      SysAdminUser user = SecurityUtil.currentUser();
      List<SysAdminUserRole> sysAdminUserRoleList = this.sysAdminUserRoleService.list((new QueryWrapper<SysAdminUserRole>()).lambda().eq(SysAdminUserRole::getUserId, user.getId()));
      List<SysRole> roles = new ArrayList();
      if (!CollectionUtils.isEmpty(sysAdminUserRoleList)) {
         Iterator var4 = sysAdminUserRoleList.iterator();

         while(var4.hasNext()) {
            SysAdminUserRole sysAdminUserRole = (SysAdminUserRole)var4.next();
            roles.add(this.sysRoleService.selectOne(sysAdminUserRole.getRoleId()));
         }
      }

      return new Payload(roles);
   }

   @Authorize
   @ApiOperation("获取部门角色下的所有用户信息")
   @GetMapping({"/{orgId}/{roleId}/users"})
   public Payload getUsersByOrgRoleId(@PathVariable("orgId") Integer orgId, @PathVariable("roleId") Integer roleId) {
      return new Payload(this.sysRoleService.getUsersByOrgRoleId(orgId, roleId));
   }

   @Authorize
   @ApiOperation("获取角色下的所有用户信息")
   @GetMapping({"/{roleId}/usersInfo"})
   public Payload getUsersInfoByRoleId(@PathVariable("roleId") Integer roleId) {
      return new Payload(this.sysRoleService.getUsersInfoByRoleId(roleId));
   }

}

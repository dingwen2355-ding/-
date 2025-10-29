package com.grandlynn.spa.catalogue.controller.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.grandlynn.spa.catalogue.dto.SysMenuDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysAdminUserRole;
import com.grandlynn.spa.catalogue.entity.SysMenu;
import com.grandlynn.spa.catalogue.entity.SysRoleMenu;
import com.grandlynn.spa.catalogue.security.Authorize;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.SysAdminUserRoleService;
import com.grandlynn.spa.catalogue.service.SysMenuService;
import com.grandlynn.spa.catalogue.service.SysRoleMenuService;
import com.grandlynn.spa.catalogue.service.SysRoleService;
import com.grandlynn.util.config.Payload;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(
   tags = {"管理端菜单信息"}
)
@RestController
@RequestMapping({"sysMenu"})
public class SysMenuController {
   @Resource
   private SysMenuService sysMenuService;
   @Resource
   private SysAdminUserRoleService sysAdminUserRoleService;
   @Resource
   private SysRoleMenuService sysRoleMenuService;
   @Resource
   private SysRoleService sysRoleService;

   @Authorize
   @ApiOperation("保存")
   @PutMapping
   public Payload<Void> save(@RequestBody @Valid SysMenu sysMenu) {
      sysMenu.setSolSystem(sysMenu.getSolSystem() != null ? sysMenu.getSolSystem() : 1);
      this.sysMenuService.saveOne(sysMenu);
      return new Payload();
   }

   @Authorize
   @ApiOperation("删除")
   @DeleteMapping
   public Payload<Void> delete(@RequestParam Long id) {
      this.sysMenuService.deleteOne(id);
      return new Payload();
   }

   @Authorize
   @ApiOperation("查询详情")
   @GetMapping({""})
   public Payload<SysMenu> detail(@RequestParam Long id) {
      return new Payload(this.sysMenuService.getById(id));
   }

   @Authorize
   @ApiOperation("菜单列表查询(当前用户菜单)")
   @GetMapping({"/tree"})
   public Payload<List<SysMenuDTO>> tree(@RequestParam(value = "solSystem",defaultValue = "1") Integer solSystem) {
      SysAdminUser user = SecurityUtil.currentUser();
      List<Long> menuIdList = Lists.newArrayList();
      List<SysAdminUserRole> sysAdminUserRoleList = this.sysAdminUserRoleService.list((new QueryWrapper<SysAdminUserRole>()).lambda().eq(SysAdminUserRole::getUserId, user.getId()));
      if (CollectionUtils.isNotEmpty(sysAdminUserRoleList)) {
         List<Long> roleIdList = (List)sysAdminUserRoleList.stream().map(SysAdminUserRole::getRoleId).distinct().collect(Collectors.toList());
         List<SysRoleMenu> sysRoleMenuList = this.sysRoleMenuService.list((new QueryWrapper<SysRoleMenu>()).in("role_id", roleIdList));
         if (CollectionUtils.isNotEmpty(sysRoleMenuList)) {
            menuIdList = (List)sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
         }
      }

      return new Payload(this.sysMenuService.findList((List)menuIdList, solSystem));
   }

   @Authorize
   @ApiOperation("菜单列表查询(所有菜单)")
   @GetMapping({"/tree/all"})
   public Payload<List<SysMenuDTO>> treeAll(@RequestParam(value = "solSystem",defaultValue = "1") Integer solSystem) {
      return new Payload(this.sysMenuService.findList(solSystem));
   }

   @Authorize
   @ApiOperation("展示角色的授权信息")
   @GetMapping({"tree/role"})
   public Payload<List<SysMenuDTO>> treeRole(@RequestParam("id") Long id, @RequestParam(value = "solSystem",defaultValue = "1") Integer solSystem) {
      List<SysRoleMenu> sysRoleMenuList = this.sysRoleMenuService.list((new QueryWrapper<SysRoleMenu>()).lambda().eq(SysRoleMenu::getRoleId, id));
      List<Long> menuIdList = (List)sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
      return new Payload(this.sysMenuService.authorizeInfo(menuIdList, solSystem));
   }

}

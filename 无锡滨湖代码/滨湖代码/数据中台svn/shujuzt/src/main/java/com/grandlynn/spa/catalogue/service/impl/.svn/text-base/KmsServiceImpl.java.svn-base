package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import java.util.stream.Collectors;

import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysRole;
import com.grandlynn.spa.catalogue.entity.SysRoleMenu;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.service.KmsService;
import com.grandlynn.spa.catalogue.service.SysAdminUserService;
import com.grandlynn.spa.catalogue.service.SysRoleMenuService;
import com.grandlynn.spa.catalogue.utils.KmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KmsServiceImpl implements KmsService {
   private static final Logger log = LoggerFactory.getLogger(KmsServiceImpl.class);
   @Autowired
   private SysAdminUserService sysAdminUserService;
   @Autowired
   private SysRoleMenuService sysRoleMenuService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean sm3HmacAllUser() {
      List<SysAdminUser> users = this.sysAdminUserService.list();
      for (SysAdminUser user : users) {
         String authorizeSm3Hmac = KmsUtils.sm3Hmac(queryUserAuthorize(user));
         user.setAuthorizeSm3Hmac(authorizeSm3Hmac);
      }
      return this.sysAdminUserService.updateBatchById(users);
   }

   public String sm3HmacByUser(Long userId) {
      SysAdminUser user = this.sysAdminUserService.getById(userId);
      return KmsUtils.sm3Hmac(this.queryUserAuthorize(user));
   }

   public boolean authorizeSm3Hmac(SysAdminUser user) {
      if (user.getLogin().equals("admin")) {
         return true;
      } else {
         String authorizeSm3Hmac = KmsUtils.sm3Hmac(this.queryUserAuthorize(user));
         return user.getAuthorizeSm3Hmac().equals(authorizeSm3Hmac);
      }
   }

   private String queryUserAuthorize(SysAdminUser user) {
      StringBuilder roleUser = new StringBuilder();
      List<SysRole> userRole = this.sysAdminUserService.getUserRole(user.getId());
      List<Long> roleId = userRole.stream().map(BaseEntity::getId).distinct().sorted().collect(Collectors.toList());
      roleUser.append(user.getId());
      for (Long aLong : roleId) {
         roleUser.append(aLong);
      }

      LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.in(SysRoleMenu::getRoleId, roleId);
      List<SysRoleMenu> roleMenuList = this.sysRoleMenuService.list(queryWrapper);
      List<Long> menuIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().sorted().collect(Collectors.toList());
      for (Long menuId : menuIds)
         roleUser.append(menuId);

      return roleUser.toString();
   }

}

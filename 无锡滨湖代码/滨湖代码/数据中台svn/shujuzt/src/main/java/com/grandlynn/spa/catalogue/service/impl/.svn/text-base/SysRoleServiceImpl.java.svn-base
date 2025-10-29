package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.SysRoleEnum;
import com.grandlynn.spa.catalogue.domain.request.SysRoleAuthorizeRequest;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysMenu;
import com.grandlynn.spa.catalogue.entity.SysRole;
import com.grandlynn.spa.catalogue.entity.SysRoleMenu;
import com.grandlynn.spa.catalogue.mapper.SysRoleMapper;
import com.grandlynn.spa.catalogue.service.*;
import com.grandlynn.util.extension.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
   @Resource
   private SysMenuService sysMenuService;
   @Resource
   private SysRoleMenuService sysRoleMenuService;
   @Resource
   private SysAdminUserRoleService sysAdminUserRoleService;
   @Lazy
   @Autowired
   private KmsService kmsService;

   public void saveOne(SysRole sysRole) {
      SysRole role = this.getOne((new QueryWrapper<SysRole>()).lambda().eq(SysRole::getName, sysRole.getName()));
      if (null != role && !role.getId().equals(sysRole.getId()) && role.getName().equals(sysRole.getName())) {
         throw new ApplicationException("角色名已存在!");
      } else {
         this.saveOrUpdate(sysRole);
      }
   }

   public void deleteOne(Long id) {
      boolean exists = this.sysAdminUserRoleService.existsUser(id);
      if (exists) {
         throw new ApplicationException("已关联用户,不允许删除!");
      } else {
         this.removeById(id);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void authorize(SysRoleAuthorizeRequest request) {
      Long roleId = request.getRoleId();
      LambdaQueryWrapper<SysMenu> queryWrapperMenu = (new QueryWrapper()).lambda();
      queryWrapperMenu.orderByAsc(BaseEntity::getId);
      queryWrapperMenu.eq(SysMenu::getSolSystem, request.getSolSystem());
      List<SysMenu> list = this.sysMenuService.list(queryWrapperMenu);
      List<Long> ids = new ArrayList();
      list.forEach((l) -> {
         ids.add(l.getId());
      });
      LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(SysRoleMenu::getRoleId, request.getRoleId());
      queryWrapper.in(SysRoleMenu::getMenuId, ids);
      this.sysRoleMenuService.remove(queryWrapper);
      if (!CollUtil.isEmpty(request.getMenuIdList())) {
         List<SysRoleMenu> rms = (List)request.getMenuIdList().stream().map((menu) -> {
            return new SysRoleMenu(roleId, menu);
         }).collect(Collectors.toList());
         this.sysRoleMenuService.saveBatch(rms);
         this.kmsService.sm3HmacAllUser();
      }
   }

   public Boolean orgLimitJudge(List<SysRole> roles) {
      if (CollUtil.isEmpty(roles))
         return Boolean.valueOf(true);
      for (SysRole role : roles) {
         if ((new Long(SysRoleEnum.ZSJ_ADMIN.getCode().intValue())).equals(role.getId()) || (new Long(SysRoleEnum.ADMIN
                 .getCode().intValue())).equals(role.getId()))
            return Boolean.valueOf(false);
      }
      return Boolean.valueOf(true);
   }

   public List<Integer> getUsersByRoleId(Integer roleId) {
      return (List)(roleId == null ? new ArrayList() : this.sysAdminUserRoleService.getUsersByRoleId(roleId));
   }

   public SysRole selectOne(Long id) {
      return (SysRole)this.getOne((new QueryWrapper<SysRole>()).lambda().eq(BaseEntity::getId, id));
   }

   public List<UserInfoDTO> getUsersByOrgRoleId(Integer orgId, Integer roleId) {
      return (List)(roleId == null ? new ArrayList() : this.sysAdminUserRoleService.getUsersByOrgRoleId(orgId, roleId));
   }

   public List<UserInfoDTO> getUsersInfoByRoleId(Integer roleId) {
      return (List)(roleId == null ? new ArrayList() : this.sysAdminUserRoleService.getUsersInfoByRoleId(roleId));
   }

}

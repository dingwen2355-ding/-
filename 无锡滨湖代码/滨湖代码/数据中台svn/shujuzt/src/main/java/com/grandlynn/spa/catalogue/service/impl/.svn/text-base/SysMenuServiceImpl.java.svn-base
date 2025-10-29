package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.StateEnum;
import com.grandlynn.spa.catalogue.dto.SysMenuDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysAdminUserRole;
import com.grandlynn.spa.catalogue.entity.SysMenu;
import com.grandlynn.spa.catalogue.entity.SysRoleMenu;
import com.grandlynn.spa.catalogue.mapper.SysMenuMapper;
import com.grandlynn.spa.catalogue.security.SecurityUtil;
import com.grandlynn.spa.catalogue.service.SysAdminUserRoleService;
import com.grandlynn.spa.catalogue.service.SysMenuService;
import com.grandlynn.spa.catalogue.service.SysRoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
   @Autowired
   private SysRoleMenuService sysRoleMenuService;
   @Autowired
   private SysAdminUserRoleService sysAdminUserRoleService;


   public List<SysMenuDTO> findList(List<Long> menuIdList, Integer solSystem) {
      if (CollectionUtils.isEmpty(menuIdList)) {
         return Lists.newArrayList();
      } else {
         LambdaQueryWrapper<SysMenu> queryWrapper = (new QueryWrapper()).lambda();
         queryWrapper.orderByAsc(SysMenu::getSort, BaseEntity::getId);
         queryWrapper.in(BaseEntity::getId, menuIdList);
         queryWrapper.eq(!solSystem.equals(3), SysMenu::getSolSystem, solSystem);
         List<SysMenu> list = this.list(queryWrapper);
         List<SysMenuDTO> sysMenuDTOList = (List)list.stream().map((m) -> {
            SysMenuDTO sysMenuDTO = new SysMenuDTO();
            BeanUtils.copyProperties(m, sysMenuDTO);
            return sysMenuDTO;
         }).collect(Collectors.toList());
         return this.getTreeList(sysMenuDTOList, new Long(0L));
      }
   }

   public List<SysMenuDTO> findList(Integer solSystem) {
      LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.orderByAsc(SysMenu::getSort, BaseEntity::getId);
      queryWrapper.eq(!solSystem.equals(3), SysMenu::getSolSystem, solSystem);
      List<SysMenu> list = this.list(queryWrapper);
      List<SysMenuDTO> sysMenuDTOList = (List)list.stream().map((m) -> {
         SysMenuDTO sysMenuDTO = new SysMenuDTO();
         BeanUtils.copyProperties(m, sysMenuDTO);
         return sysMenuDTO;
      }).collect(Collectors.toList());
      return this.getTreeList(sysMenuDTOList, new Long(0L));
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void saveOne(SysMenu sysMenu) {
      this.saveOrUpdate(sysMenu);
      if (!CollUtil.isEmpty(SecurityUtil.currentUser().getRoles())) {
         List<SysRoleMenu> roleMenus = (List)SecurityUtil.currentUser().getRoles().stream().map((r) -> {
            return new SysRoleMenu(r.getId(), sysMenu.getId());
         }).collect(Collectors.toList());
         this.sysRoleMenuService.saveBatch(roleMenus);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void deleteOne(Long id) {
      this.removeById(id);
      SysRoleMenu updateEnt = new SysRoleMenu();
      updateEnt.setDeleted(StateEnum.DELETED.getCode());
      UpdateWrapper<SysRoleMenu> updateWrapper = new UpdateWrapper();
      updateWrapper.lambda().eq(SysRoleMenu::getMenuId, id);
      this.sysRoleMenuService.update(updateEnt, updateWrapper);
   }

   public List<SysMenuDTO> authorizeInfo(List<Long> menuIdList, Integer solSystem) {
      LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.orderByAsc(BaseEntity::getId);
      queryWrapper.eq(!solSystem.equals(3), SysMenu::getSolSystem, solSystem);
      List<SysMenu> list = this.list(queryWrapper);
      List<SysMenuDTO> sysMenuDTOList = (List)list.stream().map((m) -> {
         SysMenuDTO sysMenuDTO = new SysMenuDTO();
         BeanUtils.copyProperties(m, sysMenuDTO);
         sysMenuDTO.setIsCheck(menuIdList.contains(m.getId()) ? Boolean.TRUE : Boolean.FALSE);
         return sysMenuDTO;
      }).collect(Collectors.toList());
      return this.getTreeList(sysMenuDTOList, new Long(0L));
   }

   public Boolean checkPower(SysAdminUser adminUser, String menuName) {
      List<SysAdminUserRole> sysAdminUserRoleList = this.sysAdminUserRoleService.list((new QueryWrapper<SysAdminUserRole>()).eq("user_id", adminUser.getId()));
      if (CollectionUtils.isNotEmpty(sysAdminUserRoleList)) {
         List<Long> roleIdList = (List)sysAdminUserRoleList.stream().map(SysAdminUserRole::getRoleId).distinct().collect(Collectors.toList());
         List<SysRoleMenu> sysRoleMenuList = this.sysRoleMenuService.list((new QueryWrapper<SysRoleMenu>()).in("role_id", roleIdList));
         if (CollectionUtils.isNotEmpty(sysRoleMenuList)) {
            List<Long> menuIdList = (List)sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).distinct().collect(Collectors.toList());
            List<SysMenu> sysMenuList = this.list((new QueryWrapper<SysMenu>()).in("id", menuIdList));
            return sysMenuList.stream().anyMatch((item) -> {
               return item.getName().indexOf(menuName) > -1;
            });
         }
      }

      return Boolean.FALSE;
   }

   private List<SysMenuDTO> getTreeList(List<SysMenuDTO> list, Long pid) {
      List<SysMenuDTO> menuDTOList = Lists.newArrayList();
      if (CollectionUtils.isNotEmpty(list)) {
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            SysMenuDTO data = (SysMenuDTO)var4.next();
            if (null != pid && pid.equals(data.getParentId())) {
               data.setList(this.getTreeList(list, data.getId()));
               menuDTOList.add(data);
            }
         }
      }

      return menuDTOList;
   }

   private SysMenu checkSysMenuByMap(SysMenu sysMenu) {
      SysMenu menu = (SysMenu)this.getOne((new QueryWrapper<SysMenu>()).eq("is_deleted", 0).eq("name", sysMenu.getName()).eq("parent_id", sysMenu.getParentId()));
      return menu;
   }

}

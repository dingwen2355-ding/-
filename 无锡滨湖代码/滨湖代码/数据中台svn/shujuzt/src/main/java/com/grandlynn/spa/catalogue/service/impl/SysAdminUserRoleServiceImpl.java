package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUserRole;
import com.grandlynn.spa.catalogue.mapper.SysAdminUserRoleMapper;
import com.grandlynn.spa.catalogue.service.SysAdminUserRoleService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAdminUserRoleServiceImpl extends ServiceImpl<SysAdminUserRoleMapper, SysAdminUserRole> implements SysAdminUserRoleService {
   @Autowired
   private SysAdminUserRoleMapper sysAdminUserRoleMapper;

   public boolean existsUser(Long roleId) {
      Integer num = ((SysAdminUserRoleMapper)this.baseMapper).existsUserNum(roleId);
      return num > 0;
   }

   public List<Integer> getUsersByRoleId(Integer roleId) {
      return this.sysAdminUserRoleMapper.getUsersByRoleId(roleId);
   }

   public List<UserInfoDTO> getUsersByOrgRoleId(Integer orgId, Integer roleId) {
      return this.sysAdminUserRoleMapper.getUsersByOrgRoleId(orgId, roleId);
   }

   public List<UserInfoDTO> getUsersInfoByRoleId(Integer roleId) {
      return this.sysAdminUserRoleMapper.getUsersInfoByRoleId(roleId);
   }
}

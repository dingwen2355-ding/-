package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUserRole;

import java.util.List;

public interface SysAdminUserRoleService extends IService<SysAdminUserRole> {
   boolean existsUser(Long roleId);

   List<Integer> getUsersByRoleId(Integer roleId);

   List<UserInfoDTO> getUsersByOrgRoleId(Integer orgId, Integer roleId);

   List<UserInfoDTO> getUsersInfoByRoleId(Integer roleId);
}

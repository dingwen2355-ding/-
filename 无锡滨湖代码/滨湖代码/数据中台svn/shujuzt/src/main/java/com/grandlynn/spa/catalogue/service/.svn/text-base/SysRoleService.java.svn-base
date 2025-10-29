package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.SysRoleAuthorizeRequest;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
   void saveOne(SysRole sysRole);

   void deleteOne(Long id);

   void authorize(SysRoleAuthorizeRequest request);

   Boolean orgLimitJudge(List<SysRole> roles);

   List<Integer> getUsersByRoleId(Integer roleId);

   SysRole selectOne(Long id);

   List<UserInfoDTO> getUsersByOrgRoleId(Integer orgId, Integer roleId);

   List<UserInfoDTO> getUsersInfoByRoleId(Integer roleId);
}

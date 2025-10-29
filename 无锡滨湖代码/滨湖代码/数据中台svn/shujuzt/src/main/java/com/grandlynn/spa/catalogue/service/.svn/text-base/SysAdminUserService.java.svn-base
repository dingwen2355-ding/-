package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.domain.request.SysAdminUserQueryRequest;
import com.grandlynn.spa.catalogue.dto.SysAdminUserDTO;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysRole;
import com.grandlynn.util.pageHelper.PageBean;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface SysAdminUserService extends IService<SysAdminUser> {
   void saveOne(SysAdminUser user) throws NoSuchAlgorithmException;

   PageBean<SysAdminUserDTO> page(SysAdminUserQueryRequest request);

   Long[] getUserOrgId(Long userId, Boolean recursion);

   Long[] getOrgIdRecursive(Long orgId);

   List<SysRole> getUserRole(Long id);

   SysAdminUser saveOAUser(SysAdminUser oaUser);

   String getMobileById(Long id);

   List<UserInfoDTO> findMenuAuthority(Long userId, Long menuId);
}

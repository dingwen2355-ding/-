package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUserRole;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysAdminUserRoleMapper extends BaseMapper<SysAdminUserRole> {
   Integer existsUserNum(@Param("roleId") Long roleId);

   List<Integer> getUsersByRoleId(Integer roleId);

   List<UserInfoDTO> getUsersByOrgRoleId(@Param("orgId") Integer orgId, @Param("roleId") Integer roleId);

   List<UserInfoDTO> getUsersInfoByRoleId(@Param("roleId") Integer roleId);
}

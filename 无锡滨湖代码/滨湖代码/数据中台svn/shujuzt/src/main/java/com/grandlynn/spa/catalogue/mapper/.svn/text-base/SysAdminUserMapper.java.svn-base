package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.SysAdminUserQueryRequest;
import com.grandlynn.spa.catalogue.dto.SysAdminUserDTO;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysAdminUserMapper extends BaseMapper<SysAdminUser> {
   List<SysAdminUserDTO> list(@Param("searchQuery") SysAdminUserQueryRequest searchQuery);

   List<Long> getUserOrgIdByRecursion(@Param("orgId") Long orgId);

   List<UserInfoDTO> findMenuAuthority(@Param("userId") Long userId, @Param("menuId") Long menuId);
}

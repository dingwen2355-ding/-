package com.grandlynn.spa.catalogue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grandlynn.spa.catalogue.dto.SysMenuDTO;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
   List<SysMenuDTO> findList(List<Long> menuIdList, Integer solSystem);

   List<SysMenuDTO> findList(Integer solSystem);

   void saveOne(SysMenu sysMenu);

   void deleteOne(Long id);

   List<SysMenuDTO> authorizeInfo(List<Long> menuIdList, Integer solSystem);

   Boolean checkPower(SysAdminUser sysAdminUser, String menuName);
}

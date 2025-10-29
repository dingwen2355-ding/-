package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.SysRoleEnum;
import com.grandlynn.spa.catalogue.common.enums.user.UserTypeEnum;
import com.grandlynn.spa.catalogue.common.utils.PwdUtil;
import com.grandlynn.spa.catalogue.config.SsoLoginConfig;
import com.grandlynn.spa.catalogue.domain.request.SysAdminUserQueryRequest;
import com.grandlynn.spa.catalogue.dto.SysAdminUserDTO;
import com.grandlynn.spa.catalogue.dto.UserInfoDTO;
import com.grandlynn.spa.catalogue.dto.sso.AppDepList;
import com.grandlynn.spa.catalogue.dto.sso.AppUserInfoDTO;
import com.grandlynn.spa.catalogue.entity.*;
import com.grandlynn.spa.catalogue.mapper.SysAdminUserMapper;
import com.grandlynn.spa.catalogue.service.*;
import com.grandlynn.util.extension.ApplicationException;
import com.grandlynn.util.pageHelper.PageBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SysAdminUserServiceImpl extends ServiceImpl<SysAdminUserMapper, SysAdminUser> implements SysAdminUserService {
   private static final Logger log = LogManager.getLogger(SysAdminUserServiceImpl.class);
   @Resource
   private SysRoleService roleService;
   @Resource
   private SysAdminUserRoleService uRoleService;
   @Resource
   private SysAdminUserOrgService uOrgService;
   @Resource
   private CatalogueOrgService catalogueOrgService;
   @Resource
   private AuthAppUserService authAppUserService;
   @Resource
   private SsoLoginConfig ssoLoginConfig;
   @Lazy
   @Autowired
   private KmsService kmsService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public void saveOne(SysAdminUser user) throws NoSuchAlgorithmException {
      SysAdminUser uent = this.lambdaQuery().eq(SysAdminUser::getLogin, user.getLogin()).one();
      if (null != uent && !uent.getId().equals(user.getId()) && uent.getLogin().equals(user.getLogin())) {
         throw new ApplicationException("该账号名已存在!");
      } else {
         user.setPassword(null == user.getId() ? PwdUtil.generate(user.getPassword()) : null);
         this.saveOrUpdate(user);
         if (CollUtil.isEmpty(user.getRoles())) {
            throw new ApplicationException("未关联角色!");
         } else {
            this.uRoleService.remove(new LambdaQueryWrapper<SysAdminUserRole>().eq(SysAdminUserRole::getUserId, user.getId()));
            this.uRoleService.saveBatch(user.getRoles().stream().map((role) -> {
               return new SysAdminUserRole(user.getId(), role.getId());
            }).collect(Collectors.toList()));
            if (null != user.getOrgids() && user.getOrgids().length >= 1) {
               List<SysAdminUserOrg> uOrgs = new ArrayList();
               this.uOrgService.remove(new LambdaQueryWrapper<SysAdminUserOrg>().eq(SysAdminUserOrg::getUserId, user.getId()));
               log.info("修改用户2--->");
               Long[] var4 = user.getOrgids();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Long orgid = var4[var6];
                  uOrgs.add(new SysAdminUserOrg(user.getId(), orgid));
               }
               this.uOrgService.saveBatch(uOrgs);
            }
            String authorizeSm3Hmac = this.kmsService.sm3HmacByUser(user.getId());
            LambdaUpdateWrapper<SysAdminUser> updateWrapper = new LambdaUpdateWrapper();
            updateWrapper.set(SysAdminUser::getAuthorizeSm3Hmac, authorizeSm3Hmac);
            updateWrapper.eq(BaseEntity::getId, user.getId());
            this.update(updateWrapper);
         }
      }
   }

   public PageBean<SysAdminUserDTO> page(SysAdminUserQueryRequest request) {
      PageHelper.startPage(request.getPage(), request.getSize());
      List<SysAdminUserDTO> list = ((SysAdminUserMapper)this.baseMapper).list(request);
      list.stream().forEach((u) -> {
         u.setRoleList(this.getUserRole(u.getId()));
         u.setTel(DesensitizedUtil.mobilePhone(u.getTel()));
         u.setEmail(DesensitizedUtil.email(u.getEmail()));
      });
      list.stream().forEach((u) -> {
         u.setOrgList(this.getUserOrgList(u.getId()));
      });
      return new PageBean(list);
   }

   public SysAdminUser saveOAUser(SysAdminUser sysAdminUser) {
      return this.saveOrUpdateBySsoid(sysAdminUser);
   }

   public String getMobileById(Long id) {
      return ((SysAdminUser)this.getById(id)).getTel();
   }

   public List<UserInfoDTO> findMenuAuthority(Long userId, Long menuId) {
      return ((SysAdminUserMapper)this.baseMapper).findMenuAuthority(userId, menuId);
   }

   public Long[] getUserOrgId(Long userId, Boolean recursion) {
      List<SysAdminUserOrg> userOrg = this.uOrgService.list((new LambdaQueryWrapper<SysAdminUserOrg>()).eq(SysAdminUserOrg::getUserId, userId));
      List<Long> orgIds = (List)userOrg.stream().map(SysAdminUserOrg::getOrgId).collect(Collectors.toList());
      if (recursion) {
         Iterator var5 = userOrg.iterator();

         while(var5.hasNext()) {
            SysAdminUserOrg org = (SysAdminUserOrg)var5.next();
            orgIds.addAll(((SysAdminUserMapper)this.baseMapper).getUserOrgIdByRecursion(org.getOrgId()));
         }
      }

      return (Long[])orgIds.stream().toArray((x$0) -> {
         return new Long[x$0];
      });
   }

   public Long[] getOrgIdRecursive(Long orgId) {
      List<Long> orgIds = new ArrayList();
      orgIds.add(orgId);
      orgIds.addAll(((SysAdminUserMapper)this.baseMapper).getUserOrgIdByRecursion(orgId));
      return (Long[])orgIds.stream().toArray((x$0) -> {
         return new Long[x$0];
      });
   }

   private List<CatalogueOrgDO> getUserOrgList(Long id) {
      List<SysAdminUserOrg> uOrgs = this.uOrgService.list((new LambdaQueryWrapper<SysAdminUserOrg>()).eq(SysAdminUserOrg::getUserId, id));
      return (List)uOrgs.stream().map((org) -> {
         return (CatalogueOrgDO)this.catalogueOrgService.getById(org.getOrgId());
      }).collect(Collectors.toList());
   }

   public List<SysRole> getUserRole(Long id) {
      List<SysRole> roles = this.roleService.list();
      List<SysAdminUserRole> uRoles = this.uRoleService.list((new LambdaQueryWrapper<SysAdminUserRole>()).eq(SysAdminUserRole::getUserId, id));
      if (CollUtil.isEmpty(uRoles)) {
         return null;
      } else {
         Map<Long, SysAdminUserRole> uRMap = (Map)uRoles.stream().collect(Collectors.toMap(SysAdminUserRole::getRoleId, Function.identity()));
         return (List)roles.stream().filter((r) -> {
            return uRMap.containsKey(r.getId());
         }).collect(Collectors.toList());
      }
   }

   private SysAdminUser saveOrUpdateBySsoid(SysAdminUser source) {
      AppUserInfoDTO appUserInfoDTO = this.authAppUserService.appUserInfo(source.getLogin());
      if (ObjectUtil.isNotEmpty(appUserInfoDTO)) {
         source.setTel(appUserInfoDTO.getMajorMobile());
      }

      SysAdminUser adminUser = (SysAdminUser)super.getOne(new LambdaQueryWrapper<SysAdminUser>().eq(BaseEntity::getDeleted, 0).eq(SysAdminUser::getSsoid, source.getSsoid()).or().eq(SysAdminUser::getTel, source.getTel()).eq(SysAdminUser::getName, source.getName()).last("limit 1"));
      if (!this.ssoLoginConfig.getUser_sync_allow() && ObjectUtil.isEmpty(adminUser)) {
         log.info("302无权限访问滨湖区公共数据资源共享门户，请联系管理");
         throw new ApplicationException("302", "无权限访问滨湖区公共数据资源共享门户，请联系管理员添加账号");
      } else if (null == adminUser) {
         SysAdminUser adminUserLogin = (SysAdminUser)super.getOne(new LambdaQueryWrapper<SysAdminUser>().eq(BaseEntity::getDeleted, 0).eq(SysAdminUser::getLogin, source.getLogin()).last("limit 1"));
         if (ObjectUtil.isNotEmpty(adminUserLogin)) {
            source.setLogin(source.getLogin() + "bh");
         }

         source.setType(UserTypeEnum.NORMAL.getCode());
         source.setAppId(11L);
         source.setTenantId("11");
         super.save(source);
         this.uRoleService.save(new SysAdminUserRole(source.getId(), new Long((long) SysRoleEnum.OA.getCode())));
         if (appUserInfoDTO != null) {
            try {
               Long orgId = null;
               int majorDept = appUserInfoDTO.getMajorDept();
               if (majorDept == 0) {
                  String externalCorpName = appUserInfoDTO.getExternalCorpName();
                  orgId = this.catalogueOrgService.selectByOrgName(externalCorpName);
               } else {
                  AppDepList authUserDep = this.authUserDep(majorDept);
                  if (authUserDep != null) {
                     String depName = authUserDep.getFullName();
                     if (StrUtil.isEmpty(depName)) {
                        depName = authUserDep.getName();
                     }

                     orgId = this.catalogueOrgService.selectByOrgName(depName);
                     if (orgId == null) {
                        orgId = this.getAuthOrg(authUserDep, orgId, 1);
                     }
                  }
               }

               if (orgId != null) {
                  SysAdminUserOrg adminUserOrg = new SysAdminUserOrg();
                  adminUserOrg.setOrgId(orgId);
                  adminUserOrg.setUserId(source.getId());
                  this.uOrgService.save(adminUserOrg);
               }
            } catch (Exception var9) {
               log.info("获取部门失败");
            }
         }

         return source;
      } else {
         adminUser.setName(source.getName());
         if (StrUtil.isNotEmpty(source.getTel())) {
            adminUser.setTel(source.getTel());
         }

         adminUser.setEmail(source.getEmail());
         adminUser.setSsoid(source.getSsoid());
         super.updateById(adminUser);
         return adminUser;
      }
   }

   private Long getAuthOrg(AppDepList authUserDep, Long orgId, int next) {
      if (orgId == null && next <= 2) {
         ++next;
         Integer parentId = authUserDep.getParentId();
         AppDepList parentDep = this.authUserDep(parentId);
         if (ObjectUtil.isEmpty(parentDep)) {
            return orgId;
         } else {
            String parentName = parentDep.getFullName();
            if (StrUtil.isEmpty(parentName)) {
               parentName = parentDep.getName();
            }

            orgId = this.catalogueOrgService.selectByOrgName(parentName);
            if (ObjectUtil.isEmpty(orgId)) {
               log.info("查询统一认证中心部门次数：{}", next);
               this.getAuthOrg(parentDep, orgId, next);
            }

            return orgId;
         }
      } else {
         return orgId;
      }
   }

   private AppDepList authUserDep(Integer majorDept) {
      List<AppDepList> appDepLists = this.authAppUserService.departmentList(String.valueOf(majorDept));
      if (appDepLists.size() == 0) {
         return null;
      } else {
         List<AppDepList> depList = appDepLists.stream().filter((dep) -> {
            return dep.getId() == majorDept;
         }).collect(Collectors.toList());
         return depList.get(0);
      }
   }

   private SysAdminUser checkSysAdminByMap(String name) {
      SysAdminUser adminUser = this.getOne(new QueryWrapper<SysAdminUser>().eq("is_deleted", 0).eq("login", name));
      return adminUser;
   }

}

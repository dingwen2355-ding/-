package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.ForeignOaOrg;
import com.grandlynn.spa.catalogue.entity.SysAdminUser;
import com.grandlynn.spa.catalogue.entity.SysAdminUserOrg;
import com.grandlynn.spa.catalogue.mapper.SysAdminUserOrgMapper;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.ForeignOaOrgService;
import com.grandlynn.spa.catalogue.service.SysAdminUserOrgService;
import com.grandlynn.util.extension.ApplicationException;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class SysAdminUserOrgServiceImpl extends ServiceImpl<SysAdminUserOrgMapper, SysAdminUserOrg> implements SysAdminUserOrgService {
   @Resource
   private ForeignOaOrgService sysOrgService;
   @Resource
   private CatalogueOrgService catalogueOrgService;

   public void saveOAUserOrg(SysAdminUser adminUser) {
      if (null != adminUser.getOrgids() && adminUser.getOrgids().length >= 1) {
         this.removeOrg(adminUser);
         List<SysAdminUserOrg> orgs = this.resursionTop(adminUser);
         this.saveBatch(orgs);
      }
   }

   private List<SysAdminUserOrg> resursionTop(SysAdminUser oaUser) {
      List<SysAdminUserOrg> orgs = new ArrayList();
      Long[] var3 = oaUser.getOrgids();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Long orgid = var3[var5];
         ForeignOaOrg org = this.sysOrgService.recursionTop(orgid);
         if (null != org) {
            List<CatalogueOrgDO> catalogueOrgs = this.catalogueOrgService.list(new LambdaQueryWrapper<CatalogueOrgDO>().eq(CatalogueOrgDO::getNodeNo, org.getNodeNo()));
            if (CollUtil.isEmpty(catalogueOrgs)) {
               this.log.error("OA登录关联部门错误,未能通过组织机构代码[" + org.getNodeNo() + "]找到对应组织");
            } else {
               orgs.add(new SysAdminUserOrg(oaUser.getId(), ((CatalogueOrgDO)catalogueOrgs.get(0)).getId()));
            }
         }
      }

      if (CollUtil.isEmpty(orgs)) {
         throw new ApplicationException("OA登录关联部门错误,未能匹配到任一组织");
      } else {
         return orgs;
      }
   }

   private void removeOrg(SysAdminUser sysAdminUser) {
      this.baseMapper.deleteById(sysAdminUser.getId());
   }


}

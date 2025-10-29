package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.ForeignOaOrg;
import com.grandlynn.spa.catalogue.mapper.ForeignOaOrgMapper;
import com.grandlynn.spa.catalogue.service.ForeignOaOrgService;
import org.springframework.stereotype.Service;

@Service
public class ForeignOaOrgServiceImpl extends ServiceImpl<ForeignOaOrgMapper, ForeignOaOrg> implements ForeignOaOrgService {
   public ForeignOaOrg recursionTop(Long orgid) {
      return ((ForeignOaOrgMapper)this.baseMapper).recursionTop(orgid);
   }
}

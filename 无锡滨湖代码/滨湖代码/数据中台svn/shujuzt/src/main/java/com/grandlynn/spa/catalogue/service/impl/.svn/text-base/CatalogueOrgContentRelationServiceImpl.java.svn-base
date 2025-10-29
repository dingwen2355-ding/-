package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgContentRelationMapper;
import com.grandlynn.spa.catalogue.service.CatalogueOrgContentRelationService;
import java.lang.invoke.SerializedLambda;

import org.springframework.stereotype.Service;

@Service
public class CatalogueOrgContentRelationServiceImpl extends ServiceImpl<CatalogueOrgContentRelationMapper, CatalogueOrgContentRelationDO> implements CatalogueOrgContentRelationService {
   public CatalogueOrgContentRelationDO getOneByContentId(Long id) {
      LambdaQueryWrapper<CatalogueOrgContentRelationDO> wp = new LambdaQueryWrapper();
      return (CatalogueOrgContentRelationDO)this.getOne(wp.eq(CatalogueOrgContentRelationDO::getContentId, id));
   }


}

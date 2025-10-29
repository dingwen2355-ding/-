package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueBaseConfigQueryRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueFieldBaseConfigDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueFieldBaseConfigMapper;
import com.grandlynn.spa.catalogue.service.CatalogueFieldBaseConfigService;
import com.grandlynn.util.pageHelper.PageBean;
import java.lang.invoke.SerializedLambda;

import org.springframework.stereotype.Service;

@Service("catalogueFieldBaseConfigService")
public class CatalogueFieldBaseConfigServiceImpl extends ServiceImpl<CatalogueFieldBaseConfigMapper, CatalogueFieldBaseConfigDO> implements CatalogueFieldBaseConfigService {
   public PageBean<CatalogueFieldBaseConfigDO> query(CatalogueBaseConfigQueryRequest request) {
      LambdaQueryWrapper<CatalogueFieldBaseConfigDO> queryWrapper = (new QueryWrapper()).lambda();
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueFieldBaseConfigDO::getFieldNameCh, request.getName());
      queryWrapper.or();
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueFieldBaseConfigDO::getFieldNameEn, request.getName());
      queryWrapper.orderByDesc(BaseEntity::getUpdatedTime);
      PageUtil.page(request.getPage(), request.getSize());
      return new PageBean(this.list(queryWrapper));
   }

}

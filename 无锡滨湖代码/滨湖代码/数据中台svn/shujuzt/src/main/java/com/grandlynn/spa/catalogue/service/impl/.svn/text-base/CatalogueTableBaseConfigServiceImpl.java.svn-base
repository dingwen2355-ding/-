package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueBaseConfigQueryRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueTableBaseConfigDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableBaseConfigMapper;
import com.grandlynn.spa.catalogue.service.CatalogueTableBaseConfigService;
import com.grandlynn.util.pageHelper.PageBean;
import java.lang.invoke.SerializedLambda;

import org.springframework.stereotype.Service;

@Service("catalogueTableBaseConfigService")
public class CatalogueTableBaseConfigServiceImpl extends ServiceImpl<CatalogueTableBaseConfigMapper, CatalogueTableBaseConfigDO> implements CatalogueTableBaseConfigService {
   public PageBean<CatalogueTableBaseConfigDO> query(CatalogueBaseConfigQueryRequest request) {
      LambdaQueryWrapper<CatalogueTableBaseConfigDO> queryWrapper = (new QueryWrapper()).lambda();
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueTableBaseConfigDO::getConfigName, request.getName());
      queryWrapper.orderByDesc(BaseEntity::getUpdatedTime);
      PageUtil.page(request.getPage(), request.getSize());
      return new PageBean(this.list(queryWrapper));
   }

}

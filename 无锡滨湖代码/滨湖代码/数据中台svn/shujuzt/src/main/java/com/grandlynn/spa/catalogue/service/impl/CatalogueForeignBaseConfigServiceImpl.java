package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.utils.PageUtil;
import com.grandlynn.spa.catalogue.domain.request.CatalogueForeignConfigQueryRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueForeignBaseConfigDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueForeignBaseConfigMapper;
import com.grandlynn.spa.catalogue.service.CatalogueForeignBaseConfigService;
import com.grandlynn.util.pageHelper.PageBean;
import java.lang.invoke.SerializedLambda;

import org.springframework.stereotype.Service;

@Service("catalogueForeignBaseConfigService")
public class CatalogueForeignBaseConfigServiceImpl extends ServiceImpl<CatalogueForeignBaseConfigMapper, CatalogueForeignBaseConfigDO> implements CatalogueForeignBaseConfigService {
   public PageBean<CatalogueForeignBaseConfigDO> query(CatalogueForeignConfigQueryRequest request) {
      LambdaQueryWrapper<CatalogueForeignBaseConfigDO> queryWrapper = (new QueryWrapper()).lambda();
      queryWrapper.orderByDesc(BaseEntity::getUpdatedTime);
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueForeignBaseConfigDO::getOutId, request.getName()).or();
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueForeignBaseConfigDO::getInnerId, request.getName()).or();
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueForeignBaseConfigDO::getOutName, request.getName()).or();
      queryWrapper.like(StrUtil.isNotBlank(request.getName()), CatalogueForeignBaseConfigDO::getInnerName, request.getName());
      queryWrapper.eq(null != request.getState(), CatalogueForeignBaseConfigDO::getState, request.getState());
      PageUtil.page(request.getPage(), request.getSize());
      return new PageBean(this.list(queryWrapper));
   }

}

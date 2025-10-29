package com.grandlynn.spa.catalogue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldByTableIdAndFieldNameRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueVersionList;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldVersionDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldVersionMapper;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldVersionService;
import java.lang.invoke.SerializedLambda;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("catalogueTableFieldVersionService")
public class CatalogueTableFieldVersionServiceImpl extends ServiceImpl<CatalogueTableFieldVersionMapper, CatalogueTableFieldVersionDO> implements CatalogueTableFieldVersionService {
   public List<ExportSysCatalogueTemplateDTO> findCatalogueListBySysIds(FindCatalogueVersionList request) {
      List<ExportSysCatalogueTemplateDTO> list = ((CatalogueTableFieldVersionMapper)this.baseMapper).findCatalogueListBySysIds(request);
      return list;
   }

   public CatalogueTableFieldVersionDO selectFieldByTableIdAndFieldName(CatalogueGetFieldByTableIdAndFieldNameRequest request) {
      QueryWrapper<CatalogueTableFieldVersionDO> queryWrapper = new QueryWrapper();
      queryWrapper.lambda().eq(BaseEntity::getTenantId, request.getTenantId()).eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId()).eq(CatalogueTableFieldVersionDO::getSysTableId, request.getSysTableId()).eq(CatalogueTableFieldVersionDO::getFieldName, request.getFieldName());
      return (CatalogueTableFieldVersionDO)((CatalogueTableFieldVersionMapper)this.baseMapper).selectOne(queryWrapper);
   }

}

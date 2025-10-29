package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.common.enums.ApproveStateEnum;
import com.grandlynn.spa.catalogue.common.enums.TableFlagEnum;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetFieldByTableIdAndFieldNameRequest;
import com.grandlynn.spa.catalogue.domain.request.FindCatalogueListRequest;
import com.grandlynn.spa.catalogue.dto.ExportSysCatalogueTemplateDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueTableFieldDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueTableFieldMapper;
import com.grandlynn.spa.catalogue.service.CatalogueTableFieldService;
import java.lang.invoke.SerializedLambda;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("catalogueTableFieldService")
public class CatalogueTableFieldServiceImpl extends ServiceImpl<CatalogueTableFieldMapper, CatalogueTableFieldDO> implements CatalogueTableFieldService {
   public List<ExportSysCatalogueTemplateDTO> findCatalogueListBySysIds(FindCatalogueListRequest request) {
      return ((CatalogueTableFieldMapper)this.baseMapper).findCatalogueListBySysIds(request);
   }

   public CatalogueTableFieldDO selectFieldByTableIdAndFieldName(CatalogueGetFieldByTableIdAndFieldNameRequest request) {
      LambdaQueryWrapper<CatalogueTableFieldDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(BaseEntity::getTenantId, request.getTenantId());
      queryWrapper.eq(request.getAppId() != null, BaseEntity::getAppId, request.getAppId());
      queryWrapper.eq(CatalogueTableFieldDO::getSysTableId, request.getSysTableId());
      queryWrapper.eq(CatalogueTableFieldDO::getFieldNameCh, request.getFieldNameCh());
      List<CatalogueTableFieldDO> list = ((CatalogueTableFieldMapper)this.baseMapper).selectList(queryWrapper);
      return CollUtil.isEmpty(list) ? null : (CatalogueTableFieldDO)list.get(0);
   }

   public CatalogueTableFieldDO getWithDisableState(CatalogueTableFieldDO tableFieldDO, CatalogueGetFieldByTableIdAndFieldNameRequest fieldNameRequest) {
      CatalogueTableFieldDO ent = this.selectFieldByTableIdAndFieldName(fieldNameRequest);
      if (ent == null) {
         tableFieldDO.setFieldFlag(TableFlagEnum.INSERT.getCode());
         return tableFieldDO;
      } else {
         tableFieldDO.setId(ent.getId());
         if (!ApproveStateEnum.BLOCK.getCode().equals(ent.getFieldState()) && !TableFlagEnum.DISABLE.getCode().equals(ent.getFieldFlag()) && !TableFlagEnum.ENABLE.getCode().equals(ent.getFieldFlag())) {
            if (!TableFlagEnum.INSERT.getCode().equals(ent.getFieldFlag())) {
               tableFieldDO.setFieldFlag(TableFlagEnum.UPDATE.getCode());
            }

            return tableFieldDO;
         } else {
            return tableFieldDO;
         }
      }
   }

}

package com.grandlynn.spa.catalogue.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grandlynn.spa.catalogue.base.BaseEntity;
import com.grandlynn.spa.catalogue.domain.request.CatalogueOrgTypeRequest;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgTypeDO;
import com.grandlynn.spa.catalogue.mapper.CatalogueOrgTypeMapper;
import com.grandlynn.spa.catalogue.service.CatalogueOrgService;
import com.grandlynn.spa.catalogue.service.CatalogueOrgTypeService;
import com.grandlynn.util.extension.ApplicationException;
import java.lang.invoke.SerializedLambda;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CatalogueOrgTypeServiceImpl extends ServiceImpl<CatalogueOrgTypeMapper, CatalogueOrgTypeDO> implements CatalogueOrgTypeService {
   @Resource
   @Lazy
   private CatalogueOrgService catalogueOrgService;

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean saveOrgType(CatalogueOrgTypeRequest catalogueOrgTypeRequest) {
      if (ObjectUtil.isEmpty(catalogueOrgTypeRequest)) {
         throw new ApplicationException("参数不能为空");
      } else if (StrUtil.isEmpty(catalogueOrgTypeRequest.getTypeName())) {
         throw new ApplicationException("分类名称参数不能为空");
      } else {
         LambdaQueryWrapper<CatalogueOrgTypeDO> queryWrapper = new LambdaQueryWrapper();
         queryWrapper.eq(CatalogueOrgTypeDO::getTypeName, catalogueOrgTypeRequest.getTypeName());
         long count = this.count(queryWrapper);
         if (count > 0) {
            throw new ApplicationException("分类名称已存在");
         } else {
            CatalogueOrgTypeDO catalogueOrgTypeDO = new CatalogueOrgTypeDO();
            catalogueOrgTypeDO.setTypeName(catalogueOrgTypeRequest.getTypeName());
            return ((CatalogueOrgTypeMapper)this.baseMapper).insert(catalogueOrgTypeDO) == 1;
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean editOrgType(CatalogueOrgTypeRequest catalogueOrgTypeRequest) {
      if (ObjectUtil.isEmpty(catalogueOrgTypeRequest)) {
         throw new ApplicationException("参数不能为空");
      } else if (ObjectUtil.isEmpty(catalogueOrgTypeRequest.getId())) {
         throw new ApplicationException("ID参数不能为空");
      } else if (StrUtil.isEmpty(catalogueOrgTypeRequest.getTypeName())) {
         throw new ApplicationException("分类名称参数不能为空");
      } else {
         CatalogueOrgTypeDO catalogueOrgTypeDO = (CatalogueOrgTypeDO)((CatalogueOrgTypeMapper)this.baseMapper).selectById(catalogueOrgTypeRequest.getId());
         if (ObjectUtil.isEmpty(catalogueOrgTypeDO)) {
            throw new ApplicationException("修改失败，数据不存在");
         } else {
            LambdaQueryWrapper<CatalogueOrgTypeDO> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(CatalogueOrgTypeDO::getTypeName, catalogueOrgTypeRequest.getTypeName());
            queryWrapper.ne(BaseEntity::getId, catalogueOrgTypeRequest.getId());
            long count = this.count(queryWrapper);
            if (count > 0) {
               throw new ApplicationException("分类名称已存在");
            } else {
               catalogueOrgTypeDO.setTypeName(catalogueOrgTypeRequest.getTypeName());
               return ((CatalogueOrgTypeMapper)this.baseMapper).updateById(catalogueOrgTypeDO) == 1;
            }
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public Boolean removeByOrgTypeId(Long id) {
      LambdaQueryWrapper<CatalogueOrgDO> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(CatalogueOrgDO::getOrgTypeId, id);
      long count = this.catalogueOrgService.count(queryWrapper);
      if (count > 0) {
         throw new ApplicationException("已关联组织，不允许删除");
      } else {
         return this.baseMapper.deleteById(id) > 0;
      }
   }

   public List<CatalogueOrgTypeDO> listBy() {
      List<CatalogueOrgTypeDO> list = this.list();
      list.forEach((orgTyp) -> {
         LambdaQueryWrapper<CatalogueOrgDO> queryWrapper = new LambdaQueryWrapper();
         queryWrapper.eq(CatalogueOrgDO::getOrgTypeId, orgTyp.getId());
         long count = this.catalogueOrgService.count(queryWrapper);
         orgTyp.setOrgNum(count);
      });
      return list;
   }

   public String getTypeNameById(Long id) {
      CatalogueOrgTypeDO catalogueOrgTypeDO = (CatalogueOrgTypeDO)this.getById(id);
      return ObjectUtil.isNotEmpty(catalogueOrgTypeDO) ? catalogueOrgTypeDO.getTypeName() : null;
   }

}

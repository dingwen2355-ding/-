package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.entity.CatalogueDataSourceApiDO;
import org.apache.ibatis.annotations.Param;

public interface CatalogueDataSourceApiMapper extends BaseMapper<CatalogueDataSourceApiDO> {
   CatalogueDataSourceApiDO selectByTableId(@Param("tableId") Long tableId);
}

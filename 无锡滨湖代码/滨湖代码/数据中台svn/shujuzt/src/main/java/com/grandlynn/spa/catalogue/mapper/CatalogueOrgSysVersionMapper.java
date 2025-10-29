package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysVersionDO;
import org.apache.ibatis.annotations.Param;

public interface CatalogueOrgSysVersionMapper extends BaseMapper<CatalogueOrgSysVersionDO> {
   int insertFromOld(@Param("oldDO") CatalogueOrgSysDO catalogueOrgSysDO);
}

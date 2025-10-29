package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgVersionDO;
import org.apache.ibatis.annotations.Param;

public interface CatalogueOrgVersionMapper extends BaseMapper<CatalogueOrgVersionDO> {
   int insertFromOrg(@Param("orgDO") CatalogueOrgVersionDO catalogueOrgVersionDO);
}

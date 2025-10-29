package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysAndOrgNameBySysIdsRequest;
import com.grandlynn.spa.catalogue.domain.request.CatalogueGetSysByOrgIdAndSysNameRequest;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgSysDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueOrgSysMapper extends BaseMapper<CatalogueOrgSysDO> {
   List<SystemsDTO> getSysByOrgIdAndSysName(@Param("request") CatalogueGetSysByOrgIdAndSysNameRequest request);

   List<SystemsDTO> getSysAndOrgNameBySysIds(@Param("request") CatalogueGetSysAndOrgNameBySysIdsRequest request);
}

package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationVersionDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueOrgContentRelationVersionMapper extends BaseMapper<CatalogueOrgContentRelationVersionDO> {
   List<SysContentDTO> findSysContentByOrgId(@Param("orgId") Long orgId, @Param("contentType") Integer contentType, @Param("versionNo") String versionNo);

   List<SystemsDTO> findSystemsByOrgIds(@Param("orgIds") Long[] orgIds, @Param("versionNo") String versionNo);

   int insertFromOld(@Param("oldDO") CatalogueOrgContentRelationVersionDO catalogueOrgContentRelationVersionDO);
}

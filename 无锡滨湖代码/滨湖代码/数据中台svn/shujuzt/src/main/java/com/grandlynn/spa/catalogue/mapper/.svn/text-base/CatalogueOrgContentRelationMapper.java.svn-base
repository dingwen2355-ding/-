package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.dto.SysContentDTO;
import com.grandlynn.spa.catalogue.dto.SystemsDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueOrgContentRelationDO;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueOrgContentRelationMapper extends BaseMapper<CatalogueOrgContentRelationDO> {
   List<SysContentDTO> findSysContentByOrgId(@Param("orgIds") Long[] orgIds, @Param("contentType") Integer contentType, @Param("sysName") String sysName, @Param("intermediate") int intermediate, @Param("sysStatus") String sysStatus, @Param("sysType") String sysType, @Param("netType") String netType);

   List<SystemsDTO> findSystemsByOrgIds(@Param("orgIds") Long[] orgIds, @Param("intermediate") int intermediate);
}

package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.dto.CatalogueVersionLogTableDTO;
import com.grandlynn.spa.catalogue.entity.CatalogueVersionLogDO;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CatalogueVersionLogMapper extends BaseMapper<CatalogueVersionLogDO> {
   List<CatalogueVersionLogTableDTO> findAllOrgPublish(@Param("beginTime") Timestamp beginTime, @Param("endTime") Timestamp endTime);
}

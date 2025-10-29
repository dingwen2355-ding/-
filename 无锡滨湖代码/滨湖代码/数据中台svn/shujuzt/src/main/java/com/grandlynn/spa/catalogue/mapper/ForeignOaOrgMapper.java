package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.entity.ForeignOaOrg;
import org.apache.ibatis.annotations.Param;

public interface ForeignOaOrgMapper extends BaseMapper<ForeignOaOrg> {
   ForeignOaOrg recursionTop(@Param("orgid") Long orgid);
}

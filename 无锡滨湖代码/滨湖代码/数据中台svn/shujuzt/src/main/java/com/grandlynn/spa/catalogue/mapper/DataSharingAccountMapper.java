package com.grandlynn.spa.catalogue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountPageDTO;
import com.grandlynn.spa.catalogue.dto.DataSharingAccountQueryDTO;
import com.grandlynn.spa.catalogue.entity.DataSharingAccount;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DataSharingAccountMapper extends BaseMapper<DataSharingAccount> {
   List<DataSharingAccountPageDTO> selectByPage(@Param("con") DataSharingAccountQueryDTO pageDTO);
}

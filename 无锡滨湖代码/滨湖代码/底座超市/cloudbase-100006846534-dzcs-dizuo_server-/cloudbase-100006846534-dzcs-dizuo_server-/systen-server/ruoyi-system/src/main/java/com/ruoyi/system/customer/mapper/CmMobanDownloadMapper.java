package com.ruoyi.system.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.customer.domain.CmMoban;
import com.ruoyi.system.customer.domain.CmMobanDownload;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmMobanDownloadMapper extends BaseMapper<CmMobanDownload> {


    List<Map<String, String>> top10Download();
}

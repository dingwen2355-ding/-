package com.ruoyi.system.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.customer.domain.CmAbilityExamine;
import com.ruoyi.system.customer.domain.CmMoban;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CmMobanMapper extends BaseMapper<CmMoban> {


    void updateIsUseTo0ByType(String type);

    List<CmMoban> getmobanUrl(String type);
}

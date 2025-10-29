package com.ruoyi.system.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.customer.domain.CmApp;
import com.ruoyi.system.customer.domain.CmAppParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmAppMapper  extends BaseMapper<CmApp> {


    @Update(value = "update cm_app set cas_app_id = #{data} where id = #{id}")
    void updateCasAppidById(@Param("id") String id, @Param("data") Object data);

}

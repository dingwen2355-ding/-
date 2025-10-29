package com.ruoyi.system.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.customer.domain.CmAppType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CmAppTypeMapper extends BaseMapper<CmAppType> {


    @Update(value = "update cm_app_type set status=#{status}  where id= #{id} ")
    int updateStatus(@Param("id") String id, @Param("status") Long status);
}

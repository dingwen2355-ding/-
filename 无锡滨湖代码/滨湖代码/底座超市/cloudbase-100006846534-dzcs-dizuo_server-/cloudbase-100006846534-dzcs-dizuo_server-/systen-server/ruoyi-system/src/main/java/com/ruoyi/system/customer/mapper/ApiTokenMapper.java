package com.ruoyi.system.customer.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.customer.domain.ApiToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApiTokenMapper extends BaseMapper<ApiToken> {


    @Select( " select * from cm_api_token order by create_time DESC limit 1")
    ApiToken findOneMax();
}

package cn.wxgis.jc.screen.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuotaAssessCycleMapper {

    /**
     * 查询考核周期列表（）
     */
    List<String> getCycleList(@Param("month") Integer month);

}

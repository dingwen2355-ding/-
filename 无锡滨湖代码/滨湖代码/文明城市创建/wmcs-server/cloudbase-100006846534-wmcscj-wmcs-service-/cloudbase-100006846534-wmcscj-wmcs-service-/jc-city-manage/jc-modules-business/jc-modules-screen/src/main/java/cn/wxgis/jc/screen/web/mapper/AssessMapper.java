package cn.wxgis.jc.screen.web.mapper;

import cn.wxgis.jc.screen.domain.vo.assess.AssessVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssessMapper {

    /**
     * 获取考核月份
     */
    List<String> getMonth(@Param("limit") Integer limit);
    /**
     * 获取考核周期
     */
    List<String> getCycle(@Param("limit") Integer limit);

    /**
     * 根据考核月份查询文明指数排名
     * @param month
     */
    List<AssessVo> assessList(@Param("month") String month, @Param("type") Integer type);

    /**
     * 查询各区域每个月文明指数
     */
    List<AssessVo> assessListGroupByRegion(@Param("cycles") List<String> cycles);

    /**
     * 查询各考核周期的分数情况
     */
    List<AssessVo> assessTrend(@Param("cycles") List<String> cycles, @Param("regionName") String regionName);

}

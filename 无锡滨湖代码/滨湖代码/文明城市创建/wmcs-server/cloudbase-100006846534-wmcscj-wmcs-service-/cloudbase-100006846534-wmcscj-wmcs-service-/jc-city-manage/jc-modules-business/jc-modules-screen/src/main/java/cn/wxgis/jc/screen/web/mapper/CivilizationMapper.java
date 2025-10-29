package cn.wxgis.jc.screen.web.mapper;

import cn.wxgis.jc.common.util.page.QueryExt;
import cn.wxgis.jc.screen.domain.vo.civilization.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CivilizationMapper {

    /**
     * 志愿者服务排名
     */
    List<CivilizationCountVo> activityCount();

    /**
     * 查询志愿者活动列表
     */
    IPage<ActivityVo> activityList(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 先进典型数据统计
     */
    List<CivilizationCountVo> advancedCountByType();


    /**
     * 分区划板块统计先进典型数据
     */
    List<AdvancedCountRegionVo> advancedCountByRegion();

    /**
     * 先进典型关键词
     */
    List<CivilizationCountVo> advancedKeyword();

    /**
     * 查询先进典型人物列表
     */
    IPage<AdvancedVo> advancedList(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 查询文明村镇列表
     */
    IPage<TownVo> townList(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 文明校园单位分组统计数据
     */
    List<CivilizationCountVo> companyCount();

    /**
     * 查询文明单位列表
     */
    IPage<CompanyVo> companyList(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 文明校园分组统计数据
     */
    List<CivilizationCountVo> schoolCount();

    /**
     * 查询文明校园列表
     */
    IPage<SchoolVo> schoolList(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 查询文明家庭列表
     */
    IPage<FamilyVo> familyList(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

}

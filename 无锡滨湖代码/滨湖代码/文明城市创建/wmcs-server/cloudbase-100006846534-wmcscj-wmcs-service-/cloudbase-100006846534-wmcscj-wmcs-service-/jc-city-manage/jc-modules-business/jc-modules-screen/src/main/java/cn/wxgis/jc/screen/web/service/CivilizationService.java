package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.screen.domain.select.PageSelect;
import cn.wxgis.jc.screen.domain.vo.civilization.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface CivilizationService {

    /**
     * 志愿者服务排名
     */
    List<CivilizationCountVo> activityCount();

    /**
     * 查询志愿者服务列表
     */
    IPage<ActivityVo> activityList(PageSelect select);

    /**
     * 先进典型任务统计
     */
    AdvancedCountVo advancedCount();

    /**
     * 分区划板块统计先进典型数据
     */
    Map<String, AdvancedCountVo> advancedCountByRegion();

    /**
     * 先进典型关键词
     */
    List<CivilizationCountVo> advancedKeyword();

    /**
     * 查询先进典型人物列表
     */
    IPage<AdvancedVo> advancedList(PageSelect select);

    /**
     * 查询文明村镇列表
     */
    IPage<TownVo> townList(PageSelect select);

    /**
     * 查询文明单位各分组统计
     */
    Map<String, Integer> companyCount();

    /**
     * 查询文明单位列表
     */
    IPage<CompanyVo> companyList(PageSelect select);

    /**
     * 查询文明校园各分组统计
     */
    Map<String, Integer> schoolCount();

    /**
     * 查询文明校园列表
     */
    IPage<SchoolVo> schoolList(PageSelect select);

    /**
     * 查询文明家庭列表
     */
    IPage<FamilyVo> familyList(PageSelect select);

}

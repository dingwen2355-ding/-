package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.screen.domain.vo.assess.AssessChangeVo;
import cn.wxgis.jc.screen.domain.vo.assess.AssessVo;

import java.util.List;
import java.util.Map;

public interface AssessService {

    /**
     * 获取考核周期
     */
    List<String> getMonth();

    /**
     * 获取考核月份
     */
    List<String> getCycle();

    /**
     * 查询文明指数排名
     * @param month 月份
     */
    List<AssessVo> assessList(String month, Integer type);

    /**
     * 查询文明指数趋势
     */
    Map<String, Object> assessTrend();

    AssessChangeVo assessChange();


}

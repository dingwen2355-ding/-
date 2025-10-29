package cn.wxgis.jc.screen.web.service;

import cn.wxgis.jc.screen.domain.vo.points.CheckListVo;
import cn.wxgis.jc.screen.domain.vo.points.DataCountVo;


public interface DataCountService {

    /**
     * 查询首页数据统计
     */
    DataCountVo dataCount();

}

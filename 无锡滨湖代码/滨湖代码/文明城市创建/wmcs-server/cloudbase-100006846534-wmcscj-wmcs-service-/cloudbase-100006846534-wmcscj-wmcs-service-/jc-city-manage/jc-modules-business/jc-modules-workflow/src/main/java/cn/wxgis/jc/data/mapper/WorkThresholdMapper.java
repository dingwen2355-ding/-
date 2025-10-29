package cn.wxgis.jc.data.mapper;

import cn.wxgis.jc.civilization.quota.vo.EventTypeVo;
import cn.wxgis.jc.civilization.work.po.WorkThreshold;
import cn.wxgis.jc.common.web.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkThresholdMapper extends BaseDao<WorkThreshold> {

    /**
     * 根据告警类型查询信息
     * @param warnType 告警类型
     */
    EventTypeVo findByWarnType(String warnType);
}

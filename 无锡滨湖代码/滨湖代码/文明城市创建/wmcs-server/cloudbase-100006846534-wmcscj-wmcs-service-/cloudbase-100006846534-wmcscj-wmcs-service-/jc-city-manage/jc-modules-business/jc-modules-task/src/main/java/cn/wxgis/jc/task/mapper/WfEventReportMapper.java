package cn.wxgis.jc.task.mapper;

import cn.wxgis.jc.civilization.data.resp.DataCheckPointsResponse;
import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.workflow.po.WfEventReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WfEventReportMapper extends BaseDao<WfEventReport> {

    /**
     * 根据检查点位id获取点位信息
     * @param checkPointsId 检查点位id
     */
    DataCheckPointsResponse findCheckPointsResponse(@Param("checkPointsId") String checkPointsId);

}

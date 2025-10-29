package cn.wxgis.jc.workflow.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.workflow.po.WfDeptEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WfDeptEventMapper extends BaseDao<WfDeptEvent> {

    /**
     * 根据任务id查询部门事件信息
     * @param taskId 任务id
     */
    WfDeptEvent findByTaskId(@Param("taskId") String taskId);

    /**
     * 查询除了在区核查节点的最高的节点
     * @param flowinstanceid
     * @return
     */
    WfDeptEvent findHighNode(@Param("flowinstanceid") String flowinstanceid);
}

package cn.wxgis.jc.workflow.web.service;

import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.workflow.po.WfTask;
import cn.wxgis.jc.workflow.resp.WfTaskResponse;

import java.util.List;

public interface WfTaskService extends BaseService<WfTask> {

    /**
     * 根据流程实例id删除
     * @param flowinstanceid 流程id
     * @return 结果
     */

    int deleteByFlowInstanceid(String flowinstanceid);

    /**
     * 根据流程实例id和部门id删除
     *
     * @param flowinstanceid 流程id
     * @param deptId         部门id
     */
    void deleteByFlowInstanceidDeptId(String flowinstanceid, String deptId);


    /**
     * 根据流程实例id查询数据
     * @param instanceid 流程实例id
     * @return 事件任务列表
     */
    List<WfTask> listByInstanceid(String instanceid);


    /**
     * 根据流程实例id查询数据
     * @param instanceid 流程实例id
     * @return 事件任务列表
     */
    List<WfTask> listByInstanceidAndDept(String instanceid, String deptId);

    /**
     * 根据流程实例id查询数据
     * @param flowinstanceid 流程实例id
     * @return 事件任务列表
     */
    List<WfTask> listByInstanceidAndDepts(String flowinstanceid, List<String> notDeptIds);

    /**
     * 根据流程实例id和节点id查询数据
     * @param flowinstanceid 流程实例id
     * @param nodeid 节点id
     * @return 事件任务列表
     */
    List<WfTask> listByInstanceidAndNodeid(String flowinstanceid, String nodeid);

    /**
     * 根据id查询数据
     * @param id 任务id
     * @return 流程任务信息
     */
    WfTaskResponse findResponseById(String id);

}

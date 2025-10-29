package cn.wxgis.jc.workflow.web.service;

import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.web.service.BaseService;
import cn.wxgis.jc.workflow.po.WfBaseNode;
import cn.wxgis.jc.workflow.po.WfDeptEvent;
import cn.wxgis.jc.workflow.req.WfBaseNodeSaveRequest;
import cn.wxgis.jc.workflow.select.WfBaseNodeSelect;
import cn.wxgis.jc.workflow.web.vo.NodeInfoVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WfBaseNodeService extends BaseService<WfBaseNode> {

    /**
     * 新增
     * @param request 新增信息
     * @return 新增条数
     */
    int save(WfBaseNodeSaveRequest request);

    /**
     * 查询唯一性
     * @param existsParam 查询参数
     * @return 返回结果
     */
    ExistsReturn exists(Map<String, String> existsParam);

    /**
     * 查询节点列表
     * @param select 查询参数
     * @return 节点列表
     */
    List<WfBaseNode> list(WfBaseNodeSelect select);

    /**
     * 查询开始节点
     * @param flowid 流程id
     * @return 节点信息
     */
    WfBaseNode findByStartNode(String flowid);

    /**
     * 根据结束节点id查询信息
     * @param flowid 流程id
     * @param nodeid 结束节点id
     * @return 节点信息
     */
    WfBaseNode findByNodeId(String flowid, String nodeid);

    /**
     * 获取开始节点
     * @param flowid 流程id
     * @return 节点信息
     */
    NodeInfoVO getNodeInfo(String flowid);

    /**
     * 根据当前节点获取时限
     * @param flowid 流程id
     * @param nodeid 节点id
     * @return 时限日期
     */
    Date getTimeLimitByCurrNode(String flowid, String nodeid);


}

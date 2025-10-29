package cn.wxgis.jc.workflow.fegin;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.constant.SecurityConstants;
import cn.wxgis.jc.common.constant.ServiceNameConstants;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.workflow.fegin.factory.RemoteEventReportFallbackFactory;
import cn.wxgis.jc.workflow.po.WfEventReport;
import cn.wxgis.jc.workflow.vo.WmcsWarnVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 事件上报服务
 */
@FeignClient(contextId = "remoteEventReportService", value = ServiceNameConstants.WORKFLOW_SERVICE, fallbackFactory = RemoteEventReportFallbackFactory.class)
public interface RemoteEventReportService
{

    /**
     * 创建工单流程
     * @param warn 告警信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.CREATE_WORKFLOW)
    JsonResult createWorkflow(@RequestBody WmcsWarnVo warn, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 办结工单流程
     * @param flowinstanceid 事件流程id
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping(ProviderConstant.FINISH_WORKFLOW)
    JsonResult finishWorkflow(@RequestParam("flowinstanceid") String flowinstanceid, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}

package cn.wxgis.jc.fegin;


import cn.wxgis.jc.civilization.data.po.DataCheckPointsDevice;
import cn.wxgis.jc.civilization.data.resp.DataCheckPointsResponse;
import cn.wxgis.jc.civilization.quota.po.QuotaAssessCycle;
import cn.wxgis.jc.civilization.quota.vo.EventTypeVo;
import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.exception.ServiceException;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.data.mapper.SysDeptMapper;
import cn.wxgis.jc.data.service.*;
import cn.wxgis.jc.security.annotation.InnerAuth;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysRegion;
import cn.wxgis.jc.workflow.enums.WfStatusEnum;
import cn.wxgis.jc.workflow.req.WorkflowReportRequest;
import cn.wxgis.jc.workflow.vo.WmcsWarnVo;
import cn.wxgis.jc.workflow.web.service.WfEventReportService;
import cn.wxgis.jc.workflow.web.service.WfEventSubmitService;
import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
public class RemoteEventReportController extends BaseController {

    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private WfEventReportService eventReportService;
    @Autowired
    private WfEventSubmitService eventSubmitService;
    @Autowired
    private WorkThresholdService workThresholdService;
    @Autowired
    private DataCheckPointsDeviceService dataCheckPointsDeviceService;


    /**
     * 根据检查项id查询检查项
     */
    @InnerAuth
    @PostMapping(ProviderConstant.CREATE_WORKFLOW)
    public JsonResult createWork(@RequestBody WmcsWarnVo warn) {
        WorkflowReportRequest request = new WorkflowReportRequest();
        // 查询点位分析
        DataCheckPointsResponse checkPoints = dataCheckPointsDeviceService.findResponseByDeviceId(warn.getChannelId());
        if (StringUtils.isNull(checkPoints)) {
            throw new ServiceException("点位未绑定");
        }
        log.info("点位数据分析=====");
        log.info(JSON.toJSONString(checkPoints));
        SysRegion region = deptMapper.findRegionByDeptId(checkPoints.getRegionCode());
        request.setCheckPointsId(checkPoints.getId());
        request.setCheckPointsTypeId(checkPoints.getTypeId());
        request.setDeptIds(checkPoints.getRegionCode());
        request.setRegionCode(region.getRegionCode());
        request.setRegionName(region.getRegionName());
        request.setContent(warn.getContent());
        request.setAttchnameid(warn.getImages());
        // 查询事件类别
        EventTypeVo eventTypeVo = workThresholdService.findByWarnType(warn.getType());
        request.setEventType(eventTypeVo.getEventType());
        request.setEventTypeOne(eventTypeVo.getEventTypeOne());
        request.setEventTypeTwo(eventTypeVo.getEventTypeTwo());

        request.setChannelId(warn.getChannelId());
        request.setWarnType(warn.getType());
        request.setEventTitle("AI告警监控到问题类型：" + warn.getTypeName());
        request.setAddress(warn.getChannelName());

        // 封装上报参数
        request.setFlowid("1234");
        request.setStartnodeid(WfStatusEnum.REPORT.getValue());
        request.setEndnodeid(WfStatusEnum.GIVE.getValue());
        eventReportService.aiReport(request);
        return JsonResult.ok();
    }

    @InnerAuth
    @PostMapping(ProviderConstant.FINISH_WORKFLOW)
    public JsonResult finishWork(@RequestParam("flowinstanceid") String flowinstanceid) {
        eventSubmitService.submitEventByFinish(flowinstanceid);
        return JsonResult.ok();
    }

}

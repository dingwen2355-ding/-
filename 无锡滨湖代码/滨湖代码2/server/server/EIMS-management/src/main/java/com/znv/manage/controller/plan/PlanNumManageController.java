package com.znv.manage.controller.plan;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.plan.*;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.service.plan.PlanNumManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 预案数字化管理
 *
 * @author chd
 * @date 2022/5/7
 */
@RestController
@Api(tags = "预案数字化管理")
@Slf4j
public class PlanNumManageController {

    @Resource
    PlanNumManageService planNumManageService;

    /**
     * 新增预案数字化
     *
     * @param planNumInfoBean
     * @return
     */
    @ApiOperation(value = "新增预案数字化")
    @PostMapping(value = "/add-plan-num")
    public Result addPlanNumInfo(@RequestBody @Validated PlanNumInfoBean planNumInfoBean, HttpServletRequest request) {
        if (planNumInfoBean == null) {
            log.error("addPlanNumInfo planNumInfoBean is null.");
            throw new BusinessException("planNumInfoBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if(StringUtils.isNotEmpty(userId)) {
            planNumInfoBean.setCreateId(userId);
        }
        return planNumManageService.addPlanNumInfo(planNumInfoBean);
    }

    /**
     * 更新预案数字化
     *
     * @param planNumInfoBean
     * @return
     */
    @ApiOperation(value = "更新预案数字化")
    @PostMapping(value = "/update-plan-num")
    public Result updatePlanNumInfo(@RequestBody @Validated PlanNumInfoBean planNumInfoBean, HttpServletRequest request) {
        if (planNumInfoBean == null) {
            log.error("updatePlanNumInfo planNumInfoBean is null.");
            throw new BusinessException("planNumInfoBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if(StringUtils.isNotEmpty(userId)) {
            planNumInfoBean.setCreateId(userId);
        }
        return planNumManageService.updatePlanNumInfo(planNumInfoBean);
    }

    /**
     * 保存预案数字化相关资料
     *
     * @param savePlanNumDataBean
     * @return
     */
    @ApiOperation(value = "保存预案数字化相关资料")
    @PostMapping(value = "/save-plan-num-data")
    public Result savePlanNumData(@RequestBody @Validated SavePlanNumDataBean savePlanNumDataBean) {
        return planNumManageService.savePlanNumData(savePlanNumDataBean);
    }

    /**
     * 新增预案数字化前期准备表
     *
     * @param addPlanNumPreBean
     * @return
     */
    @ApiOperation(value = "新增预案数字化前期准备表")
    @PostMapping(value = "/add-plan-num-pre")
    public Result addPlanNumPre(@RequestBody @Validated AddPlanNumPreBean addPlanNumPreBean) {
        return planNumManageService.addPlanNumPre(addPlanNumPreBean);
    }

    /**
     * 更新预案数字化前期准备表
     *
     * @param updatePlanNumPreBean
     * @return
     */
    @ApiOperation(value = "更新预案数字化前期准备表")
    @PostMapping(value = "/update-plan-num-pre")
    public Result updatePlanNumPre(@RequestBody @Validated UpdatePlanNumPreBean updatePlanNumPreBean) {
        return planNumManageService.updatePlanNumPre(updatePlanNumPreBean);
    }

    /**
     * 查询预案数字化
     *
     * @param queryPlanNumBean
     * @return
     */
    @ApiOperation(value = "查询预案数字化")
    @PostMapping(value = "/query-plan-num")
    public Result queryPlanNum(@RequestBody @Validated QueryPlanNumBean queryPlanNumBean) {
        return planNumManageService.queryPlanNum(queryPlanNumBean);
    }

    /**
     * 保存预案数字化监测预警
     *
     * @param savePlanNumMonitorBean
     * @return
     */
    @ApiOperation(value = "保存预案数字化监测预警")
    @PostMapping(value = "/save-plan-num-monitor")
    public Result savePlanNumMonitor(@RequestBody @Validated SavePlanNumMonitorBean savePlanNumMonitorBean) {
        return planNumManageService.savePlanNumMonitor(savePlanNumMonitorBean);
    }

    /**
     * 新增预案数字化应急响应
     *
     * @param planNumEmergencyResponseBean
     * @return
     */
    @ApiOperation(value = "新增预案数字化前应急响应")
    @PostMapping(value = "/add-plan-num-emergency-response")
    public Result addPlanNumEmergencyResponse(@RequestBody @Validated PlanNumEmergencyResponseBean planNumEmergencyResponseBean) {
        return planNumManageService.addPlanNumEmergencyResponse(planNumEmergencyResponseBean);
    }

    /**
     * 更新预案数字化应急响应
     *
     * @param planNumEmergencyResponseBean
     * @return
     */
    @ApiOperation(value = "更新预案数字化前应急响应")
    @PostMapping(value = "/update-plan-num-emergency-response")
    public Result updatePlanNumEmergencyResponse(@RequestBody @Validated PlanNumEmergencyResponseBean planNumEmergencyResponseBean) {
        return planNumManageService.updatePlanNumEmergencyResponse(planNumEmergencyResponseBean);
    }

    /**
     * 新增预案数字化应急保障表
     *
     * @param planNumEmergencySupportBean
     * @return
     */
    @ApiOperation(value = "新增预案数字化前应急保障表")
    @PostMapping(value = "/add-plan-num-emergency-support")
    public Result addPlanNumEmergencySupport(@RequestBody @Validated PlanNumEmergencySupportBean planNumEmergencySupportBean) {
        return planNumManageService.addPlanNumEmergencySupport(planNumEmergencySupportBean);
    }

    /**
     * 更新预案数字化应急保障表
     *
     * @param planNumEmergencySupportBean
     * @return
     */
    @ApiOperation(value = "更新预案数字化应急保障表")
    @PostMapping(value = "/update-plan-num-emergency-support")
    public Result updatePlanNumEmergencySupport(@RequestBody @Validated PlanNumEmergencySupportBean planNumEmergencySupportBean) {
        return planNumManageService.updatePlanNumEmergencySupport(planNumEmergencySupportBean);
    }

    /**
     * 新增预案数字化应急响应终止表
     *
     * @param planNumEmergencyStopBean
     * @return
     */
    @ApiOperation(value = "新增预案数字化应急响应终止表")
    @PostMapping(value = "/add-plan-num-emergency-stop")
    public Result addPlanNumEmergencyStop(@RequestBody @Validated PlanNumEmergencyStopBean planNumEmergencyStopBean) {
        return planNumManageService.addPlanNumEmergencyStop(planNumEmergencyStopBean);
    }

    /**
     * 更新预案数字化应急响应终止表
     *
     * @param planNumEmergencyStopBean
     * @return
     */
    @ApiOperation(value = "更新预案数字化应急响应终止表")
    @PostMapping(value = "/update-plan-num-emergency-stop")
    public Result updatePlanNumEmergencyStop(@RequestBody @Validated PlanNumEmergencyStopBean planNumEmergencyStopBean) {
        return planNumManageService.updatePlanNumEmergencyStop(planNumEmergencyStopBean);
    }

    /**
     * 获取监测分类
     *
     * @return
     */
    @ApiOperation(value = "获取监测分类")
    @GetMapping(value = "/get-monitor-type")
    public Result getMonitorType() {
        return new Result(getMonitorType());
    }

    /**
     * 获取响应级别
     *
     * @return
     */
    @ApiOperation(value = "获取响应级别")
    @GetMapping(value = "/get-emergency-response")
    public Result getEmergencyResponse() {
        return new Result(EmergencyConstant.getEmergencyResponseType());
    }

    /**
     * 查询分组和相关资料信息
     *
     * @param id
     * @param name
     * @return
     */
    @ApiOperation(value = "查询分组和相关资料等信息")
    @GetMapping(value = "/query-plan-num-data")
    public Result queryPlanNumData(@RequestParam(value = "id", required = false) String id,
                                   @RequestParam(value = "name", required = false) String name) {
        return planNumManageService.queryPlanNumData(id, name);
    }

    /**
     * 删除预案数字化
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除预案数字化")
    @PostMapping(value = "/del-plan-num")
    public Result delPlanNum(@RequestBody List<String> ids) {
        return planNumManageService.delPlanNum(ids);
    }

    /**
     * 新增预案数字化工作组
     *
     * @param planNumGroupBean
     * @return
     */
    @ApiOperation(value = "新增预案数字化工作组")
    @PostMapping(value = "/add-plan-group")
    public Result addPlanNumGroup(@RequestBody PlanNumGroupBean planNumGroupBean) {
        return planNumManageService.addPlanNumGroup(planNumGroupBean);
    }

    /**
     * 修改预案数字化工作组
     *
     * @param planNumGroupBean
     * @return
     */
    @ApiOperation(value = "修改预案数字化工作组")
    @PostMapping(value = "/update-plan-group-num")
    public Result updatePlanNumGroup(@RequestBody PlanNumGroupBean planNumGroupBean) {
        return planNumManageService.updatePlanNumGroup(planNumGroupBean);
    }

    /**
     * 获取预案数字化工作组组员
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取预案数字化工作组组员")
    @GetMapping(value = "/get-plan-group-person")
    public Result getPlanNumGroupPerson(@RequestParam(value = "id") String id) {
        return planNumManageService.getPlanNumGroupPerson(id);
    }

    /**
     * 删除预案数字化工作组
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除预案数字化工作组")
    @GetMapping(value = "/del-plan-group")
    public Result delPlanNumGroup(@RequestParam(value = "id") String id) {
        return planNumManageService.delPlanNumGroup(id);
    }

    /**
     * 新增预案数字化工作组组员
     *
     * @param groupPersonBeans
     * @return
     */
    @ApiOperation(value = "新增预案数字化工作组组员")
    @PostMapping(value = "/add-plan-group-person")
    public Result addPlanNumGroupPerson(@RequestBody List<GroupPersonBean> groupPersonBeans) {
        return planNumManageService.addPlanNumGroupPerson(groupPersonBeans);
    }

    /**
     * 删除预案数字化工作组组员
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除预案数字化工作组组员")
    @PostMapping(value = "/del-plan-group-person")
    public Result delPlanNumGroupPerson(@RequestBody List<String> ids) {
        return planNumManageService.delPlanNumGroupPerson(ids);
    }

    /**
     * 更新奖励与责任追究机制
     *
     * @param rewardDutyBean
     * @return
     */
    @ApiOperation(value = "更新奖励与责任追究机制")
    @PostMapping(value = "/update-plan-num-reward-duty")
    public Result updatePlanNumRewardDuty(@RequestBody @Validated RewardDutyBean rewardDutyBean) {
        return planNumManageService.updatePlanNumRewardDuty(rewardDutyBean);
    }
}

package com.znv.manage.service.plan;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.plan.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 预案数字化管理
 *
 * @author chd
 * @date 2022/5/7
 */
public interface PlanNumManageService {

    /**
     * 新增预案数字化
     *
     * @param planNumInfoBean
     * @return
     */
    Result addPlanNumInfo(PlanNumInfoBean planNumInfoBean);

    /**
     * 保存预案数字化相关资料
     *
     * @param savePlanNumDataBean
     * @return
     */
    Result savePlanNumData(SavePlanNumDataBean savePlanNumDataBean);

    /**
     * 保存预案数字化前期准备表
     *
     * @param addPlanNumPreBean
     * @return
     */
    Result addPlanNumPre(AddPlanNumPreBean addPlanNumPreBean);

    /**
     * 更新预案数字化前期准备表
     *
     * @param updatePlanNumPreBean
     * @return
     */
    Result updatePlanNumPre(UpdatePlanNumPreBean updatePlanNumPreBean);

    /**
     * 查询预案数字化
     *
     * @param queryPlanNumBean
     * @return
     */
    Result queryPlanNum(QueryPlanNumBean queryPlanNumBean);

    /**
     * 更新预案数字化
     *
     * @param planNumInfoBean
     * @return
     */
    Result updatePlanNumInfo(PlanNumInfoBean planNumInfoBean);

    /**
     * 保存预案数字化监测预警
     *
     * @param savePlanNumMonitorBean
     * @return
     */
    Result savePlanNumMonitor(SavePlanNumMonitorBean savePlanNumMonitorBean);

    /**
     * 新增预案数字化前应急响应
     *
     * @param planNumEmergencyResponseBean
     * @return
     */
    Result addPlanNumEmergencyResponse(PlanNumEmergencyResponseBean planNumEmergencyResponseBean);

    /**
     * 更新预案数字化前应急响应
     *
     * @param planNumEmergencyResponseBean
     * @return
     */
    Result updatePlanNumEmergencyResponse(PlanNumEmergencyResponseBean planNumEmergencyResponseBean);

    /**
     * 新增预案数字化前应急保障表
     *
     * @param planNumEmergencySupportBean
     * @return
     */
    Result addPlanNumEmergencySupport(PlanNumEmergencySupportBean planNumEmergencySupportBean);

    /**
     * 更新预案数字化前应急保障表
     *
     * @param planNumEmergencySupportBean
     * @return
     */
    Result updatePlanNumEmergencySupport(PlanNumEmergencySupportBean planNumEmergencySupportBean);

    /**
     * 新增预案数字化应急响应终止表
     *
     * @param planNumEmergencyStopBean
     * @return
     */
    Result addPlanNumEmergencyStop(PlanNumEmergencyStopBean planNumEmergencyStopBean);

    /**
     * 更新预案数字化应急响应终止表
     *
     * @param planNumEmergencyStopBean
     * @return
     */
    Result updatePlanNumEmergencyStop(PlanNumEmergencyStopBean planNumEmergencyStopBean);

    /**
     * 查询分组和相关资料信息
     *
     * @param id
     * @param name
     * @return
     */
    Result queryPlanNumData(String id, String name);

    /**
     * 删除预案数字化
     *
     * @param ids
     * @return
     */
    Result delPlanNum(List<String> ids);

    /**
     * 新增预案数字化工作组
     *
     * @param planNumGroupBean
     * @return
     */
    Result addPlanNumGroup(PlanNumGroupBean planNumGroupBean);

    /**
     * 修改预案数字化工作组
     *
     * @param planNumGroupBean
     * @return
     */
    Result updatePlanNumGroup(PlanNumGroupBean planNumGroupBean);

    /**
     * 删除预案数字化工作组
     *
     * @param id
     * @return
     */
    Result delPlanNumGroup(@RequestBody String id);

    /**
     * 新增预案数字化工作组组员
     *
     * @param groupPersonBeans
     * @return
     */
    Result addPlanNumGroupPerson(List<GroupPersonBean> groupPersonBeans);

    /**
     * 删除预案数字化工作组组员
     *
     * @param ids
     * @return
     */
    Result delPlanNumGroupPerson(List<String> ids);

    /**
     * 获取预案数字化工作组组员
     *
     * @param id
     * @return
     */
    Result getPlanNumGroupPerson(String id);

    /**
     * 更新奖励与责任追究机制
     *
     * @param rewardDutyBean
     * @return
     */
    Result updatePlanNumRewardDuty(RewardDutyBean rewardDutyBean);

}

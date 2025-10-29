package com.znv.manage.dao.plan;

import com.znv.manage.common.bean.plan.*;
import com.znv.manage.common.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 预案数字化管理
 *
 * @author chd
 * @date 2022/5/7
 */
@Repository
public interface PlanNumManageDao {
    /**
     * 新增预案数字化
     * @param planNumInfoBean
     */
    void addPlanNumInfo(@Param("planNumInfoBean") PlanNumInfoBean planNumInfoBean);

    /**
     * 更新预案数字化
     * @param planNumInfoBean
     */
    void updatePlanNumInfo(@Param("planNumInfoBean") PlanNumInfoBean planNumInfoBean);

    /**
     * 添加预案相关资料信息
     * @param planNumDataBeans
     */
    void addPlanNumData(@Param("planNumDataBeans") List<PlanNumDataBean> planNumDataBeans);

    /**
     * 更新预案相关资料信息
     * @param planNumDataBeans
     */
    void updatePlanNumData(@Param("planNumDataBeans") List<PlanNumDataBean> planNumDataBeans);

    /**
     * 根据id删除相关资料信息
     * @param ids
     * @return
     */
    int delPlanNumDataById(@Param("ids") List<String> ids);

    /**
     * 根据预案数字化id删除相关资料信息
     * @param ids
     * @return
     */
    int delPlanNumData(@Param("ids") List<String> ids);

    /**
     * 添加预案前期准备
     * @param addPlanNumPreBean
     * @return
     */
    void addPlanNumPre(@Param("addPlanNumPreBean") AddPlanNumPreBean addPlanNumPreBean);

    /**
     * 添加预案前期准备工作组
     * @param savePlanNumGroupBeans
     * @return
     */
    void addPlanNumPreGroup(@Param("savePlanNumGroupBeans") List<SavePlanNumGroupBean> savePlanNumGroupBeans);

    /**
     * 添加预案前期准备工作组组员
     * @param addGroupPersonBeans
     * @return
     */
    void addPlanNumPreGroupPerson(@Param("addGroupPersonBeans") List<GroupPersonBean> addGroupPersonBeans);

    /**
     * 添加预案前期准备工作关联系统
     * @param planNumSystemBeans
     * @return
     */
    void addPlanNumPreSystem(@Param("planNumSystemBeans") List<PlanNumSystemBean> planNumSystemBeans);

    /**
     * 更新预案前期准备
     * @param updatePlanNumPreBean
     * @return
     */
    void updatePlanNumPre(@Param("updatePlanNumPreBean") UpdatePlanNumPreBean updatePlanNumPreBean);

    /**
     * 更新预案前期准备工作组
     * @param savePlanNumGroupBeans
     * @return
     */
    void updatePlanNumPreGroup(@Param("savePlanNumGroupBeans") List<SavePlanNumGroupBean> savePlanNumGroupBeans);

    /**
     * 更新预案前期准备工作组组员
     * @param updateGroupPersonBeans
     * @return
     */
    void updatePlanNumPreGroupPerson(@Param("updateGroupPersonBeans") List<GroupPersonBean> updateGroupPersonBeans);

    /**
     * 根据id删除组员信息
     * @param ids
     * @return
     */
    int delPlanNumPreGroupPersonById(@Param("ids") List<String> ids);

    /**
     * 根据id删除工作组
     * @param ids
     * @return
     */
    int delPlanNumPreGroup(@Param("ids") List<String> ids);

    /**
     * 根据id删除工作组组员
     * @param ids
     * @return
     */
    int delPlanNumPreGroupPerson(@Param("ids") List<String> ids);

    /**
     * 更新预案前期准备工作关联系统
     * @param updatePlanNumSystemBeans
     * @return
     */
    void updatePlanNumPreSystem(@Param("updatePlanNumSystemBeans") List<PlanNumSystemBean> updatePlanNumSystemBeans);

    /**
     * 根据id删除工作组关联系统
     * @param ids
     * @return
     */
    int delPlanNumPreSystem(@Param("ids") List<String> ids);

    /**
     * 统计
     * @param id
     * @return
     */
    CountPlanNumPreGroupBean countPlanNumPreGroup(@Param("id") String id);

    /**
     * 更新预案组员统计
     * @param countPlanNumPreGroupBean
     * @return
     */
    void updatePlanNumGroupCount(@Param("countPlanNumPreGroupBean") CountPlanNumPreGroupBean countPlanNumPreGroupBean);

    /**
     * 获取组id
     * @param planNumId
     * @param groupName
     * @return
     */
    String getGroupId(@Param("planNumId") String planNumId, @Param("groupName") String groupName);

    /**
     * 获取预案id
     * @param planNumName
     * @return
     */
    String getPlanNumId(@Param("planNumName") String planNumName);

    /**
     * 查询预案预案数字化
     * @param queryPlanNumBean
     * @return
     */
    List<PlanNumInfoBean> queryPlanNum(@Param("queryPlanNumBean") QueryPlanNumBean queryPlanNumBean);

    /**
     * 获取url
     * @param planId
     * @return
     */
    List<String> getNumFileUrl(@Param("planId") String planId);

    /**
     * 新增预案数字化应急保障表
     * @param planNumEmergencySupportBean
     */
    void addPlanNumEmergencySupport(@Param("planNumEmergencySupportBean") PlanNumEmergencySupportBean planNumEmergencySupportBean);

    /**
     * 更新预案数字化应急保障表
     * @param planNumEmergencySupportBean
     */
    void updatePlanNumEmergencySupport(@Param("planNumEmergencySupportBean") PlanNumEmergencySupportBean planNumEmergencySupportBean);

    /**
     * 新增预案数字化应急响应终止表
     * @param planNumEmergencyStopBean
     */
    void addPlanNumEmergencyStop(@Param("planNumEmergencyStopBean") PlanNumEmergencyStopBean planNumEmergencyStopBean);

    /**
     * 更新预案数字化应急响应终止表
     * @param planNumEmergencyStopBean
     */
    void updatePlanNumEmergencyStop(@Param("planNumEmergencyStopBean") PlanNumEmergencyStopBean planNumEmergencyStopBean);

    /**
     * 新增预案数字化应急响应表
     * @param planNumEmergencyResponseBean
     */
    void addPlanNumEmergencyResponse(@Param("planNumEmergencyResponseBean") PlanNumEmergencyResponseBean planNumEmergencyResponseBean);

    /**
     * 更新预案数字化应急响应表
     * @param planNumEmergencyResponseBean
     */
    void updatePlanNumEmergencyResponse(@Param("planNumEmergencyResponseBean") PlanNumEmergencyResponseBean planNumEmergencyResponseBean);


    /**
     * 添加预案监测预警
     * @param addPlanNumMonitorBeans
     */
    void addPlanNumMonitor(@Param("planNumMonitorBeans") List<PlanNumMonitorBean> addPlanNumMonitorBeans);

    /**
     * 更新预案监测预警
     * @param updatePlanNumMonitorBeans
     */
    void updatePlanNumMonitor(@Param("planNumMonitorBeans") List<PlanNumMonitorBean> updatePlanNumMonitorBeans);

    /**
     * 根据id删除监测预警
     * @param ids
     * @return
     */
    int delPlanNumMonitorById(@Param("ids") List<String> ids);

    /**
     * 获取相关信息
     * @param id
     * @return
     */
    List<PlanNumDataBean> getPlanNumDataByPlanId(@Param("id") String id);

    /**
     * 获取前期准备信息系信息
     * @param planNumId
     * @return
     */
    PlanNumPreBean getPlanNumPreBeanByPlanId(@Param("planNumId") String planNumId);

    /**
     * 获取前期准备信息工作组
     * @param planNumId
     * @return
     */
    List<PlanNumGroupBean> getPlanNumGroupBeanByPlanId(@Param("planNumId") String planNumId);

    /**
     * 获取组员
     * @param groupId
     * @return
     */
    List<GroupPersonBean> getGroupPersonBean(@Param("groupId") String groupId);

    /**
     * 获取关联系统
     * @param planNumId
     * @return
     */
    List<PlanNumSystemBean> getPlanNumSystemBean(@Param("planNumId") String planNumId);

    /**
     * 获取检测信息
     * @param planNumId
     * @return
     */
    List<PlanNumMonitorBean> getPlanNumMonitorBean(@Param("planNumId") String planNumId);

    /**
     * 获取应急响应表
     * @param planNumId
     * @return
     */
    List<PlanNumEmergencyResponseBean> getPlanNumEmergencyResponseBean(@Param("planNumId") String planNumId);

    /**
     * 获取应急响保障
     * @param planNumId
     * @return
     */
    PlanNumEmergencySupportBean getPlanNumEmergencySupportBean(@Param("planNumId") String planNumId);

    /**
     * 获取应急响应终止表
     * @param planNumId
     * @return
     */
    PlanNumEmergencyStopBean getPlanNumEmergencyStopBean(@Param("planNumId") String planNumId);

    /**
     * 获取相关资料
     * @param planNumId
     * @return
     */
    List<PlanNumDataBean> getPlanNumDataBean(@Param("planNumId") String planNumId);

    /**
     * 删除预案数字化
     * @param ids
     * @return
     */
    int delPlanNumInfo(@Param("ids") List<String> ids);

    /**
     * 删除监测预警
     * @param ids
     * @return
     */
    int delPlanNumMonitor(@Param("ids") List<String> ids);

    /**
     * 删除应急响应
     * @param ids
     * @return
     */
    int delPlanNumEmergencyResponse(@Param("ids") List<String> ids);

    /**
     * 删除应急保障
     * @param ids
     * @return
     */
    int delPlanNumEmergencySupport(@Param("ids") List<String> ids);

    /**
     * 删除响应终止
     * @param ids
     * @return
     */
    int delPlanNumEmergencyStop(@Param("ids") List<String> ids);

    /**
     * 删除前期准备
     * @param ids
     * @return
     */
    int delPlanNumPre(@Param("ids") List<String> ids);

    /**
     * 删除工作组
     * @param ids
     * @return
     */
    int delPlanNumGroup(@Param("ids") List<String> ids);

    /**
     * 删除工作组员
     * @param ids
     * @return
     */
    int delPlanNumGroupPerson(@Param("ids") List<String> ids);

    /**
     * 删除系统表
     * @param ids
     * @return
     */
    int delPlanNumSystem(@Param("ids") List<String> ids);

    /**
     * 添加预案工作组
     * @param planNumGroupBean
     * @return
     */
    void addPlanNumGroup(@Param("planNumGroupBean") PlanNumGroupBean planNumGroupBean);

    /**
     * 添加预案工作组
     * @param planNumGroupBean
     * @return
     */
    void updatePlanNumGroup(@Param("planNumGroupBean") PlanNumGroupBean planNumGroupBean);

    /**
     * 删除预案工作组
     * @param id
     * @return
     */
    int delGroup(@Param("id") String id);

    /**
     * 更新预案级别
     * @param level
     * @param planNumId
     * @param levelName
     */
    void updatePlanLevel(@Param("level") String level, @Param("planNumId")String planNumId, @Param("levelName")String levelName);

    /**
     * 获取应急响应id
     * @param planNumId
     * @return
     */
    int getEmergencyResponse(@Param("planNumId") String planNumId);

    /**
     * 获取检测预警组名
     * @param planNumId
     * @return
     */
    List<String> getGroupNames(@Param("planNumId") String planNumId);

    /**
     * 获取事件id
     * @param planNumId
     * @return
     */
    List<String> getEventIds(@Param("planNumId") String planNumId);

    /**
     * 更新奖励与责任追究机制
     * @param rewardDutyBean
     */
    void updateRewardDuty(@Param("rewardDutyBean") RewardDutyBean rewardDutyBean);

    /**
     * 查询我的预案奖励与责任追究机制
     * @param id
     * @return
     */
    String queryRewardDuty(@Param("id") String id);

    /**
     * 根据人员id获取预案id
     * @param groupIds
     * @return
     */
    List<String> getPlanNumIdById(@Param("groupIds") List<String> groupIds);

    /**
     * 获取url
     * @param planId
     * @return
     */
    List<Map<String, Object>> getFileData(@Param("planId") String planId);

    /**
     * 根据名称获取预案id
     * @param name
     * @return
     */
    String getPlanNumIdByName(@Param("name") String name);
}

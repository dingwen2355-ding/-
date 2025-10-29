package com.znv.manage.service.plan;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.plan.*;

import java.util.List;

/**
 * 预案管理
 *
 * @author chd
 * @date 2022/5/6
 */
public interface PlanManageService {

    /**
     * 查询我的预案
     *
     * @param queryPlanBean
     * @return
     */
    Result queryPlanInfo(QueryPlanBean queryPlanBean);

    Result queryPlanInfoEvent(QueryPlanBean queryPlanBean);

    /**
     * 查询分组和相关资料信息
     *
     * @param id
     * @param name
     * @return
     */
    Result queryGroupAndData(String id, String name, String levelId);

    Result queryGroupAndDataEvent(String id, String name);

    /**
     * 新增我的预案
     *
     * @param planInfoBean
     * @return
     */
    Result addPlanInfo(PlanInfoBean planInfoBean);

    Result addPlanInfoEvent(String planId, String eventId);

    /**
     * 保存我的预案分组
     *
     * @param planChildBean
     * @return
     */
    Result updatePlanGroup(PlanChildBean planChildBean);

    Result updatePlanGroupEvent(PlanChildBean planChildBean);

    /**
     * 保存我的预案分组
     *
     * @param savePlanDataBean
     * @return
     */
    Result savePlanData(SavePlanDataBean savePlanDataBean);

    Result savePlanDataEvent(SavePlanDataBean savePlanDataBean);

    /**
     * 更新我的预案
     *
     * @param planInfoBean
     * @return
     */
    Result updatePlanInfo(PlanInfoBean planInfoBean);

    Result updatePlanInfoEvent(PlanInfoBean planInfoBean);

    /**
     * 更新奖励与责任追究机制
     *
     * @param rewardDutyBean
     * @return
     */
    Result updatePlanRewardDuty(RewardDutyBean rewardDutyBean);

    /**
     * 删除我的预案
     *
     * @param ids
     * @return
     */
    Result delPlanInfo(List<String> ids);

    Result delPlanInfoEvent(List<String> ids);

    /**
     * 发布我的预案
     *
     * @param releasePlanInfoBean
     * @return
     */
    Result releasePlanInfo(ReleasePlanInfoBean releasePlanInfoBean);

    /**
     * 审核我的预案
     *
     * @param auditPlanInfoBean
     * @return
     */
    Result auditPlanInfo(AuditPlanInfoBean auditPlanInfoBean);

    /**
     * 共享我的预案
     *
     * @param sharePlanInfoBean
     * @return
     */
    Result sharePlanInfo(SharePlanInfoBean sharePlanInfoBean);

    /**
     * 获取类型树结构
     *
     * @return
     */
    Result getTypeTree();

    /**
     * 获取预案分类
     *
     * @return
     */
    Result getEventType();

    /**
     * 新增预案评论
     *
     * @param planCommentBean
     * @return
     */
    Result addPlanComment(PlanCommentBean planCommentBean);

    /**
     * 新增预案评论回复
     *
     * @param planCommentRespBean
     * @return
     */
    Result addPlanCommentResp(PlanCommentRespBean planCommentRespBean);

    /**
     * 获取预案评论回复
     *
     * @param queryPlanCommentBean
     * @return
     */
    Result queryPlanCommentResp(QueryPlanCommentBean queryPlanCommentBean);

    /**
     * 根据河道名称查询预案名称
     *
     * @param riverName
     * @return
     */
    String queryPlanName(String riverName);

    /**
     * 获取预案行政级别
     *
     * @return
     */
    Result getLevel();

    /**
     * 获取发布单位分类
     *
     * @return
     */
    Result getUnitType();

    Result getLevelType();

    /**
     * 获取单位
     *
     * @return
     */
    Result getUnit();

    /**
     * 新增单位
     *
     * @param name
     * @return
     */
    Result addUnit(String name);

    /**
     * 删除单位
     *
     * @param id
     * @return
     */
    Result delUnit(String id);

    /**
     * 获取区县乡镇树
     *
     * @return
     */
    Result getRegion(String level,String streetId);

    /**
     * 保存我的预案分组
     *
     * @param savePlanGroupBean
     * @return
     */
    Result savePlanGroup(SavePlanGroupBean savePlanGroupBean);
}

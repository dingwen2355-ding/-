package com.znv.manage.dao.plan;

import com.znv.manage.common.bean.plan.*;
import com.znv.manage.common.bean.*;
import com.znv.manage.common.bean.plan.PlanInfoBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 预案管理
 *
 * @author chd
 * @date 2022/5/6
 */
@Repository
public interface PlanManageDao {

    /**
     * 添加预案基本信息
     * @param planInfoBean
     */
    void addPlanInfo(@Param("planInfoBean") PlanInfoBean planInfoBean);

    void addPlanInfoEvent(@Param("planInfoBean") PlanInfoBean planInfoBean);

    /**
     * 添加预案分组信息
     * @param planGroupBeans
     */
    void addPlanGroup(@Param("planGroupBeans") List<PlanGroupBean> planGroupBeans);

    void addPlanGroupEvent(@Param("planGroupBeans") List<PlanGroupBean> planGroupBeans);

    /**
     * 添加预案相关资料信息
     * @param planDataBeans
     */
    void addPlanData(@Param("planDataBeans") List<PlanDataBean> planDataBeans);

    void addPlanDataEvent(@Param("planDataBeans") List<PlanDataBean> planDataBeans);

    /**
     * 更新预案基本信息
     * @param planInfoBean
     */
    void updatePlanInfo(@Param("planInfoBean") PlanInfoBean planInfoBean);

    void updatePlanInfoEvent(@Param("planInfoBean") PlanInfoBean planInfoBean);

    /**
     * 更新奖励与责任追究机制
     * @param rewardDutyBean
     */
    void updateRewardDuty(@Param("rewardDutyBean") RewardDutyBean rewardDutyBean);

    /**
     * 更新预案分组信息
     * @param planGroupBeans
     */
    void updatePlanGroup(@Param("planGroupBeans") List<PlanGroupBean> planGroupBeans);

    void insertPlanGroup(@Param("entities") List<PlanGroupBean> planGroupBeans);

    void updatePlanGroupEvent(@Param("planGroupBeans") List<PlanGroupBean> planGroupBeans);

    void insertPlanGroupEvent(@Param("entities") List<PlanGroupBean> planGroupBeans);

    /**
     * 更新预案相关资料信息
     * @param planDataBeans
     */
    void updatePlanData(@Param("planDataBeans") List<PlanDataBean> planDataBeans);

    void insertPlanData(@Param("entities") List<PlanDataBean> planDataBeans);

    void updatePlanDataEvent(@Param("planDataBeans") List<PlanDataBean> planDataBeans);

    void insertPlanDataEvent(@Param("entities") List<PlanDataBean> planDataBeans);

    /**
     * 根据id删除预案
     * @param ids
     * @return
     */
    int delPlanInfo(@Param("ids") List<String> ids);

    int delPlanInfoEvent(@Param("ids") List<String> ids);

    /**
     * 根据id删除所有分组信息
     * @param ids
     * @return
     */
    int delPlanGroupById(@Param("ids") List<String> ids);

    int delPlanGroupEventById(@Param("ids") List<String> ids);

    /**
     * 根据id删除相关资料信息
     * @param ids
     * @return
     */
    int delPlanDataById(@Param("ids") List<String> ids);

    int delPlanDataEventById(@Param("ids") List<String> ids);

    /**
     * 根据预案id删除所有分组信息
     * @param ids
     * @return
     */
    int delPlanGroup(@Param("ids") List<String> ids);

    int delPlanGroupEvent(@Param("ids") List<String> ids);

    /**
     * 根据预案id删除所有相关资料信息
     * @param ids
     * @return
     */
    int delPlanData(@Param("ids") List<String> ids);

    int delPlanDataEvent(@Param("ids") List<String> ids);

    /**
     * 发布预案
     * @param releasePlanInfoBean
     * @return
     */
    void releasePlanInfo(@Param("releasePlanInfoBean") ReleasePlanInfoBean releasePlanInfoBean);

    /**
     * 审核预案
     * @param auditPlanInfoBean
     * @return
     */
    void auditPlanInfo(@Param("auditPlanInfoBean") AuditPlanInfoBean auditPlanInfoBean);

    /**
     * 共享预案
     * @param sharePlanInfoBean
     * @return
     */
    void sharePlanInfo(@Param("sharePlanInfoBean") SharePlanInfoBean sharePlanInfoBean);

    /**
     * 查询我的预案
     * @param queryPlanBean
     * @return
     */
    List<PlanInfoBean> queryPlanInfo(@Param("queryPlanBean") QueryPlanBean queryPlanBean);

    PlanInfoBean queryPlanInfoById(@Param("planId") String planId);

    List<PlanInfoBean> queryPlanInfoEvent(@Param("queryPlanBean") QueryPlanBean queryPlanBean);

    /**
     * 查询我的预案分组
     * @param id
     * @return
     */
    List<PlanGroupBean> queryGroup(@Param("id") String id,
                                   @Param("levelId") String levelId);

    List<PlanGroupBean> queryGroupEvent(@Param("id") String id);

    /**
     * 查询我的预案相关资料
     * @param id
     * @return
     */
    List<PlanDataBean> queryData(@Param("id") String id);

    List<PlanDataBean> queryDataEvent(@Param("id") String id);

    /**
     * 查询我的预案奖励与责任追究机制
     * @param id
     * @return
     */
    String queryRewardDuty(@Param("id") String id);

    String queryRewardDutyEvent(@Param("id") String id);

    /**
     * 根据用户id获取用户名称
     * @param id
     * @return
     */
    String getUserName(@Param("id") String id);

    /**
     * 获取一级分类
     * @return
     */
    List<LabelBean> queryEventType1();

    /**
     * 获取二级分类
     * @param id
     * @return
     */
    List<LabelBean> queryEventType2(@Param("id") String id);

    /**
     * 获取预案分类
     *
     * @return
     */
    List<LabelBean> getEventType(@Param("parentId") String parentId);

    /**
     * 获取预案id
     * @param planName
     * @return
     */
    String getPlanId(@Param("planName") String planName);

    /**
     * 获取url
     * @param planId
     * @return
     */
    List<String> getFileUrl(@Param("planId") String planId);

    List<String> getFileUrlEvent(@Param("planId") String planId);

    /**
     * 获取url
     * @param type
     * @return
     */
    String getNameByType(@Param("type") String type);

    /**
     * 新增预案评论
     * @param planCommentBean
     * @return
     */
    void addPlanComment(@Param("planCommentBean") PlanCommentBean planCommentBean);

    /**
     * 新增预案评论回复
     * @param planCommentRespBean
     * @return
     */
    void addPlanCommentResp(@Param("planCommentRespBean") PlanCommentRespBean planCommentRespBean);

    /**
     * 获取预案评论回复
     * @param queryPlanCommentBean
     * @return
     */
    List<PlanCommentBean> getPlanComment(@Param("queryPlanCommentBean") QueryPlanCommentBean queryPlanCommentBean);

    /**
     * 获取预案评论回复
     * @param id
     * @param respType
     * @return
     */
    List<PlanCommentRespBean> getPlanCommentResp(@Param("id") String id, @Param("respType") String respType);

    /**
     * 根据河道名称查询预案名称
     * @param riverName
     * @return
     */
     String queryPlanName(@Param("riverName") String riverName);

    /**
     * 获取url
     * @param planId
     * @return
     */
    List<Map<String, Object>> getFileData(@Param("planId") String planId);

    List<Map<String, Object>> getFileDataEvent(@Param("planId") String planId);

    /**
     * 根据名称获取预案id
     * @param name
     * @return
     */
    String getPlanIdByName(@Param("name") String name);

    String getEventPlanIdByName(@Param("name") String name);

    /**
     * 获取预案分类
     *
     * @return
     */
    List<LabelBean> getUnitType();

    List<LabelBean> getLevelType();

    /**
     * 获取单位
     *
     * @return
     */
    List<Map<String, Object>> getUnit();

    /**
     * 新增单位
     *
     * @param name
     * @return
     */
    void addUnit(@Param("name") String name);

    /**
     * 删除单位
     *
     * @param id
     * @return
     */
    void delUnit(@Param("id") String id);

    /**
     * 获取区县乡镇树
     *
     * @param parentId
     * @return
     */
    List<LabelBean> getRegion(@Param("parentId") String parentId, @Param("streetId") String streetId);

    void copyCommand(@Param("eventPlanId") String eventPlanId,
                     @Param("planId") String planId);
}

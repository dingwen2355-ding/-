package com.znv.manage.controller.plan;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.plan.*;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.service.LogService;
import com.znv.manage.service.plan.PlanManageService;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 预案管理
 *
 * @author chd
 * @date 2022/5/6
 */
@RestController
@Api(tags = "预案管理")
@Slf4j
public class PlanManageController {

    @Resource
    PlanManageService planManageService;

    @Resource
    LogService logService;

    /**
     * 查询我的预案
     *
     * @param queryPlanBean
     * @return
     */
    @ApiOperation(value = "查询我的预案")
    @PostMapping(value = "/query-plan")
    public Result queryPlanInfo(@RequestBody @Validated QueryPlanBean queryPlanBean,
                                @RequestParam(value = "userId", required = false) String userId,
                                @RequestParam(value = "userName", required = false) String userName,
                                HttpServletRequest request) {
        if (queryPlanBean == null) {
            queryPlanBean = new QueryPlanBean();
        }
        logService.insertLog("预案查询", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(planManageService.queryPlanInfo(queryPlanBean).getCode()),
                planManageService.queryPlanInfo(queryPlanBean).getMessage());
        return planManageService.queryPlanInfo(queryPlanBean);
    }

    @ApiOperation(value = "查询我的预案-事件相关")
    @PostMapping(value = "/query-plan-event")
    public Result queryPlanInfoEvent(@RequestBody @Validated QueryPlanBean queryPlanBean, HttpServletRequest request) {
        if (queryPlanBean == null) {
            queryPlanBean = new QueryPlanBean();
        }
        return planManageService.queryPlanInfoEvent(queryPlanBean);
    }


    /**
     * 查询我的预案（支持河道和预案名字映射，add by ljx at 20220529）
     *
     * @param queryPlanBean
     * @return
     */
    @ApiOperation(value = "查询我的预案（支持河道名字和预案名字映射）")
    @PostMapping(value = "/query-plan-extend")
    public Result queryPlanInfoExtend(@RequestBody @Validated QueryPlanBean queryPlanBean, HttpServletRequest request) {
        if (queryPlanBean == null) {
            queryPlanBean = new QueryPlanBean();
        }
        String riverName = queryPlanBean.getName();
        if (!StringUtils.isEmpty(riverName)) {
            String planName = planManageService.queryPlanName(riverName);
            queryPlanBean.setName(planName);
        }
        return planManageService.queryPlanInfo(queryPlanBean);
    }

    /**
     * 查询共享预案
     *
     * @param queryPlanBean
     * @return
     */
    @ApiOperation(value = "查询共享预案")
    @PostMapping(value = "/query-share-plan")
    public Result querySharePlanInfo(@RequestBody @Validated QueryPlanBean queryPlanBean) {
        return planManageService.queryPlanInfo(queryPlanBean);
    }

    /**
     * 查询分组和相关资料信息
     *
     * @param id
     * @param name
     * @return
     */
    @ApiOperation(value = "查询分组和相关资料信息")
    @GetMapping(value = "/query-group-data")
    public Result queryGroupAndData(@RequestParam(value = "id", required = false) String id,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "levelId", required = false) String levelId) {
        return planManageService.queryGroupAndData(id, name, levelId);
    }

    @ApiOperation(value = "查询分组和相关资料信息-事件相关")
    @GetMapping(value = "/query-group-data-event")
    public Result queryGroupAndDataEvent(@RequestParam(value = "id", required = false) String id,
                                         @RequestParam(value = "name", required = false) String name) {
        return planManageService.queryGroupAndDataEvent(id, name);
    }

    /**
     * 新增我的预案
     *
     * @param planInfoBean
     * @return
     */
    @ApiOperation(value = "新增我的预案")
    @PostMapping(value = "/add-plan")
    public Result addPlanInfo(@RequestBody @Validated PlanInfoBean planInfoBean, HttpServletRequest request) {
        if (planInfoBean == null) {
            log.error("addPlanInfo planInfoBean is null.");
            throw new BusinessException("planInfoBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if (StringUtils.isNotEmpty(userId)) {
            planInfoBean.setCreateId(userId);
        }
        return planManageService.addPlanInfo(planInfoBean);
    }

    @ApiOperation(value = "新增我的预案-事件相关")
    @GetMapping(value = "/add-plan-event")
    public Result addPlanInfoEvent(@RequestParam("planId") String planId,
                                   @RequestParam("eventId") String eventId) {
        return planManageService.addPlanInfoEvent(planId, eventId);
    }

    /**
     * 保存我的预案相关资料
     *
     * @param savePlanDataBean
     * @return
     */
    @ApiOperation(value = "保存我的预案相关资料")
    @PostMapping(value = "/save-plan-data")
    public Result savePlanData(@RequestBody @Validated SavePlanDataBean savePlanDataBean) {
        return planManageService.savePlanData(savePlanDataBean);
    }

    @ApiOperation(value = "保存我的预案相关资料-事件相关")
    @PostMapping(value = "/save-plan-data-event")
    public Result savePlanDataEvent(@RequestBody @Validated SavePlanDataBean savePlanDataBean) {
        return planManageService.savePlanDataEvent(savePlanDataBean);
    }

    /**
     * 保存我的预案分组
     *
     * @param planChildBean
     * @return
     */
    @ApiOperation(value = "更新我的预案分组")
    @PostMapping(value = "/update-plan-group")
    public Result updatePlanGroup(@RequestBody @Validated PlanChildBean planChildBean) {
        return planManageService.updatePlanGroup(planChildBean);
    }

    @ApiOperation(value = "更新我的预案分组-事件相关")
    @PostMapping(value = "/update-plan-group-event")
    public Result updatePlanGroupEvent(@RequestBody @Validated PlanChildBean planChildBean) {
        return planManageService.updatePlanGroupEvent(planChildBean);
    }

    /**
     * 更新我的预案
     *
     * @param planInfoBean
     * @return
     */
    @ApiOperation(value = "更新我的预案")
    @PostMapping(value = "/update-plan")
    public Result updatePlanInfo(@RequestBody @Validated PlanInfoBean planInfoBean) {
        return planManageService.updatePlanInfo(planInfoBean);
    }

    @ApiOperation(value = "更新我的预案-事件相关")
    @PostMapping(value = "/update-plan-event")
    public Result updatePlanInfoEvent(@RequestBody @Validated PlanInfoBean planInfoBean) {
        return planManageService.updatePlanInfoEvent(planInfoBean);
    }

    /**
     * 更新奖励与责任追究机制
     *
     * @param rewardDutyBean
     * @return
     */
    @ApiOperation(value = "更新奖励与责任追究机制")
    @PostMapping(value = "/update-plan-reward-duty")
    public Result updatePlanRewardDuty(@RequestBody @Validated RewardDutyBean rewardDutyBean) {
        return planManageService.updatePlanRewardDuty(rewardDutyBean);
    }

    /**
     * 删除我的预案
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除我的预案")
    @PostMapping(value = "/del-plan")
    public Result delPlanInfo(@RequestBody List<String> ids) {
        return planManageService.delPlanInfo(ids);
    }

    @ApiOperation(value = "删除我的预案-事件相关")
    @PostMapping(value = "/del-plan-event")
    public Result delPlanInfoEvent(@RequestBody List<String> ids) {
        return planManageService.delPlanInfoEvent(ids);
    }

    /**
     * 发布我的预案
     *
     * @param releasePlanInfoBean
     * @return
     */
    @ApiOperation(value = "发布我的预案")
    @PostMapping(value = "/release-plan")
    public Result releasePlanInfo(@RequestBody @Validated ReleasePlanInfoBean releasePlanInfoBean) {
        return planManageService.releasePlanInfo(releasePlanInfoBean);
    }

    /**
     * 审核我的预案
     *
     * @param auditPlanInfoBean
     * @return
     */
    @ApiOperation(value = "审核我的预案")
    @PostMapping(value = "/audit-plan")
    public Result auditPlanInfo(@RequestBody @Validated AuditPlanInfoBean auditPlanInfoBean, HttpServletRequest request) {
        if (auditPlanInfoBean == null) {
            log.error("auditPlanInfo auditPlanInfoBean is null.");
            throw new BusinessException("auditPlanInfoBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if (StringUtils.isNotEmpty(userId)) {
            auditPlanInfoBean.setAuditorId(userId);
        }
        return planManageService.auditPlanInfo(auditPlanInfoBean);
    }

    /**
     * 共享我的预案
     *
     * @param sharePlanInfoBean
     * @return
     */
    @ApiOperation(value = "共享我的预案")
    @PostMapping(value = "/share-plan")
    public Result sharePlanInfo(@RequestBody @Validated SharePlanInfoBean sharePlanInfoBean, HttpServletRequest request) {
        if (sharePlanInfoBean == null) {
            log.error("sharePlanInfo sharePlanInfoBean is null.");
            throw new BusinessException("sharePlanInfoBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if (StringUtils.isNotEmpty(userId)) {
            sharePlanInfoBean.setSharerId(userId);
        }
        return planManageService.sharePlanInfo(sharePlanInfoBean);
    }

    /**
     * 获取类型树结构
     *
     * @return
     */
    @ApiOperation(value = "获取类型树结构")
    @GetMapping(value = "/get-type-tree")
    public Result getTypeTree() {
        return planManageService.getTypeTree();
    }

    /**
     * 获取预案行政级别
     *
     * @return
     */
    @ApiOperation(value = "获取预案行政级别")
    @GetMapping(value = "/getLevel")
    public Result getLevel() {
        return planManageService.getLevel();
    }

    /**
     * 获取发布单位分类
     *
     * @return
     */
    @ApiOperation(value = "获取发布单位分类")
    @GetMapping(value = "/getUnitType")
    public Result getUnitType() {
        return planManageService.getUnitType();
    }

    /**
     * 获取预案分类
     *
     * @return
     */
    @ApiOperation(value = "获取预案分类")
    @GetMapping(value = "/get-event-type")
    public Result getEventType() {
        return planManageService.getEventType();
    }

    /**
     * 获取预案分类
     *
     * @return
     */
    @ApiOperation(value = "获取响应级别")
    @GetMapping(value = "/get-level-type")
    public Result getLevelType() {
        return planManageService.getLevelType();
    }


    /**
     * 新增预案评论
     *
     * @param planCommentBean
     * @param request
     * @return
     */
    @ApiOperation(value = "新增预案评论")
    @PostMapping(value = "/add-plan-comment")
    public Result addPlanComment(@RequestBody @Validated PlanCommentBean planCommentBean, HttpServletRequest request) {
        if (planCommentBean == null) {
            log.error("addPlanComment planCommentBean is null.");
            throw new BusinessException("planCommentBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if (StringUtils.isNotEmpty(userId)) {
            planCommentBean.setOperatorId(userId);
        }
        return planManageService.addPlanComment(planCommentBean);
    }

    /**
     * 新增预案评论回复
     *
     * @param planCommentRespBean
     * @param request
     * @return
     */
    @ApiOperation(value = "新增预案评论回复")
    @PostMapping(value = "/add-plan-comment_resp")
    public Result addPlanCommentResp(@RequestBody @Validated PlanCommentRespBean planCommentRespBean, HttpServletRequest request) {
        if (planCommentRespBean == null) {
            log.error("addPlanCommentResp planCommentRespBean is null.");
            throw new BusinessException("planCommentRespBean is null.");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if (StringUtils.isNotEmpty(userId)) {
            planCommentRespBean.setOperatorId(userId);
        }
        return planManageService.addPlanCommentResp(planCommentRespBean);
    }

    /**
     * 获取预案评论回复
     *
     * @param queryPlanCommentBean
     * @return
     */
    @ApiOperation(value = "获取预案评论回复")
    @PostMapping(value = "/get-plan-comment_resp")
    public Result queryPlanCommentResp(@RequestBody @Validated QueryPlanCommentBean queryPlanCommentBean) {
        if (queryPlanCommentBean == null) {
            log.error("getPlanCommentResp queryPlanCommentBean is null.");
            throw new BusinessException("queryPlanCommentBean is null.");
        }
        return planManageService.queryPlanCommentResp(queryPlanCommentBean);
    }

    /**
     * 获取单位
     *
     * @return
     */
    @ApiOperation(value = "获取单位")
    @GetMapping(value = "/getUnit")
    public Result getUnit() {
        return planManageService.getUnit();
    }

    /**
     * 新增单位
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "新增单位")
    @GetMapping(value = "/addUnit")
    public Result addUnit(@RequestParam(value = "name") String name) {
        return planManageService.addUnit(name);
    }

    /**
     * 删除单位
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除单位")
    @GetMapping(value = "/delUnit")
    public Result delUnit(@RequestParam(value = "id") String id) {
        return planManageService.delUnit(id);
    }


    /**
     * 获取区县乡镇树
     *
     * @return
     */
    @ApiOperation(value = "获取区县乡镇树")
    @GetMapping(value = "/getRegion")
    public Result getRegion(@RequestParam(value = "level", required = false) String level,
                            @RequestParam(value = "streetId", required = false) String streetId) {
        return planManageService.getRegion(level,streetId);
    }

    @ApiOperation(value = "保存我的预案分组")
    @PostMapping(value = "/save-plan-group")
    public Result savePlanGroup(@RequestBody @Validated SavePlanGroupBean savePlanGroupBean) {
        return planManageService.savePlanGroup(savePlanGroupBean);
    }
}

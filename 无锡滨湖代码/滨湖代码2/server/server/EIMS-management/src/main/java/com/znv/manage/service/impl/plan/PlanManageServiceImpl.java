package com.znv.manage.service.impl.plan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.Event;
import com.znv.manage.common.bean.plan.*;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.dao.plan.PlanLinkDao;
import com.znv.manage.dao.plan.PlanManageDao;
import com.znv.manage.service.plan.PlanManageService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预案管理
 *
 * @author chd
 * @date 2022/5/6
 */
@Service
@Slf4j
public class PlanManageServiceImpl implements PlanManageService {

    @Resource
    PlanManageDao planManageDao;

    @Resource
    PlanLinkDao planLinkDao;

    @Resource
    EventDao eventDao;

    @Override
    public Result queryPlanInfo(QueryPlanBean queryPlanBean) {
        PageHelper.startPage(queryPlanBean.getPageNum(), queryPlanBean.getPageSize());
        List<PlanInfoBean> planInfoBeans = planManageDao.queryPlanInfo(queryPlanBean);
        for (PlanInfoBean planInfoBean : planInfoBeans) {
            List<String> fileUrls = planManageDao.getFileUrl(planInfoBean.getId());
            planInfoBean.setFileUrls(fileUrls);
            List<Map<String, Object>> fileData = planManageDao.getFileData(planInfoBean.getId());
            planInfoBean.setFileData(fileData);
        }
        return new Result(new PageInfo<>(planInfoBeans));
    }

    @Override
    public Result queryPlanInfoEvent(QueryPlanBean queryPlanBean) {
        PageHelper.startPage(queryPlanBean.getPageNum(), queryPlanBean.getPageSize());
        List<PlanInfoBean> planInfoBeans = planManageDao.queryPlanInfoEvent(queryPlanBean);
        for (PlanInfoBean planInfoBean : planInfoBeans) {
            List<String> fileUrls = planManageDao.getFileUrlEvent(planInfoBean.getId());
            planInfoBean.setFileUrls(fileUrls);
            List<Map<String, Object>> fileData = planManageDao.getFileDataEvent(planInfoBean.getId());
            planInfoBean.setFileData(fileData);
        }
        return new Result(new PageInfo<>(planInfoBeans));
    }


    @Override
    public Result queryGroupAndData(String id, String name, String levelId) {
        if (StringUtils.isEmpty(id) && StringUtils.isEmpty(name)) {
            return new Result();
        }
        if (!StringUtils.isEmpty(name)) {
            id = planManageDao.getPlanIdByName(name);
        }
        if (StringUtils.isEmpty(id)) {
            return new Result();
        }
        List<PlanGroupBean> planGroupBeanList = planManageDao.queryGroup(id, levelId);
        PlanChildBean planChildBean = new PlanChildBean();
        //应急响应
        List<PlanGroupBean> planResponseGroups = new ArrayList<>();
        //终止条件
        List<PlanGroupBean> planStopGroups = new ArrayList<>();
        if (!CollectionUtils.isEmpty(planGroupBeanList)) {
            for (PlanGroupBean planGroupBean : planGroupBeanList) {
                switch (planGroupBean.getGroupType()) {
                    case "2":
                        planResponseGroups.add(planGroupBean);
                        break;
                    case "5":
                        planStopGroups.add(planGroupBean);
                        break;
                    default:
                        break;
                }
            }
        }

        List<PlanLinkBean> planLinkBeans = planLinkDao.queryByCondition(null, null, id, "1", null);
        List<String> stuffs = new ArrayList<>();
        List<String> experts = new ArrayList<>();
        List<String> teams = new ArrayList<>();
        List<String> shelters = new ArrayList<>();
        if (!CollectionUtils.isEmpty(planLinkBeans)) {
            for (PlanLinkBean linkBean : planLinkBeans) {
                String resourceId = linkBean.getResourceId();
                if (StringUtils.isNotBlank(resourceId)) {
                    switch (linkBean.getResourceType()) {
                        case "1":
                            experts.add(resourceId);
                            break;
                        case "2":
                            teams.add(resourceId);
                            break;
                        case "3":
                            stuffs.add(resourceId);
                            break;
                        case "4":
                            shelters.add(resourceId);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        planChildBean.setPlanResponseGroups(planResponseGroups);
        planChildBean.setPlanStopGroups(planStopGroups);
        PlanChildBean.PlanSupportGroups planSupportGroups = new PlanChildBean.PlanSupportGroups();

        planSupportGroups.setExperts(experts);
        planSupportGroups.setTeams(teams);
        planSupportGroups.setStuffs(stuffs);
        planSupportGroups.setShelters(shelters);
        planChildBean.setPlanSupportGroups(planSupportGroups);
        planChildBean.setPlanDataBeans(planManageDao.queryData(id));
        planChildBean.setPlanId(id);
        return new Result(planChildBean);
    }

    @Override
    public Result queryGroupAndDataEvent(String id, String name) {
        if (StringUtils.isEmpty(id) && StringUtils.isEmpty(name)) {
            return new Result();
        }
        if (!StringUtils.isEmpty(name)) {
            id = planManageDao.getEventPlanIdByName(name);
        }
        if (StringUtils.isEmpty(id)) {
            return new Result();
        }
        List<PlanGroupBean> planGroupBeanList = planManageDao.queryGroupEvent(id);
        PlanChildBean planChildBean = new PlanChildBean();
        //应急响应
        List<PlanGroupBean> planResponseGroups = new ArrayList<>();
        //终止条件
        List<PlanGroupBean> planStopGroups = new ArrayList<>();
        if (!CollectionUtils.isEmpty(planGroupBeanList)) {
            for (PlanGroupBean planGroupBean : planGroupBeanList) {
                switch (planGroupBean.getGroupType()) {
                    case "2":
                        planResponseGroups.add(planGroupBean);
                        break;
                    case "5":
                        planStopGroups.add(planGroupBean);
                        break;
                    default:
                        break;
                }
            }
        }
        List<PlanLinkBean> planLinkBeans = planLinkDao.queryByCondition(null, null, id, "2", null);
        List<String> stuffs = new ArrayList<>();
        List<String> experts = new ArrayList<>();
        List<String> teams = new ArrayList<>();
        List<String> shelters = new ArrayList<>();
        if (!CollectionUtils.isEmpty(planLinkBeans)) {
            for (PlanLinkBean linkBean : planLinkBeans) {
                String resourceId = linkBean.getResourceId();
                if (StringUtils.isNotBlank(resourceId)) {
                    switch (linkBean.getResourceType()) {
                        case "1":
                            experts.add(linkBean.getResourceId());
                            break;
                        case "2":
                            teams.add(linkBean.getResourceId());
                            break;
                        case "3":
                            stuffs.add(linkBean.getResourceId());
                            break;
                        case "4":
                            shelters.add(linkBean.getResourceId());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        planChildBean.setPlanResponseGroups(planResponseGroups);
        planChildBean.setPlanStopGroups(planStopGroups);
        PlanChildBean.PlanSupportGroups planSupportGroups = new PlanChildBean.PlanSupportGroups();

        planSupportGroups.setExperts(experts);
        planSupportGroups.setTeams(teams);
        planSupportGroups.setStuffs(stuffs);
        planSupportGroups.setShelters(shelters);
        planChildBean.setPlanSupportGroups(planSupportGroups);
        planChildBean.setPlanDataBeans(planManageDao.queryDataEvent(id));
        planChildBean.setPlanId(id);
        return new Result(planChildBean);
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addPlanInfo(PlanInfoBean planInfoBean) {
        log.info("addPlanInfo is begin. planInfoBean:{}", JSONObject.toJSONString(planInfoBean));
        try {
            if (StringUtils.isNotEmpty(planInfoBean.getCreateId())) {
                String createName = planManageDao.getUserName(planInfoBean.getCreateId());
                planInfoBean.setCreateName(createName);
            }
            // 添加预案基本信息
            buildPlanInfo(planInfoBean);
            planManageDao.addPlanInfo(planInfoBean);
            String id = planInfoBean.getId();
            List<PlanDataBean> planDataBeans = planInfoBean.getPlanDataBeans();
            if (!CollectionUtils.isEmpty(planDataBeans)) {
                for (PlanDataBean planDataBean : planDataBeans) {
                    planDataBean.setPlanId(id);
                }
                planManageDao.addPlanData(planDataBeans);
            }
        } catch (Exception e) {
            log.error("addPlanInfo is fail. e:{}", e);
            throw new BusinessException("addPlanInfo is fail." + e.getMessage());
        }
        log.info("addPlanInfo is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addPlanInfoEvent(String planId, String eventId) {
        try {
            PlanInfoBean planInfoBean = planManageDao.queryPlanInfoById(planId);
            if (planInfoBean == null) {
                throw new BusinessException("不存在该预案");
            }
            planInfoBean.setEventId(eventId);
            planManageDao.addPlanInfoEvent(planInfoBean);
            String id = planInfoBean.getId();
            List<PlanDataBean> planDataBeans = planManageDao.queryData(planId);
            List<PlanGroupBean> planGroupBeans = planManageDao.queryGroup(planId, null);
            List<PlanLinkBean> planLinkBeans = planLinkDao.queryByCondition(null, null, planId, "1", null);
            if (!CollectionUtils.isEmpty(planDataBeans)) {
                for (PlanDataBean planDataBean : planDataBeans) {
                    planDataBean.setPlanId(id);
                }
                planManageDao.addPlanDataEvent(planDataBeans);
            }
            if (!CollectionUtils.isEmpty(planGroupBeans)) {
                for (PlanGroupBean planGroupBean : planGroupBeans) {
                    planGroupBean.setPlanId(id);
                }
                planManageDao.addPlanGroupEvent(planGroupBeans);
            }
            if (!CollectionUtils.isEmpty(planLinkBeans)) {
                for (PlanLinkBean planLinkBean : planLinkBeans) {
                    planLinkBean.setLinkId(id);
                    planLinkBean.setType("2");
                }
                planLinkDao.insertBatch(planLinkBeans);
            }
            planManageDao.copyCommand(id, planId);
            Event event = new Event();
            event.setId(new Integer(eventId));
            event.setStatus(41);
            eventDao.update(event);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("addPlanInfoEvent is fail. e:{}", e.toString());
            throw new BusinessException("addPlanInfoEvent is fail." + e.getMessage());
        }
        log.info("addPlanInfoEvent is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result updatePlanGroup(PlanChildBean planChildBean) {
        Result result = new Result();
        try {
            String planId = planChildBean.getPlanId();
            if (StringUtils.isBlank(planId)) {
                throw new BusinessException("planId cannot be null");
            }
            List<PlanGroupBean> planStopGroups = planChildBean.getPlanStopGroups();
            List<PlanGroupBean> planResponseGroups = planChildBean.getPlanResponseGroups();
            List<PlanDataBean> planDataBeans = planChildBean.getPlanDataBeans();
            PlanChildBean.PlanSupportGroups planSupportGroups = planChildBean.getPlanSupportGroups();

            //全删全插
            List<String> planIds = new ArrayList<>();
            planIds.add(planChildBean.getPlanId());
            planManageDao.delPlanGroup(planIds);
            planManageDao.delPlanData(planIds);
            planLinkDao.deleteByLinkId(planId, "1");

            if (!CollectionUtils.isEmpty(planStopGroups)) {
                planStopGroups.forEach(entity -> {
                    entity.setPlanId(planId);
                    entity.setGroupType("5");
                });
                planManageDao.insertPlanGroup(planStopGroups);
            }
            if (!CollectionUtils.isEmpty(planResponseGroups)) {
                planResponseGroups.forEach(entity -> {
                    entity.setPlanId(planId);
                    entity.setGroupType("2");
                });
                planManageDao.insertPlanGroup(planResponseGroups);
            }
            if (!CollectionUtils.isEmpty(planDataBeans)) {
                planDataBeans.forEach(entity -> entity.setPlanId(planId));
                planManageDao.insertPlanData(planDataBeans);
            }

            if (planSupportGroups != null) {
                if (!CollectionUtils.isEmpty(planSupportGroups.getExperts())) {
                    List<PlanLinkBean> insertExperts = new ArrayList<>();
                    planSupportGroups.getExperts().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("1");
                        planLinkBean.setResourceType("1");
                        insertExperts.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertExperts);
                }
                if (!CollectionUtils.isEmpty(planSupportGroups.getShelters())) {
                    List<PlanLinkBean> insertShelters = new ArrayList<>();
                    planSupportGroups.getShelters().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("1");
                        planLinkBean.setResourceType("4");
                        insertShelters.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertShelters);
                }
                if (!CollectionUtils.isEmpty(planSupportGroups.getStuffs())) {
                    List<PlanLinkBean> insertStuffs = new ArrayList<>();
                    planSupportGroups.getStuffs().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("1");
                        planLinkBean.setResourceType("3");
                        insertStuffs.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertStuffs);
                }
                if (!CollectionUtils.isEmpty(planSupportGroups.getTeams())) {
                    List<PlanLinkBean> insertTeams = new ArrayList<>();
                    planSupportGroups.getTeams().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("1");
                        planLinkBean.setResourceType("2");
                        insertTeams.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertTeams);
                }
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result updatePlanGroupEvent(PlanChildBean planChildBean) {
        Result result = new Result();
        try {
            String planId = planChildBean.getPlanId();
            if (StringUtils.isBlank(planChildBean.getPlanId())) {
                throw new BusinessException("planId cannot be null");
            }
            List<PlanGroupBean> planStopGroups = planChildBean.getPlanStopGroups();
            List<PlanGroupBean> planResponseGroups = planChildBean.getPlanResponseGroups();
            List<PlanDataBean> planDataBeans = planChildBean.getPlanDataBeans();
            PlanChildBean.PlanSupportGroups planSupportGroups = planChildBean.getPlanSupportGroups();

            //全删全插
            List<String> planIds = new ArrayList<>();
            planIds.add(planChildBean.getPlanId());
            planManageDao.delPlanGroupEvent(planIds);
            planManageDao.delPlanDataEvent(planIds);
            planLinkDao.deleteByLinkId(planId, "2");

            if (!CollectionUtils.isEmpty(planStopGroups)) {
                planStopGroups.forEach(entity -> {
                    entity.setPlanId(planId);
                    entity.setGroupType("5");
                });
                planManageDao.insertPlanGroupEvent(planStopGroups);
            }
            if (!CollectionUtils.isEmpty(planResponseGroups)) {
                planStopGroups.forEach(entity -> {
                    entity.setPlanId(planId);
                    entity.setGroupType("2");
                });
                planManageDao.insertPlanGroupEvent(planResponseGroups);
            }
            if (!CollectionUtils.isEmpty(planDataBeans)) {
                planDataBeans.forEach(entity -> entity.setPlanId(planId));
                planManageDao.insertPlanDataEvent(planDataBeans);
            }
            if (planSupportGroups != null) {
                if (!CollectionUtils.isEmpty(planSupportGroups.getExperts())) {
                    List<PlanLinkBean> insertExperts = new ArrayList<>();
                    planSupportGroups.getExperts().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("2");
                        planLinkBean.setResourceType("1");
                        insertExperts.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertExperts);
                }
                if (!CollectionUtils.isEmpty(planSupportGroups.getShelters())) {
                    List<PlanLinkBean> insertShelters = new ArrayList<>();
                    planSupportGroups.getShelters().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("2");
                        planLinkBean.setResourceType("4");
                        insertShelters.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertShelters);
                }
                if (!CollectionUtils.isEmpty(planSupportGroups.getStuffs())) {
                    List<PlanLinkBean> insertStuffs = new ArrayList<>();
                    planSupportGroups.getStuffs().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("2");
                        planLinkBean.setResourceType("3");
                        insertStuffs.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertStuffs);
                }
                if (!CollectionUtils.isEmpty(planSupportGroups.getTeams())) {
                    List<PlanLinkBean> insertTeams = new ArrayList<>();
                    planSupportGroups.getTeams().forEach(entity -> {
                        PlanLinkBean planLinkBean = new PlanLinkBean();
                        planLinkBean.setResourceId(entity);
                        planLinkBean.setLinkId(planId);
                        planLinkBean.setType("2");
                        planLinkBean.setResourceType("2");
                        insertTeams.add(planLinkBean);
                    });
                    planLinkDao.insertBatch(insertTeams);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result savePlanData(SavePlanDataBean savePlanDataBean) {
        if (savePlanDataBean == null) {
            log.error("savePlanData savePlanDataBean is null.");
            throw new BusinessException("savePlanDataBean is null.");
        }
        log.info("savePlanData is begin. savePlanDataBean:{}", JSONObject.toJSONString(savePlanDataBean));
        try {
            // 获取预案相关资料的新增，修改，删除数据
            List<PlanDataBean> addPlanDataBeans = savePlanDataBean.getAddPlanDataBeans();
            List<PlanDataBean> updatePlanDataBeans = savePlanDataBean.getUpdatePlanDataBeans();
            List<String> delIds = savePlanDataBean.getDelIds();

            // 判空新增，修改，删除，分别采取操作
            if (!CollectionUtils.isEmpty(addPlanDataBeans)) {
                planManageDao.addPlanData(addPlanDataBeans);
            }
            // 更新
            if (!CollectionUtils.isEmpty(updatePlanDataBeans)) {
                planManageDao.updatePlanData(updatePlanDataBeans);
            }
            // 删除
            if (!CollectionUtils.isEmpty(delIds)) {
                planManageDao.delPlanDataById(delIds);
            }
        } catch (Exception e) {
            log.error("savePlanDataEvent is fail. e:{}", e);
            throw new BusinessException("savePlanDataEvent is fail." + e.getMessage());
        }
        log.info("savePlanDataEvent is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result savePlanDataEvent(SavePlanDataBean savePlanDataBean) {
        if (savePlanDataBean == null) {
            log.error("savePlanDataEvent savePlanDataBean is null.");
            throw new BusinessException("savePlanDataEvent is null.");
        }
        log.info("savePlanDataEvent is begin. savePlanDataBean:{}", JSONObject.toJSONString(savePlanDataBean));
        try {
            // 获取预案相关资料的新增，修改，删除数据
            List<PlanDataBean> addPlanDataBeans = savePlanDataBean.getAddPlanDataBeans();
            List<PlanDataBean> updatePlanDataBeans = savePlanDataBean.getUpdatePlanDataBeans();
            List<String> delIds = savePlanDataBean.getDelIds();

            // 判空新增，修改，删除，分别采取操作
            if (!CollectionUtils.isEmpty(addPlanDataBeans)) {
                planManageDao.addPlanDataEvent(addPlanDataBeans);
            }
            // 更新
            if (!CollectionUtils.isEmpty(updatePlanDataBeans)) {
                planManageDao.updatePlanDataEvent(updatePlanDataBeans);
            }
            // 删除
            if (!CollectionUtils.isEmpty(delIds)) {
                planManageDao.delPlanDataEventById(delIds);
            }
        } catch (Exception e) {
            log.error("savePlanDataEvent is fail. e:{}", e);
            throw new BusinessException("savePlanDataEvent is fail." + e.getMessage());
        }
        log.info("savePlanDataEvent is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result updatePlanInfo(PlanInfoBean planInfoBean) {
        if (planInfoBean == null) {
            log.error("updatePlanInfo planInfoBean is null.");
            throw new BusinessException("planInfoBean is null.");
        }
        log.info("updatePlanInfo is begin. planInfoBean:{}", JSONObject.toJSONString(planInfoBean));
        try {
            // 添加预案基本信息
            buildPlanInfo(planInfoBean);
            // 更新预案基本信息
            planManageDao.updatePlanInfo(planInfoBean);
            List<PlanDataBean> planDataBeans = planInfoBean.getPlanDataBeans();
            if (!CollectionUtils.isEmpty(planDataBeans)) {
                String planId = planInfoBean.getId();
                List<String> delIds = new ArrayList();
                List<PlanDataBean> addPlanDataBeans = new ArrayList();
                List<PlanDataBean> planDataBeansLocal = planManageDao.queryData(planId);
                // 获取新增相关信息数据
                for (PlanDataBean planDataBean : planDataBeans) {
                    boolean flag = false;
                    for (PlanDataBean planDataBeanLocal : planDataBeansLocal) {
                        if (planDataBean.getPlanId().equals(planDataBeanLocal.getPlanId())
                                && planDataBean.getFileUrl().equals(planDataBeanLocal.getFileUrl())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        addPlanDataBeans.add(planDataBean);
                    }
                }
                // 获取删除的相关信息数据
                for (PlanDataBean planDataBeanLocal : planDataBeansLocal) {
                    boolean flag = false;
                    for (PlanDataBean planDataBean : planDataBeans) {
                        if (planDataBean.getPlanId().equals(planDataBeanLocal.getPlanId())
                                && planDataBean.getFileUrl().equals(planDataBeanLocal.getFileUrl())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        delIds.add(planDataBeanLocal.getId());
                    }
                }

                if (!CollectionUtils.isEmpty(addPlanDataBeans)) {
                    planManageDao.addPlanData(addPlanDataBeans);
                }

                if (!CollectionUtils.isEmpty(delIds)) {
                    planManageDao.delPlanDataById(delIds);
                }
            } else {
                String id = planInfoBean.getId();
                List<String> ids = new ArrayList();
                ids.add(id);
                planManageDao.delPlanData(ids);
            }

        } catch (Exception e) {
            log.error("updatePlanInfo is fail. e:{}", e);
            throw new BusinessException("updatePlanInfo is fail." + e.getMessage());
        }
        log.info("updatePlanInfo is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result updatePlanInfoEvent(PlanInfoBean planInfoBean) {
        if (planInfoBean == null) {
            log.error("updatePlanInfoEvent planInfoBean is null.");
            throw new BusinessException("updatePlanInfoEvent is null.");
        }
        log.info("updatePlanInfoEvent is begin. planInfoBean:{}", JSONObject.toJSONString(planInfoBean));
        try {
            // 添加预案基本信息
            buildPlanInfo(planInfoBean);
            // 更新预案基本信息
            planManageDao.updatePlanInfoEvent(planInfoBean);
            List<PlanDataBean> planDataBeans = planInfoBean.getPlanDataBeans();
            if (!CollectionUtils.isEmpty(planDataBeans)) {
                String planId = planInfoBean.getId();
                List<String> delIds = new ArrayList();
                List<PlanDataBean> addPlanDataBeans = new ArrayList();
                List<PlanDataBean> planDataBeansLocal = planManageDao.queryData(planId);
                // 获取新增相关信息数据
                for (PlanDataBean planDataBean : planDataBeans) {
                    boolean flag = false;
                    for (PlanDataBean planDataBeanLocal : planDataBeansLocal) {
                        if (planDataBean.getPlanId().equals(planDataBeanLocal.getPlanId())
                                && planDataBean.getFileUrl().equals(planDataBeanLocal.getFileUrl())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        addPlanDataBeans.add(planDataBean);
                    }
                }
                // 获取删除的相关信息数据
                for (PlanDataBean planDataBeanLocal : planDataBeansLocal) {
                    boolean flag = false;
                    for (PlanDataBean planDataBean : planDataBeans) {
                        if (planDataBean.getPlanId().equals(planDataBeanLocal.getPlanId())
                                && planDataBean.getFileUrl().equals(planDataBeanLocal.getFileUrl())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        delIds.add(planDataBeanLocal.getId());
                    }
                }

                if (!CollectionUtils.isEmpty(addPlanDataBeans)) {
                    planManageDao.addPlanDataEvent(addPlanDataBeans);
                }

                if (!CollectionUtils.isEmpty(delIds)) {
                    planManageDao.delPlanDataEventById(delIds);
                }
            } else {
                String id = planInfoBean.getId();
                List<String> ids = new ArrayList();
                ids.add(id);
                planManageDao.delPlanDataEvent(ids);
            }

        } catch (Exception e) {
            log.error("updatePlanInfoEvent is fail. e:{}", e);
            throw new BusinessException("updatePlanInfoEvent is fail." + e.getMessage());
        }
        log.info("updatePlanInfoEvent is end.");
        return new Result();
    }

    @Override
    public Result updatePlanRewardDuty(RewardDutyBean rewardDutyBean) {
        if (rewardDutyBean == null) {
            log.error("updateRewardDuty rewardDutyBean is null.");
            throw new BusinessException("rewardDutyBean is null.");
        }
        log.info("updateRewardDuty is begin. rewardDutyBean:{}", JSONObject.toJSONString(rewardDutyBean));
        planManageDao.updateRewardDuty(rewardDutyBean);
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delPlanInfo(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("delPlanInfo ids is null.");
            throw new BusinessException("ids is null.");
        }
        log.info("delPlanInfo is begin. ids:{}", JSONObject.toJSONString(ids));
        try {
            // 删除预案基本信息
            planManageDao.delPlanInfo(ids);
            // 删除分组信息
            planManageDao.delPlanGroup(ids);
            // 删除相关资料
            planManageDao.delPlanData(ids);
        } catch (Exception e) {
            log.error("delPlanInfo is fail. e:{}", e);
            throw new BusinessException("delPlanInfo is fail." + e.getMessage());
        }
        log.info("delPlanInfo is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delPlanInfoEvent(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("delPlanInfoEvent ids is null.");
            throw new BusinessException("ids is null.");
        }
        log.info("delPlanInfoEvent is begin. ids:{}", JSONObject.toJSONString(ids));
        try {
            // 删除预案基本信息
            planManageDao.delPlanInfoEvent(ids);
            // 删除分组信息
            planManageDao.delPlanGroupEvent(ids);
            // 删除相关资料
            planManageDao.delPlanDataEvent(ids);
        } catch (Exception e) {
            log.error("delPlanInfoEvent is fail. e:{}", e);
            throw new BusinessException("delPlanInfoEvent is fail." + e.getMessage());
        }
        log.info("delPlanInfoEvent is end.");
        return new Result();
    }

    @Override
    public Result releasePlanInfo(ReleasePlanInfoBean releasePlanInfoBean) {
        if (releasePlanInfoBean == null) {
            log.error("releasePlanInfo releasePlanInfoBean is null.");
            throw new BusinessException("releasePlanInfoBean is null.");
        }
        planManageDao.releasePlanInfo(releasePlanInfoBean);
        return new Result();
    }

    @Override
    public Result auditPlanInfo(AuditPlanInfoBean auditPlanInfoBean) {
        if (auditPlanInfoBean == null) {
            log.error("auditPlanInfo id auditPlanInfoBean null.");
            throw new BusinessException("auditPlanInfoBean is null.");
        }
        if (StringUtils.isNotEmpty(auditPlanInfoBean.getAuditorId())) {
            String auditorName = planManageDao.getUserName(auditPlanInfoBean.getAuditorId());
            auditPlanInfoBean.setAuditorName(auditorName);
        }
        if ("3".equals(auditPlanInfoBean.getAudit())) {
            auditPlanInfoBean.setShared("0");
        }
        planManageDao.auditPlanInfo(auditPlanInfoBean);
        return new Result();
    }

    @Override
    public Result sharePlanInfo(SharePlanInfoBean sharePlanInfoBean) {
        if (sharePlanInfoBean == null) {
            log.error("sharePlanInfo id sharePlanInfoBean null.");
            throw new BusinessException("sharePlanInfoBean is null.");
        }
        if (StringUtils.isNotEmpty(sharePlanInfoBean.getSharerId())) {
            String sharerName = planManageDao.getUserName(sharePlanInfoBean.getSharerId());
            sharePlanInfoBean.setSharerName(sharerName);
        }
        planManageDao.sharePlanInfo(sharePlanInfoBean);
        return new Result();
    }

    @Override
    public Result getTypeTree() {
        List<LabelBean> labelBeans = planManageDao.queryEventType1();
        if (CollectionUtils.isEmpty(labelBeans)) {
            return new Result();
        }
        JSONArray jsonArray = new JSONArray();
        for (LabelBean labelBean : labelBeans) {
            List<LabelBean> labelBeansTmp = planManageDao.queryEventType2(labelBean.getValue());
            JSONObject jsonObject = new JSONObject();
            List<LabelBean> newList = labelBeansTmp.stream().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            jsonObject.put("value", labelBean.getValue());
            jsonObject.put("label", labelBean.getLabel());
            jsonObject.put("children", newList);
            jsonArray.add(jsonObject);
        }
        return new Result(jsonArray);
    }

    @Override
    public Result getEventType() {
        return new Result(planManageDao.getEventType("68"));
    }

    @Override
    public Result addPlanComment(PlanCommentBean planCommentBean) {
        if (StringUtils.isNotEmpty(planCommentBean.getOperatorId())) {
            String operatorName = planManageDao.getUserName(planCommentBean.getOperatorId());
            planCommentBean.setOperatorName(operatorName);
        }
        planManageDao.addPlanComment(planCommentBean);
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addPlanCommentResp(PlanCommentRespBean planCommentRespBean) {
        if (StringUtils.isNotEmpty(planCommentRespBean.getOperatorId())) {
            String operatorName = planManageDao.getUserName(planCommentRespBean.getOperatorId());
            planCommentRespBean.setOperatorName(operatorName);
        }
        if (StringUtils.isNotEmpty(planCommentRespBean.getRecipientId())) {
            String recipientName = planManageDao.getUserName(planCommentRespBean.getRecipientId());
            planCommentRespBean.setRecipientName(recipientName);
        }
        planManageDao.addPlanCommentResp(planCommentRespBean);
        return new Result();
    }

    @Override
    public Result queryPlanCommentResp(QueryPlanCommentBean queryPlanCommentBean) {
        String respType = queryPlanCommentBean.getRespType();
        PageHelper.startPage(queryPlanCommentBean.getPageNum(), queryPlanCommentBean.getPageSize());
        if ("1".equals(respType)) {
            List<PlanCommentBean> planCommentBeans = planManageDao.getPlanComment(queryPlanCommentBean);
            PageInfo pageInfo = new PageInfo<>(planCommentBeans);
            if (!CollectionUtils.isEmpty(planCommentBeans)) {
                for (PlanCommentBean planCommentBean : planCommentBeans) {
                    PageHelper.startPage(1, 10);
                    List<PlanCommentRespBean> planCommentRespBeans = planManageDao.getPlanCommentResp(planCommentBean.getId(), "1");
                    planCommentBean.setPlanCommentResp(new PageInfo(planCommentRespBeans));
                }
            }
            return new Result(pageInfo);
        } else if ("2".equals(respType)) {
            List<PlanCommentRespBean> planCommentRespBeans = planManageDao.getPlanCommentResp(queryPlanCommentBean.getId(), "1");
            PageInfo pageInfo = new PageInfo<>(planCommentRespBeans);
            if (!CollectionUtils.isEmpty(planCommentRespBeans)) {
                for (PlanCommentRespBean planCommentRespBean : planCommentRespBeans) {
                    PageHelper.startPage(1, 10);
                    List<PlanCommentRespBean> planCommentRespBean1 = planManageDao.getPlanCommentResp(planCommentRespBean.getId(), "2");
                    planCommentRespBean.setPlanCommentResp(new PageInfo(planCommentRespBean1));
                }
            }
            return new Result(pageInfo);
        } else {
            List<PlanCommentRespBean> planCommentRespBeans = planManageDao.getPlanCommentResp(queryPlanCommentBean.getId(), "2");
            PageInfo pageInfo = new PageInfo<>(planCommentRespBeans);
            if (!CollectionUtils.isEmpty(planCommentRespBeans)) {
                for (PlanCommentRespBean planCommentRespBean : planCommentRespBeans) {
                    PageHelper.startPage(1, 10);
                    List<PlanCommentRespBean> planCommentRespBean1 = planManageDao.getPlanCommentResp(planCommentRespBean.getId(), "2");
                    planCommentRespBean.setPlanCommentResp(new PageInfo(planCommentRespBean1));
                }
            }
            return new Result(pageInfo);
        }
    }

    private void buildPlanInfo(PlanInfoBean planInfoBean) {
        planInfoBean.setLevelName(planManageDao.getNameByType(planInfoBean.getLevelId()));
        planInfoBean.setEventTypeName(planManageDao.getNameByType(planInfoBean.getEventTypeId()));
        String emergencyTypeId = planInfoBean.getEmergencyTypeId();
        if (StringUtils.isNotEmpty(emergencyTypeId)) {
            String[] s = emergencyTypeId.split(",");
            if (s.length == 1) {
                String emergencyTypeName = planManageDao.getNameByType(s[0]);
                planInfoBean.setEmergencyTypeName(emergencyTypeName);
            } else if (s.length == 2) {
                String emergencyTypeName = planManageDao.getNameByType(s[0]) + "/" + planManageDao.getNameByType(s[1]);
                planInfoBean.setEmergencyTypeName(emergencyTypeName);
            } else {
                String[] data = new String[s.length];
                for (int i = 0; i < s.length; i++) {
                    data[i] = planManageDao.getNameByType(s[i]);
                }
                String emergencyTypeName = Arrays.toString(data);
                emergencyTypeName = emergencyTypeName.replace("[", "");
                emergencyTypeName = emergencyTypeName.replace("]", "");
                planInfoBean.setEmergencyTypeName(emergencyTypeName);
            }
        }
    }

    @Override
    public String queryPlanName(String riverName) {
        return planManageDao.queryPlanName(riverName);
    }

    /**
     * 获取预案行政级别
     *
     * @return
     */
    @Override
    public Result getLevel() {
        return new Result(planManageDao.getEventType("984"));
    }

    /**
     * 获取发布单位分类
     *
     * @return
     */
    @Override
    public Result getUnitType() {
        return new Result(planManageDao.getUnitType());
    }

    @Override
    public Result getLevelType() {
        return new Result(planManageDao.getLevelType());
    }

    /**
     * 获取单位
     *
     * @return
     */
    @Override
    public Result getUnit() {
        return new Result(planManageDao.getUnit());
    }

    /**
     * 新增单位
     *
     * @param name
     * @return
     */
    @Override
    public Result addUnit(String name) {
        planManageDao.addUnit(name);
        return new Result();
    }

    /**
     * 删除单位
     *
     * @param id
     * @return
     */
    @Override
    public Result delUnit(String id) {
        planManageDao.delUnit(id);
        return new Result();
    }

    @Override
    public Result getRegion(String level, String streetId) {
        if (!org.springframework.util.StringUtils.isEmpty(level) && !org.springframework.util.StringUtils.isEmpty(streetId)) {
            if (Integer.valueOf(level) < 400 || Integer.valueOf(level) >= 500) {
                streetId = null;
            }
        }
        return new Result(planManageDao.getRegion("1", streetId));
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result savePlanGroup(SavePlanGroupBean savePlanGroupBean) {
        if (savePlanGroupBean == null) {
            log.error("savePlanGroup savePlanGroupBean is null.");
            throw new BusinessException("savePlanGroupBean is null.");
        }
        log.info("savePlanGroup is begin. savePlanGroupBean:{}", JSONObject.toJSONString(savePlanGroupBean));
        try {
            // 获取预案分组的新增，修改，删除数据
            List<PlanGroupBean> addPlanGroupBeans = savePlanGroupBean.getAddPlanGroupBeans();
            List<PlanGroupBean> updatePlanGroupBeans = savePlanGroupBean.getUpdatePlanGroupBeans();
            List<String> delIds = savePlanGroupBean.getDelIds();

            // 判空新增，修改，删除，分别采取操作
            if (!CollectionUtils.isEmpty(addPlanGroupBeans)) {
                planManageDao.addPlanGroup(addPlanGroupBeans);
            }
            if (!CollectionUtils.isEmpty(updatePlanGroupBeans)) {
                planManageDao.updatePlanGroup(updatePlanGroupBeans);
            }
            if (!CollectionUtils.isEmpty(delIds)) {
                planManageDao.delPlanGroupById(delIds);
            }
        } catch (Exception e) {
            log.error("savePlanGroup is fail. e:{}", e);
            throw new BusinessException("savePlanGroup is fail." + e.getMessage());
        }
        log.info("savePlanGroup is end.");
        return new Result();
    }
}

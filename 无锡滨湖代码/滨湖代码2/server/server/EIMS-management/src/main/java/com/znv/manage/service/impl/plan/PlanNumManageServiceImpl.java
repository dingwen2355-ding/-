package com.znv.manage.service.impl.plan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.plan.*;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
//import com.znv.manage.dao.CommandMainMapper;
import com.znv.manage.dao.plan.PlanManageDao;
import com.znv.manage.dao.plan.PlanNumManageDao;
import com.znv.manage.service.plan.PlanNumManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 预案数字化管理
 *
 * @author chd
 * @date 2022/5/7
 */
@Service
@Slf4j
public class PlanNumManageServiceImpl implements PlanNumManageService {
    @Resource
    PlanNumManageDao planNumManageDao;

    @Resource
    PlanManageDao planManageDao;

//    @Autowired
//    CommandMainMapper commandMainMapper;

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addPlanNumInfo(PlanNumInfoBean planNumInfoBean) {
        log.info("addPlanNumInfo is begin. planNumInfoBean:{}", JSONObject.toJSONString(planNumInfoBean));
        try {
            if (StringUtils.isNotEmpty(planNumInfoBean.getCreateId())) {
                String createName = planManageDao.getUserName(planNumInfoBean.getCreateId());
                planNumInfoBean.setCreateName(createName);
            }
            buildPlanInfo(planNumInfoBean);
            // 添加预案基本信息
            planNumManageDao.addPlanNumInfo(planNumInfoBean);
            String id = planNumInfoBean.getId();
            List<PlanNumDataBean> planNumDataBeans = planNumInfoBean.getPlanNumDataBeans();
            if (!CollectionUtils.isEmpty(planNumDataBeans)) {
                for (PlanNumDataBean planNumDataBean : planNumDataBeans) {
                    planNumDataBean.setPlanNumId(id);
                }
                planNumManageDao.addPlanNumData(planNumDataBeans);
            }
        } catch (Exception e) {
            log.error("addPlanNumInfo is fail. e:{}", e);
            throw new BusinessException("addPlanNumInfo is fail." + e.getMessage());
        }
        log.info("addPlanNumInfo is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result savePlanNumData(SavePlanNumDataBean savePlanNumDataBean) {
        if (savePlanNumDataBean == null) {
            log.error("savePlanNumData savePlanNumDataBean is null.");
            throw new BusinessException("savePlanNumDataBean is null.");
        }
        log.info("savePlanNumData is begin. savePlanNumDataBean:{}", JSONObject.toJSONString(savePlanNumDataBean));
        try {
            // 获取预案相关资料的新增，修改，删除数据
            List<PlanNumDataBean> addPlanNumDataBeans = savePlanNumDataBean.getAddPlanNumDataBeans();
            List<PlanNumDataBean> updatePlanNumDataBeans = savePlanNumDataBean.getUpdatePlanNumDataBeans();
            List<String> delIds = savePlanNumDataBean.getDelIds();

            // 判空新增，修改，删除，分别采取操作
            if (!CollectionUtils.isEmpty(addPlanNumDataBeans)) {
                planNumManageDao.addPlanNumData(addPlanNumDataBeans);
            }
            // 更新
            if (!CollectionUtils.isEmpty(updatePlanNumDataBeans)) {
                planNumManageDao.updatePlanNumData(updatePlanNumDataBeans);
            }
            // 删除
            if (!CollectionUtils.isEmpty(delIds)) {
                planNumManageDao.delPlanNumDataById(delIds);
            }
        } catch (Exception e) {
            log.error("savePlanNumData is fail. e:{}", e);
            throw new BusinessException("savePlanNumData is fail." + e.getMessage());
        }
        log.info("savePlanNumData is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addPlanNumPre(AddPlanNumPreBean addPlanNumPreBean) {
        if (addPlanNumPreBean == null) {
            log.error("addPlanNumPre addPlanNumPreBean is null.");
            throw new BusinessException("savePlanNumDataBean is null.");
        }
        log.info("addPlanNumPre is begin. addPlanNumPreBean:{}", JSONObject.toJSONString(addPlanNumPreBean));
        try {
            // 新增前期准备
            planNumManageDao.addPlanNumPre(addPlanNumPreBean);
//            // 获取新增工作组
//            List<SavePlanNumGroupBean> savePlanNumGroupBeans = addPlanNumPreBean.getSavePlanNumGroupBeans();
//            if (!CollectionUtils.isEmpty(savePlanNumGroupBeans)) {
//                // 新增工作组
//                planNumManageDao.addPlanNumPreGroup(savePlanNumGroupBeans);
//                // 遍历新增工作组
//                for (SavePlanNumGroupBean savePlanNumGroupBean : savePlanNumGroupBeans) {
//                    List<GroupPersonBean> addGroupPersonBeans = savePlanNumGroupBean.getAddGroupPersonBeans();
//                    if (!CollectionUtils.isEmpty(addGroupPersonBeans)) {
//                        String groupId = planNumManageDao.getGroupId(savePlanNumGroupBean.getPlanNumId(), savePlanNumGroupBean.getGroupName());
//                        for (GroupPersonBean groupPersonBean : addGroupPersonBeans) {
//                            groupPersonBean.setGroupId(groupId);
//                        }
//                        // 开始新增组员
//                        planNumManageDao.addPlanNumPreGroupPerson(addGroupPersonBeans);
//                    }
//                }
//            }
            // 新增关联系统
            List<PlanNumSystemBean> planNumSystemBeans = addPlanNumPreBean.getPlanNumSystemBeans();
            if (!CollectionUtils.isEmpty(planNumSystemBeans)) {
                planNumManageDao.addPlanNumPreSystem(planNumSystemBeans);
            }
            countPlanNumPreGroup(addPlanNumPreBean.getPlanNumId());
        } catch (Exception e) {
            log.error("addPlanNumPre is fail. e:{}", e);
            if (e instanceof org.springframework.dao.DuplicateKeyException) {
                throw new BusinessException(ResultCodeEnum.SYSTEMERROR.getCode(), "添加失败，组名称重复!");
            } else {
                throw new BusinessException("addPlanNumPre is fail." + e.getMessage());
            }
        }
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result updatePlanNumPre(UpdatePlanNumPreBean updatePlanNumPreBean) {
        if (updatePlanNumPreBean == null) {
            log.error("updatePlanNumPre updatePlanNumPreBean is null.");
            throw new BusinessException("updatePlanNumPreBean is null.");
        }
        log.info("updatePlanNumPre is begin. updatePlanNumPreBean:{}", JSONObject.toJSONString(updatePlanNumPreBean));
        try {
            // 更新前期准备
            planNumManageDao.updatePlanNumPre(updatePlanNumPreBean);
            // 获取新增，删除，修改工作组
//            List<SavePlanNumGroupBean> addSavePlanNumGroupBeans = updatePlanNumPreBean.getAddSavePlanNumGroupBeans();
//            List<SavePlanNumGroupBean> updateSavePlanNumGroupBeans = updatePlanNumPreBean.getUpdateSavePlanNumGroupBeans();
//            List<String> delPlanNumGroupIds = updatePlanNumPreBean.getDelPlanNumGroupIds();
//            // 处理新增工作组
//            if (!CollectionUtils.isEmpty(addSavePlanNumGroupBeans)) {
//                // 新增工作组
//                planNumManageDao.addPlanNumPreGroup(addSavePlanNumGroupBeans);
//                // 遍历新增工作组
//                for (SavePlanNumGroupBean savePlanNumGroupBean : addSavePlanNumGroupBeans) {
//                    List<GroupPersonBean> addGroupPersonBeans = savePlanNumGroupBean.getAddGroupPersonBeans();
//                    if (!CollectionUtils.isEmpty(addGroupPersonBeans)) {
//                        String groupId = planNumManageDao.getGroupId(savePlanNumGroupBean.getPlanNumId(), savePlanNumGroupBean.getGroupName());
//                        for (GroupPersonBean groupPersonBean : addGroupPersonBeans) {
//                            groupPersonBean.setGroupId(groupId);
//                        }
//                        // 开始新增组员
//                        planNumManageDao.addPlanNumPreGroupPerson(addGroupPersonBeans);
//                    }
//                }
//            }
//
//            // 处理修改工作组
//            if (!CollectionUtils.isEmpty(updateSavePlanNumGroupBeans)) {
//                // 更新工作组
//                planNumManageDao.updatePlanNumPreGroup(updateSavePlanNumGroupBeans);
//                // 遍历更新工作组
//                for (SavePlanNumGroupBean savePlanNumGroupBean : updateSavePlanNumGroupBeans) {
//                    // 新增删除修改组员
//                    List<GroupPersonBean> addGroupPersonBeans = savePlanNumGroupBean.getAddGroupPersonBeans();
//                    List<GroupPersonBean> updateGroupPersonBeans = savePlanNumGroupBean.getUpdateGroupPersonBeans();
//                    List<String> delGroupPersonIds = savePlanNumGroupBean.getDelGroupPersonIds();
//
//                    if (!CollectionUtils.isEmpty(addGroupPersonBeans)) {
//                        // 开始新增组员
//                        planNumManageDao.addPlanNumPreGroupPerson(addGroupPersonBeans);
//                    }
//                    if (!CollectionUtils.isEmpty(updateGroupPersonBeans)) {
//                        // 开始修改组员
//                        planNumManageDao.updatePlanNumPreGroupPerson(updateGroupPersonBeans);
//                    }
//                    if (!CollectionUtils.isEmpty(delGroupPersonIds)) {
//                        // 开始删除组员
//                        planNumManageDao.delPlanNumPreGroupPersonById(delGroupPersonIds);
//                    }
//                }
//            }
//
//            // 处理删除工作组
//            if (!CollectionUtils.isEmpty(delPlanNumGroupIds)) {
//                // 删除工作组
//                planNumManageDao.delPlanNumPreGroup(delPlanNumGroupIds);
//                // 删除组下成员
//                planNumManageDao.delPlanNumPreGroupPerson(delPlanNumGroupIds);
//            }

            // 新增.修改.删除关联系统
            List<PlanNumSystemBean> addPlanNumSystemBeans = updatePlanNumPreBean.getAddPlanNumSystemBeans();
            List<PlanNumSystemBean> updatePlanNumSystemBeans = updatePlanNumPreBean.getUpdatePlanNumSystemBeans();
            List<String> delPlanNumSystemIds = updatePlanNumPreBean.getDelPlanNumSystemIds();
            if (!CollectionUtils.isEmpty(addPlanNumSystemBeans)) {
                planNumManageDao.addPlanNumPreSystem(addPlanNumSystemBeans);
            }
            if (!CollectionUtils.isEmpty(updatePlanNumSystemBeans)) {
                planNumManageDao.updatePlanNumPreSystem(updatePlanNumSystemBeans);
            }
            if (!CollectionUtils.isEmpty(delPlanNumSystemIds)) {
                planNumManageDao.delPlanNumPreSystem(delPlanNumSystemIds);
            }
            countPlanNumPreGroup(updatePlanNumPreBean.getPlanNumId());
        } catch (Exception e) {
            log.error("updatePlanNumPre is fail. e:{}", e);
            throw new BusinessException("updatePlanNumPre is fail." + e.getMessage());
        }
        return new Result();
    }

    @Override
    public Result queryPlanNum(QueryPlanNumBean queryPlanNumBean) {
        PageHelper.startPage(queryPlanNumBean.getPageNum(), queryPlanNumBean.getPageSize());
        List<PlanNumInfoBean> planNumInfoBeans = planNumManageDao.queryPlanNum(queryPlanNumBean);
        for (PlanNumInfoBean planNumInfoBean : planNumInfoBeans) {
            List<String> fileUrls = planNumManageDao.getNumFileUrl(planNumInfoBean.getId());
            planNumInfoBean.setFileUrls(fileUrls);
            List<Map<String, Object>> fileData = planNumManageDao.getFileData(planNumInfoBean.getId());
            planNumInfoBean.setFileData(fileData);

        }
        return new Result(new PageInfo<>(planNumInfoBeans));
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result updatePlanNumInfo(PlanNumInfoBean planNumInfoBean) {
        if (planNumInfoBean == null) {
            log.error("updatePlanNumInfo planNumInfoBean is null.");
            throw new BusinessException("planNumInfoBean is null.");
        }
        log.info("planNumInfoBean is begin. planNumInfoBean:{}", JSONObject.toJSONString(planNumInfoBean));
        try {
            // 添加预案基本信息
            buildPlanInfo(planNumInfoBean);
            // 更新预案基本信息
            planNumManageDao.updatePlanNumInfo(planNumInfoBean);
            List<PlanNumDataBean> planNumDataBeans = planNumInfoBean.getPlanNumDataBeans();
            if (!CollectionUtils.isEmpty(planNumDataBeans)) {
                String planId = planNumInfoBean.getId();
                List<String> delIds = new ArrayList();
                List<PlanNumDataBean> addPlanNumDataBeans = new ArrayList();
                List<PlanNumDataBean> planNumDataBeansLocal = planNumManageDao.getPlanNumDataByPlanId(planId);
                // 获取新增相关信息数据
                for (PlanNumDataBean planNumDataBean : planNumDataBeans) {
                    boolean flag = false;
                    for (PlanNumDataBean planNumDataBeanLocal : planNumDataBeansLocal) {
                        if (planNumDataBean.getPlanNumId().equals(planNumDataBeanLocal.getPlanNumId())
                                && planNumDataBean.getFileUrl().equals(planNumDataBeanLocal.getFileUrl())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        addPlanNumDataBeans.add(planNumDataBean);
                    }
                }
                // 获取删除的相关信息数据
                for (PlanNumDataBean planNumDataBeanLocal : planNumDataBeansLocal) {
                    boolean flag = false;
                    for (PlanNumDataBean planNumDataBean : planNumDataBeans) {
                        if (planNumDataBean.getPlanNumId().equals(planNumDataBeanLocal.getPlanNumId())
                                && planNumDataBean.getFileUrl().equals(planNumDataBeanLocal.getFileUrl())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        delIds.add(planNumDataBeanLocal.getId());
                    }
                }

                if (!CollectionUtils.isEmpty(addPlanNumDataBeans)) {
                    planNumManageDao.addPlanNumData(addPlanNumDataBeans);
                }

                if (!CollectionUtils.isEmpty(delIds)) {
                    planNumManageDao.delPlanNumDataById(delIds);
                }
            } else {
                String id = planNumInfoBean.getId();
                List<String> ids = new ArrayList();
                ids.add(id);
                planNumManageDao.delPlanNumData(ids);
            }

        } catch (Exception e) {
            log.error("updatePlanNumInfo is fail. e:{}", e);
            throw new BusinessException("updatePlanNumInfo is fail." + e.getMessage());
        }
        log.info("updatePlanNumInfo is end.");
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result savePlanNumMonitor(SavePlanNumMonitorBean savePlanNumMonitorBean) {
        if (savePlanNumMonitorBean == null) {
            log.error("savePlanNumMonitor savePlanNumMonitorBean is null.");
            throw new BusinessException("savePlanNumMonitorBean is null.");
        }
        log.info("savePlanNumMonitor is begin. savePlanNumMonitorBean:{}", JSONObject.toJSONString(savePlanNumMonitorBean));
        try {
            // 获取预案相关资料的新增，修改，删除数据
            List<PlanNumMonitorBean> addPlanNumMonitorBeans = savePlanNumMonitorBean.getAddPlanNumMonitorBeans();
            List<PlanNumMonitorBean> updatePlanNumMonitorBeans = savePlanNumMonitorBean.getUpdatePlanNumMonitorBeans();
            List<String> delIds = savePlanNumMonitorBean.getDelIds();

            // 判空新增，修改，删除，分别采取操作
            if (!CollectionUtils.isEmpty(addPlanNumMonitorBeans)) {
                buildMonitorType(addPlanNumMonitorBeans);
                planNumManageDao.addPlanNumMonitor(addPlanNumMonitorBeans);
                String planNumId = addPlanNumMonitorBeans.get(0).getPlanNumId();
                if (StringUtils.isNotEmpty(planNumId)) {
                    List<String> eventIds = planNumManageDao.getEventIds(planNumId);
                    if (!CollectionUtils.isEmpty(eventIds)) {
                        List<String> groupNames = new ArrayList();
                        for (PlanNumMonitorBean planNumMonitorBean : addPlanNumMonitorBeans) {
                            groupNames.add(planNumMonitorBean.getMonitorGroup());
                        }
                        for (String eventId : eventIds) {
//                            commandMainMapper.addEmergencyResp(eventId, planNumId, groupNames);
                        }
                    }
                }
            }
            // 更新
            if (!CollectionUtils.isEmpty(updatePlanNumMonitorBeans)) {
                buildMonitorType(updatePlanNumMonitorBeans);
                planNumManageDao.updatePlanNumMonitor(updatePlanNumMonitorBeans);
            }
            // 删除
            if (!CollectionUtils.isEmpty(delIds)) {
                planNumManageDao.delPlanNumMonitorById(delIds);
            }
        } catch (Exception e) {
            log.error("savePlanNumData is fail. e:{}", e);
            throw new BusinessException("savePlanNumData is fail." + e.getMessage());
        }
        log.info("savePlanNumData is end.");
        return new Result();
    }

    @Override
    public Result addPlanNumEmergencyResponse(PlanNumEmergencyResponseBean planNumEmergencyResponseBean) {
        if (planNumEmergencyResponseBean == null) {
            log.error("addPlanNumEmergencyResponse planNumEmergencyResponseBean is null.");
            throw new BusinessException("planNumEmergencyResponseBean is null.");
        }
        buildPlanNumEmergency(planNumEmergencyResponseBean);
        planNumManageDao.addPlanNumEmergencyResponse(planNumEmergencyResponseBean);
        return new Result();
    }

    @Override
    public Result updatePlanNumEmergencyResponse(PlanNumEmergencyResponseBean planNumEmergencyResponseBean) {
        if (planNumEmergencyResponseBean == null) {
            log.error("updatePlanNumEmergencyResponse planNumEmergencyResponseBean is null.");
            throw new BusinessException("planNumEmergencyResponseBean is null.");
        }
        buildPlanNumEmergency(planNumEmergencyResponseBean);
        planNumManageDao.updatePlanNumEmergencyResponse(planNumEmergencyResponseBean);
        return new Result();
    }

    @Override
    public Result addPlanNumEmergencySupport(PlanNumEmergencySupportBean planNumEmergencySupportBean) {
        if (planNumEmergencySupportBean == null) {
            log.error("addPlanNumEmergencySupport planNumEmergencySupportBean is null.");
            throw new BusinessException("planNumEmergencySupportBean is null.");
        }
        planNumManageDao.addPlanNumEmergencySupport(planNumEmergencySupportBean);
        return new Result();
    }

    @Override
    public Result updatePlanNumEmergencySupport(PlanNumEmergencySupportBean planNumEmergencySupportBean) {
        if (planNumEmergencySupportBean == null) {
            log.error("updatePlanNumEmergencySupport planNumEmergencySupportBean is null.");
            throw new BusinessException("planNumEmergencySupportBean is null.");
        }
        planNumManageDao.updatePlanNumEmergencySupport(planNumEmergencySupportBean);
        return new Result();
    }

    @Override
    public Result addPlanNumEmergencyStop(PlanNumEmergencyStopBean planNumEmergencyStopBean) {
        if (planNumEmergencyStopBean == null) {
            log.error("addPlanNumEmergencyStop planNumEmergencyStopBean is null.");
            throw new BusinessException("planNumEmergencyStopBean is null.");
        }
        planNumManageDao.addPlanNumEmergencyStop(planNumEmergencyStopBean);
        return new Result();
    }

    @Override
    public Result updatePlanNumEmergencyStop(PlanNumEmergencyStopBean planNumEmergencyStopBean) {
        if (planNumEmergencyStopBean == null) {
            log.error("updatePlanNumEmergencyStop planNumEmergencyStopBean is null.");
            throw new BusinessException("planNumEmergencyStopBean is null.");
        }
        planNumManageDao.updatePlanNumEmergencyStop(planNumEmergencyStopBean);
        return new Result();
    }

    @Override
    public Result queryPlanNumData(String id, String name) {
        if (StringUtils.isEmpty(id) && StringUtils.isEmpty(name)) {
            return new Result();
        }
        if (!StringUtils.isEmpty(name)) {
            id = planNumManageDao.getPlanNumIdByName(name);
        }
        if (StringUtils.isEmpty(id)) {
            return new Result();
        }
        // 获取前期主备信息
        PlanNumPreBean planNumPreBean = planNumManageDao.getPlanNumPreBeanByPlanId(id);
        // 获取组
        List<PlanNumGroupBean> planNumGroupBeans = planNumManageDao.getPlanNumGroupBeanByPlanId(id);
        if (!CollectionUtils.isEmpty(planNumGroupBeans)) {
            for (PlanNumGroupBean planNumGroupBean : planNumGroupBeans) {
                // 获取组员
                List<GroupPersonBean> groupPersonBeans = planNumManageDao.getGroupPersonBean(planNumGroupBean.getId());
                planNumGroupBean.setGroupPersonBeans(groupPersonBeans);
            }
        }
        if (planNumPreBean != null) {
            List<PlanNumSystemBean> planNumSystemBeans = planNumManageDao.getPlanNumSystemBean(id);
            planNumPreBean.setPlanNumGroupBeans(planNumGroupBeans);
            planNumPreBean.setPlanNumSystemBeans(planNumSystemBeans);
        } else {
            planNumPreBean = new PlanNumPreBean();
            CountPlanNumPreGroupBean countPlanNumPreGroupBean = planNumManageDao.countPlanNumPreGroup(id);
            planNumPreBean.setGroupNum(countPlanNumPreGroupBean.getGroupNum());
            planNumPreBean.setPersonNum(countPlanNumPreGroupBean.getPersonNum());
            List<PlanNumSystemBean> planNumSystemBeans = planNumManageDao.getPlanNumSystemBean(id);
            planNumPreBean.setPlanNumGroupBeans(planNumGroupBeans);
            planNumPreBean.setPlanNumSystemBeans(planNumSystemBeans);
        }
        // 获取关联系统
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("planNumPreBean", planNumPreBean);

        // 获取检测预警
        List<PlanNumMonitorBean> planNumMonitorBeans = planNumManageDao.getPlanNumMonitorBean(id);
        jsonObject.put("planNumMonitorBeans", planNumMonitorBeans);
        // 获取应急响应
        List<PlanNumEmergencyResponseBean> planNumEmergencyResponseBeans = planNumManageDao.getPlanNumEmergencyResponseBean(id);
        jsonObject.put("planNumEmergencyResponseBeans", planNumEmergencyResponseBeans);
        // 获取应急保障
        PlanNumEmergencySupportBean planNumEmergencySupportBean = planNumManageDao.getPlanNumEmergencySupportBean(id);
        jsonObject.put("planNumEmergencySupportBean", planNumEmergencySupportBean);
        // 获取赢响应终止
        PlanNumEmergencyStopBean planNumEmergencyStopBean = planNumManageDao.getPlanNumEmergencyStopBean(id);
        jsonObject.put("planNumEmergencyStopBean", planNumEmergencyStopBean);

        // 获取相关资料
        List<PlanNumDataBean> planNumDataBeans = planNumManageDao.getPlanNumDataBean(id);
        jsonObject.put("planNumDataBeans", planNumDataBeans);

        String rewardDuty = planNumManageDao.queryRewardDuty(id);
        jsonObject.put("rewardDuty", rewardDuty);
        return new Result(jsonObject);
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delPlanNum(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("delPlanNum ids is null.");
            throw new BusinessException("ids is null.");
        }
        log.info("delPlanNum is begin. ids:{}", JSONObject.toJSONString(ids));
        try {
            // 删除预案数字化
            planNumManageDao.delPlanNumInfo(ids);
            // 删除前期准备
            planNumManageDao.delPlanNumPre(ids);
            // 删除工作组
            planNumManageDao.delPlanNumGroup(ids);
            // 删除组员
            planNumManageDao.delPlanNumGroupPerson(ids);
            // 删除关联系统表
            planNumManageDao.delPlanNumSystem(ids);
            // 删除相关资料
            planNumManageDao.delPlanNumData(ids);
            // 删除监测预警
            planNumManageDao.delPlanNumMonitor(ids);
            // 删除应急响应
            planNumManageDao.delPlanNumEmergencyResponse(ids);
            // 删除应急保障
            planNumManageDao.delPlanNumEmergencySupport(ids);
            // 删除响应终止
            planNumManageDao.delPlanNumEmergencyStop(ids);

        } catch (Exception e) {
            log.error("delPlanNum is fail. e:{}", e);
            throw new BusinessException("delPlanNum is fail." + e.getMessage());
        }
        log.info("delPlanNum is end.");
        return new Result();
    }

    @Override
    public Result addPlanNumGroup(PlanNumGroupBean planNumGroupBean) {
        if (planNumGroupBean == null) {
            log.error("planNumGroupBean is null.");
            throw new BusinessException("planNumGroupBean is null.");
        }
        planNumManageDao.addPlanNumGroup(planNumGroupBean);
        countPlanNumPreGroup(planNumGroupBean.getPlanNumId());
        return new Result();
    }

    @Override
    public Result updatePlanNumGroup(PlanNumGroupBean planNumGroupBean) {
        if (planNumGroupBean == null) {
            log.error("planNumGroupBean is null.");
            throw new BusinessException("planNumGroupBean is null.");
        }
        planNumManageDao.updatePlanNumGroup(planNumGroupBean);
        return new Result();
    }

    @Override
    public Result delPlanNumGroup(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null.");
            throw new BusinessException("id is null.");
        }
        planNumManageDao.delGroup(id);
        List<String> ids = new ArrayList();
        ids.add(id);
        planNumManageDao.delPlanNumPreGroupPerson(ids);
        countPlanNumPreGroup(id);
        return new Result();
    }

    @Override
    public Result addPlanNumGroupPerson(List<GroupPersonBean> groupPersonBeans) {
        if (CollectionUtils.isEmpty(groupPersonBeans)) {
            log.error("groupPersonBeans is null.");
            throw new BusinessException("groupPersonBeans is null.");
        }
        planNumManageDao.addPlanNumPreGroupPerson(groupPersonBeans);
        countPlanNumPreGroup(groupPersonBeans.get(0).getPlanNumId());
        return new Result();
    }

    @Override
    public Result delPlanNumGroupPerson(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("ids is null.");
            throw new BusinessException("ids is null.");
        }
        List<String> planNumIds = planNumManageDao.getPlanNumIdById(ids);
        planNumManageDao.delPlanNumPreGroupPersonById(ids);
        if (!CollectionUtils.isEmpty(planNumIds)) {
            for (String planNumId : planNumIds) {
                countPlanNumPreGroup(planNumId);
            }
        }
        return new Result();
    }

    @Override
    public Result getPlanNumGroupPerson(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null.");
            throw new BusinessException("id is null.");
        }
        List<GroupPersonBean> groupPersonBeans = planNumManageDao.getGroupPersonBean(id);
        return new Result(groupPersonBeans);
    }

    @Override
    public Result updatePlanNumRewardDuty(RewardDutyBean rewardDutyBean) {
        if (rewardDutyBean == null) {
            log.error("updatePlanNumRewardDuty rewardDutyBean is null.");
            throw new BusinessException("rewardDutyBean is null.");
        }
        log.info("updatePlanNumRewardDuty is begin. rewardDutyBean:{}", JSONObject.toJSONString(rewardDutyBean));
        planNumManageDao.updateRewardDuty(rewardDutyBean);
        return new Result();
    }

    private void countPlanNumPreGroup(String planNumId) {
        CountPlanNumPreGroupBean countPlanNumPreGroupBean = planNumManageDao.countPlanNumPreGroup(planNumId);
        countPlanNumPreGroupBean.setId(planNumId);
        planNumManageDao.updatePlanNumGroupCount(countPlanNumPreGroupBean);
    }

    private void buildPlanInfo(PlanNumInfoBean planNumInfoBean) {
        try {
            String emergencyTypeId = planNumInfoBean.getEmergencyTypeId();
            if (StringUtils.isNotEmpty(emergencyTypeId)) {
                String[] s = emergencyTypeId.split(",");
                String emergencyTypeName = planManageDao.getNameByType(s[0]) + "/" + planManageDao.getNameByType(s[1]);
                planNumInfoBean.setEmergencyTypeName(emergencyTypeName);
            }
        } catch (Exception e) {
            log.error("buildPlanInfo is fail. e:{}", e);
            throw new BusinessException("buildPlanInfo is fail." + e.getMessage());
        }
    }

    private void buildMonitorType(List<PlanNumMonitorBean> addPlanNumMonitorBeans) {
        for (PlanNumMonitorBean planNumMonitorBean : addPlanNumMonitorBeans) {
            String monitorTypeId = planNumMonitorBean.getMonitorTypeId();
            String[] s = monitorTypeId.split(",");
            String monitorTypeName = EmergencyConstant.getMonitorTypeMap().get(s[0]) +
                    "/" + EmergencyConstant.getMonitorTypeMap().get(s[1]);
            planNumMonitorBean.setMonitorTypeName(monitorTypeName);
        }
    }

    private void buildPlanNumEmergency(PlanNumEmergencyResponseBean planNumEmergencyResponseBean) {
        String levelId = planNumEmergencyResponseBean.getLevelId();
        if (StringUtils.isNotEmpty(levelId)) {
            String levelName = EmergencyConstant.getEmergencyResponseTypeMap().get(levelId);
            planNumEmergencyResponseBean.setLevelName(levelName);
        }
    }
}

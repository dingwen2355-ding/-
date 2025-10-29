package com.znv.manage.service.impl.information;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.*;
import com.znv.manage.common.bean.plan.EmergencyConstant;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.ThreadPool;
import com.znv.manage.dao.information.MesManageDao;
import com.znv.manage.dao.plan.PlanManageDao;
import com.znv.manage.service.information.CallAndTextService;
import com.znv.manage.service.information.MesManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 信息管理
 *
 * @author chd
 * @date 2022/6/1
 */
@Service
@Slf4j
public class MesManageServiceImpl implements MesManageService {

    @Resource
    PlanManageDao planManageDao;

    @Resource
    MesManageDao mesManageDao;

    @Resource
    CallAndTextService callAndTextService;

    public static final String MES_STATUS_OK = "2";

    public static final String MES_REL_TYPE = "1";

    protected static final Map<String, String> REL_TYPE_MAP = new HashMap();

    static {
        REL_TYPE_MAP.put("1", "融合通讯");
        REL_TYPE_MAP.put("2", "站内信");
    }

    protected static final Map<String, String> MES_STATUS_MAP = new HashMap();

    static {
//        MES_STATUS_MAP.put("草稿", "0");
        MES_STATUS_MAP.put("流程中", "1");
        MES_STATUS_MAP.put("结束", "2,3");
    }

    @Override
    public Result addMesRel(MesRelBean mesRelBean) {
        if (mesRelBean == null) {
            throw new BusinessException(ResultCodeEnum.INVALIDREQUEST.getCode(), "mesRelBean is null.");
        }
        if (StringUtils.isNotEmpty(mesRelBean.getCreateId())) {
            String createName = planManageDao.getUserName(mesRelBean.getCreateId());
            mesRelBean.setCreateName(createName);
        }
        mesRelBean.setTypeName(REL_TYPE_MAP.get(mesRelBean.getTypeId()));
        mesManageDao.addMesRel(mesRelBean);
        return new Result();
    }

    @Override
    public Result editMesRel(MesRelBean mesRelBean) {
        if (mesRelBean == null) {
            throw new BusinessException(ResultCodeEnum.INVALIDREQUEST.getCode(), "mesRelBean is null.");
        }
        mesRelBean.setTypeName(REL_TYPE_MAP.get(mesRelBean.getTypeId()));
        mesManageDao.editMesRel(mesRelBean);
        return new Result();
    }

    @Override
    public Result delMesRel(String id) {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(ResultCodeEnum.INVALIDREQUEST.getCode(), "id is null.");
        }
        mesManageDao.delMesRel(id);
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delMesInfo(String id) {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(ResultCodeEnum.INVALIDREQUEST.getCode(), "id is null.");
        }
        mesManageDao.delMesInfo(id);
        mesManageDao.delMesRelStatus(id);
        return new Result();
    }

    @Override
    public Result getMesRel(String text, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<MesRelBean> mesRelBeans = mesManageDao.getMesRel(text);
        return new Result(new PageInfo(mesRelBeans));
    }

    @Override
    public Result getMesReview(GetMesReviewBean getMesReviewBean) {
        if (getMesReviewBean.getPageNum() != null && getMesReviewBean.getPageSize() != null) {
            PageHelper.startPage(getMesReviewBean.getPageNum(), getMesReviewBean.getPageSize());
        }
        List<MesReviewBean> mesReviewBeans = mesManageDao.getMesReview(getMesReviewBean);
        return new Result(new PageInfo(mesReviewBeans));
    }

    @Override
    public Result getMsgTypeTree() {
        return new Result(EmergencyConstant.getMsgTypeTree());
    }

    @Override
    public Result addMesGroup(MesGroupBean mesGroupBean) {
        if (mesGroupBean == null) {
            log.error("mesGroupBean is null.");
            throw new BusinessException("mesGroupBean is null.");
        }
        String userId = mesGroupBean.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            mesGroupBean.setPrecinctId(mesManageDao.getPrecinctId(userId));
        }
        mesManageDao.addMesGroup(mesGroupBean);
        return new Result();
    }

    @Override
    public Result updateMesGroup(MesGroupBean mesGroupBean) {
        if (mesGroupBean == null) {
            log.error("mesGroupBean is null.");
            throw new BusinessException("mesGroupBean is null.");
        }
        mesManageDao.updateMesGroup(mesGroupBean);
        return new Result();
    }

    @Override
    public Result getMesGroupPerson(String id, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null.");
            throw new BusinessException("id is null.");
        }
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<MesGroupBeanPerson> groupPersonBeans = mesManageDao.getMesGroupPerson(id);
        return new Result(new PageInfo(groupPersonBeans));
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delMesGroup(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null.");
            throw new BusinessException("id is null.");
        }
        List<String> personIds = mesManageDao.getPersonIdById(id);
        mesManageDao.delMesGroup(id);
        mesManageDao.delMesGroupPerson(id);
        // 同步删除选中删除的人的信息发送状态表
        if (!CollectionUtils.isEmpty(personIds)) {
            mesManageDao.delMesRelPerson(personIds);
        }
        return new Result();
    }

    @Override
    public Result addMesGroupPerson(MesGroupBeanPerson mesGroupBeanPerson) {
        if (mesGroupBeanPerson == null) {
            log.error("mesGroupBeanPerson is null.");
            throw new BusinessException("mesGroupBeanPerson is null.");
        }
        mesManageDao.addMesGroupPerson(mesGroupBeanPerson);
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delMesGroupPerson(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.error("ids is null.");
            throw new BusinessException("ids is null.");
        }
        mesManageDao.delMesGroupPersonById(ids);
        // 同步删除选中删除的人的信息发送状态表
        mesManageDao.delMesRelPerson(ids);
        return new Result();
    }

    @Override
    public Result getMesGroup(String userId, Integer pageNum, Integer pageSize) {
        String precinctId = null;
        if (StringUtils.isNotBlank(userId)) {
            precinctId = mesManageDao.getPrecinctId(userId);
            if (StringUtils.isBlank(precinctId)) {
                return new Result();
            }
        } else {
            return new Result();
        }
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<MesGroupBean> mesGroupBeans = mesManageDao.getMesGroup(userId, precinctId);
        return new Result(new PageInfo(mesGroupBeans));
    }

//    @Resource
//    DutyService dutyService;

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addMesInfo(MesInfoBean mesInfoBean) {
        if (mesInfoBean == null) {
            log.error("mesInfoBean is null.");
            throw new BusinessException("mesInfoBean is null.");
        }
        log.info("addMesInfo mesInfoBean:{}", JSONObject.toJSONString(mesInfoBean));
        String userId = mesInfoBean.getCreateId();
        if (StringUtils.isNotBlank(userId)) {
            String userName = planManageDao.getUserName(userId);
            mesInfoBean.setCreateName(userName);
            // 发布人，新增为空时，取当前用户
            if (StringUtils.isBlank(mesInfoBean.getRelPerson())) {
                mesInfoBean.setRelPerson(userName);
            }
        }
        // 发布时间，新增为空时，取当前时间
        if (StringUtils.isEmpty(mesInfoBean.getRelTime())) {
            mesInfoBean.setRelTime(DateUtil.getStringDate());
        }
        buildMesInfo(mesInfoBean);
        mesInfoBean.setProcessTime(DateUtil.getStringDate());
        mesInfoBean.setStatus("0");
        mesManageDao.addMesInfo(mesInfoBean);

//        // 添加信息发起日志
//        DutyLogBean dutyLogBean = new DutyLogBean();
//        dutyLogBean.setUserId(mesInfoBean.getUserId());
//        dutyLogBean.setType("信息发起");
//        dutyLogBean.setMatter(mesInfoBean.getMesName());
//        dutyLogBean.setRelId(mesInfoBean.getId());
//        dutyService.addDutyLogBean(dutyLogBean);

        List<String> personIds = mesInfoBean.getPersonIds();
        if (!CollectionUtils.isEmpty(personIds)) {
            mesInfoBean.setPersonIds(personIds.stream().distinct().collect(Collectors.toList()));
            mesManageDao.addMesRelStatus(mesInfoBean);
        }

        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result editMesInfo(MesInfoBean mesInfoBean) {
        if (mesInfoBean == null) {
            log.error("mesInfoBean is null.");
            throw new BusinessException("mesInfoBean is null.");
        }
        log.info("addMesInfo mesInfoBean:{}", JSONObject.toJSONString(mesInfoBean));
        buildMesInfo(mesInfoBean);
        mesManageDao.editMesInfo(mesInfoBean);
        mesManageDao.delMesRelStatus(mesInfoBean.getId());
        List<String> personIds = mesInfoBean.getPersonIds();
        if (!CollectionUtils.isEmpty(personIds)) {
            String relObjectId = mesManageDao.getRelObjectIdById(mesInfoBean.getId());
            mesInfoBean.setRelObjectId(relObjectId);
            mesInfoBean.setPersonIds(personIds.stream().distinct().collect(Collectors.toList()));
            mesManageDao.addMesRelStatus(mesInfoBean);
        }
        return new Result();
    }

    @Override
    public Result getMesInfo(GetMesInfoBean getMesInfoBean) {
        if (getMesInfoBean == null) {
            getMesInfoBean = new GetMesInfoBean();
        }
        Integer pageNum = getMesInfoBean.getPageNum();
        Integer pageSize = getMesInfoBean.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<MesInfoBean> mesInfoBeans = mesManageDao.getMesInfo(getMesInfoBean);
        if (!CollectionUtils.isEmpty(mesInfoBeans)) {
            for (MesInfoBean mesInfoBean : mesInfoBeans) {
                String allPersonIds = mesInfoBean.getAllPersonIds();
                if (StringUtils.isNotBlank(allPersonIds)) {
                    mesInfoBean.setPersonIds(Arrays.asList(allPersonIds.split(",")));
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", mesInfoBean.getRelId());
                jsonObject.put("relName", mesInfoBean.getRelName());
                jsonObject.put("typeId", mesInfoBean.getRelObjectId());
                jsonObject.put("typeName", mesInfoBean.getRelObjectName());
                mesInfoBean.setJsonObject(jsonObject);
            }
        }
        return new Result(new PageInfo(mesInfoBeans));
    }

    /**
     * 根据id查询我的信息
     *
     * @param id
     * @return
     */
    @Override
    public Result getMesById(String id) {
        Result result = new Result();
        MesInfoBean mesInfoBean = mesManageDao.getMesById(id);
        String allPersonIds = mesInfoBean.getAllPersonIds();
        if (StringUtils.isNotBlank(allPersonIds)) {
            mesInfoBean.setPersonIds(Arrays.asList(allPersonIds.split(",")));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", mesInfoBean.getRelId());
        jsonObject.put("relName", mesInfoBean.getRelName());
        jsonObject.put("typeId", mesInfoBean.getRelObjectId());
        jsonObject.put("typeName", mesInfoBean.getRelObjectName());
        mesInfoBean.setJsonObject(jsonObject);
        result.setData(mesInfoBean);
        return result;
    }

    @Override
    public Result getMesInfoProcess(GetMesInfoBean getMesInfoBean) {
        if (getMesInfoBean == null) {
            getMesInfoBean = new GetMesInfoBean();
        }
        String text = getMesInfoBean.getText();
        if (StringUtils.isNotBlank(text)) {
            getMesInfoBean.setStatus(MES_STATUS_MAP.get(text));
        }
        Integer pageNum = getMesInfoBean.getPageNum();
        Integer pageSize = getMesInfoBean.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<MesInfoBean> mesInfoBeans = mesManageDao.getMesInfoProcess(getMesInfoBean);
        if (!CollectionUtils.isEmpty(mesInfoBeans)) {
            for (MesInfoBean mesInfoBean : mesInfoBeans) {
                String status = mesInfoBean.getStatus();
                if (status.equals("1")) {
                    mesInfoBean.setProcess("流程中");
                } else {
                    mesInfoBean.setProcess("结束");
                }
                String allPersonIds = mesInfoBean.getAllPersonIds();
                if (StringUtils.isNotBlank(allPersonIds)) {
                    mesInfoBean.setPersonIds(Arrays.asList(allPersonIds.split(",")));
                }
            }
        }
        return new Result(new PageInfo(mesInfoBeans));
    }

    @Override
    public Result auditMesInfo(AuditMesBean auditMesBean) {
        if (auditMesBean == null) {
            return new Result();
        }
        log.info("auditMesBean:{}", JSONObject.toJSONString(auditMesBean));
        String auditorId = auditMesBean.getAuditorId();
        if (StringUtils.isNotBlank(auditorId)) {
            String auditorName = planManageDao.getUserName(auditorId);
            auditMesBean.setAuditorName(auditorName);
        }
        auditMesBean.setProcessTime(DateUtil.getStringDate());
        mesManageDao.auditMesInfo(auditMesBean);

//        // 添加信息发布日志
//        DutyLogBean dutyLogBean = new DutyLogBean();
//        dutyLogBean.setUserId(auditMesBean.getUserId());
//        dutyLogBean.setType("信息发布");
//        dutyLogBean.setMatter(auditMesBean.getMesName());
//        dutyLogBean.setRelId(auditMesBean.getId());
//        dutyService.addDutyLogBean(dutyLogBean);

        if (MES_STATUS_OK.equals(auditMesBean.getStatus())) {
            mesManageDao.updateMesRelStatus(auditMesBean.getId(), "3", null);
            ThreadPool.getInstance().executeScheduled(5, () -> sendMes(auditMesBean.getId()));
        }
        return new Result();
    }

    @Override
    public PageInfo<List<MesRelLogBean>> getMesRelLog(GetMesRelLogBean getMesRelLogBean) {
        if (getMesRelLogBean == null) {
            getMesRelLogBean = new GetMesRelLogBean();
        }
        PageHelper.startPage(getMesRelLogBean.getPageNum(), getMesRelLogBean.getPageSize());

        List<MesRelLogBean> mesRelLogBeans = mesManageDao.getMesRelLog(getMesRelLogBean);

        return new PageInfo(mesRelLogBeans);
    }

    @Override
    public PageInfo<List<MesRelPersonBean>> getMesRelStatus(String id, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<MesRelPersonBean> mesRelPersonBeans = mesManageDao.getMesRelStatusById(id);
        return new PageInfo(mesRelPersonBeans);
    }

    private void buildMesInfo(MesInfoBean mesInfoBean) {
        String relObjectId = mesInfoBean.getRelObjectId();
        if (StringUtils.isNotEmpty(relObjectId)) {
            mesInfoBean.setRelObjectName(REL_TYPE_MAP.get(mesInfoBean.getRelObjectId()));
        }

        String relId = mesInfoBean.getRelId();
        if (StringUtils.isNotEmpty(relId)) {
            mesInfoBean.setRelName(mesManageDao.getNameById(relId));
        }

        String typeId = mesInfoBean.getTypeId();
        if (StringUtils.isNotEmpty(typeId)) {
            String[] s = typeId.split(",");
            StringBuilder sb = new StringBuilder();
            if (s != null && s.length > 0) {
                for (Object object : EmergencyConstant.getMsgTypeTree()) {
                    JSONObject jsonObject = (JSONObject) object;
                    if (jsonObject.getString("value").equals(s[0])) {
                        sb.append(jsonObject.getString("label"));
                        if (s.length > 1) {
                            List<JSONObject> jsonObjects = jsonObject.getObject("children", List.class);
                            for (JSONObject jsonObject1 : jsonObjects) {
                                if (jsonObject1.getString("value").equals(s[1])) {
                                    sb.append("/").append(jsonObject1.getString("label"));
                                    break;
                                }
                            }
                        }
                        mesInfoBean.setTypeName(sb.toString());
                        break;
                    }
                }
            }
        }
    }

    private void sendMes(String id) {
        log.info("sendMes is begin... id:{}", id);
        try {
            List<MesRelStatusBean> mesRelStatusBeans = mesManageDao.getMesRelStatusBean(id);
            if (CollectionUtils.isEmpty(mesRelStatusBeans)) {
                log.info("mesRelStatusBeans is null.");
                log.info("sendMes is end.");
                return;
            }
            String relType = mesRelStatusBeans.get(0).getRelType();
            if (!(MES_REL_TYPE.equals(relType))) {
                log.info("relType is not 1,not need sendMes.");
                log.info("sendMes is end.");
                return;
            }
            for (MesRelStatusBean mesRelStatusBean : mesRelStatusBeans) {
                try {
                    // 短信发送接口为浪潮接口
                    JSONObject responseJson = callAndTextService.text(mesRelStatusBean.getPhoneNumber(), mesRelStatusBean.getContent());

                    if (responseJson.getJSONObject("status") != null
                            && responseJson.getJSONObject("status").getInteger("code") != null &&
                            responseJson.getJSONObject("status").getInteger("code").equals(0)) {
                        mesManageDao.updateMesRelStatus(mesRelStatusBean.getMesId(), "0", mesRelStatusBean.getPersonId());
                    } else {
                        mesManageDao.updateMesRelStatus(mesRelStatusBean.getMesId(), "1", mesRelStatusBean.getPersonId());
                    }
                } catch (Exception e) {
                    log.error("sendMes is fail... mesRelStatusBean:{}", JSONObject.toJSONString(mesRelStatusBean));
                    log.error("sendMes is fail... e:{}", e);
                    try {
                        mesManageDao.updateMesRelStatus(mesRelStatusBean.getMesId(), "1", mesRelStatusBean.getPersonId());
                    } catch (Exception e1) {
                        log.error("updateMesRelStatus is fail... e:{}", e1);
                    }
                }
            }
        } catch (Exception e) {
            log.error("SENDMES is fail... e:{}", e);
            return;
        }
        log.info("sendMes is end.");
    }
}

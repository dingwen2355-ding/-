package com.znv.manage.service.impl.information;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.*;
import com.znv.manage.common.bean.plan.EmergencyConstant;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.information.DisasterManageDao;
import com.znv.manage.dao.information.MesManageDao;
import com.znv.manage.dao.plan.PlanManageDao;
import com.znv.manage.service.information.DisasterManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 灾情信息报送管理
 *
 * @author chd
 * @date 2022/6/7
 */
@Service
@Slf4j
public class DisasterManageServiceImpl implements DisasterManageService {

    @Resource
    PlanManageDao planManageDao;

    @Resource
    MesManageDao mesManageDao;

    @Resource
    DisasterManageDao disasterManageDao;

    protected static final Map<String, String> Disaster_STATUS_MAP = new HashMap();

    static {
        Disaster_STATUS_MAP.put("未报送", "0");
        Disaster_STATUS_MAP.put("已报送", "1");
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result addDisaster(DisasterBean disasterBean) {
        if (disasterBean == null) {
            log.error("disasterBean is null.");
            throw new BusinessException("disasterBean is null.");
        }
        log.info("addDisaster disasterBean:{}", JSONObject.toJSONString(disasterBean));

        // 获取当前用户
        String userId = disasterBean.getCreateId();
        if (StringUtils.isNotBlank(userId)) {
            String userName = planManageDao.getUserName(userId);
            disasterBean.setCreateName(userName);
            // 报送人，新增为空时，取当前用户
            if (StringUtils.isBlank(disasterBean.getRepPersonName())) {
                disasterBean.setRepPersonId(userId);
                disasterBean.setRepPersonName(userName);
            }
            disasterBean.setRepPersonId(userId);
            // 获取当前报送人科室
            disasterBean.setPrecinctId(mesManageDao.getPrecinctId(disasterBean.getRepPersonId()));
        }
        // 报送时间，新增为空时，取当前时间
        if (StringUtils.isEmpty(disasterBean.getRepTime())) {
            disasterBean.setRepTime(DateUtil.getStringDate());
        }
        disasterBean.setStatus("0");
        buildTypeId(disasterBean);

        // 添加灾情信息
        disasterManageDao.addDisaster(disasterBean);
        // 添加突发事件关联
        List<String> eventIds = disasterBean.getEventIds();
        if (!CollectionUtils.isEmpty(eventIds)) {
            disasterManageDao.addRelEvent(disasterBean);
        }
        // 添加预警信息关联
        List<String> warnIds = disasterBean.getWarnIds();
        if (!CollectionUtils.isEmpty(warnIds)) {
            disasterManageDao.addRelWarn(disasterBean);
        }
        // 添加相关资料
        List<DisasterDataBean> disasterDataBeans = disasterBean.getDisasterDataBeans();
        if (!CollectionUtils.isEmpty(disasterDataBeans)) {
            String id = disasterBean.getId();
            for (DisasterDataBean disasterDataBean : disasterDataBeans) {
                disasterDataBean.setDisId(id);
            }
            disasterManageDao.addDisasterData(disasterDataBeans);
        }
        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result editDisaster(DisasterBean disasterBean) {
        if (disasterBean == null) {
            log.error("disasterBean is null.");
            throw new BusinessException("disasterBean is null.");
        }
        log.info("editDisaster disasterBean:{}", JSONObject.toJSONString(disasterBean));
        String id = disasterBean.getId();
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("id is null.");
        }
        String repPersonId = disasterBean.getRepPersonId();
        if (StringUtils.isNotBlank(repPersonId)) {
            disasterBean.setRepPersonName(planManageDao.getUserName(repPersonId));
            disasterBean.setPrecinctId(mesManageDao.getPrecinctId(repPersonId));
        }

        buildTypeId(disasterBean);
        // 修改灾情信息
        disasterManageDao.editDisaster(disasterBean);

        // 先删除原数据再添加突发事件关联
        List<String> eventIds = disasterBean.getEventIds();
        disasterManageDao.delRelEvent(id);
        if (!CollectionUtils.isEmpty(eventIds)) {
            disasterManageDao.addRelEvent(disasterBean);
        }

        // 先删除原数据再添加预警信息关联
        List<String> warnIds = disasterBean.getWarnIds();
        disasterManageDao.delRelWarn(id);
        if (!CollectionUtils.isEmpty(warnIds)) {
            disasterManageDao.addRelWarn(disasterBean);
        }

        // 添加相关资料
        List<DisasterDataBean> disasterDataBeans = disasterBean.getDisasterDataBeans();
        disasterManageDao.delRelData(id);
        if (!CollectionUtils.isEmpty(disasterDataBeans)) {
            for (DisasterDataBean disasterDataBean : disasterDataBeans) {
                disasterDataBean.setDisId(id);
            }
            disasterManageDao.addDisasterData(disasterDataBeans);
        }

        return new Result();
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result delDisaster(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null.");
            throw new BusinessException("id is null.");
        }
        // 删除灾情报送
        disasterManageDao.delDisaster(id);

        // 删除关联突发事件
        disasterManageDao.delRelEvent(id);

        // 删除关联预警信息
        disasterManageDao.delRelWarn(id);

        // 删除关联相关数据
        disasterManageDao.delRelData(id);
        return new Result();
    }

    @Override
    public Result repDisaster(String id) {
        disasterManageDao.repDisaster(id);
        return new Result();
    }

    @Override
    public PageInfo<List<DisasterBean>> getDisaster(GetDisasterBean getDisasterBean) {
        String userId = getDisasterBean.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            getDisasterBean.setPrecinctId(mesManageDao.getPrecinctId(userId));
        } else {
            log.error("userId is null.");
            throw new BusinessException("userId is null.");
        }
        String text = getDisasterBean.getText();
        if (StringUtils.isNotEmpty(text)) {
            getDisasterBean.setStatus(Disaster_STATUS_MAP.get(text));
            getDisasterBean.setTextPrecinctIds(mesManageDao.getPrecinctIdByName(text));
        }
        Integer pageNum = getDisasterBean.getPageNum();
        Integer pageSize = getDisasterBean.getPageSize();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<DisasterBean> disasterBeans = disasterManageDao.getDisaster(getDisasterBean);
        if (userId.equals("57")) {
            for (DisasterBean disasterBean : disasterBeans) {
                String status = disasterBean.getStatus();
                if ("1".equals(status)) {
                    disasterBean.setEdit(1);
                }
            }
        } else {
            String precinctId = getDisasterBean.getPrecinctId();
            if (!StringUtils.isEmpty(precinctId)) {
                for (DisasterBean disasterBean : disasterBeans) {
                    String status = disasterBean.getStatus();
                    if ("1".equals(status)) {
                        disasterBean.setEdit(1);
                        continue;
                    }
                    String s = disasterBean.getPrecinctId();
                    if (StringUtils.isEmpty(s)) {
                        disasterBean.setEdit(1);
                    } else {
                        if (s.indexOf(precinctId) == -1) {
                            disasterBean.setEdit(1);
                        }
                    }
                }
            }
        }
        return new PageInfo(disasterBeans);
    }

    @Override
    public DisasterBean getDisasterData(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null.");
            throw new BusinessException("id is null.");
        }
        DisasterBean disasterBean = disasterManageDao.getDisasterDate(id);
        if (disasterBean == null) {
            return new DisasterBean();
        }
        // 突发事件
        List<EventBean> eventBeans = disasterManageDao.getEventBean(id);
        disasterBean.setEventBeans(eventBeans);
        // 相关资料
        List<DisasterDataBean> disasterDataBeans = disasterManageDao.getDate(id);
        disasterBean.setDisasterDataBeans(disasterDataBeans);

        // 预警信息
        List<WarnBean> warnBeans = disasterManageDao.getWarnBean(id);
        disasterBean.setWarnBeans(warnBeans);
        return disasterBean;
    }

    private void buildTypeId(DisasterBean disasterBean) {
        String typeId = disasterBean.getTypeId();
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
                        disasterBean.setTypeName(sb.toString());
                        break;
                    }
                }
            }
        }
    }
}

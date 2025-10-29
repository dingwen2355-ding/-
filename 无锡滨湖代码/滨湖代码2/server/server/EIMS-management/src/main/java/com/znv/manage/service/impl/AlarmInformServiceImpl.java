package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.AlarmInform;
import com.znv.manage.common.bean.Constant;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.IAlarmInformMapper;
import com.znv.manage.dao.IAlarmInstallMapper;
import com.znv.manage.dao.event.EventManageDao;
import com.znv.manage.service.IAlarmInformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
@Slf4j
@Service
public class AlarmInformServiceImpl implements IAlarmInformService {

    @Autowired
    private IAlarmInformMapper alarmInformMapper;
    @Autowired
    private IAlarmInstallMapper alarmInstallMapper;
    @Autowired
    private EventManageDao eventManageDao;

    @Value("${auth.url}")
    private String authUrl;

    /**
     * 查询所有告警
     *
     * @return
     */
    @Override
    public Result selAlarmInform(String alarmId, String announcementType, String content, String isAccept, String eventId, String startTime, String endTime,
                                 String userId, String departmentId, Integer pageStart, Integer pageLen, String isAll, HttpServletRequest request, String reportSource) {
        Result result = new Result();
        try {
            /* 1. 校验用户信息，主要获取用户的角色、部门等属性 */
            Map<String, String> userInfo = getUserInfo(request);
            String roleId = null;
            if (CollectionUtils.isEmpty(userInfo) && !"1".equals(isAll)) {
                result.setResutCode(ResultCodeEnum.UNAUTHORIZED.getCode(), "获取用户信息失败！");
                return result;
            } else if (!CollectionUtils.isEmpty(userInfo) && !"1".equals(isAll)) {
                // 用户id
                userId = userInfo.get("userId");
                // 角色
                roleId = userInfo.get("roleId");
                // 部门
                departmentId = userInfo.get("deptId");
            }

            /* 2. 查询参数转义 */
            // 公告类型转义
            if (!StringUtils.isEmpty(announcementType)) {
                if (announcementType.equals("超期告警")) {
                    announcementType = "1";
                }
                if (announcementType.equals("催办告警")) {
                    announcementType = "2";
                }
                if (announcementType.equals("即将超期")) {
                    announcementType = "3";
                }
            }

            // 起始行
            Integer start = null;
            if (pageStart != null && pageLen != null) {
                start = pageLen * (pageStart - 1);
            }

            Long count = 0L;
            List<Map<String, Object>> list = Collections.emptyList();
            if (Constant.ROLE_ID.get("超管").equals(roleId)
                    || Constant.ROLE_ID.get("街道坐席人员").equals(roleId)
                    || Constant.ROLE_ID.get("街道管理员").equals(roleId) || "1".equals(isAll)) {
                list = alarmInformMapper.selAlarmInform(alarmId, content, isAccept, eventId, startTime, endTime, null, null, announcementType, start, pageLen, null, reportSource);
                count = alarmInformMapper.selAlarmInformCount(alarmId, content, isAccept, eventId, startTime, endTime, null, null, announcementType, null, null, null, reportSource);
            } else {
                // 街道科室、社区人员、网格员只能看到自己的告警 和 派遣给所属组织（部门）但没有明确指定人的告警
                // （1）查询只能自己看到的告警
                List<Map<String, Object>> tempUserAlarmList = alarmInformMapper.selAlarmInform(alarmId, content, isAccept, eventId, startTime, endTime, userId, null, announcementType, start, pageLen, null, reportSource);
                Long tempUserAlarmCount = alarmInformMapper.selAlarmInformCount(alarmId, content, isAccept, eventId, startTime, endTime, userId, null, announcementType, null, null, null, reportSource);
                // (2) 补齐派遣给当前用户所属组织（部门）但没有指定人的告警
                List<Map<String, Object>> tempDeptNoUserAlarmList = alarmInformMapper.selAlarmInform(alarmId, content, isAccept, eventId, startTime, endTime, null, departmentId, announcementType, start, pageLen, 1, reportSource);
                Long tempDeptNoUserAlarmCount = alarmInformMapper.selAlarmInformCount(alarmId, content, isAccept, eventId, startTime, endTime, null, departmentId, announcementType, start, pageLen, 1, reportSource);

                if (!CollectionUtils.isEmpty(tempUserAlarmList)) {
                    list = tempUserAlarmList;
                }
                if (!CollectionUtils.isEmpty(list)) {
                    list.addAll(tempDeptNoUserAlarmList);
                } else {
                    list = tempDeptNoUserAlarmList;
                }
                count = tempUserAlarmCount + tempDeptNoUserAlarmCount;
            }

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("list", list);
            data.put("count", count);
            result.setData(data);
        } catch (Exception e) {
            log.error("selAlarmInform occur an exception!", e);
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result update(String alarmIds, String isAccept, String remark) {
        Result result = new Result();
        try {
            String[] split = alarmIds.split(",");
            if (StringUtils.isEmpty(isAccept)) {
                isAccept = "已接收";
            }
            if (isAccept.equals("已接收")) {
                isAccept = "2";
            } else {
                isAccept = "1";
            }

            int data = alarmInformMapper.update(split, isAccept, remark);
            result.setData(data);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
            log.error(""+e);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result installAlarmInform(JSONArray jsonArray) {
        Result result = new Result();
        try {
            List<String> eventIdList = new ArrayList<>(jsonArray.size());
            List<AlarmInform> list = new ArrayList<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 事件ID
                String eventId = jsonObject.getString("event_id");
                // 校验是否已产生”催办提醒“告警
                List<Map<String, Object>> mapList = alarmInformMapper.selAlarmInform(null, null, null, eventId, null, null, null, null, "2", null, null, null, null);
                if (CollectionUtils.isEmpty(mapList)) {
                    // 上报来源
                    String reportSource = jsonObject.getString("reportSource") + "到期未闭环处置";
                    // 推送部门编号
                    String dealDeptId = jsonObject.getString("deal_dept_id");
                    // 推送人员编号
                    String dealPersonId = jsonObject.getString("deal_person_id");
                    // 推送人员编号
                    String userId = jsonObject.getString("userId");
                    // 备注
                    String remark = jsonObject.getString("remark");
                    //CB+当前时间
                    String alarmId = "CB" + System.nanoTime();

                    // 封装属性值到告警对象实例
                    AlarmInform alarmInform = new AlarmInform();
                    alarmInform.setAlarmId(alarmId);
                    alarmInform.setEventId(eventId);
                    alarmInform.setAnnouncementTitle(reportSource);
                    alarmInform.setAnnouncementType("2");
                    alarmInform.setAnnouncementContent(remark);
                    alarmInform.setPushDepartmentNumber(dealDeptId);
                    alarmInform.setPushPersonNumber(dealPersonId);
                    alarmInform.setCreator(userId);
                    alarmInform.setIsAccept("1");

                    list.add(alarmInform);
                    eventIdList.add(eventId);
                }
            }
            // 告警数据入库
            if (!CollectionUtils.isEmpty(list)) {
                alarmInstallMapper.insertAlarmInstall(list);
            }
            // 更新事件主表的 is_alarm = 2，表示已经产生催办告警
            if (!CollectionUtils.isEmpty(eventIdList)) {
                eventManageDao.updateEventIsAlarm("2", null, eventIdList);
            }
        } catch (Exception e) {
            log.error("The method [installAlarmInform] occur an exception!", e);
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateAlarmData(String announcementType, String reportSource, String dateTime, String remindTemplate, Map<String, String> precinctMap) {
        log.info("============= Generate Alarm Data [reportSource={}] [START] =============", reportSource);
        try (Cursor<Map<String, Object>> cursorList = alarmInformMapper.selOverTimeAlarmByCursor(announcementType, reportSource, dateTime)) {
            long total = 0L;
            List<AlarmInform> alarmList = new ArrayList<>();
            for (Map<String, Object> eventInfo : cursorList) {
                AlarmInform alarmInform = getAlarmInfo(eventInfo, announcementType, remindTemplate, precinctMap);
                if (ObjectUtils.isEmpty(alarmInform)) {
                    continue;
                }
                if (alarmList.size() < 100) {
                    alarmList.add(alarmInform);
                } else {
                    // 入库
                    saveAlarmData(alarmList, announcementType);
                    total += alarmList.size();
                    alarmList.clear();
                    log.info("Generate Alarm Data [reportSource={}] process: {}", reportSource, total);
                }
            }

            // 最后一批数据入库
            if (!CollectionUtils.isEmpty(alarmList)) {
                saveAlarmData(alarmList, announcementType);
                total += alarmList.size();
                alarmList.clear();
            }
            log.info("Generate Alarm Data [reportSource={}] total: {}", reportSource, total);
        } catch (Exception e) {
            log.error("Generate Alarm Data [reportSource={}] occur an exception!", reportSource, e);
        }
        log.info("============= Generate Alarm Data [reportSource={}] [END] =============", reportSource);
    }

    /**
     * 获取告警数据列表
     *
     * @param eventInfo        事件信息
     * @param announcementType 告警类型(1:超期告警、2:催办告警、3:即将超期)
     * @param remindTemplate   预警提醒模板
     * @param precinctMap      区域Map，key: precinctId, value: precinctName
     * @return
     */
    private AlarmInform getAlarmInfo(Map<String, Object> eventInfo, String announcementType, String remindTemplate, Map<String, String> precinctMap) {
        // 事件ID
        String eventId = String.valueOf(eventInfo.get("event_id"));
        // 校验当前事件是否已经生成对应的 announcementType 类型的告警
        if (alarmInformMapper.selAlarmInformCount(null, null, null, eventId, null, null, null, null, announcementType, null, null, null, null) > 0) {
            log.info("The event(eventId={}) has already produced alarm(announcementType={}) !", eventId, announcementType);
            return null;
        }

        // 上报来源
        String reportSource = String.valueOf(eventInfo.get("reportSource"));

        // 街道
        String gridId = String.valueOf(eventInfo.get("grid_id"));
        String streetId = null;
        if (!StringUtils.isEmpty(gridId) && !"null".equals(gridId)) {
            streetId = (gridId.length() > 9) ? (gridId.substring(0, 9)) : gridId;
            streetId = (streetId + "00000000000000000000").substring(0, 19);
        }
        String streetName = precinctMap.getOrDefault(streetId, "");

        // 部门
        String dealDeptId = String.valueOf(eventInfo.get("deal_dept_id"));
        String dealDeptName = precinctMap.getOrDefault(dealDeptId, "");

        // 事件来源
        String infoSource = String.valueOf(eventInfo.get("infoSource"));

        // 截止时间
        String deadlineTime = String.valueOf(eventInfo.get("deadline_time"));
        if (StringUtils.isEmpty(deadlineTime) || "null".equals(deadlineTime)) {
            deadlineTime = "";
        }

        // 告警id：CQ+当前时间+4位随机数
        String alarmId = "CQ" + System.nanoTime();

        // 提示信息模板
        if (StringUtils.isEmpty(remindTemplate)) {
            List<Map<String, Object>> alarmInstallList = alarmInstallMapper.selAlarmInstall(null, reportSource);
            if (!CollectionUtils.isEmpty(alarmInstallList)
                    && !ObjectUtils.isEmpty(alarmInstallList.get(0))
                    && !StringUtils.isEmpty(alarmInstallList.get(0).get("remindTemplate"))) {
                remindTemplate = String.valueOf(alarmInstallList.get(0).get("remindTemplate"));
            } else {
                remindTemplate = "经数据分析，[街道名称]的[组织名称]的[事件来源]-[事件编号]的事件将于[时间]过期，请负责人员及时处置！";
            }
        }
        remindTemplate = remindTemplate
                .replace("[街道名称]", streetName)
                .replace("[组织名称]", dealDeptName)
                .replace("[事件来源]", infoSource)
                .replace("[事件编号]", eventId)
                .replace("[时间]", deadlineTime);

        // 封装告警对象
        AlarmInform alarmInform = new AlarmInform();
        alarmInform.setAlarmId(alarmId);
        alarmInform.setEventId(eventId);
        alarmInform.setAnnouncementTitle(reportSource + "到期未闭环处置");
        alarmInform.setAnnouncementType(announcementType);
        alarmInform.setAnnouncementContent(remindTemplate);
        alarmInform.setCreator("系统生成");
        alarmInform.setIsAccept("1");
        return alarmInform;
    }

    /**
     * 告警数据写入数据库
     *
     * @param alarmList
     * @param announcementType
     */
    private void saveAlarmData(List<AlarmInform> alarmList, String announcementType) {
        List<String> eventIdList = Collections.emptyList();
        try {
            alarmInstallMapper.insertAlarmInstall(alarmList);
            // 更新事件主表的 is_alarm 字段
            eventIdList = alarmList.stream().map(AlarmInform::getEventId).collect(Collectors.toList());
            // announcementType=1对应事件超期状态为”已经超期“，announcementType=3对应事件超期状态为”即将超期“
            String overdueState = "1".equals(announcementType) ? "30" : "20";
            eventManageDao.updateEventIsAlarm(announcementType, overdueState, eventIdList);
        } catch (Exception e) {
            log.error("Write alarm data into db occur an exception! EventIdList=[{}] .", eventIdList, e);
        }
    }

    private Map<String, String> getUserInfo(HttpServletRequest request) {
        Map<String, String> userInfo = new HashMap<>();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = authUrl + "/verifyAuthority/user/queryUserInfoByToken?token=" + token;
            JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
            if (CollectionUtils.isEmpty(jsonObject) || !jsonObject.containsKey("data")
                    || CollectionUtils.isEmpty(jsonObject.getJSONArray("data"))
                    || !jsonObject.getJSONArray("data").getJSONObject(0).containsKey("userMessage")
                    || CollectionUtils.isEmpty(jsonObject.getJSONArray("data").getJSONObject(0).getJSONObject("userMessage"))) {
                return null;
            }
            JSONObject userMssage = jsonObject.getJSONArray("data").getJSONObject(0).getJSONObject("userMessage");
            userInfo.put("userId", userMssage.getString("userId"));
            userInfo.put("roleId", userMssage.getString("roleId"));
            userInfo.put("deptId", userMssage.getString("departmentId"));
            userInfo.put("precinctId", userMssage.getString("departmentId"));
        }
        return userInfo;
    }

}

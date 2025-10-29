package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.IAlarmInstallMapper;
import com.znv.manage.service.IAlarmInstallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
@Slf4j
@Service
public class AlarmInstallServiceImpl implements IAlarmInstallService {

    @Autowired
    private IAlarmInstallMapper alarmInstallMapper;

    Result result = new Result();

    /**
     * 查询所有告警规则
     *
     * @return
     */
    @Override
    public Result selAlarmInstall() {
        try {
            List<Map<String, Object>> data = alarmInstallMapper.selAlarmInstall(null, null);
            for (Map<String, Object> map : data) {
                String rulesState = String.valueOf(map.get("rulesState"));
                if (rulesState.equals("1")) {
                    map.put("rulesState", true);
                } else {
                    map.put("rulesState", false);
                }
            }
            result.setData(data);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateAlarmInstall(Integer id, String threshold, String rulesState, String user) {
        try {
            if (!StringUtils.isEmpty(rulesState)) {
                if (rulesState.equals("true")) {
                    rulesState = "1";
                }
                if (rulesState.equals("false")) {
                    rulesState = "2";
                }
            }
            int data = alarmInstallMapper.updateAlarmInstall(id, threshold, rulesState, user);
            result.setData(data);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}

package com.znv.manage.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.ThreadLocalUtil;
import com.znv.manage.service.EventSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class EventSyncServiceImpl implements EventSyncService {
    @Value("${event.system.code:system-center-yjglj}")
    String correctSystemCode;

    @Value("${event.token:57b5c571747d4cda9890d4db3892c0e5}")
    String correctToken;

    @Override
    public JSONObject assignEvent(JSONObject json, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject error = new JSONObject();
        result.put("requestId", ThreadLocalUtil.get());
        result.put("data", new JSONObject());
        try {
            auth(request);
            log.info("事件分派传参:{}", json);
            //TODO

            result.put("errors", array);
            result.put("data", new JSONObject());
            result.put("serviceSuccess", true);
            result.put("code", 200);
        } catch (Exception e) {
            log.error(e.toString());
            result.put("code", ResultCodeEnum.SYSTEMERROR.getCode());
            error.put("errorCode", "");
            error.put("parameter", "");
            error.put("msg", e.getMessage());
            array.add(error);
            result.put("errors", array);
            result.put("serviceSuccess", false);
        }
        return result;
    }

    public void auth(HttpServletRequest request) {
        String systemCode = request.getHeader("system-code");
        if (!correctSystemCode.equals(systemCode)) {
            throw new BusinessException("system-code error");
        }
/*        String timestamp = request.getHeader("system-timestamp");
        log.info("事件派送timestamp:{}",timestamp);
        if (StringUtils.isNotBlank(timestamp)) {
            try {
                //验证时间是否大于当前时间五分钟
                Date date = DateUtil.date(Long.parseLong(timestamp));
                long between = DateUtil.between(date, new Date(), DateUnit.MINUTE);
                if (between > 5) {
                    throw new BusinessException("system-timestamp timeout");
                }
            } catch (NumberFormatException e) {
                throw new BusinessException("system-timestamp error");
            }
        } else {
            throw new BusinessException("system-timestamp error");
        }*/
        String signature = request.getHeader("system-signature");
        if (!DigestUtil.sha256Hex(correctToken).equals(signature)) {
            throw new BusinessException("system-signature error");
        }
    }

    public static void main(String[] args) {
        Long s = System.currentTimeMillis();
        System.out.println(s);
        System.out.println(DigestUtil.sha256Hex(s + "57b5c571747d4cda9890d4db3892c0e5" + s));
    }
}

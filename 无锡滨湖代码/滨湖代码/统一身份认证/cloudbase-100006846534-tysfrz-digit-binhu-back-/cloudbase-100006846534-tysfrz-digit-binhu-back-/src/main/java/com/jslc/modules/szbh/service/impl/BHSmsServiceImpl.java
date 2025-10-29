/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.jslc.modules.szbh.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.system.entity.Dept;
import com.jslc.modules.system.mapper.DeptMapper;
import com.jslc.modules.szbh.entity.BhApplicationManagement;
import com.jslc.modules.szbh.entity.BhSmsSend;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.service.IBHSmsService;
import com.jslc.modules.szbh.service.IBhApplicationManagementService;
import com.jslc.modules.szbh.service.IBhSmsSendService;
import com.jslc.modules.szbh.service.IBhUserService;
import com.jslc.modules.szbh.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class BHSmsServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IBHSmsService {

    //用于打印日志
    private static Logger logger = LoggerFactory.getLogger(BHSmsServiceImpl.class);

    @Value(value = "${sms.appId}")
    private String smsAppId;

    @Value(value = "${sms.secret}")
    private String smsSecret;

    @Value(value = "${sms.address}")
    private String smsAddress;

    @Value(value = "${sms.localUrl}")
    private String localUrl;

    @Autowired
    private BhRestServiceImpl bhRestService;
    @Autowired
    private IBhSmsSendService bhSmsSendService;
    @Autowired
    private IBhUserService bhUserService;
    @Autowired
    private IBhApplicationManagementService bhApplicationManagementService;

    @Override
    public ResultVO sendSms(String appId, String token, String mobile, String msg) {
        ResultVO result = new ResultVO();

        result.setCode("500");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            if (StringUtils.isBlank(mobile)) {
                result.setCode("500");
                result.setMessage("请输入手机号码");
                return result;
            }
            if (StringUtils.isBlank(msg)) {
                result.setCode("500");
                result.setMessage("请输入短信内容");
                return result;
            }
            if (Func.isEmpty(appId)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId为空");
                return result;
            }
            String s = bhRestService.checkToken(appId, token);
            if (s.equals(bhRestService.TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(bhRestService.TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(bhRestService.TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                //先生成短信发送记录
                List<Long> ids = addSmsSend(appId, mobile, msg);

                try {
                    String url = localUrl + "?mobile=" + mobile + "&msg=" + msg;
                    String backResult = HttpUtil.get(url);
                    if (Func.isNotEmpty(backResult)) {
                        result = JSONObject.parseObject(backResult, ResultVO.class);
                    }
                    //发送成功回写结果
                    if (result.getCode().equals("200")) {
                        updateSmsSend(ids);
                    }
                } catch (Exception e) {
                    logger.error("短信发送失败：{}，发送内容：{}", result.getMessage(), "appId:" + appId + ",mobile:" + mobile + ",msg:" + msg);
                }
            }
        }

        return result;
    }

    public List<Long> addSmsSend(String appId, String mobile, String msg) {

        List<Long> result = new ArrayList<>();

        List<BhSmsSend> list = new ArrayList<>();
        List<String> mobiles = Func.split(mobile, ',');

        List<BhUser> userList = bhUserService.list(Wrappers.<BhUser>query().lambda().in(BhUser::getMajorMobile, mobiles).eq(BhUser::getIsDeleted, 0));
        Map<String, String> userMap = null;
        if (Func.isNotEmpty(userList)) {
            userMap = userList.stream().collect(Collectors.toMap(BhUser::getMajorMobile, BhUser::getName, (k1, k2) -> k1));
        }

        BhApplicationManagement bhApplicationManagement = null;
        if (Func.isNotEmpty(appId)) {
            bhApplicationManagement = bhApplicationManagementService.getOne(Wrappers.<BhApplicationManagement>query().lambda()
                    .eq(BhApplicationManagement::getAppId, appId).eq(BhApplicationManagement::getIsDeleted, 0));
        }

        for (String mb : mobiles) {
            BhSmsSend bhSmsSend = new BhSmsSend();
            bhSmsSend.setNoticePerson(userMap != null ? userMap.get(mb) : null);
            bhSmsSend.setNoticeTime(LocalDateTime.now());
            bhSmsSend.setMobile(mb);
            bhSmsSend.setEventType(Func.isEmpty(appId) ? "用户初始化发送提示初始密码" : null);
            bhSmsSend.setApplication(bhApplicationManagement != null ? bhApplicationManagement.getName() : null);
            bhSmsSend.setAppId(appId);
            bhSmsSend.setContent(msg);
            bhSmsSend.setResult(0);
            bhSmsSend.setCreateTime(LocalDateTime.now());

            list.add(bhSmsSend);
        }

        boolean saveResult = bhSmsSendService.saveBatch(list);
        if (saveResult) {
            result = list.stream().map(BhSmsSend::getId).collect(Collectors.toList());
        }
        return result;
    }

    public void updateSmsSend(List<Long> ids) {
        List<BhSmsSend> list = bhSmsSendService.listByIds(ids);
        if (Func.isNotEmpty(list)) {
            list.forEach(o -> {
                o.setResult(1);
            });
            bhSmsSendService.saveOrUpdateBatch(list);
        }
    }

    @Override
    public ResultVO initUserSendSms(String mobile, String msg) {
        ResultVO result = new ResultVO();

        result.setCode("500");
        //先生成短信发送记录
        List<Long> ids = addSmsSend(null, mobile, msg);

        try {
            String url = localUrl + "?mobile=" + mobile + "&msg=" + msg;
            String backResult = HttpUtil.get(url);
            if (Func.isNotEmpty(backResult)) {
                result = JSONObject.parseObject(backResult, ResultVO.class);
            }
            //发送成功回写结果
            if (result.getCode().equals("200")) {
                updateSmsSend(ids);
            }
        } catch (Exception e) {
            logger.error("初始用户提示短信发送失败：{}，发送内容：{}", result.getMessage(), "mobile:" + mobile + ",msg:" + msg);
        }

        return result;
    }

    @Override
    public ResultVO sendSmsVerCode(String mobile, String msg) {
        ResultVO result = new ResultVO();

        result.setCode("500");

        if (StringUtils.isBlank(mobile)) {
            result.setCode("500");
            result.setMessage("请输入手机号码");
            return result;
        }
        if (StringUtils.isBlank(msg)) {
            result.setCode("500");
            result.setMessage("请输入短信内容");
            return result;
        }

        //先生成短信发送记录
        List<Long> ids = addSmsSend(null, mobile, msg);

        try {
            String url = localUrl + "?mobile=" + mobile + "&msg=" + msg;
            String backResult = HttpUtil.get(url);
            if (Func.isNotEmpty(backResult)) {
                result = JSONObject.parseObject(backResult, ResultVO.class);
            }
            //发送成功回写结果
            if (result.getCode().equals("200")) {
                updateSmsSend(ids);
            }
        } catch (Exception e) {
            logger.error("短信发送失败：{}，发送内容：{}", result.getMessage(), "mobile:" + mobile + ",msg:" + msg);
        }


        return result;
    }
}

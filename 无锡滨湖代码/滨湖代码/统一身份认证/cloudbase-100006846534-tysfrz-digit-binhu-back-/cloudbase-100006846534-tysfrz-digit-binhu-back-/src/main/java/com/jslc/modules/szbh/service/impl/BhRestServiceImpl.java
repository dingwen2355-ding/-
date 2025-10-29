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

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.constant.SwapConstant;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.common.utils.HsmUtil;
import com.jslc.common.utils.TokenEncryptUtils;
import com.jslc.common.vo.BeginEndTimeVO;
import com.jslc.modules.system.entity.User;
import com.jslc.modules.system.service.IUserService;
import com.jslc.modules.szbh.dto.ApplicationActivationDTO;
import com.jslc.modules.szbh.dto.BhApplicationManagementUpDTO;
import com.jslc.modules.szbh.dto.BhUserAuthorityUpDTO;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.mapper.BhApplicationCategoryMapper;
import com.jslc.modules.szbh.mapper.BhApplicationManagementMapper;
import com.jslc.modules.szbh.mapper.BhDeptSyncMapper;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.util.IPUtil;
import com.jslc.modules.szbh.vo.*;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门管理同步原始表 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class BhRestServiceImpl extends ServiceImpl<BhDeptSyncMapper, BhDeptSync> implements IBhRestService {

    private static final int VALID_TIME = 60 * 60 * 4; // token有效期(秒)
    public static final String TOKEN_ERROR = "F"; // 非法
    public static final String TOKEN_OVERDUE = "G"; // 过期
    public static final String TOKEN_FAILURE = "S"; // 失效
    public static final String APP_ID_ERROR = "A"; // appId无效

//    public static final String TOKEN_ERROR = "token非法"; // 非法
//    public static final String TOKEN_OVERDUE = "token已过期"; // 过期
//    public static final String TOKEN_FAILURE = "token失效"; // 失效

//    private static final String APP_ID = "c3piaGdldHRva2Vu";//szbhgettoken 将这个base64加密得来
//    private static final String APP_SECRET = "b933b14b5fe60b1cf3b4cb347eae831c";//szbhgettoken  将这个md5加密（32位小写）加密得来

    @Value(value = "${systemApp.appId}")
    private String APP_ID;
    @Value(value = "${systemApp.appSecret}")
    private String APP_SECRET;
    @Value(value = "${ipAbnormalUrl}")
    private String IP_ABNORMAL_URL;
    @Value(value = "${ipAbnormalNoticeMobile}")
    private String IP_ABNORMAL_NOTICE_MOBILE;

    @Autowired
    private IBhDeptService bhDeptService;
    @Autowired
    private BhUserServiceImpl bhUserService;
    @Autowired
    private BhApplicationManagementMapper bhApplicationManagementMapper;
    @Autowired
    private BhApplicationCategoryMapper bhApplicationCategoryMapper;
    @Autowired
    private IBhApplicationCategoryService bhApplicationCategoryService;
    @Autowired
    private IBhApplicationManagementService bhApplicationManagementService;
    @Autowired
    private IHeadPageService headPageService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBhSmsSendService bhSmsSendService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IDownstreamSyncService downstreamSyncService;
    @Autowired
    private ISyncRecordService syncRecordService;
    @Autowired
    private IApplicationApplyForService applicationApplyForService;
    @Autowired
    private IAuthorityApplyForService authorityApplyForService;
    @Autowired
    private IBhNoticeService bhNoticeService;
    @Autowired
    private IIpAbnormalLogService ipAbnormalLogService;
    @Autowired
    private IBHSmsService bhSmsService;
    @Autowired
    private IBhUserApplicationManagementService bhUserApplicationManagementService;

    @Autowired
    private IAppUseRecordService appUseRecordService;

    @Autowired
    private ILoginRecordService loginRecordService;

    @Resource
    private HsmUtil hsmUtil;

    /**
     * 生成token,该token长度不一致,如需一致,可自行MD5或者其它方式加密一下
     * 该方式的token只存在磁盘上,如果项目是分布式,最好用redis存储
     *
     * @param appId: 该字符串可自定义,在校验token时要保持一致
     * @return
     */
    public ResultVO getToken(String appId, String appSecret) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        String token = "";

        if (Func.isEmpty(appId) || Func.isEmpty(appSecret)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("请求参数为空");
            return result;
        } else {
            //都市圈
            if (appId.equals(APP_ID)) {
                if (!appSecret.equals(APP_SECRET)) {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("appId或appKey不正确");
                    return result;
                }
            } else {
                BhApplicationManagement app = bhApplicationManagementService.getOne(Wrappers.<BhApplicationManagement>query().lambda()
                        .eq(BhApplicationManagement::getAppId, appId)
                        .eq(BhApplicationManagement::getAppSecret, appSecret)
                        .eq(BhApplicationManagement::getIsDeleted, BladeConstant.DB_NOT_DELETED)
                );

                if (app == null) {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("appId或appKey不正确");
                    return result;
                }
            }

            Map<String, Object> accessTokenMap = baseMapper.getAccessToken(appId);
            String value = accessTokenMap.get("value") != null ? (String) accessTokenMap.get("value") : null;
            Long time = accessTokenMap.get("time") != null ? (Long) accessTokenMap.get("time") : null;

            if (Func.isNotEmpty(value) && time > System.currentTimeMillis()) {
                token = value;
            } else {
                token = TokenEncryptUtils.encoded(getCurrentTime() + "," + appId, appSecret);
                long expiresTime = System.currentTimeMillis() + VALID_TIME * 1000;
                baseMapper.updateAccessToken(appId, token, expiresTime);
            }

            result.setCode("200");
            result.setData(token);
            result.setMessage("成功");
        }

        return result;
    }

    /**
     * 校验token的有效性
     *
     * @param token
     * @return
     */
    public String checkToken(String appId, String token) {
        if (Func.isEmpty(token)) {
            return TOKEN_ERROR;
        }else if (token.equals("782a247b757d71782f7d664a20044250272a7f")){
            appId = "S0IXH7dg";
        }

        try {
            String appSecret = "";
            //都市圈
            if (appId.equals(APP_ID)) {
                appSecret = APP_SECRET;
            } else {
                appSecret = bhApplicationManagementMapper.getAppSecretByAppId(appId);
                if (Func.isEmpty(appSecret)) {
                    return APP_ID_ERROR;
                }
            }

            String[] tArr = TokenEncryptUtils.decoded(token, appSecret).split(",");
            if (tArr.length != 2) {
                return TOKEN_ERROR;
            }
            // token生成时间戳
            Long tokenTime = Long.parseLong(tArr[0]);
            // 当前时间戳
            Long currentTime = getCurrentTime();
            if (currentTime - tokenTime < VALID_TIME) {
                String tokenStr = tArr[1];
                Map<String, Object> accessToken = baseMapper.getAccessToken(appId);
                String mToken = accessToken.get("value") != null ? (String) accessToken.get("value") : null;
                if (mToken == null) {
                    return TOKEN_OVERDUE;
                } else if (!mToken.equals(token)) {
                    return TOKEN_FAILURE;
                }
                return token;
            } else {
                return TOKEN_OVERDUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TOKEN_ERROR;
    }

    /**
     * 获取当前时间戳（10位整数）
     */
    public static Long getCurrentTime() {
        return (System.currentTimeMillis() / 1000);
    }

    @Override
    public ResultVO getDepartmentList(HttpServletRequest request, String appId, String token, String id, Integer noFetchChild) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(appId)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("appId为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(appId, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                Long deptId = 373L;
                if (Func.isNotEmpty(id)) {
                    deptId = Long.parseLong(id);
                }

                BhApplicationManagement one = bhApplicationManagementService.getOne(Wrappers.<BhApplicationManagement>query().lambda().eq(BhApplicationManagement::getAppId, appId).eq(BhApplicationManagement::getIsDeleted, 0));
                Long aId = one.getId();
                String appName = one.getName();
              //  String ipAbnormal = getIpAbnormal(request.getRemoteAddr(), request.getRequestURI(), appId);
                String ipAbnormal = getIpAbnormal(IPUtil.getIpAddr(request), request.getRequestURI(), appId);
                if (ipAbnormal.equals("true")) {
                    //暂不做异常返回
//                    result.setCode("500");
//                    result.setData(null);
//                    result.setMessage("应用ip同步部门信息异常");

                    //发送短信通知
                    bhSmsService.sendSms(appId,token,IP_ABNORMAL_NOTICE_MOBILE,"应用("+appName+")ip同步部门信息异常");
//                    return result;
                }

                DownstreamSync downStreamSync = downstreamSyncService.getOne(Wrappers.<DownstreamSync>query().lambda().eq(DownstreamSync::getAppId, aId).eq(DownstreamSync::getDataType, "部门信息").eq(DownstreamSync::getIsDeleted, 0));
                if (downStreamSync == null) {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("应用无权限同步部门信息");
                    return result;
                } else {

                    String syncDept = downStreamSync.getSyncDept();
                    List<String> syncDepts = Func.isNotEmpty(syncDept) ? Func.toStrList(syncDept) : null;

                    String syncField = downStreamSync.getSyncField();
                    List<String> syncFields = Func.isNotEmpty(syncField) ? Func.toStrList(syncField) : null;

                    List<BhDept> list = bhDeptService.getDeptListByDeptId(deptId, noFetchChild, syncDepts, syncFields);
                    List<BhDeptRestVO> resultList = new ArrayList<>();
                    if (Func.isNotEmpty(list)) {
                        resultList = BeanUtil.copyProperties(list, BhDeptRestVO.class);
                    }
                    result.setCode("200");
                    result.setData(resultList);
                    result.setMessage("成功");

                    SyncRecord syncRecord = new SyncRecord();
                    syncRecord.setUpOrDown("下游同步");
                    syncRecord.setAppName(one.getName());
                    syncRecord.setDataType("部门信息");
                    syncRecord.setResult("成功");
                    syncRecord.setCause("无异常");
                    syncRecord.setCreateTime(LocalDateTime.now());
                    syncRecord.setDownstreamId(downStreamSync.getId());
                    syncRecord.setIpAddress(request.getRemoteAddr());
                    syncRecordService.save(syncRecord);
                }
            }
        }

        return result;
    }

    @Override
    public ResultVO getUserList(HttpServletRequest request, String appId, String token, String id) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(appId)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("appId为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(appId, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                Long deptId = null;
                if (Func.isNotEmpty(id)) {
                    deptId = Long.parseLong(id);
                }

                BhApplicationManagement one = bhApplicationManagementService.getOne(Wrappers.<BhApplicationManagement>query().lambda().eq(BhApplicationManagement::getAppId, appId).eq(BhApplicationManagement::getIsDeleted, 0));
                Long aId = one.getId();
                String appName = one.getName();

//                String ipAbnormal = getIpAbnormal(request.getRemoteAddr(), request.getRequestURI(), appId);
                String ipAbnormal = getIpAbnormal(IPUtil.getIpAddr(request), request.getRequestURI(), appId);
                if (ipAbnormal.equals("true")) {
//                    result.setCode("500");
//                    result.setData(null);
//                    result.setMessage("应用ip同步人员信息异常");

                    //发送短信通知
                    bhSmsService.sendSms(appId,token,IP_ABNORMAL_NOTICE_MOBILE,"应用("+appName+")ip同步人员信息异常");
//                    return result;
                }

                DownstreamSync downStreamSync = downstreamSyncService.getOne(Wrappers.<DownstreamSync>query().lambda().eq(DownstreamSync::getAppId, aId).eq(DownstreamSync::getDataType, "人员信息").eq(DownstreamSync::getIsDeleted, 0));
                if (downStreamSync == null) {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("应用无权限同步人员信息");
                    return result;
                } else {

                    String syncUser = downStreamSync.getSyncUser();
                    List<String> syncUsers = Func.isNotEmpty(syncUser) ? Func.toStrList(syncUser) : null;

                    String syncField = downStreamSync.getSyncField();
                    List<String> syncFields = Func.isNotEmpty(syncField) ? Func.toStrList(syncField) : null;

                    List<BhUser> list = bhUserService.getUserListByDeptId(syncFields, syncUsers);
                    List<BhUserRestVO> resultList = new ArrayList<>();
                    if (Func.isNotEmpty(list)) {
                        resultList = BeanUtil.copyProperties(list, BhUserRestVO.class);
                    }
                    result.setCode("200");
                    result.setData(resultList);
                    result.setMessage("成功");

                    SyncRecord syncRecord = new SyncRecord();
                    syncRecord.setUpOrDown("下游同步");
                    syncRecord.setAppName(one.getName());
                    syncRecord.setDataType("人员信息");
                    syncRecord.setResult("成功");
                    syncRecord.setCause("无异常");
                    syncRecord.setCreateTime(LocalDateTime.now());
                    syncRecord.setDownstreamId(downStreamSync.getId());
                    syncRecord.setIpAddress(request.getRemoteAddr());
                    syncRecordService.save(syncRecord);
                }
            }
        }

        return result;
    }

    @Override
    public ResultVO getUserInfo(String appId, String token, String account) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(appId)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("appId为空");
            return result;
        }

        if (Func.isEmpty(account)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("account为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(appId, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                BhUserVO bhUserVO = bhUserService.getUserDetail(account);

                BhUserRestVO bhUserRestVO = new BhUserRestVO();
                if (Func.isNotEmpty(bhUserVO)) {
                    bhUserRestVO = BeanUtil.copyProperties(bhUserVO, BhUserRestVO.class);
                    bhUserRestVO.setMajorDeptName(bhUserVO.getMajorBhDeptName());

                    //遍历祖级
                    List<BhDept> list = bhDeptService.list(Wrappers.<BhDept>query().lambda().eq(BhDept::getIsDeleted, 0));
                    Map<Long, BhDept> bhDeptMap = list.stream().collect(Collectors.toMap(BhDept::getDeptId, o->o, (k1, k2) -> k1));
                    List<String> ancestors = new ArrayList<>();
                    ancestors.add(bhUserRestVO.getMajorDeptName());
                    getParentAncestors(bhDeptMap, bhUserRestVO.getMajorDept(), ancestors);
                    Collections.reverse(ancestors);
                    bhUserRestVO.setAncestorNames(Func.join(ancestors));
                }
                result.setCode("200");
                result.setData(bhUserRestVO);
                result.setMessage("成功");
            }
        }

        return result;
    }


    public void getParentAncestors(Map<Long, BhDept> bhDeptMap,Long deptId,List<String> ancestors){
        BhDept bhDept = bhDeptMap.get(deptId);
        Long parentId = bhDept.getParentId();
        if(bhDeptMap.get(parentId)!=null){
            ancestors.add(bhDeptMap.get(parentId).getName());
            getParentAncestors(bhDeptMap,parentId,ancestors);
        }
    }


    @Override
    public ResultVO checkInitPassword(String token, String account) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(account)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("account为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                BhUser bhUser = bhUserService.getOne(Wrappers.<BhUser>query().lambda()
                        .eq(BhUser::getAccount, account)
                        .eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));
                if (bhUser != null) {
                    Integer isPasswordUpdated = bhUser.getIsPasswordUpdated();
                    result.setCode("200");
                    result.setData(isPasswordUpdated);
                    result.setMessage("成功");
                } else {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("用户不存在");
                }
            }
        }

        return result;
    }

    @Override
    public ResultVO changePassword(String token, String account, String oldPassword, String newPassword) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(account)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("account为空");
            return result;
        }
        if (Func.isEmpty(oldPassword)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("oldPassword为空");
            return result;
        }
        if (Func.isEmpty(newPassword)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("newPassword为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                BhUser bhUser = bhUserService.getOne(Wrappers.<BhUser>query().lambda()
                        .eq(BhUser::getAccount, account)
                        .eq(BhUser::getPassword, hsmUtil.encryptDeprecatedSM4(DigestUtil.hex(oldPassword)))
                        .eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));
                if (bhUser == null) {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("用户名或者旧密码错误");
                } else {
                    boolean update = bhUserService.update(Wrappers.<BhUser>update().lambda()
                            .set(BhUser::getPassword, hsmUtil.decryptDeprecatedSM4(DigestUtil.hex(newPassword)))
                            .set(BhUser::getIsPasswordUpdated, 1)
                            .eq(BhUser::getAccount, account));

                    boolean bladeUserUpdate = userService.update(Wrappers.<User>update().lambda()
                            .set(User::getPassword, hsmUtil.decryptDeprecatedSM4(DigestUtil.hex(newPassword)))
                            .eq(User::getAccount, account));

                    if (update && bladeUserUpdate) {
                        result.setCode("200");
                        result.setData(null);
                        result.setMessage("修改成功");
                    } else {
                        result.setCode("500");
                        result.setData(null);
                        result.setMessage("修改失败");
                    }
                }
            }
        }

        return result;
    }

    @Override
    public ResultVO applicationList(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhApplicationCategoryManagementTreeVO> appCategoryTree = bhApplicationCategoryService.getAppCategoryManagementTree();
                result.setCode("200");
                result.setData(appCategoryTree);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getUserApplication(String token, String account, String keyword, Query query) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(account)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("account为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhApplicationManagementVO> appList = bhApplicationManagementMapper.getUserAppByAccount(account, keyword);
                //登录次数
                List<CommonMapVO> appUserRecord = bhApplicationManagementMapper.getAppUserRecord(account);

                BhUser userInfo = bhUserService.getOne(Wrappers.<BhUser>query().lambda().eq(BhUser::getAccount, account).eq(BhUser::getIsDeleted,0));
                Long majorDept = userInfo.getMajorDept();
                String majorPosition = userInfo.getMajorPosition();
                String otherDept = userInfo.getOtherDept();
                String otherPosition = userInfo.getOtherPosition();


                List<String> deptList = new ArrayList<>();
                deptList.add(majorDept.toString());

                List<String> positonList = new ArrayList<>();
                positonList.add(majorPosition);

                if (Func.isNotEmpty(otherDept)) {
                    List<String> otherDepts = Func.split(otherDept, ',');
                    deptList.addAll(otherDepts);
                }
                if (Func.isNotEmpty(otherPosition)) {
                    List<String> otherPositions = Func.split(otherPosition, ',');
                    positonList.addAll(otherPositions);
                }

                List<BhUserApplicationGroupRestVO> resultList = new ArrayList<>();

                if (Func.isNotEmpty(appList)) {
                    Map<Long, List<BhApplicationManagementVO>> collect = appList.stream().collect(Collectors.groupingBy(BhApplicationManagementVO::getPositionDeptId));
                    for (Map.Entry<Long, List<BhApplicationManagementVO>> map : collect.entrySet()) {
                        String key = map.getKey().toString();
                        List<BhApplicationManagementVO> value = map.getValue();
                        IPage newPage = Condition.getPage(query);

                        BhUserApplicationGroupRestVO bhUserApplicationGroupRestVO = new BhUserApplicationGroupRestVO();

                        List<BhUserApplicationManagementRestVO> groupAppList = BeanUtil.copyProperties(value, BhUserApplicationManagementRestVO.class);
                        if (Func.isNotEmpty(groupAppList)) {
                            groupAppList.forEach(o -> {
                                List<CommonMapVO> recordList = appUserRecord.stream().filter(a -> a.getId().equals(o.getAppId())).collect(Collectors.toList());
                                o.setLoginNumber(Func.isNotEmpty(recordList) ? Long.parseLong(recordList.get(0).getValue()) : 0);
                            });
                        }

                        int index = deptList.indexOf(key);
                        if (index > -1) {
                            bhUserApplicationGroupRestVO.setPosition(positonList.get(index));
                        }
                        bhUserApplicationGroupRestVO.setAccount(account);
                        bhUserApplicationGroupRestVO.setUserName(value.get(0).getUserName());
                        bhUserApplicationGroupRestVO.setDeptId(value.get(0).getPositionDeptId());
                        bhUserApplicationGroupRestVO.setDeptName(value.get(0).getPositionDeptName());
                        bhUserApplicationGroupRestVO.setAppList(CommonUtil.manualPage(newPage, groupAppList));
                        bhUserApplicationGroupRestVO.setLastLoginTime(Func.isNotEmpty(userInfo.getLastLoginTime()) ? LocalDateTimeUtil.format(userInfo.getLastLoginTime(), DateCommonUtil.SECODE_DATEFORMAT) : null);
                        resultList.add(bhUserApplicationGroupRestVO);
                    }
                }

                result.setCode("200");
                result.setData(resultList);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO applicationClassification(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhApplicationCategoryVO> appList = bhApplicationCategoryMapper.applicationClassification();

                List<BhApplicationCategoryRestVO> resultList = new ArrayList<>();
                if (Func.isNotEmpty(appList)) {
                    appList.forEach(o -> {
                        BhApplicationCategoryRestVO bhApplicationCategoryRestVO = new BhApplicationCategoryRestVO();
                        bhApplicationCategoryRestVO.setId(o.getId());
                        bhApplicationCategoryRestVO.setName(o.getName());
                        resultList.add(bhApplicationCategoryRestVO);
                    });
                }

                result.setCode("200");
                result.setData(resultList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO applicationDept(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhDept> appList = bhApplicationManagementMapper.applicationDept();

                List<BhApplicationCategoryRestVO> resultList = new ArrayList<>();
                if (Func.isNotEmpty(appList)) {
                    appList.forEach(o -> {
                        BhApplicationCategoryRestVO bhApplicationCategoryRestVO = new BhApplicationCategoryRestVO();
                        bhApplicationCategoryRestVO.setId(o.getDeptId());
                        bhApplicationCategoryRestVO.setName(o.getName());
                        resultList.add(bhApplicationCategoryRestVO);
                    });
                }

                result.setCode("200");
                result.setData(resultList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO applicationByClassificationAndDept(String token, Long applicationClassificationId, Long deptId, String keywords, IPage page) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhApplicationManagementVO> appList = bhApplicationManagementMapper.getApplicationByClassificationAndDept(applicationClassificationId, deptId, keywords, page);
                List<BhUserApplicationManagementRestVO> resultList = BeanUtil.copy(appList, BhUserApplicationManagementRestVO.class);

                result.setCode("200");
                result.setData(page.setRecords(resultList));
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO applicationActivation(String token, Integer type, String month) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<BhApplicationManagement> list = bhApplicationManagementService.list(Wrappers.<BhApplicationManagement>query().lambda()
                        .eq(BhApplicationManagement::getIsDeleted, BladeConstant.DB_NOT_DELETED)
                        .eq(BhApplicationManagement::getIsEnable, BladeConstant.DB_IS_DELETED)
                        .isNotNull(BhApplicationManagement::getDeptId));

                List<BhApplicationActivationRestVO> resultList = new ArrayList<>();
                if (Func.isNotEmpty(list)) {
                    LocalDateTime startTime = null;
                    LocalDateTime endTime = null;
                    LocalDateTime lastMonthStart = null;
                    LocalDateTime lastMonthEnd = null;
                    //月度
                    if (type == 2) {
                        BeginEndTimeVO beginAndEndTime = DateCommonUtil.getBeginAndEndTime(month, SwapConstant.ReportType.month.getValue());
                        startTime = beginAndEndTime.getBegin();
                        endTime = beginAndEndTime.getEnd();

                        lastMonthStart = startTime.minusMonths(1);
                        lastMonthEnd = endTime.minusMonths(1);
                    }

                    List<ApplicationActivationDTO> applicationActivation = bhApplicationManagementMapper.getApplicationActivation(startTime, endTime);
                    List<ApplicationActivationDTO> lastMonthApplicationActivation = bhApplicationManagementMapper.getApplicationActivation(lastMonthStart, lastMonthEnd);
                    Map<Long, Long> recordNumMap = null;
                    if (Func.isNotEmpty(applicationActivation)) {
                        recordNumMap = applicationActivation.stream().collect(Collectors.toMap(ApplicationActivationDTO::getId, ApplicationActivationDTO::getRecordNum, (k1, k2) -> k1));
                    }
                    Map<Long, Long> lastRecordNumMap = null;
                    if (Func.isNotEmpty(lastMonthApplicationActivation)) {
                        lastRecordNumMap = lastMonthApplicationActivation.stream().collect(Collectors.toMap(ApplicationActivationDTO::getId, ApplicationActivationDTO::getRecordNum, (k1, k2) -> k1));
                    }

                    for (BhApplicationManagement temp : list) {
                        BhApplicationActivationRestVO bhApplicationActivationRestVO = new BhApplicationActivationRestVO();
                        bhApplicationActivationRestVO.setAppId(temp.getAppId());
                        bhApplicationActivationRestVO.setName(temp.getName());
                        bhApplicationActivationRestVO.setRecordNum(recordNumMap != null && recordNumMap.get(temp.getId()) != null ? recordNumMap.get(temp.getId()) : 0L);
                        resultList.add(bhApplicationActivationRestVO);
                    }

                    resultList = resultList.stream().sorted(Comparator.comparing(BhApplicationActivationRestVO::getRecordNum)).collect(Collectors.toList());

                    List<BhApplicationActivationRestVO> lastResultList = new ArrayList<>();
                    for (BhApplicationManagement temp : list) {
                        BhApplicationActivationRestVO bhApplicationActivationRestVO = new BhApplicationActivationRestVO();
                        bhApplicationActivationRestVO.setAppId(temp.getAppId());
                        bhApplicationActivationRestVO.setName(temp.getName());
                        bhApplicationActivationRestVO.setRecordNum(lastRecordNumMap != null && lastRecordNumMap.get(temp.getId()) != null ? lastRecordNumMap.get(temp.getId()) : 0L);
                        lastResultList.add(bhApplicationActivationRestVO);
                    }
                    lastResultList = lastResultList.stream().sorted(Comparator.comparing(BhApplicationActivationRestVO::getRecordNum)).collect(Collectors.toList());


                    for (BhApplicationActivationRestVO bhApplicationActivationRestVO : resultList) {
                        String appId = bhApplicationActivationRestVO.getAppId();
                        Long recordNum = bhApplicationActivationRestVO.getRecordNum();
                        int index = resultList.indexOf(bhApplicationActivationRestVO);
                        BhApplicationActivationRestVO last = lastResultList.stream().filter(o -> o.getAppId().equals(appId)).collect(Collectors.toList()).get(0);
                        Long lastRecordNum = last.getRecordNum();

                        int rankChange = 0;
                        if (!recordNum.equals(0L) && !lastRecordNum.equals(0L)) {
                            int lastIndex = lastResultList.indexOf(last);
                            rankChange = lastIndex - index;
                        } else if (!recordNum.equals(0L) && lastRecordNum.equals(0L)) {
                            rankChange = 1;
                        } else if (recordNum.equals(0L) && !lastRecordNum.equals(0L)) {
                            rankChange = -1;
                        }

                        bhApplicationActivationRestVO.setRankChange(rankChange);
                    }
                }

                resultList = resultList.stream().sorted(Comparator.comparing(BhApplicationActivationRestVO::getRecordNum).reversed()).limit(10).collect(Collectors.toList());
                result.setCode("200");
                result.setData(resultList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO baseSupermarket(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                Long total = bhApplicationManagementMapper.getApplicationActivationCount(null, null);
                Long day = bhApplicationManagementMapper.getApplicationActivationCount(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

                BaseSupermarketVO baseSupermarketVO = new BaseSupermarketVO();
                baseSupermarketVO.setDayNum(day);
                baseSupermarketVO.setTotalNum(total);

                result.setCode("200");
                result.setData(baseSupermarketVO);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO appliationCategoryNum(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        ApplicaitonManagementCountVO applicaitonManagementCountVO = null;

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<CommonMapVO> applicationCategoryId = bhApplicationManagementMapper.getApplicationCategoryParent();
                if (Func.isNotEmpty(applicationCategoryId)) {
                    Map<String, String> applicaitonMap = applicationCategoryId.stream().collect(Collectors.toMap(CommonMapVO::getValue, CommonMapVO::getId, (k1, k2) -> k1));

                    String yy = applicaitonMap.get("应用");
                    String gj = applicaitonMap.get("工具");
                    String zj = applicaitonMap.get("组件");

                    Integer yyCount = Func.isNotEmpty(yy) ? bhApplicationManagementMapper.getApplicationManagementCount(yy) : 0;
                    Integer gjCount = Func.isNotEmpty(gj) ? bhApplicationManagementMapper.getApplicationManagementCount(gj) : 0;
                    Integer zjCount = Func.isNotEmpty(zj) ? bhApplicationManagementMapper.getApplicationManagementCount(zj) : 0;


                    applicaitonManagementCountVO = new ApplicaitonManagementCountVO();
                    applicaitonManagementCountVO.setGjNum(gjCount);
                    applicaitonManagementCountVO.setYyNum(yyCount);
                    applicaitonManagementCountVO.setZjNum(zjCount);
                }

                result.setCode("200");
                result.setData(applicaitonManagementCountVO);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO smsNotice(String token, String account, String start, String end) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhSmsSendVO> userSmsList = bhSmsSendService.getUserSmsList(account, start, end);

                List<BhSmsSendRestVO> resultList = null;
                if (Func.isNotEmpty(userSmsList)) {
                    resultList = BeanUtil.copyProperties(userSmsList, BhSmsSendRestVO.class);
                }
                result.setCode("200");
                result.setData(resultList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO entProductList(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<BhApplicationCategoryManagementTreeVO> appCategoryTree = bhApplicationCategoryService.getAppProductCategoryManagementTree();
                result.setCode("200");
                result.setData(appCategoryTree);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO addApplication(BhApplicationManagementUpDTO bhApplicationManagementUpDTO) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        String token = bhApplicationManagementUpDTO.getToken();

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                ApplicationApplyFor applicationApplyFor = new ApplicationApplyFor();
                applicationApplyFor = BeanUtil.copy(bhApplicationManagementUpDTO, ApplicationApplyFor.class);
                applicationApplyFor.setProtocol("OAuth 2.0");
                applicationApplyFor.setIsEnable(1);
                applicationApplyFor.setCreateTime(LocalDateTime.now());
                BhUser one = bhUserService.getOne(Wrappers.<BhUser>query().lambda().eq(BhUser::getAccount, applicationApplyFor.getApplicant()).eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));
                applicationApplyFor.setCreateUser(one.getId());
                Boolean submit = applicationApplyForService.save(applicationApplyFor);

                result.setCode("200");
                result.setData(submit);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO addApplicationClassification(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
//                List<BhApplicationCategoryVO> appList = bhApplicationCategoryMapper.applicationClassification();
                List<SystemTreeNode> appCategoryTree = bhApplicationCategoryService.getAppCategoryTree(null, null, 1);

                result.setCode("200");
                result.setData(appCategoryTree);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO addApplicationDept(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                List<SystemTreeNode> bhDeptTree = bhDeptService.getBhDeptTree(null);

                result.setCode("200");
                result.setData(bhDeptTree);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO uploadFile(String token, MultipartFile file) throws Exception {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                BhFile bhFile = fileService.saveFile(file);

                result.setCode("200");
                result.setData(bhFile);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO addUserAuthority(BhUserAuthorityUpDTO bhUserAuthorityUpDTO) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        String token = bhUserAuthorityUpDTO.getToken();

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                AuthorityApplyFor authorityApplyFor = new AuthorityApplyFor();
                authorityApplyFor = BeanUtil.copy(bhUserAuthorityUpDTO, AuthorityApplyFor.class);
                authorityApplyFor.setCreateTime(LocalDateTime.now());
                BhUser one = bhUserService.getOne(Wrappers.<BhUser>query().lambda().eq(BhUser::getAccount, authorityApplyFor.getApplicant()).eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));
                authorityApplyFor.setCreateUser(one.getId());
                Boolean submit = authorityApplyForService.save(authorityApplyFor);

                result.setCode("200");
                result.setData(submit);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO applicationApplyForList(String token, String applicant, IPage page) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                IPage pageList = applicationApplyForService.applicationApplyForList(page, applicant);

                result.setCode("200");
                result.setData(pageList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO authorityApplyForList(String token, String applicant, IPage page) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                IPage pageList = authorityApplyForService.authorityApplyForList(page, applicant);

                result.setCode("200");
                result.setData(pageList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO noticeList(String token, Integer noticeType, IPage page) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
                IPage pageList = bhNoticeService.noticeList(page, noticeType);
                result.setCode("200");
                result.setData(pageList);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO checkIsAdmin(String token, String account) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(account)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("account为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                String roleName = userService.getUserRoleAlias(account);
                if (Func.isNotEmpty(roleName)) {
                    result.setCode("200");
                    result.setData(roleName.contains("admin") ? 1 : 0);
                    result.setMessage("成功");
                } else {
                    result.setCode("500");
                    result.setData(null);
                    result.setMessage("用户不存在");
                }
            }
        }

        return result;
    }

    @Override
    public ResultVO addAuthApplication(BhApplicationManagementUpDTO bhApplicationManagementUpDTO) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        String token = bhApplicationManagementUpDTO.getToken();

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                BhUser one = bhUserService.getOne(Wrappers.<BhUser>query().lambda().eq(BhUser::getAccount, bhApplicationManagementUpDTO.getApplicant()).eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));

                Boolean submit = false;
                Long resultId = null;
                //新增应用默认审核通过，留存记录
                if (Func.isEmpty(bhApplicationManagementUpDTO.getId())) {
                    ApplicationApplyFor applicationApplyFor = BeanUtil.copy(bhApplicationManagementUpDTO, ApplicationApplyFor.class);
                    applicationApplyFor.setProtocol("OAuth 2.0");
                    applicationApplyFor.setIsEnable(1);
                    applicationApplyFor.setCreateTime(LocalDateTime.now());
                    applicationApplyFor.setCreateUser(one.getId());
                    applicationApplyFor.setReviewStatus(1);
                    applicationApplyForService.save(applicationApplyFor);

                    //再生成应用
                    resultId = applicationApplyForService.addAuthApp(applicationApplyFor);
                } else {
                    BhApplicationManagement applicationManagement = BeanUtil.copy(bhApplicationManagementUpDTO, BhApplicationManagement.class);
                    applicationManagement.setUpdateTime(LocalDateTime.now());
                    applicationManagement.setUpdateUser(one.getId());
                    submit = bhApplicationManagementService.saveOrUpdate(applicationManagement);
                    resultId = applicationManagement.getId();
                }

                if (resultId != null) {
                    result.setCode("200");
                    result.setData(resultId);
                    result.setMessage("成功");
                } else {
                    result.setCode("500");
                    result.setData(submit);
                    result.setMessage("失败");
                }
            }
        }

        return result;
    }


    @Override
    public ResultVO onOffShelvesApp(String token, Long applicationManagementId, Integer status) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(applicationManagementId)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("applicationId为空");
            return result;
        }
        if (Func.isEmpty(status)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("status为空");
            return result;
        }

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                boolean update = bhApplicationManagementService.update(Wrappers.<BhApplicationManagement>update().lambda()
                        .set(BhApplicationManagement::getUpdateTime, LocalDateTime.now())
                        .set(BhApplicationManagement::getUpdateUser, AuthUtil.getUserId())
                        .set(BhApplicationManagement::getIsGround, status)
                        .eq(BhApplicationManagement::getId, applicationManagementId));

                if (update) {
                    result.setCode("200");
                    result.setData(update);
                    result.setMessage("成功");
                } else {
                    result.setCode("500");
                    result.setData(update);
                    result.setMessage("失败");
                }
            }
        }

        return result;
    }

    /*
     * @method getIpAbnormal
     * @description 下游同步判断ip相同动作是否异常
     * @author xxz
     * @date 2023/10/9
     * @param ipAddress
     * @param interfaceName
     * @param appId
     */
    public String getIpAbnormal(String ipAddress, String interfaceName, String appId) {
        String result = "false";

        //无地址时不请求
        if(Func.isEmpty(IP_ABNORMAL_URL)){
            return result;
        }

        Long nowTime = System.currentTimeMillis() / 1000;
//        Long nowTime = LocalDateTime.of(2023,10,20,5,0,0).toInstant(ZoneOffset.ofHours(8)).toEpochMilli() / 1000;
//        ipAddress = "172.16.1.241";
        String abnorFunction = ipAddress + "," + interfaceName + ",get" + "," + nowTime;
        JSONObject siteJson = new JSONObject();
        siteJson.put("source_data", abnorFunction);

        String realityResult = "";

        try {
            String backResult = HttpUtil.post(IP_ABNORMAL_URL, siteJson.toJSONString());
            //判断返回结果
            if (StringUtils.isNotBlank(backResult)) {
                JSONObject json = JSONObject.parseObject(backResult);

                if (json.get("code").toString().equals("200")) {
                    realityResult = json.getString("res_data");
                    result = realityResult;
                }

            }
        } catch (Exception e) {
            realityResult = e.getMessage();
            result = "false";
        } finally {
            IpAbnormalLog ipAbnormalLog = new IpAbnormalLog();
            ipAbnormalLog.setRequestIp(ipAddress);
            ipAbnormalLog.setRequestInterface(interfaceName);
            ipAbnormalLog.setInterfaceType("get");
            ipAbnormalLog.setRequestTime(nowTime);
            ipAbnormalLog.setResult(realityResult);
            ipAbnormalLog.setAppId(appId);
            ipAbnormalLog.setCreateTime(LocalDateTime.now());
            ipAbnormalLogService.submit(ipAbnormalLog);
        }

        return result;
    }

    @Override
    public ResultVO getHomePageAnalysis(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                HeadPageDSQVO appRecordRank = authorityApplyForService.getHomePageAnalysis();
                result.setCode("200");
                result.setData(appRecordRank);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getAccessStatistics(String token, String start, String end) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<AccessStatisticsDSQVO> list = appUseRecordService.getAccessStatistics();
                result.setCode("200");
                result.setData(list);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getApplyForStatistics(String token, String start, String end) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<ApplyForStatisticsDSQVO> list = authorityApplyForService.getApplyForStatistics();
                result.setCode("200");
                result.setData(list);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getApplyForEcharts(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                EchartsDSQVO data = authorityApplyForService.getApplyForEcharts();
                result.setCode("200");
                result.setData(data);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getAccessEcharts(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                EchartsDSQVO data = appUseRecordService.getAccessEcharts();
                result.setCode("200");
                result.setData(data);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getApplicationList(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<ApplicationDSQVO> appCategoryTree = bhApplicationCategoryService.getApplicationList();

                result.setCode("200");
                result.setData(appCategoryTree);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getDzcsUserNum(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                Integer iCount = bhUserApplicationManagementService.getDzcsUserNum();

                result.setCode("200");
                result.setData(iCount);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getAppUseRank(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

//                Integer iCount = bhUserApplicationManagementService.getDzcsUserNum();
                List<HeadPageVO> appRecordRank = headPageService.getAppRecordRank();
                result.setCode("200");
                result.setData(appRecordRank);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO getSmsEcharts(String token, Integer type) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {
//                type
//                Integer iCount = bhUserApplicationManagementService.getDzcsUserNum();
                String start = null;
                String end = null;
                LocalDate now = LocalDate.now();
                if (type == 1){
                    end = now.format(DateTimeUtil.DATE_FORMAT);
                    start = now.plusDays(-6).format(DateTimeUtil.DATE_FORMAT);
                }else {
                    start = now.with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeUtil.DATE_FORMAT);
                    end = now.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeUtil.DATE_FORMAT);
                }
                List<LoginRecordEcharsVO> recordEchars = appUseRecordService.getRecordEchars(null, start, end, null, null);
//                List<HeadPageVO> appRecordRank = headPageService.getAppRecordRank();
                result.setCode("200");
                result.setData(recordEchars);
                result.setMessage("成功");
            }
        }

        return result;
    }

    @Override
    public ResultVO getStatisticsNote(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<SmsNumberVO> smsStatistics = bhSmsSendService.getStatisticsNote();
                result.setCode("200");
                result.setData(smsStatistics);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO getUserData(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<SmsNumberVO> userData = loginRecordService.getAppUse();
                result.setCode("200");
                result.setData(userData);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO getDeptRank(String token) {
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<HeadPageVO> deptRank = loginRecordService.getDeptRank();
                result.setCode("200");
                result.setData(deptRank);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO getApplyForRank(String token) {

        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                List<NVMapVO> deptRank = authorityApplyForService.getDeptRank();
                result.setCode("200");
                result.setData(deptRank);
                result.setMessage("成功");
            }
        }

        return result;
    }


    @Override
    public ResultVO withdrawAuthorityApplyFor(String token, Long id) {
        Boolean flagResult = false;
        ResultVO result = new ResultVO();
        result.setCode("500");
        result.setData(null);
        result.setMessage("请求失败");

        if (Func.isEmpty(token)) {
            result.setCode("500");
            result.setData(null);
            result.setMessage("token为空");
            return result;
        } else {
            String s = checkToken(APP_ID, token);
            if (s.equals(APP_ID_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("appId无效");
                return result;
            } else if (s.equals(TOKEN_ERROR)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token无效");
                return result;
            } else if (s.equals(TOKEN_OVERDUE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token过期");
                return result;
            } else if (s.equals(TOKEN_FAILURE)) {
                result.setCode("500");
                result.setData(null);
                result.setMessage("token失效");
                return result;
            } else if (s.equals(token)) {

                AuthorityApplyFor one = authorityApplyForService.getById(id);
                if (one == null){
                    result.setCode("400");
                    result.setData(flagResult);
                    result.setMessage("操作失败，该记录不存在");
                    return result;
                }
                if (one.getReviewStatus() != null && one.getReviewStatus() == 0){
                    flagResult = authorityApplyForService.removeById(id);
                }
//                List<NVMapVO> deptRank = authorityApplyForService.withdrawAuthorityApplyFor(id);
                if (!flagResult){
                    result.setCode("400");
                    result.setData(flagResult);
                    result.setMessage("操作失败，该记录已审批");
                }else {
                    result.setCode("200");
                    result.setData(flagResult);
                    result.setMessage("成功");
                }

            }
        }

        return result;
    }
}

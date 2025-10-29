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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.constant.SwapConstant;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.HsmUtil;
import com.jslc.modules.system.entity.User;
import com.jslc.modules.system.service.IUserService;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.mapper.AuthorityApplyForMapper;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限申请表 服务实现类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Service
public class AuthorityApplyForServiceImpl extends ServiceImpl<AuthorityApplyForMapper, AuthorityApplyFor> implements IAuthorityApplyForService {

    //用于打印日志
    private static Logger logger = LoggerFactory.getLogger(AuthorityApplyForServiceImpl.class);

    @Autowired
    private IBhApplicationManagementService bhApplicationManagementService;
    @Autowired
    private IApplicationAuthorityService applicationAuthorityService;
    @Autowired
    private IBhUserService bhUserService;
    @Autowired
    private ISmsSendSettingService smsSendSettingService;

    @Lazy
    @Autowired
    private IUserService userService;

    @Resource
    private HsmUtil hsmUtil;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${DSQ.authorityUrl:''}")
    private String dsqUrl;
    @Value("${DSQ.getToken:''}")
    private String dsqTokenUrl;
    @Value("${DSQ.dsqUserName:''}")
    private String dsqUserName;
    @Value("${DSQ.dsqPassword:''}")
    private String dsqPassword;



    @Override
    public AuthorityApplyForVO getDetail(Long id) {
        AuthorityApplyForVO authorityApplyForVO = baseMapper.getDetail(id);
        authorityApplyForVO.setReviewStatusName(SwapConstant.ReviewStatus.getDescByValue(authorityApplyForVO.getReviewStatus()));
        return authorityApplyForVO;
    }

    @Override
    public IPage<AuthorityApplyForVO> selectAuthorityApplyForPage(IPage<AuthorityApplyForVO> page) {
        //获取当前登录人的关联应用
        User user = userService.getById(AuthUtil.getUserId());
        List<Long> managementIds = null;
        if (Func.isNotEmpty(user) && Func.isNotEmpty(user.getManagementId()) && user.getManagementId().length() >0){
            managementIds = Func.toLongList(user.getManagementId());

        }
        List<AuthorityApplyForVO> list = baseMapper.selectAuthorityApplyForPage(page,managementIds);
        if (CollectionUtils.isNotEmpty(list)) {
            for (AuthorityApplyForVO authorityApplyForVO : list) {
                authorityApplyForVO.setReviewStatusName(SwapConstant.ReviewStatus.getDescByValue(authorityApplyForVO.getReviewStatus()));
            }
        }
        return page.setRecords(list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submit(AuthorityApplyFor authorityApplyFor) {

        Boolean flag = false;
        Boolean addFlag = false;
        Long userId = null;
        AuthorityApplyFor authorityApplyFor1 = this.getById(authorityApplyFor.getId());
        if (authorityApplyFor.getId() != null) {//修改

            authorityApplyFor.setUpdateTime(LocalDateTime.now());
            authorityApplyFor.setUpdateUser(AuthUtil.getUserId());
            if (authorityApplyFor.getReviewStatus() != null && authorityApplyFor.getReviewStatus() == 1) {

                LambdaQueryWrapper<BhApplicationManagement> queryApp = Wrappers.lambdaQuery(BhApplicationManagement.class).eq(BhApplicationManagement::getId, authorityApplyFor1.getApplicationManagementId())
                        .eq(BhApplicationManagement::getIsDeleted, 0);
                BhApplicationManagement one = bhApplicationManagementService.getOne(queryApp);
                if (Func.isEmpty(one)) {
                    throw new ServiceException("没有当前应用无法审核，请检查应用是否存在");
                }
                LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getAccount, authorityApplyFor1.getAccount()).eq(BhUser::getIsDeleted, 0);
                BhUser user = bhUserService.getOne(query);
                if (Func.isEmpty(user)) {
                    throw new ServiceException("没有当前账户不存在无法审核，请检查用户是否存在");
                } else {
                    userId = user.getId();
                    if (authorityApplyFor.getDeptId() == null){
                        authorityApplyFor.setDeptId(user.getMajorDept());
                    }
                }
            }

            flag = true;
        } else {


            LambdaQueryWrapper<BhApplicationManagement> queryApp = Wrappers.lambdaQuery(BhApplicationManagement.class).eq(BhApplicationManagement::getId, authorityApplyFor.getApplicationManagementId())
                    .eq(BhApplicationManagement::getIsDeleted, 0);
            BhApplicationManagement one = bhApplicationManagementService.getOne(queryApp);
            if (Func.isEmpty(one)) {
                throw new ServiceException("请检查应用是否存在");
            }
            LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getAccount, authorityApplyFor.getAccount()).eq(BhUser::getIsDeleted, 0);
            BhUser user = bhUserService.getOne(query);
            if (Func.isEmpty(user)) {
                throw new ServiceException("请检查用户是否存在");
            } else {
                userId = user.getId();
                if (authorityApplyFor.getDeptId() == null){
                    authorityApplyFor.setDeptId(user.getMajorDept());
                }
            }
            authorityApplyFor.setReviewStatus(0);
            authorityApplyFor.setCreateTime(LocalDateTime.now());
            authorityApplyFor.setCreateUser(AuthUtil.getUserId());
//			authorityApplyFor.setApplicant();
            addFlag = true;
        }

        boolean result = this.saveOrUpdate(authorityApplyFor);
        if (addFlag){//新增权限申请发送短信
            //获取应用对应的管理员信息
            List<User> users = userService.getUserByAppId(authorityApplyFor.getApplicationManagementId());
            logger.info("管理员："+users);
            LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getAccount, authorityApplyFor.getAccount()).eq(BhUser::getIsDeleted, 0);
            BhUser user = bhUserService.getOne(query);

            LambdaQueryWrapper<BhApplicationManagement> queryApp = Wrappers.lambdaQuery(BhApplicationManagement.class).eq(BhApplicationManagement::getId, authorityApplyFor.getApplicationManagementId())
                    .eq(BhApplicationManagement::getIsDeleted, 0);
            BhApplicationManagement appInfo = bhApplicationManagementService.getOne(queryApp);

            if (CollectionUtils.isNotEmpty(users)) {
//                for (User f : users) {
//                    String message = "尊敬的管理员，账户："+user.getAccount()+"申请了"
//                            +appInfo.getName()+"的访问权限，请及时确认。";
//                    logger.info("申请通知管理员短信发送内容："+message);
//
//                    smsSendSettingService.applictionNotification(f.getPhone(), message,f.getName(),appInfo.getAppId(),appInfo.getName());
//                }
                String phones = users.stream().map(User::getPhone).collect(Collectors.joining(","));
//                for (User f : users) {
                    String message = "尊敬的管理员，账户："+user.getAccount()+"申请了"
                            +appInfo.getName()+"的访问权限，请及时确认。";
                    logger.info("申请通知管理员短信发送内容："+message+"---发送的手机号："+phones);

                    smsSendSettingService.applictionNotification(users, message,appInfo.getAppId(),appInfo.getName());
//                }

            }
        }

        if (flag && authorityApplyFor.getReviewStatus() != null && authorityApplyFor.getReviewStatus() == 1) {
            List<ApplicationAuthority> list = new ArrayList<>();
            //审核通过 新增应用访问关系
            ApplicationAuthority applicationAuthority = new ApplicationAuthority();
            applicationAuthority.setApplicationId(authorityApplyFor1.getApplicationManagementId().toString());
            applicationAuthority.setBizId(userId.toString());
            applicationAuthority.setType(4);
            list.add(applicationAuthority);
            applicationAuthorityService.submitApplyFor(list);
        }
        if (flag && result && authorityApplyFor.getReviewStatus() != null){
            LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getAccount, authorityApplyFor1.getAccount()).eq(BhUser::getIsDeleted, 0);
            BhUser user = bhUserService.getOne(query);
            LambdaQueryWrapper<BhApplicationManagement> queryApp = Wrappers.lambdaQuery(BhApplicationManagement.class).eq(BhApplicationManagement::getId, authorityApplyFor1.getApplicationManagementId())
                    .eq(BhApplicationManagement::getIsDeleted, 0);
            BhApplicationManagement appInfo = bhApplicationManagementService.getOne(queryApp);
            if (Func.isNotEmpty(user)) {
                String message = "尊敬的"+user.getAccount()+"账户使用者，您申请的"
                        +appInfo.getName()+"的访问权限"+
                        (authorityApplyFor.getReviewStatus()==1 ? "已通过":"被驳回")+"，理由:"
                        +authorityApplyFor.getReviewOpinion();
                logger.info("审批短信发送内容："+message);
                logger.info("发送人："+user);
                smsSendSettingService.resultNotification(user.getMajorMobile(),user.getName(), message,appInfo.getAppId(),appInfo.getName());

            }

            DSQLoginUserVO dsqLoginUserVO = new DSQLoginUserVO();
            dsqLoginUserVO.setUsername(dsqUserName);
            dsqLoginUserVO.setPassword(dsqPassword);
            String token = null;
            JSONObject jsonObject = null;

            try {
                jsonObject = restTemplate.postForObject(dsqTokenUrl, dsqLoginUserVO, JSONObject.class);
                logger.info("都市圈获取Token信息：{}"+jsonObject);
                if ("200".equals(jsonObject.getString("code"))){
                    token = jsonObject.getString("token");
                }
                //发送成功回写结果
            } catch (Exception e) {
                logger.error("都市圈接口返回内容：{}，发送数据：{}", jsonObject, "data:" + JSONObject.toJSONString(dsqLoginUserVO));
            }

            //远程调用都市圈 传递审批状态
            DSQAuthorityApplyStatusVO dsqAuthorityApplyStatusVO = new DSQAuthorityApplyStatusVO();
            dsqAuthorityApplyStatusVO.setApplyId(user.getAccount());
            dsqAuthorityApplyStatusVO.setApplyUserName(user.getName());
            dsqAuthorityApplyStatusVO.setAppName(appInfo.getName());
            dsqAuthorityApplyStatusVO.setExamineUserId(AuthUtil.getUserAccount());
            dsqAuthorityApplyStatusVO.setExamineUserName(AuthUtil.getUserName());
            dsqAuthorityApplyStatusVO.setStatus(authorityApplyFor.getReviewStatus()==1 ? 0 : 2);

            //获取请求头
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Authorization", token);
            HttpEntity requestEntity = new HttpEntity(dsqAuthorityApplyStatusVO, requestHeaders);
            JSONObject resultVO = null;
            try {
                resultVO = restTemplate.postForObject(dsqUrl, requestEntity, JSONObject.class);
                logger.info("返回数据结构：{}"+resultVO);
                //发送成功回写结果
            } catch (Exception e) {
                logger.error("都市圈接口返回内容：{}，发送数据：{}", resultVO, "data:" + JSONObject.toJSONString(dsqAuthorityApplyStatusVO));
            }
        }

        return result;
    }

//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        DSQLoginUserVO dsqLoginUserVO = new DSQLoginUserVO();
//        dsqLoginUserVO.setUsername("admin");
//        dsqLoginUserVO.setPassword("O.cnwx123");
//
//        String tokenUrl = "http://2.21.137.206:11511/system_server/login";
//        String token = null;
//        JSONObject jsonObject = null;
//
//        ResultVO resultVO = new ResultVO();
//        //远程调用都市圈 传递审批状态
//        DSQAuthorityApplyStatusVO dsqAuthorityApplyStatusVO = new DSQAuthorityApplyStatusVO();
//        dsqAuthorityApplyStatusVO.setApplyId("1");
//        dsqAuthorityApplyStatusVO.setApplyUserName("user.getName()");
//        dsqAuthorityApplyStatusVO.setAppName("appInfo.getName()");
//        dsqAuthorityApplyStatusVO.setExamineUserId("2l");
//        dsqAuthorityApplyStatusVO.setExamineUserName("AuthUtil.getUserName()");
//        dsqAuthorityApplyStatusVO.setStatus(2==1 ? 0 : 2);
//        try {
//
//            jsonObject = restTemplate.postForObject(tokenUrl, dsqLoginUserVO, JSONObject.class);
//            //发送成功回写结果
//
//            String data = jsonObject.getString("token");
//
//            //获取请求头
//            HttpHeaders requestHeaders = new HttpHeaders();
//            requestHeaders.set("Token", "1122339955555.....");
//            HttpEntity requestEntity = new HttpEntity(dsqAuthorityApplyStatusVO, requestHeaders);
//
//            String dsqUrl = "http://127.0.0.1:9898/xsyzw-sms/sms/test";
//            resultVO = restTemplate.postForObject(dsqUrl, requestEntity, ResultVO.class);
//            //发送成功回写结果
//        } catch (Exception e) {
//            logger.error("都市圈接口返回内容：{}，发送数据：{}", resultVO, "data:" + dsqAuthorityApplyStatusVO);
//        }
//
//    }


    @Override
    public String getToken() {

        DSQLoginUserVO dsqLoginUserVO = new DSQLoginUserVO();
//        dsqLoginUserVO.setUsername("admin");
//        dsqLoginUserVO.setPassword("O.cnwx123");
        dsqLoginUserVO.setUsername(dsqUserName);
        dsqLoginUserVO.setPassword(dsqPassword);

//        String tokenUrl = "http://2.21.137.206:11511/system_server/login";

        JSONObject jsonObject = null;
        try {
            jsonObject = restTemplate.postForObject(dsqTokenUrl, dsqLoginUserVO, JSONObject.class);
        }catch (Exception e){

        }
        return jsonObject.toJSONString();

    }

    @Override
    public Boolean submitList(AuthorityApplyForIdsVO authorityApplyFor) {
        List<Long> ids = Func.toLongList(authorityApplyFor.getIds());
        if (authorityApplyFor.getReviewStatus() == null){
            return true;
        }
        //将已审核的过滤掉

        //审核状态为通过 判断应用是否存在 判断应用是否存在 用户是否存在
        LambdaQueryWrapper<AuthorityApplyFor> queryAuthorityApplyList = Wrappers.lambdaQuery(AuthorityApplyFor.class).in(AuthorityApplyFor::getId, ids).eq(AuthorityApplyFor::getIsDeleted, 0);
        List<AuthorityApplyFor> list = this.list(queryAuthorityApplyList);
        //根据user账户进行分组
        //获取所有的应用
        LambdaQueryWrapper<BhApplicationManagement> queryApp = Wrappers.lambdaQuery(BhApplicationManagement.class)
                .in(BhApplicationManagement::getId, list.stream().map(AuthorityApplyFor::getApplicationManagementId).distinct().collect(Collectors.toList()))
                .eq(BhApplicationManagement::getIsDeleted, 0);
        List<BhApplicationManagement> applicationManagementList = bhApplicationManagementService.list(queryApp);
        Map<Long,BhApplicationManagement> applicationManagementMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(applicationManagementList)){
            applicationManagementMap = applicationManagementList.stream().collect(Collectors.toMap(BhApplicationManagement::getId,k->k,(o1,o2)->o1 ));
        }
        //获取所有的人员
        LambdaQueryWrapper<BhUser> queryBhUser = Wrappers.lambdaQuery(BhUser.class)
                .in(BhUser::getAccount, list.stream().map(AuthorityApplyFor::getAccount).distinct().collect(Collectors.toList()) )
                .eq(BhUser::getIsDeleted, 0);
        List<BhUser> userList = bhUserService.list(queryBhUser);
        Map<String,BhUser> bhUserMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(userList)){
            bhUserMap = userList.stream().collect(Collectors.toMap(BhUser::getAccount,k->k,(o1,o2)->o1 ));
        }

        if (authorityApplyFor.getReviewStatus() != null && authorityApplyFor.getReviewStatus() == 1){
            for (AuthorityApplyFor applyFor : list) {
                if (Func.isEmpty(applicationManagementMap.get(applyFor.getApplicationManagementId()))) {
                    throw new ServiceException("申请应为id为:"+applyFor.getApplicationManagementId()+"的应用不存在，请检查批量审核的应用是否存在");
                }
                if (Func.isEmpty(bhUserMap.get(applyFor.getAccount()))) {
                    throw new ServiceException(applyFor.getAccount()+"账户不存在无法审核，请检查批量审核的账户是否存在");
                }else {
                    if (applyFor.getDeptId() == null){
                        applyFor.setDeptId(bhUserMap.get(applyFor.getAccount()).getMajorDept());
                    }

                }
                applyFor.setUpdateTime(LocalDateTime.now());
                applyFor.setUpdateUser(AuthUtil.getUserId());
                applyFor.setReviewStatus(authorityApplyFor.getReviewStatus());
                applyFor.setReviewOpinion(authorityApplyFor.getReviewOpinion());
            }

        }else {
            for (AuthorityApplyFor applyFor : list) {
                applyFor.setUpdateTime(LocalDateTime.now());
                applyFor.setUpdateUser(AuthUtil.getUserId());
                applyFor.setReviewStatus(authorityApplyFor.getReviewStatus());
                applyFor.setReviewOpinion(authorityApplyFor.getReviewOpinion());
            }

        }

        boolean result = this.updateBatchById(list);
        List<ApplicationAuthority> authorities = new ArrayList<>();
        Map<String, List<AuthorityApplyFor>> collect = list.stream().collect(Collectors.groupingBy(AuthorityApplyFor::getAccount));
        if (result && authorityApplyFor.getReviewStatus() != null && authorityApplyFor.getReviewStatus() == 1) {
            for (Map.Entry<String, List<AuthorityApplyFor>> entry : collect.entrySet()) {
                BhUser bhUser = userList.stream().filter(f -> f.getAccount().equals(entry.getKey())).collect(Collectors.toList()).get(0);
                List<Long> appIds = new ArrayList<>();
                List<String> appNames = new ArrayList<>();
                for (AuthorityApplyFor applyFor : entry.getValue()) {
                    appIds.add(applyFor.getApplicationManagementId());
                    appNames.add(applicationManagementMap.get(applyFor.getApplicationManagementId()).getName());
                }
                //审核通过 新增应用访问关系
                ApplicationAuthority applicationAuthority = new ApplicationAuthority();
                applicationAuthority.setApplicationId(Func.join(appIds,","));
                applicationAuthority.setBizId(bhUser.getId().toString());
                applicationAuthority.setType(4);
                authorities.add(applicationAuthority);


            }
            applicationAuthorityService.submitApplyFor(authorities);

        }
        if (result){
            for (Map.Entry<String, List<AuthorityApplyFor>> entry : collect.entrySet()) {

                BhUser bhUser = null;
                if (CollectionUtils.isNotEmpty(userList.stream().filter(f -> f.getAccount().equals(entry.getKey())).collect(Collectors.toList()))){
                    bhUser = userList.stream().filter(f -> f.getAccount().equals(entry.getKey())).collect(Collectors.toList()).get(0);
                }
                List<String> appNames = new ArrayList<>();
                for (AuthorityApplyFor applyFor : entry.getValue()) {
                    if (applicationManagementMap.get(applyFor.getApplicationManagementId()) != null){
                        appNames.add(applicationManagementMap.get(applyFor.getApplicationManagementId()).getName());
                    }

                }
                if (Func.isNotEmpty(bhUser)) {
                    String message = "尊敬的"+bhUser.getAccount()+"账户使用者，您申请的"
                            +Func.join(appNames,",")+"的访问权限"+
                            (authorityApplyFor.getReviewStatus()==1 ? "已通过":"被驳回")+"，理由:"
                            +authorityApplyFor.getReviewOpinion();
                    logger.info("批量审批短信发送内容："+message);
                    Boolean sendFlag = smsSendSettingService.testSend(bhUser.getMajorMobile(), message);

                }


            }

        }


        //远程调用都市圈 传递审批状态
        if (CollectionUtils.isNotEmpty(list) && result){
            DSQLoginUserVO dsqLoginUserVO = new DSQLoginUserVO();
            dsqLoginUserVO.setUsername(dsqUserName);
            dsqLoginUserVO.setPassword(dsqPassword);
            String token = null;
            JSONObject jsonObject = null;

            try {
                jsonObject = restTemplate.postForObject(dsqTokenUrl, dsqLoginUserVO, JSONObject.class);
                logger.info("都市圈获取Token信息：{}"+jsonObject);
                if ("200".equals(jsonObject.getString("code"))){
                    token = jsonObject.getString("token");
                }

                //发送成功回写结果
            } catch (Exception e) {
                logger.error("都市圈接口返回内容：{}，发送数据：{}", jsonObject, "data:" + JSONObject.toJSONString(dsqLoginUserVO));
            }

            for (AuthorityApplyFor applyFor : list) {
                BhUser bhUser = null;
                if (CollectionUtils.isNotEmpty(userList.stream().filter(f -> f.getAccount().equals(applyFor.getAccount())).collect(Collectors.toList()))){
                    bhUser = userList.stream().filter(f -> f.getAccount().equals(applyFor.getAccount())).collect(Collectors.toList()).get(0);
                }

                //远程调用都市圈 传递审批状态
                DSQAuthorityApplyStatusVO dsqAuthorityApplyStatusVO = new DSQAuthorityApplyStatusVO();
                dsqAuthorityApplyStatusVO.setApplyId(bhUser.getAccount());
                dsqAuthorityApplyStatusVO.setApplyUserName(bhUser.getName());
                dsqAuthorityApplyStatusVO.setAppName(applicationManagementMap.get(applyFor.getApplicationManagementId()).getName());
                dsqAuthorityApplyStatusVO.setExamineUserId(AuthUtil.getUserAccount());
                dsqAuthorityApplyStatusVO.setExamineUserName(AuthUtil.getUserName());
                dsqAuthorityApplyStatusVO.setStatus(authorityApplyFor.getReviewStatus()==1 ? 0 : 2);

                ResultVO resultVO = new ResultVO();
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.set("Authorization", token);
                HttpEntity requestEntity = new HttpEntity(dsqAuthorityApplyStatusVO, requestHeaders);
                try {
                    resultVO = restTemplate.postForObject(dsqUrl, requestEntity, ResultVO.class);
                } catch (Exception e) {
                    logger.error("都市圈接口返回内容：{}，发送数据：{}", resultVO, "data:" + JSONObject.toJSONString(dsqAuthorityApplyStatusVO));
                }
            }
        }
        return result;
    }

    @Override
    public IPage<AuthorityApplyForRestVO> authorityApplyForList(IPage<AuthorityApplyForRestVO> page, String applicant) {
        List<AuthorityApplyForRestVO> list = baseMapper.authorityApplyForList(page, applicant);
        return page.setRecords(list);
    }

    @Override
    public IPage<AuthorityApplyForVO> getUserApplyFor(IPage<AuthorityApplyForVO> page, String key, Integer reviewStatus) {

        List<AuthorityApplyForVO> result = baseMapper.getUserApplyFor(null,key,reviewStatus,AuthUtil.getUserAccount());
        if (CollectionUtils.isNotEmpty(result) && Func.isNotEmpty(key)){
            result =result.stream().filter(f->f.getAppName() != null && (f.getAppName().contains(key) || f.getAppId().contains(key))).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(result)){
            for (AuthorityApplyForVO f : result) {
                f.setReviewStatusName(SwapConstant.ReviewStatus.getDescByValue(f.getReviewStatus()));

            }
        }
        IPage iPage = CommonUtil.manualPage(page, result);
        return iPage;
    }


    @Override
    public List<HeadPageVO> getApplicationApplyForRanking() {
        List<HeadPageVO> result = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        List<AuthorityApplyForVO> list = baseMapper.getApplicationApplyForRanking(start,end);
        if (CollectionUtils.isNotEmpty(list)){
            Map<Long, List<AuthorityApplyForVO>> collect = list.stream().collect(Collectors.groupingBy(AuthorityApplyForVO::getApplicationManagementId));
            for (Map.Entry<Long, List<AuthorityApplyForVO>> entry : collect.entrySet()) {
                HeadPageVO headPageVO = new HeadPageVO();
                headPageVO.setNumber(entry.getValue().size());
                headPageVO.setName(entry.getValue().get(0).getAppName());
//				headPageVO.setPercent(new BigDecimal(entry.getValue().size()).divide(new BigDecimal(list.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");

                result.add(headPageVO);
            }
        }
        if (CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(HeadPageVO::getNumber).reversed()).collect(Collectors.toList());
        }

        return result;
    }


    @Override
    public HeadPageDSQVO getHomePageAnalysis() {
        HeadPageDSQVO result = new HeadPageDSQVO();

        List<AuthorityApplyFor> list = this.list(Wrappers.lambdaQuery(AuthorityApplyFor.class).eq(AuthorityApplyFor::getIsDeleted, 0));
        Integer approvalsNum = 0;
        Integer applyForNum = 0;
        List<MapDSQVO> echarts = new ArrayList<>();
        List<AuthorityApplyFor> checkList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            checkList = list.stream().filter(f -> f.getReviewStatus() != null && (f.getReviewStatus() == 1 || f.getReviewStatus() == 2)).collect(Collectors.toList());
            applyForNum = list.size();
            if (CollectionUtils.isNotEmpty(checkList)){
                approvalsNum =  checkList.size();

            }
            List<AuthorityApplyFor> collect1 = list.stream().filter(f -> f.getReviewStatus() != null && f.getReviewStatus() == 0).collect(Collectors.toList());
            List<AuthorityApplyFor> collect2 = list.stream().filter(f -> f.getReviewStatus() != null && f.getReviewStatus() == 1).collect(Collectors.toList());
            List<AuthorityApplyFor> collect3 = list.stream().filter(f -> f.getReviewStatus() != null && f.getReviewStatus() == 2).collect(Collectors.toList());

            MapDSQVO mapDSQVO = new MapDSQVO();
            mapDSQVO.setName("待审核");
            mapDSQVO.setNum(CollectionUtils.isNotEmpty(collect1) ? collect1.size()+"" : "0");
            echarts.add(mapDSQVO);

            MapDSQVO mapDSQVO2 = new MapDSQVO();
            mapDSQVO2.setName("通过");
            mapDSQVO2.setNum(CollectionUtils.isNotEmpty(collect2) ? collect1.size()+"" : "0");
            echarts.add(mapDSQVO2);

            MapDSQVO mapDSQVO3 = new MapDSQVO();
            mapDSQVO3.setName("驳回");
            mapDSQVO3.setNum(CollectionUtils.isNotEmpty(collect3) ? collect1.size()+"" : "0");
            echarts.add(mapDSQVO3);

            MapDSQVO mapDSQVO4 = new MapDSQVO();
            mapDSQVO4.setName("撤回");
            mapDSQVO4.setNum("0");
            echarts.add(mapDSQVO4);
        }
        result.setApplyForNum(applyForNum+"");
        result.setApprovalsNum(approvalsNum+"");
        result.setEcharts(echarts);

        return result;
    }

    @Override
    public DeptUserVO getUserInfo() {
        String userAccount = AuthUtil.getUserAccount();
        DeptUserVO userInfo = baseMapper.getUserInfo(userAccount);
        return userInfo;
    }

    @Override
    public List<ApplyForStatisticsDSQVO> getApplyForStatistics() {
        List<ApplyForStatisticsDSQVO> result = new ArrayList<>();
        List<AuthorityApplyForVO> list = baseMapper.getApplyForStatistics(null,null);

        if (CollectionUtils.isNotEmpty(list)){
            Map<String,List<AuthorityApplyForVO>> map = list.stream().collect(Collectors.groupingBy(AuthorityApplyForVO::getAppId));
            for (Map.Entry<String, List<AuthorityApplyForVO>> entry : map.entrySet()) {
                ApplyForStatisticsDSQVO applyForStatisticsDSQVO = new ApplyForStatisticsDSQVO();
                AuthorityApplyForVO authorityApplyForVO = entry.getValue().get(0);
                applyForStatisticsDSQVO.setAppId(authorityApplyForVO.getAppId()+"");
                applyForStatisticsDSQVO.setApplicationName(authorityApplyForVO.getAppName());
                applyForStatisticsDSQVO.setId(authorityApplyForVO.getApplicationManagementId());
                applyForStatisticsDSQVO.setApplyForNum(entry.getValue().size()+"");
                applyForStatisticsDSQVO.setAppSecret(authorityApplyForVO.getAppSecret());
                result.add(applyForStatisticsDSQVO);

            }
        }

        return result;
    }

    @Override
    public EchartsDSQVO getApplyForEcharts() {
        EchartsDSQVO result = new EchartsDSQVO();
        List<String> xList = new ArrayList<>();
        List<String> yList = new ArrayList<>();
//        LocalDateTime start = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),LocalTime.MIN);
//        LocalDateTime end = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),LocalTime.MAX);
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.plusDays(-29L);
        List<AuthorityApplyForVO> list = baseMapper.getApplyForStatistics(start,end);
        Map<String,List<AuthorityApplyForVO>> map = new HashMap<>();

        if (CollectionUtils.isNotEmpty(list)){
            map = list.stream().filter(k->k.getCreateTime() != null).collect(Collectors.groupingBy(f->f.getCreateTime().getDayOfMonth()+""));
        }
        for (LocalDateTime i = start; i.isBefore(end);) {

            xList.add(i.getDayOfMonth()+"");
            List<AuthorityApplyForVO> dayList = map.get(i.getDayOfMonth() + "");
            yList.add(CollectionUtils.isNotEmpty(dayList) ? dayList.size()+"" : "0");
            i = i.plusDays(1L);
        }
        result.setX(xList);
        result.setY(yList);
        return result;
    }

    @Override
    public List<NVMapVO> getDeptRank() {
        List<NVMapVO> result = new ArrayList<>();
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.plusDays(-29L);
        List<AuthorityApplyForVO> list = baseMapper.getApplyForStatistics(start,end);
        Map<Long,List<AuthorityApplyForVO>> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)){
            map = list.stream().collect(Collectors.groupingBy(AuthorityApplyForVO::getApplicationManagementId));
        }
        if (CollectionUtils.isNotEmpty(map)){
            for (Map.Entry<Long, List<AuthorityApplyForVO>> entry : map.entrySet()) {
                NVMapVO nvMapVO = new NVMapVO();
                nvMapVO.setName(entry.getValue().get(0).getAppName());
                nvMapVO.setValue(entry.getValue().size());
                result.add(nvMapVO);
            }
        }

        if (CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(NVMapVO::getValue).reversed()).limit(8L).collect(Collectors.toList());
        }

        return result;
    }
}

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

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.jslc.modules.system.entity.User;
import com.jslc.modules.system.service.IUserService;
import com.jslc.modules.szbh.entity.AppUseRecord;
import com.jslc.modules.szbh.entity.BhSmsSend;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.entity.LoginRecord;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.vo.AppUseRecordVO;
import com.jslc.modules.szbh.vo.HeadActiveVO;
import com.jslc.modules.szbh.vo.HeadPageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 空气大屏  服务实现类
 *
 * @author BladeX
 * @since 2020-09-03
 */
@Service
@AllArgsConstructor
public class HeadPageServiceImpl  implements IHeadPageService {


    @Autowired
    private BladeRedis bladeRedis;

    @Autowired
    private IBhUserService bhUserService;
    @Autowired
    private ILoginRecordService loginRecordService;
    @Autowired
    private IBhSmsSendService bhSmsSendService;
    @Autowired
    private IAppUseRecordService appUseRecordService;
    @Autowired
    private  IUserService userService;


    @Override
    public Map<String, Object> getUserInfo() {
        Map<String, Object> result = new HashMap<>();
        Long userId = AuthUtil.getUserId();
        User user = userService.getById(userId);
        if (Func.isNotEmpty(user)){
            result.put("name",user.getName());
            LambdaQueryWrapper<BhUser> queryBh = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getAccount, user.getAccount()).eq(BhUser::getIsDeleted,0);
            BhUser bhUser = bhUserService.getOne(queryBh);
            if (Func.isNotEmpty(bhUser)){
                result.put("dept",bhUser.getMajorDept());
                result.put("position",bhUser.getMajorPosition());
            }
        }


        return result;
    }

    @Override
    public List<HeadPageVO> getBulletin() {
        List<HeadPageVO> result = new ArrayList<>();
        result.add(getCreateUser(null));
        result.add(getLoginUser(null));
        result.add(getSmsRecord(null));
        result.add(getAppRecord(null));
        result.add(getCreateUser(1));
        result.add(getLoginUser(1));
        result.add(getSmsRecord(1));
        result.add(getAppRecord(1));


        return result;
    }

    @Override
    public List<HeadPageVO> getAppRecordRank() {
        List<HeadPageVO> result = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

        List<AppUseRecordVO> list = appUseRecordService.getAppRecord(start,end);
        if (CollectionUtils.isNotEmpty(list)){
            Map<Long, List<AppUseRecordVO>> collect = list.stream().collect(Collectors.groupingBy(AppUseRecordVO::getAppId));
            for (Map.Entry<Long, List<AppUseRecordVO>> entry : collect.entrySet()) {
                HeadPageVO headPageVO = new HeadPageVO();
                headPageVO.setNumber(entry.getValue().size());
                headPageVO.setName(entry.getValue().get(0).getAppName());
                headPageVO.setPercent(new BigDecimal(entry.getValue().size()).divide(new BigDecimal(list.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");

                result.add(headPageVO);
            }
        }
        if (CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(HeadPageVO::getNumber).reversed()).collect(Collectors.toList());
        }

        return result;
    }


    @Override
    public HeadActiveVO getYearActive() {
        HeadActiveVO headActiveVO = new HeadActiveVO();

        List<String> time = Stream.of("1","2","3","4","5","6","7","8","9","10","11","12").collect(Collectors.toList());
        List<String> createUserNum =  Stream.of("0","0","0","0","0","0","0","0","0","0","0","0").collect(Collectors.toList());
        List<String> loginNum =  Stream.of("0","0","0","0","0","0","0","0","0","0","0","0").collect(Collectors.toList());

        headActiveVO.setCreateUserNum(createUserNum);
        headActiveVO.setLoginNum(loginNum);
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()),LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()),LocalTime.MAX);
        //当年新增
        LambdaQueryWrapper<BhUser> queryUser = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, startTime, endTime);
        List<BhUser> users = bhUserService.list(queryUser);
        //当年登录
        LambdaQueryWrapper<LoginRecord> query = Wrappers.lambdaQuery(LoginRecord.class).between(LoginRecord::getCreateTime, startTime, endTime);
        List<LoginRecord> login = loginRecordService.list(query);
        if (CollectionUtils.isNotEmpty(users)){
            Map<Integer, List<BhUser>> collect = users.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().getMonthValue()));
            for (String s : time) {
                List<BhUser> users1 = collect.get(Integer.valueOf(s));
                if (CollectionUtils.isNotEmpty(users1)){
                    createUserNum.set(Integer.valueOf(s)-1,users1.size()+"");
                }
            }

        }

        if (CollectionUtils.isNotEmpty(login)){
            Map<Integer, List<LoginRecord>> collect = login.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().getMonthValue()));
            for (String s : time) {
                List<LoginRecord> loginRecords = collect.get(Integer.valueOf(s));
                if (CollectionUtils.isNotEmpty(loginRecords)){
                    loginNum.set(Integer.valueOf(s)-1,loginRecords.size()+"");
                }
            }
        }
        List<String> collect = time.stream().map(f -> f + "月").collect(Collectors.toList());
        headActiveVO.setTime(collect);



        return headActiveVO;
    }

    public HeadPageVO getCreateUser(Integer type){
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        //今日新增
        LambdaQueryWrapper<BhUser> queryUser = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, startTime, endTime);
        List<BhUser> create = bhUserService.list(queryUser);
        //昨日新增
        LambdaQueryWrapper<BhUser> queryOld = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, startTime.plusDays(-1L), endTime.plusDays(-1L));
        List<BhUser> oldCreate = bhUserService.list(queryOld);
        List<BhUser> listUser = bhUserService.list(Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0));
        HeadPageVO createUser = new HeadPageVO();
        createUser.setName("今日新增用户");
        createUser.setNumber(0);
        createUser.setPercent("0");
        if (CollectionUtils.isNotEmpty(create)){
            createUser.setNumber(create.size());
            if (CollectionUtils.isNotEmpty(listUser)){
                createUser.setPercent(new BigDecimal(create.size()).divide(new BigDecimal(listUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
            }
        }

        if (CollectionUtils.isNotEmpty(create)){
            createUser.setNumber(create.size());
            if (CollectionUtils.isNotEmpty(create) && CollectionUtils.isNotEmpty(oldCreate) && create.size() - oldCreate.size() != 0){
                createUser.setPercent(new BigDecimal(create.size()-oldCreate.size()).divide(new BigDecimal(oldCreate.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
            }else if (CollectionUtils.isEmpty(oldCreate) && CollectionUtils.isNotEmpty(create)){
                createUser.setPercent("100");
            }
        }

        if (type != null){
            createUser.setName("总计用户");
            createUser.setNumber(0);
            createUser.setPercent("0");
            startTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
            LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, startTime, endTime);
            List<BhUser> upUser = bhUserService.list(query);
            List<BhUser> listAll = bhUserService.list(Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0));

            LambdaQueryWrapper<BhUser> queryNow = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, startTime.plusMonths(1L), endTime.plusMonths(1L));
            List<BhUser> nowList = bhUserService.list(queryNow);

            if (CollectionUtils.isNotEmpty(listAll)){
                createUser.setNumber(listAll.size());
                if (CollectionUtils.isNotEmpty(upUser) && CollectionUtils.isNotEmpty(nowList) && nowList.size() - upUser.size() != 0){
                    createUser.setPercent(new BigDecimal(nowList.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
                }else if (CollectionUtils.isEmpty(upUser) && CollectionUtils.isNotEmpty(nowList)){
                    createUser.setPercent("100");
                }
            }

        }

        return createUser;
    }

    public HeadPageVO getLoginUser(Integer type){
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        //今日
//        LambdaQueryWrapper<LoginRecord> query = Wrappers.lambdaQuery(LoginRecord.class).between(LoginRecord::getCreateTime, startTime, endTime);
        List<LoginRecord> create = loginRecordService.getBhUserRecord(startTime,endTime);

        List<LoginRecord> list = loginRecordService.list();
        HeadPageVO createUser = new HeadPageVO();
        createUser.setName("活跃用户");
        createUser.setNumber(0);
        createUser.setPercent("0");

//        if (CollectionUtils.isNotEmpty(create)){
//            createUser.setNumber(create.size());
//            if (CollectionUtils.isNotEmpty(list)){
//                createUser.setPercent(new BigDecimal(create.size()).divide(new BigDecimal(list.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
//            }
//        }

        //左日
//        LambdaQueryWrapper<LoginRecord> queryOld = Wrappers.lambdaQuery(LoginRecord.class).between(LoginRecord::getCreateTime, startTime.plusDays(-1L), endTime.plusDays(-1L));
        List<LoginRecord> oldCreate = loginRecordService.getBhUserRecord(startTime.plusDays(-1L),endTime.plusDays(-1L));
        if (CollectionUtils.isNotEmpty(create)){
            int createNum = create.stream().map(LoginRecord::getUsername).distinct().collect(Collectors.toList()).size();
            createUser.setNumber(createNum);
            if (CollectionUtils.isNotEmpty(create) && CollectionUtils.isNotEmpty(oldCreate) && createNum - oldCreate.stream().map(LoginRecord::getUsername).distinct().collect(Collectors.toList()).size() != 0){
                int oldNum = oldCreate.stream().map(LoginRecord::getUsername).distinct().collect(Collectors.toList()).size();
                createUser.setPercent(new BigDecimal(createNum-oldNum).divide(new BigDecimal(oldNum),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
            }else if (CollectionUtils.isEmpty(oldCreate) && CollectionUtils.isNotEmpty(create)){
                createUser.setPercent("100");
            }
        }


        if (type != null){
            createUser.setName("总计登录");
            createUser.setNumber(0);
            createUser.setPercent("0");
            startTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
            LambdaQueryWrapper<LoginRecord> queryWrapper = Wrappers.lambdaQuery(LoginRecord.class).between(LoginRecord::getCreateTime, startTime, endTime);
            List<LoginRecord> upUser = loginRecordService.list(queryWrapper);
            List<LoginRecord> listAll = loginRecordService.list();

//            if (CollectionUtils.isNotEmpty(listAll)){
//                createUser.setNumber(listAll.size());
//                if (CollectionUtils.isNotEmpty(upUser) && listAll.size() > upUser.size()){
//                    createUser.setPercent(new BigDecimal(listAll.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
//                }
//            }

            //本月
            LambdaQueryWrapper<LoginRecord> queryNow = Wrappers.lambdaQuery(LoginRecord.class).between(LoginRecord::getCreateTime, startTime.plusMonths(1L), endTime.plusMonths(1L));
            List<LoginRecord> nowList = loginRecordService.list(queryNow);

            if (CollectionUtils.isNotEmpty(listAll)){
                createUser.setNumber(listAll.size());
                if (CollectionUtils.isNotEmpty(upUser) && CollectionUtils.isNotEmpty(nowList) && nowList.size() - upUser.size() != 0){
                    createUser.setPercent(new BigDecimal(nowList.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
                }else if (CollectionUtils.isEmpty(upUser) && CollectionUtils.isNotEmpty(nowList)){
                    createUser.setPercent("100");
                }
            }



        }

        return createUser;
    }


    public HeadPageVO getSmsRecord(Integer type){
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LambdaQueryWrapper<BhSmsSend> query = Wrappers.lambdaQuery(BhSmsSend.class).between(BhSmsSend::getCreateTime, startTime, endTime);
        List<BhSmsSend> create = bhSmsSendService.list(query);
        List<BhSmsSend> list = bhSmsSendService.list();
        HeadPageVO createUser = new HeadPageVO();
        createUser.setName("短信推送");
        createUser.setNumber(0);
        createUser.setPercent("0");
//        if (CollectionUtils.isNotEmpty(create)){
//            createUser.setNumber(create.size());
//            if (CollectionUtils.isNotEmpty(list)){
//                createUser.setPercent(new BigDecimal(create.size()).divide(new BigDecimal(list.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
//            }
//        }
        //左日
        LambdaQueryWrapper<BhSmsSend> queryOld = Wrappers.lambdaQuery(BhSmsSend.class).between(BhSmsSend::getCreateTime, startTime.plusDays(-1L), endTime.plusDays(-1L));
        List<BhSmsSend> oldCreate = bhSmsSendService.list(queryOld);
        if (CollectionUtils.isNotEmpty(create)){
            createUser.setNumber(create.size());
            if (CollectionUtils.isNotEmpty(create) && CollectionUtils.isNotEmpty(oldCreate) && create.size() - oldCreate.size() != 0){
                createUser.setPercent(new BigDecimal(create.size()-oldCreate.size()).divide(new BigDecimal(oldCreate.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
            }else if (CollectionUtils.isEmpty(oldCreate) && CollectionUtils.isNotEmpty(create)){
                createUser.setPercent("100");
            }
        }

        if (type != null){
            createUser.setName("短信总计");
            createUser.setNumber(0);
            createUser.setPercent("0");
            startTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
            LambdaQueryWrapper<BhSmsSend> queryWrapper = Wrappers.lambdaQuery(BhSmsSend.class).between(BhSmsSend::getCreateTime, startTime, endTime);
            List<BhSmsSend> upUser = bhSmsSendService.list(queryWrapper);
            List<BhSmsSend> listAll = bhSmsSendService.list();

//            if (CollectionUtils.isNotEmpty(listAll)){
//                createUser.setNumber(listAll.size());
//                if (CollectionUtils.isNotEmpty(upUser) && listAll.size() > upUser.size()){
//                    createUser.setPercent(new BigDecimal(listAll.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
//                }
//            }
            //本月
            LambdaQueryWrapper<BhSmsSend> queryNow = Wrappers.lambdaQuery(BhSmsSend.class).between(BhSmsSend::getCreateTime, startTime.plusMonths(1L), endTime.plusMonths(1L));
            List<BhSmsSend> nowList = bhSmsSendService.list(queryNow);

            if (CollectionUtils.isNotEmpty(listAll)){
                createUser.setNumber(listAll.size());
                if (CollectionUtils.isNotEmpty(upUser) && CollectionUtils.isNotEmpty(nowList) && nowList.size() - upUser.size() != 0){
                    createUser.setPercent(new BigDecimal(nowList.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
                }else if (CollectionUtils.isEmpty(upUser) && CollectionUtils.isNotEmpty(nowList)){
                    createUser.setPercent("100");
                }
            }

        }

        return createUser;
    }

    public HeadPageVO getAppRecord(Integer type){
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        LambdaQueryWrapper<AppUseRecord> query = Wrappers.lambdaQuery(AppUseRecord.class).between(AppUseRecord::getCreateTime, startTime, endTime);
        List<AppUseRecord> create = appUseRecordService.list(query);
        List<AppUseRecord> list = appUseRecordService.list();
        HeadPageVO createUser = new HeadPageVO();
        createUser.setName("今日请求");
        createUser.setNumber(0);
        createUser.setPercent("0");
//        if (CollectionUtils.isNotEmpty(create)){
//            createUser.setNumber(create.size());
//            if (CollectionUtils.isNotEmpty(list)){
//                createUser.setPercent(new BigDecimal(create.size()).divide(new BigDecimal(list.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
//            }
//        }

        //左日
        LambdaQueryWrapper<AppUseRecord> queryOld = Wrappers.lambdaQuery(AppUseRecord.class).between(AppUseRecord::getCreateTime, startTime.plusDays(-1L), endTime.plusDays(-1L));
        List<AppUseRecord> oldCreate = appUseRecordService.list(queryOld);
        if (CollectionUtils.isNotEmpty(create)){
            createUser.setNumber(create.size());
            if (CollectionUtils.isNotEmpty(create) && CollectionUtils.isNotEmpty(oldCreate) && create.size() - oldCreate.size() != 0){
                createUser.setPercent(new BigDecimal(create.size()-oldCreate.size()).divide(new BigDecimal(oldCreate.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
            }else if (CollectionUtils.isEmpty(oldCreate) && CollectionUtils.isNotEmpty(create)){
                createUser.setPercent("100");
            }
        }

        if (type != null){
            createUser.setName("接口总计");
            createUser.setNumber(0);
            createUser.setPercent("0");
            startTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now().plusMonths(-1L).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
            LambdaQueryWrapper<AppUseRecord> queryWrapper = Wrappers.lambdaQuery(AppUseRecord.class).between(AppUseRecord::getCreateTime, startTime, endTime);
            List<AppUseRecord> upUser = appUseRecordService.list(queryWrapper);
            List<AppUseRecord> listAll = appUseRecordService.list();

//            if (CollectionUtils.isNotEmpty(listAll)){
//                createUser.setNumber(listAll.size());
//                if (CollectionUtils.isNotEmpty(upUser) && listAll.size() > upUser.size()){
//                    createUser.setPercent(new BigDecimal(listAll.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
//                }
//            }

            //本月
            LambdaQueryWrapper<AppUseRecord> queryNow = Wrappers.lambdaQuery(AppUseRecord.class).between(AppUseRecord::getCreateTime, startTime.plusMonths(1L), endTime.plusMonths(1L));
            List<AppUseRecord> nowList = appUseRecordService.list(queryNow);

            if (CollectionUtils.isNotEmpty(listAll)){
                createUser.setNumber(listAll.size());
                if (CollectionUtils.isNotEmpty(upUser) && CollectionUtils.isNotEmpty(nowList) && nowList.size() - upUser.size() != 0){
                    createUser.setPercent(new BigDecimal(nowList.size()-upUser.size()).divide(new BigDecimal(upUser.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");
                }else if (CollectionUtils.isEmpty(upUser) && CollectionUtils.isNotEmpty(nowList)){
                    createUser.setPercent("100");
                }
            }

        }

        return createUser;
    }



}

package com.ruoyi.system.customer.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.vo.CommonRes;
import com.ruoyi.system.customer.domain.*;
import com.ruoyi.system.customer.mapper.CmAbilityMapper;
import com.ruoyi.system.customer.mapper.HouseInfoMapper;
import com.ruoyi.system.customer.service.AbilityService;
import com.ruoyi.system.customer.service.HouseInfoService;
import com.ruoyi.system.customer.service.RequestService;
import com.ruoyi.system.customer.vo.DeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AbilityImpl implements AbilityService {

    @Autowired
    private CmAbilityMapper cmAbilityMapper;
    @Autowired
    private RequestService requestService;



    @Override
    public int insert(CmAbility cmAbility) {
        cmAbility.setCreateBy(SecurityUtils.getUserId().toString());
        cmAbility.setUpdateBy(SecurityUtils.getUserId().toString());
        return cmAbilityMapper.insertAbility( cmAbility);
    }

    @Override
    public CmAbility getAbilityById(String id) {
        return cmAbilityMapper.getAbilityById(id);
    }

    @Override
    public int update(CmAbility cmAbility) {
        cmAbility.setUpdateBy(SecurityUtils.getUserId().toString());
        return cmAbilityMapper.update( cmAbility);
    }

    @Override
    public void save(CmAbilityExamine cmAbilityExamine) {
        cmAbilityExamine.setCreateBy(SecurityUtils.getUserId().toString());
        cmAbilityExamine.setUpdateBy(SecurityUtils.getUserId().toString());
        cmAbilityMapper.save(cmAbilityExamine);
    }

    @Override
    public List<CmAbility> centerAbility(Page<CmAbility> page) {
        String s = SecurityUtils.getUserId().toString();
        long currentpage=page.getCurrent();
        long size=page.getSize();
        return cmAbilityMapper.centerAbility(s, currentpage,size);
    }

    @Override
    public List<CmAbility> getAllAbility(CmAbility cmAbility) {
        return cmAbilityMapper.getAllAbility( cmAbility);
    }

    @Override
    public List<CmAbility> centerAbilitytotal() {
        String s = SecurityUtils.getUserId().toString();
        return cmAbilityMapper.centerAbilitytotal(s);
    }

    @Override
    public List<CmAbility> getAllAbilityForPage(CmAbility cmAbility, Page<CmAbility> page) {
        return cmAbilityMapper.getAllAbilityForPage(cmAbility,page.getCurrent(),page.getSize());
    }

    @Override
    public void updateStatusById(CmAbility cmAbility) {
        cmAbilityMapper.updateStatusById( cmAbility);
    }

    @Override
    public List<Map<String, Integer>> getShouYeShenpiCount() {
        return cmAbilityMapper.getShouYeShenpiCount();
    }

    @Override
    public  List<Map<String, String>> getAppfenleiCount() {
        return cmAbilityMapper.getAppfenleiCount();
    }

    @Override
    public List<Map<String, String>> getRuZhulaiyuanZhanBi() {
        return cmAbilityMapper.getRuZhulaiyuanZhanBi();
    }

    @Override
    public List<Map<String, String>> getRuZhuDeptZhanBi() throws Exception {
        List<Map<String, String>> deptIdList = cmAbilityMapper.getRuZhuDeptZhanBi();
        CommonRes commonRes = requestService.addApplicationDept();
        if (HttpStatus.SUCCESS == commonRes.getCode()) {
            List<DeptVo> allDeptList = JSON.parseArray(JSON.toJSONString(commonRes.getData()), DeptVo.class);
            Optional<DeptVo> optional = allDeptList.stream().filter(x -> "373".equals(x.getId())).findFirst();
            if (optional.isPresent()) {
                DeptVo deptVo = optional.get();
                List<DeptVo> childrenList = deptVo.getChildren();
                List<Map<String, String>> fitDeptList = deptIdList.stream().map(x -> {
                    String deptId = x.get("dept");
                    Optional<DeptVo> existOpt = childrenList.stream().filter(y -> deptId.equals(y.getId())).findFirst();
                    if (existOpt.isPresent()) {
                        DeptVo curDeptVo = existOpt.get();
                        x.put("name",curDeptVo.getTitle());
                        return x;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());
                fitDeptList.removeIf(Objects::isNull); //去除空元素
                return fitDeptList;
            }
        }
        return null;
    }

    @Override
    public CommonRes getUserTotal() {
        try {
            CommonRes commonRes = requestService.getUserTotal();
            return commonRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

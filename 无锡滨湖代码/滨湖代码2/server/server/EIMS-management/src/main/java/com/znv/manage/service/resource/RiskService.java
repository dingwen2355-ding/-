package com.znv.manage.service.resource;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Protection;
import com.znv.manage.common.bean.resource.Risk;

import java.util.Date;
import java.util.List;

public interface RiskService {
    PageInfo<Risk> queryRiskList(String industry, String enterpriseName, String rectificationProgress, String startTime, String endTime, Integer pageNum, Integer pageSize);

    Result deleteByPrimaryKey(String ids);

    Result insertSelective(Risk risk);

    Result updateByPrimaryKeySelective(Risk risk);

    Result insertSelectiveList(List<Risk> riskList);
}

package com.znv.manage.service.resource;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.BasicExpertInfo;
import com.znv.manage.common.bean.resource.Protection;

import java.util.List;

public interface ProtectionService {
    PageInfo<Protection> queryProtectionList(String protectionName, String protectionType, Integer pageNum, Integer pageSize);

    Result deleteByPrimaryKey(String ids);

    Result insertSelective(Protection protection);

    Result updateByPrimaryKeySelective(Protection protection);


    Result insertSelectiveList(List<Protection> protectionList);
}

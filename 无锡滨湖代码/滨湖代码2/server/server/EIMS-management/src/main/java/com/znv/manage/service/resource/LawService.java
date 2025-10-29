package com.znv.manage.service.resource;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Law;

public interface LawService {
    PageInfo<Law> queryLawList(String lawName, String accidentType,Integer pageNum, Integer pageSize);

    Result deleteLaw(String ids);

    Result insertSelective(Law law);

    Result updateByPrimaryKeySelective(Law law);
}

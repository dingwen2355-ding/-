package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;

/**
 * @author znv
 * @ClassName: ManageService
 * @Description:
 * @date 2018/5/18 14:56
 */
public interface CompanyDictService {

    Result queryRegion();

    Result queryCompanyType();

    Result queryStandLevel();

    Result queryCompanyState();

    Result queryIndustry();

}

package com.znv.manage.service.resource;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.bean.StockInfo;
import com.znv.manage.common.bean.Result;

/**
 * @author znv
 * @ClassName: ManageService
 * @Description:
 * @date 2018/5/18 14:56
 */
public interface CommonMenuService {

    Result getRegion();

    Result getBelongUnit();

    Result getBeManagedType();

    Result getStorageLevel();

    Result getStorageType();

    Result getIndustry();

    Result getHelpType();

    String queryStockByRegion(String region);
}

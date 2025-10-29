package com.znv.manage.service;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.SyncCar;

/**
 * TDataSyncCar表服务接口
 *
 * @author 0049003788
 * @since 2023-01-10 16:11:04
 */
public interface SyncCarService {

    Result insert(SyncCar syncCar);

    Result update(SyncCar syncCar);

    Result deleteById(String license);

    Result deleteBatch(String ids);

    Result queryByCondition(String license, String dept, String online, String type, String districtCode, Integer page, Integer pageSize);

    void sync();

    void syncFireEngine();
}

package com.znv.manage.service;

import com.znv.manage.common.bean.CityFirmware;
import com.znv.manage.common.bean.Result;

/**
 * TCityFirmware表服务接口
 *
 * @author 0049003788
 * @since 2023-03-01 14:45:11
 */
public interface CityFirmwareService {

    Result insert(CityFirmware cityFirmware);

    Result update(CityFirmware cityFirmware);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String name, String parentType, String type, Integer page, Integer pageSize);
}

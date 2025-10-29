package com.znv.manage.service;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ThirdPlatformEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author SunJiaHao
 * @date 2022-08-16
 * @Description 第三方系统平台相关业务
 */
public interface ThirdPlatformService {

    /**
     * 新增
     * @param entity ThirdPlatformEntity
     * @return Result
     */
    Result addThirdPlatform(ThirdPlatformEntity entity);

    /**
     * 删除
     * @param ids 主键id
     * @return Result
     */
    Result deleteThirdPlatform(String ids);

    /**
     * 更新
     * @param entity ThirdPlatformEntity
     * @return Result
     */
    Result updateThirdPlatformById(ThirdPlatformEntity entity);

    /**
     * 查询
     * @param paramMap 检索参数
     * @param pageNum 页码
     * @param pageSize 页容量
     * @return Result
     */
    Result queryThirdPlatformList(Map<String, Object> paramMap, Integer pageNum, Integer pageSize);

}

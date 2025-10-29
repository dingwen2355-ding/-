package com.znv.manage.dao;

import com.znv.manage.common.bean.ThirdPlatformEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author SunJiaHao
 * @date 2022-08-16
 * @Description 第三方系统平台 Dao
 */
@Repository
public interface ThirdPlatformDao {

    /**
     * 新增
     * @param entity ThirdPlatformEntity
     */
    void add(ThirdPlatformEntity entity);

    /**
     * 删除
     * @param id 主键id
     */
    void deleteById(@Param("id") Long id);

    /**
     * 更新
     * @param entity ThirdPlatformEntity
     */
    void updateById(ThirdPlatformEntity entity);

    /**
     * 查询
     * @param paramMap 检索参数
     * @return List<ThirdPlatformEntity>
     */
    List<ThirdPlatformEntity> queryList(Map<String, Object> paramMap);

}

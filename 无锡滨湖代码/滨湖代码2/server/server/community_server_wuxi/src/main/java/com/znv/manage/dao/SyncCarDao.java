package com.znv.manage.dao;

import com.znv.manage.common.bean.SyncCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TDataSyncCar表数据库访问层
 *
 * @author 0049003788
 * @since 2023-01-10 16:11:04
 */
public interface SyncCarDao {

    /**
     * 通过ID查询单条数据
     *
     * @param license 主键
     * @return 实例对象
     */
    SyncCar queryById(String license);

    /**
     * 统计总行数
     *
     * @param syncCar 查询条件
     * @return 总行数
     */
    long count(SyncCar syncCar);

    /**
     * 新增数据
     *
     * @param syncCar 实例对象
     * @return 影响行数
     */
    int insert(SyncCar syncCar);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SyncCar> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SyncCar> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SyncCar> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SyncCar> entities);

    /**
     * 修改数据
     *
     * @param syncCar 实例对象
     * @return 影响行数
     */
    int update(SyncCar syncCar);

    /**
     * 通过主键删除数据
     *
     * @param license 主键
     * @return 影响行数
     */
    int deleteById(String license);

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteBatch(@Param("ids") String ids);

    List<SyncCar> queryByCondition(@Param("license") String license,
                                   @Param("dept") String dept,
                                   @Param("online") String online,
                                   @Param("type") String type,
                                   @Param("districtCode") String districtCode);

}


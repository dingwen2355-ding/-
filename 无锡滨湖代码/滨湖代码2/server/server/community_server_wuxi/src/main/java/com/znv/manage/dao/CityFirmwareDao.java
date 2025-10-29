package com.znv.manage.dao;

import com.znv.manage.common.bean.CityFirmware;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCityFirmware表数据库访问层
 *
 * @author 0049003788
 * @since 2023-03-01 14:45:11
 */
public interface CityFirmwareDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CityFirmware queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param cityFirmware 查询条件
     * @return 总行数
     */
    long count(CityFirmware cityFirmware);

    /**
     * 新增数据
     *
     * @param cityFirmware 实例对象
     * @return 影响行数
     */
    int insert(CityFirmware cityFirmware);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CityFirmware> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CityFirmware> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CityFirmware> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CityFirmware> entities);

    /**
     * 修改数据
     *
     * @param cityFirmware 实例对象
     * @return 影响行数
     */
    int update(CityFirmware cityFirmware);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteBatch(@Param("ids") String ids);

    /**
     * 条件查询
     *
     * @param id         主键
     * @param name       固件名称
     * @param parentType 大类
     * @param type       小类
     * @return 实例列表
     */
    List<CityFirmware> queryByCondition(@Param("id") Integer id,
                                        @Param("name") String name,
                                        @Param("parentType") String parentType,
                                        @Param("type") String type);

}


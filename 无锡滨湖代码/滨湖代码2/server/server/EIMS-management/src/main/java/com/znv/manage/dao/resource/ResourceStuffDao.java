package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.ResourceStuff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgResourceStuff表数据库访问层
 *
 * @author 0049003788
 * @since 2023-05-19 15:13:41
 */
public interface ResourceStuffDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ResourceStuff queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param resourceStuff 查询条件
     * @return 总行数
     */
    long count(ResourceStuff resourceStuff);

    /**
     * 新增数据
     *
     * @param resourceStuff 实例对象
     * @return 影响行数
     */
    int insert(ResourceStuff resourceStuff);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ResourceStuff> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ResourceStuff> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ResourceStuff> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ResourceStuff> entities);

    /**
     * 修改数据
     *
     * @param resourceStuff 实例对象
     * @return 影响行数
     */
    int update(ResourceStuff resourceStuff);

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
     * @param id           主键
     * @param stuffName    物资名称
     * @param stuffType    物资类型
     * @param helpType     救援类型
     * @param helpTypeName 救援类型名称
     * @param dutyPerson   负责人
     * @param dutyPhone    负责电话
     * @param region       区域id
     * @return 实例列表
     */
    List<ResourceStuff> queryByCondition(@Param("id") Integer id,
                                         @Param("stuffName") String stuffName,
                                         @Param("stuffType") String stuffType,
                                         @Param("helpType") String helpType,
                                         @Param("helpTypeName") String helpTypeName,
                                         @Param("dutyPerson") String dutyPerson,
                                         @Param("dutyPhone") String dutyPhone,
                                         @Param("region") String region,
                                         @Param("planId") String planId,
                                         @Param("eventId") String eventId);

    /**
     * 通过地址聚合
     *
     * @param address 地址
     * @return 实例列表
     */
    List<ResourceStuff> queryGroupByAddress(@Param("address") String address,
                                            @Param("eventId") String eventId,
                                            @Param("planId") String planId);

}


package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.ResourceOperation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgResourceOperation表数据库访问层
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:26
 */
public interface ResourceOperationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ResourceOperation queryById(Integer id);

    /**
     * 通过ID查询多条数据
     *
     * @param ids 主键
     * @return 实例对象
     */
    List<ResourceOperation> queryByIds(@Param("ids") String ids);

    /**
     * 统计总行数
     *
     * @param resourceOperation 查询条件
     * @return 总行数
     */
    long count(ResourceOperation resourceOperation);

    /**
     * 新增数据
     *
     * @param resourceOperation 实例对象
     * @return 影响行数
     */
    int insert(ResourceOperation resourceOperation);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ResourceOperation> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ResourceOperation> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ResourceOperation> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ResourceOperation> entities);

    /**
     * 修改数据
     *
     * @param resourceOperation 实例对象
     * @return 影响行数
     */
    int update(ResourceOperation resourceOperation);

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

    List<ResourceOperation> query(@Param("id") Integer id,
                                  @Param("stuffName") String stuffName,
                                  @Param("type") String type,
                                  @Param("info") String info);

}


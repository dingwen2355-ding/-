package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.AssessProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgAssessProject表数据库访问层
 *
 * @author 0049003788
 * @since 2022-12-09 13:39:56
 */
public interface AssessProjectDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AssessProject queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param assessProject 查询条件
     * @return 总行数
     */
    long count(AssessProject assessProject);

    /**
     * 新增数据
     *
     * @param assessProject 实例对象
     * @return 影响行数
     */
    int insert(AssessProject assessProject);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AssessProject> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AssessProject> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AssessProject> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AssessProject> entities);

    /**
     * 修改数据
     *
     * @param assessProject 实例对象
     * @return 影响行数
     */
    int update(AssessProject assessProject);

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

    List<AssessProject> queryByCondition(@Param("id") Integer id,
                                         @Param("projectName") String projectName);

}


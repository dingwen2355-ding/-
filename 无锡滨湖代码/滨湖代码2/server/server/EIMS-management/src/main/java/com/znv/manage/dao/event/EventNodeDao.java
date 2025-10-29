package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.EventNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgEventNode表数据库访问层
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:20
 */
public interface EventNodeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EventNode queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param eventNode 查询条件
     * @return 总行数
     */
    long count(EventNode eventNode);

    /**
     * 新增数据
     *
     * @param eventNode 实例对象
     * @return 影响行数
     */
    int insert(EventNode eventNode);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventNode> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EventNode> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventNode> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<EventNode> entities);

    /**
     * 修改数据
     *
     * @param eventNode 实例对象
     * @return 影响行数
     */
    int update(EventNode eventNode);

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

    List<EventNode> queryByCondition(@Param("id") Integer id,
                                     @Param("nodeName") String nodeName);

}


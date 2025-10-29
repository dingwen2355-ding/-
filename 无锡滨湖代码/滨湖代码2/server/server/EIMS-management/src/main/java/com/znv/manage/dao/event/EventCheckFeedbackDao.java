package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.EventCheckFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgEventCheckFeedback表数据库访问层
 *
 * @author 0049003788
 * @since 2023-05-18 15:18:58
 */
public interface EventCheckFeedbackDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EventCheckFeedback queryById(Integer id);

    /**
     * 通过核查下发id查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EventCheckFeedback queryByCheckId(Integer id);

    /**
     * 统计总行数
     *
     * @param eventCheckFeedback 查询条件
     * @return 总行数
     */
    long count(EventCheckFeedback eventCheckFeedback);

    /**
     * 新增数据
     *
     * @param eventCheckFeedback 实例对象
     * @return 影响行数
     */
    int insert(EventCheckFeedback eventCheckFeedback);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventCheckFeedback> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EventCheckFeedback> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventCheckFeedback> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<EventCheckFeedback> entities);

    /**
     * 修改数据
     *
     * @param eventCheckFeedback 实例对象
     * @return 影响行数
     */
    int update(EventCheckFeedback eventCheckFeedback);

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
     * @param checkId      核查表id
     * @return 实例列表
     */
    List<EventCheckFeedback> queryByCondition(@Param("id") Integer id,
                                              @Param("checkId") Integer checkId);

}


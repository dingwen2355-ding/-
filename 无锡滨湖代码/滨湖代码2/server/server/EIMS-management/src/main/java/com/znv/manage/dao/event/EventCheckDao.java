package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.EventCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgEventCheck表数据库访问层
 *
 * @author 0049003788
 * @since 2023-05-18 15:14:36
 */
public interface EventCheckDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EventCheck queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param eventCheck 查询条件
     * @return 总行数
     */
    long count(EventCheck eventCheck);

    /**
     * 新增数据
     *
     * @param eventCheck 实例对象
     * @return 影响行数
     */
    int insert(EventCheck eventCheck);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventCheck> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EventCheck> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventCheck> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<EventCheck> entities);

    /**
     * 修改数据
     *
     * @param eventCheck 实例对象
     * @return 影响行数
     */
    int update(EventCheck eventCheck);

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
     * @param id              主键
     * @param eventId         关联事件id
     * @param checkPersonName 核查人名称
     * @param checkUserId     核查人id(用户表)
     * @param requirement     要求
     * @return 实例列表
     */
    List<EventCheck> queryByCondition(@Param("id") Integer id,
                                      @Param("eventId") Integer eventId,
                                      @Param("checkPersonName") String checkPersonName,
                                      @Param("checkUserId") Integer checkUserId,
                                      @Param("requirement") String requirement);

}


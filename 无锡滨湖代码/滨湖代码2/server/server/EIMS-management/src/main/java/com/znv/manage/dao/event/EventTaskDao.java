package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.EventTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgEventTask表数据库访问层
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:53
 */
public interface EventTaskDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EventTask queryById(Integer id);


    /**
     * 新增数据
     *
     * @param eventTask 实例对象
     * @return 影响行数
     */
    int insert(EventTask eventTask);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventTask> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EventTask> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventTask> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<EventTask> entities);

    /**
     * 修改数据
     *
     * @param eventTask 实例对象
     * @return 影响行数
     */
    int update(EventTask eventTask);

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

    List<EventTask> queryByCondition(@Param("id") Integer id,
                                     @Param("taskTitle") String taskTitle,
                                     @Param("eventTitle") String eventTitle,
                                     @Param("contactId") Integer contactId,
                                     @Param("person") String person,
                                     @Param("personId") String personId,
                                     @Param("status") String status,
                                     @Param("eventId") String eventId,
                                     @Param("startTime") String startTime,
                                     @Param("endTime") String endTime);

    String selectPhoneByUserId(String userId);

}


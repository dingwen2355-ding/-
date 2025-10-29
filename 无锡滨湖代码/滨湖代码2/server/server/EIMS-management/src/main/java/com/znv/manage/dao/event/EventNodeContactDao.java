package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.EventNodeContact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TCfgEventNodeContact表数据库访问层
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:31
 */
public interface EventNodeContactDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EventNodeContact queryById(Integer id);

    /**
     * 统计总行数
     *
     * @param eventNodeContact 查询条件
     * @return 总行数
     */
    long count(EventNodeContact eventNodeContact);

    /**
     * 新增数据
     *
     * @param eventNodeContact 实例对象
     * @return 影响行数
     */
    int insert(EventNodeContact eventNodeContact);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventNodeContact> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EventNodeContact> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EventNodeContact> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<EventNodeContact> entities);

    /**
     * 修改数据
     *
     * @param eventNodeContact 实例对象
     * @return 影响行数
     */
    int update(EventNodeContact eventNodeContact);

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

    List<EventNodeContact> queryByCondition(@Param("id") Integer id,
                                            @Param("eventId") Integer eventId,
                                            @Param("nodeId") Integer nodeId,
                                            @Param("isHidden") String isHidden,
                                            @Param("order") Integer order);

}


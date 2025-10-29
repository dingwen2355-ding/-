package com.znv.manage.dao.event;

import com.znv.manage.common.bean.event.Event;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TCfgEvent表数据库访问层
 *
 * @author 0049003788
 * @since 2022-12-09 13:41:36
 */
@Component
public interface EventDao {

    /**
     * 新增数据
     *
     * @param event 实例对象
     * @return 影响行数
     */
    int insert(Event event);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Event> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Event> entities);

    /**
     * 修改数据
     *
     * @param event 实例对象
     * @return 影响行数
     */
    int update(Event event);

    int updateDevice(@Param("eventId") Integer eventId,
                     @Param("deviceIds") String deviceIds);

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

    List<Event> queryByCondition(@Param("id") Integer id,
                                 @Param("eventTitle") String eventTitle,
                                 @Param("typeId") Integer typeId,
                                 @Param("streetId") Integer streetId,
                                 @Param("status") String status,
                                 @Param("communityId") Integer communityId,
                                 @Param("sourceId") String sourceId,
                                 @Param("streetName") String streetName,
                                 @Param("communityName") String communityName,
                                 @Param("startTime") String startTime,
                                 @Param("endTime") String endTime,
                                 @Param("typeName") String typeName,
                                 @Param("sourceName") String sourceName,
                                 @Param("statusIds") String statusIds,
                                 @Param("userId") String userId);

    List<Event> queryByIds(@Param("ids") String ids);

    Map<String, String> queryGps(@Param("eventId") String eventId);

    List<Map<String, Object>> getContactByEventId(@Param("eventId") Integer eventId);

    List<Map<String, Object>> countSyncEventByType(@Param("startTime") String startTime,
                                                   @Param("endTime") String endTime);

    List<Map<String, Object>> countSyncEventBySource(@Param("startTime") String startTime,
                                                     @Param("endTime") String endTime);

    List<Map<String, Object>> querySyncEventList(@Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);


}


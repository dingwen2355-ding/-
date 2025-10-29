package com.znv.manage.dao;

import com.znv.manage.common.bean.AlarmInform;
import com.znv.manage.common.bean.EventInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
@Repository
public interface IAlarmInstallMapper {

    List<Map<String,Object>> selAlarmInstall(@Param("id")Integer id,@Param("name")String name);

    int updateAlarmInstall(@Param("id") Integer id,
                           @Param("threshold") String threshold,
                           @Param("rulesState") String rulesState,
                           @Param("user") String user);

    Map<String, Object> selCommunity(@Param("community")String community);

    /**
     * 查询组织信息
     * @param paramMap 检索条件
     * @return
     */
    List<Map<String, Object>> queryPrecinctList(Map<String, Object> paramMap);


    int insertAlarmInstall(@Param("lists")List<AlarmInform> list);

    List<EventInfo> selEventManageByEventId(@Param("eventIds")String eventIds);
}

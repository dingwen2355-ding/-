package com.znv.manage.dao.event;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface EventManageDao {
    List<Map<String,String>> queryDictFlowNode();

    List<Map<String,String>> queryDictOverDue();

    List<Map<String,String>> queryDictEventState();

    List<Map<String,String>> queryDictEventInfoSource();

    /**
     * 给事件更新 is_alarm 字段的值为已经产生告警类型的值，可选值(1:超期告警、2:催办告警、3:即将超期)
     * @param announcementType 告警的类型的值
     * @param overDueState 超期状态值
     * @param eventIdList 事件ID列表
     */
    void updateEventIsAlarm(@Param("announcementType") String announcementType,
                            @Param("overDueState") String overDueState,
                            @Param("eventIdList") List<String> eventIdList);

    List<Map<String,String>> queryDictEventReportSource(@Param("id")String id);
}

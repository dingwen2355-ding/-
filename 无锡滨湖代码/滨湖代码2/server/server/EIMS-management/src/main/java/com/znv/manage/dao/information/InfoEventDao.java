package com.znv.manage.dao.information;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface InfoEventDao {
    List<Map<String, String>> queryEventType();

    List<Map<String, String>> queryEventList(@Param("eventType") String eventType,
                                             @Param("eventType2") String eventType2,
                                             @Param("text") String text, @Param("eventId") String eventId,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime,
                                             @Param("reportPeople") String reportPeople);

    List<Map<String, String>> queryEventInstructions(@Param("eventId") String eventId);

    List<Map<String, String>> queryEventProgress(@Param("eventId") String eventId);

    List<Map<String, String>> queryDealResults(@Param("eventId") String eventId);

    List<Map<String, String>> queryJudges(@Param("eventId") String eventId);

    List<Map<String, String>> queryPlans(@Param("eventId") String eventId);

    List<Map<String, String>> queryFiles(@Param("eventId") String eventId);
}

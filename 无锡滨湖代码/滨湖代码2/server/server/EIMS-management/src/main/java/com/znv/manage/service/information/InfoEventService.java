package com.znv.manage.service.information;

import java.util.List;
import java.util.Map;

public interface InfoEventService {
    Map<String, List<String>> queryEventType();

    List<Map<String,String>> queryEventList(String eventType,String eventType2,String text,
                                            String startTime, String endTime, String reportPeople);

    Map<String,Object> queryEventInfo(String eventId);
}

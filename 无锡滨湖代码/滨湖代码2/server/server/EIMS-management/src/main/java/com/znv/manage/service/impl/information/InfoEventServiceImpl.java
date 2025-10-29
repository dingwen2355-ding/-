package com.znv.manage.service.impl.information;

import com.znv.manage.dao.information.InfoEventDao;
import com.znv.manage.service.information.InfoEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InfoEventServiceImpl implements InfoEventService {
    @Autowired
    private InfoEventDao infoEventDao;

    @Override
    public Map<String, List<String>> queryEventType() {
        List<Map<String, String>> list = infoEventDao.queryEventType();
        Map<String, List<String>> result = new HashMap<>();
        for (Map<String, String> map : list) {
            String emergency = map.get("emergency");
            String helpType = map.get("helpType");
            if (result.containsKey(emergency)) {
                List<String> list1 = result.get(emergency);
                list1.add(helpType);
            } else {
                List<String> objects = new ArrayList<>();
                objects.add(helpType);
                result.put(emergency, objects);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, String>> queryEventList(String eventType, String eventType2, String text,
                                                    String startTime, String endTime, String reportPeople) {
        return infoEventDao.queryEventList(eventType, eventType2, text, null, startTime, endTime, reportPeople);
    }

    @Override
    public Map<String, Object> queryEventInfo(String eventId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> list = infoEventDao.queryEventList(null, null, null, eventId, null, null, null);
        for (Map<String, String> map : list) {
            String attachment = map.get("attachment");
            int i = attachment.lastIndexOf("/");
            String substring = attachment.substring(i + 1);
            map.put("attachmentFile", substring);
        }
        result.put("info", list.get(0));
        List<Map<String, String>> list1 = infoEventDao.queryEventInstructions(eventId);
        for (Map<String, String> map : list1) {
            String attachment = map.get("attachment");
            int i = attachment.lastIndexOf("/");
            String substring = attachment.substring(i + 1);
            map.put("attachmentFile", substring);
        }
        result.put("instructions", list1);
        List<Map<String, String>> list2 = infoEventDao.queryEventProgress(eventId);
        for (Map<String, String> map : list2) {
            String attachment = map.get("attachment");
            int i = attachment.lastIndexOf("/");
            String substring = attachment.substring(i + 1);
            map.put("attachmentFile", substring);
        }
        result.put("progress", list2);

        List<Map<String, String>> dealResults = infoEventDao.queryDealResults(eventId);
        result.put("dealResults", dealResults);

        List<Map<String, String>> judges = infoEventDao.queryJudges(eventId);
        result.put("judges", judges);

        List<Map<String, String>> plans = infoEventDao.queryPlans(eventId);
        result.put("plans", plans);

        List<Map<String, String>> files = infoEventDao.queryFiles(eventId);
        result.put("files", files);
        return result;
    }
}

package com.znv.manage.service.impl.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventTagDictEntity;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.HolidayDao;
import com.znv.manage.dao.event.EventManageDao;
import com.znv.manage.dao.event.EventTagDictDao;
import com.znv.manage.service.event.EventManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventManageServiceImpl implements EventManageService {

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private EventManageDao eventManageDao;

    @Autowired
    private EventTagDictDao eventTagDictDao;


    @Override
    public Result queryAllHoliday(String type, String year) {
        List<String> dateList = holidayDao.queryAllHolidayByYear(type, year);
        if (!CollectionUtils.isEmpty(dateList)) {
            dateList = dateList.stream().map(item -> item.replace('-', '/')).collect(Collectors.toList());
        }
        Result result = new Result();
        result.setData(dateList);
        return result;
    }

    @Override
    public List<Map<String, String>> queryDictEventInfoSource() {
        return eventManageDao.queryDictEventInfoSource();
    }

    @Override
    public List<Map<String, String>> queryDictEventState() {
        return eventManageDao.queryDictEventState();
    }

    @Override
    public Result getEventTagTree() {
        Result result = new Result();
        try {
            List<EventTagDictEntity> tagList = eventTagDictDao.queryEventTagDict(null, null, null, null);
            List<EventTagDictEntity> levelOneList = tagList.stream()
                    .filter(item -> (!ObjectUtils.isEmpty(item.getTagLevel()) && (1 == item.getTagLevel())))
                    .collect(Collectors.toList());
            List<Map<String, Object>> treeList = new ArrayList<>(levelOneList.size());
            for (EventTagDictEntity tag : levelOneList) {
                Map<String, Object> tagInfoMap = new HashMap<>();
                tagInfoMap.put("nodeId", tag.getTagId());
                tagInfoMap.put("nodeName", tag.getTagName());
                tagInfoMap.put("nodeUpNodeId", tag.getPTagId());
                tagInfoMap.put("nodeTreeLevel", tag.getTagLevel());
                addChildrenInfo(tagInfoMap, tag, tagList);
                treeList.add(tagInfoMap);
            }
            result.setData(treeList);
        } catch (Exception e) {
            log.error("Exception: ", e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public List<Map<String, String>> queryDictFlowNode() {
        return eventManageDao.queryDictFlowNode();
    }

    @Override
    public List<Map<String, String>> queryDictOverDue() {
        return eventManageDao.queryDictOverDue();
    }

    @Override
    public List<Map<String, String>> queryDictEventReportSource() {
        return eventManageDao.queryDictEventReportSource(null);
    }

    private void addChildrenInfo(Map<String, Object> categoryInfoMap, EventTagDictEntity tag, List<EventTagDictEntity> tagList) {
        if (CollectionUtils.isEmpty(categoryInfoMap) || ObjectUtils.isEmpty(tag) || CollectionUtils.isEmpty(tagList)) {
            return;
        }
        String tagId = tag.getTagId();
        List<EventTagDictEntity> childrenList = tagList.stream()
                .filter(item -> tagId.equals(item.getPTagId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childrenList)) {
            return;
        }

        List<Map<String, Object>> childrenMapList = new ArrayList<>(childrenList.size());
        for (EventTagDictEntity child : childrenList) {
            Map<String, Object> childMap = new HashMap<>();
            childMap.put("nodeId", child.getTagId());
            childMap.put("nodeName", child.getTagName());
            childMap.put("nodeUpNodeId", child.getPTagId());
            childMap.put("nodeTreeLevel", child.getTagLevel());
            addChildrenInfo(childMap, child, tagList);
            childrenMapList.add(childMap);
        }
        categoryInfoMap.put("childrenList", childrenMapList);
    }
}

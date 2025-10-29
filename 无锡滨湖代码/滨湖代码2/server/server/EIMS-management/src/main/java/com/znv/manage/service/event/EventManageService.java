package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;

import java.util.List;
import java.util.Map;

public interface EventManageService {

    /**
     * 查询节假日
     *
     * @param type 0 : 法定节假日，1 调休日
     * @param year 查询的年分
     * @return
     */
    Result queryAllHoliday(String type, String year);

    List<Map<String, String>> queryDictEventInfoSource();

    List<Map<String, String>> queryDictEventState();

    /**
     * 查询事件标签树
     *
     * @return
     */
    Result getEventTagTree();

    List<Map<String, String>> queryDictOverDue();

    List<Map<String, String>> queryDictFlowNode();

    List<Map<String, String>> queryDictEventReportSource();

}

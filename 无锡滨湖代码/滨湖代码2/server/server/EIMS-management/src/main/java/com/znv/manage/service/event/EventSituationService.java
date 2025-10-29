package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;

import javax.servlet.http.HttpServletResponse;

/**
 * TCfgResourceList表服务接口
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:14
 */
public interface EventSituationService {

    Result quartAnalysis();

    Result eventLevelCount(String beginTime,String endTime);

    Result eventStreetCount(String beginTime,String endTime);

    Result burstEventTotalCount(String beginTime,String endTime,String type);

    Result burstEventTypeCount(String beginTime,String endTime,String type);

    Result burstEventTypeCountAll(String beginTime,String endTime);

    Result burstEventParentType(String beginTime,String endTime);
}

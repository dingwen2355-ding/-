package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

public interface PublicOpinionService {

//    Result getRealPublicOpinionDataByEventId(String eventId);
//
//    Result getPublicOpinionByKeyWord(String keyWords, String eventId);
//
//    Result boundPublicOPinionWithEvent(String eventId, String publicOpinionIds, String type);
//
//    void updateEventOpinionLevel();
//
//    void updateOpinionReportName();
//
//    void savePublicOpinion();

    String setToken();

    Result searchAll(String eventId,String keywords,String limitNum);

    Result opinionStatistics(String eventId,String keywords);

    Result mediaTypeStatistics(String eventId,String keywords);

    Result attitudeStatistics(String eventId,String keywords);
}

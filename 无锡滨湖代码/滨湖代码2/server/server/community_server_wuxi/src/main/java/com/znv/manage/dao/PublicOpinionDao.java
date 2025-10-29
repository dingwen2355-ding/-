package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PublicOpinionDao {

    List<Map<String, Object>> getReceivedPublicOpinionData(@Param("eventId") String eventId,
                                                           @Param("publicOpinionId") String publicOpinionId);

    List<Map<String, Object>> getReceivedPublicOpinionReport(@Param("eventId") String eventId);

    List<Map<String, Object>> getSocialReportList(@Param("eventId") String eventId,
                                                  @Param("status") String status);

    List<Map<String, Object>> getPublicOpinionDataByKeyWord(@Param("keyWords") String keyWords, @Param("eventId") String eventId);

    void updatePublicOpinionRelation(@Param("eventId") String eventId, @Param("opinionId") String opinionId);

    List<Map<String, Object>> getPublicOpinionInfo(@Param("publicOpinionId") String publicOpinionId);


    List<Map<String, Object>> getPublicOpinionReportInfo(@Param("publicOpinionId") String publicOpinionId,
                                                         @Param("mediaName") String mediaName,
                                                         @Param("reportTime") String reportTime,
                                                         @Param("reportTitle") String reportTitle,
                                                         @Param("reportAbstract") String reportAbstract,
                                                         @Param("reportUrl") String reportUrl,
                                                         @Param("type") String type,
                                                         @Param("mediaType") String mediaType,
                                                         @Param("id") String id
    );

    int insertPublicOpinionInfo(Map<String, Object> map);

    void insertPublicOpinionReportInfo(Map<String, Object> map);

    int insertPublicOpinionInfos(@Param("maps") List<Map<String, String>> maps);

    void insertPublicOpinionReportInfos(@Param("maps") List<Map<String, String>> maps);

    List<Map<String,String>> queryPublicOpinionInfo();

    List<Map<String,String>> queryPublicOpinionInfoAll();

    List<Map<String, Object>> getEventListWithoutOpinionLevel();

    List<Map<String, Object>> queryTopNOpinions(@Param("eventId") String eventId,
                                                @Param("topN") String topN,
                                                @Param("beginTime") String beginTime,
                                                @Param("endTime") String endTime);

    List<String> queryUrlWithoutName();

    void insertPublicOpinionReportDomainInfo(@Param("list") List<Map<String, String>> list);

    List<Map<String, String>> queryPublicOpinionWithOutDomainUrl();

    List<Map<String, Object>> queryPublicOpinionReportWithoutName();

    List<Map<String, Object>> queryPublicOpinionWithOtherUrl(@Param("noNameReportIds") String noNameReportIds);

    void updateReportDomainName(@Param("list") List<Map<String, Object>> list);

    List<Map<String,Object>> getPublicOpinionList();

    List<Map<String,String>> getEventInfoTitle();

    int updatePublicOpinionEventId(List<Map<String,Object>> list);

}

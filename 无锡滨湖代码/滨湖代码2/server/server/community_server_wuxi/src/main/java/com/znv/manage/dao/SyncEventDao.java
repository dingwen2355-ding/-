package com.znv.manage.dao;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.SyncEvent;
import com.znv.manage.common.bean.SyncEventContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: xx描述xx
 * @date
 */
@Repository
public interface SyncEventDao {

    int insertEventSync(@Param("list") JSONArray resultList);

    int updateEventTypeId();

    //int insertEventDealSync(SyncEvent resultList);

    int insertEventDealContentSync(SyncEventContent syncEventContent);

    //int queryDealExist(SyncEvent syncEvent);

    int queryEventExist(SyncEvent syncEvent);

    int queryEventContentExist(SyncEventContent syncEventContent);

    int queryEventContentReExist(SyncEventContent syncEventContent);

    List<Map> queryEventTypeConf(@Param("typeId")String typeId);

    int insertEventLeffConf(@Param("eventId")String eventId,
                            @Param("list")List<Map> list);
}

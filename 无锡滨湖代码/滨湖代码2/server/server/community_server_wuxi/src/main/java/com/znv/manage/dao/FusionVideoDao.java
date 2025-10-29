package com.znv.manage.dao;

import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.EventInfo;
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
public interface FusionVideoDao {

    EventInfo getData(@Param("id") String id);

    List<EventInfo> getData2();

    List<DeviceInfo> getList();

    List<DeviceInfo> getList2(@Param("gpsx")String gpsx,
                              @Param("gpsy")String gpsy);

    int updateEventInfo(EventInfo eventInfo);

    int updateEventDeviceId(@Param("list")List<EventInfo> list);

    DeviceInfo getDeviceById(@Param("id") String id,@Param("precinctId")String precinctId);

    List<DeviceInfo> query33DeviceList(@Param("precinctId")String precinctId);

    List<DeviceInfo> query35DeviceList(@Param("precinctId")String precinctId,@Param("deviceKind")String deviceKind);

    void updateFusionMax(@Param("eventId")String eventId,@Param("type")String type,@Param("deviceIds")String deviceIds);

    List<Map<String, Object>> queryCameraInfoByType(@Param("deviceKind") String deviceKind,
                                                    @Param("name") String name,
                                                    @Param("deviceId") String deviceId,
                                                    @Param("precinctId")String precinctId,
                                                    @Param("devtype")String devtype,
                                                    @Param("gpsx")String gpsx,
                                                    @Param("gpsy")String gpsy,
                                                    @Param("pageStart")Integer pageStart,
                                                    @Param("pageLen")Integer pageLen);

    int queryCameraInfoByTypeCount(@Param("deviceKind") String deviceKind,
                                      @Param("name") String name,
                                      @Param("deviceId") String deviceId,
                                      @Param("precinctId")String precinctId,
                                      @Param("devtype")String devtype);

    List<Map<String, Object>> fusionMax(@Param("eventId") String eventId);

    Map<String,Object> fusionMaxCfg(@Param("eventId")String eventId);

    List<Map<String,Object>> queryDeviceKindList();

    List<Map<String,Object>> queryFixedCameraList(@Param("precinctName")String precinctName);

    int updateEventDeviceIds(@Param("eventId")String eventId,@Param("deviceIds")String deviceIds);

}

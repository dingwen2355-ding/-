package com.ruoyi.system.customer.mapper;


import com.ruoyi.system.customer.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface HouseInfoMapper  {

    List<RoomBaseInfo> getRoomBaseInfoByBuindingCode(String gmlId);

    List<Map<String,String>> getresidentInfoByRoomCode(String roomCode);


    List<Map<String, String>> getModuleCouts();

    List<CmApp> getAllAppList(CmAppParam param);

    List<CmApp> getAllAppList2(CmAppParam param);

    List<CmApp> getAllAppListForAdmin(CmAppParam param);

    List<CmApp> getTuiJian();

    List<CmApp> getLastApp();

    void updateStatusById(CmAbility cmAbility);

    CmApp getCmAppDetail(String id);
}

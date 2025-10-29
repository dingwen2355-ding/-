package com.ruoyi.system.customer.service;


import com.ruoyi.system.customer.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 房屋业务层
 *
 * @author ruoyi
 */
public interface HouseInfoService
{
    List<RoomBaseInfo> getRoomBaseInfoByBuindingCode(String gmlId);

    List<Map<String,String>> getresidentInfoByRoomCode(String roomCode);

    List<Map<String, String>> getModuleCouts();

    List<CmApp> getAllAppList(CmAppParam param);
    List<CmApp> getAllAppListForAdmin(CmAppParam param);
    List<CmApp> getAllAppList2(CmAppParam param);

    List<CmApp> getTuiJian();

    List<CmApp> getLastApp();

    void updateStatusById(CmAbility cmAbility);

    CmApp getCmAppDetail(String id);
}

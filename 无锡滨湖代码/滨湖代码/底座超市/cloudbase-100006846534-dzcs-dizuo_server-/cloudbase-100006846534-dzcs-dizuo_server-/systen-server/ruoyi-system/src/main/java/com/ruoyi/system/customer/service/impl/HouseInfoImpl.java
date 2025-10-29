package com.ruoyi.system.customer.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.customer.domain.*;
import com.ruoyi.system.customer.mapper.HouseInfoMapper;
import com.ruoyi.system.customer.service.HouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseInfoImpl implements HouseInfoService {

    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<RoomBaseInfo> getRoomBaseInfoByBuindingCode(String gmlId) {
        return houseInfoMapper.getRoomBaseInfoByBuindingCode(gmlId) ;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<Map<String,String>> getresidentInfoByRoomCode(String roomCode) {
        return houseInfoMapper.getresidentInfoByRoomCode( roomCode);
    }

    @Override
    public List<Map<String, String>> getModuleCouts() {
        return houseInfoMapper.getModuleCouts();
    }

    @Override
    public List<CmApp> getAllAppList(CmAppParam param) {
        return houseInfoMapper.getAllAppList(param);
    }
    @Override
    public List<CmApp> getAllAppListForAdmin(CmAppParam param) {
        return houseInfoMapper.getAllAppListForAdmin(param);
    }
    @Override
    public List<CmApp> getAllAppList2(CmAppParam param) {
        return houseInfoMapper.getAllAppList2(param);
    }

    @Override
    public List<CmApp> getTuiJian() {
        return houseInfoMapper.getTuiJian();
    }

    @Override
    public List<CmApp> getLastApp() {
        return houseInfoMapper.getLastApp();
    }

    @Override
    public void updateStatusById(CmAbility cmAbility) {
        houseInfoMapper.updateStatusById(cmAbility);
    }

    @Override
    public CmApp getCmAppDetail(String id) {
        return houseInfoMapper.getCmAppDetail(id);
    }
}

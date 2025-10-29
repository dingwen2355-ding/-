package com.znv.manage.service.impl;

import com.znv.manage.common.bean.HikCameraInfo;
import com.znv.manage.common.bean.Region;
import com.znv.manage.common.bean.RegionTree;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.HikRegionDao;
import com.znv.manage.service.HikRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class HikRegionServiceImpl implements HikRegionService {

    @Autowired
    private HikRegionDao hikRegionDao;


    @Override
    public Result queryRegionList(String regionIds) {
        Result result = new Result();
        try {
            List<Region> list = hikRegionDao.queryRegionList(regionIds);
            RegionTree regionTree = new RegionTree(list);
            list = regionTree.builTree();
            result.setData(list);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @Override
    public Result queryRegionListByUpId(String UpRegionId) {
        Result result = new Result();
        try {
            List<Region> list = hikRegionDao.queryRegionListByUpId(UpRegionId);
            result.setData(list);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @Override
    public Result queryStaticRegionListByUpId(String UpRegionId) {
        Result result = new Result();
        try {
            List<Region> list = hikRegionDao.queryStaticRegionListByUpId(UpRegionId);
            result.setData(list);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @Override
    public Result queryRegionAndDeviceByUpId(String UpRegionId) {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = hikRegionDao.queryRegionListByUpIdNew(UpRegionId);
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(region -> region.put("children", null));
            }
            list.addAll(hikRegionDao.queryDeviceByRegionId(UpRegionId));
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(region -> {
                    region.put("parentIndexCode", UpRegionId);
                });
            }
            result.setData(list);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @Override
    public Result queryStaticRegionAndDeviceByUpId(String UpRegionId) {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = hikRegionDao.queryStaticRegionListByUpIdNew(UpRegionId);
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(region -> region.put("children", null));
            }
            list.addAll(hikRegionDao.queryStaticDeviceByRegionId(UpRegionId));
            if (!CollectionUtils.isEmpty(list)) {
                list.forEach(region -> {
                    region.put("parentIndexCode", UpRegionId);
                });
            }
            result.setData(list);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @Override
    public Result queryRegionDownDevices(String regionId, String deviceName, String precinctId, String onofflineState, String gpsState, String regionName, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            List<String> regionList = null;
            if (StringUtils.isEmpty(regionId)) {
                regionId = null;
            }
            if (!StringUtils.isEmpty(regionId)) {
                String regions = hikRegionDao.queryRegionDown(regionId);
                regionList = Arrays.asList(regions.split(","));
            }
            Integer start = 0;
            start = pageSize * (pageNum - 1);
            List<Map<String, String>> list2 = hikRegionDao.queryRegionDownDevices2(regionList, deviceName, precinctId, onofflineState, gpsState, regionName, start, pageSize);
            Map<String, Object> map = new HashMap<>();
            map.put("count", hikRegionDao.queryRegionDownDevicesCount(regionList, deviceName, precinctId, onofflineState, gpsState, regionName));
            map.put("list", list2);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result queryStaticRegionDownDevices(String regionId, String deviceName, String precinctId, String onofflineState, String gpsState, String regionName, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            List<String> regionList = null;
            if (StringUtils.isEmpty(regionId)) {
                regionId = null;
            }
            if (!StringUtils.isEmpty(regionId)) {
                String regions = hikRegionDao.queryStaticRegionDown(regionId);
                regionList = Arrays.asList(regions.split(","));
            }
            Integer start = 0;
            start = pageSize * (pageNum - 1);
            List<Map<String, String>> list2 = hikRegionDao.queryStaticRegionDownDevices2(regionList, deviceName, precinctId, onofflineState, gpsState, regionName, start, pageSize);
            Map<String, Object> map = new HashMap<>();
            map.put("count", hikRegionDao.queryRegionDownDevicesCount(regionList, deviceName, precinctId, onofflineState, gpsState, regionName));
            map.put("list", list2);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(transactionManager = "baseTransactionManager", rollbackFor = Exception.class)
    public Result editCameraDevices(HikCameraInfo hikCameraInfo) {
        Result result = new Result();
        try {
            hikRegionDao.editCameraDevices(hikCameraInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
            result.setMessage(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}

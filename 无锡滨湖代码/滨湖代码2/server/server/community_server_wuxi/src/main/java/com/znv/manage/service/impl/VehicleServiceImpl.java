package com.znv.manage.service.impl;

import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.XFCDao;
import com.znv.manage.service.VehicleService;
import com.znv.manage.xycy.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private XFCDao xfcDao;

    @Override
    public List<Map> queryVehicleGpsList(String vehicleNo,String dateStr) {
        return vehicleDao.queryVehicleGpsList(vehicleNo,dateStr);
    }

    @Override
    public List<Map> queryVehicleGpsListByVehicleNo(String vehicleNo,String dateStr) {
        return vehicleDao.queryVehicleGpsListByVehicleNo(vehicleNo,dateStr);
    }

    @Override
    public List<Map<String, Object>> queryBusList(String number,String hours) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isEmpty(hours)){
            list = vehicleDao.queryBusList(number,null);
        }else {
            Date date = new Date();
            long time = (date.getTime() / 1000) - Integer.parseInt(hours) * 60 * 60;
            list = vehicleDao.queryBusList(number,String.valueOf(time * 1000));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryTaxiList(String number,String hours) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isEmpty(hours)){
            list = vehicleDao.queryTaxiList(number,null);
        }else {
            Date date = new Date();
            long time = (date.getTime() / 1000) - Integer.parseInt(hours) * 60 * 60;
            list = vehicleDao.queryTaxiList(number,String.valueOf(time * 1000));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryTruckList(String number,String hours) {
        List<Map<String,Object>> list;
        if(StringUtils.isEmpty(hours)){
            list = vehicleDao.queryTruckList(number,null);
        }else {
            Date date = new Date();
            long time = (date.getTime() / 1000) - Integer.parseInt(hours) * 60 * 60;
            list = vehicleDao.queryTruckList(number,String.valueOf(time * 1000));
        }
        return list;
    }

    @Override
    public List<Map<String, String>> queryXFCList(String id) {
        return xfcDao.queryXFCList(id);
    }
}

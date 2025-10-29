package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.utils.GpsUtils;
import com.znv.manage.dao.SyncMaterialDao;
import com.znv.manage.dao.XyxcUserDao;
import com.znv.manage.service.MaterialService;
import com.znv.manage.service.UserGpsService;
import com.znv.manage.ylwz.dao.MaterialDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MaterialServiceImpl implements MaterialService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Autowired
    private MaterialDao materialDao;

    @Autowired
    SyncMaterialDao syncMaterialDao;

    @Override
    public void syncMaterial(){
        //判断库内是否已存在之前的数据
        int count = syncMaterialDao.getDataCount();
        List<Map<String,Object>> list = new ArrayList<>();
        if(count>0){
            //只获取当天数据
             list = materialDao.queryMaterialList();
        }else{
            //获取所有数据
             list = materialDao.queryAllMaterialList();
        }
        if(list.size()>0){
            syncMaterialDao.insertMaterialData(list);
        }
    }

    @Override
    public  List<Map<String, Object>> queryMaterialUnitList() {
       return syncMaterialDao.queryMaterialUnitList();
    }

    @Override
    public  List<Map<String, Object>> queryMaterialList(String unitId) {
        return syncMaterialDao.queryMaterialList(unitId);
    }

    @Override
    public void syncMaterialUnit(){
        List<Map<String,Object>> list = syncMaterialDao.queryMaterialUnitGroup();
        for(Map map : list){
            if(!StringUtils.isEmpty(map.get("report_unit_name").toString())){
                Map<String, String> s = GpsUtils.getGps("襄阳市",map.get("report_unit_name").toString());
                double[] d = GpsUtils.gcj02_To_Gps84(Double.valueOf(s.get("lat")), Double.valueOf(s.get("lon")));
                map.put("gpsx",String .format("%.6f", d[1]));
                map.put("gpsy",String .format("%.6f", d[0]));
            }
        }
        if(list.size()>0){
            syncMaterialDao.insertMaterialUnitGroup(list);
        }
    }

}

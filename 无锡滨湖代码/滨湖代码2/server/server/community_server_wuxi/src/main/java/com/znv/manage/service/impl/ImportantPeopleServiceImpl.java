package com.znv.manage.service.impl;

import com.znv.manage.common.bean.ImportantPeople;
import com.znv.manage.common.bean.ImportantPeopleGps;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.GpsUtils;
import com.znv.manage.dao.ImportantPeopleDao;
import com.znv.manage.service.ImportantPeopleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
@Slf4j
public class ImportantPeopleServiceImpl implements ImportantPeopleService {

    @Autowired
    private ImportantPeopleDao importantPeopleDao;

    @Override
    public List<ImportantPeople> queryImportantPeopleList(String id,String name) {
        List<ImportantPeople> list = importantPeopleDao.queryImportantPeopleList(id,name);
        if(!CollectionUtils.isEmpty(list)){
            for(ImportantPeople importantPeople:list){
                List<Map<String,String>> mapList = importantPeople.getGpsList();
                if(!CollectionUtils.isEmpty(mapList)){
                    List<Map<String,String>> gpsList = new ArrayList<>();
                    Map<String,String> startAddress = new HashMap<>();
                    startAddress.put("id",mapList.get(0).get("id")+"#"+mapList.get(0).get("startAddress"));
                    startAddress.put("address",mapList.get(0).get("startAddress"));
                    startAddress.put("gpsx",mapList.get(0).get("startGpsx"));
                    startAddress.put("gpsy",mapList.get(0).get("startGpsy"));
                    startAddress.put("time",
                            StringUtils.isEmpty(mapList.get(0).get("startTime")) ?
                                    mapList.get(0).get("endTime") : mapList.get(0).get("startTime"));
                    gpsList.add(startAddress);
                    Map<String,String> endAddress = new HashMap<>();
                    endAddress.put("id",mapList.get(0).get("id")+"#"+mapList.get(0).get("endAddress"));
                    endAddress.put("address",mapList.get(0).get("endAddress"));
                    endAddress.put("gpsx",mapList.get(0).get("endGpsx"));
                    endAddress.put("gpsy",mapList.get(0).get("endGpsy"));
                    endAddress.put("time",
                            !StringUtils.isEmpty(mapList.get(0).get("endTime")) ?
                                    mapList.get(0).get("endTime") : mapList.get(0).get("startTime"));
                    gpsList.add(endAddress);
                    if(mapList.size() > 1){
                        for(int i=1;i<mapList.size();i++){
                            if(!gpsList.get(gpsList.size()-1).get("gpsx").equals(mapList.get(i).get("startGpsx"))
                                    || !gpsList.get(gpsList.size()-1).get("gpsy").equals(mapList.get(i).get("startGpsy"))){
                            /*Map<String,String> start = new HashMap<>();
                            start.put("id",mapList.get(i).get("id")+"#"+mapList.get(i).get("startAddress"));
                            start.put("address",mapList.get(i).get("startAddress"));
                            start.put("gpsx",mapList.get(i).get("startGpsx"));
                            start.put("gpsy",mapList.get(i).get("startGpsy"));
                            start.put("time",mapList.get(i).get("startTime"));
                            gpsList.add(start);*/
                                Map<String,String> end = new HashMap<>();
                                end.put("id",mapList.get(i).get("id")+"#"+mapList.get(i).get("endAddress"));
                                end.put("address",mapList.get(i).get("endAddress"));
                                end.put("gpsx",mapList.get(i).get("endGpsx"));
                                end.put("gpsy",mapList.get(i).get("endGpsy"));
                                end.put("time",
                                        !StringUtils.isEmpty(mapList.get(i).get("endTime")) ?
                                                mapList.get(i).get("endTime") : mapList.get(i).get("startTime"));
                                gpsList.add(end);
                            }else {
                                Map<String,String> end = new HashMap<>();
                                end.put("id",mapList.get(i).get("id")+"#"+mapList.get(i).get("endAddress"));
                                end.put("address",mapList.get(i).get("endAddress"));
                                end.put("gpsx",mapList.get(i).get("endGpsx"));
                                end.put("gpsy",mapList.get(i).get("endGpsy"));
                                end.put("time",
                                        !StringUtils.isEmpty(mapList.get(i).get("endTime")) ?
                                                mapList.get(i).get("endTime") : mapList.get(i).get("startTime"));
                                gpsList.add(end);
                            }
                        }
                    }
                    importantPeople.setGpsList(gpsList);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryImportantRouteList(String names) {
        List<Map<String, Object>> list = new ArrayList<>();
        if(StringUtils.isEmpty(names)){
            return list;
        }
        list = importantPeopleDao.queryImportantRouteList(names);
        List<String> nameList = Arrays.asList(names.split(","));
        if(!CollectionUtils.isEmpty(list)){
            for(String name : nameList){
                boolean first = true;
                String LastID = "";
                String lastEndAddress = "";
                for(int i=list.size() -1 ;i>-1 ;i--){
                    List<Map<String,Object>> routeList = (ArrayList<Map<String,Object>>)list.get(i).get("routeList");
                    if(!CollectionUtils.isEmpty(routeList)){
                        for(int j = routeList.size()-1;j>-1;j--){
                            if(name.equals(routeList.get(j).get("name").toString())){
                                if(first){
                                    routeList.get(j).put("id",routeList.get(j).get("ID")+"#"+routeList.get(j).get("startAddress")+"-"+routeList.get(j).get("ID")+"#"+routeList.get(j).get("endAddress"));
                                    first = false;
                                    LastID = String.valueOf(routeList.get(j).get("ID"));
                                    lastEndAddress = String.valueOf(routeList.get(j).get("endAddress"));
                                }else {
                                    routeList.get(j).put("id",LastID+"#"+lastEndAddress+"-"+routeList.get(j).get("ID")+"#"+routeList.get(j).get("endAddress"));
                                    first = false;
                                    LastID = String.valueOf(routeList.get(j).get("ID"));
                                    lastEndAddress = String.valueOf(routeList.get(j).get("endAddress"));
                                }
                            }

                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public int updateRouteGps(String city) {
        int count = 0;
        List<ImportantPeopleGps> list = importantPeopleDao.queryImportantGpsList();
        if(!CollectionUtils.isEmpty(list)){
            for(ImportantPeopleGps importantPeopleGps:list){
                Map<String,String> startGps = GpsUtils.getGps(city,importantPeopleGps.getStartAddressDetail());
                importantPeopleGps.setStartGpsx(startGps.get("lon"));
                importantPeopleGps.setStartGpsy(startGps.get("lat"));
                Map<String,String> endGps = GpsUtils.getGps(city,importantPeopleGps.getEndAddressDetail());
                importantPeopleGps.setEndGpsx(endGps.get("lon"));
                importantPeopleGps.setEndGpsy(endGps.get("lat"));
            }
            count = importantPeopleDao.updateRouteGps(list);
        }
        return count;
    }

    @Override
    public List<ImportantPeopleGps> queryImportantGpsList2(String id, String name) {
        return importantPeopleDao.queryImportantGpsList2(id,name);
    }

    @Override
    public Result saveImportantPeopleInfo(ImportantPeople importantPeople) {
        Result result = new Result();
        try {
            if(!StringUtils.isEmpty(importantPeople.checkInfo())){
                result.setCode(500);
                result.setMessage(importantPeople.checkInfo());
                return result;
            }else {
                importantPeopleDao.saveImportantPeopleInfo(importantPeople);
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result deleteImportantPeopleByIds(String ids) {
        Result result = new Result();
        try {
            int count = importantPeopleDao.deleteImportantPeopleByIds(ids);
            result.setMessage("成功删除"+count+"条");
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result saveImportantRouteInfo(ImportantPeopleGps importantPeopleGps) {
        Result result = new Result();
        try {
            if(!StringUtils.isEmpty(importantPeopleGps.checkInfo())){
                result.setCode(500);
                result.setMessage(importantPeopleGps.checkInfo());
                return result;
            }else {
                importantPeopleDao.saveImportantRouteInfo(importantPeopleGps);
            }
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result deleteImportantRouteByIds(String ids) {
        Result result = new Result();
        try {
            int count = importantPeopleDao.deleteImportantRouteByIds(ids);
            result.setMessage("成功删除"+count+"条");
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }
}

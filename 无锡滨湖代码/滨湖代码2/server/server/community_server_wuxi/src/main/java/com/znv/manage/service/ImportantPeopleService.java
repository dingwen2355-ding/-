package com.znv.manage.service;

import com.znv.manage.common.bean.ImportantPeople;
import com.znv.manage.common.bean.ImportantPeopleGps;
import com.znv.manage.common.bean.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ImportantPeopleService {

    List<ImportantPeople> queryImportantPeopleList(@Param("id")String id, @Param("name")String name);

    List<Map<String,Object>> queryImportantRouteList(@Param("names")String names);

    int updateRouteGps(String city);

    List<ImportantPeopleGps> queryImportantGpsList2(@Param("id")String id,
                                                    @Param("name")String name);

    Result saveImportantPeopleInfo(ImportantPeople importantPeople);

    Result deleteImportantPeopleByIds(@Param("ids")String ids);

    Result saveImportantRouteInfo(ImportantPeopleGps importantPeopleGps);

    Result deleteImportantRouteByIds(@Param("ids")String ids);
}

package com.znv.manage.dao;

import com.znv.manage.common.bean.ImportantPeople;
import com.znv.manage.common.bean.ImportantPeopleGps;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ImportantPeopleDao {

    List<ImportantPeople> queryImportantPeopleList(@Param("id")String id,@Param("name")String name);

    List<Map<String,Object>> queryImportantRouteList(@Param("names")String names);

    List<ImportantPeopleGps> queryImportantGpsList();

    int updateRouteGps(@Param("list") List<ImportantPeopleGps> list);

    List<ImportantPeopleGps> queryImportantGpsList2(@Param("id")String id,
                                                    @Param("name")String name);

    int saveImportantPeopleInfo(ImportantPeople importantPeople);

    int deleteImportantPeopleByIds(@Param("ids")String ids);

    int saveImportantRouteInfo(ImportantPeopleGps importantPeopleGps);

    int deleteImportantRouteByIds(@Param("ids")String ids);
}

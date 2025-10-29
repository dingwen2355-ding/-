package com.znv.manage.dao;

import com.znv.manage.common.bean.ArrivedResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArrivedResourceMapper {

    List<Map<String,Object>> queryArrivedResourceListByEventId(@Param("eventId") String eventId);

    List<Map<String,Object>> queryUnitList(@Param("type") String type,@Param("precinctId")String precinctId);

    List<Map<String,Object>> queryResourceTypeList();

    List<Map<String,Object>> queryArrivedResourceList(
            @Param("updateId") String updateId,
            @Param("eventId") String eventId,
            @Param("unitId") String unitId,
            @Param("resourceId") String resourceId,
            @Param("precinctId")String precinctId);

    int insertArrivedResource(List<ArrivedResource> list);

    int updateArrivedResource(ArrivedResource arrivedResource);

    int deleteArrivedResourceById(@Param("id") String id);

    void deleteArrivedResourceByEventId(@Param("eventId") String eventId);

    void saveArrivedResource(@Param("maps") List<Map> maps);

    int addResourceType(@Param("name") String name, @Param("type") String type);

    List<String> queryResourceId(@Param("name") String name, @Param("type") String type);
}

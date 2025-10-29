package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShelterDao {
    List<Map<String, Object>> shelterCoordinatesList();

    List<Map<String, Object>> queryCoordinatesDetails(@Param("id") Integer id);
}

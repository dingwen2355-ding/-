package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmergencySuppliesDao {

    List<Map<String, Object>> emergencySuppliesCoordinatesList();

    List<Map<String, Object>> queryCoordinatesDetails(@Param("id") Integer id);

}

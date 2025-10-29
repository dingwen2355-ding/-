package com.znv.manage.dao;

import com.znv.manage.common.bean.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CityManageMapper {

    List<Map<String,String>> getCityManageCategory();

    List<Map<String,String>> getCityManageDetail(@Param("largeType") String largeType);

}

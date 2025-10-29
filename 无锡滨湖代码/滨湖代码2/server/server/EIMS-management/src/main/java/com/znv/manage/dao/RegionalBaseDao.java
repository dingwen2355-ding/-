package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: dailongli
 * @Date: 2018/8/22 10:25
 */
@Repository
public interface RegionalBaseDao {

    /**
     * 获取省份信息
     *
     * @return
     */
    List<Map<String, String>> getProvince();

    /**
     * @param province
     * @return
     */
    List<Map<String, String>> getCity(@Param("province") String province);

    /**
     * @param city
     * @return
     */
    List<Map<String, String>> getCounty(@Param("city") String city);

    /**
     * 获取areaCode对应的省市区中文名称
     *
     * @param areaCode
     * @return
     */
    String getRegionalName(@Param("areaCode") String areaCode);
}

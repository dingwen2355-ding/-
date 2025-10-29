package com.znv.manage.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: dailongli
 * @Date: 2018/8/22 10:27
 */
public interface RegionalBaseService {

    /**
     * 获取所有省/市编码
     *
     * @return
     */
    List<Map<String, String>> getProvince();

    /**
     * 根据省编码，获取所有市区编码
     *
     * @param province
     * @return
     */
    List<Map<String, String>> getCity(@Param("province") String province);

    /**
     * 根据市编码， 获取所有县编码
     *
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

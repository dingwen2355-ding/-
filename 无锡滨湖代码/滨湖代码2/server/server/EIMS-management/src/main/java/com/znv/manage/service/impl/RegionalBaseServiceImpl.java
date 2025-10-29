package com.znv.manage.service.impl;

import com.znv.manage.dao.RegionalBaseDao;
import com.znv.manage.service.RegionalBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: dailongli
 * @Date: 2018/8/22 10:27
 */
@Service
public class RegionalBaseServiceImpl implements RegionalBaseService {


    @Autowired
    RegionalBaseDao regionalBaseDao;

    /**
     *
     * @return
     */
    @Override
    public List<Map<String ,String>> getProvince()
    {
        return regionalBaseDao.getProvince();
    }

    /**
     *
     * @param province
     * @return
     */
    @Override
    public List<Map<String ,String>> getCity(String province)
    {
        return regionalBaseDao.getCity(province);
    }

    /**
     *
     * @param city
     * @return
     */
    @Override
    public List<Map<String ,String>> getCounty(String city)
    {
        return regionalBaseDao.getCounty(city);
    }

    /**
     * 获取areaCode对应的省市区中文名称
     * @param areaCode
     * @return
     */
    @Override
    public String getRegionalName(String areaCode)
    {
        return regionalBaseDao.getRegionalName(areaCode);
    }
}

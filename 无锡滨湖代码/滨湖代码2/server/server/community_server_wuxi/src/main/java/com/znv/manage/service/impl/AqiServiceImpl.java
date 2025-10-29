package com.znv.manage.service.impl;

import com.znv.manage.service.AqiService;
import com.znv.manage.xycy.dao.AqiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AqiServiceImpl implements AqiService {

    @Autowired
    private AqiDao aqiDao;

    @Override
    public List<Map> queryAqiInfo() {
        try {
            return aqiDao.queryAqiInfo();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

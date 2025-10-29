package com.znv.manage.service.impl;

import com.znv.manage.dao.SzzdDao;
import com.znv.manage.service.SzzdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SzzdServiceImpl implements SzzdService {

    @Autowired
    SzzdDao szzdDao;


    @Override
    public List<Map<String, Object>> selectSiteList(String precinctId, String precinctName) {
        return szzdDao.selectSiteList(precinctId,precinctName);
    }

}

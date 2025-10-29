package com.znv.manage.service.impl;

import com.znv.manage.dao.KqjczdDao;
import com.znv.manage.service.KqjczdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KqjczdServiceImpl implements KqjczdService {

    @Autowired
    KqjczdDao kqjczdDao;


    @Override
    public List<Map<String, Object>> selectSiteList(String precinctId, String precinctName) {
        return kqjczdDao.selectSiteList(precinctId,precinctName);
    }

}

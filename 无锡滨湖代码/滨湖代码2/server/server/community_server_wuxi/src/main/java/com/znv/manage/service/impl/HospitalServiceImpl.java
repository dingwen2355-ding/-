package com.znv.manage.service.impl;

import com.znv.manage.dao.HospitalDao;
import com.znv.manage.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalDao hospitalDao;

    @Override
    public List<Map<String, String>> queryDdHospitalList() {
        return hospitalDao.queryDdHospitalList();
    }

    @Override
    public List<Map<String, String>> query2HospitalList() {
        return hospitalDao.query2HospitalList();
    }

    @Override
    public List<Map<String, String>> queryIsolationList() {
        return hospitalDao.queryIsolationList();
    }
}

package com.znv.manage.dao;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface HospitalDao {

    List<Map<String,String>> queryDdHospitalList();

    List<Map<String,String>> query2HospitalList();

    List<Map<String,String>> queryIsolationList();
}

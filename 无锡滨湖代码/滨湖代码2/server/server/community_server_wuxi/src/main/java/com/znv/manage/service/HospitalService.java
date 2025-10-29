package com.znv.manage.service;

import java.util.List;
import java.util.Map;

public interface HospitalService {

    List<Map<String,String>> queryDdHospitalList();

    List<Map<String,String>> query2HospitalList();

    List<Map<String,String>> queryIsolationList();
}

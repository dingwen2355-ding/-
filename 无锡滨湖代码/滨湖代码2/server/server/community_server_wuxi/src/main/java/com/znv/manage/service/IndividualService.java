package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


public interface IndividualService {

    String token();

    String HLWtoken();


    JSONObject memberList();

    String verificationCode();

    JSONObject gps();

    JSONObject gpsZL();

    JSONObject HLWgps();

    JSONObject createGroup(String name,List<String> deviceIds);

    JSONObject HLWcreateGroup(String name,List<String> uids);

    JSONObject dissolveGroup(String gid);

    JSONObject HLWdissolveGroup(String gid);

    JSONObject checkGroup(String gid);

    JSONObject saveInfo(JSONArray message);

    JSONObject saveStatus(JSONArray array);

    void updateGps();
}

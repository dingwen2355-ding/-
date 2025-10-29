package com.znv.manage.service;

import java.util.List;
import java.util.Map;

public interface UserGpsService {

    List<Map<String,Object>> queryUserGpsList(String userId,String eventId);

    Map<String,Object> queryUserGpsListByUserId(String userId);

    String handleToken();

    void syncUserGps();
}

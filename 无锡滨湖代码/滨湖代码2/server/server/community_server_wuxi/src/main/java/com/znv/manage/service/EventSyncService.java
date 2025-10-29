package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

public interface EventSyncService {
    JSONObject assignEvent(JSONObject json, HttpServletRequest request);
}

package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;


/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */

public interface HikService {

    JSONObject getCameraJson();

    int updateRtsp(String apiUrl);

    Result getCamera(Integer pageNo,Integer pageSize);

    Result getRtsp(String indexCode);

    Result getHls(String indexCode);
}

package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.OperateLog;
import com.znv.manage.common.utils.HttprequestHelper;
import com.znv.manage.dao.OperateLogDao;
import com.znv.manage.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：Yang GuanRong
 * @date ：Created in 2019/11/15
 */
@Service
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Value("${auth.server.uri:localhost}")
    private String authServerUri;

    @Autowired
    OperateLogDao operateLogDao;
    @Override
    public int insertOperateLog(OperateLog operateLog) {

        return operateLogDao.insertOperateLog(operateLog);
    }

    @Override
    public List<Map<String,Object>> queryOperateLog(String userId,
                                                    String username,
                                                    String beginTime,
                                                    String endTime,
                                                    String operateModule,
                                                    String operateDetail){
//        String url = String.format("%s/verifyAuthority/user/queryRoleIdByUserId?userId=%s",this.authServerUri, userId);
//        log.info("调用查询用户信息的接口：{}",url);
//        JSONObject jsonObject = HttprequestHelper.httpRequest(url, "GET", "");
//        String roleId=null;
//        String userName=null;
//        if (jsonObject != null && "success".equals(jsonObject.getString("remark"))) {
//            log.info("查询用户信息: " + authServerUri + " 成功!");
//            JSONArray array =  jsonObject.getJSONArray("data");
//            Map map = (Map) array.get(0);
//            roleId = (String)map.get("roleId");
//            userName = (String)map.get("userName");
//        } else {
//            log.error("查询用户信息: " + authServerUri + " 失败!" + jsonObject);
//        }
//
//        List<String> userNameList = null;
//        if ("2".equals(roleId)) {
//            String sameStreetUserUrl = String.format("%s/verifyAuthority/user/querySameStreetUser?userId=%s",
//                    this.authServerUri, userId);
//            log.info("调用查询用户信息的接口：{}", url);
//            JSONObject sameStreetUserJson = HttprequestHelper.httpRequest(sameStreetUserUrl, "GET", "");
//            JSONArray sameStreetUserArray = sameStreetUserJson.getJSONArray("data");
//            String sameStreetUserJsonStr = JSONObject.toJSONString(sameStreetUserArray);
//            userNameList = JSONObject.parseArray(sameStreetUserJsonStr, String.class);
//        } else if ("4".equals(roleId)) {
//            String queryBelongToUrl = String.format("%s/verifyAuthority/user/queryBelongToAreaUser?userId=%s",
//                    this.authServerUri, userId);
//            log.info("调用查询用户信息的接口：{}", url);
//            JSONObject queryBelongToJson = HttprequestHelper.httpRequest(queryBelongToUrl, "GET", "");
//            JSONArray queryBelongToArray = queryBelongToJson.getJSONArray("data");
//            String queryBelongToJsonStr = JSONObject.toJSONString(queryBelongToArray);
//            userNameList = JSONObject.parseArray(queryBelongToJsonStr, String.class);
//            userNameList.add(userName);
//        } else if ("1".equals(roleId)){
//            userNameList = null;
//        } else {
//            userNameList = new ArrayList();
//            userNameList.add(userName);
//        }
        return operateLogDao.queryOperateLog(username,beginTime,endTime,
                operateModule,operateDetail);
    }

    @Override
    public String queryPrecinctId() {
        return operateLogDao.queryPrecinctId();
    }
}

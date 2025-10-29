package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.*;
import com.znv.manage.common.utils.HttpsClientRequestFactory;
import com.znv.manage.common.utils.SSLClient;
import com.znv.manage.controller.HuaweiController;
import com.znv.manage.dao.HuaWeiConfereceDao;
import com.znv.manage.service.HuaWeiConferenceService;
import com.znv.manage.webservice.SiteParamEx;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Slf4j
public class HuaWeiConferenceServiceImpl implements HuaWeiConferenceService {

    @Autowired
    private HuaWeiConfereceDao huaWeiConfereceDao;

    @Autowired
    private HuaweiController huaweiController;

    @Override
    public List<HuaweiGroup> queryGroupList(String type,String name,String precinctId) {
        List<HuaweiGroup> list = huaWeiConfereceDao.queryGroupList(type,name,precinctId);
        if(!CollectionUtils.isEmpty(list)){
            Result result = huaweiController.querySites(1,1000);
            Map<String,Object> data = (Map<String, Object>) result.getData();
            if(null != data){
                List<SiteParamEx> allUris = (List<SiteParamEx>)data.get("list");
                for(HuaweiGroup group:list){
                    if(!StringUtils.isEmpty(group.getUris())){
                        group.setUriList(getUriList(group.getUris(),allUris));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<HuaweiGroup> queryGroupTree(String type,String precinctId) {
        List<HuaweiGroup> list = huaWeiConfereceDao.queryGroupList(type,null,precinctId);
        if(!CollectionUtils.isEmpty(list)){
            Result result = huaweiController.querySites(1,1000);
            Map<String,Object> data = (Map<String, Object>) result.getData();
            if(null != data){
                List<SiteParamEx> allUris = (List<SiteParamEx>)data.get("list");
                for(HuaweiGroup group:list){
                    if(!StringUtils.isEmpty(group.getUris())){
                        group.setChildren(getUriList(group.getUris(),allUris));
                    }
                }
            }
            HuaweiGroupTree groupTree = new HuaweiGroupTree(list);
            list = groupTree.builTree();
        }
        return list;
    }

    private List<Object> getUriList(String uris,List<SiteParamEx> allUris) {
        List<Object> result = new ArrayList<>();
        List<String> urisList = Arrays.asList(uris.split(","));
        for(int i = 0;i<allUris.size();i++){
            SiteParamEx uri = allUris.get(i);
            if(urisList.contains(uri.getUri())){
                JSONObject temp = JSONObject.parseObject(JSON.toJSONString(uri));
                temp.put("kind","会场");
                result.add(temp);
            }
        }
        return result;
    }

    @Override
    public int addGroup(HuaweiGroup huaweiGroup) {
        return huaWeiConfereceDao.addGroup(huaweiGroup);
    }

    @Override
    public int updateGroupInfo(HuaweiGroup huaweiGroup) {
        return huaWeiConfereceDao.updateGroupInfo(huaweiGroup);
    }

    @Override
    public int deleteGroup(String ids) {
        return huaWeiConfereceDao.deleteGroup(ids);
    }
}

package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.HuaweiGroup;
import com.znv.manage.common.bean.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HuaWeiConferenceService {

    List<HuaweiGroup> queryGroupList(String type,String name,String precinctId);

    List<HuaweiGroup> queryGroupTree(String type,String precinctId);

    int addGroup(HuaweiGroup huaweiGroup);

    int updateGroupInfo(HuaweiGroup huaweiGroup);

    int deleteGroup(@Param("ids")String ids);

}

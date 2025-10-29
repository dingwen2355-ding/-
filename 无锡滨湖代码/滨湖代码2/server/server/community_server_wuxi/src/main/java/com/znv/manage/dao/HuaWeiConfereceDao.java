package com.znv.manage.dao;


import com.znv.manage.common.bean.HuaweiGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface HuaWeiConfereceDao {

    List<Map<String,String>> queryMainUri(@Param("precinctId")String precinctId);

    List<HuaweiGroup> queryGroupList(@Param("type")String type,@Param("name")String name,@Param("precinctId")String precinctId);

    int addGroup(HuaweiGroup huaweiGroup);

    int updateGroupInfo(HuaweiGroup huaweiGroup);

    int deleteGroup(@Param("ids")String ids);
}

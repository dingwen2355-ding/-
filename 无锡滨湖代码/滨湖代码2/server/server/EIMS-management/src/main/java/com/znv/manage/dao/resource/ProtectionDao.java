package com.znv.manage.dao.resource;

import com.znv.manage.common.bean.resource.BasicExpertInfo;
import com.znv.manage.common.bean.resource.Protection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtectionDao {
    List<Protection> queryProtectionList(@Param("protectionName") String protectionName,
                                         @Param("protectionType") String protectionType);

    int deleteByPrimaryKey(String ids);

    int insertSelective(Protection protection);

    int updateByPrimaryKeySelective(Protection protection);

    int insertSelectiveList(List<Protection> protectionList);


}

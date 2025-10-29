package com.znv.manage.dao;


import com.znv.manage.common.bean.WhpjyxxBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface WhpjyxxDao {

    int insertWhpjyxxList(@Param("list") List<WhpjyxxBean> resultList);

    int updateWhpjyxxList(@Param("list") List<WhpjyxxBean> resultList);

    int deleteWhpjyxxList();

    List<Map<String, Object>> queryWhpjyxxList(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("limit") Integer limit);

    int queryWhpjyxxCount(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("limit") Integer limit);
}

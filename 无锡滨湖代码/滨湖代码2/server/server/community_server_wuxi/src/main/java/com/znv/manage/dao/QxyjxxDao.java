package com.znv.manage.dao;


import com.znv.manage.common.bean.QxyjxxBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: zl
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface QxyjxxDao {

    int insertQxyjxxList(@Param("list") List<QxyjxxBean> resultList);

    int updateQxyjxxList(@Param("list") List<QxyjxxBean> resultList);

    int deleteQxyjxxList();

    List<Map<String, Object>> queryQxyjxxList(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    int queryQxyjxxCount(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}

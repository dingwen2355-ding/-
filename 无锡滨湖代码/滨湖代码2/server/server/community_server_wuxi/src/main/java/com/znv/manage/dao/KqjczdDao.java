package com.znv.manage.dao;


import com.znv.manage.common.bean.Sw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/07/20
 * @Description: 注：
 */

@Repository
public interface KqjczdDao {
    List<Map<String, Object>> selectSiteList(@Param("precinctId") String precinctId,@Param("precinctName") String precinctName);
}

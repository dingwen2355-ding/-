package com.znv.manage.dao;


import com.znv.manage.common.bean.ZdwxyBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface ZdwxyDao {

    int insertZdwxyList(@Param("list") List<ZdwxyBean> resultList);

    int updateZdwxyList(@Param("list") List<ZdwxyBean> resultList);

    int deleteZdwxyList();

    int queryZdwxyList(@Param("SJWXHXPMC") String SJWXHXPMC,@Param("RKSJ") String RKSJ);
}

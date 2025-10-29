package com.znv.manage.dao;


import com.znv.manage.common.bean.Jqsskll;
import com.znv.manage.common.bean.Ywgd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/07/06
 * @Description: 注：
 */

@Repository
public interface JqsskllDao {

    int insertJqsskllList(@Param("list") List<Jqsskll> resultList);

    int delAll();
}

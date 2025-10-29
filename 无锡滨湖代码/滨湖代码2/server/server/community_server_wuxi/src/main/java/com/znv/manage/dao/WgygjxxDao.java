package com.znv.manage.dao;


import com.znv.manage.common.bean.Jjsw;
import com.znv.manage.common.bean.Wgygjxx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/07/01
 * @Description: 注：
 */

@Repository
public interface WgygjxxDao {

    int insertWgygjxxList(@Param("list") List<Wgygjxx> resultList);

    int delAll();
}

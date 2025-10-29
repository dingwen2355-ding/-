package com.znv.manage.dao;


import com.znv.manage.common.bean.Jczdjbxx;
import com.znv.manage.common.bean.Jjsw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/07/01
 * @Description: 注：
 */

@Repository
public interface JjswDao {

    int insertJjswList(@Param("list") List<Jjsw> resultList);

}

package com.znv.manage.dao;


import com.znv.manage.common.bean.Zsdw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface ZsdwDao {

    int insertZsdwList(@Param("list") List<Zsdw> resultList);
}

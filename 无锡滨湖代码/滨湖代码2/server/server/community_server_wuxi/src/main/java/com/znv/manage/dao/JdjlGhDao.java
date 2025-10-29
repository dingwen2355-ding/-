package com.znv.manage.dao;


import com.znv.manage.common.bean.Jdjl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/07/08
 * @Description: 注：
 */

@Repository
public interface JdjlGhDao {

    int insertJdjlList(@Param("list") List<Jdjl> resultList);

    int getLettersAndVisitsCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}

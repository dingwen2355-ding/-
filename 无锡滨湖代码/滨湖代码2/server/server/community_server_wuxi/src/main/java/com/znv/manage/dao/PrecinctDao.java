package com.znv.manage.dao;

import com.znv.manage.common.bean.Precinct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecinctDao {

    List<Precinct> queryPrecinctList();

    String queryWxUserId(@Param("precinctId")String precinctId);
}

package com.znv.manage.dao;

import com.znv.manage.common.bean.Fwgdb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface LeaderFileDao {

    List<Map<String, Object>> queryLeaderFileList(@Param("title")String title,
                                                    @Param("type")String type,
                                                    @Param("pageStart")Integer pageStart,
                                                    @Param("pageLen")Integer pageLen);

}

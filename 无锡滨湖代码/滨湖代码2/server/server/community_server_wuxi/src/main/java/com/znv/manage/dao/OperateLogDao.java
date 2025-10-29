package com.znv.manage.dao;


import com.znv.manage.common.bean.OperateLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Component
public interface OperateLogDao {

    int saveLog(OperateLog operateLog);

    List<OperateLog> queryOperatelogList(@Param("operatorName")String operatorName,
                                         @Param("precinctId")String precinctId,
                                         @Param("beginTime") Date beginTime,
                                         @Param("endTime")Date endTime,
                                         @Param("pageStart")Integer pageStart,
                                         @Param("pageLen")Integer pageLen);
}

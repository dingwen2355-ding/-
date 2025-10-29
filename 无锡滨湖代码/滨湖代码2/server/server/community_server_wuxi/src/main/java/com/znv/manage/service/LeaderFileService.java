package com.znv.manage.service;

import com.znv.manage.common.bean.Result;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LeaderFileService {

    Result queryLeaderFileList(String title,
                               String type,
                               Integer pageStart,
                               Integer pageLen);

}

package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.RescueTeamInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @title: TeamBasicInfoService
 * @Author yf
 * @Date: 2022/4/11 15:09
 */

public interface TeamBasicInfoService {


    public void insertTeamInfo(RescueTeamInfo rescueTeamInfo);

    public void updateTeamInfo(RescueTeamInfo rescueTeamInfo);

    public Result deleteTeamInfo(int id);

    public Result batchDeleteTeamInfo(@Param("ids") String ids);

    public RescueTeamInfo queryRescueTeamInfoById(int id);
}

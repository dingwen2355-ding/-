package com.znv.manage.service.impl.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.RescueTeamInfo;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.resource.TeamInfoDao;
import com.znv.manage.service.resource.TeamBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: TeamBasicInfoServiceImpl
 * @Author yf
 * @Date: 2022/4/11 15:13
 */
@Service
public class TeamBasicInfoServiceImpl implements TeamBasicInfoService {

    @Autowired
    private TeamInfoDao teamInfoDao;

    @Override
    public void insertTeamInfo(RescueTeamInfo rescueTeamInfo) {
        teamInfoDao.insertTeamInfo(rescueTeamInfo);
    }

    @Override
    public void updateTeamInfo(RescueTeamInfo rescueTeamInfo) {
        teamInfoDao.updateTeamInfo(rescueTeamInfo);
    }

    @Override
    public Result deleteTeamInfo(int id) {
        Result result = new Result();
        teamInfoDao.deleteTeamInfo(id);
        result.setCode(ResultCodeEnum.NORMAL.getCode());
        result.setMessage(ResultCodeEnum.NORMAL.getName());
        return result;
    }

    @Override
    public Result batchDeleteTeamInfo(String ids) {
        Result result = new Result();
        try {
            teamInfoDao.batchDeleteTeamInfo(ids);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public RescueTeamInfo queryRescueTeamInfoById(int id) {
        RescueTeamInfo rescueTeamInfo = new RescueTeamInfo();
        List<RescueTeamInfo> rescueTeamInfos = teamInfoDao.queryRescueTeamInfoById(id);
        rescueTeamInfo = rescueTeamInfos.get(0);
        return rescueTeamInfo;
    }
}

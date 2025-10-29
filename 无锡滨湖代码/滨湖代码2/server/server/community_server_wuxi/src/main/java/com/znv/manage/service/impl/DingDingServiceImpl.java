package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.*;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.DingDingUtil;
import com.znv.manage.dao.DingDingDao;
import com.znv.manage.service.DingDingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author:
 * @Date:
 * @Description:
 */

@Service
@Slf4j
public class DingDingServiceImpl implements DingDingService {

    @Autowired
    private DingDingDao dingDingDao;

    private static Logger logger = LoggerFactory.getLogger(DingDingService.class);

    @Override
    public Result syncDepartment(String deptId) {
        Result result = new Result();
        //获取部门信息
        DeptInfo deptInfo = DingDingUtil.getDeptInfo(deptId);
        try {
            if (deptInfo != null) {
                if (!queryDeptExist(deptInfo.getDeptId())) {
                    //插入数据
                    dingDingDao.insertDeptData(deptInfo);
                }/*else {
                    dingDingDao.updateDeptData(deptInfo);
                }*/
                List<String> list = DingDingUtil.getDeptList(deptId);
                if (list.size() > 0) {
                    for (String id : list) {
                        this.syncDepartment(id);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage("同步数据失败");
        }
        return result;
    }


    @Override
    public Result syncCommunicationData(String deptId) {
        Result result = new Result();
        List<String> list = DingDingUtil.getDeptList(deptId);
        //查询通讯录数据
        result = searchData(deptId);
        if (list.size() > 0) {
            for (String id : list) {
                this.syncCommunicationData(id);
            }
        }
        return result;
    }

    @Override
    public Result createVideoConferences(List<String> unionIds,String titleName) {
        Result result = new Result();
        VideoConferencesInfo videoConferencesInfo = DingDingUtil.videoConferences(unionIds,titleName);
        result.setData(videoConferencesInfo);
        return result;
    }


    public Result searchData(String deptId) {
        Result result = new Result();
        int pageNumber = -1;
        int pageSize = 100;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                List<CommunicationInfo> list = DingDingUtil.getUserList(deptId, pageNumber, pageSize);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    insertCommunicationData(list);
                } else {
                    logger.info("insertCommunicationData list.size为0");
                }
                if (list.size() < pageSize) {
                    flag = false;
                }
            } while (flag);
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage("同步数据失败");
            logger.error("searchData res error ： " + e.getMessage());
        }
        return result;
    }

    public int insertCommunicationData(List<CommunicationInfo> list) {
        int i = 0;
        List<CommunicationInfo> resultList = new ArrayList();
        /*List<CommunicationInfo> updateList = new ArrayList();*/
        for(CommunicationInfo communicationInfo : list){
            if(!queryCommunicationExist(communicationInfo.getUserId())){
                resultList.add(communicationInfo);
            }/*else{
                updateList.add(communicationInfo);
            }*/
        }
        if (resultList.size() != 0) {
            i += dingDingDao.insertCommunicationData(resultList);
        }
       /* if (updateList.size() != 0) {
            i += dingDingDao.updateCommunicationData(updateList);
        }*/
        return i;
    }

    public boolean queryDeptExist(String id) {
        boolean flag = false;
        int count = dingDingDao.queryDeptExist(id);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

    public boolean queryCommunicationExist(String id) {
        boolean flag = false;
        int count = dingDingDao.queryCommunicationExist(id);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}

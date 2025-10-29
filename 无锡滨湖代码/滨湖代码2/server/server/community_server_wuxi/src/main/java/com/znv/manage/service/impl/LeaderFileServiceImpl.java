package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.dao.LeaderFileDao;
import com.znv.manage.service.LeaderFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class LeaderFileServiceImpl implements LeaderFileService {

    @Autowired
    LeaderFileDao leaderFileDao;

    @Override
    public Result queryLeaderFileList(String title, String type, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list1 = leaderFileDao.queryLeaderFileList(title,type,null,null);
            if(null == pageNum || null == pageSize){
                result.setData(list1);
                return result;
            }
            Integer start = 0;
            start = pageSize * (pageNum - 1);
            List<Map<String,Object>> list2 = leaderFileDao.queryLeaderFileList(title,type,start,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("count", CollectionUtils.isEmpty(list1)?0:list1.size());
            map.put("list",list2);
            result.setData(map);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}

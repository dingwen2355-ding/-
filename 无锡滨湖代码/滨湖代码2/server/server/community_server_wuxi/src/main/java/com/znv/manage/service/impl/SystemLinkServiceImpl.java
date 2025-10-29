package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.SystemLinkDao;
import com.znv.manage.service.SystemLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:
 * @Date:
 * @Description:
 */

@Service
@Slf4j
public class SystemLinkServiceImpl implements SystemLinkService {

    @Autowired
    private SystemLinkDao systemLinkDao;

    @Override
    public Result getSystemLink() {
        Result result = new Result();
        try {
            //获取实时报警数据
            List<Map<String, Object>> list = systemLinkDao.getSystemLink();
            //获取实时报警数量
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            map.put("count", list.size());
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}

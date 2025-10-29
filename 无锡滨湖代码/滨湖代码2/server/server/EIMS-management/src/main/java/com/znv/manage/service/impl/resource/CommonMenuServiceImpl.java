package com.znv.manage.service.impl.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.resource.CommonMenuDao;
import com.znv.manage.service.resource.CommonMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: 服务层
 * @date 2018/5/18 14:57
 */
@Service
public class CommonMenuServiceImpl implements CommonMenuService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CommonMenuServiceImpl.class);

    @Autowired
    CommonMenuDao commonMenuDao;

    @Override
    public Result getRegion() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = commonMenuDao.getRegion();
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getBelongUnit() {
        Result result = new Result();
        try {
            List<Map<String, Object>> area = commonMenuDao.getBelongArea();
            for (Map map : area) {
                List<Map<String, Object>> list = commonMenuDao.getBelongUnit(map.get("area").toString());
                map.put("data", list);
            }
            result.setData(area);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getBeManagedType() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = commonMenuDao.getBeManagedType();
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getStorageLevel() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = commonMenuDao.getStorageLevel();
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getStorageType() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = commonMenuDao.getStorageType();
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getIndustry() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = commonMenuDao.getIndustry();
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getHelpType() {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = commonMenuDao.getHelpType();
            Map<String, Object> map = new HashMap<>();
            map.put("data", list);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public String queryStockByRegion(String region) {
        return commonMenuDao.queryStockByRegion(region);
    }

}

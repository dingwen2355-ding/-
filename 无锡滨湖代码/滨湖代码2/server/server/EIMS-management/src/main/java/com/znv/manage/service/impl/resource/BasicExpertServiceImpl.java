package com.znv.manage.service.impl.resource;

import com.alibaba.druid.util.StringUtils;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.BasicExpertInfo;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.dao.resource.BasicExpertDao;
import com.znv.manage.service.resource.BasicExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
public class BasicExpertServiceImpl implements BasicExpertService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(BasicExpertServiceImpl.class);

    @Autowired
    BasicExpertDao basicExpertDao;

    @Autowired
    EventDao eventDao;

    @Override
    public Result getBasicExpertList(Integer id, String name, Integer sex, String type,
                                     String title, String company, String expertCategory, String address,
                                     String planId, String eventId, String isAll, Integer pageStart, Integer pageLen) {
        Result result = new Result();
        try {
            Integer start = 0;
            if (pageStart != null && pageLen != null) {
                start = pageLen * (pageStart - 1);
            }
            String tempEventId = eventId;
            if ("1".equals(isAll)) {
                tempEventId = null;
            }
            //获取所有数据
            List<Map<String, Object>> list = basicExpertDao.getBasicExpertList(id, name, sex, type,
                    title, company, expertCategory, address, planId, tempEventId, start, pageLen);
            //计算距离
            if (!org.springframework.util.StringUtils.isEmpty(eventId) && !CollectionUtils.isEmpty(list)) {
                Map<String, String> map = eventDao.queryGps(eventId);
                if (map != null) {
                    list.forEach(temp -> temp.put("distance", CommonUtils.calculateDistance(map.get("lat"), map.get("lon"), (String) temp.get("gpsy"), (String) temp.get("gpsx"))));
                } else {
                    list.forEach(temp -> temp.put("distance", null));
                }
            }
            Integer listCount = basicExpertDao.getBasicExpertListCount(id, name, sex, type,
                    title, company, address, expertCategory);
            Map<String, Object> map = new HashMap<>();
            map.put("count", listCount);
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
    public Result insertBasicExpertInfo(BasicExpertInfo basicExpertInfo) {
        Result result = new Result();
        try {
            if (basicExpertDao.queryExist(basicExpertInfo) > 0) {
                result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), "数据重复");
            } else {
                basicExpertDao.insertBasicExpertInfo(basicExpertInfo);
                result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result insertBasicExpertInfoList(List<BasicExpertInfo> basicExpertInfos) {
        Result result = new Result();
        try {
            basicExpertDao.insertBasicExpertInfoList(basicExpertInfos);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateBasicExpertInfo(BasicExpertInfo basicExpertInfo) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(basicExpertInfo.getId())) {
                result.setResutCode(ResultCodeEnum.NOTACCEPTABLE.getCode(), ResultCodeEnum.NOTACCEPTABLE.getName());
                result.setData("Id is null");
            }
            if (basicExpertDao.queryExist(basicExpertInfo) > 0) {
                result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), "数据重复");
            } else {
                basicExpertDao.updateBasicExpertInfo(basicExpertInfo);
                result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBasicExpertInfo(String ids) {

        Result result = new Result();
        try {
            basicExpertDao.deleteBasicExpertInfo(ids);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }

        return result;
    }

    @Override
    public List<BasicExpertInfo> getBasicExpertType(String name) {
        List<BasicExpertInfo> list = new ArrayList<>();
        try {
            list = basicExpertDao.getBasicExpertType(name);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getBasicExpertTitle() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = basicExpertDao.getBasicExpertTitle();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return list;
    }

    @Override
    public boolean queryExist(BasicExpertInfo basicExpertInfo) {
        boolean flag = false;
        int count = basicExpertDao.queryExist(basicExpertInfo);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}

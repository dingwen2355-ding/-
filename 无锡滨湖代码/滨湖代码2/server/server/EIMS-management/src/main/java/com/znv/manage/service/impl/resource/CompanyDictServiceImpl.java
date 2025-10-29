package com.znv.manage.service.impl.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.dao.resource.CompanyDictMapper;
import com.znv.manage.service.resource.CompanyDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: 服务层
 * @date 2018/5/18 14:57
 */
@Service
public class CompanyDictServiceImpl implements CompanyDictService {

    @Autowired
    CompanyDictMapper companyDictMapper;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CompanyDictServiceImpl.class);

    @Override
    public Result queryRegion() {
        Result result = new Result();
        List<Map<String, Object>> list = companyDictMapper.queryRegion();
        result.setData(list);
        return result;
    }

    @Override
    public Result queryCompanyType() {
        Result result = new Result();
        List<Map<String, Object>> list = companyDictMapper.queryCompanyTypeParent();
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            String parentId = map.get("companyType").toString();
            List<Map<String, Object>> list2 = companyDictMapper.queryCompanyType(parentId);
            map.put("children", list2);
        }
        result.setData(list);
        return result;
    }

    @Override
    public Result queryStandLevel() {
        Result result = new Result();
        List<Map<String, Object>> list = companyDictMapper.queryStandLevel();
        result.setData(list);
        return result;
    }

    @Override
    public Result queryCompanyState() {
        Result result = new Result();
        List<Map<String, Object>> list = companyDictMapper.queryCompanyState();
        result.setData(list);
        return result;
    }

    @Override
    public Result queryIndustry() {
        Result result = new Result();
        List<Map<String, Object>> list = companyDictMapper.queryIndustry();
        result.setData(list);
        return result;
    }
}

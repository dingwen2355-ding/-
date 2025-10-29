package com.znv.manage.service.impl.information;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.information.WarningRelease;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.information.WarningReleaseMapper;
import com.znv.manage.service.information.CommandWarningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class CommandWarningServiceImpl implements CommandWarningService {

    @Autowired
    WarningReleaseMapper warningReleaseMapper;

    @Override
    public Result getWarning(Long id, String title, String deal, String type1, String type2, String level, String startDate, String endDate, String area,String flag) {
        Result result = new Result();
        try {
            result.setData(warningReleaseMapper.getWarning(id, title, deal, type1, type2, level, startDate, endDate, area,flag));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getWarningScreen(Long id, String title, String deal, String type1, String type2, String level, String startDate, String endDate, String area) {
        Result result = new Result();
        try {
            result.setData(warningReleaseMapper.getWarningScreen(id, title, deal, type1, type2, level, startDate, endDate, area));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateWarning(WarningRelease messageRelease) {
        Result result = new Result();
        try {
            warningReleaseMapper.updateByPrimaryKey(messageRelease);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result insertWarning(WarningRelease warningRelease) {
        Result result = new Result();
        try {
            warningReleaseMapper.insert(warningRelease);
            result.setData(warningReleaseMapper.getWarning(Long.valueOf(warningRelease.getId()), null, null, null, null, null, null, null, null,null));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteWarning(Long id) {
        Result result = new Result();
        try {
            warningReleaseMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result dealWarning(Long id, String content, String deal, String userId, String title) {
        Result result = new Result();
        try {
            warningReleaseMapper.dealWarning(id, content, deal);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result warningCount(String area, String type, String startDate, String endDate) {
        Result result = new Result();
        try {
            Map<String, String> map = warningReleaseMapper.warningCount(area, type, startDate, endDate);
            result.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}

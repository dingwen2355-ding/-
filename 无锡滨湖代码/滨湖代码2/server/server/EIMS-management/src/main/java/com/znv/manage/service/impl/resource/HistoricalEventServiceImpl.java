package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.HistoricalEvent;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.dao.resource.HistoricalEventDao;
import com.znv.manage.service.resource.HistoricalEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
public class HistoricalEventServiceImpl implements HistoricalEventService {
    @Autowired
    private HistoricalEventDao historicalEventDao;

    @Value("${filePath}")
    private String filePath;

    @Value("${urlPath}")
    private String urlPath;

    @Override
    public PageInfo<HistoricalEvent> queryHistoricalEventList(String accidentTitle, String accidentAddress, String accidentType, Integer pageNum, Integer pageSize) {
        if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<HistoricalEvent> list = historicalEventDao.queryHistoricalEventList(accidentTitle, accidentAddress, accidentType);
        return new PageInfo<HistoricalEvent>(list);
    }

    @Override
    public Result deleteHistoricalEvent(String ids) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(ids)) {
                result.setCode(ResultCodeEnum.INVALIDREQUEST.getCode());
                result.setMessage(ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                String annexUrl = historicalEventDao.queryAnnexUrl(Long.valueOf(id[i]));
                CommonUtils.deleteFile(annexUrl, filePath, urlPath);
            }
            historicalEventDao.deleteHistoricalEvent(ids);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelective(HistoricalEvent historicalEvent, MultipartFile files) {
        Result result = new Result();
        try {
            if (null != files) {
                String s = CommonUtils.uploadFile(files, filePath, urlPath);
                s = URLDecoder.decode(URLEncoder.encode(s));
                historicalEvent.setAnnexUrl(s);
            }
            historicalEventDao.insertSelective(historicalEvent);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result updateByPrimaryKeySelective(HistoricalEvent historicalEvent, MultipartFile files) {
        Result result = new Result();
        try {
            // 重新上传文件的url
            if (null != files) {
                String s = CommonUtils.uploadFile(files, filePath, urlPath);
                s = URLDecoder.decode(URLEncoder.encode(s));
                historicalEvent.setAnnexUrl(s);
            }
            // 数据库中的url
            String annexUrl = historicalEventDao.queryAnnexUrl(historicalEvent.getId());
            // 如果两个url不一致且库中的url不为空,则删除
            if (!StringUtils.isEmpty(annexUrl) && !annexUrl.equals(historicalEvent.getAnnexUrl())) {
                CommonUtils.deleteFile(annexUrl, filePath, urlPath);
            }
            historicalEventDao.updateByPrimaryKeySelective(historicalEvent);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}

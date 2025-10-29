package com.znv.manage.service.resource;

import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.HistoricalEvent;
import org.springframework.web.multipart.MultipartFile;

public interface HistoricalEventService {
    PageInfo<HistoricalEvent> queryHistoricalEventList(String accidentTitle, String accidentAddress, String accidentType, Integer pageNum, Integer pageSize);

    Result deleteHistoricalEvent(String ids);

    Result insertSelective(HistoricalEvent historicalEvent, MultipartFile files);

    Result updateByPrimaryKeySelective(HistoricalEvent historicalEvent, MultipartFile files);
}

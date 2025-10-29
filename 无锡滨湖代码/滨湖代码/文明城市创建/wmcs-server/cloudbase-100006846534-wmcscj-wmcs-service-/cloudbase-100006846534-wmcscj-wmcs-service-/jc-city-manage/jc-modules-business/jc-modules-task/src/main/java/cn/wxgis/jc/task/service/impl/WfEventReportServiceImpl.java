package cn.wxgis.jc.task.service.impl;

import cn.wxgis.jc.common.web.service.impl.BaseServiceImpl;
import cn.wxgis.jc.task.mapper.WfEventReportMapper;
import cn.wxgis.jc.task.service.WfEventReportService;
import cn.wxgis.jc.workflow.enums.EventSourceEnum;
import cn.wxgis.jc.workflow.enums.WfStatusEnum;
import cn.wxgis.jc.workflow.po.WfEventReport;
import cn.wxgis.jc.workflow.vo.WmcsWarnVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WfEventReportServiceImpl extends BaseServiceImpl<WfEventReportMapper, WfEventReport> implements WfEventReportService {
    public WfEventReportServiceImpl(WfEventReportMapper baseDao) {
        super(baseDao);
    }

    @Override
    public List<WfEventReport> listNoEndByAi() {
        LambdaQueryWrapper<WfEventReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(WfEventReport::getStatus, WfStatusEnum.END.getCode());
        queryWrapper.eq(WfEventReport::getEventSource, EventSourceEnum.AI.getCode());
        return baseDao.selectList(queryWrapper);
    }

    @Override
    public boolean existByWarnInfo(WmcsWarnVo warn) {
        // 组装查询数据
        String sourceType = String.valueOf(EventSourceEnum.AI.getCode());
        String warnType = warn.getType();
        String channelId = warn.getChannelId();
        Integer status =  WfStatusEnum.END.getCode();
//        String startTime = DateUtils.getLastMinuteDate(new Date(), warn.getMinute());
//        String time = DateUtils.parseDateToString(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS);
        LambdaQueryWrapper<WfEventReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfEventReport::getEventSource, sourceType)
//                .ge(WfEventReport::getCreateTime, startTime)
//                .le(WfEventReport::getCreateTime, time)
                .eq(WfEventReport::getWarnType, warnType)
                .eq(WfEventReport::getChannelId, channelId)
                .ne(WfEventReport::getStatus, WfStatusEnum.END.getCode())
                .orderByDesc(WfEventReport::getCreateTime);
        int count = baseDao.selectCount(queryWrapper);
        return count > 0;
    }
}

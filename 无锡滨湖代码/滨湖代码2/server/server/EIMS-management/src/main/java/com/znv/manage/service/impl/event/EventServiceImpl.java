package com.znv.manage.service.impl.event;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.Event;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.common.utils.ExcelUtils;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.service.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TCfgEvent表服务实现类
 *
 * @author 0049003788
 * @since 2022-12-09 13:41:37
 */
@Service
@Slf4j
public class EventServiceImpl implements EventService {
    @Resource
    private EventDao eventDao;

    @Value("${filePath}")
    String filePath;

    @Value("${urlPath}")
    String fileUrl;

    @Override
    public Result insert(Event event) {
        Result result = new Result();
        try {
            eventDao.insert(event);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(Event event) {
        Result result = new Result();
        try {
            eventDao.update(event);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateDevice(Integer eventId, String deviceIds) {
        Result result = new Result();
        try {
            eventDao.updateDevice(eventId, deviceIds);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteById(Integer id) {
        Result result = new Result();
        try {
            result.setData(eventDao.deleteById(id) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBatch(String ids) {
        Result result = new Result();
        try {
            result.setData(eventDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, String eventTitle, Integer typeId, Integer streetId, Integer communityId,
                                   String sourceId, String streetName, String communityName, String startTime,
                                   String endTime, String status, String typeName, String sourceName, String statusIds,
                                   String userId, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventDao.queryByCondition(id, eventTitle, typeId, streetId, status, communityId, sourceId,
                    streetName, communityName, startTime, endTime,  typeName, sourceName, statusIds, userId)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result export(String ids) {
        Result result = new Result();
        try {
            List<Event> list = eventDao.queryByIds(ids);
            if (!list.isEmpty()) {
                String fileName = "事件查询" + DateUtils.getNowTime("yyyyMMddhhmmss");
                exportExcel(filePath + "/event/" + fileName + ".xlsx", list);
                result.setData(fileUrl + "/event/" + fileName + ".xlsx");
            } else {
                throw new BusinessException("当前数据为空");
            }
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getContactByEventId(Integer eventId) {
        Result result = new Result();
        try {
            result.setData(eventDao.getContactByEventId(eventId));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getSyncEventCount(String startTime, String endTime) {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = eventDao.countSyncEventByType(startTime, endTime);
            if (!CollectionUtils.isEmpty(list) && list.size() > 10) {
                Long num = (Long) list.get(0).get("num");
                for (int i = 1; i < 9; i++) {
                    num = num - (Long) list.get(i).get("num");
                }
                list = list.subList(0, 9);
                Map<String, Object> map = new HashMap<>();
                map.put("typeName", "其他");
                map.put("num", num);
                list.add(map);
            }
            result.setData(list);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getSyncEventCountBySource(String startTime, String endTime) {
        Result result = new Result();
        try {
            result.setData(eventDao.countSyncEventBySource(startTime, endTime));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result syncEventList(String startTime, String endTime, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            result.setData(new PageInfo<>(eventDao.querySyncEventList(startTime, endTime)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    private void exportExcel(String file, List<Event> list) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");
        List<String> title = Arrays.asList("事故标题", "事故类型", "街道名称", "事故来源", "社区名称",
                "时间", "小类名称", "事件级别", "影响级别", "监督单位", "轻伤人员", "重伤人员", "死亡人员",
                "经济损失", "是否总结", "是否指挥", "经度", "纬度", "地址", "概述");
        XSSFCellStyle style = wb.createCellStyle();
        // 生成表头
        XSSFRow headRow = sheet.createRow(0);
        sheet.setDefaultColumnWidth(10);
        for (int i = 0; i < title.size(); i++) {
            XSSFCellStyle headStyle = wb.createCellStyle();
            ExcelUtils.setExcelValue(headRow.createCell(i), title.get(i), headStyle);
        }
        //填充数据
        int startRow = 1;
        for (Event entity : list) {
            XSSFRow row = sheet.createRow(startRow++);
            ExcelUtils.setExcelValue(row.createCell(0), entity.getEventTitle(), style);
            ExcelUtils.setExcelValue(row.createCell(1), entity.getTypeName(), style);
            ExcelUtils.setExcelValue(row.createCell(2), entity.getStreetName(), style);
            ExcelUtils.setExcelValue(row.createCell(3), entity.getSourceName(), style);
            ExcelUtils.setExcelValue(row.createCell(4), entity.getCommunityName(), style);
            ExcelUtils.setExcelValue(row.createCell(5), entity.getEventTime(), style);
            ExcelUtils.setExcelValue(row.createCell(6), entity.getSmallTypeName(), style);
            ExcelUtils.setExcelValue(row.createCell(7), entity.getLevelName(), style);
            ExcelUtils.setExcelValue(row.createCell(8), entity.getImpactLevel(), style);
            ExcelUtils.setExcelValue(row.createCell(9), entity.getMonitorUnit(), style);
            ExcelUtils.setExcelValue(row.createCell(10), entity.getMinorNum(), style);
            ExcelUtils.setExcelValue(row.createCell(11), entity.getSeriousNum(), style);
            ExcelUtils.setExcelValue(row.createCell(12), entity.getDeathNum(), style);
            ExcelUtils.setExcelValue(row.createCell(13), entity.getEconomicLoss(), style);
            ExcelUtils.setExcelValue(row.createCell(14), entity.getIsConclusion(), style);
            ExcelUtils.setExcelValue(row.createCell(15), entity.getIsCommand(), style);
            ExcelUtils.setExcelValue(row.createCell(16), entity.getLon(), style);
            ExcelUtils.setExcelValue(row.createCell(17), entity.getLat(), style);
            ExcelUtils.setExcelValue(row.createCell(18), entity.getOccurAddress(), style);
            ExcelUtils.setExcelValue(row.createCell(19), entity.getSummary(), style);
        }
        ExcelUtils.globalStyleSet(wb);
        ExcelUtils.setTitleStyle(wb, sheet, headRow, null);
        ExcelUtils.saveFile(wb, file);
    }
}

package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.ResourceOperation;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.common.utils.ExcelUtils;
import com.znv.manage.dao.resource.ResourceOperationDao;
import com.znv.manage.service.resource.ResourceOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * TCfgResourceOperation表服务实现类
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:27
 */
@Service
@Slf4j
public class ResourceOperationServiceImpl implements ResourceOperationService {
    @Resource
    private ResourceOperationDao resourceOperationDao;


    @Value("${filePath}")
    String filePath;

    @Value("${urlPath}")
    String fileUrl;

    @Override
    public Result insert(ResourceOperation resourceOperation) {
        Result result = new Result();
        try {
            resourceOperationDao.insert(resourceOperation);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(ResourceOperation resourceOperation) {
        Result result = new Result();
        try {
            resourceOperationDao.update(resourceOperation);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }


    @Override
    public Result query(Integer id, String stuffName, String type, String info, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(resourceOperationDao.query(id, stuffName, type, info)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBatch(String ids) {
        Result result = new Result();
        try {
            resourceOperationDao.deleteBatch(ids);
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
            //未关联到的脏数据默认过滤
            List<ResourceOperation> list = resourceOperationDao.queryByIds(ids);
            if (!list.isEmpty()) {
                String fileName = "物资操作" + DateUtils.getNowTime("yyyyMMddhhmmss");
                exportExcel(filePath + "/resource/" + fileName + ".xlsx", list);
                result.setData(fileUrl + "/resource/" + fileName + ".xlsx");
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

    private void exportExcel(String file, List<ResourceOperation> list) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");
        List<String> title = Arrays.asList("物资名称", "物资类型", "单位",
                "出入库类型", "数量", "物资来源/领用人员", "备注");
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
        for (ResourceOperation resourceOperation : list) {
            XSSFRow row = sheet.createRow(startRow++);
            ExcelUtils.setExcelValue(row.createCell(0), resourceOperation.getStuffName(), style);
            ExcelUtils.setExcelValue(row.createCell(1), resourceOperation.getTypeName(), style);
            ExcelUtils.setExcelValue(row.createCell(2), resourceOperation.getStuffUnit(), style);
            ExcelUtils.setExcelValue(row.createCell(3), "1".equals(resourceOperation.getType()) ? "入库" : "出库", style);
            ExcelUtils.setExcelValue(row.createCell(4), resourceOperation.getNum(), style);
            ExcelUtils.setExcelValue(row.createCell(5), resourceOperation.getInfo(), style);
            ExcelUtils.setExcelValue(row.createCell(6), resourceOperation.getNote(), style);
        }

        ExcelUtils.merge(sheet, 0, 1, 2);
        ExcelUtils.globalStyleSet(wb);
        ExcelUtils.setTitleStyle(wb, sheet, headRow, null);
        ExcelUtils.saveFile(wb, file);
    }

}

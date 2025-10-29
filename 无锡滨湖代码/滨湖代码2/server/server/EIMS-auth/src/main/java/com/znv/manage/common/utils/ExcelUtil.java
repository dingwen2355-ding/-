package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * @author ：Yang GuanRong
 * @date ：Created in 2020-4-8
 */

@Slf4j
public class ExcelUtil {

    public static List<Map<String, Object>> resolveExcel(MultipartFile file, int beginColumn, int endColumn) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        Workbook workbook = null;
        InputStream is = null;
        List<String> titles = new ArrayList<>();

        try {
            is = file.getInputStream();
            if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".xlsx")) {
                workbook = new XSSFWorkbook(is);
            } else {
                return null;
            }

            Sheet sheet = workbook.getSheetAt(0);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

            //第二行开始遍历
            for (int i = 1; i < physicalNumberOfRows; i++) {
                Map<String, Object> mapInfo = new LinkedHashMap<>();
                if (i == 1) {
                    for (int k = beginColumn; k < endColumn; k++) {
                        if (sheet.getRow(i).getCell(k) != null) {
                            Cell cell = sheet.getRow(i).getCell(k);
                            cell.setCellType(cell.getCellType());
                            titles.add(k,String.valueOf(cell));
                        } else {
                            titles.add(k,"");
                            break;
//
                        }
                    }
                    continue;
                } else {
                    String str[] = new String[titles.size()-1];

                    for (int j = beginColumn; j < titles.size()-1; j++) {
                        Row row = sheet.getRow(i);
                        if (row != null && row.getCell(j) != null) {
                            Cell cell = sheet.getRow(i).getCell(j);
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            str[j] = String.valueOf(cell);
                        } else {
                            str[j] = "";
                        }
                        mapInfo.put(titles.get(j), str[j]);
                    }
                }

                resultList.add(mapInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultList;
    }

    /**
     * 将数据导出成Excel文件
     *
     * @param sheetName sheet名称
     * @param titles 标题
     * @param values 内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(int startRow,String sheetName, List<String> titles, String[][] values) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制

        HSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("表头");
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(0, true);
        sheet.setColumnWidth(0, sheet.getColumnWidth(0) * 15 / 10);

        HSSFRow row = sheet.createRow(startRow);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //设置单元格宽度自适应，在此基础上把宽度调至1.5倍
        for (int i = 0; i < titles.size(); i++) {
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
        }
        // 声明列对象
        HSSFCell cell = null;
        int width;
        // 创建标题
        for (int i = 0; i < titles.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            width = sheet.getColumnWidth(i) * 15 / 10;
            if(width < 255*256){
                sheet.setColumnWidth(i, width < 3000 ? 3000 : width);
            }else{
                sheet.setColumnWidth(i,5000 );
            }
            if ("户籍地".equals(titles.get(i))){
                sheet.setColumnWidth(i,8000 );
            }
        }
        // 创建内容

        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1 + startRow);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                cell = row.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(values[i][j]);
            }
        }
        return wb;
    }


}

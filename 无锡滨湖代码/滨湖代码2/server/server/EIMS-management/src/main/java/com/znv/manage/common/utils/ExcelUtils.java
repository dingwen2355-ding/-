package com.znv.manage.common.utils;

import com.znv.manage.common.exception.BusinessException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * poi导入导出excel工具类
 *
 * @author ：0049003788
 * @date ：2022/11/21 10:45
 */
public class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * 将文件输出本地
     */
    public static void saveFile(XSSFWorkbook wb, String fileName) throws IOException {
        //不存在时自动创建
        File file = new File(fileName);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new BusinessException("文件夹创建失败");
        }
        FileOutputStream output = new FileOutputStream(fileName);
        wb.write(output);
        output.flush();
        output.close();
    }


    /**
     * 全局样式设定
     */
    public static void globalStyleSet(XSSFWorkbook wb) {
        //全局样式设置
        XSSFCellStyle generalStyle = wb.createCellStyle();
        XSSFSheet sheet = wb.getSheetAt(0);
        //全边框
        generalStyle.setBorderBottom(CellStyle.BORDER_THIN);
        generalStyle.setBorderLeft(CellStyle.BORDER_THIN);
        generalStyle.setBorderRight(CellStyle.BORDER_THIN);
        generalStyle.setBorderTop(CellStyle.BORDER_THIN);
        // 居中
        generalStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直居中
        generalStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //字体
        XSSFFont generalFont = wb.createFont();
        generalFont.setFontHeightInPoints((short) 10);
        generalStyle.setWrapText(true);
        generalStyle.setFont(generalFont);
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            XSSFRow row = sheet.getRow(i) == null ? sheet.createRow(i) : sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                XSSFCell xssfCell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                xssfCell.setCellStyle(generalStyle);
            }
        }
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格
     * @return String 单元格的值
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == 0 && DateUtil.isCellDateFormatted(cell)) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return String.valueOf(cell.getStringCellValue());
        }
        return "";
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet  页
     * @param row    行下标
     * @param column 列下标
     * @return String 值
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                Row fRow = sheet.getRow(firstRow);
                Cell fCell = fRow.getCell(firstColumn);
                return getCellValue(fCell);
            }
        }
        return null;
    }

    /**
     * 是不是合并单元格的首行
     *
     * @param sheet  页
     * @param row    行下标
     * @param column 列下标
     * @return boolean
     */
    public static boolean isMergedRegionFirstRow(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return row == firstRow;
            }
        }
        return true;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet  页
     * @param row    行下标
     * @param column 列下标
     * @return boolean
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置Excel 浮点数可做金额等数据统计
     *
     * @param cell  单元格类
     * @param value 传入的值
     */
    public static void setExcelValue(XSSFCell cell, Object value, XSSFCellStyle style) {
        if (value == null) {
            cell.setCellValue("");
        } else {
            if (value instanceof Integer || value instanceof Long) {
                cell.setCellValue(Long.parseLong(value.toString()));
            } else if (value instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) value).setScale(1, RoundingMode.HALF_UP).doubleValue());
            } else if (value instanceof Date) {
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value));
            } else {
                cell.setCellValue(value.toString());
            }
            cell.setCellStyle(style);
        }
    }

    /**
     * 合并列相同的数据(前提为已排序)
     *
     * @param sheet   页
     * @param columns 要合并的列的序号(从0开始)
     */
    public static void merge(XSSFSheet sheet, int... columns) {
        String startValue = sheet.getRow(0).getCell(columns[1]).getStringCellValue();
        int firstRow = 0;
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            String thisValue = sheet.getRow(i).getCell(columns[1]).getStringCellValue();
            if (!thisValue.equals(startValue)) {
                for (int column : columns) {
                    sheet.addMergedRegion(new CellRangeAddress(firstRow, i - 1, column, column));
                }
                startValue = thisValue;
                firstRow = i;
            }
            if (i == sheet.getLastRowNum()) {
                for (int column : columns) {
                    sheet.addMergedRegion(new CellRangeAddress(firstRow, i, column, column));
                }
            }
        }
    }

    /**
     * 标题样式设置
     *
     * @param wb    文件
     * @param sheet 页
     * @param row   标题列
     */
    public static void setTitleStyle(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row, XSSFCellStyle titleStyle) {
        if (titleStyle == null) {
            //标题样式设置
            titleStyle = wb.createCellStyle();
            titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            //字体
            Font titleFont = wb.createFont();
            titleFont.setFontHeightInPoints((short) 13);
            titleStyle.setFont(titleFont);

            //全边框
            titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
            titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
            titleStyle.setBorderRight(CellStyle.BORDER_THIN);
            titleStyle.setBorderTop(CellStyle.BORDER_THIN);
            //背景色
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
        }
        row.setHeight((short) 1000);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(titleStyle);
            sheet.setColumnWidth(i, 44000 / row.getLastCellNum());
        }
    }
}

package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
                            titles.add(k, String.valueOf(cell));
                        } else {
                            titles.add(k, "");
                            break;
//
                        }
                    }
                    continue;
                } else {
                    String str[] = new String[titles.size() - 1];

                    for (int j = beginColumn; j < titles.size() - 1; j++) {
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
     * @param titles    标题
     * @param values    内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(int startRow, String sheetName, List<String> titles, String[][] values) {

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
            if (width < 255 * 256) {
                sheet.setColumnWidth(i, width < 3000 ? 3000 : width);
            } else {
                sheet.setColumnWidth(i, 5000);
            }
            if ("户籍地".equals(titles.get(i))) {
                sheet.setColumnWidth(i, 8000);
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

    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[],
                                          String sheetName, String mergeColumnNames[], String title, String fileName) {
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        Workbook wb;
        // 创建excel工作簿
        if (isExcel2003) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        // 创建第一个sheet（页），并命名
        //        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        //Sheet sheet = wb.createSheet("预约看房导出");
        Sheet sheet = wb.createSheet(sheetName);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        Short start = 0;
        Row titleRow = null;
        if (!StringUtils.isEmpty(title)) {
            start++;
            // 创建第1行
            titleRow = sheet.createRow(0);
        }
        Row mergeRow = null;
        if (mergeColumnNames != null) {
            start++;
            // 创建第2行
            mergeRow = sheet.createRow(1);
        }
        // 创建第start行
        Row row = sheet.createRow(start);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
            if (titleRow != null) {
                Cell cell01 = titleRow.createCell(i);
                cell01.setCellStyle(cs);
            }
            if (mergeRow != null) {
                Cell cell02 = mergeRow.createCell(i);
                cell02.setCellStyle(cs);
            }
        }
        // 设置标题
        if (titleRow != null) {
            Cell cell = titleRow.createCell(0);
            cell.setCellValue(title);
            cell.setCellStyle(cs);
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, keys.length - 1);
            sheet.addMergedRegion(region);
        }
        if (mergeRow != null) {
            // 设置一级列名
            int mergeStart = 0;
            for (int i = 0; i < mergeColumnNames.length; i++) {
                Cell cell = mergeRow.createCell(mergeStart);
                String mColName = mergeColumnNames[i];
                String[] mColArr = mColName.split("\\$\\|");
                cell.setCellStyle(cs);
                if (mColArr.length == 2) {
                    cell.setCellValue(mColArr[0]);
                    int lastCol = mergeStart + Integer.parseInt(mColArr[1]) - 1;
                    CellRangeAddress region = new CellRangeAddress(1, 1, mergeStart, lastCol);
                    sheet.addMergedRegion(region);
                    mergeStart = lastCol + 1;
                } else {
                    cell.setCellValue(mColName);
                    mergeStart++;
                }
            }
        }
        //数值型金额格式
        CellStyle cellStyleTmp_number = wb.createCellStyle();
        cellStyleTmp_number.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cellStyleTmp_number.setFont(f2);
        cellStyleTmp_number.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleTmp_number.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleTmp_number.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleTmp_number.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleTmp_number.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleTmp_number.setVerticalAlignment(CellStyle.ALIGN_CENTER);

        //年月日 日期格式
        CellStyle cellStyleTmp_date = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyleTmp_date.setDataFormat(format.getFormat("yyyy年m月d日"));
        cellStyleTmp_date.setFont(f2);
        cellStyleTmp_date.setBorderLeft(CellStyle.ALIGN_CENTER);
        cellStyleTmp_date.setBorderRight(CellStyle.ALIGN_CENTER);
        cellStyleTmp_date.setBorderTop(CellStyle.ALIGN_CENTER);
        cellStyleTmp_date.setBorderBottom(CellStyle.ALIGN_CENTER);
        cellStyleTmp_date.setAlignment(CellStyle.ALIGN_CENTER);

        //设置每行每列的值
        for (int i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(start + i + 1);
            row1.setHeight((short) 300);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                Object tmpValue = list.get(i).get(keys[j]);
                if (tmpValue instanceof BigDecimal) {
//                    cell.setCellValue(tmpValue == null ? 0.00D : ((BigDecimal) tmpValue).doubleValue());
                    cell.setCellValue(String.valueOf(tmpValue));
                    cell.setCellStyle(cellStyleTmp_number);
                } else if (tmpValue instanceof Date) {
                    cell.setCellValue((Date) tmpValue);
                    cell.setCellStyle(cellStyleTmp_date);
                } else if (tmpValue instanceof Long || tmpValue instanceof Integer) {
                    cell.setCellStyle(cs2);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(String.valueOf(tmpValue));
                } else {
                    cell.setCellValue(tmpValue == null ? " " : tmpValue.toString());
                    cell.setCellStyle(cs2);
                }
            }
        }
        sheet.shiftRows(0, 1, 1);
        sheet.createRow(0).createCell(0).setCellValue(sheetName);
        sheet.getRow(0).getCell(0).setCellStyle(cs);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheet.getRow(sheet.getLastRowNum()).getLastCellNum() - 1));
        saveFile(wb, fileName);
        return wb;
    }

    private static void saveFile(Workbook wb, String fileName) {
        try {
            //将文件输出本地
            FileOutputStream output = new FileOutputStream(fileName);
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<Map<String, String>>> getResult(MultipartFile file, int row) {
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            //判断file后缀名
            Map<String, List<Map<String, String>>> result = new HashMap<>();
            if (fileName.endsWith(".xls")) {
                result = getExcelDataToGroup2003(row, inputStream);
            } else if (fileName.endsWith(".xlsx")) {
                result = getExcelDataToGroup2007(row, inputStream);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数组的数据转成集合  2003版本
     *
     * @param rows
     * @param fis
     * @return
     */
    public static Map<String, List<Map<String, String>>> getExcelDataToGroup2003(int rows, InputStream fis) {
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        try {
            //创建对excel的应用
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis);
            //获取都有多少页
            int numberOfSheets = hssfWorkbook.getNumberOfSheets();
            for (int sheetNumber = 0; sheetNumber < numberOfSheets; sheetNumber++) {
                HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetNumber);
                String sheetName = sheet.getSheetName();
                //获取excel有多少行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                log.info("该文件共有" + physicalNumberOfRows + "行");
                //获取有多少行
                if (physicalNumberOfRows > rows) {
                    HSSFRow row = sheet.getRow(rows - 1);
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    log.info("共有" + physicalNumberOfCells + "列");
                    List<Map<String, String>> maps = new ArrayList<>();
                    for (int i = rows; i < physicalNumberOfRows; i++) {
                        HSSFRow sheetRow = sheet.getRow(i);
                        if (sheetRow != null) {
                            Map<String, String> map = new HashMap<>();
                            boolean flag = true;
                            for (int j = 0; j < physicalNumberOfCells; j++) {
                                String cellValue = row.getCell(j).getStringCellValue();
                                HSSFCell cell = sheetRow.getCell(j);
                                if (cell == null) {
                                    map.put(cellValue, null);
                                } else {
                                    if (cell.getCellType() == 0 && org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                        map.put(cellValue, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
                                    } else if (cell.getCellType() == 0) {
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        map.put(cellValue, df.format(cell.getNumericCellValue()));
                                    } else {
                                        map.put(cellValue, cell.toString().trim());
                                    }
                                }
                            }
                            if (flag) {
                                maps.add(map);
                            }
                        }
                    }
                    result.put(sheetName, maps);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将数组的数据转成二维数组  2007版本以上
     *
     * @param rows
     * @param fis
     * @return
     */
    public static Map<String, List<Map<String, String>>> getExcelDataToGroup2007(int rows, InputStream fis) {
        Map<String, List<Map<String, String>>> result = new HashMap<>();
//        List<String[]> strings = new ArrayList<>();
        try {
            //创建对excel的应用
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
            //获取都有多少页
            int numberOfSheets = xssfWorkbook.getNumberOfSheets();
            for (int sheetNumber = 0; sheetNumber < numberOfSheets; sheetNumber++) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNumber);
                String sheetName = sheet.getSheetName();
                //获取excel有多少行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                log.info("该文件共有" + physicalNumberOfRows + "行");
                if (physicalNumberOfRows > rows) {
                    XSSFRow row = sheet.getRow(rows - 1);
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    log.info("共有" + physicalNumberOfCells + "列");
                    List<Map<String, String>> maps = new ArrayList<>();
                    for (int i = rows; i < physicalNumberOfRows; i++) {
                        XSSFRow sheetRow = sheet.getRow(i);
                        Map<String, String> map = new HashMap<>();
                        map.put("row", String.valueOf(i));
                        boolean flag = true;
                        for (int j = 0; j < physicalNumberOfCells; j++) {
                            int cellType = row.getCell(j).getCellType();
                            String cellValue = "";
                            if (cellType == 1) {
                                cellValue = row.getCell(j).getStringCellValue();
                            } else if (cellType == 0) {
                                cellValue = row.getCell(j).getNumericCellValue() + "";
                            }
                            XSSFCell cell = sheetRow.getCell(j);
                            if (cell == null) {
                                map.put(cellValue, null);
                            } else {
                                if (cell.getCellType() == 0 && org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    map.put(cellValue, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
                                } else {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    String s = cell.getStringCellValue().trim();
                                    if ("检查对象名称".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("机构名称".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("姓名".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("所属辖区".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("标题".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("摄像机ID".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("危险源名称".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if ("隐患类型".equals(cellValue)) {
                                        if (StringUtils.isEmpty(s)) {
                                            flag = false;
                                            break;
                                        }
                                    }
                                    map.put(cellValue, s);
                                }
                            }
                        }
                        if (flag) {
                            maps.add(map);
                        }
                    }
                    result.put(sheetName, maps);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

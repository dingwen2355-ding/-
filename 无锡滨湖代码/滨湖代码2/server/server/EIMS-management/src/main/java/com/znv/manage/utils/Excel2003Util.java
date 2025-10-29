package com.znv.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Slf4j
public class Excel2003Util {

    public static Workbook createWorkBook(List<Map<String, String>> list, String[] keys,
                                          String columnNames[], String model) {
        return createWorkBook(list, keys, columnNames, model, new ArrayList<>());
    }

    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, String>> list, String[] keys,
                                          String columnNames[], String model, List<String> picKeys) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        //        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        //Sheet sheet = wb.createSheet("预约看房导出");
        Sheet sheet = wb.createSheet(model);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

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

        //      Font f3=wb.createFont();
        //      f3.setFontHeightInPoints((short) 10);
        //      f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
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

        //年月日 日期格式
        CellStyle cellStyleTmp_date = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyleTmp_date.setDataFormat(format.getFormat("yyyy年m月d日"));
        cellStyleTmp_date.setFont(f2);
        cellStyleTmp_date.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setAlignment(CellStyle.ALIGN_CENTER);

        //设置每行每列的值 
        for (int i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i + 1);
            row1.setHeight((short) 300);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                Object tmpValue = list.get(i).get(keys[j]);

                if (picKeys.contains(keys[j]) && !StringUtils.isEmpty(tmpValue)) {
                    insertPic(tmpValue.toString(), (HSSFWorkbook) wb, (HSSFSheet) sheet, cell);
                    continue;
                }

                if (tmpValue instanceof BigDecimal) {
                    cell.setCellValue(tmpValue == null ? 0.00D : ((BigDecimal) tmpValue).doubleValue());

                    cell.setCellStyle(cellStyleTmp_number);
                } else if (tmpValue instanceof Date) {
                    cell.setCellValue((Date) tmpValue);
                    cell.setCellStyle(cellStyleTmp_date);
                } else {
                    cell.setCellValue(tmpValue == null ? " " : tmpValue.toString());
                    cell.setCellStyle(cs2);
                }
            }
        }
        return wb;
    }

    private static void insertPic(String url, HSSFWorkbook hSSFWorkbook, HSSFSheet sheet, Cell cell) {
        BufferedImage bufferImg = null;
        ByteArrayOutputStream byteArrayOut = null;
        try {
            byteArrayOut = new ByteArrayOutputStream();
            //加载图片
            URL urlPic = new URL(url);
            bufferImg = ImageIO.read(urlPic);
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            /**
             dx1 - the x coordinate within the first cell.//定义了图片在第一个cell内的偏移x坐标，既左上角所在cell的偏移x坐标，一般可设0
             dy1 - the y coordinate within the first cell.//定义了图片在第一个cell的偏移y坐标，既左上角所在cell的偏移y坐标，一般可设0
             dx2 - the x coordinate within the second cell.//定义了图片在第二个cell的偏移x坐标，既右下角所在cell的偏移x坐标，一般可设0
             dy2 - the y coordinate within the second cell.//定义了图片在第二个cell的偏移y坐标，既右下角所在cell的偏移y坐标，一般可设0
             col1 - the column (0 based) of the first cell.//第一个cell所在列，既图片左上角所在列
             row1 - the row (0 based) of the first cell.//图片左上角所在行
             col2 - the column (0 based) of the second cell.//图片右下角所在列
             row2 - the row (0 based) of the second cell.//图片右下角所在行
             */
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(),
                    cell.getRowIndex(), (short) (cell.getColumnIndex() + 1), cell.getRowIndex() + 1);
            //插入图片
            patriarch.createPicture(anchor, hSSFWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            byteArrayOut.flush();
            bufferImg.flush();
            byteArrayOut.close();
            bufferImg = null;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            org.apache.tomcat.util.http.fileupload.IOUtils.closeQuietly(byteArrayOut);
        }
    }

    /**
     * 创建excel文档，
     *
     * @param list         数据
     * @param keys         list中map的key数组集合
     * @param columnNames  excel的列名
     * @param columnWidths 列宽度
     * @param model        sheet名称
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys,
                                          String[] columnNames, Short[] columnWidths, String model) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        //        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        //Sheet sheet = wb.createSheet("预约看房导出");
        Sheet sheet = wb.createSheet(model);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, columnWidths[i]);
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);
        row.setHeight((short) 400);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 11);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        //      Font f3=wb.createFont();
        //      f3.setFontHeightInPoints((short) 10);
        //      f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
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

        //年月日 日期格式
        CellStyle cellStyleTmp_date = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyleTmp_date.setDataFormat(format.getFormat("yyyy年m月d日"));
        cellStyleTmp_date.setFont(f2);
        cellStyleTmp_date.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setAlignment(CellStyle.ALIGN_CENTER);

        //设置每行每列的值 
        for (int i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i + 1);
            row1.setHeight((short) 300);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                Object tmpValue = list.get(i).get(keys[j]);
                if (tmpValue instanceof BigDecimal) {
                    cell.setCellValue(tmpValue == null ? 0.00D : ((BigDecimal) tmpValue).doubleValue());

                    cell.setCellStyle(cellStyleTmp_number);
                } else if (tmpValue instanceof Date) {
                    cell.setCellValue((Date) tmpValue);
                    cell.setCellStyle(cellStyleTmp_date);
                } else {
                    cell.setCellValue(tmpValue == null ? " " : tmpValue.toString());
                    cell.setCellStyle(cs2);
                }
            }
        }
        return wb;
    }

    /**
     * 创建excel文档，
     *
     * @param list         数据
     * @param keys         list中map的key数组集合
     * @param columnNames  excel的列名
     * @param columnWidths 列宽度
     * @param model        sheet名称
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys,
                                          String[] columnNames, Short[] columnWidths, String model,
                                          List<Map<String, Object>> list2, String[] keys2,
                                          String[] columnNames2, Short[] columnWidths2, String model2) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        //        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        //Sheet sheet = wb.createSheet("预约看房导出");
        Sheet sheet = wb.createSheet(model);
        Sheet sheet2 = wb.createSheet(model2);
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, columnWidths[i]);
        }
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys2.length; i++) {
            sheet2.setColumnWidth((short) i, columnWidths2[i]);
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);
        row.setHeight((short) 400);

        // 创建第一行
        Row row2 = sheet2.createRow((short) 0);
        row2.setHeight((short) 400);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 11);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        //      Font f3=wb.createFont();
        //      f3.setFontHeightInPoints((short) 10);
        //      f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
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
        }
        //设置列名
        for (int i = 0; i < columnNames2.length; i++) {
            Cell cell = row2.createCell(i);
            cell.setCellValue(columnNames2[i]);
            cell.setCellStyle(cs);
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

        //年月日 日期格式
        CellStyle cellStyleTmp_date = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyleTmp_date.setDataFormat(format.getFormat("yyyy年m月d日"));
        cellStyleTmp_date.setFont(f2);
        cellStyleTmp_date.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleTmp_date.setAlignment(CellStyle.ALIGN_CENTER);

        //设置每行每列的值 
        for (int i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i + 1);
            row1.setHeight((short) 300);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                Object tmpValue = list.get(i).get(keys[j]);
                if (tmpValue instanceof BigDecimal) {
                    cell.setCellValue(tmpValue == null ? 0.00D : ((BigDecimal) tmpValue).doubleValue());

                    cell.setCellStyle(cellStyleTmp_number);
                } else if (tmpValue instanceof Date) {
                    cell.setCellValue((Date) tmpValue);
                    cell.setCellStyle(cellStyleTmp_date);
                } else {
                    cell.setCellValue(tmpValue == null ? " " : tmpValue.toString());
                    cell.setCellStyle(cs2);
                }
            }
        }

        //设置每行每列的值 
        for (int i = 0; i < list2.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet2.createRow(i + 1);
            row1.setHeight((short) 300);
            // 在row行上创建一个方格
            for (int j = 0; j < keys2.length; j++) {
                Cell cell = row1.createCell(j);
                Object tmpValue = list2.get(i).get(keys2[j]);
                if (tmpValue instanceof BigDecimal) {
                    cell.setCellValue(tmpValue == null ? 0.00D : ((BigDecimal) tmpValue).doubleValue());

                    cell.setCellStyle(cellStyleTmp_number);
                } else if (tmpValue instanceof Date) {
                    cell.setCellValue((Date) tmpValue);
                    cell.setCellStyle(cellStyleTmp_date);
                } else {
                    cell.setCellValue(tmpValue == null ? " " : tmpValue.toString());
                    cell.setCellStyle(cs2);
                }
            }
        }

        return wb;
    }

    /**
     * 解析excel文件
     *
     * @param file        excel文件
     * @param startNumber 开始解析的列行--列名行  从0开始
     * @return 内容对象
     */
    public static List<Map<String, Object>> excelImport(MultipartFile file, int startNumber) {
        if (null == file || startNumber < 0) {
            return null;
        }
        //excel内容从 开始解析的列行下一行开始
        int contentNumber = startNumber + 1;
        String fileName = file.getOriginalFilename();
        List<Map<String, Object>> reuslt = new ArrayList<>();
        try {
            //errorMsg为错误日志信息
            String errorMsg = "";
            String br = "<br/>";
            //如果上传文件格式不正确，直接打印日志，不做任何操作
            if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                errorMsg += br + "上传文件格式不正确";
            } else {
                boolean isExcel2003 = true;
                if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                    isExcel2003 = false;
                }
                InputStream is = file.getInputStream();
                Workbook wb = null;
                if (isExcel2003) {
                    wb = new HSSFWorkbook(is);
                } else {
                    wb = new XSSFWorkbook(is);
                }
                Sheet sheet = wb.getSheetAt(0);
                if (sheet == null) {
                    errorMsg += br + "上传文件为空";
                } else {
                    int totalRows = sheet.getPhysicalNumberOfRows();
                    int totalCells = 0;
                    //得到Excel的列数(前提是有行数)，从第二行算起
                    if (totalRows >= 2 && sheet.getRow(1) != null) {
                        totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
                    }
                    Map<String, Object> map = null;
                    //这一行是字段明细行
                    Row row0 = sheet.getRow(startNumber);
                    for (int r = contentNumber; r <= sheet.getLastRowNum(); r++) {
                        Row row = sheet.getRow(r);
                        if (row == null) {
                            errorMsg += br + "第" + (r + 1) + "行数据有问题，请仔细检查！";
                            continue;
                        }
                        map = new HashMap();
                        for (int c = 0; c < totalCells; c++) {
                            map.put(String.valueOf(row0.getCell(c)), String.valueOf(row.getCell(c)) == "null" ? "" : String.valueOf(row.getCell(c)));
                        }
                        reuslt.add(map);
                    }
                }
            }
            if (!"".equals(errorMsg)) {
                log.info(errorMsg);
            }
        } catch (IOException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return reuslt;
    }

    /**
     * 自动伸缩列（如非必要，请勿打开此方法比较耗内存）
     *
     * @param sheet
     * @param size  列数量
     */
    private static void autoSizeColumn(Sheet sheet, Integer size) {
        for (int j = 0; j < size; j++) {
            sheet.autoSizeColumn(j);
        }
    }
}

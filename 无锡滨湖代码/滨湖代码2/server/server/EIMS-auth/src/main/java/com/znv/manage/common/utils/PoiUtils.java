package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.lawInspect.InspectStandard;
import com.znv.manage.common.bean.lawInspect.LawInspect;
import com.znv.manage.common.bean.lawInspect.LawInspectObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Slf4j
public class PoiUtils {
    /**
     * 获取工作表的信息map
     *
     * @param hssfWorkbook
     * @param i
     * @return
     */
    public static Map<String, List<Map<String, String>>> getSheetInfo(XSSFWorkbook hssfWorkbook, int i) {
        Map<String, List<Map<String, String>>> mapSheet = new HashMap<String, List<Map<String, String>>>();
        //获取该工作表
        XSSFSheet sheet = hssfWorkbook.getSheetAt(i);
        String sheetName = sheet.getSheetName();
        if (i == 0) {
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            XSSFRow row0 = sheet.getRow(0);
            //获取列名
            if (row0 != null && physicalNumberOfRows > 1) {
                //获取第一行的列数
                int physicalNumberOfCells = row0.getPhysicalNumberOfCells();
                List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
                //获取第一列的信息
                List<String> topInfo = getTopInfo(physicalNumberOfCells, row0);
                //遍历数据
                for (int j = 1; j < physicalNumberOfRows; j++) {
                    XSSFRow rowJ = sheet.getRow(j);
                    if (rowJ == null) {
                        break;
                    }
                    Map<String, String> map = new HashMap<>();
                    //获取列的值
                    for (int k = 0; k < topInfo.size(); k++) {
                        //获取该行长度
                        int rowJLength = rowJ.getPhysicalNumberOfCells();
                        if (k < rowJLength) {
                            String cellValue = getCellValue(rowJ.getCell(k));
                            map.put(topInfo.get(k), cellValue);
                        } else {
                            map.put(topInfo.get(k), "");
                        }
                    }
                    mapList.add(map);
                }
                mapSheet.put(sheetName, mapList);
                return mapSheet;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }


    public static String getCellInfo(XSSFSheet sheet, int i, int j) {
        XSSFRow rowJ = sheet.getRow(i);
        XSSFCell value = rowJ.getCell(j);
        return getCellValue(value);
    }

    /**
     * 根据第一行来获取集合对象
     *
     * @param cell
     * @param row0
     * @return
     */
    public static List<String> getTopInfo(int cell, XSSFRow row0) {
        List<String> topNames = new ArrayList<String>();
        for (int i = 0; i < cell; i++) {
            XSSFCell cellKey = row0.getCell(i);
            String cellValue = getCellValue(cellKey);
            topNames.add(cellValue);
        }
        return topNames;
    }

    /**
     * 获取该列的值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(XSSFCell cell) {
        //修改列属性为String
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    public static String readPicture(XSSFSheet sheet, String dirName, String name) {
        String dirPath = "F:\\image\\merchants\\" + dirName + "\\";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = "";
        FileOutputStream out = null;
        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFPictureData pictureData = pic.getPictureData();
                    byte[] data = pictureData.getData();
                    String ext = pictureData.suggestFileExtension();
                    try {
                        if (ext.equals("jpeg")) {
                            filePath = dirPath + name + ".jpg";
                            out = new FileOutputStream(filePath);
                            out.write(data);
                            out.close();
                        }
                        if (ext.equals("png")) {
                            filePath = dirPath + name + ".png";
                            out = new FileOutputStream(filePath);
                            out.write(data);
                            out.close();
                        }
                    } catch (Exception e) {
                        log.error(e.toString());
                    } finally {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                log.error(e.toString());
                            }
                        }
                    }
                }
            }
        }
        return filePath.replace("F:\\image\\merchants\\", "http://172.30.152.135:8237/ZNVIMAGE/merchants/");
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
//        List<String[]> strings = new ArrayList<>();
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
//                            HSSFCell cellDh = sheetRow.getCell(0);
//                            if(cellDh!=null&& StringUtils.isNotBlank(cellDh.toString())){
                            Map<String, String> map = new HashMap<>();
//                                String[] s=new String[physicalNumberOfCells];
                            boolean flag = true;
                            for (int j = 0; j < physicalNumberOfCells; j++) {
                                String cellValue = row.getCell(j).getStringCellValue();
                                HSSFCell cell = sheetRow.getCell(j);
                                if (cell == null) {
//                                        flag=false;
//                                        break;
                                    map.put(cellValue, null);
                                } else {
                                    if (cell.getCellType() == 0 && DateUtil.isCellDateFormatted(cell)) {
                                        map.put(cellValue, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
//                                            s[j]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                                    } else if (cell.getCellType() == 0) {
                                        DecimalFormat df = new DecimalFormat("0.00");
                                        map.put(cellValue, df.format(cell.getNumericCellValue()));
//                                            s[j]=df.format(cell.getNumericCellValue());
                                    } else {
                                        map.put(cellValue, cell.toString().trim());
//                                            s[j]=cell.toString();
                                    }
                                }
                            }
                            if (flag) {
                                maps.add(map);
//                                    strings.add(s);
                            }
//                            }
                        }
                    }
                    result.put(sheetName, maps);
                }

            }
        } catch (Exception e) {
            log.error(e.toString());
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
//                        XSSFCell cellDh = sheetRow.getCell(0);
//                        if(cellDh!=null&&StringUtils.isNotBlank(cellDh.toString())){
                        Map<String, String> map = new HashMap<>();
//                            String[] s=new String[physicalNumberOfCells];
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
//                                        flag=false;
//                                        break;
                                map.put(cellValue, null);
                            } else {
                                boolean addFlag = false;
                                if (cell.getCellType() == Cell.CELL_TYPE_BLANK && j <= 5) {
                                    continue;
                                }
                                if (cell.getCellType() == 0 && DateUtil.isCellDateFormatted(cell)) {
                                    map.put(cellValue, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
//                                        s[j]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                                    if (addFlag) {
                                        XSSFCell cell1 = sheetRow.getCell(j);
                                        cell1.setCellValue(cell.getDateCellValue());
                                    }
                                } else if (cell.getCellType() == 0 && "物资数量".equals(cellValue)) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    map.put(cellValue, df.format(cell.getNumericCellValue()));
//                                        s[j]=df.format(cell.getNumericCellValue());
                                    if (addFlag) {
                                        XSSFCell cell1 = sheetRow.getCell(j);
                                        cell1.setCellValue(cell.getNumericCellValue());
                                    }
                                    cell.setCellValue(cell.getNumericCellValue());
                                } else {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    map.put(cellValue, cell.getStringCellValue().trim());
//                                        s[j]=cell.toString();
                                    if (addFlag) {
                                        XSSFCell cell1 = sheetRow.getCell(j);
                                        cell1.setCellValue(cell.getStringCellValue().trim());
                                    }
                                }
                            }
                        }
                        if (flag) {
                            maps.add(map);
//                                strings.add(s);
                        }
//                        }
                    }
                    result.put(sheetName, maps);
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return result;
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
            log.error(e.toString());
        }
        return null;
    }

    public static void saveExcel(String bgginTime, String endTime, String fileName, List<Map<String, String>> maps) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("总账");
        XSSFCellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        XSSFRow row1 = sheet.createRow(0);
        XSSFCell cell = row1.createCell(0);
        row1.setHeight((short) (800));
//        sheet.setDefaultColumnWidth(50);
        for (int i = 0; i < 6; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
        cell.setCellValue("应急管理局应急物资出入库明细表\n统计周期：" + bgginTime + "~" + endTime);
        cell.setCellStyle(cellStyle);
        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0);

        XSSFCell cell1 = row2.createCell(0);
        cell1.setCellValue("救援类型");
        cell1.setCellStyle(cellStyle);
        XSSFCell cell2 = row2.createCell(1);
        cell2.setCellValue("物资编号");
        cell2.setCellStyle(cellStyle);
        XSSFCell cell3 = row2.createCell(2);
        cell3.setCellValue("物资名称");
        cell3.setCellStyle(cellStyle);
        XSSFCell cell4 = row2.createCell(3);
        cell4.setCellValue("物资品牌");
        cell4.setCellStyle(cellStyle);
        XSSFCell cell5 = row2.createCell(4);
        cell5.setCellValue("物资型号");
        cell5.setCellStyle(cellStyle);
//        HSSFCell cell6 = row2.createCell(5);
//        cell6.setCellValue("生产日期");
//        cell6.setCellStyle(cellStyle);
        XSSFCell cell7 = row2.createCell(5);
        cell7.setCellValue("计量单位");
        cell7.setCellStyle(cellStyle);
        int stockLocation = 6;
        XSSFCell rCell = row2.createCell(stockLocation);
        rCell.setCellValue("入库统计");
        rCell.setCellStyle(cellStyle);
        //生成第三行
        XSSFRow row3 = sheet.createRow(2);

        //处理数据
        Map<String, Integer> stockMap = new HashMap();
        for (Map<String, String> map : maps) {
            String s = map.get("STOCK_NAME");
            stockMap.put(s, 0);
        }

        for (String s : stockMap.keySet()) {
            row3.createCell(stockLocation).setCellValue(s);
            row3.createCell(stockLocation + stockMap.size()).setCellValue(s);
            row3.createCell(stockLocation + stockMap.size() * 2).setCellValue(s);
            stockMap.put(s, stockLocation);
            stockLocation++;

        }
        XSSFCell cCell = row2.createCell(6 + stockMap.size());
        cCell.setCellValue("出库统计");
        cCell.setCellStyle(cellStyle);
        XSSFCell kCell = row2.createCell(6 + stockMap.size() * 2);
        kCell.setCellValue("库存统计");
        kCell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 6 + stockMap.size() - 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 6 + stockMap.size(), 5 + stockMap.size() * 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 6 + stockMap.size() * 2, 5 + stockMap.size() * 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 5, 5));
//        sheet.addMergedRegion(new CellRangeAddress(1,2,6,6));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6 + stockMap.size() * 3 - 1));
        int i = 1;
        int stuffLocation = 3;
        String helpNameComp = "";
        int helpNameFirst = 3;
        LinkedHashMap<String, List<Map>> tm = CommonUtils.changeMap3(maps, "tm");
        for (String s : tm.keySet()) {
            List<Map> lists = tm.get(s);
            Map stuffMap = lists.get(0);
            XSSFRow row = sheet.createRow(stuffLocation);
            String stuffName = stuffMap.get("stuffName").toString();
            String brand = stuffMap.get("brand").toString();
            String model = stuffMap.get("model").toString();
            String stuffUnit = stuffMap.get("stuffUnit").toString();
            row.createCell(1).setCellValue(i);
            row.createCell(2).setCellValue(stuffName);
            row.createCell(3).setCellValue(brand);
            row.createCell(4).setCellValue(model);
//            row.createCell(5).setCellValue(buyDate);
            row.createCell(5).setCellValue(stuffUnit);
            String helpName = stuffMap.get("helpName").toString();
            if (stuffLocation == 3) {
                XSSFCell cell8 = row.createCell(0);
                cell8.setCellValue(helpName);
                cell8.setCellStyle(cellStyle);
                helpNameComp = helpName;
            }
            for (Map map : lists) {
                String operationType = map.get("operation_type").toString();
                String stockName = map.get("STOCK_NAME").toString();
                Integer stockLocation2 = stockMap.get(stockName);
                String num = map.get("num").toString();
                String stuffAllNum = map.get("stuffAllNum").toString();
                if ("1".equals(operationType)) {
                    row.createCell(stockLocation2 + stockMap.size()).setCellValue(num);
                } else if ("2".equals(operationType)) {
                    row.createCell(stockLocation2).setCellValue(num);
                } else if ("3".equals(operationType)) {
                    row.createCell(stockLocation2 + stockMap.size() * 2).setCellValue(stuffAllNum);
                }
            }
            if (!helpNameComp.equals(helpName)) {
                sheet.addMergedRegion(new CellRangeAddress(helpNameFirst, stuffLocation - 1, 0, 0));
                helpNameComp = helpName;
                XSSFCell cell8 = row.createCell(0);
                cell8.setCellValue(helpName);
                cell8.setCellStyle(cellStyle);
                helpNameFirst = stuffLocation;
            }
            stuffLocation++;
            i++;
        }
        sheet.addMergedRegion(new CellRangeAddress(helpNameFirst, stuffLocation - 1, 0, 0));
        saveFile(wb, fileName);
    }

    public static void saveDutyExcel(String fileName, List<Map<String, String>> maps) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("值班信息");
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("日期");
        cell1.setCellStyle(cellStyle);
        XSSFCell cell2 = row1.createCell(1);
        cell2.setCellValue("带班领导");
        cell2.setCellStyle(cellStyle);
        XSSFCell cell3 = row1.createCell(2);
        cell3.setCellValue("综合协调岗");
        cell3.setCellStyle(cellStyle);
        XSSFCell cell4 = row1.createCell(3);
        cell4.setCellStyle(cellStyle);
        XSSFCell cell5 = row1.createCell(4);
        cell5.setCellValue("信息报送岗");
        cell5.setCellStyle(cellStyle);
        XSSFCell cell6 = row1.createCell(5);
        cell6.setCellStyle(cellStyle);
        XSSFCell cell7 = row1.createCell(6);
        cell7.setCellValue("跟班司机");
        cell7.setCellStyle(cellStyle);
        XSSFCell cell8 = row1.createCell(7);
        cell8.setCellValue("消防值守岗");
        cell8.setCellStyle(cellStyle);

        XSSFCell cell9 = row1.createCell(8);
        cell9.setCellValue("备注");
        cell9.setCellStyle(cellStyle);

        //生成第二行
        XSSFRow row2 = sheet.createRow(1);
        XSSFCell cell_21 = row2.createCell(0);
        cell_21.setCellStyle(cellStyle);

        XSSFCell cell_22 = row2.createCell(1);
        cell_22.setCellStyle(cellStyle);

        XSSFCell cell_23 = row2.createCell(2);
        cell_23.setCellValue("主值班");
        cell_23.setCellStyle(cellStyle);

        XSSFCell cell_24 = row2.createCell(3);
        cell_24.setCellValue("白班");
        cell_24.setCellStyle(cellStyle);

        XSSFCell cell_25 = row2.createCell(4);
        cell_25.setCellValue("主值班");
        cell_25.setCellStyle(cellStyle);

        XSSFCell cell_26 = row2.createCell(5);
        cell_26.setCellValue("白班");
        cell_26.setCellStyle(cellStyle);

        XSSFCell cell_27 = row2.createCell(6);
        cell_27.setCellStyle(cellStyle);

        XSSFCell cell_28 = row2.createCell(7);
        cell_28.setCellStyle(cellStyle);

        XSSFCell cell_29 = row2.createCell(8);
        cell_29.setCellStyle(cellStyle);

        CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(region);

        CellRangeAddress region2 = new CellRangeAddress(0, 1, 1, 1);
        sheet.addMergedRegion(region2);

        CellRangeAddress region3 = new CellRangeAddress(0, 1, 6, 6);
        sheet.addMergedRegion(region3);

        CellRangeAddress region4 = new CellRangeAddress(0, 1, 7, 7);
        sheet.addMergedRegion(region4);

        CellRangeAddress region5 = new CellRangeAddress(0, 1, 8, 8);
        sheet.addMergedRegion(region5);

        CellRangeAddress region6 = new CellRangeAddress(0, 0, 2, 3);
        sheet.addMergedRegion(region6);

        CellRangeAddress region7 = new CellRangeAddress(0, 0, 4, 5);
        sheet.addMergedRegion(region7);

        //处理数据
        for (int i = 0; i < maps.size(); i++) {
            //创建单元格，并赋值
            XSSFRow row = sheet.createRow(i + 2);
            Map<String, String> map = maps.get(i);
            row.createCell((short) 0).setCellValue(map.get("date"));
            row.createCell((short) 1).setCellValue(map.get("nameA"));
            row.createCell((short) 2).setCellValue(map.get("nameB"));
            row.createCell((short) 3);
            row.createCell((short) 4).setCellValue(map.get("nameC"));
            row.createCell((short) 5);
            row.createCell((short) 6).setCellValue(map.get("nameD"));
            row.createCell((short) 7).setCellValue(map.get("nameE"));
            row.createCell((short) 8);
        }


        saveFile(wb, fileName);
    }


    public static void saveExcel(Map<String, Object> maps, String fileName) {
        //创建excel
        XSSFWorkbook wb = new XSSFWorkbook();
        for (String s : maps.keySet()) {
            Map<String, Object> stockInfos = (Map<String, Object>) maps.get(s);
            for (String s1 : stockInfos.keySet()) {
                if ("r".equals(s1) || "c".equals(s1)) {
                    //获取时间
                    String sheetName = s;
                    if ("r".equals(s1)) {
                        sheetName = s + "入库";
                    } else {
                        sheetName = s + "出库";
                    }
                    List<Map> stockInfo = (List<Map>) stockInfos.get(s1);
                    XSSFSheet sheet = wb.createSheet(sheetName);
                    LinkedHashMap<String, Integer> opTime = new LinkedHashMap<>();
                    Map<String, List<Map>> stuffInfo = CommonUtils.changeMapT(stockInfo, "stuffId");
                    int timeIndex = 4;
                    for (Map map : stockInfo) {
                        String operationTime = map.get("operationTime").toString();
                        if (!opTime.containsKey(operationTime)) {
                            opTime.put(operationTime, timeIndex);
                            timeIndex++;
                        }
                    }
                    createSheetModel(sheet, opTime, sheetName, s1);
                    //物资详情
                    int i = 2;
                    for (String stuffId : stuffInfo.keySet()) {
                        List<Map> maps1 = stuffInfo.get(stuffId);
                        //取出时间物资时间一致的
                        Map<String, List<Map>> stuffCode = CommonUtils.changeMapT(maps1, "stuffCode");
                        sheet.addMergedRegion(new CellRangeAddress(1, 1, i, i + stuffCode.size() - 1));
                        for (String s2 : stuffCode.keySet()) {
                            List<Map> maps2 = stuffCode.get(s2);
                            Map map1 = maps2.get(0);
                            String stuffName = map1.get("stuffName").toString();
                            String stuffBrand = map1.get("stuffBrand").toString();
                            String stuffModel = map1.get("stuffModel").toString();
                            String stuffUnit = map1.get("stuffUnit") == null ? "" : map1.get("stuffUnit").toString();
                            XSSFRow row1 = sheet.getRow(1);
                            XSSFRow row2 = sheet.getRow(2);
                            XSSFRow row3 = sheet.getRow(3);
                            XSSFRow row4 = sheet.getRow(4);
                            XSSFCell cell = row1.createCell(i);
                            cell.setCellValue(stuffName + "(" + stuffUnit + ")");
                            XSSFCell cell1 = row2.createCell(i);
                            cell1.setCellValue(stuffBrand);
                            XSSFCell cell2 = row3.createCell(i);
                            cell2.setCellValue(stuffModel);
                            for (Map map : maps2) {
                                String stuffOperationNum = map.get("stuffOperationNum").toString();
                                String operationTime = map.get("operationTime").toString();
                                //获取脚标
                                Integer integer = opTime.get(operationTime);
                                XSSFRow row = sheet.getRow(integer);
                                XSSFCell cell4 = row.createCell(i);
                                cell4.setCellValue(stuffOperationNum);
                            }
                            i++;
                        }
                    }
                }
                if ("t".equals(s1)) {
                    XSSFSheet sheet = wb.createSheet(s + "汇总");
                    List<Map> stockInfo = (List<Map>) stockInfos.get(s1);
                    LinkedHashMap<String, Integer> operationRTime = new LinkedHashMap<>();
                    LinkedHashMap<String, Integer> operationCTime = new LinkedHashMap<>();
                    int rIndex = 4;
                    int cIndex = 4;
                    //提取时间
                    for (Map map : stockInfo) {
                        String operationTime = map.get("operationTime").toString();
                        String operationtype = map.get("operationtype").toString();

                        if ("2".equals(operationtype)) {
                            if (!operationRTime.containsKey(operationTime)) {
                                operationRTime.put(operationTime, rIndex);
                                rIndex++;
                            }
                        } else {
                            if (!operationCTime.containsKey(operationTime)) {
                                operationCTime.put(operationTime, cIndex);
                                cIndex++;
                            }
                        }
                    }
                    for (String s2 : operationCTime.keySet()) {
                        operationCTime.put(s2, operationCTime.get(s2) + operationRTime.size() + 1);
                    }
                    //创建模板
                    createSheetModelT(sheet, operationRTime, operationCTime, s);
                    //数据转成
                    Map<String, List<Map>> stuffInfo = CommonUtils.changeMapT(stockInfo, "stuffId");
                    int rowIndex = 4;
                    for (String s2 : stuffInfo.keySet()) {
                        List<Map> maps1 = stuffInfo.get(s2);
                        Map<String, List<Map>> stuffCode = CommonUtils.changeMapT(maps1, "stuffCode");
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + stuffCode.size() - 1, 0, 0));
                        for (String s3 : stuffCode.keySet()) {
                            List<Map> maps2 = stuffCode.get(s3);
                            Map map1 = maps2.get(0);
                            //填充数据
                            String stuffName = map1.get("stuffName").toString();
                            String stuffBrand = map1.get("stuffBrand").toString();
                            String stuffModel = map1.get("stuffModel").toString();
                            String stuffUnit = map1.get("stuffUnit") == null ? "" : map1.get("stuffUnit").toString();
                            XSSFRow row = sheet.createRow(rowIndex);
                            XSSFCell cell = row.createCell(0);
                            cell.setCellValue(stuffName);
                            XSSFCell cell1 = row.createCell(1);
                            cell1.setCellValue(stuffBrand);
                            XSSFCell cell2 = row.createCell(2);
                            cell2.setCellValue(stuffModel);
                            XSSFCell cell3 = row.createCell(3);
                            cell3.setCellValue(stuffUnit);
                            BigDecimal kc = new BigDecimal(BigInteger.ZERO);
                            BigDecimal totalR = new BigDecimal(BigInteger.ZERO);
                            BigDecimal totalC = new BigDecimal(BigInteger.ZERO);
                            for (Map map : maps2) {
                                String operationtype = map.get("operationtype").toString();
                                String operationTime = map.get("operationTime").toString();
                                BigDecimal stuffOperationNum = map.get("stuffOperationNum") == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(map.get("stuffOperationNum").toString());
                                kc = map.get("stockStuffNum") == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(map.get("stockStuffNum").toString());
                                if ("2".equals(operationtype)) {
                                    Integer integer = operationRTime.get(operationTime);
                                    XSSFCell cell4 = row.createCell(integer);
                                    cell4.setCellValue(stuffOperationNum.doubleValue());
                                    totalR = totalR.add(stuffOperationNum);
                                } else {
                                    Integer integer = operationCTime.get(operationTime);
                                    XSSFCell cell4 = row.createCell(integer);
                                    cell4.setCellValue(stuffOperationNum.doubleValue());
                                    totalC = totalC.add(stuffOperationNum);
                                }

                            }
                            XSSFCell cell4 = row.createCell(3 + operationRTime.size() + 1);
                            cell4.setCellValue(totalR.doubleValue());
                            XSSFCell cell5 = row.createCell(3 + operationRTime.size() + operationCTime.size() + 2);
                            cell5.setCellValue(totalC.doubleValue());
                            XSSFCell cell6 = row.createCell(3 + operationRTime.size() + operationCTime.size() + 3);
                            cell6.setCellValue(kc.doubleValue());
                            rowIndex++;
                        }
                    }
//                    sheet.removeColumnBreak(4);
//                    sheet.isColumnHidden(4);
//                    sheet.setColumnHidden(3,true);
                }
            }
        }
        setStyle(wb);
        saveFile(wb, fileName);
    }

    private static void createSheetModel(XSSFSheet sheet, LinkedHashMap<String, Integer> opTime, String stockName, String type) {
        XSSFRow row1 = sheet.createRow(0);
        XSSFCell cell = row1.createCell(0);
        if ("r".equals(type)) {
            cell.setCellValue("物资入库情况统计表");
        } else {
            cell.setCellValue("物资出库情况统计表");
        }
        cell.setCellValue("物资入库情况统计表");
        XSSFRow row = sheet.createRow(1);
        XSSFRow rowx = sheet.createRow(2);
        XSSFRow rowxx = sheet.createRow(3);
        XSSFRow rowxxx = sheet.createRow(4);
        XSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("日期");
        XSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("物资来源");
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
        for (String s : opTime.keySet()) {
            XSSFRow row2 = sheet.createRow(opTime.get(s));
            XSSFCell cell3 = row2.createCell(0);
            XSSFCell cell4 = row2.createCell(1);
            cell3.setCellValue(s);
            cell4.setCellValue(stockName);
        }
    }

    private static void createSheetModelT(XSSFSheet sheet, Map<String, Integer> rTimes, Map<String, Integer> cTimes, String stockName) {
        XSSFRow row1 = sheet.createRow(0);
        XSSFCell cell = row1.createCell(0);
        cell.setCellValue("物资出入库情况统计表");
        XSSFRow row = sheet.createRow(1);
        XSSFRow row2 = sheet.createRow(2);
        XSSFRow row3 = sheet.createRow(3);
        XSSFCell cell1 = row.createCell(0);
        XSSFCell cell2 = row.createCell(1);
        XSSFCell cell3 = row.createCell(2);
        XSSFCell cell4 = row.createCell(3);
        cell1.setCellValue("物资名称");
        cell2.setCellValue("物资品牌");
        cell3.setCellValue("物资型号");
        cell4.setCellValue("计量单位");
        XSSFCell cell5 = row.createCell(4);
        XSSFCell cell6 = row.createCell(4 + rTimes.size() + 1);
        XSSFCell cell7 = row.createCell(4 + rTimes.size() + cTimes.size() + 2);
        XSSFCell cell8 = row2.createCell(4);
        XSSFCell cell9 = row2.createCell(4 + rTimes.size() + 1);
        XSSFCell cell10 = row2.createCell(4 + rTimes.size() + cTimes.size() + 2);
        cell5.setCellValue("入库数量");
        cell6.setCellValue("出库数量");
        cell7.setCellValue("库存数量");
        cell8.setCellValue(stockName);
        cell9.setCellValue(stockName);
        cell10.setCellValue(stockName);
        XSSFCell cellR = row.createCell(4 + rTimes.size());
        cellR.setCellValue("累积入库");
        XSSFCell cellC = row.createCell(4 + rTimes.size() + cTimes.size() + 1);
        cellC.setCellValue("累积出库");
        for (String s : rTimes.keySet()) {
            Integer integer = rTimes.get(s);
            XSSFCell cell11 = row3.createCell(integer);
            cell11.setCellValue(s);
        }
        for (String s : cTimes.keySet()) {
            Integer integer = cTimes.get(s);
            XSSFCell cell11 = row3.createCell(integer);
            cell11.setCellValue(s);
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 3 + (rTimes.size() == 0 ? 1 : rTimes.size())));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5 + (rTimes.size() == 0 ? 1 : rTimes.size()), 4 + (rTimes.size() == 0 ? 1 : rTimes.size()) + (cTimes.size() == 0 ? 1 : cTimes.size())));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 4 + rTimes.size()));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5 + (rTimes.size() == 0 ? 1 : rTimes.size()), 5 + (rTimes.size() == 0 ? 1 : rTimes.size()) + (cTimes.size() == 0 ? 1 : cTimes.size())));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6 + (rTimes.size() == 0 ? 1 : rTimes.size()) + (cTimes.size() == 0 ? 1 : cTimes.size())));
    }

    private static void saveFile(HSSFWorkbook wb, String fileName) {
        try {
            //输出Excel文件
            //将workbook转换为流的形式
            InputStream input = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            input = new ByteArrayInputStream(os.toByteArray());

            //将文件输出本地
            FileOutputStream output = new FileOutputStream(fileName);
            wb.write(output);
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private static void saveFile(XSSFWorkbook wb, String fileName) {
        try {
            //将文件输出本地
            FileOutputStream output = new FileOutputStream(fileName);
            wb.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private static void setStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
        int numberOfSheets = wb.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            XSSFSheet sheetAt = wb.getSheetAt(i);
            int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
            for (int j = 0; j < physicalNumberOfRows; j++) {
                XSSFRow row = sheetAt.getRow(j);
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                for (int k = 0; k < physicalNumberOfCells; k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell != null) {
                        cell.setCellStyle(cellStyle);
                        if (j == 1) {
                            int length = cell.getStringCellValue().getBytes().length;
                            sheetAt.setColumnWidth(k, length * 256);
                        }
                    }
                }
            }
        }
    }

    public static List<Map<String, Object>> readDutyExcel(MultipartFile file) throws IOException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        InputStream fileStream = file.getInputStream();
        XSSFWorkbook wb = new XSSFWorkbook(fileStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        List<Map<String, Object>> list = new ArrayList<>();
        int rows = sheet.getLastRowNum() + 1;
        for (int i = 2; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                Cell date = row.getCell(0);
                if (date == null || date.getCellType() == Cell.CELL_TYPE_BLANK) {
                    continue;
                }

                for (int j = 1; j < 9; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        Map<String, Object> map = new HashMap<>(3);
                        map.put("dutyTime", sd.format(date.getDateCellValue()));
                        map.put("personName", cell.getStringCellValue().replaceAll(" ", ""));
                        switch (j) {
                            case 1:
                                map.put("groupId", "8");
                                break;
                            case 2:
                            case 3:
                                map.put("groupId", "2");
                                break;
                            case 4:
                            case 5:
                                map.put("groupId", "4");
                                break;
                            case 6:
                                map.put("groupId", "6");
                                break;
                            case 7:
                                map.put("groupId", "3");
                                break;
                            case 8:
                                map.put("groupId", "9");
                                break;
                        }
                        list.add(map);
                    }
                }

            }
        }
        return list;
    }

    public static void saveStuffCountRaw(String fileName, List<Map<String, String>> list) {
        //所有物资集合
        List<Map<String, String>> stuffList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        int stockIndex = 1;
        //把list里相同仓库的合并为一个JSONObject,格式：
/*    obj1:{
                "area": "临淄区",
                "gx": "区县级",
                "stockName": "临淄区防疫物资储备库",
                "stockIndex": 1,
                "stuffs": {
                    "医用防护服\n-未分类": {
                        "stuffNumber": 178900.00,
                        "agreementNumber": 0
                     },
                     "手术帽\n-未分类": {
                        "stuffNumber": 176000.00,
                        "agreementNumber": 0
                    },
                    ...
                 }
            }*/
        for (Map<String, String> map : list) {
            Map<String, String> stuffMap = new HashMap<>();
            stuffMap.put("stuff", map.get("stuff"));
            stuffMap.put("stuffType", map.get("stuffType"));
            stuffMap.put("parentType", map.get("parentType"));
            stuffList.add(stuffMap);
            JSONObject numbers = new JSONObject();
            numbers.put("stuffNumber", map.get("stuffNumber"));
            numbers.put("agreementNumber", map.get("agreementNumber"));
            //存在返回下标，不存在返回-1
            int arrayIndex = hasStock(jsonArray, map.get("stockName"));
            if (arrayIndex != -1) {
                JSONObject stockInfo = jsonArray.getJSONObject(arrayIndex);
                JSONObject stuffs = stockInfo.getJSONObject("stuffs");
                stuffs.put(map.get("stuff"), numbers);
            } else {
                JSONObject jsonObject = new JSONObject();
                JSONObject stuffs = new JSONObject();
                stuffs.put(map.get("stuff"), numbers);
                jsonObject.put("stockIndex", stockIndex);
                jsonObject.put("gx", map.get("gx"));
                jsonObject.put("area", map.get("area"));
                jsonObject.put("stockName", map.get("stockName"));
                jsonObject.put("stockType", map.get("stockType"));
                jsonObject.put("stuffs", stuffs);
                jsonArray.add(jsonObject);
                stockIndex++;
            }
        }
        //物资名list排序和去重
        stuffList = stuffList.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(map -> map.get("stuff")))),
                        ArrayList::new)
                );
        stuffList = stuffList.stream()
                .sorted(Comparator.comparing(map -> map.get("parentType")))
                .sorted(Comparator.comparing(map -> map.get("stuffType")))
                .collect(Collectors.toList());

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");

        //第一行：标题
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell00 = row0.createCell(0);
        row0.setHeight((short) 500);
        cell00.setCellValue("2022年淄博市应急物资装备统计表");
        //合并标题单元格
        CellRangeAddress typeRegion = new CellRangeAddress(0, 0, 0, 14);
        sheet.addMergedRegion(typeRegion);

        //第6行：列字段名填充
        XSSFRow row2 = sheet.createRow(2);
        XSSFRow row3 = sheet.createRow(3);
        XSSFRow row4 = sheet.createRow(4);
        //合并345行前5列
        for (int i = 0; i < 6; i++) {
            sheet.addMergedRegion(new CellRangeAddress(2, 4, i, i));
        }
        row4.setHeight((short) 1000);
        XSSFCell cell20 = row2.createCell(0);
        cell20.setCellValue("序号");
        XSSFCell cell21 = row2.createCell(1);
        cell21.setCellValue("管辖");
        XSSFCell cell22 = row2.createCell(2);
        cell22.setCellValue("管理层级");
        XSSFCell cell23 = row2.createCell(3);
        cell23.setCellValue("仓库类型");
        XSSFCell cell24 = row2.createCell(4);
        cell24.setCellValue("仓库名称");
        //设置仓库名称列的长度
        sheet.setColumnWidth(4, 5000);
        XSSFCell cell25 = row2.createCell(5);
        cell25.setCellValue("储备方式");

        //第三四五行物资大小类别+名称+规格动态数据填充
        int row2Index = 6;
        for (Map<String, String> map : stuffList) {
            String parentType = map.get("parentType");
            String stuffType = map.get("stuffType");
            XSSFCell cell2 = row2.createCell(row2Index);
            cell2.setCellValue(parentType);
            XSSFCell cell3 = row3.createCell(row2Index);
            cell3.setCellValue(stuffType);
            sheet.setColumnWidth(row2Index, 2500);
            XSSFCell stuffCell = row4.createCell(row2Index);
            stuffCell.setCellValue(map.get("stuff"));
            row2Index++;
        }

        //第三四行物资大小类合并
        String cell26 = row2.getCell(6).getStringCellValue();
        String cell36 = row3.getCell(6).getStringCellValue();
        int startParentType = 6;
        int startStuffType = 6;
        for (int i = 7; i < row2Index; i++) {
            String cell2i = row2.getCell(i).getStringCellValue();
            String cell3i = row3.getCell(i).getStringCellValue();
            if (cell2i.equals(cell26)) {
                if (i == row2Index - 1) {
                    sheet.addMergedRegion(new CellRangeAddress(2, 2, startParentType, i));
                }
            } else {
                sheet.addMergedRegion(new CellRangeAddress(2, 2, startParentType, i - 1));
                cell26 = cell2i;
                startParentType = i;
            }
            if (cell3i.equals(cell36)) {
                if (i == row2Index - 1) {
                    sheet.addMergedRegion(new CellRangeAddress(3, 3, startStuffType, i));
                }
            } else {
                sheet.addMergedRegion(new CellRangeAddress(3, 3, startStuffType, i - 1));
                cell36 = cell3i;
                startStuffType = i;
            }
        }
        CellStyle areaStyle = wb.createCellStyle();
        areaStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        areaStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        Font areaFont = wb.createFont();
        areaFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        areaStyle.setFont(areaFont);

        //填充动态数据
        String area;
        //暂存区小计所在行,用于后面的统计
        int areaRowIndex;
        List<Integer> areaRowIndexList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject stockInfo = jsonArray.getJSONObject(i);
            area = stockInfo.getString("area");
            //xxx区域小计填充
            if (i == 0 || !jsonArray.getJSONObject(i - 1).get("area").equals(area)) {
                areaRowIndex = sheet.getLastRowNum() + 1;
                areaRowIndexList.add(areaRowIndex);
                XSSFRow areaRow = sheet.createRow(areaRowIndex);
                XSSFRow areaRow2 = sheet.createRow(areaRowIndex + 1);
                XSSFCell areaCell = areaRow.createCell(0);
                areaCell.setCellValue(area + "小计");
                areaCell.setCellStyle(areaStyle);
                CellRangeAddress areaRegion = new CellRangeAddress(areaRow.getRowNum(), areaRow2.getRowNum(), 0, 4);
                sheet.addMergedRegion(areaRegion);
                XSSFCell stuffNumberCell = areaRow.createCell(5);
                stuffNumberCell.setCellValue("实物储备");
                XSSFCell agreementNumberCell = areaRow2.createCell(5);
                agreementNumberCell.setCellValue("协议储备");
            }
            //仓库数据填充
            XSSFRow stockRow = sheet.createRow(sheet.getLastRowNum() + 1);
            XSSFRow stockRow2 = sheet.createRow(sheet.getLastRowNum() + 1);
            XSSFCell indexCell = stockRow.createCell(0);
            indexCell.setCellValue(stockInfo.getIntValue("stockIndex"));
            XSSFCell gxCell = stockRow.createCell(1);
            gxCell.setCellValue(stockInfo.getString("gx"));
            XSSFCell stockAreaCell = stockRow.createCell(2);
            stockAreaCell.setCellValue(stockInfo.getString("area"));
            XSSFCell stockTypeCell = stockRow.createCell(3);
            stockTypeCell.setCellValue(stockInfo.getString("stockType"));
            XSSFCell stockNameCell = stockRow.createCell(4);
            stockNameCell.setCellValue(stockInfo.getString("stockName"));
            XSSFCell stuffNumberCell = stockRow.createCell(5);
            stuffNumberCell.setCellValue("实物储备");
            XSSFCell agreementNumberCell = stockRow2.createCell(5);
            agreementNumberCell.setCellValue("协议储备");

            //合并仓库数据单元格
            for (int j = 0; j < 5; j++) {
                sheet.addMergedRegion(new CellRangeAddress(stockRow.getRowNum(), stockRow2.getRowNum(), j, j));
            }

            //储备数据填充
            if (StringUtils.isNotBlank(stockInfo.get("stuffs").toString())) {
                JSONObject stuffs = stockInfo.getJSONObject("stuffs");
                //双循环匹配
                for (Map.Entry<String, Object> entry : stuffs.entrySet()) {
                    String key = entry.getKey();
                    JSONObject value = (JSONObject) entry.getValue();
                    int index = 6;
                    for (Map<String, String> map : stuffList) {
                        if (map.get("stuff").equals(key)) {
                            XSSFCell stuffNumberValueCell = stockRow.createCell(index);
                            stuffNumberValueCell.setCellValue(value.getString("stuffNumber"));
                            XSSFCell agreementNumberValueCell = stockRow2.createCell(index);
                            agreementNumberValueCell.setCellValue(value.getString("agreementNumber"));
                            break;
                        }
                        index++;
                    }
                }
            }

        }

        //区小计统计
        areaRowIndexList.add(sheet.getLastRowNum());
        for (int i = 1; i < areaRowIndexList.size(); i++) {
            int lastIndex = areaRowIndexList.get(i - 1);
            int index = areaRowIndexList.get(i);
            XSSFRow stuffNumCountRow = sheet.getRow(lastIndex);
            XSSFRow agreementCountRow = sheet.getRow(lastIndex + 1);
            //行
            for (int j = 6; j < row2Index; j++) {
                double agreementNumberCount = 0;
                double stuffNumberCount = 0;
                //列
                for (int k = lastIndex; k < index; k += 2) {
                    XSSFRow stuffNumXssfRow = sheet.getRow(k);
                    XSSFRow agreementXssfRow = sheet.getRow(k + 1);
                    XSSFCell agreementXssfRowCell = agreementXssfRow.getCell(j);
                    XSSFCell stuffNumXssfRowCell = stuffNumXssfRow.getCell(j);
                    if (agreementXssfRowCell != null && agreementXssfRowCell.getCellType() == Cell.CELL_TYPE_STRING) {
                        agreementNumberCount += Double.parseDouble(agreementXssfRowCell.getStringCellValue());
                    }
                    if (stuffNumXssfRowCell != null && stuffNumXssfRowCell.getCellType() == Cell.CELL_TYPE_STRING) {
                        stuffNumberCount += Double.parseDouble(stuffNumXssfRowCell.getStringCellValue());
                    }
                }
                stuffNumCountRow.createCell(j).setCellValue(stuffNumberCount);
                agreementCountRow.createCell(j).setCellValue(agreementNumberCount);
            }
        }

        //总统计
        areaRowIndexList.remove(areaRowIndexList.size() - 1);
        sheet.shiftRows(5, sheet.getLastRowNum(), 2, true, false);
        XSSFRow allCountRow = sheet.createRow(5);
        XSSFRow allCountRow2 = sheet.createRow(6);
        XSSFCell countCell = allCountRow.createCell(0);
        countCell.setCellValue("总计");
        countCell.setCellStyle(areaStyle);
        CellRangeAddress allCountRegion = new CellRangeAddress(allCountRow.getRowNum(), allCountRow2.getRowNum(), 0, 4);
        sheet.addMergedRegion(allCountRegion);
        XSSFCell stuffNumberCell = allCountRow.createCell(5);
        stuffNumberCell.setCellValue("实物储备");
        XSSFCell agreementNumberCell = allCountRow2.createCell(5);
        agreementNumberCell.setCellValue("协议储备");
        for (int i = 6; i < row4.getLastCellNum(); i++) {
            int stuffCount = 0;
            int agreementCount = 0;
            for (int index : areaRowIndexList) {
                XSSFRow stuffNumCountRow = sheet.getRow(index + 2);
                XSSFRow agreementCountRow = sheet.getRow(index + 3);
                XSSFCell stuffNumCountCell = stuffNumCountRow.getCell(i);
                XSSFCell agreementCountCell = agreementCountRow.getCell(i);
                if (stuffNumCountCell != null && stuffNumCountCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    stuffCount += stuffNumCountCell.getNumericCellValue();
                }
                if (agreementCountCell != null && agreementCountCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    agreementCount += agreementCountCell.getNumericCellValue();
                }
            }
            XSSFCell cell = allCountRow.createCell(i);
            cell.setCellValue(stuffCount);
            XSSFCell cell2 = allCountRow2.createCell(i);
            cell2.setCellValue(agreementCount);
        }

        //全局样式设置
        XSSFCellStyle generalStyle = wb.createCellStyle();
        //全边框
        generalStyle.setBorderBottom(BorderStyle.THIN);
        generalStyle.setBorderLeft(BorderStyle.THIN);
        generalStyle.setBorderRight(BorderStyle.THIN);
        generalStyle.setBorderTop(BorderStyle.THIN);
        // 居中
        generalStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        generalStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //字体
        XSSFFont generalFont = wb.createFont();
        generalFont.setFontHeightInPoints((short) 10);
        generalStyle.setWrapText(true);
        generalStyle.setFont(generalFont);
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            XSSFRow row = sheet.getRow(i) == null ? sheet.createRow(i) : sheet.getRow(i);
            for (int j = 0; j < row2Index; j++) {
                XSSFCell xssfCell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                if (xssfCell.getCellType() == Cell.CELL_TYPE_STRING && xssfCell.getStringCellValue().contains("小计")) {
                    continue;
                }
                xssfCell.setCellStyle(generalStyle);
            }
        }

        //标题样式设置
        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleStyle.setFont(titleFont);
        cell00.setCellStyle(titleStyle);

        //解决前端预览展示空行导致预览不到最后一行的bug
        XSSFRow lastRow = sheet.createRow(sheet.getLastRowNum() + 1);
        XSSFCell lastCell = lastRow.createCell(0);
        lastCell.setCellValue(" ");
        saveFile(wb, fileName);
    }

    public static int hasStock(JSONArray jsonArray, String stockName) {
        if (!jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (stockName.equals(obj.get("stockName"))) {
                    return i;
                }
            }
        }
        return -1;
    }

    //监管执法-检查标准导出
    public static void exportInspectStandard(String fileName, List<InspectStandard> list) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Excel");

        //标题样式设置
        XSSFCellStyle titleStyle = wb.createCellStyle();
        //居中
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //填充灰色
        titleStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //设置字体
        Font titleFont = wb.createFont();
        //字体大小
        titleFont.setFontHeightInPoints((short) 14);
        //字体
        titleFont.setFontName("宋体");
        //加粗
        titleFont.setBoldweight((short) 700);
        titleStyle.setFont(titleFont);

        //标题数据填充
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < 8; i++) {
            //宽度
            sheet.setColumnWidth(i, 4000);
        }
        titleRow.setHeight((short) 400);
        XSSFCell cell00 = titleRow.createCell(0);
        cell00.setCellValue("标准");
        cell00.setCellStyle(titleStyle);
        XSSFCell cell01 = titleRow.createCell(1);
        cell01.setCellValue("检查项目");
        cell01.setCellStyle(titleStyle);
        XSSFCell cell02 = titleRow.createCell(2);
        cell02.setCellValue("检查内容");
        cell02.setCellStyle(titleStyle);
        XSSFCell cell03 = titleRow.createCell(3);
        cell03.setCellValue("执法依据");
        cell03.setCellStyle(titleStyle);
        XSSFCell cell04 = titleRow.createCell(4);
        cell04.setCellValue("方式方法");
        cell04.setCellStyle(titleStyle);
        XSSFCell cell05 = titleRow.createCell(5);
        cell05.setCellValue("处置依据");
        cell05.setCellStyle(titleStyle);
        XSSFCell cell06 = titleRow.createCell(6);
        cell06.setCellValue("检查层面");
        cell06.setCellStyle(titleStyle);
        XSSFCell cell07 = titleRow.createCell(7);
        cell07.setCellValue("所属机构");
        cell07.setCellStyle(titleStyle);

        //数据填充
        for (InspectStandard inspectStandard : list) {
            XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            XSSFCell cellX0 = row.createCell(0);
            cellX0.setCellValue(inspectStandard.getStandard());
            XSSFCell cellX1 = row.createCell(1);
            cellX1.setCellValue(inspectStandard.getProject());
            XSSFCell cellX2 = row.createCell(2);
            cellX2.setCellValue(inspectStandard.getContent());
            XSSFCell cellX3 = row.createCell(3);
            cellX3.setCellValue(inspectStandard.getLawBasic());
            XSSFCell cellX4 = row.createCell(4);
            cellX4.setCellValue(inspectStandard.getMethod());
            XSSFCell cellX5 = row.createCell(5);
            cellX5.setCellValue(inspectStandard.getDisposalBasis());
            XSSFCell cellX6 = row.createCell(6);
            cellX6.setCellValue(inspectStandard.getInspectLevel());
            XSSFCell cellX7 = row.createCell(7);
            cellX7.setCellValue(inspectStandard.getPrecinctName());
        }
        saveFile(wb, fileName);
    }

    //监管执法-执法检查导出
    public static void exportLawInspect(String fileName, List<LawInspect> list) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");
        //标题数据填充
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < 15; i++) {
            //宽度
            sheet.setColumnWidth(i, 2500);
        }
        titleRow.setHeight((short) 300);
        XSSFCell cell00 = titleRow.createCell(0);
        cell00.setCellValue("计划/专项名称");
        XSSFCell cell01 = titleRow.createCell(1);
        cell01.setCellValue("检查来源");
        XSSFCell cell02 = titleRow.createCell(2);
        cell02.setCellValue("检查对象");
        XSSFCell cell03 = titleRow.createCell(3);
        cell03.setCellValue("执法人员");
        XSSFCell cell04 = titleRow.createCell(4);
        cell04.setCellValue("执法人员");
        XSSFCell cell05 = titleRow.createCell(5);
        cell05.setCellValue("检查日期");
        XSSFCell cell06 = titleRow.createCell(6);
        cell06.setCellValue("检查项目");
        XSSFCell cell07 = titleRow.createCell(7);
        cell07.setCellValue("检查内容");
        XSSFCell cell08 = titleRow.createCell(8);
        cell08.setCellValue("违法行为");
        XSSFCell cell09 = titleRow.createCell(9);
        cell09.setCellValue("处置依据");
        XSSFCell cell010 = titleRow.createCell(10);
        cell010.setCellValue("隐患类别");
        XSSFCell cell011 = titleRow.createCell(11);
        cell011.setCellValue("处置类型");

        //数据填充
        int rowIndex = 1;
        int childRowIndex = rowIndex;
        boolean isFirst;
        for (LawInspect lawInspect : list) {
            XSSFRow row = sheet.createRow(rowIndex);
            isFirst = true;
            row.createCell(0).setCellValue(lawInspect.getPlanName());
            row.createCell(1).setCellValue(lawInspect.getInspectSource());
            row.createCell(2).setCellValue(lawInspect.getObjectName());
            row.createCell(3).setCellValue(lawInspect.getEnforcerFirstName());
            row.createCell(4).setCellValue(lawInspect.getEnforcerSecondName());
            row.createCell(5).setCellValue(lawInspect.getInspectDate());
            List<LawInspectObject> childList = lawInspect.getLawInspectObjectList();
            for (LawInspectObject lawInspectObject : childList) {
                XSSFRow childRow = isFirst ? row : sheet.createRow(childRowIndex);
                childRow.createCell(6).setCellValue(lawInspectObject.getProjectName());
                childRow.createCell(7).setCellValue(lawInspectObject.getCheckContent());
                childRow.createCell(8).setCellValue(lawInspectObject.getProblemDesc());
                childRow.createCell(9).setCellValue(lawInspectObject.getDisposalBasis());
                childRow.createCell(10).setCellValue(lawInspectObject.getHiddenDangerType());
                childRow.createCell(11).setCellValue(lawInspectObject.getEnforceLawMeasureItem1());
                childRowIndex++;
                isFirst = false;
            }
            for (int i = 0; i < 6; i++) {
                CellRangeAddress rangeAddress = new CellRangeAddress(rowIndex, childRowIndex - 1, i, i);
                sheet.addMergedRegion(rangeAddress);
            }
            rowIndex = childRowIndex;
        }

        //全局样式设置
        XSSFCellStyle generalStyle = wb.createCellStyle();
        //全边框
        generalStyle.setBorderBottom(BorderStyle.THIN);
        generalStyle.setBorderLeft(BorderStyle.THIN);
        generalStyle.setBorderRight(BorderStyle.THIN);
        generalStyle.setBorderTop(BorderStyle.THIN);
        // 居中
        generalStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //垂直居中
        generalStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            XSSFRow row = sheet.getRow(i) == null ? sheet.createRow(i) : sheet.getRow(i);
            for (int j = 0; j < 12; j++) {
                XSSFCell xssfCell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                if (xssfCell.getCellType() == Cell.CELL_TYPE_STRING && xssfCell.getStringCellValue().contains("小计")) {
                    continue;
                }
                xssfCell.setCellStyle(generalStyle);
            }
        }
        saveFile(wb, fileName);
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
        if (!org.springframework.util.StringUtils.isEmpty(title)) {
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
                    cell.setCellValue(tmpValue == null ? 0.00D : ((BigDecimal) tmpValue).doubleValue());

                    cell.setCellStyle(cellStyleTmp_number);
                } else if (tmpValue instanceof Date) {
                    cell.setCellValue((Date) tmpValue);
                    cell.setCellStyle(cellStyleTmp_date);
                } else if (tmpValue instanceof Long) {
                    cell.setCellStyle(cs2);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Long) tmpValue);
                } else if (tmpValue instanceof Integer) {
                    cell.setCellStyle(cs2);
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Integer) tmpValue);
                } else {
                    cell.setCellValue(tmpValue == null ? " " : tmpValue.toString());
                    cell.setCellStyle(cs2);
                }
            }
        }
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
            log.error(e.toString());
        }
    }
}

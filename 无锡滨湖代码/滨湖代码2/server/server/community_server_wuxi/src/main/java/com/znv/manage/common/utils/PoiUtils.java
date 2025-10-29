package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PoiUtils {
    /**
     * 获取工作表的信息map
     * @param hssfWorkbook
     * @param i
     * @return
     */
    public static Map<String, List<Map<String, String>>>  getSheetInfo(XSSFWorkbook hssfWorkbook, int i){
        Map<String, List<Map<String, String>>> mapSheet = new HashMap<String, List<Map<String, String>>>();
        //获取该工作表
        XSSFSheet sheet = hssfWorkbook.getSheetAt(i);
        String sheetName = sheet.getSheetName();
        if(i==0){
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            XSSFRow row0 = sheet.getRow(0);
            //获取列名
            if(row0!=null && physicalNumberOfRows>1){
                //获取第一行的列数
                int physicalNumberOfCells = row0.getPhysicalNumberOfCells();
                List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
                //获取第一列的信息
                List<String> topInfo = getTopInfo(physicalNumberOfCells, row0);
                //遍历数据
                for(int j=1;j<physicalNumberOfRows;j++){
                    XSSFRow rowJ = sheet.getRow(j);
                    if(rowJ==null){
                        break;
                    }
                    Map<String, String> map = new HashMap<>();
                    //获取列的值
                    for (int k=0;k<topInfo.size();k++){
                        //获取该行长度
                        int rowJLength = rowJ.getPhysicalNumberOfCells();
                        if(k<rowJLength){
                            String cellValue = getCellValue(rowJ.getCell(k));
                            map.put(topInfo.get(k),cellValue);
                        }else{
                            map.put(topInfo.get(k),"");
                        }
                    }
                    mapList.add(map);
                }
                mapSheet.put(sheetName,mapList);
                return mapSheet;
            }else{
                return null;
            }
        }else{
            return null;
        }

    }




    public static String getCellInfo(XSSFSheet sheet,int i,int j){
        XSSFRow rowJ = sheet.getRow(i);
        XSSFCell value = rowJ.getCell(j);
        return getCellValue(value);
    }

    /**
     * 根据第一行来获取集合对象
     * @param cell
     * @param row0
     * @return
     */
    public static List<String>  getTopInfo(int cell, XSSFRow row0){
        List<String> topNames = new ArrayList<String>();
        for(int i=0;i<cell;i++){
            XSSFCell cellKey = row0.getCell(i);
            String cellValue = getCellValue(cellKey);
            topNames.add(cellValue);
        }
        return topNames;
    }

    /**
     * 获取该列的值
     * @param cell
     * @return
     */
    public static String getCellValue(XSSFCell cell){
        //修改列属性为String
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    public static String readPicture(XSSFSheet sheet,String dirName,String name){
        String dirPath="F:\\image\\merchants\\" +dirName+"\\";
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filePath="";
        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFPictureData pictureData = pic.getPictureData();
                    byte[] data = pictureData.getData();
                    String ext = pictureData.suggestFileExtension();
                    try{
                        if (ext.equals("jpeg")) {
                            filePath=dirPath+name+".jpg";
                            FileOutputStream out = new FileOutputStream(filePath);
                            out.write(data);
                            out.close();
                        }
                        if (ext.equals("png")) {
                            filePath=dirPath+name+".png";
                            FileOutputStream out = new FileOutputStream(filePath);
                            out.write(data);
                            out.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return filePath.replace("F:\\image\\merchants\\","http://172.30.152.135:8237/ZNVIMAGE/merchants/");
    }



    /**
     * 将数组的数据转成集合  2003版本
     * @param rows
     * @param fis
     * @return
     */
    public static Map<String,List<Map<String, String>>> getExcelDataToGroup2003(int rows, InputStream fis){
        Map<String, List<Map<String, String>>> result = new HashMap<>();
//        List<String[]> strings = new ArrayList<>();
        try {
            //创建对excel的应用
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis);
            //获取都有多少页
            int numberOfSheets = hssfWorkbook.getNumberOfSheets();
            for(int sheetNumber=0;sheetNumber<numberOfSheets;sheetNumber++){
                HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetNumber);
                String sheetName = sheet.getSheetName();
                //获取excel有多少行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                log.info("该文件共有"+physicalNumberOfRows+"行");
                //获取有多少行
                if(physicalNumberOfRows>rows){
                    HSSFRow row = sheet.getRow(rows-1);
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    log.info("共有"+physicalNumberOfCells+"列");
                    List<Map<String, String>> maps = new ArrayList<>();
                    for(int i=rows;i<physicalNumberOfRows;i++){
                        HSSFRow sheetRow = sheet.getRow(i);
                        if(sheetRow!=null){
                            HSSFCell cellDh = sheetRow.getCell(0);
                            if(cellDh!=null&& StringUtils.isNotBlank(cellDh.toString())){
                                Map<String, String> map = new HashMap<>();
//                                String[] s=new String[physicalNumberOfCells];
                                boolean flag=true;
                                for(int j=0;j<physicalNumberOfCells;j++){
                                    String cellValue = row.getCell(j).getStringCellValue();
                                    HSSFCell cell = sheetRow.getCell(j);
                                    if(cell==null){
//                                        flag=false;
//                                        break;
                                        map.put(cellValue,null);
                                    }else{
                                        if(cell.getCellType()==0&& DateUtil.isCellDateFormatted(cell)){
                                            map.put(cellValue,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
//                                            s[j]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                                        }else if(cell.getCellType()==0){
                                            DecimalFormat df = new DecimalFormat("0");
                                            map.put(cellValue,df.format(cell.getNumericCellValue()));
//                                            s[j]=df.format(cell.getNumericCellValue());
                                        }else{
                                            map.put(cellValue,cell.toString());
//                                            s[j]=cell.toString();
                                        }
                                    }
                                }
                                if(flag){
                                    maps.add(map);
//                                    strings.add(s);
                                }
                            }
                        }
                    }
                    result.put(sheetName,maps);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将数组的数据转成二维数组  2007版本以上
     * @param rows
     * @param fis
     * @return
     */
    public static Map<String,List<Map<String, String>>> getExcelDataToGroup2007(int rows,InputStream fis){
        Map<String, List<Map<String, String>>> result = new HashMap<>();
//        List<String[]> strings = new ArrayList<>();
        try {
            //创建对excel的应用
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
            //获取都有多少页
            int numberOfSheets = xssfWorkbook.getNumberOfSheets();
            for(int sheetNumber=0;sheetNumber<numberOfSheets;sheetNumber++){
                XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNumber);
                String sheetName = sheet.getSheetName();
                //获取excel有多少行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                log.info("该文件共有"+physicalNumberOfRows+"行");
                if(physicalNumberOfRows>rows){
                    XSSFRow row = sheet.getRow(rows - 1);
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    log.info("共有"+physicalNumberOfCells+"列");
                    List<Map<String, String>> maps = new ArrayList<>();
                    for(int i=rows;i<physicalNumberOfRows;i++){
                        XSSFRow sheetRow = sheet.getRow(i);
                        XSSFCell cellDh = sheetRow.getCell(0);
                        if(cellDh!=null&&StringUtils.isNotBlank(cellDh.toString())){
                            Map<String, String> map = new HashMap<>();
//                            String[] s=new String[physicalNumberOfCells];
                            boolean flag=true;
                            for(int j=0;j<physicalNumberOfCells;j++){
                                int cellType = row.getCell(j).getCellType();
                                String cellValue="";
                                if(cellType==1){
                                    cellValue=row.getCell(j).getStringCellValue();
                                }else if (cellType==0){
                                    System.err.println(i+"-----------"+j);
                                    cellValue = row.getCell(j).getNumericCellValue()+"";
                                }
                                XSSFCell cell = sheetRow.getCell(j);
                                if(cell==null){
//                                        flag=false;
//                                        break;
                                    map.put(cellValue,null);
                                }else{
                                    if(cell.getCellType()==0&& DateUtil.isCellDateFormatted(cell)){
                                        map.put(cellValue,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue()));
//                                        s[j]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                                    }else if(cell.getCellType()==0){
                                        DecimalFormat df = new DecimalFormat("0");
                                        map.put(cellValue,df.format(cell.getNumericCellValue()));
//                                        s[j]=df.format(cell.getNumericCellValue());
                                    }else{
                                        map.put(cellValue,cell.toString());
//                                        s[j]=cell.toString();
                                    }
                                }
                            }
                            if(flag){
                                maps.add(map);
//                                strings.add(s);
                            }
                        }
                    }
                    result.put(sheetName,maps);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, List<Map<String, String>>> getResult(MultipartFile file,int row){
        try{
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            //判断file后缀名
            Map<String, List<Map<String, String>>> result = new HashMap<>();
            if(fileName.endsWith(".xls")){
                result = getExcelDataToGroup2003(row, inputStream);
            }else if(fileName.endsWith(".xlsx")){
                result=getExcelDataToGroup2007(row,inputStream);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

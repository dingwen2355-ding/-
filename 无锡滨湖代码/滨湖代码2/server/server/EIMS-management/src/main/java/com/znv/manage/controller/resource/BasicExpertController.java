package com.znv.manage.controller.resource;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.BasicExpertInfo;
import com.znv.manage.service.LogService;
import com.znv.manage.service.resource.BasicExpertService;
import com.znv.manage.utils.CusAccessUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 应急专家管理
 */
@Api(tags = "应急专家管理")
@RestController
@RequestMapping("basicExpert")
public class BasicExpertController {

    @Autowired
    BasicExpertService basicExpertService;

    @Resource
    LogService logService;

    @ApiOperation("应急专家管理-查询")
    @GetMapping("getBasicExpertList")
    public Result getBasicExpertList(@RequestParam(value = "id", required = false) Integer id,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "sex", required = false) Integer sex,
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "company", required = false) String company,
                                     @RequestParam(value = "expertCategory", required = false) String expertCategory,
                                     @RequestParam(value = "address", required = false) String address,
                                     @RequestParam(value = "planId", required = false) String planId,
                                     @RequestParam(value = "eventId", required = false) String eventId,
                                     @RequestParam(value = "isAll", required = false) String isAll,
                                     @RequestParam(value = "pageStart", required = false) Integer pageStart,
                                     @RequestParam(value = "pageLen", required = false) Integer pageLen,
                                     @RequestParam(value = "userId", required = false) String userId,
                                     @RequestParam(value = "userName", required = false) String userName,
                                     HttpServletRequest request) {
        Result result = basicExpertService.getBasicExpertList(id, name, sex, type,
                title, company, expertCategory, address, planId, eventId, isAll, pageStart, pageLen);
        logService.insertLog("应急专家查询", userId, userName, CusAccessUtil.getIpAddress(request),new Date(),
                null,null,Integer.toString(result.getCode()), result.getMessage());
        return result;
    }


    @ApiOperation("新增应急专家管理")
    @PostMapping("insertBasicExpertInfo")
    public Result insertBasicExpertInfo(@RequestBody BasicExpertInfo basicExpertInfo) {
        return basicExpertService.insertBasicExpertInfo(basicExpertInfo);
    }

    @ApiOperation("修改应急专家管理")
    @PostMapping("updateBasicExpertInfo")
    public Result updateMiniBusinessInfo(@RequestBody BasicExpertInfo basicExpertInfo) {
        return basicExpertService.updateBasicExpertInfo(basicExpertInfo);
    }

    @ApiOperation("删除应急专家管理")
    @PostMapping("deleteBasicExpertInfo")
    public Result deleteBasicExpertInfo(@RequestParam("ids") String ids) {
        return basicExpertService.deleteBasicExpertInfo(ids);
    }

    @ApiOperation("专家类型字典")
    @PostMapping("getBasicExpertType")
    public List<BasicExpertInfo> getBasicExpertType(@RequestParam(value = "name", required = false) String name) {
        return basicExpertService.getBasicExpertType(name);
    }

    @ApiOperation("专家职称字典")
    @PostMapping("getBasicExpertTitle")
    public List<Map<String, Object>> getBasicExpertTitle() {
        return basicExpertService.getBasicExpertTitle();
    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/importExcel")
    public void importExcel(@ApiParam(value = "文件", required = true) MultipartFile file,
                            HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Result result = new Result();
        //如果没有文件就返回报错
        if (file == null) {
            result.setMessage("请上传文件");
            String string = JSONObject.toJSONString(result);
            out.write(string);
            return;
        }
        //文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传文件名============" + fileName);
        Workbook workbook = null;
        try {
            if (fileName.endsWith(".xls")) {
                //2003版本的excel，用.xls结尾
                workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
            } else if (fileName.endsWith(".xlsx")) {
                //2007版本的excel，用.xlsx结尾
                workbook = new XSSFWorkbook(file.getInputStream());//得到工作簿
            } else {
                result.setMessage("请选择Excel文件,如  “xxx.xls”   “xxx.xlsx”");
                String string = JSONObject.toJSONString(result);
                out.write(string);
//                workbook.close();
                return;
            }
        } catch (IOException e) {
            result.setMessage(e.getMessage());
            String string = JSONObject.toJSONString(result);
            e.printStackTrace();
            out.write(string);
//            workbook.close();
            return;
        }
        // 得到 第一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获取多少行
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows <= 1) {
            result.setMessage("Excel内没有数据！");
            String string = JSONObject.toJSONString(result);
            out.write(string);
            return;
        }
        String string = "";
        string = BasicExpertInsert(sheet, rows);
        out.write(string);
    }

    ////4：应急专家管理
    private String BasicExpertInsert(Sheet sheet, int rows) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("上传成功");
        String string = JSONObject.toJSONString(result);
        try {
            Row firstRow = sheet.getRow(0);
            String name = (String) getRightTypeCell(firstRow.getCell(1));//姓名
            String sex = (String) getRightTypeCell(firstRow.getCell(2));//性别
            String idNumber = (String) getRightTypeCell(firstRow.getCell(3));//身份证号
            String phone = (String) getRightTypeCell(firstRow.getCell(4));//电话
            String company = (String) getRightTypeCell(firstRow.getCell(5));//工作单位
            String title = (String) getRightTypeCell(firstRow.getCell(6));//职称
            String address = (String) getRightTypeCell(firstRow.getCell(7));//常住地址
            String type = (String) getRightTypeCell(firstRow.getCell(8));//擅长行业
            String experience = (String) getRightTypeCell(firstRow.getCell(9));//行业描述
            String experienceType = (String) getRightTypeCell(firstRow.getCell(10));//行业类别描述

            if (!name.equals("姓名") || !sex.equals("性别") ||
                    !idNumber.equals("身份证号") ||
                    !phone.equals("电话") || !company.equals("工作单位") ||
                    !title.equals("职称") || !address.equals("常住地市") ||
                    !type.equals("擅长行业") ||
                    !experience.equals("行业描述") || !experienceType.equals("任务类别描述")) {
                result.setMessage("模板标题发生变化，无法提交，请先核对模板");
                result.setCode(500);
                string = JSONObject.toJSONString(result);
                return string;
            }
            List<BasicExpertInfo> basicExpertInfos = new ArrayList<>();
            List<BasicExpertInfo> dataList = new ArrayList();
            for (int j = 1; j < rows; j++) {
                // 获得第 j 行对象
                Row row = sheet.getRow(j);
                //必填项
                if (row.getCell(0) == null
                        && row.getCell(1) == null
                ) {
                    break;
                }
                BasicExpertInfo basicExpertInfo = new BasicExpertInfo();

                if (row.getCell(1) != null) {
                    basicExpertInfo.setName((String) getRightTypeCell(row.getCell(1)));
                }
                if (row.getCell(2) != null) {
                    String sexData = (String) getRightTypeCell(row.getCell(2));//性别
                    basicExpertInfo.setSex(sexData.equals("男") ? 0 : 1);
                }
                if (row.getCell(3) != null) {
                    String idNumberData = (String) getRightTypeCell(row.getCell(3));
                    basicExpertInfo.setIdNumber(idNumberData);
                    String year = idNumberData.substring(6, 10);
                    String month = idNumberData.substring(10, 12);
                    String day = idNumberData.substring(12, 14);
                    String birthDay = year + "-" + month + "-" + day;
                    basicExpertInfo.setBirthday(birthDay);
                }
                if (row.getCell(4) != null) {
                    basicExpertInfo.setPhone((String) getRightTypeCell(row.getCell(4)));
                }
                if (row.getCell(5) != null) {
                    basicExpertInfo.setCompany((String) getRightTypeCell(row.getCell(5)));
                }
                if (row.getCell(6) != null) {
                    basicExpertInfo.setTitle((String) getRightTypeCell(row.getCell(6)));
                }
                if (row.getCell(7) != null) {
                    basicExpertInfo.setAddress((String) getRightTypeCell(row.getCell(7)));
                }

                if (row.getCell(8) != null) {
                    String typeData = (String) getRightTypeCell(row.getCell(8));
                    basicExpertInfo.setType(basicExpertService.getBasicExpertType(typeData).get(0).getDictOrder());
                }
                if (row.getCell(9) != null) {
                    basicExpertInfo.setExperience((String) getRightTypeCell(row.getCell(9)));
                }
                if (row.getCell(10) != null) {
                    basicExpertInfo.setExperienceType((String) getRightTypeCell(row.getCell(10)));
                }
                basicExpertInfos.add(basicExpertInfo);
                //basicExpertService.insertBasicExpertInfo(basicExpertInfo);
            }
            for (BasicExpertInfo basicExpertInfo : basicExpertInfos) {
                if (!basicExpertService.queryExist(basicExpertInfo)) {
                    dataList.add(basicExpertInfo);
                }
            }
            if (dataList.size() != 0) {
                basicExpertService.insertBasicExpertInfoList(dataList);
            }
            int count = basicExpertInfos.size() - dataList.size();
            if (count > 0) {
                result.setCode(205);
                result.setMessage("导入成功" + dataList.size() + "条数据,重复数据" + count + "条");
                String stringData = JSONObject.toJSONString(result);
                return stringData;
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage("上传失败");
            string = JSONObject.toJSONString(result);
            e.printStackTrace();
        }
        return string;
    }

    /**
     * @param cell 一个单元格的对象
     * @return 返回该单元格相应的类型的值 字符 时间 公式 空值
     */
    public static Object getRightTypeCell(Cell cell) {

        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        Object object = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                //判断是不是字符类型单元格
                case Cell.CELL_TYPE_STRING: {
                    object = cell.getStringCellValue();
                    break;
                }
                case Cell.CELL_TYPE_NUMERIC: {
                    //判断是是不是时间格式
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        object = cell.getDateCellValue();
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        object = decimalFormat.format(cell.getNumericCellValue());
                    }
                    break;
                }
                //读取公式类型的单元格
                case Cell.CELL_TYPE_FORMULA: {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    object = decimalFormat.format(cell.getNumericCellValue());
                    break;
                }
                //判断单元格是不是空值
                case Cell.CELL_TYPE_BLANK: {
                    cell.setCellType(Cell.CELL_TYPE_BLANK);
                    object = cell.getStringCellValue();
                    break;
                }
            }
        }
        return object;
    }
}

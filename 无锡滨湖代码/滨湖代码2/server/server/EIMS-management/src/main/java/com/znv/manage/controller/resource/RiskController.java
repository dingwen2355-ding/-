package com.znv.manage.controller.resource;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Risk;
import com.znv.manage.service.resource.RiskService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "风险隐患")
@RestController
@RequestMapping("Risk")
public class RiskController {
    @Autowired
    private RiskService riskService;

    @GetMapping("/queryRiskList")
    @ApiOperation("查询风险隐患列表")
    public PageInfo<Risk> queryRiskList(@RequestParam(value = "industry", required = false) String industry,
                                              @RequestParam(value = "enterpriseName", required = false) String enterpriseName,
                                              @RequestParam(value = "rectificationProgress", required = false) String rectificationProgress,
                                              @RequestParam(value = "startTime", required = false) String startTime,
                                              @RequestParam(value = "endTime", required = false) String endTime,
                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return riskService.queryRiskList(industry, enterpriseName, rectificationProgress, startTime, endTime, pageNum, pageSize);
    }

    @PostMapping("/deleteByPrimaryKey")
    @ApiOperation("删除风险隐患")
    public Result deleteByPrimaryKey(@RequestParam(value = "ids", required = false) String ids) {
        return riskService.deleteByPrimaryKey(ids);
    }

    @PostMapping("/insertSelective")
    @ApiOperation("新增风险隐患")
    public Result insertSelective(@RequestBody Risk risk) {
        return riskService.insertSelective(risk);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ApiOperation("修改风险隐患")
    public Result updateByPrimaryKeySelective(@RequestBody Risk risk) {
        return riskService.updateByPrimaryKeySelective(risk);
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

    ////4：防护目标导入
    private String BasicExpertInsert(Sheet sheet, int rows) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("上传成功");
        String string = JSONObject.toJSONString(result);
        try {
            Row firstRow = sheet.getRow(0);
            String industry = (String) getRightTypeCell(firstRow.getCell(1));//行业
            String discoveryDate = (String) getRightTypeCell(firstRow.getCell(2));//发现日期
            String enterpriseName = (String) getRightTypeCell(firstRow.getCell(3));//企业名称
            String dangersType = (String) getRightTypeCell(firstRow.getCell(4));//隐患类型
            String dangersSources = (String) getRightTypeCell(firstRow.getCell(5));//隐患来源
            String dangersDetails = (String) getRightTypeCell(firstRow.getCell(6));//隐患明细
            String rectificationMeasures = (String) getRightTypeCell(firstRow.getCell(7));//整改措施
            String rectificationDate = (String) getRightTypeCell(firstRow.getCell(8));//整改时限
            String regulatoryDepartment = (String) getRightTypeCell(firstRow.getCell(9));//监管责任部门
            String responsiblePersonPhone = (String) getRightTypeCell(firstRow.getCell(10));//责任人联系方式
            String rectificationProgress = (String) getRightTypeCell(firstRow.getCell(11));//整改进度
            String isCase = (String) getRightTypeCell(firstRow.getCell(12));//是否立案
            String isFine = (String) getRightTypeCell(firstRow.getCell(13));//罚款
            String isAppointment = (String) getRightTypeCell(firstRow.getCell(14));//是否约谈
            String isExposure = (String) getRightTypeCell(firstRow.getCell(15));//是否曝光
            String isRectification = (String) getRightTypeCell(firstRow.getCell(16));//是否停产整顿
            String isClose = (String) getRightTypeCell(firstRow.getCell(17));//是否关闭取缔
            String isTransfer = (String) getRightTypeCell(firstRow.getCell(18));//是否移送司法机关追究刑事责任
            if (!industry.equals("行业")|| !discoveryDate.equals("发现日期")||
                    !enterpriseName.equals("企业名称")|| !dangersType.equals("隐患类型")||
                    !dangersSources.equals("隐患来源")|| !dangersDetails.equals("隐患明细")||
                    !rectificationMeasures.equals("整改措施")|| !rectificationDate.equals("整改时限")||
                    !regulatoryDepartment.equals("监管责任部门")|| !responsiblePersonPhone.equals("责任人联系方式")||
                    !rectificationProgress.equals("整改进度")|| !isCase.equals("是否立案")|| !isFine.equals("罚款")||
                    !isAppointment.equals("是否约谈")|| !isExposure.equals("是否曝光")||
                    !isRectification.equals("是否停产整顿")|| !isClose.equals("是否关闭取缔")||
                    !isTransfer.equals("是否移送司法机关追究刑事责任")){

                result.setMessage("模板标题发生变化，无法提交，请先核对模板");
                result.setCode(500);
                string = JSONObject.toJSONString(result);
                return string;
            }
            List<Risk> risks = new ArrayList<>();
            List<Risk> dataList = new ArrayList<>();
            for (int j = 1; j < rows; j++) {
                // 获得第 j 行对象
                Row row = sheet.getRow(j);
                //必填项
                if (row.getCell(0) == null
                        && row.getCell(1) == null
                ) {
                    break;
                }
                Risk risk = new Risk();

                if (row.getCell(1) != null) {
                    risk.setIndustry((String) getRightTypeCell(row.getCell(1)));
                }
                if (row.getCell(2) != null) {
                    risk.setDiscoveryDate((Date) getRightTypeCell(row.getCell(2)));
                }
                if (row.getCell(3) != null) {
                    risk.setEnterpriseName((String) getRightTypeCell(row.getCell(3)));
                }
                if (row.getCell(4) != null) {
                    risk.setDangersType((String) getRightTypeCell(row.getCell(4)));
                }
                if (row.getCell(5) != null) {
                    risk.setDangersSources((String) getRightTypeCell(row.getCell(5)));
                }
                if (row.getCell(6) != null) {
                    risk.setDangersDetails((String) getRightTypeCell(row.getCell(6)));
                }
                if (row.getCell(7) != null) {
                    risk.setRectificationMeasures((String) getRightTypeCell(row.getCell(7)));
                }
                if (row.getCell(8) != null) {
                    risk.setRectificationDate((Date) getRightTypeCell(row.getCell(8)));
                }
                if (row.getCell(9) != null) {
                    risk.setRegulatoryDepartment((String) getRightTypeCell(row.getCell(9)));
                }
                if (row.getCell(10) != null) {
                    risk.setResponsiblePersonPhone((String) getRightTypeCell(row.getCell(10)));
                }
                if (row.getCell(11) != null) {
                    risk.setRectificationProgress((String) getRightTypeCell(row.getCell(11)));
                }
                if (row.getCell(12) != null) {
                    risk.setIsCase((String) getRightTypeCell(row.getCell(12)));
                }
                if (row.getCell(13) != null) {
                    risk.setIsFine((String) getRightTypeCell(row.getCell(13)));
                }
                if (row.getCell(14) != null) {
                    risk.setIsAppointment((String) getRightTypeCell(row.getCell(14)));
                }
                if (row.getCell(15) != null) {
                    risk.setIsExposure((String) getRightTypeCell(row.getCell(15)));
                }
                if (row.getCell(16) != null) {
                    risk.setIsRectification((String) getRightTypeCell(row.getCell(16)));
                }
                if (row.getCell(17) != null) {
                    risk.setIsClose((String) getRightTypeCell(row.getCell(17)));
                }
                if (row.getCell(18) != null) {
                    risk.setIsTransfer((String) getRightTypeCell(row.getCell(18)));
                }

                risks.add(risk);
            }
            for (Risk risk : risks) {
                if (risk.getIndustry() != null && risk.getEnterpriseName() != null){
                    dataList.add(risk);
                }
            }
            System.out.println("dataList = " + dataList);
            if (dataList.size() != 0) {
                riskService.insertSelectiveList(dataList);
            }
            int count = risks.size() - dataList.size();
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
                        System.out.println("object1 = " + object);
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        object = decimalFormat.format(cell.getNumericCellValue());
                        System.out.println("object2 = " + object);
                        System.out.println(cell);

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

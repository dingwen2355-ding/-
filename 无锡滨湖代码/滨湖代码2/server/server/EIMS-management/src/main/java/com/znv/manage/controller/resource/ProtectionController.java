package com.znv.manage.controller.resource;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.BasicExpertInfo;
import com.znv.manage.common.bean.resource.Protection;
import com.znv.manage.service.resource.ProtectionService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "防护目标")
@RestController
@RequestMapping("Protection")
public class ProtectionController {
    @Autowired
    private ProtectionService protectionService;

    @GetMapping("/queryProtectionList")
    @ApiOperation("查询防护目标列表")
    public PageInfo<Protection> queryProtectionList(@RequestParam(value = "protectionName", required = false) String protectionName,
                                                    @RequestParam(value = "protectionType", required = false) String protectionType,
                                                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return protectionService.queryProtectionList(protectionName, protectionType, pageNum, pageSize);
    }

    @PostMapping("/deleteByPrimaryKey")
    @ApiOperation("删除防护目标")
    public Result deleteByPrimaryKey(@RequestParam(value = "ids", required = false) String ids) {
        return protectionService.deleteByPrimaryKey(ids);
    }

    @PostMapping("/insertSelective")
    @ApiOperation("新增防护目标")
    public Result insertSelective(@RequestBody Protection protection) {
        return protectionService.insertSelective(protection);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ApiOperation("修改防护目标")
    public Result updateByPrimaryKeySelective(@RequestBody Protection protection) {
        return protectionService.updateByPrimaryKeySelective(protection);
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
            String protectionName = (String) getRightTypeCell(firstRow.getCell(1));//防护名称
            String protectionType = (String) getRightTypeCell(firstRow.getCell(2));//防护类型
            String lastInspection = (String) getRightTypeCell(firstRow.getCell(3));//最近巡查时间
            String destinationAddress = (String) getRightTypeCell(firstRow.getCell(4));//目标地址
            String managerName = (String) getRightTypeCell(firstRow.getCell(5));//管理员姓名
            String managerPhone = (String) getRightTypeCell(firstRow.getCell(6));//管理员电话

            if (!protectionName.equals("防护名称") || !protectionType.equals("防护类型") ||
                    !lastInspection.equals("最近巡查时间") || !destinationAddress.equals("目标地址") ||
                    !managerName.equals("管理员姓名") || !managerPhone.equals("管理员电话")) {
                result.setMessage("模板标题发生变化，无法提交，请先核对模板");
                result.setCode(500);
                string = JSONObject.toJSONString(result);
                return string;
            }
            List<Protection> protections = new ArrayList<>();
            List<Protection> dataList = new ArrayList();
            for (int j = 1; j < rows; j++) {
                // 获得第 j 行对象
                Row row = sheet.getRow(j);
                //必填项
                if (row.getCell(0) == null
                        && row.getCell(1) == null
                ) {
                    break;
                }
                Protection protection = new Protection();

                if (row.getCell(1) != null) {
                    protection.setProtectionName((String) getRightTypeCell(row.getCell(1)));
                }
                if (row.getCell(2) != null) {
                    protection.setProtectionType((String) getRightTypeCell(row.getCell(2)));
                }
                if (row.getCell(3) != null) {
                    protection.setLastInspection((Date) getRightTypeCell(row.getCell(3)));
                }
                if (row.getCell(4) != null) {
                    protection.setDestinationAddress((String) getRightTypeCell(row.getCell(4)));
                }
                if (row.getCell(5) != null) {
                    protection.setManagerName((String) getRightTypeCell(row.getCell(5)));
                }
                if (row.getCell(6) != null) {
                    protection.setManagerPhone((String) getRightTypeCell(row.getCell(6)));
                }
                protections.add(protection);
            }
            for (Protection protection : protections) {
                dataList.add(protection);
            }
            System.out.println("dataList = " + dataList);
            if (dataList.size() != 0) {
                protectionService.insertSelectiveList(dataList);
            }
            int count = protections.size() - dataList.size();
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

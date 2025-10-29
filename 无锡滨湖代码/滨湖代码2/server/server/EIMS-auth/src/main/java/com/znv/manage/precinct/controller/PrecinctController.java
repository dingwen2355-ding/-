package com.znv.manage.precinct.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.MultipartUtil;
import com.znv.manage.precinct.service.PrecinctService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "部门管理")
@RestController
@Slf4j
public class PrecinctController {

    @Autowired
    private PrecinctService precinctService;

    @GetMapping("v2/precinct/queryPrecinct")
    public Result queryPrecinct(
            @RequestParam(value = "precinctKind", required = false) Integer precinctKind,
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "upPrecinctId", required = false) String upPrecinctId,
            @RequestParam(value = "userId", required = false) String userId
    ) {
//        String precinctIds = CommonUtils.getUserPrecicntIdsByUserId(userId);
//        return precinctService.queryPrecinct(precinctKind,precinctId,upPrecinctId,precinctIds);
        Result result = new Result();
        result.setData(precinctService.queryPrecinct(precinctKind, precinctId, upPrecinctId, null));
        return result;
    }

    @GetMapping("v2/precinct/queryPrecinctByKind")
    public Result queryPrecinctByKind(
            @RequestParam(value = "precinctKind", required = false) Integer precinctKind,
            @RequestParam(value = "forStreet", required = false) String forStreet
    ) {
//        String precinctIds = CommonUtils.getUserPrecicntIdsByUserId(userId);
//        return precinctService.queryPrecinct(precinctKind,precinctId,upPrecinctId,precinctIds);
        Result result = new Result();
        result.setData(precinctService.queryPrecinctByKind(precinctKind, forStreet));
        return result;
    }

    @PostMapping("v2/precinct/updatePrecinct")
    public Result updatePrecinct(
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "precinctName", required = false) String precinctName,
            @RequestParam(value = "precinctAddr", required = false) String precinctAddr,
            @RequestParam(value = "upPrecinctId", required = false) String upPrecinctId,
            @RequestParam(value = "precinctKind", required = false) Integer precinctKind,
            @RequestParam(value = "regionName", required = false) String regionName,
            @RequestParam(value = "streetName", required = false) String streetName,
            @RequestParam(value = "streetNo", required = false) String streetNo,
            @RequestParam(value = "buildingNo", required = false) String buildingNo,
            @RequestParam(value = "unitNo", required = false) String unitNo,
            @RequestParam(value = "floorNo", required = false) String floorNo,
            @RequestParam(value = "houseNo", required = false) String houseNo

    ) {
        Result result = new Result();
        int i = precinctService.updatePrecinct(precinctId, precinctName,
                precinctAddr, upPrecinctId,
                precinctKind, regionName, streetName, streetNo, buildingNo, unitNo, floorNo, houseNo);
        if (i >= 1) {
            result.setCode(0);
            result.setMessage("编辑成功");
        } else {
            result.setCode(1);
            result.setMessage("编辑失败");
        }
        return result;
    }

    @PostMapping("v2/precinct/addPrecinct")
    public Result addPrecinct(
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "precinctName", required = false) String precinctName,
            @RequestParam(value = "precinctAddr", required = false) String precinctAddr,
            @RequestParam(value = "upPrecinctId", required = false) String upPrecinctId,
            @RequestParam(value = "precinctKind", required = false) Integer precinctKind,
            @RequestParam(value = "regionName", required = false) String regionName,
            @RequestParam(value = "streetName", required = false) String streetName,
            @RequestParam(value = "streetNo", required = false) String streetNo,
            @RequestParam(value = "buildingNo", required = false) String buildingNo,
            @RequestParam(value = "unitNo", required = false) String unitNo,
            @RequestParam(value = "floorNo", required = false) String floorNo,
            @RequestParam(value = "houseNo", required = false) String houseNo,
            @RequestParam(value = "userId", required = false) String userId

    ) {
        Result result = new Result();
        int i = precinctService.addPrecinct(precinctId, precinctName,
                precinctAddr, upPrecinctId,
                precinctKind, regionName, streetName, streetNo, buildingNo, unitNo, floorNo, houseNo, userId);
        if (i >= 1) {
            result.setCode(0);
            result.setMessage("添加成功");
        } else {
            result.setCode(1);
            result.setMessage("添加失败");
        }
        return result;
    }

    @PostMapping("v2/precinct/deletePrecinct")
    public Result deletePrecinct(
            @RequestParam(value = "precinctId", required = false) String precinctId
    ) {
        Result result = new Result();
        int i = precinctService.deletePrecinct(precinctId);
        if (i >= 1) {
            result.setCode(0);
            result.setMessage("删除成功");
        } else {
            result.setCode(1);
            result.setMessage("删除失败");
        }
        return result;
    }

    // 删除多条信息
    @PostMapping("v2/precinct/deletePrecincts")
    public Result deletePrecincts(
            @RequestParam(value = "precinctId", required = false) String precinctId
    ) {
        Result result = new Result();
        int i = precinctService.deletePrecincts(precinctId);
        if (i >= 1) {

            result.setMessage("删除成功");
            result.setCode(0);
        } else {
            result.setCode(1);
            result.setMessage("删除失败");
        }
        return result;
    }


    @GetMapping("v2/precinct/queryPrecinctKind")
    public Result queryPrecinctKind(
    ) {
        Result result = new Result();
        result.setData(precinctService.queryPrecinctKind());
        return result;
    }


    @PostMapping("/v2/precinct/upload")
//    @RequiresPermissions("basedata:oesmembers:upload")
    public Result upload(@RequestParam("file") MultipartFile file) {

        Result result = new Result();
        if (!MultipartUtil.fileType(file)) {
            result.setCode(500);
            result.setData("上传文件格式错误");
            return result;
        }
        int count = 0;
        if (file.isEmpty()) {
            System.out.println("上传文件不能为空");
        } else {

            System.out.println("file = " + file.getContentType());

            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                //获取表格
                Sheet sheet = workbook.getSheetAt(0);
                //获取表格行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                //遍历表格，取出数据
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//标题行
                    } else {
                        String str[] = new String[17];
                        for (int i = 0; i < 17; i++) {
                            //System.out.println(sheet.getRow(j).getCell(i).toString());
                            if (sheet.getRow(j).getCell(i) != null) {
                                Cell cell = sheet.getRow(j).getCell(i);
                                cell.setCellType(cell.CELL_TYPE_STRING);
                                str[i] = cell.toString();
                            } else {
                                str[i] = "";
                            }
                        }
                        //调用Service,传递参数给Service
                        //如果表格内数据为空，传递null
                        count += precinctService.insertExcel(str[0], str[1],
                                str[2].equals("") ? null : str[2],
                                str[3],
                                str[4].equals("") ? null : Integer.parseInt(str[4]),
                                str[5],
                                str[6].equals("") ? null : str[6],
                                str[7].equals("") ? null : str[7],
                                str[8].equals("") ? null : str[8],
                                str[9].equals("") ? null : str[9],
                                str[10].equals("") ? null : str[10],
                                str[11].equals("") ? null : str[11],
                                str[12].equals("") ? null : str[12],
                                str[13].equals("") ? null : str[13],
                                str[14].equals("") ? null : str[14],
                                str[15].equals("") ? null : str[15],
                                str[16].equals("") ? null : str[16]);

                        result.setCode(0);
                        result.setMessage("录入" + count + "条数据");
                    }
                }
            } catch (Exception e) {
                result.setCode(1);
                result.setMessage("异常");
                log.error(e.getMessage());
            }
        }
        return result;
    }

    @GetMapping("/v2/device/queryPrecinctTree")
    public Result queryPrecinctTree(@RequestParam(value = "precinctId") String precinctId,
                                    @RequestParam(value = "userId", required = true) String userId) {
        Result result = new Result();
        result.setData(precinctService.queryPrecinctTree(precinctId, userId));
        return result;

    }
}

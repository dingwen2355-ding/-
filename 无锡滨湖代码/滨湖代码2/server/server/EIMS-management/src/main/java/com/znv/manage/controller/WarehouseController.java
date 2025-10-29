package com.znv.manage.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.dao.WarehouseDao;
import com.znv.manage.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@Api(tags = "仓库")
@RestController
@RequestMapping("warehouse")
@Slf4j
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    WarehouseDao warehouseDao;

    @ApiOperation(value = "物资导入")
    @PostMapping(value = "/stuffExcelAnalysis")
    public Result stuffExcelAnalysis(@RequestParam("userId") String userId,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam("userLevel") String userLevel) {
        log.info("获取到的用户" + userId);
        return warehouseService.stuffExcelAnalysis(userId, file, userLevel);
    }

    @ApiOperation(value = "物资出入库明细")
    @GetMapping(value = "/stuffRecordDetail")
    public PageInfo stuffRecordDetail(@RequestParam(value = "areaId", required = false) String areaId,
                                      @RequestParam(value = "stockName", required = false) String stockName,
                                      @RequestParam(value = "stuffName", required = false) String stuffName,
                                      @RequestParam(value = "stuffModel", required = false) String stuffModel,
                                      @RequestParam(value = "stuffBrand", required = false) String stuffBrand,
                                      @RequestParam(value = "helpTypeId", required = false) String helpTypeId,
                                      @RequestParam(value = "operationType", required = false) String operationType,
                                      @RequestParam(value = "startTime", required = false) String startTime,
                                      @RequestParam(value = "endTime", required = false) String endTime,
                                      @RequestParam(value = "manager", required = false) String manager,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "userId", required = false) String userId,
                                      @RequestParam(value = "userLevel", required = false) String userLevel) {
        return warehouseService.stuffRecordDetail(areaId, stockName, stuffName, stuffModel, stuffBrand, helpTypeId, operationType, startTime, endTime, manager, page, pageSize, userId, userLevel);
    }

    @ApiOperation(value = "物资出入库管理操作记录删除")
    @GetMapping(value = "/deleteOperationRecordById")
    public Result deleteOperationRecordById(@RequestParam(value = "stockId", required = false) String stockId,
                                            @RequestParam(value = "userId", required = false) String userId) {
        return warehouseService.deleteOperationRecordByStock(stockId, userId);
    }

    @ApiOperation(value = "物资出入库操作明细删除")
    @GetMapping(value = "/deleteOperatiionInfoById")
    public Result deleteOperatiionInfoById(@RequestParam(value = "id", required = false) String id) {
        return warehouseService.deleteOperatiionInfoById(id);
    }

    @ApiOperation(value = "物资出入库管理")
    @GetMapping(value = "/stuffRecordManagement")
    public PageInfo stuffRecordManagement(@RequestParam(value = "region", required = false) String region,
                                          @RequestParam(value = "stockName", required = false) String stockName,
                                          @RequestParam(value = "stockType", required = false) String stockType,
                                          @RequestParam(value = "status", required = false) String status,
                                          @RequestParam(value = "userId", required = false) String userId,
                                          @RequestParam(value = "userLevel", required = false) String userLevel,
                                          @RequestParam(value = "checkStartTime", required = false) String checkStartTime,
                                          @RequestParam(value = "checkEndTime", required = false) String checkEndTime,
                                          @RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return warehouseService.stuffRecordManagement(region, stockName, stockType, status, userId, userLevel, checkStartTime, checkEndTime, page, pageSize);
    }

    @ApiOperation(value = "物资出入库管理根据仓库查询详情")
    @GetMapping(value = "/stuffRecordDetailByStockId")
    public Result stuffRecordDetailByStockId(@RequestParam(value = "stockId", required = false) String stockId,
                                             @RequestParam(value = "checkTime", required = false) String checkTime) {
        return warehouseService.stuffRecordDetailByStockId(stockId, checkTime);
    }

    @ApiOperation(value = "物资库存管理")
    @GetMapping(value = "/stuffInventory")
    public PageInfo stuffInventory(@RequestParam(value = "areaId", required = false) String areaId,
                                   @RequestParam(value = "stockName", required = false) String stockName,
                                   @RequestParam(value = "stuffName", required = false) String stuffName,
                                   @RequestParam(value = "stuffModel", required = false) String stuffModel,
                                   @RequestParam(value = "stuffBrand", required = false) String stuffBrand,
                                   @RequestParam(value = "helpTypeId", required = false) String helpTypeId,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(value = "userId", required = false) String userId,
                                   @RequestParam(value = "regionId", required = false) String regionId) {
        return warehouseService.stuffInventory(areaId, stockName, stuffName, stuffModel, stuffBrand, helpTypeId, page, pageSize, userId, regionId);
    }

    @ApiOperation(value = "物资库存-核对")
    @PostMapping(value = "/stockStuffCheck")
    public Result stockStuffCheck(@RequestBody JSONObject jsonObject) {
        return warehouseService.stockStuffCheck(jsonObject);
    }

    @ApiOperation(value = "物资仓库台账-按仓库统计/按区县统计")
    @PostMapping(value = "/stuffExcelExport")
    public Result stuffExcelExport(@RequestBody JSONObject json,
                                   @RequestParam(value = "beginTime", required = false) String beginTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "helpTypes", required = false) String helpTypes) {
        return warehouseService.stuffExcelExport(json, beginTime, endTime, type, helpTypes);
    }

    @ApiOperation(value = "仓库概况(所有)")
    @GetMapping(value = "/warehouseDetail")
    public JSONArray warehouseDetail(@RequestParam(value = "userId", required = false) String userId) {
        return warehouseService.warehouseDetail(userId);
    }

    @ApiOperation(value = "通过仓库管理员id查询仓库概况")
    @GetMapping(value = "/warehouseDetailById")
    public JSONArray warehouseDetailById(@RequestParam(value = "userId", required = false) String userId) {
        return warehouseService.warehouseDetailById(userId);
    }

    @ApiOperation(value = "仓库物资详情")
    @GetMapping(value = "/warehouseStuffInfo")
    public List<Map<String, Object>> warehouseStuffInfo(@RequestParam(value = "stockId", required = false) String stockId,
                                                        @RequestParam(value = "helpId", required = false) String helpId) {
        return warehouseService.warehouseStuffInfo(stockId, helpId);
    }

    @ApiOperation(value = "物资仓库台账-按仓库统计(明细)")
    @PostMapping(value = "/stuffExcelExport2")
    public Result stuffExcelExport(@RequestBody JSONObject json,
                                   @RequestParam(value = "beginTime", required = false) String beginTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "helpTypes", required = false) String helpTypes) {
        return warehouseService.stuffExcelExport2(json, beginTime, endTime, helpTypes);
    }

    @ApiOperation("查询所在辖区")
    @GetMapping("queryRegion")
    public Result queryRegion() {
        Result result = warehouseService.selDictArea();
        return result;
    }

    /**
     * 物资管理
     *
     * @param
     * @return
     */
    @ApiOperation(value = "物资管理")
    @GetMapping(value = "/materialsManagement")
    public Result materialsManagement(@RequestParam(value = "secondLevelType", required = false) String secondLevelType) {
        return warehouseService.materialsManagement(secondLevelType);
    }

    /**
     * 物资管理类别统计
     *
     * @param secondLevelType（如type为1，点击市辖区，secondLevelType传"市辖区"）
     * @param keyword
     * @return
     */
    @ApiOperation(value = "物资管理类别统计")
    @GetMapping(value = "/materialsCategories")
    public Result materialsCategories(@RequestParam(value = "secondLevelType", required = false) String secondLevelType,
                                      @RequestParam(value = "nameType", required = false) String nameType,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return warehouseService.materialsCategories(secondLevelType, nameType, keyword, page, pageSize);
    }

    /**
     * 物资仓库统计
     *
     * @param secondLevelType 市区名称：淄川区，周村区等
     * @param nameType        类型：如卫生防疫、防汛抗旱等
     * @param thirdLevelType  商品名称：如口罩、帐篷等
     * @param keyword         关键字
     * @return
     */
    @ApiOperation(value = "物资仓库统计")
    @GetMapping(value = "/materialWarehouse")
    public PageInfo materialWarehouse(@RequestParam(value = "secondLevelType", required = false) String secondLevelType,
                                      @RequestParam(value = "nameType", required = false) String nameType,
                                      @RequestParam(value = "thirdLevelType", required = false) String thirdLevelType,
                                      @RequestParam(value = "fifthLevelType", required = false) String fifthLevelType,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return warehouseService.materialWarehouse(secondLevelType, nameType, thirdLevelType, fifthLevelType, keyword, page, pageSize);
    }

    /**
     * 物资出入库明细
     *
     * @param secondLevelType 市区名称：淄川区，周村区等
     * @param nameType        类型：如卫生防疫、防汛抗旱等
     * @param thirdLevelType  商品名称：如口罩、帐篷等
     * @param fifthLevelType  商品型号：如K17
     * @param fourthLevelType 公司名称：如山东省危险化学品堵漏应急救援中心
     * @param keyword
     * @return
     */
    @ApiOperation(value = "物资出入库明细")
    @GetMapping(value = "/materialDetail")
    public PageInfo materialDetail(@RequestParam(value = "secondLevelType", required = false) String secondLevelType,
                                   @RequestParam(value = "nameType", required = false) String nameType,
                                   @RequestParam(value = "thirdLevelType", required = false) String thirdLevelType,
                                   @RequestParam(value = "fifthLevelType", required = false) String fifthLevelType,
                                   @RequestParam(value = "fourthLevelType", required = false) String fourthLevelType,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(value = "startTime", required = false) String startTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "operationType", required = false) String operationType) {
        return warehouseService.materialDetail(secondLevelType, nameType, thirdLevelType, fifthLevelType, fourthLevelType, keyword, startTime, endTime, operationType, page, pageSize);
    }

    @ApiOperation(value = "物资出入库明细app")
    @GetMapping(value = "/materialDetailApp")
    public Result materialDetailApp(@RequestParam(value = "secondLevelType", required = false) String secondLevelType,
                                    @RequestParam(value = "nameType", required = false) String nameType,
                                    @RequestParam(value = "thirdLevelType", required = false) String thirdLevelType,
                                    @RequestParam(value = "fifthLevelType", required = false) String fifthLevelType,
                                    @RequestParam(value = "fourthLevelType", required = false) String fourthLevelType,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(value = "startTime", required = false) String startTime,
                                    @RequestParam(value = "endTime", required = false) String endTime,
                                    @RequestParam(value = "operationType", required = false) String operationType) {
        return warehouseService.materialDetailApp(secondLevelType, nameType, thirdLevelType, fifthLevelType, fourthLevelType, keyword, startTime, endTime, operationType, page, pageSize);
    }

    @ApiOperation(value = "物资仓库-各地区物资数量")
    @GetMapping(value = "/totalAmountMaterials")
    public Result totalAmountMaterials(@RequestParam(value = "nameType", required = false) String nameType) {
        return warehouseService.totalAmountMaterials(nameType);
    }

    @ApiOperation(value = "物资仓库-根据仓库分类展示商品")
    @GetMapping(value = "/classifiedDsplayGoods")
    public Result classifiedDsplayGoods(@RequestParam(value = "secondLevelType", required = false) String secondLevelType,
                                        @RequestParam(value = "nameType", required = false) String nameType,
                                        @RequestParam(value = "keyword", required = false) String keyword,
                                        @RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return warehouseService.classifiedDsplayGoods(secondLevelType, nameType, keyword, page, pageSize);
    }

    @ApiOperation(value = "物资仓库台账-物资统计(横排)")
    @PostMapping(value = "/stuffExcelExport3")
    public Result stuffExcelExport3(@RequestBody JSONObject json,
                                    @RequestParam(value = "helpTypes", required = false) String helpTypes) {
        return warehouseService.stuffExcelExport3(json, helpTypes);
    }

    @ApiOperation("物资仓库台账-通过区县id查仓库id")
    @GetMapping(value = "/getStockIdByArea")
    public Result getStockIdByArea(@RequestParam(value = "regionId", required = false) String regionId) {
        return warehouseService.getStockIdByArea(regionId);
    }

    @ApiOperation("临淄-获取库存和仓库数据")
    @GetMapping(value = "/stuffInventory-lz")
    public Result stuffInventoryLinZi(@RequestParam(value = "stuffType", required = false) String stuffType,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return warehouseService.stuffInventoryLinZi(stuffType, page, pageSize);
    }

    @ApiOperation("合并接口")
    @GetMapping(value = "/hebing")
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result hebing(@RequestParam(value = "name") String name,
                         @RequestParam(value = "otherName") String otherName) {
        Result result = new Result();
        String originId = warehouseDao.getIdByName(name);
        if (StringUtils.isBlank(originId)) {
            throw new BusinessException("找不到" + name);
        }
        for (String oldName : otherName.split("、")) {
            String oldId = warehouseDao.getIdByName(oldName);
            if (StringUtils.isBlank(oldId)) {
                throw new BusinessException("找不到" + oldName);
            }
            if (oldId.equals(originId)) {
                continue;
            }
            warehouseDao.updateStockStuff(originId, oldId);
            warehouseDao.updateAgreement(originId, oldId);
            warehouseDao.updateOperation(originId, oldId);
            warehouseDao.deleteById(oldId);
        }
        return result;
    }
}

package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface WarehouseService {
    Result stuffExcelAnalysis(String userId, MultipartFile file, String userLevel);

    PageInfo stuffRecordDetail(String areaId, String stockName, String stuffName, String stuffModel, String stuffBrand, String helpTypeId, String operationType,
                               String startTime, String endTime, String manager, Integer page, Integer pageSize, String userId, String userLevel);

    Result deleteOperationRecordByStock(String stockId, String userId);

    Result deleteOperatiionInfoById(String operationId);

    PageInfo stuffRecordManagement(String region, String stockName, String stockType, String status, String userId, String userLevel, String checkStartTime, String checkEndTime, Integer page, Integer pageSize);

    Result stuffRecordDetailByStockId(String sotckId, String checkTime);

    PageInfo stuffInventory(String areaId, String stockName, String stuffName, String stuffModel, String stuffBrand, String helpTypeId, Integer page, Integer pageSize, String userId, String regionId);

    Result stockStuffCheck(JSONObject jsonObject);

    Result stuffExcelExport(JSONObject json, String beginTime, String endTime, String type, String helpTypes);

    JSONArray warehouseDetail(String userId);

    JSONArray warehouseDetailById(String userId);

    List<Map<String, Object>> warehouseStuffInfo(String stockId, String helpId);

    Result stuffExcelExport2(JSONObject json, String beginTime, String endTime, String helpTypes);

    Result materialsManagement(String secondLevelType);

    Result materialsCategories(String secondLevelType, String nameType, String keyword, Integer page, Integer pageSize);

    PageInfo materialWarehouse(String secondLevelType, String nameType, String thirdLevelType, String fifthLevelType, String keyword, Integer page, Integer pageSize);

    PageInfo materialDetail(String secondLevelType, String nameType, String thirdLevelType, String fifthLevelType, String fourthLevelType, String keyword, String startTime, String endTime, String operationType, Integer page, Integer pageSize);

    Result materialDetailApp(String secondLevelType, String nameType, String thirdLevelType, String fifthLevelType, String fourthLevelType, String keyword, String startTime, String endTime, String operationType, Integer page, Integer pageSize);

    Result selDictArea();

    Result totalAmountMaterials(String nameType);

    Result classifiedDsplayGoods(String secondLevelType, String nameType, String keyword, Integer page, Integer pageSize);

    Result stuffExcelExport3(JSONObject json, String helpTypes);

    Result getStockIdByArea(String regionId);

    Result stuffInventoryLinZi(String stuffType, Integer page, Integer pageSize);
}

package com.znv.manage.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public interface WarehouseDao {
    List<Map> queryStuffDict();

    List<Map> queryWarehouseDict();

//    Integer queryStuffNum(@Param("warehouseId") String warehouseId,@Param("stuffId") String stuffId);

    BigDecimal queryStuffNum(@Param("warehouseId") String warehouseId, @Param("stuffId") String stuffId, @Param("brand") String brand,
                             @Param("model") String model);

    void saveStuffOperationRecord(@Param("datas") List<Map<String, String>> datas);

    void saveStuffOperationRecordInfo(@Param("datas") List<Map<String, String>> datas);

    void completionStuffOperationInfo(@Param("operationId") String operationId);

    List<Map<String, Object>> stuffRecordDetail(@Param("areaId") String areaId,
                                                @Param("stockName") String stockName,
                                                @Param("stuffName") String stuffName,
                                                @Param("stuffModel") String stuffModel,
                                                @Param("stuffBrand") String stuffBrand,
                                                @Param("helpTypeId") String helpTypeId,
                                                @Param("operationType") String operationType,
                                                @Param("startTime") String startTime,
                                                @Param("endTime") String endTime,
                                                @Param("manager") String manager,
                                                @Param("userId") String userId);

    int deleteOperationRecordByStock(@Param("stockId") String stockId, @Param("userId") String userId);

    int deleteOperationInfoById(@Param("id") String id);

    String getUserStreetId(@Param("userId") String userId);

    List<Map<String, Object>> stuffRecordManagement(@Param("region") String fileName,
                                                    @Param("stockName") String stockName,
                                                    @Param("stockType") String userName,
                                                    @Param("userId") String userId,
                                                    @Param("checkStartTime") String checkStartTime,
                                                    @Param("checkEndTime") String checkEndTime);

    List<Map<String, Object>> stuffRecordDetailByStockId(@Param("stockId") String stockId,
                                                         @Param("checkTime") String checkTime);

    void saveResourceStockStuffNum(@Param("datas") List<Map<String, String>> datas);

    List<Map<String, Object>> stuffInventory(@Param("areaId") String areaId,
                                             @Param("stockName") String stockName,
                                             @Param("stuffName") String stuffName,
                                             @Param("stuffModel") String stuffModel,
                                             @Param("stuffBrand") String stuffBrand,
                                             @Param("helpTypeId") String helpTypeId,
                                             @Param("userId") String userId,
                                             @Param("regionId") String regionId);

    List<Map<String, String>> queryStuffOperationDeatil(@Param("stockId") String stockId, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("stuffIds") String stuffIds);

    List<Map<String, String>> queryStuffOperationDeatilByArea(@Param("stockId") String stockId, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("stuffIds") String stuffIds);

    List<Map<String, String>> queryWareHouseInfo();

    List<Map<String, String>> queryWareHouseInfoById(@Param(value = "userId") String userId);

    List<Map<String, Object>> warehouseStuffInfo(@Param("stockId") String stockId, @Param("helpId") String helpId);

    List<Map> queryOperationInfo(@Param("stockId") String stockId,
                                 @Param("beginTime") String beginTime,
                                 @Param("endTime") String endTime,
                                 @Param("type") String type,
                                 @Param("stuffIds") String stuffIds);

    List<Map<String, Object>> selDictArea();

    List<Map<String, Object>> selStorageType();

    BigDecimal selMaterialsCount(@Param("dictId") String dictId, @Param("helpTypeId") String helpTypeId, @Param("storageId") String storageId);

    List<Map<String, Object>> selDictAreaByAreaName(@Param("secondLevelType") String secondLevelType, @Param("keyword") String keyword, @Param("stuffName") String stuffName);

    List<Map<String, String>> selStuffNameByAreaName(@Param("secondLevelType") String secondLevelType, @Param("keyword") String keyword, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<Map<String, String>> selMaterialsBystorageType(@Param("secondLevelType") String secondLevelType, @Param("keyword") String keyword);

    List<Map<String, Object>> selMaterialsBystorageType1(@Param("secondLevelType") String secondLevelType, @Param("keyword") String keyword, @Param("stuffName") String stuffName);

    List<Map<String, Object>> selMaterialsByEmergencyPowerDict(@Param("secondLevelType") String secondLevelType, @Param("nameType") String nameType, @Param("keyword") String keyword, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<Map<String, Object>> selMaterialsByEmergencyPowerDict1(@Param("secondLevelType") String secondLevelType, @Param("nameType") String nameType, @Param("keyword") String keyword, @Param("stuffName") String stuffName, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<Map<String, String>> selMaterialsByAll(@Param("secondLevelType") String secondLevelType, @Param("keyword") String keyword);

    List<Map<String, Object>> selMaterialsByAll1(@Param("secondLevelType") String secondLevelType, @Param("keyword") String keyword, @Param("stuffName") String stuffName);

    List<Map<String, Object>> materialWarehouse(@Param("secondLevelType") String secondLevelType, @Param("nameType") String nameType, @Param("thirdLevelType") String thirdLevelType, @Param("fifthLevelType") String fifthLevelType, @Param("stockName") String stockName, @Param("stuffId") String stuffId, @Param("keyword") String keyword);

    List<Map<String, String>> materialWarehouse1(@Param("secondLevelType") String secondLevelType, @Param("nameType") String nameType, @Param("thirdLevelType") String thirdLevelType, @Param("fifthLevelType") String fifthLevelType, @Param("stockName") String stockName, @Param("stuffId") String stuffId, @Param("keyword") String keyword);

    List<Map<String, Object>> materialDetail(@Param("secondLevelType") String secondLevelType,
                                             @Param("nameType") String nameType,
                                             @Param("thirdLevelType") String thirdLevelType,
                                             @Param("fifthLevelType") String fifthLevelType,
                                             @Param("fourthLevelType") String fourthLevelType,
                                             @Param("keyword") String keyword,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime,
                                             @Param("operationType") Integer operationType);

    List<Map<String, Object>> getStockNameList(@Param("secondLevelType") String secondLevelType, @Param("nameType") String nameType, @Param("thirdLevelType") String thirdLevelType, @Param("keyword") String keyword);

    List<String> queryStockByUserId(@Param("id") String id);

    List<Map<String, Object>> selStockNameList(@Param("secondLevelType") String secondLevelType, @Param("nameType") String nameType, @Param("keyword") String keyword, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    Map<String, Object> selOperatiionInfoById(@Param("id") String id);

    int updateStockStuffNumIn(Map<String, Object> operatiionInfo);

    int updateStockStuffNumOut(Map<String, Object> operatiionInfo);

    int updateStockStuffCheckTime(JSONObject jsonObject);

    String queryStuffNumByName(@Param("stuffName") String stuffName,
                               @Param("stockName") String stockName,
                               @Param("type") String type,
                               @Param("model") String model,
                               @Param("brand") String brand);

    List<Map<String, String>> stuffExcelExport3(@Param("stockIds") String stockIds,
                                                @Param("stuffIds") String stuffIds);

    String queryStockIdByArea(@Param("regionId") String regionId);

    String getIdByName(@Param("name") String name);

    int updateStockStuff(@Param("id1") String id1,
                         @Param("id2") String id2);

    int updateOperation(@Param("id1") String id1,
                        @Param("id2") String id2);

    int updateAgreement(@Param("id1") String id1,
                        @Param("id2") String id2);

    int deleteById(@Param("id") String id);

    List<Map<String, Object>> stuffInventoryLinZi(@Param("stuffType") String stuffType);
}

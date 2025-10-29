package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.annotation.CountyRestricted;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.PoiUtils;
import com.znv.manage.dao.WarehouseDao;
import com.znv.manage.dao.resource.TeamInfoDao;
import com.znv.manage.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private TeamInfoDao teamInfoDao;
    @Value("${filePath}")
    String filePath;
    @Value("${urlPath}")
    String fileUrl;

    @Value("${stock.level}")
    String stockLevel;

    //监管企业管理员
    @Value("${company.level}")
    String companyLevel;

    //救援队伍管理员
    @Value("${team.level}")
    String teamLevel;

    //社会力量管理员
    @Value("${social.level}")
    String socialLevel;
    @Value("${streetAdmin.level}")
    String streetAdminLevel;

    @Override
    @Transactional(value = "baseTransactionManager")
    public Result<Object> stuffExcelAnalysis(String userId, MultipartFile file, String userLevel) {
        Map<String, String> warehouseDictList = CommonUtils.changeMap2(warehouseDao.queryWarehouseDict(), "stockName", "id");
        Map<String, String> stuffDictList = CommonUtils.changeMap2(warehouseDao.queryStuffDict(), "stuffName", "id");
        Map<String, String> helpDict = CommonUtils.changeMap2(teamInfoDao.queryEmergencyPowerDictList(), "name", "id");
        Map<String, List<Map<String, String>>> result = PoiUtils.getResult(file, 2);
        List<Map<String, String>> list = completionMap(result.get("物资出入库导入模板"));
        List<String> userWarehouse = warehouseDao.queryStockByUserId(userId);
        boolean flag = true;
        Set<String> record = new HashSet<>();
        List<String> objects = new ArrayList<>();
        for (Map<String, String> map : list) {
            boolean stuffFlag = true;

            String warehouseId = "";
            String stuffId = "";
            StringBuffer exception = new StringBuffer();
            String warehouseName = map.get("仓库名称");
            String stuffName = map.get("物资名称");
            String identifier = map.get("编号");

            //物资名称为空，代表空行，过滤掉
            if (org.springframework.util.StringUtils.isEmpty(stuffName)) {
                continue;
            }

            //对仓库管理员和区县管理员角色做仓库权限校验，非仓库管理员放行
            if (stockLevel.equals(userLevel) || streetAdminLevel.equals(userLevel)) {
                if (!userWarehouse.contains(warehouseName)) {
                    exception.append("[用户没有该仓库权限]");
                    objects.add(exception.toString());
                    flag = false;
                    continue;
                }
            }

            //如果物资型号为null则put一个空字符串
            map.putIfAbsent("物资型号", "");

            if (!warehouseDictList.containsKey(warehouseName)) {
                exception.append("[仓库名称异常]");
                flag = false;
                stuffFlag = false;
            } else {
                warehouseId = warehouseDictList.get(warehouseName);
            }
            if (!stuffDictList.containsKey(stuffName)) {
                exception.append("[物资名称异常]");
                flag = false;
                stuffFlag = false;
            } else {
                stuffId = stuffDictList.get(stuffName);
            }
            try {
                int type = 1;
                BigDecimal num = new BigDecimal(map.get("物资数量"));
                if (stuffFlag) {
                    //查询数量
                    String operationType = map.get("操作类型");
                    switch (operationType) {
                        case "出库":
                            operationType = "1";
                            break;
                        case "入库":
                            operationType = "2";
                            break;
                    }
                    map.put("操作类型", operationType);
                    if (operationType.equals("1")) {
                        type = type * -1;
                    }
                    if (StringUtils.isNotBlank(warehouseId) && StringUtils.isNotBlank(stuffId)) {
                        String brand = map.get("物资品牌");
                        String model = map.get("物资型号");
                        BigDecimal warehouseNum = warehouseDao.queryStuffNum(warehouseId, stuffId, brand, model);
                        if (warehouseNum != null || "2".equals(operationType)) {
                            if (warehouseNum == null) {
                                warehouseNum = BigDecimal.ZERO;
                            }
                            BigDecimal bigDecimal = warehouseNum.add(new BigDecimal(String.valueOf(num.multiply(new BigDecimal(type)))));
                            if (bigDecimal.compareTo(BigDecimal.ZERO) >= 0) {
                                if (flag) {
                                    //加入集合中
                                    String helpType = map.get("救援类别");
                                    if (helpDict.containsKey(helpType)) {
                                        map.put("helpTypeId", helpDict.get(helpType));
                                    } else {
                                        map.put("救援类别", "其他");
                                        map.put("helpTypeId", "403");
                                    }
                                    record.add(warehouseId + "---" + operationType + "---" + identifier);
                                    map.put("stockId", warehouseId);
                                    map.put("stuffId", stuffId);
                                    map.put("stockStuffNum", bigDecimal + "");
                                }
                            } else {
                                exception.append("[物资出库数量高于出库存量]");
                            }
                        } else {
                            exception.append("[该仓库未包含该物资]");
                        }
                    }
                }
            } catch (Exception e) {
                exception.append("[物资数量异常]");
                e.printStackTrace();
            }
            if (exception.length() > 0) {
                exception.insert(0, "excel第" + map.get("row") + "行异常:");
                flag = false;
                objects.add(exception.toString());
            }
        }
        if (flag) {
            Map<String, String> operationIds = new HashMap<>();
            String originalFilename = file.getOriginalFilename();
            List<Map<String, String>> records = new ArrayList<>();
            String nowDate = DateUtil.getStringDate();
            for (String s : record) {
                String[] split = s.split("---");
                Map<String, String> map = new HashMap<>();
                String operationId = UUID.randomUUID().toString();
                map.put("id", operationId);
                map.put("fileName", originalFilename);
                map.put("warehouse", split[0]);
                map.put("userId", userId);
                map.put("operationType", split[1]);
                map.put("operationTime", nowDate);
                map.put("identifier", split[2]);
                operationIds.put(split[0], operationId);
                records.add(map);
            }
            for (Map<String, String> map : list) {
                String s = map.get("stockId");
                map.put("operationId", operationIds.get(s));
                map.put("operationTime", nowDate);
            }
            //插入数据
            warehouseDao.saveStuffOperationRecord(records);
            warehouseDao.saveStuffOperationRecordInfo(list);
            for (Map<String, String> map : list) {
                String s1 = map.get("operationId");
                warehouseDao.completionStuffOperationInfo(s1);
            }
            //更新原始数量表数量
            warehouseDao.saveResourceStockStuffNum(list);
        }
        if (objects.size() > 0) {
            Result<Object> result1 = new Result<>();
            result1.setCode(500);
            result1.setData(objects);
            result1.setMessage("导入数据异常");
            return result1;
        } else {
            return new Result(200, "导入数据成功");
        }

    }

    @Override
    public PageInfo stuffRecordDetail(String areaId, String stockName, String stuffName,
                                      String stuffModel, String stuffBrand, String helpTypeId, String operationType, String startTime, String endTime, String manager, Integer page, Integer pageSize, String userId, String userLevel) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        //如果不是仓库管理员和区县管理员，则查询全部
        if (!stockLevel.equals(userLevel) && !streetAdminLevel.equals(userLevel) && !teamLevel.equals(userLevel)
                && !socialLevel.equals(userLevel) && !companyLevel.equals(userLevel)) {
            userId = null;
        }
        return new PageInfo<>(warehouseDao.stuffRecordDetail(areaId, stockName, stuffName, stuffModel, stuffBrand,
                helpTypeId, operationType, startTime, endTime, manager, userId));
    }

    @Override
    public Result deleteOperationRecordByStock(String stockId, String userId) {
        Result result = new Result();
        int i = warehouseDao.deleteOperationRecordByStock(stockId, userId);
        result.setData(i);
        return result;
    }

    @Override
    public Result deleteOperatiionInfoById(String id) {
        Result result = new Result();
        Map<String, Object> operatiionInfo = warehouseDao.selOperatiionInfoById(id);
        String operationType = String.valueOf(operatiionInfo.get("operation_type"));

        if ("1".equals(operationType)) {
            warehouseDao.updateStockStuffNumIn(operatiionInfo);
        } else {
            warehouseDao.updateStockStuffNumOut(operatiionInfo);

        }
        int i = warehouseDao.deleteOperationInfoById(id);
        result.setData(i);
        return result;
    }

    @Override
    @CountyRestricted(regionParam = "region")
    public PageInfo stuffRecordManagement(String region, String stockName, String stockType,
                                          String status, String userId, String userLevel,
                                          String checkStartTime, String checkEndTime,
                                          Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }

        List<Map<String, Object>> list = warehouseDao.stuffRecordManagement(region, stockName, stockType, userId, checkStartTime, checkEndTime);
        if (StringUtils.isNotBlank(status)) {
            list = list.stream().filter(map -> map.get("status").toString().equals(status)).collect(Collectors.toList());
        }
        return new PageInfo<>(list);
    }

    @Override
    public Result stuffRecordDetailByStockId(String stockId, String checkTime) {
        Result result = new Result();
        List list = warehouseDao.stuffRecordDetailByStockId(stockId, checkTime);
        result.setData(list);
        return result;
    }

    @Override
    @CountyRestricted
    public PageInfo stuffInventory(String areaId, String stockName, String stuffName, String stuffModel, String stuffBrand, String helpTypeId, Integer page, Integer pageSize, String userId, String regionId) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        if (stuffName != null) {
            stuffName = stuffName.replace("%", "\\%");
        }
        return new PageInfo<>(warehouseDao.stuffInventory(areaId, stockName, stuffName, stuffModel, stuffBrand, helpTypeId, userId, regionId));
    }

    @Override
    public Result stockStuffCheck(JSONObject jsonObject) {
        Result result = new Result();
        try {
            warehouseDao.updateStockStuffCheckTime(jsonObject);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result stuffExcelExport(JSONObject json, String beginTime, String endTime, String type, String helpTypes) {
        if (StringUtils.isNotBlank(beginTime)) {
            beginTime = beginTime + " 00:00:00";
        }
        if (StringUtils.isNotBlank(endTime)) {
            endTime = endTime + " 23:59:59";
        }

        String stockIds = json.getString("stockIds");
        String stuffIds = json.getString("stuffIds");
        List<Map<String, String>> list = new ArrayList<>();
        if ("1".equals(type)) {
            list = warehouseDao.queryStuffOperationDeatil(stockIds, beginTime, endTime, stuffIds);
            //过滤出对应救援类型的数据
            list = filterListByHelpTypes1(helpTypes, list);
            for (Map<String, String> map : list) {
                map.put("stuffAllNum", warehouseDao.queryStuffNumByName(map.get("stuffName"), map.get("STOCK_NAME"), type, map.get("model"), map.get("brand")));
            }
        } else if ("2".equals(type)) {
            list = warehouseDao.
                    queryStuffOperationDeatilByArea(stockIds, beginTime, endTime, stuffIds);
            //过滤出对应救援类型的数据
            list = filterListByHelpTypes1(helpTypes, list);
            for (Map<String, String> map : list) {
                map.put("stuffAllNum", warehouseDao.queryStuffNumByName(map.get("stuffName"), map.get("STOCK_NAME"), type, map.get("model"), map.get("brand")));
            }
        }
        if (list.size() > 0) {
            String fileName = "物资台账" + DateUtil.getUserDate("yyyyMMddhhmmss");
            PoiUtils.saveExcel(beginTime, endTime, filePath + fileName + ".xlsx", list);
            return new Result(fileUrl + fileName + ".xlsx");
        } else {
            if ("1".equals(type)) {
                return new Result(500, "该仓库没有物资");
            } else if ("2".equals(type)) {
                return new Result(500, "该区县没有物资");
            }
            return new Result(500, "该仓库没有物资");
        }
    }

    private List<Map<String, String>> filterListByHelpTypes1(String helpTypes, List<Map<String, String>> list) {
        if (StringUtils.isNotBlank(helpTypes) && !list.isEmpty()) {
            List<String> paramHelpTypes = Arrays.asList(helpTypes.split(","));
            list = list.stream().filter(map -> {
                boolean flag = false;
                String value = map.get("helpType");
                if (StringUtils.isNotBlank(value)) {
                    List<String> oriHelpTypes = Arrays.asList(value.split(","));
                    if (!Collections.disjoint(oriHelpTypes, paramHelpTypes)) {
                        flag = true;
                    }
                }
                return flag;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public JSONArray warehouseDetail(String userId) {
        List<Map<String, String>> list = warehouseDao.queryWareHouseInfo();
        LinkedHashMap<String, List<Map>> trees = CommonUtils.changeMap3(list, "area_name");
        JSONArray array = new JSONArray();
        for (String s : trees.keySet()) {
            List<Map> maps = trees.get(s);
            Map map = maps.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", map.get("area_code"));
            jsonObject.put("label", map.get("area_name"));
            JSONArray array1 = new JSONArray();
            for (Map map1 : maps) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", map1.get("id"));
                jsonObject1.put("label", map1.get("STOCK_NAME"));
                jsonObject1.put("helpTypes", map1.get("HELP_TYPE"));
                jsonObject1.put("storageLevel", map1.get("storageLevel"));
                jsonObject1.put("beManagedType", map1.get("beManagedType"));
                jsonObject1.put("stockType", map1.get("stockType"));
                array1.add(jsonObject1);
            }
            jsonObject.put("children", array1);
            array.add(jsonObject);
        }
        return array;
    }

    @Override
    public JSONArray warehouseDetailById(String userId) {
        List<Map<String, String>> list = warehouseDao.queryWareHouseInfoById(userId);
        LinkedHashMap<String, List<Map>> trees = CommonUtils.changeMap3(list, "area_name");
        JSONArray array = new JSONArray();
        for (String s : trees.keySet()) {
            List<Map> maps = trees.get(s);
            Map map = maps.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", map.get("area_code"));
            jsonObject.put("label", map.get("area_name"));
            JSONArray array1 = new JSONArray();
            for (Map map1 : maps) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", map1.get("id"));
                jsonObject1.put("label", map1.get("STOCK_NAME"));
                jsonObject1.put("helpTypes", map1.get("HELP_TYPE"));
                array1.add(jsonObject1);
            }
            jsonObject.put("children", array1);
            array.add(jsonObject);
        }
        return array;
    }

    @Override
    public List<Map<String, Object>> warehouseStuffInfo(String stockId, String helpId) {
        return warehouseDao.warehouseStuffInfo(stockId, helpId);
    }

    @Override
    public Result stuffExcelExport2(JSONObject json, String beginTime, String endTime, String helpTypes) {
        String stockIds = json.getString("stockIds");
        String stuffIds = json.getString("stuffIds");
        List<Map> mapsC = warehouseDao.queryOperationInfo(stockIds, beginTime, endTime, "1", stuffIds);
        List<Map> mapsR = warehouseDao.queryOperationInfo(stockIds, beginTime, endTime, "2", stuffIds);
        List<Map> mapsT = warehouseDao.queryOperationInfo(stockIds, beginTime, endTime, null, stuffIds);
        Map<String, List<Map>> stockOperationR = new HashMap<>();
        Map<String, List<Map>> stockOperationC = new HashMap<>();
        Map<String, List<Map>> stockOperationT = new HashMap<>();
        //过滤出对应救援类型的数据
        mapsC = filterListByHelpTypes2(helpTypes, mapsC);
        mapsR = filterListByHelpTypes2(helpTypes, mapsR);
        mapsT = filterListByHelpTypes2(helpTypes, mapsT);
        if (!mapsC.isEmpty()) {
            stockOperationC = CommonUtils.changeMapT(mapsC, "stockName");
        }
        if (!mapsR.isEmpty()) {
            stockOperationR = CommonUtils.changeMapT(mapsR, "stockName");
        }
        if (!mapsT.isEmpty()) {
            stockOperationT = CommonUtils.changeMapT(mapsT, "stockName");
        }
        Map<String, Object> map = new HashMap<>();
        for (String s : stockOperationR.keySet()) {
            List<Map> maps = stockOperationR.get(s);
            Map<String, Object> r = new HashMap<>();
            r.put("r", maps);
            map.put(s, r);
        }
        for (String s : stockOperationC.keySet()) {
            List<Map> maps = stockOperationC.get(s);
            if (map.containsKey(s)) {
                Map<String, Object> info = (Map<String, Object>) map.get(s);
                info.put("c", maps);
            } else {
                Map<String, Object> c = new HashMap<>();
                c.put("c", maps);
                map.put(s, c);
            }
        }
        for (String s : stockOperationT.keySet()) {
            List<Map> maps = stockOperationT.get(s);
            Map<String, Object> info = (Map<String, Object>) map.get(s);
            info.put("t", maps);
        }
        if (map.size() > 0) {
            String fileName = "物资台账2-" + DateUtil.getUserDate("yyyyMMddhhmmss");
            PoiUtils.saveExcel(map, filePath + fileName + ".xlsx");
            return new Result(fileUrl + fileName + ".xlsx");
        } else {
            return new Result(500, "该仓库没有物资");
        }


    }

    private List<Map> filterListByHelpTypes2(String helpTypes, List<Map> list) {
        if (StringUtils.isNotBlank(helpTypes) && !list.isEmpty()) {
            List<String> paramHelpTypes = Arrays.asList(helpTypes.split(","));
            list = list.stream().filter(map -> {
                boolean flag = false;
                String value = map.get("helpType").toString();
                if (StringUtils.isNotBlank(value)) {
                    List<String> oriHelpTypes = Arrays.asList(value.split(","));
                    if (!Collections.disjoint(oriHelpTypes, paramHelpTypes)) {
                        flag = true;
                    }
                }
                return flag;
            }).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public Result materialsManagement(String secondLevelType) {
        Result result = new Result();
        List<Map<String, Object>> mapList = new LinkedList<>();
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            Map<String, Object> dictAreaMap = new LinkedHashMap<>();
            List<Map<String, Object>> dictAreaList = warehouseDao.selDictArea();
            List list1 = new LinkedList();

            for (Map<String, Object> dictArea : dictAreaList) {
                Map<String, Object> map1 = new LinkedHashMap<>();
                String dictId = String.valueOf(dictArea.get("dictId"));
                String areaName = String.valueOf(dictArea.get("areaName"));
                BigDecimal materialsCount = warehouseDao.selMaterialsCount(dictId, null, null);
                map1.put("name", areaName);
                map1.put("num", materialsCount);
                list1.add(map1);
            }
            dictAreaMap.put("type", "按辖区");
            dictAreaMap.put("list", list1);


            Map<String, Object> storageTypeMap = new LinkedHashMap<>();
            List list2 = new LinkedList();

            List<Map<String, Object>> storageTypeList = warehouseDao.selStorageType();
            for (Map<String, Object> storageType : storageTypeList) {
                Map<String, Object> map2 = new LinkedHashMap<>();
                if (storageType.get("id") == null) {
                    continue;
                }
                String storageName = storageType.get("storageName").toString();
                String storageId = storageType.get("id").toString();
                BigDecimal materialsCount = warehouseDao.selMaterialsCount(null, null, storageId);
                map2.put("name", storageName);
                map2.put("num", materialsCount);
                list2.add(map2);
            }
            storageTypeMap.put("type", "按存储类型");
            storageTypeMap.put("list", list2);

            Map<String, Object> emergencyPowerDictMap = new LinkedHashMap<>();
            List list3 = new LinkedList();
            Map<String, Object> map4 = new LinkedHashMap<>();
            map4.put("name", "卫生防疫");
            if (StringUtils.isNotBlank(secondLevelType)) {
                if ("市辖区".equals(secondLevelType)) {
                    secondLevelType = "市直管";
                }
            }
            BigDecimal materialsCount1 = warehouseDao.selMaterialsCount(secondLevelType, "21", null);
            map4.put("num", materialsCount1);
            list3.add(map4);
            List<Map> EmergencyPowerDictList = teamInfoDao.queryEmergencyPowerDictList();
            for (Map<String, Object> EmergencyPowerDict : EmergencyPowerDictList) {

                String helpTypeId = EmergencyPowerDict.get("id").toString();
                if (!"21".equals(helpTypeId)) {
                    Map<String, Object> map3 = new LinkedHashMap<>();
                    BigDecimal materialsCount = warehouseDao.selMaterialsCount(secondLevelType, helpTypeId, null);
                    map3.put("name", EmergencyPowerDict.get("name").toString());
                    map3.put("num", materialsCount);
                    list3.add(map3);
                    materialsCount1 = materialsCount1.add(materialsCount);
                }

            }
            emergencyPowerDictMap.put("type", "按救援类型");
            emergencyPowerDictMap.put("list", list3);
            mapList.add(emergencyPowerDictMap);
//            mapList.add(dictAreaMap);
//            mapList.add(storageTypeMap);
            List<Map<String, Object>> materialsList1 = warehouseDao.selMaterialsByEmergencyPowerDict(secondLevelType, null, null, null, null);
            BigDecimal sumNum = new BigDecimal(BigInteger.ZERO);
            for (Map<String, Object> map5 : materialsList1) {
                BigDecimal count = (BigDecimal) map5.get("num");
                sumNum = sumNum.add(count);
            }
            map.put("sum", sumNum);
            map.put("tableData", mapList);
            result.setData(map);
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setCode(500);
            return result;
        }
    }

    @Override
    public Result materialsCategories(String secondLevelType, String nameType, String keyword, Integer page, Integer pageSize) {
        Result result = new Result();
        Integer start = null;
        Integer type = 3;

        if (page != null && pageSize != null) {
            start = pageSize * (page - 1);
        }
        List<Map<String, Object>> maplist6 = new LinkedList<>();
        List<Map<String, Object>> mapList = new LinkedList<>();


        if (type == 3) {
            List<Map<String, Object>> materialsList = warehouseDao.selMaterialsByEmergencyPowerDict(secondLevelType, nameType, keyword, start, pageSize);
            List<Map<String, Object>> materialsList1 = warehouseDao.selMaterialsByEmergencyPowerDict(secondLevelType, nameType, keyword, null, null);
            BigDecimal sumNum = new BigDecimal(BigInteger.ZERO);
            for (Map<String, Object> map : materialsList1) {
                BigDecimal count = (BigDecimal) map.get("num");
                sumNum = sumNum.add(count);
            }

            Map<String, Object> map6 = new LinkedHashMap<>();
            for (Map<String, Object> stuffNames : materialsList) {
                List<Map<String, Object>> list = new LinkedList();
                String stuffName = stuffNames.get("stuffName").toString();
                String stuffType = stuffNames.get("type").toString();
                String parentType = stuffNames.get("parentType").toString();
                Map<String, Object> map1 = new LinkedHashMap<>();
                map1.put("name", stuffName);
                map1.put("stuffType", stuffType);
                map1.put("parentType", parentType);
                BigDecimal num = new BigDecimal(BigInteger.ZERO);
                List<Map<String, Object>> mapList1 = warehouseDao.selMaterialsByEmergencyPowerDict1(secondLevelType, nameType, keyword, stuffName, null, null);
                String stuffUnit = mapList1.get(0).get("stuffUnit").toString();
                for (Map<String, Object> map : mapList1) {
                    Map<String, Object> map2 = new LinkedHashMap<>();
                    BigDecimal count = (BigDecimal) map.get("num");
                    num = num.add(count);
                    String model = map.get("name").toString();
                    stuffUnit = map.get("stuffUnit").toString();
                    if (model.equals("") || model == null) {
                        model = stuffName;
                    }
                    if (count.compareTo(BigDecimal.ZERO) > 0) {
                        map2.put("name", model);
                        map2.put("num", count + stuffUnit);
                        list.add(map2);
                    }
                }
                if (num.compareTo(BigDecimal.ZERO) > 0) {
                    num = num.setScale(0, BigDecimal.ROUND_HALF_UP);
                    map1.put("num", num);
                    map1.put("list", list);
                    map1.put("stuffUnit", stuffUnit);
                    mapList.add(map1);
                }
            }
//            }
            Set<String> set = new HashSet<>();
            Set<String> parentTypeSet = new HashSet<>();
            for (Map<String, Object> map : mapList) {
                set.add(map.get("stuffType").toString());
                parentTypeSet.add(map.get("parentType").toString());
            }
            //转树
            JSONArray jsonArray = new JSONArray();
            List<Map<String, Object>> tempList;
            int childSum;
            for (String stuffType : set) {
                JSONObject jsonObject = new JSONObject();
                tempList = mapList.stream().
                        filter(map -> (map.get("stuffType").equals(stuffType))).
                        collect(Collectors.toList());
                childSum = tempList.stream().mapToInt(map -> Integer.parseInt(map.get("num").toString())).sum();
                jsonObject.put("list", tempList);
                jsonObject.put("sum", childSum);
                jsonObject.put("parent", tempList.get(0).get("parentType").toString());
                jsonArray.add(jsonObject);
            }
            //加单位
            mapList.forEach(map -> {
                map.put("num", map.get("num").toString() + map.get("stuffUnit"));
            });
            JSONArray jsonArrayParent = new JSONArray();
            JSONArray tempJsonArray;
            int parentSum;
            for (String parentType : parentTypeSet) {
                JSONObject jsonObject = new JSONObject();
                tempJsonArray = jsonArray.stream().
                        filter(js -> ((JSONObject) js).getString("parent").equals(parentType)).
                        collect(Collectors.toCollection(JSONArray::new));
                parentSum = tempJsonArray.stream().mapToInt(js -> ((JSONObject) js).getInteger("sum")).sum();
                jsonObject.put("list", tempJsonArray);
                jsonObject.put("sum", parentSum);
                jsonArrayParent.add(jsonObject);
            }

            sumNum = sumNum.setScale(0, BigDecimal.ROUND_HALF_UP);
            map6.put("sum", sumNum);
            map6.put("list", jsonArrayParent);
            maplist6.add(map6);

            parentTypeSet.remove("");
            set.remove("");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("parentTypeCount", parentTypeSet.size());
            resultMap.put("stuffCount", materialsList.size());
            resultMap.put("typeCount", set.size());
            maplist6.add(resultMap);
        }
        result.setData(maplist6);
        return result;

    }

    @Override
    public PageInfo materialWarehouse(String secondLevelType, String nameType, String thirdLevelType, String fifthLevelType, String keyword, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        List<Map<String, Object>> mapList = warehouseDao.getStockNameList(secondLevelType, nameType, thirdLevelType, keyword);
        List<Map<String, Object>> list = new LinkedList<>();
        for (Map<String, Object> stockNames : mapList) {
            String stockName = String.valueOf(stockNames.get("stockName"));
            String stuffId = String.valueOf(stockNames.get("STUFF_ID"));
            Map<String, Object> map = new LinkedHashMap<>();
            int sum = 0;
            String stuffName = null;
            String mainPeople = null;
            String dutyPhone = null;
            String stuffUnit = null;
            List<Map<String, Object>> materialWarehouseList = warehouseDao.materialWarehouse(secondLevelType, nameType, thirdLevelType, fifthLevelType, stockName, stuffId, keyword);
            for (Map<String, Object> materialWarehouses : materialWarehouseList) {
                int stuffNumber = Integer.parseInt(materialWarehouses.get("stuffNumber").toString());
                stuffName = materialWarehouses.get("stuff_name").toString();
                mainPeople = materialWarehouses.get("mainPeople").toString();
                dutyPhone = materialWarehouses.get("dutyPhone").toString();
                stuffUnit = materialWarehouses.get("stuffUnit").toString();
                //添加单位
                materialWarehouses.put("stuffNumber", stuffNumber + stuffUnit);
                sum += stuffNumber;
                materialWarehouses.remove("STOCK_NAME");
                materialWarehouses.remove("stuff_name");
                materialWarehouses.remove("mainPeople");
                materialWarehouses.remove("dutyPhone");
                materialWarehouses.remove("stuffUnit");
            }
            if (sum > 0) {
                map.put("STOCK_NAME", stockName);
                map.put("stuffNumber", sum + stuffUnit);
                map.put("stuff_name", stuffName);
                map.put("mainPeople", mainPeople);
                map.put("dutyPhone", dutyPhone);
                map.put("list", materialWarehouseList);
                list.add(map);
            }
        }

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo materialDetail(String secondLevelType, String nameType, String thirdLevelType, String fifthLevelType, String fourthLevelType, String keyword, String startTime, String endTime, String operationType, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        Integer operation = null;
        if ("入库".equals(operationType)) {
            operation = 2;
        }
        if ("出库".equals(operationType)) {
            operation = 1;
        }

        List<Map<String, Object>> list = warehouseDao.materialDetail(secondLevelType, nameType, thirdLevelType, fifthLevelType, fourthLevelType, keyword, startTime, endTime, operation);

        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {

                BigDecimal STUFF_NUMBER1 = new BigDecimal(o1.get("stuff_operation_num").toString());
                BigDecimal STUFF_NUMBER2 = new BigDecimal(o2.get("stuff_operation_num").toString());

                return STUFF_NUMBER2.compareTo(STUFF_NUMBER1);
            }
        });

        return new PageInfo<>(list);
    }

    @Override
    public Result materialDetailApp(String secondLevelType, String nameType, String thirdLevelType, String fifthLevelType, String fourthLevelType, String keyword, String startTime, String endTime, String operationType, Integer page, Integer pageSize) {
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        try {
            Integer operation = null;
            if ("入库".equals(operationType)) {
                operation = 2;
            }
            if ("出库".equals(operationType)) {
                operation = 1;
            }
            List<Map<String, Object>> countList = warehouseDao.materialDetail(secondLevelType, nameType, thirdLevelType, fifthLevelType, fourthLevelType, keyword, startTime, endTime, operation);
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            List<Map<String, Object>> list = warehouseDao.materialDetail(secondLevelType, nameType, thirdLevelType, fifthLevelType, fourthLevelType, keyword, startTime, endTime, operation);
            list.sort((o1, o2) -> {
                BigDecimal STUFF_NUMBER1 = new BigDecimal(o1.get("stuff_operation_num").toString());
                BigDecimal STUFF_NUMBER2 = new BigDecimal(o2.get("stuff_operation_num").toString());
                return STUFF_NUMBER2.compareTo(STUFF_NUMBER1);
            });
            int inCount = 0;
            int outCount = 0;
            BigDecimal outStuff = new BigDecimal(BigInteger.ZERO);
            BigDecimal inStuff = new BigDecimal(BigInteger.ZERO);
            for (Map<String, Object> map : countList) {
                if ("出库".equals(map.get("operation_type"))) {
                    outCount += 1;
                    outStuff = outStuff.add((BigDecimal) map.get("stuff_operation_num"));
                } else {
                    inCount += 1;
                    inStuff = inStuff.add((BigDecimal) map.get("stuff_operation_num"));
                }
            }
            //加单位
            for (Map<String, Object> map : list) {
                map.put("stuff_operation_num", map.get("stuff_operation_num").toString() + map.get("stuffUnit"));
                map.put("STUFF_NUMBER", map.get("STUFF_NUMBER").toString() + map.get("stuffUnit"));
            }

            Map<String, BigDecimal> count = new HashMap<>(4);
            count.put("inCount", new BigDecimal(inCount));
            count.put("outCount", new BigDecimal(outCount));
            count.put("inStuff", inStuff);
            count.put("outStuff", outStuff);
            jsonObject.put("data", new PageInfo<>(list));
            jsonObject.put("count", count);
            result.setData(jsonObject);
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public Result selDictArea() {
        Result result = new Result();
        List<Map<String, Object>> list = warehouseDao.selDictArea();
        result.setData(list);
        return result;
    }

    @Override
    public Result totalAmountMaterials(String nameType) {
        Result result = new Result();
        try {
            List<Map<String, Object>> list = warehouseDao.selDictArea();
            Integer sumNum = 0;
            for (Map<String, Object> dictAreas : list) {
                String secondLevelType = dictAreas.get("areaName").toString();
                Integer num = 0;
                List<Map<String, Object>> materialsList = warehouseDao.selMaterialsByEmergencyPowerDict(secondLevelType, nameType, null, null, null);
                for (Map<String, Object> map : materialsList) {
                    Integer count = Integer.parseInt(map.get("num").toString());
                    num += count;
                }
                dictAreas.put("num", num);
                dictAreas.remove("dictId");
                sumNum += num;
            }

            Map<String, Object> map = new LinkedHashMap<>();

            map.put("sum", sumNum);
            map.put("list", list);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public Result classifiedDsplayGoods(String secondLevelType, String nameType, String keyword, Integer page, Integer pageSize) {
        Result result = new Result();
        Integer start = 0;
        Integer type = 3;
        try {
            if (page != null && pageSize != null) {
                start = pageSize * (page - 1);
            }

            List<Map<String, Object>> mapList = warehouseDao.selStockNameList(secondLevelType, nameType, keyword, start, pageSize);
            List<Map<String, Object>> resultList = new LinkedList<>();
            for (Map<String, Object> stockNames : mapList) {
                String stockName = stockNames.get("stockName").toString();
                List<Map<String, String>> materialWarehouseList = warehouseDao.materialWarehouse1(secondLevelType, nameType, null, null, stockName, null, keyword);
                if (materialWarehouseList.size() > 0) {
                    int stuffNumber = materialWarehouseList.stream().mapToInt(map -> Integer.parseInt(map.get("stuffNumber"))).sum();
                    //加单位
                    for (Map<String, String> map : materialWarehouseList) {
                        map.put("stuffNumber", map.get("stuffNumber") + map.get("stuffUnit"));
                    }
                    stockNames.put("stuffNumber", Integer.toString(stuffNumber));
                    stockNames.put("list", materialWarehouseList);
                    resultList.add(stockNames);
                }
            }
            result.setData(resultList);
        } catch (Exception e) {
            result.setData(500);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private List<Map<String, String>> completionMap(List<Map<String, String>> list) {
        int i = 2;
        if (list != null && list.size() > 0) {
            String wareHouseName = "";
            String date = "";
            String manager = "";
            String number = "";
            String unit = "";
            String operationType1 = "";
            Iterator<Map<String, String>> it = list.iterator();
            while (it.hasNext()) {
                i++;
                Map<String, String> map = it.next();
                String s = map.get("物资名称");
                if (StringUtils.isBlank(s)) {
                    it.remove();
                    continue;
                } else {
                    map.put("row", i + "");
                }
                if (StringUtils.isNotBlank(map.get("仓库名称"))) {
                    wareHouseName = map.get("仓库名称");
                } else {
                    map.put("仓库名称", wareHouseName);
                }
                if (StringUtils.isNotBlank(map.get("出入库日期"))) {
                    date = map.get("出入库日期").replace("//", "-").replace(" 00:00:00", "");
                }
                map.put("出入库日期", date);
                if (StringUtils.isNotBlank(map.get("经办人"))) {
                    manager = map.get("经办人");
                } else {
                    map.put("经办人", manager);
                }
                if (StringUtils.isNotBlank(map.get("编号"))) {
                    number = map.get("编号");
                } else {
                    map.put("编号", number);
                }
                if (StringUtils.isNotBlank(map.get("物资来源/领用单位"))) {
                    unit = map.get("物资来源/领用单位");
                } else {
                    map.put("物资来源/领用单位", unit);
                }
                if (StringUtils.isNotBlank(map.get("操作类型"))) {
                    operationType1 = map.get("操作类型");
                } else {
                    map.put("操作类型", operationType1);
                }
            }
        }
        return list;

    }

    @Override
    public Result stuffExcelExport3(JSONObject json, String helpTypes) {
        Result result = new Result();
        try {
            String stockIds = json.getString("stockIds");
            String stuffIds = json.getString("stuffIds");
            List<Map<String, String>> list = warehouseDao.stuffExcelExport3(stockIds, stuffIds);
            list = filterListByHelpTypes1(helpTypes, list);
            if (!list.isEmpty()) {
                list = list.stream().filter(
                        map -> (StringUtils.isNotBlank(map.get("stuff")))).collect(Collectors.toList());
                String fileName = "物资统计(横排)" + DateUtil.getUserDate("yyyyMMddhhmmss");
                PoiUtils.saveStuffCountRaw(filePath + fileName + ".xlsx", list);
                return new Result(fileUrl + fileName + ".xlsx");
            } else {
                return new Result("该仓库没有物资");
            }
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result getStockIdByArea(String regionId) {
        Result result = new Result();
        try {
            result.setData(warehouseDao.queryStockIdByArea(regionId));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result stuffInventoryLinZi(String stuffType, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(warehouseDao.stuffInventoryLinZi(stuffType)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}

package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.SyncCar;
import com.znv.manage.common.carSync.CarRequestUtil;
import com.znv.manage.common.carSync.IoTApiClientBuilderParams;
import com.znv.manage.common.carSync.IoTApiRequest;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.SyncCarDao;
import com.znv.manage.service.SyncCarService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TDataSyncCar表服务实现类
 *
 * @author 0049003788
 * @since 2023-01-10 16:11:04
 */
@Service
@Slf4j
public class SyncCarServiceImpl implements SyncCarService {
    @Resource
    private SyncCarDao syncCarDao;

    private static final Map<String, String> PRODUCT_KEY_MAP;

    @Value("${car.sync.host:222.215.137.25:18080}")
    private String host;

    @Value("${car.sync.path:/openapi/cityhub/device/list}")
    private String path;

    @Value("${car.sync.fire.path:/openapi/cityhub/device/data/query}")
    private String firePath;

    @Value("${car.sync.appKey:23713761}")
    private String appKey;

    @Value("${car.sync.appSecret:3c42a88d3e294d9cbb0de5b8bc84b506}")
    private String appSecret;

    static {
        PRODUCT_KEY_MAP = new HashMap<>();
        PRODUCT_KEY_MAP.put("公交车", "5df8e106ae97470896cc@cc1qsM");
        PRODUCT_KEY_MAP.put("出租车-凯瑞", "linkbridgejt809@lbigL5");
        PRODUCT_KEY_MAP.put("出租车-中科", "linkbridgejt809@lbw0Wx");
        PRODUCT_KEY_MAP.put("出租车-天行路安", "linkbridgejt809@lb0Iha");
        PRODUCT_KEY_MAP.put("救护车", "0ce90313bf2e4459b33a@ccpe0A");
    }

    private static final String PRODUCT_KEY_FIRE = "0faa1a3c6a814ef4a07c@cc1gbX";

    @Override
    public Result insert(SyncCar syncCar) {
        Result result = new Result();
        try {
            syncCarDao.insert(syncCar);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(SyncCar syncCar) {
        Result result = new Result();
        try {
            syncCarDao.update(syncCar);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteById(String license) {
        Result result = new Result();
        try {
            result.setData(syncCarDao.deleteById(license) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBatch(String ids) {
        Result result = new Result();
        try {
            result.setData(syncCarDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(String license, String dept, String online, String type, String districtCode, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(syncCarDao.queryByCondition(license, dept, online, type, districtCode)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "baseTransactionManager")
    public void sync() {
        log.info("开始同步对接车辆信息");
        int result = 0;
        IoTApiClientBuilderParams params = new IoTApiClientBuilderParams();
        params.setAppKey(appKey);
        params.setAppSecret(appSecret);
        int pageNo = 1;
        int pageSize = 500;
        List<SyncCar> carList = new ArrayList<>();
        try {
            for (Map.Entry<String, String> entry : PRODUCT_KEY_MAP.entrySet()) {
                while (true) {
                    IoTApiRequest request = new IoTApiRequest();
                    request.putParam("productKey", entry.getValue());
                    request.putParam("pageNo", pageNo);
                    request.putParam("pageSize", pageSize);
                    String response = CarRequestUtil.postRequest(host, path, request, params);
                    JSONObject responseJson = JSON.parseObject(response);
                    if (responseJson.getInteger("code") == 200) {
                        JSONArray items = responseJson.getJSONObject("data").getJSONArray("items");
                        if (CollectionUtils.isEmpty(items)) {
                            break;
                        }
                        for (int i = 0; i < items.size(); i++) {
                            JSONObject jsonObject = items.getJSONObject(i);
                            SyncCar car = new SyncCar();
                            String license;
                            if ("公交车".equals(entry.getKey()) || "救护车".equals(entry.getKey())
                            ) {
                                license = jsonObject.getString("alias");
                            } else {
                                license = jsonObject.getString("deviceName");
                            }
                            car.setLicense(license);
                            car.setType(entry.getKey());
                            car.setAddress(jsonObject.getString("address"));
                            car.setDept(jsonObject.getString("supplier"));
                            JSONObject location = jsonObject.getJSONObject("deviceLocation");
                            car.setDistrictCode(location.getString("districtCode"));
                            car.setGpsx(location.getString("longitude"));
                            car.setGpsy(location.getString("latitude"));
                            car.setOnline("ONLINE".equals(jsonObject.getString("status")) ? "1" : "0");
                            if (StringUtils.isNotBlank(car.getLicense())) {
                                carList.add(car);
                            }
                        }
                    } else {
                        throw new BusinessException("获取车辆信息失败:" + response);
                    }
                    pageNo++;
                }
                pageNo = 1;
            }
            if (!CollectionUtils.isEmpty(carList)) {
                result += syncCarDao.insertOrUpdateBatch(carList);
            }
            log.info("同步对接车辆信息完成,共同步{}条", result);
        } catch (Exception e) {
            log.error("同步对接车辆失败:{}", e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "baseTransactionManager")
    public void syncFireEngine() {
        log.info("开始同步对接消防车辆信息");
        int result = 0;
        IoTApiClientBuilderParams params = new IoTApiClientBuilderParams();
        params.setAppKey(appKey);
        params.setAppSecret(appSecret);
        int pageNo = 1;
        int pageSize = 500;
        List<SyncCar> carList = new ArrayList<>();
        try {
            while (true) {
                IoTApiRequest request = new IoTApiRequest();
                request.putParam("productKey", PRODUCT_KEY_FIRE);
                request.putParam("pageNo", pageNo);
                request.putParam("pageSize", pageSize);
                String response = CarRequestUtil.postRequest(host, path, request, params);
                JSONObject responseJson = JSON.parseObject(response);
                if (responseJson.getInteger("code") == 200) {
                    JSONArray items = responseJson.getJSONObject("data").getJSONArray("items");
                    if (CollectionUtils.isEmpty(items)) {
                        break;
                    }
                    for (int i = 0; i < items.size(); i++) {
                        JSONObject jsonObject = items.getJSONObject(i);
                        SyncCar car = new SyncCar();
                        car.setLicense(jsonObject.getString("alias"));
                        car.setType("消防车");
                        car.setAddress(jsonObject.getString("address"));
                        car.setDept(jsonObject.getString("supplier"));
                        IoTApiRequest requestIn = new IoTApiRequest();
                        requestIn.putParam("productKey", PRODUCT_KEY_FIRE);
                        requestIn.putParam("deviceName", jsonObject.getString("deviceName"));
                        String responseIn = CarRequestUtil.postRequest(host, firePath, requestIn, params);
                        JSONArray array = JSON.parseObject(responseIn).getJSONArray("data");
                        if (!CollectionUtils.isEmpty(array)) {
                            for (int j = 0; j < array.size(); j++) {
                                JSONObject jsonObjectIn = array.getJSONObject(j);
                                if ("GeoLocation".equals(jsonObjectIn.getString("identifier"))) {
                                    JSONObject value = jsonObjectIn.getJSONObject("value");
                                    car.setGpsx(value.getString("Longitude"));
                                    car.setGpsy(value.getString("Latitude"));
                                }
                            }
                        }
                        car.setOnline("ONLINE".equals(jsonObject.getString("status")) ? "1" : "0");
                        if (StringUtils.isNotBlank(car.getLicense())) {
                            carList.add(car);
                        }

                    }
                } else {
                    throw new BusinessException("获取车辆信息失败:" + response);
                }
                pageNo++;
            }
            if (!CollectionUtils.isEmpty(carList)) {
                result += syncCarDao.insertOrUpdateBatch(carList);
            }
            log.info("同步对接车辆信息完成,共同步{}条", result);
        } catch (Exception e) {
            log.error("同步消防车辆失败:{}", e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}

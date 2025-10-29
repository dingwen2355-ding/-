package cn.wxgis.jc.sync.web.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.wxgis.jc.civilization.data.enums.EventCheckTypeEnum;
import cn.wxgis.jc.civilization.data.po.DataCheckItem;
import cn.wxgis.jc.civilization.data.po.DataCheckPoints;
import cn.wxgis.jc.civilization.data.po.DataCheckPointsType;
import cn.wxgis.jc.civilization.quota.po.QuotaAssessCycle;
import cn.wxgis.jc.common.constant.CacheConstants;
import cn.wxgis.jc.common.util.DateUtils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.data.service.*;
import cn.wxgis.jc.sync.web.service.DataCheckPointsService;
import cn.wxgis.jc.sync.web.service.SynchWmbService;
import cn.wxgis.jc.synch.common.config.UnifyConfig;
import cn.wxgis.jc.synch.common.config.WmbConfig;
import cn.wxgis.jc.synch.common.constant.WmbUrl;
import cn.wxgis.jc.synch.result.ResultVo;
import cn.wxgis.jc.synch.wmb.po.WorkEvent;
import cn.wxgis.jc.synch.wmb.po.WorkEventFile;
import cn.wxgis.jc.synch.wmb.vo.*;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysRegion;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SynchWmbServiceImpl implements SynchWmbService {

    @Autowired
    private UnifyConfig unifyConfig;
    @Autowired
    private WmbConfig wmbConfig;

    @Autowired
    private SysDeptService deptService;
    @Autowired
    private QuotaAssessCycleService assessCycleService;
    @Autowired
    private DataCheckPointsTypeService checkPointsTypeService;
    @Autowired
    private DataCheckItemService checkItemService;
    @Autowired
    private DataCheckPointsService checkPointsService;
    @Autowired
    private WorkEventService workEventService;
    @Autowired
    private WorkEventFileService workEventFileService;

    @Override
    public void synchCycle() {
        String token = this.getToken();
        String url = wmbConfig.getIp() + WmbUrl.getCycleUrl;
        Integer pageSize = 1000;
        String obj = HttpUtil.createGet(String.format(url, 1, pageSize))
                .addHeaders(getHeader())
                .header("Authorization", "token " + token)
                .timeout(CacheConstants.TIMEOUT).execute().body();
        WmbResultVo wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
        ResultVo resultVo = wmbResultVo.getResult();
        WmbCycleListVo wmbListVo = JSONObject.parseObject(resultVo.getData(), WmbCycleListVo.class);
        List<WmbCycleVo> cycleVoList = wmbListVo.getList();
        if (StringUtils.isNotEmpty(cycleVoList)) {
            // 查询数据库中现有考核周期
            List<QuotaAssessCycle> assessCycleList = assessCycleService.selectList(null);
            List<QuotaAssessCycle> saveList = new ArrayList<>();
            List<QuotaAssessCycle> updateList = new ArrayList<>();
            if (StringUtils.isEmpty(assessCycleList)) {
                // 如果现有数据库不存在考核周期事件，全部新增
                cycleVoList.forEach(cycle -> {
                   QuotaAssessCycle assessCycle = this.getQuotaAssessCycle(cycle);
                    saveList.add(assessCycle);
                });
            } else {
                cycleVoList.forEach(cycle -> {
                    if (assessCycleList.stream().anyMatch(dto -> dto.getName().equals(cycle.getName()))) {
                        QuotaAssessCycle assessCycle = assessCycleList.stream().filter(i -> i.getName().equals(cycle.getName())).findFirst().get();
                        assessCycle.setBeginTime(DateUtils.parseStringToBeginDate(cycle.getDefaultDate().replace("/","-")));
                        assessCycle.setFinishTime(DateUtils.parseStringToBeginDate(cycle.getPosttime().replace("/", "-")));
                        updateList.add(assessCycle);
                    } else {
                        QuotaAssessCycle assessCycle = this.getQuotaAssessCycle(cycle);
                        saveList.add(assessCycle);
                    }
                });
            }
            if (StringUtils.isNotEmpty(saveList)) {
                assessCycleService.insertBatch(saveList);
            }
            if (StringUtils.isNotEmpty(updateList)) {
                assessCycleService.updateBatchById(updateList);
            }
        }
    }
    private QuotaAssessCycle getQuotaAssessCycle(WmbCycleVo cycle) {
        QuotaAssessCycle assessCycle = new QuotaAssessCycle();
        assessCycle.setName(cycle.getName());
        assessCycle.setType(Integer.parseInt(EventCheckTypeEnum.ROUTINE.getCode()));
        assessCycle.setNumber(Integer.parseInt(cycle.getNameValue()));
        assessCycle.setBeginTime(DateUtils.parseStringToBeginDate(cycle.getDefaultDate().replace("/", "-")));
        assessCycle.setFinishTime(DateUtils.parseStringToBeginDate(cycle.getPosttime().replace("/", "-")));
        return assessCycle;
    }

    @Override
    public void synchCheckPointsType() {
        String token = this.getToken();
        List<WmbPointTypeVo> pointTypeVoList = this.pointTypeVoList(token);
        if (StringUtils.isNotEmpty(pointTypeVoList)) {
            List<DataCheckPointsType> saveList = new ArrayList<>();
            List<DataCheckPointsType> updateList = new ArrayList<>();
            List<DataCheckPointsType> list = checkPointsTypeService.selectList(null);
            pointTypeVoList.forEach(t -> {
                DataCheckPointsType data = list.stream().filter(i -> i.getId().equals(t.getId())).findFirst().get();
                if (StringUtils.isNull(data)) {
                    data = new DataCheckPointsType();
                    data.setId(t.getId());
                    data.setName(t.getName());
                    data.setOrderNum(Integer.parseInt(t.getSort()));
                    data.setCreateBy("system");
                    data.setCreateTime(new Date());
                    saveList.add(data);
                } else {
                    data.setName(t.getName());
                    data.setOrderNum(Integer.parseInt(t.getSort()));
                    data.setUpdateBy("system");
                    data.setUpdateTime(new Date());
                    updateList.add(data);
                }
            });
            if (StringUtils.isNotEmpty(saveList)) {
                checkPointsTypeService.insertBatch(saveList);
            }
            if (StringUtils.isNotEmpty(updateList)) {
                checkPointsTypeService.updateBatchById(updateList);
            }
        }
    }

    @Override
    public void synchCheckItem() {
        String token = this.getToken();
        List<WmbPointTypeVo> pointTypeVoList = this.pointTypeVoList(token);
        if (StringUtils.isNotEmpty(pointTypeVoList)) {
            List<DataCheckPointsType> checkPointsTypeList = checkPointsTypeService.selectList(null);
            List<DataCheckItem> checkItemList = checkItemService.selectList(null);
            pointTypeVoList.forEach(p -> {
                String name = p.getName();
                DataCheckPointsType checkPointsType = checkPointsTypeList.stream().filter(i -> i.getName().equals(name)).findFirst().get();
                String pid = checkPointsType.getId();

                List<WmbTypeCheckItemVo> typeCheckItemVoList = this.typeCheckItemVoList(token, p.getId());
                List<DataCheckItem> saveList = new ArrayList<>();
                List<DataCheckItem> updateList = new ArrayList<>();
                typeCheckItemVoList.forEach(i -> {
                    DataCheckItem data = checkItemList.stream().filter(c -> c.getId().equals(c.getId())).findFirst().get();
                    if (StringUtils.isNull(data)) {
                        data = new DataCheckItem();
                        data.setId(i.getId());
                        data.setName(i.getName());
                        data.setTypeId(pid);
                        data.setScore(new BigDecimal(i.getScore()).multiply(new BigDecimal(-1)));
                        data.setOrderNum(Integer.parseInt(i.getSort()));
                        data.setDeptIds(i.getDepartid());
                        data.setDeptNames(i.getDepartName());
                        data.setCreateBy("system");
                        data.setCreateTime(new Date());
                        saveList.add(data);
                    } else {
                        data.setName(i.getName());
                        data.setTypeId(pid);
                        data.setScore(new BigDecimal(i.getScore()).multiply(new BigDecimal(-1)));
                        data.setOrderNum(Integer.parseInt(i.getSort()));
                        data.setDeptIds(i.getDepartid());
                        data.setDeptNames(i.getDepartName());
                        data.setUpdateBy("system");
                        data.setUpdateTime(new Date());
                        updateList.add(data);
                    }
                });
                if (StringUtils.isNotEmpty(saveList)) {
                    checkItemService.insertBatch(saveList);
                }
                if (StringUtils.isNotEmpty(updateList)) {
                    checkItemService.updateBatchById(updateList);
                }
            });
        }
    }

    @Override
    public void synchCheckPoints() {
        String token = this.getToken();
        List<WmbCheckPointVo> checkPointVoList = this.checkPointVoList(token);
        if (StringUtils.isNotEmpty(checkPointVoList)) {
            List<DataCheckPoints> saveList = new ArrayList<>();
            List<DataCheckPoints> updateList = new ArrayList<>();
            List<SysDept> regionList = deptService.selectList(null);
            List<DataCheckPointsType> checkPointsTypeList = checkPointsTypeService.selectList(null);
            List<DataCheckPoints> checkPointsList = checkPointsService.selectList(null);
            checkPointVoList.forEach(p -> {
                String regionName = p.getRegionName();
                String typeName = p.getTypeName();
                List<SysDept> tempRegionList = regionList.stream().filter(i -> i.getName().equals(p.getRegionName())).collect(Collectors.toList());
                SysDept dept = new SysDept();
                if (StringUtils.isNotEmpty(tempRegionList)) {
                    dept = tempRegionList.get(0);
                }
                List<DataCheckPointsType> tempTypeList = checkPointsTypeList.stream().filter(i -> i.getName().equals(typeName)).collect(Collectors.toList());
                if (StringUtils.isEmpty(tempTypeList)) return;
                DataCheckPointsType checkPointsType = tempTypeList.get(0);
                String pointName = p.getPointName();
                List<DataCheckPoints> data = checkPointsList.stream().filter(i -> i.getName().equals(pointName)).collect(Collectors.toList());
                if (StringUtils.isEmpty(data)) {
                    DataCheckPoints checkPoints = new DataCheckPoints();
                    checkPoints.setName(p.getPointName());
                    checkPoints.setRegionCode(StringUtils.isNotNull(dept) ? dept.getId() : null);
                    checkPoints.setRegionName(StringUtils.isNotNull(dept) ? dept.getName() : null);
                    checkPoints.setTypeId(StringUtils.isNotNull(checkPointsType) ? checkPointsType.getId() : null);
                    checkPoints.setRemark(regionName + " || " + typeName);
                    checkPoints.setCreateBy("system");
                    checkPoints.setCreateTime(new Date());
                    saveList.add(checkPoints);
                } else {
                    DataCheckPoints checkPoints = data.get(0);
                    checkPoints.setRegionCode(StringUtils.isNotNull(dept) ? dept.getId() : null);
                    checkPoints.setRegionName(StringUtils.isNotNull(dept) ? dept.getName() : null);
                    checkPoints.setTypeId(StringUtils.isNotNull(checkPointsType) ? checkPointsType.getId() : null);
                    checkPoints.setRemark(regionName + " || " + typeName);
                    checkPoints.setUpdateBy("system");
                    checkPoints.setUpdateTime(new Date());
                    updateList.add(checkPoints);
                }
            });
            if (StringUtils.isNotEmpty(saveList)) {
                checkPointsService.insertBatch(saveList);
            }
            if (StringUtils.isNotEmpty(updateList)) {
                checkPointsService.updateBatchById(updateList);
            }
        }
    }

    @Override
    public void synchEvent() {
        String token = this.getToken();
        String url = wmbConfig.getIp() + WmbUrl.getEventListUrl;
        Integer pageSize = 1000;
        String obj = HttpUtil.createGet(String.format(url, 1, pageSize))
                .addHeaders(getHeader())
                .header("Authorization", "token " + token)
                .timeout(CacheConstants.TIMEOUT).execute().body();
        WmbResultVo wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
        ResultVo resultVo = JSONObject.parseObject(JSON.toJSONString(wmbResultVo.getResult()), ResultVo.class);
        WmbWorkEventDataVo wmbWorkEventDataVo = JSONObject.parseObject(resultVo.getData(), WmbWorkEventDataVo.class);
        Integer pageCount = wmbWorkEventDataVo.getCount()/pageSize;
        if (wmbWorkEventDataVo.getCount()/pageSize > 0) {
            pageCount = pageCount + 1;
        }
        List<WorkEvent> saveList = new ArrayList<>();
        List<WorkEventFile> saveFileList = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            obj = HttpUtil.createGet(String.format(url, i, pageSize))
                    .addHeaders(getHeader())
                    .header("Authorization", "token " + token)
                    .timeout(CacheConstants.TIMEOUT).execute().body();
            wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
            resultVo = JSONObject.parseObject(JSON.toJSONString(wmbResultVo.getResult()), ResultVo.class);
            wmbWorkEventDataVo = JSONObject.parseObject(resultVo.getData(), WmbWorkEventDataVo.class);
            List<WorkEvent> tempSaveEventList = new ArrayList<>();
            List<WorkEventFile> tempSaveEventFileList = new ArrayList<>();
            wmbWorkEventDataVo.getList().forEach(e -> {
                WorkEvent event = new WorkEvent();
                BeanUtils.copyProperties(e, event);
                tempSaveEventList.add(event);
                List<WorkEventFile> files = e.getFiles();
                files.forEach(f -> {
                    f.setEventId(e.getId());
                    tempSaveEventFileList.add(f);
                });
            });
            saveList.addAll(tempSaveEventList);
        }
        if (StringUtils.isNotEmpty(saveList)) {
            workEventService.delete(null);
            workEventService.insertBatch(saveList);
        }
        if (StringUtils.isNotEmpty(saveFileList)) {
            workEventFileService.delete(null);
            workEventFileService.insertBatch(saveFileList);
        }
    }


    /**
     * 获取token
     */
    private String getToken() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("code", true);
            map.put("username", new String(Base64.getDecoder().decode(wmbConfig.getUsername())));
            map.put("password", new String(Base64.getDecoder().decode(wmbConfig.getPassword())));
            JSONObject data = new JSONObject(map);
            String url = String.format(wmbConfig.getIp() + WmbUrl.getLoginUrl);
            Map<String, String> header = getHeader();
            log.info("请求地址:" + url);
            log.info("里约头部参数：" + JSON.toJSONString(header));
            System.out.println(HttpUtil.createPost(url).addHeaders(header).body(data.toJSONString()));
            String obj = HttpUtil.createPost(url).addHeaders(header).body(data.toJSONString())
                    .timeout(CacheConstants.TIMEOUT).execute().body();
            log.info("返回信息" + obj);
            WmbResultVo wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
            ResultVo resultVo = wmbResultVo.getResult();
            WmbLoginVo wmbLoginVo = JSONObject.parseObject(resultVo.getData(), WmbLoginVo.class);
            return wmbLoginVo.getToken();
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 点位类型列表
     */
    private List<WmbPointTypeVo> pointTypeVoList(String token) {
        String url = String.format(wmbConfig.getIp() + WmbUrl.getPointTypeListUrl);
        String obj = HttpUtil.createGet(url)
                .addHeaders(getHeader())
                .header("Authorization", "token " + token)
                .timeout(CacheConstants.TIMEOUT).execute().body();
        WmbResultVo wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
        ResultVo resultVo = wmbResultVo.getResult();
        WmbPointTypeListVo wmbListVo = JSONObject.parseObject(resultVo.getData(), WmbPointTypeListVo.class);
        List<WmbPointTypeVo> pointTypeVoList = wmbListVo.getList();
        return pointTypeVoList;
    }
    /**
     * 点位类型检查项
     */
    private List<WmbTypeCheckItemVo> typeCheckItemVoList(String token, String typeid) {
        String url = String.format(wmbConfig.getIp() + WmbUrl.getTypeCheckItemListUrl, typeid);
        String obj = HttpUtil.createGet(url)
                .addHeaders(getHeader())
                .header("Authorization", "token " + token)
                .timeout(CacheConstants.TIMEOUT).execute().body();
        WmbResultVo wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
        ResultVo resultVo = wmbResultVo.getResult();
        WmbTypeCheckItemListVo wmbListVo = JSONObject.parseObject(resultVo.getData(), WmbTypeCheckItemListVo.class);
        List<WmbTypeCheckItemVo> typeCheckItemVoList = wmbListVo.getList();
        return typeCheckItemVoList;
    }
    /**
     * 点位列表
     */
    private List<WmbCheckPointVo> checkPointVoList(String token) {
        String url = String.format(wmbConfig.getIp() + WmbUrl.getCheckPointListUrl);
        String obj = HttpUtil.createPost(url)
                .addHeaders(getHeader())
                .header("Authorization", "token " + token)
                .timeout(CacheConstants.TIMEOUT).execute().body();
        WmbResultVo wmbResultVo = JSONObject.parseObject(obj, WmbResultVo.class);
        ResultVo resultVo = wmbResultVo.getResult();
        List<WmbCheckPointVo> wmbCheckPointListVos = JSONArray.parseArray(resultVo.getData(), WmbCheckPointVo.class);
        return wmbCheckPointListVos;
    }


    private Map<String, String> getHeader() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String random = UUID.randomUUID().toString();
        String signature = SecureUtil.sha256(timestamp + unifyConfig.getPaasToken() + random + timestamp);
        Map<String, String> header = new HashMap<>();
        header.put("x-rio-paasid", unifyConfig.getPaasid());
        header.put("x-rio-timestamp", timestamp);
        header.put("x-rio-nonce", random);
        header.put("x-rio-signature", signature);
        return header;
    }
}

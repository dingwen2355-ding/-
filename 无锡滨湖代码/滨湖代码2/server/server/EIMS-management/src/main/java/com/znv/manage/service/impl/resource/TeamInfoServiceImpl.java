package com.znv.manage.service.impl.resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.RescueTeamInfo;
import com.znv.manage.common.bean.resource.RescueTeamInfoZn;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.PoiUtils;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.dao.resource.TeamInfoDao;
import com.znv.manage.service.resource.TeamBasicInfoService;
import com.znv.manage.service.resource.TeamInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeamInfoServiceImpl implements TeamInfoService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(TeamInfoServiceImpl.class);


    @Autowired
    private TeamInfoDao teamInfoDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private TeamBasicInfoService teamBasicInfoService;

    @Value("${excelUrl}")
    String fileUrl;

    @Value("${excelPath}")
    String filePath;

    /**
     * 新增
     *
     * @param rescueTeamInfo
     * @return
     */
    @Transactional(transactionManager = "baseTransactionManager", rollbackFor = Exception.class)
    @Override
    public Result insertTeam(RescueTeamInfo rescueTeamInfo) {
        Result result = new Result();
        try {
            // 队伍基本信息添加
            teamBasicInfoService.insertTeamInfo(rescueTeamInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 修改
     *
     * @param rescueTeamInfo
     * @return
     */
    @Transactional(transactionManager = "baseTransactionManager", rollbackFor = Exception.class)
    @Override
    public Result updateTeam(RescueTeamInfo rescueTeamInfo) {
        Result result = new Result();
        try {
            // 队伍基本信息修改
            teamBasicInfoService.updateTeamInfo(rescueTeamInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional(transactionManager = "baseTransactionManager", rollbackFor = Exception.class)
    @Override
    public Result deleteTeam(int id) {
        Result result = new Result();
        try {
            // 队伍基本信息删除
            teamBasicInfoService.deleteTeamInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result batchDeleteTeam(String ids) {
        Result result = new Result();
        try {
            // 队伍基本信息删除
            teamBasicInfoService.batchDeleteTeamInfo(ids);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public Result queryRescueTeamInfoByIdNew(int teamId) {
        Result result = new Result();
        try {
            Map<String, Object> rescueTeamInfo = teamInfoDao.queryRescueTeamInfoByIdNew(teamId);
            result.setData(rescueTeamInfo);
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryRescueTeamInfoById(int teamId) {
        Result result = new Result();
        try {
            // 查询队伍基本信息
            RescueTeamInfo rescueTeamInfo = teamBasicInfoService.queryRescueTeamInfoById(teamId);

            result.setData(rescueTeamInfo);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryRescueTeamInfo(String pointName, String helpType, String helpTypeName, String industry,
                                      String area, String teamName, String teamType, String apType,
                                      String storageLevel, String specialities, String userId,
                                      String userLevel, String planId, String eventId, String isAll, Integer pageStart, Integer pageLen) {
        Result result = new Result();
        Integer start = 0;
        if (pageStart != null && pageLen != null) {
            start = pageLen * (pageStart - 1);
        }
        try {
            String tempEventId = eventId;
            if ("1".equals(isAll)) {
                tempEventId = null;
            }
            List<String> helpTypeNames = new ArrayList<>();
            if (!StringUtils.isEmpty(helpTypeName)) {
                helpTypeNames = Arrays.asList(helpTypeName.split(","));
            }
            List<Map<String, Object>> teamList = teamInfoDao.queryRescueTeamInfo(pointName, helpType,
                    helpTypeNames, industry, area, teamName, teamType, apType, storageLevel,
                    specialities, userId, planId, tempEventId, start, pageLen);
            //计算距离
            if (!StringUtils.isEmpty(eventId) && !CollectionUtils.isEmpty(teamList)) {
                Map<String, String> map = eventDao.queryGps(eventId);
                if (map != null) {
                    teamList.forEach(temp -> temp.put("distance", CommonUtils.calculateDistance(map.get("lat"), map.get("lon"), (String) temp.get("LATITUDE"), (String) temp.get("LONGITUDE"))));
                } else {
                    teamList.forEach(temp -> temp.put("distance", null));
                }
            }
            int total = teamInfoDao.queryBasicInfoCount(area, teamName, teamType, storageLevel, apType, helpTypeNames, industry, specialities);
            //int personCount = teamList.stream().mapToInt(t -> Integer.parseInt((String) t.get("member_number"))).sum();
            Map map = new HashMap();
            map.put("total", total);
            //map.put("personCount", personCount);
            map.put("teamList", teamList);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> queryTeamInfoList(String pointName, String helpType, String helpTypeName, String industry, String gpsx, String gpsy, String area,
                                                       String teamName, String teamType, String apType, String storageLevel, String eventId, String specialities) {
//                                                       String teamName, String teamType, String apType, String storageLevel, String specialities,Integer pageStart,Integer pageLen) {
//        Integer start = 0;
//        if (pageStart != null && pageLen != null) {
//            start = pageLen * (pageStart - 1);
//        }
        List<Map<String, Object>> list = teamInfoDao.queryTeamInfoList(pointName, helpType, helpTypeName, industry, area, teamName, teamType, apType, storageLevel, specialities, eventId);
//        List<Map<String, Object>> list = teamInfoDao.queryTeamInfoList(pointName, helpType, helpTypeName, industry, area, teamName, teamType, apType, storageLevel, specialities,pageStart,pageLen);
       /* if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
            for (Map map : list) {
                List<ResourceTeamMember> resourceTeamMembers = resourceTeamMemberService.queryMember(Integer.parseInt(map.get("ID").toString()));
                map.put("resourceTeamMembers", resourceTeamMembers);
            }
        }*/
        if (!StringUtils.isEmpty(gpsx) && !StringUtils.isEmpty(gpsy)) {
            if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                for (Map map : list) {
                    if (!StringUtils.isEmpty(map.get("LONGITUDE"))
                            && !StringUtils.isEmpty(map.get("LATITUDE"))) {
                        double distance = CommonUtils.getDistance(gpsx, gpsy, map.get("LONGITUDE").toString(), map.get("LATITUDE").toString());
                        BigDecimal b = new BigDecimal(distance);
                        map.put("distance", b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    } else {
                        map.put("distance", "--");
                    }
                }
                list.sort((o1, o2) -> {
                    if (("--").equals(o1.get("distance"))) {
                        return 1;
                    }
                    if (("--").equals(o2.get("distance"))) {
                        return -1;
                    }
                    Double distance1 = (Double) o1.get("distance");
                    Double distance2 = (Double) o2.get("distance");
                    return distance1.compareTo(distance2);
                });

            }
        } else {
            if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                for (Map map : list) {
                    map.put("distance", "--");
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> queryTeamInfoStuff(String id, String companyId, String model, String brand, String stuffName, String stuffType, String helpType) {
        List<Map<String, Object>> list = teamInfoDao.queryTeamInfoStuff(id, companyId, model, brand, stuffName, stuffType, helpType);
        return list;
    }

    @Override
    public Result queryTeamInfoCount() {
        Result result = new Result();
        try {
            List<Map<String, Object>> queryTeamInfoCountXQ = teamInfoDao.queryTeamInfoCountXQ();
            List<Map<String, Object>> queryTeamInfoCountGLLX = teamInfoDao.queryTeamInfoCountGLLX();
            List<Map<String, Object>> queryTeamInfoCountSSHY = teamInfoDao.queryTeamInfoCountSSHY();
            List<Map<String, Object>> queryTeamInfoCountJYLX = teamInfoDao.queryTeamInfoCountJYLX();
            Map<String, Object> map = new HashMap<>();
            map.put("qx", queryTeamInfoCountXQ);
            map.put("gllx", queryTeamInfoCountGLLX);
            map.put("sshy", queryTeamInfoCountSSHY);
            map.put("jylx", queryTeamInfoCountJYLX);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result teamInfoMajorsDict() {
        Result result = new Result();
        try {
            List<Map<String, Object>> teamInfoMajorsDict = teamInfoDao.teamInfoMajorsDict();
            result.setData(teamInfoMajorsDict);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result teamInfoFullPartTimeDict() {
        Result result = new Result();
        try {
            List<Map<String, Object>> teamInfoFullPartTimeDict = teamInfoDao.teamInfoFullPartTimeDict();
            result.setData(teamInfoFullPartTimeDict);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> shelterInfo(String gpsx, String gpsy, String area, String name, String shelterCategory) {
        List<Map<String, Object>> list = teamInfoDao.shelterInfo(area, name, shelterCategory);
        if (!StringUtils.isEmpty(gpsx) && !StringUtils.isEmpty(gpsy)) {
            if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                for (Map map : list) {
                    if (!StringUtils.isEmpty(map.get("SHELTER_LONGITUDE").toString())
                            && !StringUtils.isEmpty(map.get("SHELTER_LATITUDE").toString())) {
                        double distance = CommonUtils.getDistance(gpsx, gpsy, map.get("SHELTER_LONGITUDE").toString(), map.get("SHELTER_LATITUDE").toString());
                        BigDecimal b = new BigDecimal(distance);
                        map.put("distance", b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    } else {
                        map.put("distance", "--");
                    }
                }
                list.sort((o1, o2) -> {
                    if (("--").equals(o1.get("distance"))) {
                        return 1;
                    }
                    if (("--").equals(o2.get("distance"))) {
                        return -1;
                    }
                    Double distance1 = (Double) o1.get("distance");
                    Double distance2 = (Double) o2.get("distance");
                    return distance1.compareTo(distance2);
                });

            }
        } else {
            if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                for (Map map : list) {
                    map.put("distance", "--");
                }
            }
        }
        return list;
    }

    @Override
    public Result shelterInfoCount() {
        Result result = new Result();
        try {
            List<Map<String, Object>> shelterInfoCountXQ = teamInfoDao.shelterInfoCountXQ();
            Map<String, Object> map = new HashMap<>();
            map.put("qx", shelterInfoCountXQ);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result carTeamCount() {
        Result result = new Result();
        try {
            List<Map<String, Object>> carTeamCountXQ = teamInfoDao.carTeamCountXQ();
            List<Map<String, Object>> carTeamCountLX = teamInfoDao.carTeamCountLX();
            Map<String, Object> map = new HashMap<>();
            map.put("qx", carTeamCountXQ);
            map.put("lx", carTeamCountLX);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result emergencyFloodStuffCount() {
        Result result = new Result();
        try {
            List<Map<String, Object>> carTeamCountXQ = teamInfoDao.carTeamCountXQ();
            List<Map<String, Object>> carTeamCountLX = teamInfoDao.carTeamCountLX();
            Map<String, Object> map = new HashMap<>();
            map.put("qx", carTeamCountXQ);
            map.put("lx", carTeamCountLX);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }


    @Override
    public Map<String, Object> emergencyPower(String type) {
        List<Map<String, Object>> rescueTeams = teamInfoDao.emergencyPower(type);
        Map<String, Object> result = new HashMap<>();
        List<Object> rescueTeam = new ArrayList<>();
        List<Object> rescueStock = new ArrayList<>();
        List<Object> rescueSociety = new ArrayList<>();
        for (Map<String, Object> map : rescueTeams) {
            String categories = map.get("categories").toString();
            if ("救援队伍".equals(categories)) {
                rescueTeam.add(map);
            } else if ("物资储备".equals(categories)) {
                rescueStock.add(map);
            } else if ("社会力量".equals(categories)) {
                rescueSociety.add(map);
            }
        }
        result.put("rescueTeam", rescueTeam);
        result.put("rescueStock", rescueStock);
        result.put("rescueSociety", rescueSociety);
        return result;
    }

    @Override
    public Map<String, Map<String, List<Map<String, Object>>>> company() {
        List<Map<String, Object>> maps = teamInfoDao.companyInfo();
        Map<String, Map<String, List<Map<String, Object>>>> result = new HashMap<>();
        for (Map<String, Object> map : maps) {
            String categories = map.get("categories").toString();
            String type = map.get("type").toString();
            if (result.containsKey(categories)) {
                Map<String, List<Map<String, Object>>> categoriesMap = result.get(categories);
                if (categoriesMap.containsKey(type)) {
                    List<Map<String, Object>> types = categoriesMap.get(type);
                    types.add(map);
                } else {
                    List<Map<String, Object>> types = new ArrayList<>();
                    types.add(map);
                    categoriesMap.put(type, types);
                }
            } else {
                Map<String, List<Map<String, Object>>> categoriesMap = new HashMap<>();
                List<Map<String, Object>> types = new ArrayList<>();
                types.add(map);
                categoriesMap.put(type, types);
                result.put(categories, categoriesMap);
            }
        }
        return result;
    }

    @Override
    public List<Map> emergencyPowerDictList() {
        return teamInfoDao.queryEmergencyPowerDictList();
    }

    @Override
    public List<Map> carTeam(String areaName, String carType) {
        List<Map> result = new ArrayList<>();
        List<Map<String, String>> maps = teamInfoDao.queryCarTeamInfo(areaName, carType);
        List<Map<String, String>> maps1 = teamInfoDao.queryCarTeamManager("1");
        Map<String, List<Map>> area = CommonUtils.changeMap(maps, "area");
        Map<String, List<Map>> areaManager = CommonUtils.changeMap(maps1, "area");
        for (String s : area.keySet()) {
            List<Map> carDatas = area.get(s);
            Map map = carDatas.get(0);
            List<Map> carManagerDatas = areaManager.get(s);
            Map carManager = carManagerDatas.get(0);
            Map<String, Object> carInfos = new HashMap<>();
            Integer truck = 0;
            Integer passenger = 0;
            Double truckLoad = 0.0;
            Integer passengerLoad = 0;
            for (Map carData : carDatas) {
                String type = carData.get("type").toString();
                switch (type) {
                    case "客":
                        passenger++;
                        int load = Integer.parseInt(carData.get("load").toString());
                        passengerLoad = passengerLoad + load;
                        break;
                    case "货":
                        truck++;
                        Double load2 = Double.parseDouble(carData.get("load").toString());
                        truckLoad = truckLoad + load2;
                        break;
                }
            }
            carInfos.put("areaCode", map.get("area_code"));
            carInfos.put("area", s);
            carInfos.put("managerInfo", carManager);
            carInfos.put("carInfo", carDatas);
            carInfos.put("truck", truck);
            carInfos.put("passenger", passenger);
            carInfos.put("truckLoad", truckLoad);
            carInfos.put("passengerLoad", passengerLoad);
            carInfos.put("teamName", carManager.get("unit"));
            carInfos.put("gpsx", carManager.get("gpsx"));
            carInfos.put("gpsy", carManager.get("gpsy"));
            result.add(carInfos);
        }
        return result;
    }

    @Override
    public List<Map> emergencyFloodStuff(String areaName, String stuffName) {
        List<Map> result = new ArrayList<>();
        List<Map<String, String>> maps = teamInfoDao.queryFloodStuffInfo(areaName, stuffName);
        List<Map<String, String>> maps1 = teamInfoDao.queryCarTeamManager("2");
        Map<String, List<Map>> area = CommonUtils.changeMap(maps, "area");
        Map<String, List<Map>> areaManager = CommonUtils.changeMap(maps1, "area");
        for (String s : area.keySet()) {
            List<Map> carDatas = area.get(s);
            Map map = carDatas.get(0);
            List<Map> maps2 = areaManager.get(s);
            Map manager = maps2.get(0);
            List<Map> carManagerDatas = areaManager.get(s);
            Map<String, Object> carInfos = new HashMap<>();
            carInfos.put("areaCode", map.get("area_code"));
            carInfos.put("area", s);
            carInfos.put("managerInfo", carManagerDatas.get(0));
            carInfos.put("stuffInfo", carDatas);
            carInfos.put("teamName", carManagerDatas.get(0).get("unit"));
            carInfos.put("gpsx", manager.get("gpsx"));
            carInfos.put("gpsy", manager.get("gpsy"));
            result.add(carInfos);
        }
        return result;
    }

    @Override
    public Map floodPerpare() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> list = teamInfoDao.queryFloodPerpareStuff();
        Map<String, Object> stuff = new HashMap<>();
        Set<String> stuffTypeNum = new HashSet<>();
        for (Map<String, String> map : list) {
            String stuffName = map.get("stuffName");
            String buyDate = map.get("buyDate");
            String brand = map.get("brand");
            String model = map.get("model");
            stuffTypeNum.add(stuffName + "-" + buyDate + "-" + brand + "-" + model);
        }
        stuff.put("num", stuffTypeNum.size());
        stuff.put("data", list);
        List<Map<String, Object>> maps = teamInfoDao.queryTeamInfoList(null, "19", null, null, null, null, null, null, null, null, null);
        Map<String, Object> team = new HashMap<>();
        team.put("num", maps.size());
        team.put("data", maps);
        result.put("stuff", stuff);
        List<Map<String, Object>> list1 = teamInfoDao.shelterInfo(null, null, null);
        Map<String, Object> shelter = new HashMap<>();
        shelter.put("num", list1.size());
        shelter.put("data", list1);
        result.put("shelter", shelter);
        result.put("team", team);
        Map<String, Object> disasters = new HashMap<>();
        disasters.put("num", 0);
        disasters.put("data", null);
        Map<String, Object> dam = new HashMap<>();
        dam.put("num", 0);
        dam.put("data", null);
        Map<String, Object> drainage = new HashMap<>();
        drainage.put("num", 0);
        drainage.put("data", null);
        result.put("disasters", disasters);
        result.put("dam", dam);
        result.put("drainage", drainage);
        return result;
    }

    @Override
    public Result commandSelectTeam(String commandId, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(teamInfoDao.getTeamInfoByCommandId(commandId)));
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "baseTransactionManager")
    public Result teamExcel(MultipartFile file) {
        Result result = new Result();
        //入参校验
        String name = file.getOriginalFilename();
        if (name == null) {
            result.setMessage("文件名为空");
            result.setCode(500);
            return result;
        }
        String ext = name.substring(name.lastIndexOf(".") + 1);
        if (!("xlsx".equals(ext) || "xls".equals(ext))) {
            result.setMessage("不正确的格式");
            result.setCode(500);
            return result;
        }
        Map<String, List<Map<String, String>>> map = PoiUtils.getResult(file, 1);
        List<Map<String, String>> list1 = map.get("Sheet1");
        List<RescueTeamInfoZn> rescueTeamInfoZns = JSONObject.parseArray(JSON.toJSONString(list1), RescueTeamInfoZn.class);
        buildTagId(rescueTeamInfoZns);
        return result;
    }

    @Override
    public Result exportTeamExcel(String ids, String pointName, String helpType, String helpTypeName,
                                  String industry, String area, String teamName, String teamType, String apType,
                                  String storageLevel, String specialities, String userId, String userLevel, Integer pageStart,
                                  Integer pageLen) {
        List<Map<String, Object>> teamList = teamInfoDao.queryRescueTeamInfoExport(ids, pointName, helpType,
                helpTypeName, industry, area, teamName, teamType, apType, storageLevel,
                specialities, userId, pageStart, pageLen);
        int i = 1;
        for (Map<String, Object> team : teamList) {
            team.put("rowId", i);
            i++;
        }
        StringBuilder keysStr = new StringBuilder(200);
        StringBuilder columnNamesStr = new StringBuilder(200);
        String sheetName = "救援队伍";
        keysStr.append("rowId,TEAM_NAME,BELONG_UNIT,REGION,BE_MANAGED_TYPE,A_P_TYPE," +
                "STORAGE_LEVEL,MANAGER,MANAGER_PHONE,MAIN_PEOPLE,DUTY_PHONE,member_number," +
                "HELP_TYPE,LONGITUDE,LATITUDE,ADDRESS");
        columnNamesStr.append("序号,队伍名称,管理单位,所在辖区,管理类型,专兼类型,管理级别,联系人,联系电话,主要责任人,值班电话,队员人数," +
                "救援类别,经度,纬度,详细地址");
        String title = ""; // 不要第一行的title
        String[] keys = keysStr.toString().split(",");
        String[] columnNames = columnNamesStr.toString().split(",");
        String[] mergeColumnNames = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String refileName = sheetName + simpleDateFormat.format(new Date()) + ".xlsx";
        PoiUtils.createWorkBook(teamList, keys, columnNames, sheetName, mergeColumnNames, title, filePath + refileName);
        return new Result(fileUrl + refileName);
    }

    private void buildTagId(List<RescueTeamInfoZn> rescueTeamInfoZns) {
        int i = 1;
        for (RescueTeamInfoZn rescueTeamInfoZn : rescueTeamInfoZns) {
            // 管理单位
            String belongUnitName = rescueTeamInfoZn.getBelongUnitName();
            if (!StringUtils.isEmpty(belongUnitName)) {
                Integer belongTag = teamInfoDao.getBelongTag(belongUnitName);
                if (belongTag != null) {
                    rescueTeamInfoZn.setBelongUnit(belongTag);
                }
            }
            // 所在辖区
            String regionName = rescueTeamInfoZn.getRegionName();
            if (!StringUtils.isEmpty(regionName)) {
                String region = teamInfoDao.getPrecinct(regionName);
                if (region != null) {
                    rescueTeamInfoZn.setRegion(region);
                }
            }

            // 管理类型
            String beManagedTypeName = rescueTeamInfoZn.getBeManagedTypeName();
            if (!StringUtils.isEmpty(beManagedTypeName)) {
                Integer beManagedType = teamInfoDao.getTag(beManagedTypeName, "13");
                if (beManagedType != null) {
                    rescueTeamInfoZn.setBeManagedType(beManagedType);
                }
            }
            // 专兼职类型
            String apTypeName = rescueTeamInfoZn.getApTypeName();
            if (!StringUtils.isEmpty(apTypeName)) {
                String apType = String.valueOf(teamInfoDao.getTag(apTypeName, "10"));
                if (!StringUtils.isEmpty(apType)) {
                    rescueTeamInfoZn.setApType(apType);
                }
            }
            // 管理级别
            String storageLevelName = rescueTeamInfoZn.getStorageLevelName();
            if (!StringUtils.isEmpty(storageLevelName)) {
                Integer storageLevel = teamInfoDao.getTag(storageLevelName, "39");
                if (storageLevel != null) {
                    rescueTeamInfoZn.setStorageLevel(storageLevel);
                }
            }
            // 擅长行业
            String specialities = rescueTeamInfoZn.getSpecialities();
            if (!StringUtils.isEmpty(specialities)) {
                Integer sp = teamInfoDao.getTag(specialities, "26");
                if (sp != null) {
                    rescueTeamInfoZn.setSpecialities(sp.toString());
                }
            }
            // 救援类别
            String helpTypeName1 = rescueTeamInfoZn.getHelpTypeName();
            if (!StringUtils.isEmpty(helpTypeName1)) {
                String[] helpTypeName = rescueTeamInfoZn.getHelpTypeName().split(",");
                boolean flag = false;
                StringBuilder sb = new StringBuilder();
                for (String s : helpTypeName) {
                    if (flag) {
                        sb.append(",").append(teamInfoDao.getTag(s, "17"));
                    } else {
                        sb.append(teamInfoDao.getTag(s, "17"));
                        flag = true;
                    }
                }
                rescueTeamInfoZn.setHelpType(sb.toString());
            }

            String name = rescueTeamInfoZn.getTeamName();
            String row = rescueTeamInfoZn.getRow();
            if (!StringUtils.isEmpty(row) && StringUtils.isEmpty(name)) {
                logger.error("第 :{} 存在队伍名称为空，请添加队伍名称", i);
                throw new BusinessException("第" + i + "行存在队伍名称为空，请添加队伍名称");
            }
            if (StringUtils.isEmpty(name)) {
                logger.error("第 :{} 存在队伍名称为空，请添加队伍名称", i);
                i++;
                continue;
            }
            String id = teamInfoDao.getTeamIdByName(name);
            if (StringUtils.isEmpty(id)) {

                // 队伍基本信息添加
                teamBasicInfoService.insertTeamInfo(rescueTeamInfoZn);
            } else {
                rescueTeamInfoZn.setId(Integer.valueOf(id));

                // 队伍基本信息修改
                teamBasicInfoService.updateTeamInfo(rescueTeamInfoZn);
            }
            i++;
        }
    }


}

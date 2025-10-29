package com.znv.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.RegionalManagementService;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: dailongli
 * @Date: 2018/8/21 13:47
 * operlog added by lilingling 2018/10/23
 */
@Slf4j
@Data
@RestController
@RequestMapping("/regional")
@Api(tags = "组织管理")
public class RegionalManagementController {

    private String centerCode = "";

    @Autowired
    RegionalManagementService regionalManagementService;

    @Value("${filter.precinctids:3101150000000087,3101150000000088,3101150000000100,3101150000000101,3101150000000124,3101150000000166,3101150000000340,3101150000000341,3101150000000356}")
    private String filterPrecinctIds;

    /**
     * 功能描述：根据活动查询区域
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     *
     * @param eventId
     * @return
     */
    @RequestMapping(value = "query-relation-precinctIds", method = RequestMethod.GET)
    List<Map<String, Object>> queryRelationPrecinctIds(
            @RequestParam(required = true, name = "eventId") String eventId) {
        return regionalManagementService.queryRelationPrecinctIds(eventId);
    }

    /**
     * 功能描述：查询活动区域
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     *
     * @param precinctId
     * @return
     */
    @RequestMapping(value = "query-eventIds", method = RequestMethod.GET)
    public Object queryEventIds(
            @RequestParam(required = true, name = "precinctId") String precinctId) {
        return regionalManagementService.queryEventIds(precinctId);
    }

    /**
     * 过滤掉部分区域的组织树，配置文件可配需要过滤的区域
     *
     * @param precinctId
     * @param address
     * @param precinctName
     * @param upPrecinctId
     * @param precinctKind
     * @param precinctKindTree
     * @param regionXY
     * @param areaCode
     * @param gpsX
     * @param gpsY
     * @param gpsZ
     * @param gpsType
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping(value = "filter-precinctids")
    public Object filterPrecinctidsSelect(

            @RequestParam(required = false, name = "precinctId") String precinctId,
            @RequestParam(required = false, name = "precinctAddr") String address,
            @RequestParam(required = false, name = "precinctName") String precinctName,
            @RequestParam(required = false, name = "upPrecinctId") String upPrecinctId,
            @RequestParam(required = false, name = "precinctKind") String precinctKind,
            @RequestParam(required = false, name = "precinctKindTree") String precinctKindTree,
            @RequestParam(required = false, name = "regionXY") String regionXY,
            @RequestParam(required = false, name = "areaCode") String areaCode,
            @RequestParam(required = false, name = "gpsX") String gpsX,
            @RequestParam(required = false, name = "gpsY") String gpsY,
            @RequestParam(required = false, name = "gpsZ") String gpsZ,
            @RequestParam(required = false, name = "gpsType") String gpsType,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletRequest request) {
        Object select = this.select(precinctId, address, precinctName, upPrecinctId, precinctKind, precinctKindTree,
                regionXY, areaCode, gpsX, gpsY,
                gpsZ, gpsType, pageNum, pageSize, request);

        if (select instanceof Result && !StringUtils.isEmpty(filterPrecinctIds)) {
            Result select1 = (Result) select;
            Object data = select1.getData();
            List<Map> precinctList = new ArrayList<>();
            List<Map> extendList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(data)) {
                JSONObject jsonObject = JSONObject.parseObject(String.valueOf(data));
                Object precinct = jsonObject.get("precinct");
                Object extend = jsonObject.get("extend");
                if (!ObjectUtils.isEmpty(precinct)) {
                    List<Map> maps = JSONObject.parseArray(String.valueOf(precinct), Map.class);
                    maps.forEach(map -> {
                        Object precinctId1 = map.get("precinctId");
                        if (!filterPrecinctIds.contains(String.valueOf(precinctId1))) {
                            precinctList.add(map);
                        }
                    });
                }
                if (!ObjectUtils.isEmpty(extend)) {
                    List<Map> maps = JSONObject.parseArray(String.valueOf(extend), Map.class);
                    maps.forEach(map -> {
                        Object precinctId1 = map.get("precinctId");
                        if (!filterPrecinctIds.contains(String.valueOf(precinctId1))) {
                            extendList.add(map);
                        }
                    });
                }
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("precinct", JSON.toJSON(precinctList));
                jsonObject2.put("extend", JSON.toJSON(extendList));
                return new Result(jsonObject2);

            } else {
                return select;
            }
        } else {
            return select;
        }
    }

    /**
     * 查询区域管理信息,另外扩展表信息也一并返回
     *
     * @param precinctId
     * @param address
     * @param precinctName
     * @param upPrecinctId
     * @param precinctKind
     * @param regionXY
     * @param areaCode
     * @param gpsX
     * @param gpsY
     * @param gpsZ
     * @param gpsType
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object select(@RequestParam(required = false, name = "precinctId") String precinctId, @RequestParam(required = false, name = "precinctAddr") String address,
                         @RequestParam(required = false, name = "precinctName") String precinctName, @RequestParam(required = false, name = "upPrecinctId") String upPrecinctId,
                         @RequestParam(required = false, name = "precinctKind") String precinctKind,
                         @RequestParam(required = false, name = "precinctKindTree") String precinctKindTree,
                         @RequestParam(required = false, name = "regionXY") String regionXY,
                         @RequestParam(required = false, name = "areaCode") String areaCode, @RequestParam(required = false, name = "gpsX") String gpsX,
                         @RequestParam(required = false, name = "gpsY") String gpsY, @RequestParam(required = false, name = "gpsZ") String gpsZ,
                         @RequestParam(required = false, name = "gpsType") String gpsType, @RequestParam(value = "pageNum", required = false) Integer pageNum,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                         HttpServletRequest request) {
        //过滤掉区域id不在此set集合里的
//        Boolean bNeed = bNotAdmin(request);
//        Set<String> set = (Set<String>) request.getSession().getAttribute("precinctIds");
//        if (bNeed) {
//            if (null == set || set.isEmpty()) {
//                log.info("$$$ set is null;precinctId=" + precinctId + ";");
//                Result ret = new Result();
//                ret.setMessage("set is null");
//                ret.setCode(ResultCodeEnum.NORMAL.getCode());
//                return ret;
//            }
//        }

        if (pageNum != null && pageSize != null) {
            String strPre = "";
            List<String> listPrecinct = regionalManagementService.querySubRegionalId(precinctId);
            if (!StringUtils.isEmpty(precinctId)) {
                strPre = precinctId + "," + listPrecinct.toString().substring(1, listPrecinct.toString().length() - 1).replace(" ", "");
            }
            PageHelper.startPage(pageNum, pageSize);

//            strPre = (bNeed ? getAuthPricntIds(set, strPre) : strPre);
            List<Map<String, String>> listRegional = regionalManagementService.select(strPre, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, precinctKindTree);
            return new PageInfo<>(listRegional);
        }
//        precinctId = (bNeed ? getAuthPricntIds(set, precinctId) : precinctId);
        List<Map<String, String>> list = regionalManagementService.select(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, precinctKindTree);
        List<Map<String, String>> listExpansion = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Map m : list) {
//            if(bNeed&&String.valueOf(m.get("precinctKind")).equals("202")){
//               m.put("upPrecinctId","0");
//            }
            sb.append(String.valueOf(m.get("precinctId")));
            sb.append(",");
        }
        String p = "";
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            p = sb.toString();
        }

        listExpansion.addAll(regionalManagementService.queryExpansion(p));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("precinct", JSON.toJSON(list));
        jsonObject.put("extend", JSON.toJSON(listExpansion));
        return new Result(jsonObject);
    }

    /**
     * 根据权限查询区域管理信息,另外扩展表信息也一并返回
     *
     * @param precinctId
     * @param address
     * @param precinctName
     * @param upPrecinctId
     * @param precinctKind
     * @param regionXY
     * @param areaCode
     * @param gpsX
     * @param gpsY
     * @param gpsZ
     * @param gpsType
     * @return
     */
    @RequestMapping(value = "query-by-privilege", method = RequestMethod.GET)
    public Object select(@RequestParam(required = false, name = "precinctId") String precinctId, @RequestParam(required = false, name = "precinctAddr") String address,
                         @RequestParam(required = false, name = "precinctName") String precinctName, @RequestParam(required = false, name = "upPrecinctId") String upPrecinctId,
                         @RequestParam(required = false, name = "precinctKind") String precinctKind,
                         @RequestParam(required = false, name = "precinctKindTree") String precinctKindTree,
                         @RequestParam(required = false, name = "authType", defaultValue = "6") int authType,
                         @RequestParam(required = false, name = "regionXY") String regionXY,
                         @RequestParam(required = false, name = "areaCode") String areaCode, @RequestParam(required = false, name = "gpsX") String gpsX,
                         @RequestParam(required = false, name = "gpsY") String gpsY, @RequestParam(required = false, name = "gpsZ") String gpsZ,
                         @RequestParam(required = false, name = "gpsType") String gpsType, @RequestParam(value = "pageNum", required = false) Integer pageNum,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                         HttpServletRequest request) {
        log.info("begin to query-by-privilege.");

        //过滤掉区域id不在此set集合里的
//        Boolean bNeed = bNotAdmin(request);

        Set<String> set = null;
        String cardId = (String) request.getSession().getAttribute("cardId");
        if (StringUtils.isEmpty(cardId)) {
            cardId = (String) request.getSession().getAttribute("userId");
        }

        log.info(">>cardId or userId=" + cardId);
        if (!StringUtils.isEmpty(cardId)) {
            set = regionalManagementService.addPrivilegePrecinctIds(cardId, authType);

            if (null != set && set.size() > 0) {
                set = regionalManagementService.queryPrecinctIdsDown(set);
            }

            // 根据cardId查询对应的组织机构权限
            if (null == set || set.isEmpty()) {
                log.info("privilege precinctIds is empty.");
                set = (Set<String>) request.getSession().getAttribute("precinctIds");
                log.info("set=" + set);
            }
        }

//        if (bNeed) {
//            if (null == set || set.isEmpty()) {
//                log.info("$$$ set is null;precinctId=" + precinctId + ";");
//                Result ret = new Result();
//                ret.setMessage("set is null");
//                ret.setCode(ResultCodeEnum.NORMAL.getCode());
//                return ret;
//            }
//        }

        if (pageNum != null && pageSize != null) {
            String strPre = "";
            List<String> listPrecinct = regionalManagementService.querySubRegionalId(precinctId);
            if (!StringUtils.isEmpty(precinctId)) {
                strPre = precinctId + "," + listPrecinct.toString().substring(1, listPrecinct.toString().length() - 1).replace(" ", "");
            }
            PageHelper.startPage(pageNum, pageSize);

//            strPre = (bNeed ? getAuthPricntIds(set, strPre) : strPre);
            List<Map<String, String>> listRegional = regionalManagementService.select(strPre, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, precinctKindTree);
            return new PageInfo<>(listRegional);
        }

//        precinctId = (bNeed ? getAuthPricntIds(set, precinctId) : precinctId);
        List<Map<String, String>> list = regionalManagementService.select(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, precinctKindTree);
        List<Map<String, String>> listExpansion = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Map m : list) {
//            if(bNeed&&String.valueOf(m.get("precinctKind")).equals("202")){
//               m.put("upPrecinctId","0");
//            }
            sb.append(String.valueOf(m.get("precinctId")));
            sb.append(",");
        }
        String p = "";
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            p = sb.toString();
        }

        listExpansion.addAll(regionalManagementService.queryExpansion(p));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("precinct", JSON.toJSON(list));
        jsonObject.put("extend", JSON.toJSON(listExpansion));
        return new Result(jsonObject);
    }

    /**
     * 查询组织树
     *
     * @param upPrecinctId
     * @param precinctKind
     * @return
     */
    @RequestMapping(value = "/precinct", method = RequestMethod.GET)
    public Object queryPrecinctById(@RequestParam(required = false, name = "upPrecinctId") String upPrecinctId,
                                    @RequestParam(required = false, name = "precinctKind") String precinctKind,
                                    @RequestParam(required = false, name = "forStreet") String forStreet) {
        return regionalManagementService.queryPrecinctById(upPrecinctId, precinctKind, forStreet);
    }

    /**
     * 更新区域信息
     *
     * @param jsonObj
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody JSONObject jsonObj, HttpServletRequest request) {
        String precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, orderNum;
        precinctId = jsonObj.getString("precinctId");
        address = jsonObj.getString("precinctAddr");
        precinctName = jsonObj.getString("precinctName");
        upPrecinctId = jsonObj.getString("upPrecinctId");
        precinctKind = jsonObj.getString("precinctKind");
        regionXY = jsonObj.getString("regionXY");
        areaCode = jsonObj.getString("areaCode");
        gpsX = "".equals(jsonObj.getString("gpsX")) ? null : jsonObj.getString("gpsX");
        gpsY = "".equals(jsonObj.getString("gpsY")) ? null : jsonObj.getString("gpsY");
        gpsZ = "".equals(jsonObj.getString("gpsZ")) ? null : jsonObj.getString("gpsZ");
        gpsType = "".equals(jsonObj.getString("gpsType")) ? null : jsonObj.getString("gpsType");
        orderNum = "".equals(jsonObj.getString("orderNum")) ? null : jsonObj.getString("orderNum");
        regionalManagementService.update(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, orderNum);

        return 0;
    }

    /**
     * 插入新增区域信息
     *
     * @param jsonObj
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object insert(@RequestBody JSONObject jsonObj, HttpServletRequest request) {
        String precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, orderNum;
        areaCode = jsonObj.getString("areaCode");
        address = jsonObj.getString("precinctAddr");
        precinctName = jsonObj.getString("precinctName");
        upPrecinctId = jsonObj.getString("upPrecinctId");
        precinctKind = jsonObj.getString("precinctKind");
        regionXY = jsonObj.getString("regionXY");
        gpsX = "".equals(jsonObj.getString("gpsX")) ? null : jsonObj.getString("gpsX");
        gpsY = "".equals(jsonObj.getString("gpsY")) ? null : jsonObj.getString("gpsY");
        gpsZ = "".equals(jsonObj.getString("gpsZ")) ? null : jsonObj.getString("gpsZ");
        gpsType = "".equals(jsonObj.getString("gpsType")) ? null : jsonObj.getString("gpsType");
        orderNum = "".equals(jsonObj.getString("orderNum")) ? null : jsonObj.getString("orderNum");

        if (areaCode.length() != 6) {
            return new Result(400, "区域ID位数不为6！");
        }
        //获取中心id的后四位
        if (StringUtils.isEmpty(centerCode)) {
            centerCode = regionalManagementService.queryCenterCode();
            if (centerCode == null) {
                centerCode = "0000";
            } else {
                centerCode = centerCode.substring(centerCode.length() - 4, centerCode.length());
            }
        }
        precinctId = areaCode + centerCode;
        //获取标准id
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("i_object_item", precinctId);
        paramMap.put("i_object_maxid", 999999);
        paramMap.put("i_object_flag", 2);//1设备2区域
        precinctId = regionalManagementService.getStdId(paramMap);
        //插入数据
        try {
            regionalManagementService.insert(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, "", orderNum);
//            regionalManagementService.insertRelPrecinct(precinctId, precinctName, upPrecinctId, precinctKind);
            //向缓存中插入这条数据
//            Set<String> precinctIdSet = (Set<String>) request.getSession().getAttribute("precinctIds");
//            if (precinctIdSet != null) {
//                precinctIdSet.add(precinctId);
//                request.getSession().setAttribute("precinctIds", precinctIdSet);
//            }
            Map<String, String> m = new HashMap<>();
            m.put("precinctId", precinctId);

            return new Result(m);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
            } catch (Exception e) {
                log.error("insert log failed", e);
            }
        }
    }

    /**
     * 删除区域信息以及所有子区域
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(@RequestParam("id") String id, HttpServletRequest request) {
        /* 添加操作日志 */
        Result result = null;
        try {
            result = regionalManagementService.delete(id);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
            } catch (Exception e) {
                log.error("insert log failed", e);
            }
        }
        return result;
    }

    /**
     * 查询所有子区域信息
     *
     * @param precinctId
     * @return
     */
    @RequestMapping(value = "/querySubRegional", method = RequestMethod.GET)
    public Object querySubRegional(@RequestParam(required = false, name = "precinctId") String precinctId,
                                   @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                   HttpServletRequest request
    ) {
        //过滤掉区域id不在此set集合里的
//        Boolean bNeed = bNotAdmin(request);
        Set<String> set = (Set<String>) request.getSession().getAttribute("precinctIds");
//        if (bNeed) {
//            if (null == set || set.isEmpty()) {
//                log.info("$$$ set is null;precinctId=" + precinctId + ";");
//                Result ret = new Result();
//                ret.setMessage("set is null");
//                ret.setCode(ResultCodeEnum.NORMAL.getCode());
//                return ret;
//            }
//        }

//        precinctId = (bNeed ? getAuthPricntIds(set, precinctId) : precinctId);

        if (StringUtils.isEmpty(precinctId)) {
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
                List<Map<String, String>> listRegional = regionalManagementService.select("", "", "", "", "", "", "", "", "", "", "", "");
                return new PageInfo<>(listRegional);
            }
            List<Map<String, String>> listRegional = regionalManagementService.select("", "", "", "", "", "", "", "", "", "", "", "");
            List<Map<String, String>> listExpansion = new ArrayList<>();

            StringBuilder sb = new StringBuilder();
            for (Map m : listRegional) {
                sb.append(String.valueOf(m.get("precinctId")));
                sb.append(",");
            }
            String p = "";
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                p = sb.toString();
            }

            listExpansion.addAll(regionalManagementService.queryExpansion(p));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("precinct", JSON.toJSON(listRegional));
            jsonObject.put("extend", JSON.toJSON(listExpansion));
            return new Result(jsonObject);
        } else {
            if (pageNum != null && pageSize != null) {
                List<String> listPrecinct = regionalManagementService.querySubRegional(precinctId);
                //precinctId +  "," + 此处包含自己
                String strPre = precinctId + "," + listPrecinct.toString().substring(1, listPrecinct.toString().length() - 1).replace(" ", "");
                PageHelper.startPage(pageNum, pageSize);

//                strPre = (bNeed ? getAuthPricntIds(set, strPre) : strPre);
                List<Map<String, String>> listRegional = regionalManagementService.select(strPre, "", "", "", "", "", "", "", "", "", "", "");
                return new PageInfo<>(listRegional);
            }
            List<String> listPrecinct = regionalManagementService.querySubRegional(precinctId);
            String strPre = precinctId + "," + listPrecinct.toString().substring(1, listPrecinct.toString().length() - 1).replace(" ", "");

//            strPre = (bNeed ? getAuthPricntIds(set, strPre) : strPre);
            List<Map<String, String>> listRegional = regionalManagementService.select(strPre, "", "", "", "", "", "", "", "", "", "", "");
            List<Map<String, String>> listExpansion = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (Map m : listRegional) {
                sb.append(String.valueOf(m.get("precinctId")));
                sb.append(",");
            }
            String p = "";
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                p = sb.toString();
            }

            listExpansion.addAll(regionalManagementService.queryExpansion(p));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("precinct", JSON.toJSON(listRegional));
            jsonObject.put("extend", JSON.toJSON(listExpansion));
            return new Result(jsonObject);
        }
    }

    /**
     * 查询所有子区域的区域ID信息
     *
     * @param precinctId
     * @return
     */
    @RequestMapping(value = "/querySubRegionalPrecinctId", method = RequestMethod.GET)
    public Object querySubRegionalPrecinctId(@RequestParam(required = false, name = "precinctId") String precinctId
    ) {
        List<String> listPrecinct = regionalManagementService.querySubRegional(precinctId);
        return new Result(listPrecinct);
    }

    /**
     * 查询gps类型
     *
     * @return
     */
    @RequestMapping(value = "/queryGpsType", method = RequestMethod.GET)
    public Object queryGpsType() {
        List<Map<String, String>> list = regionalManagementService.queryGpsType();
        return new Result(list);
    }

    /**
     * 查询区域类型
     *
     * @return
     */
    @RequestMapping(value = "/queryPrecinctKind", method = RequestMethod.GET)
    public Object queryPrecinctKind() {
        List<Map<String, String>> list = regionalManagementService.queryPrecinctKind();
        return new Result(list);
    }


    /**
     * 将iPrecintIds中不属于set集合的区域Id过滤掉
     *
     * @param set         该用户拥有权限的区域Id
     * @param iPrecintIds 需要查询的区域Id
     * @return 查询的该用户拥有权限的区域Id
     * @Author lilingling
     */
    public String getAuthPricntIds(Set<String> set, String iPrecintIds) {
        log.info("$$$ iPrecintIds=" + iPrecintIds + ";set=" + set);

        //如果区域id为空代表查询所有有权限的id，不适用上级区域id
        if (StringUtils.isEmpty(iPrecintIds)) {
            String[] strNeed = set.toArray(new String[set.size()]);
            String strLast = String.join(",", strNeed);
            log.info("$$$ strLast=" + strLast);
            return strLast;
        }

        //否则，过滤没有权限的区域Id
        String[] str = iPrecintIds.split(",");
        Set<String> setNeed = new HashSet<String>();
        for (String strTmp : str) {
            for (String setTmp : set) {
                if (setTmp.equals(strTmp)) {
                    setNeed.add(strTmp);
                    break;
                }
            }
        }

        String[] strNeed = setNeed.toArray(new String[setNeed.size()]);
        String strLast = String.join(",", strNeed);
        log.info("$$$ strLast=" + strLast);
        return strLast;
    }


    /**
     * 判断是否admin用户
     *
     * @param request 请求参数
     * @return true不是admin  false是admin
     * @author lilingling
     */
//    Boolean bNotAdmin(HttpServletRequest request) {
//
//        Object userIdObj = (request.getSession().getAttribute("userId"));
//        log.info("$$$ userIdObj=" + userIdObj);
//        if (null == userIdObj || userIdObj.toString().isEmpty()) {
//            return false;
//        } else {
//            String userId = userIdObj.toString();
//            String usrType = userService.queryUserByUsrId(userId);
//            if (EnumUserType.SUPER_ADMIN.equals(usrType)) {
//                return false;
//            }
//
//            return true;
//        }
//    }

}

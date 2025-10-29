package com.znv.manage.service.impl.resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.bean.user.PrecinctEnum;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.SiteManageBean;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.ExcelUtil;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.dao.resource.SiteManageDao;
import com.znv.manage.service.resource.SiteManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangbo
 * @email
 * @date 2022/11/9
 */
@Service
@Slf4j
public class SiteManageServiceImpl implements SiteManageService {

    @Autowired
    SiteManageDao siteManageDao;

    @Autowired
    EventDao eventDao;

    @Override
    public Result queryForList(String name, String category, String address, String acreage, String precinctTreeId, String precinctId, String MAXGALLERYFUL, String streetName, String community, String type, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            Map<String, Object> map = new HashMap<>();
            List<SiteManageBean> list = new LinkedList<>();
            if (!StringUtils.isEmpty(precinctId)) {
                if (precinctId.contains(PrecinctEnum.YJADMIN.getId())) {
                    precinctId = PrecinctEnum.SUPERADMIN.getId();
                }
            }
            if (pageNum == null && pageSize == null) {
                list = siteManageDao.queryForList(name, category, address, acreage, precinctTreeId, precinctId, MAXGALLERYFUL, streetName, community, type, null);
                map.put("list", list);
                Integer count = siteManageDao.queryForList(name, category, address, acreage, precinctTreeId, precinctId, MAXGALLERYFUL, streetName, community, type, null).size();
                map.put("count", count);
            } else {
                PageHelper.startPage(pageNum, pageSize);
                list = siteManageDao.queryForList(name, category, address, acreage, precinctTreeId, precinctId, MAXGALLERYFUL, streetName, community, type, null);
                map.put("list", new PageInfo<>(list));
            }
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    @Transactional
    public Result save(SiteManageBean siteManageBean) {
        Result result = new Result();
        try {
            int data = 0;
            if (ObjectUtils.isEmpty(siteManageBean.getId())) {
                data = siteManageDao.insert(siteManageBean);
            } else {
                data = siteManageDao.update(siteManageBean);
            }
            result.setData(data);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result remove(String id) {
        Result result = new Result();
        try {
            int data = 0;
            List<String> ids = Arrays.asList(id.split(","));
            data = siteManageDao.remove(ids);
            result.setData(data);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Value("${filePath}")
    String filePath;

    @Value("${urlPath}")
    String fileUrl;

    @Override
    public Result exportSite(String name, String category, String address, String acreage, String precinctTreeId, String precinctId, String MAXGALLERYFUL, String streetName, String community, String type, String ids) {
        Result result = new Result();
        List<SiteManageBean> list = siteManageDao.queryForList(name, category, address, acreage, precinctTreeId, precinctId, MAXGALLERYFUL, streetName, community, type, ids);
        if (CollectionUtils.isEmpty(list)) {
            log.error("list is null.");
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData("list is null.");
            return result;
        }
        for (SiteManageBean siteManageBean : list) {
            String state = siteManageBean.getState();
            switch (state) {
                case "1":
                    siteManageBean.setState("开放");
                    break;
                case "2":
                    siteManageBean.setState("有人");
                    break;
                case "3":
                    siteManageBean.setState("告警");
                    break;
                case "4":
                    siteManageBean.setState("没开放");
                    break;
                case "5":
                    siteManageBean.setState("暂不使用");
                    break;
                default:
                    siteManageBean.setState("");
                    break;
            }
        }
        List<Map<String, Object>> maps =
                JSON.parseObject(JSONObject.toJSONString(list), new TypeReference<List<Map<String, Object>>>() {
                });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String fileName = String.format("避难场所-%s.xlsx", simpleDateFormat.format(new Date()));
        if (!CollectionUtils.isEmpty(list)) {
            StringBuilder keysStr = new StringBuilder(100);
            StringBuilder columnNamesStr = new StringBuilder(100);
//            keysStr.append("name,type,category,roomCount,person,phone,community," +
//                    "acreage,state,responsibility,gpsx,gpsy,address");
            keysStr.append("避难场所名称,类型,类别,最大容纳人数,管理人员,管理员手机,所属社区," +
                    "面积（m2）,状态,应急职责,经度,纬度,地址");
            columnNamesStr.append("避难场所名称,类型,类别,最大容纳人数,管理人员,管理员手机,所属社区," +
                    "面积（m2）,状态,应急职责,经度,纬度,地址");
            String[] keys = keysStr.toString().split(",");
            String[] columnNames = columnNamesStr.toString().split(",");
            ExcelUtil.createWorkBook(maps, keys, columnNames, "Sheet1", null, "", filePath + "/site/" + fileName);
        }
        result.setData(fileUrl + "/site/" + fileName);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result importSite(MultipartFile file) {
        Result result = new Result();
        //入参校验
        String name = file.getOriginalFilename();
        if (StringUtils.isEmpty(name)) {
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
        Map<String, List<Map<String, String>>> map = ExcelUtil.getResult(file, 1);
        List<Map<String, String>> list1 = map.get("Sheet1");
        List<SiteManageBean> siteManageBeans = JSONObject.parseArray(JSON.toJSONString(list1), SiteManageBean.class);
        if (CollectionUtils.isEmpty(siteManageBeans)) {
            log.error("siteManageBeans is null.");
            result.setMessage("文件内容为空");
            result.setCode(500);
            return result;
        }
        for (SiteManageBean siteManageBean : siteManageBeans) {
            if (StringUtils.isBlank(siteManageBean.getName())) {
                continue;
            }
            String state = siteManageBean.getState();
            if (!StringUtils.isEmpty(state)) {
                switch (state) {
                    case "开放":
                        siteManageBean.setState("1");
                        break;
                    case "有人":
                        siteManageBean.setState("2");
                        break;
                    case "告警":
                        siteManageBean.setState("3");
                        break;
                    case "未开放":
                        siteManageBean.setState("4");
                        break;
                    case "暂不使用":
                        siteManageBean.setState("5");
                        break;
                    default:
                        siteManageBean.setState("");
                        break;
                }
            }
            siteManageDao.insert(siteManageBean);
        }
        return result;
    }

    @Override
    public Result queryForResource(String shelterOrga, String shelterName, String shelterCategory, String planId, Integer pageStart, Integer pageLen) {
        Result result = new Result();
        try {
            Integer start = 0;
            if (pageStart != null && pageLen != null) {
                start = pageLen * (pageStart - 1);
            }
            //获取所有数据
            List<Map<String, Object>> data = siteManageDao.queryForResource(shelterOrga, shelterName, shelterCategory, planId, start, pageLen);
            List<Map<String, Object>> list = siteManageDao.queryForResource(shelterOrga, shelterName, shelterCategory, planId, null, null);
            Map<String, Object> map = new HashMap<>();
            map.put("count", list.size());
            map.put("data", data);
            result.setData(map);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result selectBasicShelterList(String shelterOrga, String shelterName, String shelterCategory, String eventId, String isAll, Integer pageStart, Integer pageLen) {

        Result result = new Result();
        try {
            Integer start = 0;
            if (pageStart != null && pageLen != null) {
                start = pageLen * (pageStart - 1);
            }
            String tempEventId = eventId;
            if ("1".equals(isAll)) {
                tempEventId = null;
            }
            //获取所有数据
            List<Map<String, Object>> data = siteManageDao.selectBasicShelterEntityList(shelterOrga, shelterName, shelterCategory, tempEventId, start, pageLen);
            //计算距离
            if (!org.springframework.util.StringUtils.isEmpty(eventId) && !CollectionUtils.isEmpty(data)) {
                Map<String, String> map = eventDao.queryGps(eventId);
                if (map != null) {
                    data.forEach(temp -> temp.put("distance", CommonUtils.calculateDistance(map.get("lat"), map.get("lon"), (String) temp.get("shelterLatitude"), (String) temp.get("shelterLongitude"))));
                } else {
                    data.forEach(temp -> temp.put("distance", null));
                }
            }
            List<Map<String, Object>> list = siteManageDao.selectBasicShelterEntityList(shelterOrga, shelterName, shelterCategory, eventId, null, null);
            Map<String, Object> map = new HashMap<>();
            map.put("count", list.size());
            map.put("data", data);
            result.setData(map);
            result.setResutCode(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

}

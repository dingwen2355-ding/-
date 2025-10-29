package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.StringUtil;
import com.znv.manage.dao.RegionalManagementDao;
import com.znv.manage.service.RegionalManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: dailongli
 * @Date: 2018/8/21 13:49
 */
@Service
@Slf4j
public class RegionalManagementServiceImpl implements RegionalManagementService {

    @Autowired
    RegionalManagementDao regionalManagementDao;

    /**
     * 功能描述：根据活动查询区域
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     *
     * @param eventId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryRelationPrecinctIds(String eventId) {
        return regionalManagementDao.queryRelationPrecinctIds(eventId);
    }

    /**
     * 功能描述：根据区域查询活动
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     *
     * @param precinctId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryEventIds(String precinctId) {
        return regionalManagementDao.queryEventIds(precinctId);
    }

    /**
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
     * @param precinctKindTree
     * @return
     */
    @Override
    public List<Map<String, String>> select(String precinctId, String address, String precinctName, String upPrecinctId, String precinctKind, String regionXY, String areaCode, String gpsX, String gpsY, String gpsZ, String gpsType, String precinctKindTree) {
        return regionalManagementDao.select(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, precinctKindTree);
    }


    /**
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
     * @param orderNum
     */
    @Override
    public void update(String precinctId, String address, String precinctName, String upPrecinctId, String precinctKind, String regionXY, String areaCode, String gpsX, String gpsY, String gpsZ, String gpsType, String orderNum) {
        regionalManagementDao.update(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, orderNum);
//        regionalManagementDao.updateRelPrecinct(precinctId, precinctName, upPrecinctId, precinctKind);
    }

    /**
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
     */
    @Override
    public void insert(String precinctId, String address, String precinctName, String upPrecinctId, String precinctKind, String regionXY, String areaCode, String gpsX, String gpsY, String gpsZ, String gpsType, String createUser, String orderNum) {
        regionalManagementDao.insert(precinctId, address, precinctName, upPrecinctId, precinctKind, regionXY, areaCode, gpsX, gpsY, gpsZ, gpsType, createUser, orderNum);
    }

    /**
     * 获取区域数量
     *
     * @param precinctId
     * @return
     */
    @Override
    public int getnum(String precinctId) {
        return regionalManagementDao.getnum(precinctId);
    }

    /**
     * 查询所有子区域
     *
     * @param precinctId
     * @return
     */
    @Override
    public List<String> querySubRegional(String precinctId) {
        ArrayList result = new ArrayList();
        findSubRegional(result, precinctId);
        return result;
    }

    /**
     * 获取子区域方法，只是子区域，没孙子区域
     *
     * @param precinctId
     * @return
     */
    @Override
    public List<String> querySubRegionalId(String precinctId) {
        return regionalManagementDao.querySubRegional(precinctId);
    }

    /**
     * 递归获取所有子区域，包含孙子区域
     *
     * @param result
     * @param precinctId
     */
    private void findSubRegional(ArrayList<String> result, String precinctId) {
        List<String> subPrecinctList = querySubRegionalId(precinctId);
        if (!subPrecinctList.isEmpty()) {
            result.addAll(subPrecinctList);
            String subPrecinctIds = StringUtil.arrayList2String((ArrayList<String>) subPrecinctList);
            findSubRegional(result, subPrecinctIds);
        }
    }

    /**
     * 查询中心编码
     *
     * @return
     */
    @Override
    public String queryCenterCode() {
        return regionalManagementDao.queryCenterCode();
    }

    /**
     * 查询gps类型
     *
     * @return
     */
    @Override
    public List<Map<String, String>> queryGpsType() {
        return regionalManagementDao.queryGpsType();
    }

    /**
     * 查询区域类型
     *
     * @return
     */
    @Override
    public List<Map<String, String>> queryPrecinctKind() {
        return regionalManagementDao.queryPrecinctKind();
    }


    /**
     * 获取标准区域ID
     *
     * @param paramMap
     * @return
     */
    @Override
    public String getStdId(Map<String, Object> paramMap) {
        return regionalManagementDao.getStdId(paramMap);
    }

    /**
     * 获取扩展表信息
     *
     * @param precinctId
     * @return
     */
    @Override
    public List<Map<String, String>> queryExpansion(String precinctId) {
        return regionalManagementDao.queryExpansion(precinctId);
    }


    /**
     * 查询组织树
     *
     * @param upPrecinctId
     * @param precinctKind
     * @return
     */
    @Override
    public List<Map<String, String>> queryPrecinctById(String upPrecinctId, String precinctKind, String forStreet) {
        return regionalManagementDao.queryPrecinctById(upPrecinctId, precinctKind, forStreet);
    }

    /**
     * @param precinctId
     */
    @Override
    public Result delete(String precinctId) {
        //查询所有子区域id
        List<String> list = querySubRegional(precinctId);
        list.add(precinctId);
        //判断设备 房屋 警员 群防群治 单位等表中是否有区域id等于上面的区域id的
        for (String id : list) {
            if (regionalManagementDao.queryPrecinct(id, "t_cfg_device") > 0) {
                return new Result(400, "不能删除，有设备挂在该区域");
            }
//            if (regionalManagementDao.queryPrecinct(id, "t_cfg_house") != null) {
//                return new Result(400, "不能删除，有房屋挂在该区域");
//            }
//            if (regionalManagementDao.queryPrecinct(id, "t_cfg_police") != null) {
//                return new Result(400, "不能删除，有警员属于该区域");
//            }
//            if (regionalManagementDao.queryPrecinct(id, "t_cfg_protect") != null) {
//                return new Result(400, "不能删除，有群防群治挂在该区域");
//            }
//            if (regionalManagementDao.queryPrecinct(id, "t_cfg_company") != null) {
//                return new Result(400, "不能删除，有单位挂在该区域");
//            }
        }
        //如果都没有，则删除该区域和其所有子区域
        for (String str : list) {
            regionalManagementDao.delete(str);
//            regionalManagementDao.deleteRelPrecinct(str);
        }
        return new Result(200, "删除成功");


    }

    /**
     * 插入区域时，顺便将信息插入到另一个关联表
     *
     * @param precinctId
     * @param precinctName
     * @param upPrecinctId
     * @param precinctKind
     */
    @Override
    public void insertRelPrecinct(String precinctId, String precinctName, String upPrecinctId, String precinctKind) {
        regionalManagementDao.insertRelPrecinct(precinctId, precinctName, upPrecinctId, precinctKind);
    }

    /**
     * * 根据cardId查询对应的组织机构权限
     *
     * @param cardId
     * @param authType
     */
    @Override
    public Set<String> addPrivilegePrecinctIds(String cardId, int authType) {
        String precinctIds = regionalManagementDao.queryPrivilegePrecinctIds(cardId, authType);

        if (StringUtils.isEmpty(precinctIds)) {
            return null;
        } else {
            Set<String> set = new HashSet<>();

            String[] precinctIdArray = precinctIds.split(",");
            for (String tempPrecinctId : precinctIdArray) {
                set.add(tempPrecinctId);
            }
            return set;
        }
    }

    /**
     * * 查询下级precinctId
     *
     * @param set
     * @return
     */
    @Override
    public Set<String> queryPrecinctIdsDown(Set<String> set) {

        Set<String> result = new HashSet<>();
        for (String str : set) {
            String precinctIds = regionalManagementDao.queryPrecinctIdsDown(str);

            if (!StringUtils.isEmpty(precinctIds)) {
                String[] precinctIdArray = precinctIds.split(",");
                for (String tempPrecinctId : precinctIdArray) {
                    result.add(tempPrecinctId);
                }
            }
        }

        return result;

    }

}

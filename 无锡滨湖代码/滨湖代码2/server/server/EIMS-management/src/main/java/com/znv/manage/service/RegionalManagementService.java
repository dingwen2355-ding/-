package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: dailongli
 * @Date: 2018/8/21 13:48
 */
public interface RegionalManagementService {

    /**
     * 功能描述：根据活动查询区域
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     * @param eventId
     * @return
     */
    List<Map<String,Object>> queryRelationPrecinctIds(String eventId);
    /**
     * 功能描述：根据区域查询活动
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     * @param precinctId
     * @return
     */
    List<Map<String,Object>> queryEventIds(String precinctId);
    /**
     * 查询
     * @param precinctId
     * @param address
     * @param PrecinctName
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
    List<Map<String,String>> select(String precinctId, String address, String PrecinctName, String upPrecinctId, String precinctKind, String regionXY, String areaCode, String gpsX, String gpsY, String gpsZ, String gpsType, String precinctKindTree);

    /**
     * 删除
     * @param precinctId
     */
    Result delete(String precinctId);

    /**
     * 更新
     * @param precinctId
     * @param address
     * @param PrecinctName
     * @param upPrecinctId
     * @param precinctKind
     * @param regionXY
     * @param areaCode
     * @param gpsX
     * @param gpsY
     * @param gpsZ
     * @param gpsType
     */
    void update(String precinctId, String address, String PrecinctName, String upPrecinctId, String precinctKind, String regionXY, String areaCode, String gpsX, String gpsY, String gpsZ, String gpsType, String orderNum);

    /**
     * 插入
     * @param precinctId
     * @param address
     * @param PrecinctName
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
    void insert(String precinctId, String address, String PrecinctName, String upPrecinctId, String precinctKind, String regionXY, String areaCode, String gpsX, String gpsY, String gpsZ, String gpsType, String createUser, String orderNum);

    /**
     * 获取数量
     * @param precinctId
     * @return
     */
    int getnum(String precinctId);

    /**
     * 查询子区域信息
     * @param precinctId
     * @return
     */
    List<String> querySubRegional(String precinctId);

    /**
     * 查询子区域信息，不包含孙子区域
     * @param precinctId
     * @return
     */
    List<String> querySubRegionalId(String precinctId);

    /**
     * 查询中心编码
     * @return
     */
    String queryCenterCode();

    /**
     * 查询gpstype
     * @return
     */
    List<Map<String ,String>> queryGpsType();

    /**
     * 查询区域类型
     * @return
     */
    List<Map<String ,String>> queryPrecinctKind();

    /**
     * 获取标准id
     * @param paramMap
     * @return
     */
    String getStdId(Map<String, Object> paramMap);

    /**
     * 查询扩展信息
     * @param precinctId
     * @return
     */
    List<Map<String ,String>> queryExpansion(String precinctId);

    /**
     * 插入区域时，顺便将信息插入到另一个关联表
     * @param precinctId
     * @param precinctName
     * @param upPrecinctId
     * @param precinctKind
     */
    void insertRelPrecinct(String precinctId, String precinctName, String upPrecinctId, String precinctKind);

    /**
     * 查询组织树
     * @param upPrecinctId
     * @param precinctKind
     * @return
     */
    List<Map<String ,String>> queryPrecinctById(String upPrecinctId, String precinctKind, String forStreet);

    /**
     ** 根据cardId查询对应的组织机构权限
     * @param cardId
     * @param authType
     */
    Set<String> addPrivilegePrecinctIds(String cardId, int authType);
    
    /**
     ** 查询下级precinctId 
     * @param set
     * @return
     */
    Set<String> queryPrecinctIdsDown(Set<String> set);
    
}

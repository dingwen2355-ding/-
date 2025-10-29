package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: dailongli
 * @Date: 2018/8/21 13:47
 */
@Repository
public interface RegionalManagementDao {

    /**
     * 功能描述：根据活动查询区域
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     *
     * @param eventId
     * @return
     */
    List<Map<String, Object>> queryRelationPrecinctIds(@Param("eventId") String eventId);

    /**
     * 功能描述：根据区域查询活动
     * 创建时间：2019/9/24
     * 创 建 人：chenhui
     *
     * @param precinctId
     * @return
     */
    List<Map<String, Object>> queryEventIds(@Param("precinctId") String precinctId);

    /**
     * 查询区域，不含有子区域
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
     * @param precinctKindTree
     * @return
     */
    List<Map<String, String>> select(@Param("precinctId") String precinctId, @Param("address") String address,
                                     @Param("precinctName") String precinctName, @Param("upPrecinctId") String upPrecinctId,
                                     @Param("precinctKind") String precinctKind, @Param("regionXY") String regionXY,
                                     @Param("areaCode") String areaCode, @Param("gpsX") String gpsX,
                                     @Param("gpsY") String gpsY, @Param("gpsZ") String gpsZ,
                                     @Param("gpsType") String gpsType,
                                     @Param("precinctKindTree") String precinctKindTree);

    /**
     * 删除区域
     *
     * @param precinctId
     */
    void delete(@Param("precinctId") String precinctId);

    /**
     * 更新区域
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
     */
    void update(@Param("precinctId") String precinctId, @Param("address") String address,
                @Param("precinctName") String precinctName, @Param("upPrecinctId") String upPrecinctId,
                @Param("precinctKind") String precinctKind, @Param("regionXY") String regionXY,
                @Param("areaCode") String areaCode, @Param("gpsX") String gpsX,
                @Param("gpsY") String gpsY, @Param("gpsZ") String gpsZ,
                @Param("gpsType") String gpsType,@Param("orderNum") String orderNum);

    /**
     * 插入区域
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
     * @param orderNum
     */
    void insert(@Param("precinctId") String precinctId, @Param("address") String address,
                @Param("precinctName") String precinctName, @Param("upPrecinctId") String upPrecinctId,
                @Param("precinctKind") String precinctKind, @Param("regionXY") String regionXY,
                @Param("areaCode") String areaCode, @Param("gpsX") String gpsX,
                @Param("gpsY") String gpsY, @Param("gpsZ") String gpsZ,
                @Param("gpsType") String gpsType,
                @Param("createUser") String createUser,@Param("orderNum") String orderNum);

    /**
     * 获取区域数量
     *
     * @param precinctId
     * @return
     */
    int getnum(@Param("precinctId") String precinctId);


    /**
     * 查询所有子区域
     *
     * @param precinctId
     * @return
     */
    List<String> querySubRegional(String precinctId);

    /**
     * 查询中心编码
     *
     * @return
     */
    String queryCenterCode();

    /**
     * 查询gps类型
     *
     * @return
     */
    List<Map<String, String>> queryGpsType();

    /**
     * 查询区域类型
     *
     * @return
     */
    List<Map<String, String>> queryPrecinctKind();


    /**
     * 获取扩展表信息
     *
     * @param precinctId
     * @return
     */
    List<Map<String, String>> queryExpansion(@Param("precinctId") String precinctId);

    /**
     * 查询组织树
     *
     * @param upPrecinctId
     * @param precinctKind
     * @return
     */
    List<Map<String, String>> queryPrecinctById(@Param("upPrecinctId") String upPrecinctId,
                                                @Param("precinctKind") String precinctKind,
                                                @Param("forStreet") String forStreet);


    /**
     * 插入区域时，顺便将信息插入到另一个关联表
     *
     * @param precinctId
     * @param precinctName
     * @param upPrecinctId
     * @param precinctKind
     */
    void insertRelPrecinct(@Param("precinctId") String precinctId, @Param("precinctName") String precinctName,
                           @Param("upPrecinctId") String upPrecinctId, @Param("precinctKind") String precinctKind);

    /**
     * 判断区域id是否在别的表里面存在
     *
     * @param precinctId
     * @param tableName
     * @return
     */
    int queryPrecinct(@Param("precinctId") String precinctId, @Param("tableName") String tableName);


    void deleteRelPrecinct(@Param("precinctId") String precinctId);

    void updateRelPrecinct(@Param("precinctId") String precinctId,
                           @Param("precinctName") String precinctName, @Param("upPrecinctId") String upPrecinctId,
                           @Param("precinctKind") String precinctKind);

    String queryPrivilegePrecinctIds(@Param("cardId") String cardId, @Param("authType") int authType);

    String queryPrecinctIdsDown(@Param("precinctId") String precinctId);

    String getStdId(Map<String, Object> paramMap);
}

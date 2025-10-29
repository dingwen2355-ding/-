package com.znv.manage.precinct.dao;

import com.znv.manage.common.bean.Precinct;
import com.znv.manage.precinct.model.PrecinctKind;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrecinctServiceMapper {

    List<Map<String,Object>> queryStreetInfo(@Param("precinctId") String precinctId);

    List<Map<String,Object>> queryCommitteeList(
           // @Param("precinctIds") String precinctIds
    );

    /**
     * @return 区域信息
     */
    List<Precinct> queryPrecinctList(@Param(value = "precinctId") String precinctId,

                                     @Param(value = "precinctName") String precinctName,

                                     @Param(value = "upPrecinctId") String upPrecinctId,

                                     @Param(value = "precinctKind") Integer precinctKind,

                                     @Param(value = "regionName") String regionName,

                                     @Param(value = "streetName") String streetName,

                                     @Param(value = "streetNo") String streetNo,

                                     @Param(value = "buildingNo") String buildingNo,

                                     @Param(value = "unitNo") String unitNo,

                                     @Param(value = "floorNo") String floorNo,

                                     @Param(value = "houseNo") String houseNo);
    
    List<Precinct> queryPrecinctListFor2d(@Param(value = "precinctId") String precinctId,

                                          @Param(value = "precinctName") String precinctName,

                                          @Param(value = "upPrecinctId") String upPrecinctId,

                                          @Param(value = "precinctKind") Integer precinctKind,

                                          @Param(value = "regionName") String regionName,

                                          @Param(value = "streetName") String streetName,

                                          @Param(value = "streetNo") String streetNo,

                                          @Param(value = "buildingNo") String buildingNo,

                                          @Param(value = "unitNo") String unitNo,

                                          @Param(value = "floorNo") String floorNo,

                                          @Param(value = "houseNo") String houseNo);

    List<Precinct> queryPrecinctListByKind(@Param(value = "precinctId") String precinctId,
                                           @Param(value = "precinctKind") Integer precinctKind);

    List<Map<String, String>> queryPrecinctCamera(@Param(value = "precinctId") String precinctId);

    /**
     * 查询区域内人口及房屋的数量
     */
    List<Map<String, String>> queryBuildAndPopulationInPrecinct(@Param(value = "precinctId") String precinctId);

    List<Map<String, String>> queryPrecinctSixActualPower(@Param(value = "precinctId") String precinctId);

    List<Map<String, Object>> queryPrecinctIdByMapAddr(@Param(value = "mapAddr") String mapAddr,
                                                       @Param(value = "precinctKind") String precinctKind);

    void updatePrecinctCoordinate(@Param("precinctId") String precinctId,
                                  @Param("gpsX") Double gpsX,
                                  @Param("gpsY") Double gpsY,
                                  @Param("regionXY") String regionXY);


    List<Precinct> queryPrecinct(
            @Param("precinctKind") Integer precinctKind,
            @Param("precinctId") String precinctId,
            @Param("upPrecinctId") String upPrecinctId,
            @Param("precinctIds")String precinctIds);

    List<Precinct> queryPrecinctByKind(
            @Param("precinctKind") Integer precinctKind,
            @Param("forStreet") String forStreet);

    int updatePrecinct(@Param(value = "precinctId") String precinctId,
                                  @Param(value = "precinctName") String precinctName,
                                  @Param(value = "precinctAddr") String precinctAddr,
                                  @Param(value = "upPrecinctId") String upPrecinctId,
                                  @Param(value = "precinctKind") Integer precinctKind,
                                  @Param(value = "regionName") String regionName,
                                  @Param(value = "streetName") String streetName,
                                  @Param(value = "streetNo") String streetNo,
                                  @Param(value = "buildingNo") String buildingNo,
                                  @Param(value = "unitNo") String unitNo,
                                  @Param(value = "floorNo") String floorNo,
                                  @Param(value = "houseNo") String houseNo);

    List<PrecinctKind> queryPrecinctKind();

    int addPrecinct( @Param(value = "precinctId") String precinctId,
                     @Param(value = "precinctName") String precinctName,
                     @Param(value = "precinctAddr") String precinctAddr,
                     @Param(value = "upPrecinctId") String upPrecinctId,
                     @Param(value = "precinctKind") Integer precinctKind,
                     @Param(value = "regionName") String regionName,
                     @Param(value = "streetName") String streetName,
                     @Param(value = "streetNo") String streetNo,
                     @Param(value = "buildingNo") String buildingNo,
                     @Param(value = "unitNo") String unitNo,
                     @Param(value = "floorNo") String floorNo,
                     @Param(value = "houseNo") String houseNo,
                     @Param(value = "gpsx") String gpsx,
                     @Param(value = "gpsy") String gpsy);

    int deletePrecinct(@Param(value = "precinctId")String precinctId);

    int deletePrecincts(@Param(value = "precinctId") String precinctId);
    int deleteUpPrecincts(@Param(value = "upPrecinctId") String upPrecinctId);

    int updatePrecinctIds(@Param("precinctIds")String precinctIds,@Param("userId")String userId);

    String queryLastPrecinctId(@Param(value = "upPrecinctId") String upPrecinctId);

    int insertExcel(
            @Param(value = "precinctId") String precinctId,
            @Param(value = "precinctName") String precinctName,
            @Param(value = "precinctAddr") String precinctAddr,
            @Param(value = "upPrecinctId") String upPrecinctId,
            @Param(value = "precinctKind") Integer precinctKind,
            @Param(value = "regionName") String regionName,
            @Param(value = "streetName") String streetName,
            @Param(value = "streetNo") String streetNo,
            @Param(value = "buildingNo") String buildingNo,
            @Param(value = "unitNo") String unitNo,
            @Param(value = "floorNo") String floorNo,
            @Param(value = "houseNo") String houseNo,
            @Param(value = "mapAddr") String mapAddr,
            @Param(value = "houseArea") String houseArea,
            @Param(value = "gpsX") String gpsX,
            @Param(value = "gpsY") String gpsY,
            @Param(value = "regionXY") String regionXY
    );

    String queryCity(@Param(value = "areaCode") String areaCode);
}

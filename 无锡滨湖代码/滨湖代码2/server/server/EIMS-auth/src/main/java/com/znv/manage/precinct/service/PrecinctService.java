package com.znv.manage.precinct.service;

import com.znv.manage.common.bean.Precinct;
import com.znv.manage.precinct.model.PrecinctKind;

import java.util.List;
import java.util.Map;

public interface PrecinctService {

    List<Map<String, Object>> queryPrecinctTree(String precinctId, String userId);

    List<Precinct> queryPrecinct(Integer precinctKind, String precinctId, String upPrecinctId,String precinctIds);

    List<Precinct> queryPrecinctByKind(Integer precinctKind, String forStreet);

    int updatePrecinct(String precinctId, String precinctName, String precinctAddr, String upPrecinctId, Integer precinctKind, String regionName, String streetName, String streetNo, String buildingNo, String unitNo, String floorNo, String houseNo);

    List<PrecinctKind> queryPrecinctKind();

    int addPrecinct(String precinctId, String precinctName,
                    String precinctAddr, String upPrecinctId,
                    Integer precinctKind, String regionName,
                    String streetName, String streetNo, String buildingNo,
                    String unitNo, String floorNo, String houseNo,String userId);

    int deletePrecinct(String precinctId);

    int deletePrecincts(String precinctId);

    int insertExcel(String precinctId, String precinctName,
                    String precinctAddr, String upPrecinctId,
                    Integer precinctKind, String regionName,
                    String streetName, String streetNo, String buildingNo,
                    String unitNo, String floorNo, String houseNo,
                    String mapAddr, String houseArea, String gpsX,
                    String gpsY, String regionXY);
}

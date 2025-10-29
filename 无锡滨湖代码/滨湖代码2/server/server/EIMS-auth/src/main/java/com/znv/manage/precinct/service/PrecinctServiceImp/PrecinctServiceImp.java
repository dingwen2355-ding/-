package com.znv.manage.precinct.service.PrecinctServiceImp;

import com.znv.manage.common.bean.Precinct;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.GpsUtils;
import com.znv.manage.precinct.dao.PrecinctServiceMapper;
import com.znv.manage.precinct.model.PrecinctKind;
import com.znv.manage.precinct.service.PrecinctService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class PrecinctServiceImp implements PrecinctService {

    @Autowired
    private PrecinctServiceMapper precinctServiceMapper;

    @Override
    public List<Map<String, Object>> queryPrecinctTree(String precinctId,String userId) {
//        String precinctIds = CommonUtils.getUserPrecicntIdsByUserId(userId);
        List<Map<String,Object>> list = precinctServiceMapper.queryStreetInfo(precinctId);
        List<Map<String,Object>> committeeList = precinctServiceMapper.queryCommitteeList();
        list.get(0).put("children",committeeList);
//        if(StringUtils.isEmpty(precinctIds)){
//            return null;
//        }else {
//            if("exception".equals(precinctIds)){
//                list.get(0).put("children",null);
//            }else{
//                List<Map<String,Object>> committeeList = precinctServiceMapper.queryCommitteeList(precinctIds);
//                list.get(0).put("children",committeeList);
//            }
//        }

        return list;
    }

    @Override
    public List<Precinct> queryPrecinct(Integer precinctKind, String precinctId, String upPrecinctId, String precinctIds) {
        return precinctServiceMapper.queryPrecinct(precinctKind, precinctId, upPrecinctId, precinctIds);
    }

    @Override
    public List<Precinct> queryPrecinctByKind(Integer precinctKind, String forStreet) {
        return precinctServiceMapper.queryPrecinctByKind(precinctKind, forStreet);
    }

    @Override
    public int updatePrecinct(String precinctId, String precinctName, String precinctAddr,
                              String upPrecinctId, Integer precinctKind, String regionName,
                              String streetName, String streetNo, String buildingNo,
                              String unitNo, String floorNo, String houseNo) {
        return precinctServiceMapper.updatePrecinct(precinctId, precinctName, precinctAddr, upPrecinctId,
                precinctKind, regionName, streetName, streetNo, buildingNo, unitNo, floorNo, houseNo);
    }

    @Override
    public List<PrecinctKind> queryPrecinctKind() {
        List<PrecinctKind> list = new ArrayList<>();
        List<PrecinctKind> list1 = new ArrayList<>();
        list = precinctServiceMapper.queryPrecinctKind();
        for (PrecinctKind precinctKind : list) {
            if ((precinctKind.getPrecinctKind() > 2 && precinctKind.getPrecinctKind() < 8)
                    || (precinctKind.getPrecinctKind() > 10)) {
                if (precinctKind.getPrecinctKind() == 100) {
                    precinctKind.setName("行政区划");
                }
                list1.add(precinctKind);
            }
        }
        return list1;
    }

    @Override
    public int addPrecinct(String precinctId, String precinctName,
                           String precinctAddr, String upPrecinctId,
                           Integer precinctKind, String regionName,
                           String streetName, String streetNo,
                           String buildingNo, String unitNo,
                           String floorNo, String houseNo, String userId) {
        if (null == upPrecinctId) {
            log.error("upPrecinctId is null");
            return -1;
        }
        String lastPrecinctId = precinctServiceMapper.queryLastPrecinctId(upPrecinctId);
        if (lastPrecinctId != null) {
            String sub = lastPrecinctId.substring(lastPrecinctId.length() - 2);
            int num = Integer.parseInt(sub) + 1;
            if (num >= 10 && num < 99) {
                precinctId = upPrecinctId + "0" + num;
            } else if (num < 10) {
                precinctId = upPrecinctId + "00" + num;
            } else if (num >= 100) {
                precinctId = upPrecinctId + num;
            }
        } else {
            precinctId = upPrecinctId + "001";
        }
        String precinctIds = CommonUtils.getUserPrecicntIdsByUserId(userId);
        precinctIds = precinctIds + "," + precinctId;
        precinctServiceMapper.updatePrecinctIds(precinctIds, userId);
        String address = precinctName + streetName;

        String city = precinctServiceMapper.queryCity(precinctId.substring(0, 6));
        Map<String, String> gpsMap = GpsUtils.getGps(city, address);
        String gpsx = "";
        String gpsy = "";
        if (!CollectionUtils.isEmpty(gpsMap)) {
            double[] gps = GpsUtils.gcj02_To_Gps84(Double.valueOf(gpsMap.get("lat")),
                    Double.valueOf(gpsMap.get("lon")));
            // 这里假设对应单元格原来的类型也是String类型
            gpsx = String.format("%.6f", gps[1]);
            gpsy = String.format("%.6f", gps[0]);
        }
        return precinctServiceMapper.addPrecinct(precinctId, precinctName, precinctAddr,
                upPrecinctId, precinctKind, regionName, streetName,
                streetNo, buildingNo, unitNo, floorNo, houseNo, gpsx, gpsy);
    }

    @Override
    public int deletePrecinct(String precinctId) {
        int i = precinctServiceMapper.deletePrecincts(precinctId);
        i += precinctServiceMapper.deleteUpPrecincts(precinctId);
        return i;
    }

    @Override
    public int deletePrecincts(String precinctId) {
        int i = precinctServiceMapper.deletePrecincts(precinctId);
        i += precinctServiceMapper.deleteUpPrecincts(precinctId);
        return i;
    }

    @Override
    public int insertExcel(String precinctId, String precinctName, String precinctAddr,
                           String upPrecinctId, Integer precinctKind, String regionName,
                           String streetName, String streetNo, String buildingNo,
                           String unitNo, String floorNo, String houseNo,
                           String mapAddr, String houseArea, String gpsX,
                           String gpsY, String regionXY) {
        return precinctServiceMapper.insertExcel(precinctId, precinctName,
                precinctAddr, upPrecinctId,
                precinctKind, regionName,
                streetName, streetNo, buildingNo,
                unitNo, floorNo, houseNo,
                mapAddr, houseArea, gpsX,
                gpsY, regionXY);
    }
}

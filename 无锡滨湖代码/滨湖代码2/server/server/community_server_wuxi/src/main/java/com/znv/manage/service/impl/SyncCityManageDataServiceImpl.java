package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.SyncCityManageData;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.SyncCityManageDataMapper;
import com.znv.manage.service.SyncCityManageDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SyncCityManageDataServiceImpl implements SyncCityManageDataService {

    @Autowired
    SyncCityManageDataMapper syncCityManageDataMapper;

    @Value("${url}")
    private String IP;

    private String prefix = "/service/api/zhb/";
    private String[] arrayList = {
            "LS_HDJSP_PT",
            "LS_GDLJX_PT",
            "LS_JTZY_PT",
            "LS_DS_PT",
            "LS_TXJZ_PT",
            "LS_DMP_PT",
            "LS_PQ_PT",
            "LS_LMP_PT",
            "LS_WSKJCZ_PT",
            "LS_XIUXT_PT",
            "LS_XCL_PT",
            "LS_GCZSP_PT",
            "LS_DLSSBZP_PT",
            "LS_DLSS_PT",
            "LS_LDWHSS_PT",
            "LS_RQJG_PT",
            "LS_GAJG_PT",
            "LS_RFGS_PT",
            "LS_CLJYQDZ_PT",
            "LS_JTXHD_PT",
            "LS_JTXHSS_PT",
            "LS_TCC_PT",
            "LS_HSSS_PT",
            "LS_DLXXXSP_PT",
            "LS_HJHB_PT",
            "LS_BMJG_PT",
            "LS_JTGT_PT",
            "LS_HFC_PT",
            "LS_TCMB_PT",
            "LS_LJJL_PT",
            "LS_DLLG_PT",
            "LS_LDBYQ_PT",
            "LS_TXLG_PT",
            "LS_FZT_PT",
            "LS_LDKZQ_PT",
            "LS_SYQBZ_PT",
            "LS_QG_PT",
            "LS_AQD_PT",
            "LS_DD_PT",
            "LS_LD_PT",
            "LS_BDD_PT",
            "LS_DHT_PT",
            "LS_JGD_PT",
            "LS_CPD_PT",
            "LS_DZGGP_PT",
            "LS_YT_PT",
            "LS_CGGT_PT",
            "LS_HWJSSS_PT",
            "LS_XXT_PT",
            "LS_RFZSP_PT",
            "LS_MT_PT",
            "LS_JTHL_PL",
            "LS_GYXTT_PT",
            "LS_CCZJ_PL",
            "LS_BYQX_PT",
            "LS_HDS_PL",
            "LS_SSJG_PT",
            "LS_RQTYZX_PT",
            "LS_SYHL_PL",
            "LS_GJZT_PT",
            "LS_SJ_PT",
            "LS_FJDCTFD_PT",
            "LS_SYBSP_PT",
            "LS_KHQ_PT",
            "LS_GSQ_PT",
            "LS_WSJG_PT",
            "LS_JTBZP_PT",
            "LS_DXJJX_PT",
            "LS_GGCS_PT",
            "LS_SYQJG_PT",
            "LS_YSBZ_PT",
            "LS_RXHDZ_PT",
            "LS_YSJG_PT",
            "LS_BDZ_PT",
            "LS_DLJG_PT",
            "LS_TSJG_PT",
            "LS_SYFSSS_PT",
            "LS_PBBS_PT",
            "LS_LDJG_PT",
            "LS_XHDDYJG_PT",
            "LS_JKDZY_PT",
            "LS_HWGG_PT",
            "LS_WLJG_PT",
            "LS_DSJG_PT",
            "LS_XFSS_PT",
            "LS_DXJG_PT",
            "LS_LD_PY"
    };

    @Override
    public int syncData() {
        int i = 0;
        try {
            Map<String, String> map = new HashMap<>();
            for (int j = 0; j < arrayList.length; j++) {
                String url = arrayList[i];
                log.info("---------正在处理第" + i + "个接口，" + arrayList[i] + "的接口信息");
                String newUrl = IP + prefix + url;
                Object object = AccessUtil.get(newUrl, map);
                log.info("---------第" + i + "个接口" + arrayList[i] + "的接口信息返回 : " + object);
                String returnJson = String.valueOf(object);
                JSONArray jsonArray = JSONArray.parseArray(returnJson);
                List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
                i += insertSyncDataList(dataArr);
            }
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }

    public int insertSyncDataList(List<JSONObject> list) {
        int i = 0;
        List<SyncCityManageData> insertSyncData = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertSyncDataList list.size为0");
            } else {
                for (JSONObject jsonObject : list) {
                    SyncCityManageData syncCityManageData = new SyncCityManageData();
                    syncCityManageData.setCategory(jsonObject.getString("category"));
                    syncCityManageData.setCollect_time(jsonObject.getString("collect_time"));
                    syncCityManageData.setCoordinate_system(jsonObject.getString("coordinate_system"));
                    syncCityManageData.setDepartment(jsonObject.getString("department"));
                    syncCityManageData.setId(jsonObject.getString("id"));
                    syncCityManageData.setMap_catalogue(jsonObject.getString("map_catalogue"));
                    syncCityManageData.setMap_name(jsonObject.getString("map_name"));
                    syncCityManageData.setREST_address(jsonObject.getString("REST_address"));
                    syncCityManageData.setWMS_address(jsonObject.getString("WMS_address"));
                    insertSyncData.add(syncCityManageData);
                }
            }
            if (insertSyncData.size() != 0) {
                i += syncCityManageDataMapper.syncData(insertSyncData);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

}

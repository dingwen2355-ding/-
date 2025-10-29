package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.system.po.SysDictData;
import cn.wxgis.jc.system.req.SysDictDataSaveRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysDictDataEntity {

    public static SysDictData getSaveDictData(SysDictDataSaveRequest request) {
        SysDictData data = new SysDictData();
        setCommonData(request, data);
        return data;
    }

    public static SysDictData getUpdateDictData(SysDictDataSaveRequest request, SysDictData data) {
        setCommonData(request, data);
        return data;
    }

    public static List<SysDictData> toSaveList(List<SysDictDataSaveRequest> requests) {
        List<SysDictData> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(requests)) return resultList;
        requests.forEach(i -> {
            SysDictData data = new SysDictData();
            setCommonData(i, data);
            resultList.add(data);
        });
        return resultList;
    }

    public static List<SysDictData> toUpdateList(List<SysDictDataSaveRequest> requests) {
        List<SysDictData> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(requests)) return resultList;
        requests.forEach(i ->{
            SysDictData data = new SysDictData();
            data.setId(i.getId());
            setCommonData(i, data);
            resultList.add(data);
        });
        return resultList;
    }

    private static void setCommonData(SysDictDataSaveRequest request, SysDictData data) {
        data.setDictType(request.getDictType());
        data.setDictLabel(request.getDictLabel());
        data.setDictValue(request.getDictValue());
        data.setOrderNum(request.getOrderNum());
        data.setStatus(request.getStatus());
        data.setRemark(request.getRemark());
    }

}

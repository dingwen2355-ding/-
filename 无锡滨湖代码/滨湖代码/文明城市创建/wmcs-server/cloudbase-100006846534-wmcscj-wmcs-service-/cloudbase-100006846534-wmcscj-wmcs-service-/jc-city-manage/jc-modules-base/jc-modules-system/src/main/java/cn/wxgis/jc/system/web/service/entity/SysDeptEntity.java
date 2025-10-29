package cn.wxgis.jc.system.web.service.entity;

import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.req.SysDeptSaveRequest;

import java.util.Date;

public class SysDeptEntity {

    public static SysDept getSaveDept(SysDeptSaveRequest request) {
        SysDept data = new SysDept();
        /*** 如果上级id为空则表示此数据为顶级数据，将parentId设置为0，并且将ancestors字段设置为0,当前id，表示当前数据的上下级集合 ***/
        setCommonData(request, data);
        return data;
    }

    public static SysDept getUpdateDept(SysDeptSaveRequest request, SysDept data) {
        setCommonData(request, data);
        return data;
    }

    private static void setCommonData(SysDeptSaveRequest request, SysDept data) {
        data.setName(request.getName());
        data.setLeader(request.getLeader());
        data.setPhone(request.getPhone());
        data.setEmail(request.getEmail());
        data.setOrderNum(request.getOrderNum());
        data.setType(request.getType());
        data.setStatus(request.getStatus());
        data.setRemark(request.getRemark());
    }
}

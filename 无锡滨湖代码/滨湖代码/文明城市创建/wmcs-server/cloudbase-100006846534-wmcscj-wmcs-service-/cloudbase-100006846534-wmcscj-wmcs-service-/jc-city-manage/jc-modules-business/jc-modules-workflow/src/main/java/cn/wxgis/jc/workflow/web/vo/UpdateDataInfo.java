package cn.wxgis.jc.workflow.web.vo;

import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.workflow.po.WfDeptEvent;
import cn.wxgis.jc.workflow.po.WfEventReport;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDataInfo {

    private WfEventReport eventReport;

    private WfDeptEvent deptEvent;

    private SysDept dept;

    private List<SysDept> deptList;

}

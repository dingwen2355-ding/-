package cn.wxgis.jc.screen.domain.vo.points;

import cn.wxgis.jc.civilization.data.po.DataCheckUser;
import cn.wxgis.jc.civilization.work.po.WorkCheckTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CheckListVo {

    @ApiModelProperty(value = "检查用户列表")
    private List<DataCheckUser> checkUserList;

    @ApiModelProperty(value = "检查任务列表")
    private List<WorkCheckTask> checkTaskList;
}

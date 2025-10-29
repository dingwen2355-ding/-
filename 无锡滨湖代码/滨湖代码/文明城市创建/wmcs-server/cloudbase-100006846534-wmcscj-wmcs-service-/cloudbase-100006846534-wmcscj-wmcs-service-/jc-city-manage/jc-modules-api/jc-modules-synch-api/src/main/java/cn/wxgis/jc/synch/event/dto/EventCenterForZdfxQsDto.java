package cn.wxgis.jc.synch.event.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 工单签收实体类
 */
@Data
public class EventCenterForZdfxQsDto {

    /**
     * 流程号 flowNo
     * 必填
     */
    private String flowNo;

    /**
     * 操作标签 optTag
     * NORMAL_FENPAI 分派
     * NORMALSUB_qianshou 签收
     * NORMAL_zhuanpai 转派
     * NORMALSUB_FINISH 处置
     * NORMALSUB_PASS 核查通过
     * NORMALSUB_nopass 核查不通过
     * postpone_sub 延期
     * yanqi_tongguo 延期通过
     * yanqi_butongguo 不通过
     * 必填
     */
    private String optTag = "NORMALSUB_qianshou";

    /**
     * 模型键
     * NORMAL_MAIN 主流程
     * NORMAL_SUB 子流程
     * 必填
     */
    private String modelKey = "NORMAL_SUB";

    /**
     * 任务分配列表
     */
    private List<TaskDistribute> taskDistributeList;

    /**
     * 处置对象
     */
    private Map<String, Object> mapVo;

    /**
     * 子工单处置单位ID
     */
    @NotBlank(message = "签收单位不能为空")
    private String subGroupId;

    /**
     * 完成结果
     */
    private String finishResult;

    /**
     * 附件相关
     */
    private Object listFile;

    /**
     * 转派目标
     */
    private String toGroupId;

    /**
     * 延期原因
     */
    private String postponeReason;

    /**
     * 核查意见 CheckOpinion
     */
    private String CheckOpinion;

}

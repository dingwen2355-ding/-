package com.znv.manage.common.bean.event;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.znv.manage.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TCfgEventTask)实体类
 *
 * @author makejava
 * @since 2022-12-09 16:32:53
 */
@Data
@NoArgsConstructor
@SuppressWarnings("CopyConstructorMissesField")
public class EventTask implements Serializable {
    private static final long serialVersionUID = -59755974444610649L;

    private Integer id;

    private EventTask(EventTask eventTask) {
        this.taskTitle = eventTask.taskTitle;
        this.contactId = eventTask.contactId;
        this.issueTime = eventTask.issueTime;
        this.status = eventTask.status;
        this.feedback = eventTask.feedback;
        this.fileVideo = eventTask.fileVideo;
        this.filePicture = eventTask.filePicture;
        this.fileVoice = eventTask.fileVoice;
        this.taskContent = eventTask.taskContent;
        this.dealTime = eventTask.dealTime;
    }


    @ApiModelProperty(value = "任务标题")
    private String taskTitle;

    @ApiModelProperty(value = "事件联系id")
    private Integer contactId;

    @ApiModelProperty(value = "下发时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date issueTime;

    @ApiModelProperty(value = "处置人")
    private String person;

    @ApiModelProperty(value = "处置人Id")
    private String personId;

    @ApiModelProperty(value = "反馈状态")
    private String status;

    @ApiModelProperty(value = "反馈内容")
    private String feedback;

    @ApiModelProperty(value = "文件地址-视频")
    private String fileVideo;

    @ApiModelProperty(value = "文件地址-图片")
    private String filePicture;

    @ApiModelProperty(value = "文件地址-音频")
    private String fileVoice;

    @ApiModelProperty(value = "处置部门")
    private String department;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "事件标题")
    private String eventTitle;

    @ApiModelProperty(value = "任务内容")
    private String taskContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "反馈时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dealTime;

    @ApiModelProperty(value = "人员信息-新增时使用")
    private List<PersonInfo> personInfoList;

    @Data
    private static class PersonInfo {
        @ApiModelProperty(value = "处置人Id")
        private String personId;
        @ApiModelProperty(value = "处置人部门")
        private String department;
        @ApiModelProperty(value = "处置人姓名")
        private String personName;
    }

    public List<EventTask> taskToList() throws BusinessException {
        if (CollectionUtils.isEmpty(personInfoList)) {
            throw new BusinessException("PersonInfo is null");
        }
        List<EventTask> result = new ArrayList<>();
        for (PersonInfo personInfo : this.getPersonInfoList()) {
            EventTask entity = new EventTask(this);
            entity.setPersonId(personInfo.getPersonId());
            entity.setDepartment(personInfo.getDepartment());
            entity.setPerson(personInfo.getPersonName());
            result.add(entity);
        }
        return result;
    }
}

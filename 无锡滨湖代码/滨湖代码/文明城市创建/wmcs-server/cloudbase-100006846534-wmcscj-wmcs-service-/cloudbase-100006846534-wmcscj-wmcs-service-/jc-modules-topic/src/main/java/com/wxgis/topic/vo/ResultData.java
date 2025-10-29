package com.wxgis.topic.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResultData {

    private Map<String, String> AlarmInfo;

    private String DecodeTimestamp;

    private String AlarmEvent;

    private Alarm alarm;

    private List<Alarm> ObjectList;

    private String CaptureImage;

    private String CaptureTime;

    private String FlowExecutor;

    private String TemplateId;

    private String TemplateName;

    private String ApplicationId;

    private String CreatedTime;

    private String ExecutionName;

    private String FlowCreator;

    private String DataSourceName;

    private String DataSourceType;

    private String FlowId;

    private String FrameSize;

    private String AlarmId;

    private String DataSourceId;

    private String ExecutionId;

    private String OverlyingInfo;

    private Overlying overlying;


}

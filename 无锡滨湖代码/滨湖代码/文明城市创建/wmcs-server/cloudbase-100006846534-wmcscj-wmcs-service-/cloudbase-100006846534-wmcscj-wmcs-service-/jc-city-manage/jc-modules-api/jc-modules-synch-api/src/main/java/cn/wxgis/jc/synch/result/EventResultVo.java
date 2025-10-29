package cn.wxgis.jc.synch.result;

import lombok.Data;

@Data
public class EventResultVo {

    private String code;

    private String data;

    private long serviceTime;

    private String msg;
}

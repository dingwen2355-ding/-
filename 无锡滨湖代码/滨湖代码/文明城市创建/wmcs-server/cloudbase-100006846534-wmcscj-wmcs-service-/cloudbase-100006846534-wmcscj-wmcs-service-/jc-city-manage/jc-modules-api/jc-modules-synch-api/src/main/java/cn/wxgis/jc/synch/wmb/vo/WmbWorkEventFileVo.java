package cn.wxgis.jc.synch.wmb.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class WmbWorkEventFileVo {

    private String code;

    private String fileType;

    private String name;

    private String ossName;

    private String uriBig;

    private String url;

    private String eventId;

}

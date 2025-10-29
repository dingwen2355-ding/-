package com.znv.manage.common.bean.information;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WarningRelease {
    private String id;

    private String warningTitle;

    private String warningType1;

    private String warningLevel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;

    private String warningContent;

    private String warningInfluence;

    private String warningType2;

    private String defenseAdvice;

    private String deal;

    private String dealContent;

    private String GRZ;

    private String CRZ;

    private String WRZ;

    private String area;

    private String flag;
}
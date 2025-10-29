package com.znv.manage.common.bean.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommandTransfer {
    private Long id;

    private String stuffName;

    private String stockName;

    private BigDecimal transferNum;

    private String dutyPerson;

    private String dutyPhone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date transferDate;

    private String content;
}
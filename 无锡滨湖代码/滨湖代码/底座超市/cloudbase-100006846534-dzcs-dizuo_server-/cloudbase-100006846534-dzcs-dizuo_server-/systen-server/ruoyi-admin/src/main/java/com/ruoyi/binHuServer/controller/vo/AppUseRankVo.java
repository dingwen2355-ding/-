package com.ruoyi.binHuServer.controller.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AppUseRankVo implements Serializable {


    private String name;

    private int number;

    private String percent;
}

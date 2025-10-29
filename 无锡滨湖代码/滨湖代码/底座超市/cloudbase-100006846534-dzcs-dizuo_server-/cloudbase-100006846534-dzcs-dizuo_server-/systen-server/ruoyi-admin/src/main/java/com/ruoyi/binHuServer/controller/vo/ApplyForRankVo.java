package com.ruoyi.binHuServer.controller.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ApplyForRankVo implements Serializable {

    private String name;

    private int value;


}

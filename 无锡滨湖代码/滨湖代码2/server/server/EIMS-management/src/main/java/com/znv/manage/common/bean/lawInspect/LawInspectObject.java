package com.znv.manage.common.bean.lawInspect;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class LawInspectObject {
    private Integer id;

    private String projectName;

    //检查内容
    private String checkContent;

    //违法行为
    private String problemDesc;

    //处置依据
    private String disposalBasis;

    //隐患类别
    private String hiddenDangerType;

    //处置类型
    private String enforceLawMeasureItem1;

    //公示状态
    private String publicStatus;

    //是否整改
    private String isChange;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
package com.ruoyi.common.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Data;

@JSONType
@Data
public class CommonRes {


    private Object data;

    /**
     * 返回码，200成功，其他失败
     */
    @JSONField(name = "data")
    private Integer code;


    @JSONField(name = "data",required = false)
    private Boolean success;

    @JSONField(name = "data",required = false)
    private String msg;
    /**
     * 对返回码的文本描述
     */
    @JSONField(name = "data",required = false)
    private String message;
}

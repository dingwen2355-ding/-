package com.znv.manage.common.bean.tip;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资讯管理
 *
 * @author chd
 * @date 2022/8/29
 */
@Data
public class InformationBean {

    String id;

    String title;

    String publisher;

    String releaseTime;

    String remark;

    String status;

    String informationType;

    String annexUrl;

    String isTop;

    String isTurn;
}

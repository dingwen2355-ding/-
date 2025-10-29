package com.znv.manage.common.bean.tip;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  待办提示
 *
 * @author chd
 * @date 2022/6/9
 */
@Data
public class ToDoTipBean {

    @ApiModelProperty(value = "标题")
    String title;

    @ApiModelProperty(value = "类型")
    String type;

    @ApiModelProperty(value = "路由")
    String router;

    @ApiModelProperty(value = "时间")
    String createTime;
}

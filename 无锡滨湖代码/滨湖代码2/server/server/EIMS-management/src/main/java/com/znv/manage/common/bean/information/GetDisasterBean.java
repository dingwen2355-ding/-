package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询灾情信息报送
 *
 * @author chd
 * @date 2022/6/8
 */
@Data
public class GetDisasterBean {

    @ApiModelProperty(value = "标题/状态/科室")
    String text;

    @ApiModelProperty(value = "后台使用，勿传")
    List<String> textPrecinctIds;

    @ApiModelProperty(value = "后台使用，勿传")
    String status;

    @ApiModelProperty(value = "后台使用，勿传")
    String precinctId;

    @ApiModelProperty(value = "报送时间开始")
    String repBeginTime;

    @ApiModelProperty(value = "报送时间结束")
    String repEndTime;

    @ApiModelProperty(value = "当前用户")
    String userId;

    @ApiModelProperty(value = "分页页数")
    Integer pageNum = 1;

    @ApiModelProperty(value = "分页大小")
    Integer pageSize = 10;
}

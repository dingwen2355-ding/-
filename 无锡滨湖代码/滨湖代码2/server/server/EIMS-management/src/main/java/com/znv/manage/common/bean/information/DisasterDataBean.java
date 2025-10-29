package com.znv.manage.common.bean.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 预案相关资料bean
 *
 * @author chd
 * @date 2022/5/6
 */
@Data
public class DisasterDataBean {
    @ApiModelProperty(value = "相关资料主键")
    String id;

    @ApiModelProperty(value = "相关资料灾情信息id")
    String disId;

    @ApiModelProperty(value = "文件名称")
    String fileName = "";

    @ApiModelProperty(value = "文件路径")
    String fileUrl = "";

    @ApiModelProperty(value = "创建时间")
    String createTime;
}

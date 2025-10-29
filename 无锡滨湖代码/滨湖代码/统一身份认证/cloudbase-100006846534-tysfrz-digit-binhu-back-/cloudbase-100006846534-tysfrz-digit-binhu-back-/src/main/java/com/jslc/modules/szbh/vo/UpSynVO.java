package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "上游同步", description = "上游同步")
public class UpSynVO {



    /**
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String dataSource;


    /**
     * 数据类型 名称
     */
    @ApiModelProperty(value = "数据类型 名称")
    private String dataTypeName;

    /**
     * 数据类型
     */
    @ApiModelProperty(value = "数据类型")
    private Integer dataType;


    /**
     * 自动同步
     */
    @ApiModelProperty(value = "自动同步")
    private String isEnableName;


    /**
     * 同步时间
     */
    @ApiModelProperty(value = "同步时间")
    private LocalDateTime createTime;




    /**
     * 同步状态
     */
    @ApiModelProperty(value = "同步状态")
    private String isSynName;

}

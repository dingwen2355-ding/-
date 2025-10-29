package com.znv.manage.common.bean.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (TCfgAssessProject)实体类
 *
 * @author makejava
 * @since 2022-12-09 13:39:56
 */
@Data
public class AssessProject implements Serializable {
    private static final long serialVersionUID = -60529475051165093L;
         
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "标准")
    private String standard;

}

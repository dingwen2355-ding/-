package com.jslc.modules.szbh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "组下的人员信息", description = "组下的人员信息")
public class GroupUserInfoVO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;


    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 用户账户
     */
    @ApiModelProperty(value = "用户账户")
    private String userAccount;


}

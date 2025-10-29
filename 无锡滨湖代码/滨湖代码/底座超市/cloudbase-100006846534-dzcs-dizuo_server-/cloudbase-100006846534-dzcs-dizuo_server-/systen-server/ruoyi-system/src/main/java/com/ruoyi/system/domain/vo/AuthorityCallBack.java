package com.ruoyi.system.domain.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "权限申请回调接口参数")
@Data
public class AuthorityCallBack implements Serializable {
    private static final long serialVersionUID = -389320229389481267L;

    /**
     * 审核状态: 0:通过 1:待审核  2:驳回 4:撤销
     */
    @ApiModelProperty(value = "审核状态: 0:通过 1:待审核  2:驳回 4:撤销", required = true)
    @NotNull(message = "审核状态不能为空")
    private int status;

    /**
     * 应用申请人账号
     */
    @ApiModelProperty(value = "应用申请人账号", required = true)
    @NotNull(message = "应用申请人账号不能为空")
    private String applyId;

    /**
     * 应用申请人_用户姓名
     */
    @ApiModelProperty(value = "应用申请人_用户姓名", required = true)
    @NotNull(message = "应用申请人_用户姓名不能为空")
    private String applyUserName;

    /**
     * 审核人用户账号
     */
    @ApiModelProperty(value = "审核人用户账号", required = true)
    @NotNull(message = "审核人用户账号不能为空")
    private String examineUserId;
    /**
     * 审批人姓名
     */
    @ApiModelProperty(value = "审批人姓名", required = true)
    @NotNull(message = "审批人姓名不能为空")
    private String examineUserName;
    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", required = true)
    @NotNull(message = "应用名称不能为空")
    private String appName;
}

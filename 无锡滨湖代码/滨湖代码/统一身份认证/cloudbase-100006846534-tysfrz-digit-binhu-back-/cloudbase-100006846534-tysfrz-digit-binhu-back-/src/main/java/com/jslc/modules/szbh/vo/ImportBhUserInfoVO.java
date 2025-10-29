package com.jslc.modules.szbh.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author BladeX
 * @since
 */
@Data
@ApiModel(value = "导入用户", description = "导入用户")
public class ImportBhUserInfoVO {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称")
    @ExcelProperty(value = "用户名称",index = 0)
    private String name;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    @ExcelProperty(value = "用户账号",index = 1)
    private String account;
//    /**
//     * 密码
//     */
//    @ApiModelProperty(value = "密码")
//    private String password;

//    /**
//     * 所属主部门
//     */
//    @ApiModelProperty(value = "所属主部门")
//    @ExcelProperty(value = "点位名称",index = 2)
//    private String majorDept;

    /**
     * 主部门职位
     */
    @ApiModelProperty(value = "主部门职位")
    @ExcelProperty(value = "主部门职位",index = 2)
    private String majorPosition;
    /**
     * 账号状态 启用 停用
     */
    @ApiModelProperty(value = "账号状态")
    @ExcelProperty(value = "账号状态",index = 3)
    private String isEnableName;
    /**
     * 主部门手机
     */
    @ApiModelProperty(value = "主部门手机")
    @ExcelProperty(value = "主部门手机",index = 4)
    private String majorMobile;
    /**
     * 是否短信通知 1开 0 不开
     */
    @ApiModelProperty(value = "是否短信通知 1开 0 不开")
    @ExcelProperty(value = "点位名称",index = 5)
    private String isSendSmsName;


//    /**
//     * 其他所属部门
//     */
//    @ApiModelProperty(value = "其他所属部门")
//    @ExcelProperty(value = "点位名称",index = 6)
//    private String otherDeptName;

//    /**
//     * 其他部门职位
//     */
//    @ApiModelProperty(value = "其他部门职位")
//    @ExcelProperty(value = "其他部门职位",index = 6)
//    private String otherPosition;
    /**
     * 性别，1-男，2-女
     */
    @ApiModelProperty(value = "性别，1-男，2-女")
    @ExcelProperty(value = "点位名称",index = 6)
    private String gender;

    /**
     * 别名
     */
    @ApiModelProperty(value = "别名")
    @ExcelProperty(value = "别名",index = 7)
    private String englishName;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @ExcelProperty(value = "邮箱",index = 8)
    private String email;

    /**
     * 对外简称
     */
    @ApiModelProperty(value = "对外简称")
    @ExcelProperty(value = "对外简称",index = 9)
    private String externalCorpName;


}

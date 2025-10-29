package cn.wxgis.jc.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作人类别
 */
@Getter
@AllArgsConstructor
public enum OperLogUserTypeEnum
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 后台用户
     */
    MANAGE,

    /**
     * 手机端用户
     */
    MOBILE;


}

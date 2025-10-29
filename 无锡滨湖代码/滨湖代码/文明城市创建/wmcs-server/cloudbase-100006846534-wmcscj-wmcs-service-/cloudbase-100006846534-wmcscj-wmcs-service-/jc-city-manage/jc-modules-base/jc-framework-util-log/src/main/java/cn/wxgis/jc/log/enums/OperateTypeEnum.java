package cn.wxgis.jc.log.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 提交操作类型枚举类
 */
@Getter
@AllArgsConstructor
public enum OperateTypeEnum {

    /**
     * 新增
     */
    INSERT(1),
    /**
     * 修改
     */
    UPDATE(2),
    /**
     * 删除
     */
    DELETE(3);

    /**
     * 类型
     */

    private Integer code;


}

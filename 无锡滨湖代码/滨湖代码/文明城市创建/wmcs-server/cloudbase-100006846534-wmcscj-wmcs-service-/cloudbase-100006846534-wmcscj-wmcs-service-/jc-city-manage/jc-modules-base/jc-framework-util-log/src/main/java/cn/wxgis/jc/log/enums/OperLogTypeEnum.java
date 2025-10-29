package cn.wxgis.jc.log.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志的操作类型
 */
@Getter
@AllArgsConstructor
public enum OperLogTypeEnum {

    /**
     * 授权
     */
    GRANT,
    /**
     * 新增
     */
    INSERT,
    /**
     * 修改
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 审核
     */
    EXAMINE,
    /**
     * 导出
     */
    EXPORT,
    /**
     * 导入
     */
    IMPORT,
    /**
     * 上传
     */
    UPLOAD,
    /**
     * 下载
     */
    DOWNLOAD,
    /**
     * 其它
     *
     * 在无法归类时，可以选择使用其它。因为还有操作名可以进一步标识
     */
    OTHER;

}

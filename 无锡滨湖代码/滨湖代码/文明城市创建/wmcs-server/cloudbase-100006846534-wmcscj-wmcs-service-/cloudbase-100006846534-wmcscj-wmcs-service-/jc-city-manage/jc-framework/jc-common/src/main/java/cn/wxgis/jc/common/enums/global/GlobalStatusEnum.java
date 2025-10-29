package cn.wxgis.jc.common.enums.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * ExceptionEnum
 * 全局状态码
 */
@Getter
@AllArgsConstructor
public enum GlobalStatusEnum implements GlobalStatus {

    STATUS_SUCCESS("200",  "操作成功"),
    UNAUTHORIZED("401", "没有访问权限，请联系管理员授权"),
    FORBIDDEN("403", "访问受限，授权过期"),
    STATUS_ERROR("500", "系统错误"),
    STATUS_UNKNOWN_ERROR("-1",  "未知异常"),

    PARAM_VALIDATED("2001", "参数校验不通过"),
    DATA_EXISTE("2002", "数据已存在"),
    DATA_STATUS_ERROR("2005", "数据状态错误"),
    DATA_NOT_FOUND("2004", "未查到相关记录"),
    DATA_DELETE_EXCEPTION("2005", "数据被删除"),
    // Exception
    PASSWORD_INPUT_ERROR_MAX_EXCEPTION("4001", "密码输入错误10次以上，请24小时后重试或联系管理员"),
    PASSWORD_MISTAKE_EXCEPTION("4002", "用户不存在/密码错误"),
    JCAPTCHA_EXPIRE_EXCEPTION("4003", "验证码错误"),
    USER_SYSTEM_MATCHING_EXCEPTION_WANGGEYUAN("4004", "此账号非网格员账号"),

    FILE_UPLOAD_FAIL("5001", "文件上传失败"),
    FILE_DOWNLOAD_FAIL("5002", "文件下载失败"),
    ;

    private String code;
    private String msg;

    public static GlobalStatusEnum getByCode(String code) {
        return Arrays.asList(GlobalStatusEnum.values())
                .stream()
                .filter(oee -> oee.getCode().equals(code))
                .findFirst()
                .orElse(GlobalStatusEnum.STATUS_UNKNOWN_ERROR);

    }
}

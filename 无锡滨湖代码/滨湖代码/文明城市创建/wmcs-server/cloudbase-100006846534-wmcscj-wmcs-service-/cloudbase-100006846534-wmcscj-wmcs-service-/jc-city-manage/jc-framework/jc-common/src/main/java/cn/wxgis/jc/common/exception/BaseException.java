package cn.wxgis.jc.common.exception;

import cn.wxgis.jc.common.enums.global.GlobalStatus;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseException
 * 基础异常，所有的异常继承该类
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 状态码
     */
    private String code;
    /**
     * 英文描述
     */
    private String msg;

    /**
     * 错误码对应的参数
     */
    private Object[] args;


    public BaseException(String code, String msg, Object[] args)
    {
        this.code = code;
        this.msg = msg;
        this.args = args;
    }

    private BaseException(GlobalStatusEnum globalStatusEnum) {
        super();
    }

    public BaseException(GlobalStatus code) {
        this(code.getCode(), code.getMsg(), (Object[]) null);
    }

    public BaseException(GlobalStatus code, String remark) {
        this(code.getCode(), code.getMsg(), remark);
    }

    private BaseException(String code, String msg, String remark) {
        super(String.format("%s : %s : %s", code, msg, remark));
        this.code = code;
        this.msg = msg;
    }

    public Map<String, Object> baseException2Map(BaseException baseException) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", baseException.getCode());
        map.put("msg", baseException.getMsg());
        return map;
    }
}

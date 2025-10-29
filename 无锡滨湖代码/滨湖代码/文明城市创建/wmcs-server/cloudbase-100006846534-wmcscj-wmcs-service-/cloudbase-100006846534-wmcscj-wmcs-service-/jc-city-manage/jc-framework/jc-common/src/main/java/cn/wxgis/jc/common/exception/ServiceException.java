package cn.wxgis.jc.common.exception;

import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public final class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() { }

    public ServiceException(String message)
    {
        this.message = message;
    }
    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

}

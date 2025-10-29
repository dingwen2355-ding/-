package cn.wxgis.jc.common.exception;

import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import lombok.Getter;

/**
 * 验证码失效异常类
 */
@Getter
public class CaptchaExpireException extends BaseException {

    /**
     * 初始化异常
     */
    public CaptchaExpireException() {
        super(GlobalStatusEnum.JCAPTCHA_EXPIRE_EXCEPTION);
    }

    /**
     * 初始化异常，并添加附件信息
     *
     * @param globalStatusEnum
     * @param remark
     */
    public CaptchaExpireException(GlobalStatusEnum globalStatusEnum, String remark) {
        super(globalStatusEnum, remark);
    }

}

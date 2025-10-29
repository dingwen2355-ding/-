package cn.wxgis.jc.gateway.service;

import cn.wxgis.jc.common.exception.CaptchaExpireException;
import cn.wxgis.jc.common.result.JsonResult;

import java.io.IOException;

/**
 * 验证码处理
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
    JsonResult createCaptcha() throws IOException, CaptchaExpireException;

    /**
     * 校验验证码
     */
    void checkCaptcha(String key, String value) throws CaptchaExpireException;
}

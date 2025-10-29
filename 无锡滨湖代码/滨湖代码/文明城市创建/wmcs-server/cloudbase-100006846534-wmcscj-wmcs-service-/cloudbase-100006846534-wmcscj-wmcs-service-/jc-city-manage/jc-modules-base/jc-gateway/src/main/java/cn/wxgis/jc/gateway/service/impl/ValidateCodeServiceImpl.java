package cn.wxgis.jc.gateway.service.impl;

import cn.wxgis.jc.common.constant.CacheConstants;
import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.exception.CaptchaExpireException;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.Base64Utils;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.util.id.IdUtils;
import cn.wxgis.jc.gateway.config.properties.CaptchaProperties;
import cn.wxgis.jc.gateway.service.ValidateCodeService;
import cn.wxgis.jc.redis.service.RedisService;
import com.google.code.kaptcha.Producer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 */
@AllArgsConstructor
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService
{
    private final Producer captchaProducer;
    private final Producer captchaProducerMath;
    private final RedisService redisService;
    private final CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @Override
    public JsonResult createCaptcha() throws IOException, CaptchaExpireException
    {
        boolean captchaEnabled = captchaProperties.getEnabled();
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return JsonResult.ok(ajax);
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return JsonResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64Utils.encode(os.toByteArray()));
        return JsonResult.ok(ajax);
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaExpireException
    {
        if (StringUtils.isEmpty(code))
        {
            throw new CaptchaExpireException(GlobalStatusEnum.JCAPTCHA_EXPIRE_EXCEPTION, "验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new CaptchaExpireException(GlobalStatusEnum.JCAPTCHA_EXPIRE_EXCEPTION, "验证码已失效");
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaExpireException(GlobalStatusEnum.JCAPTCHA_EXPIRE_EXCEPTION, "验证码错误");
        }
    }
}

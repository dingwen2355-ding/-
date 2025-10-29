package cn.wxgis.jc.synch.common.config;

import cn.hutool.db.PageResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 配置信息
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "wechat.config")
public class WechatConfig {

    /**
     * 访问地址
     */
    private String addr;

    /**
     * id
     */
    private String agentId;

    /**
     * 单位id
     */
    private String corpid;

    /**
     * 应用的凭证密钥
     */
    private String corpsecret;

    /**
     * 获取url
     */
    private String url;
}

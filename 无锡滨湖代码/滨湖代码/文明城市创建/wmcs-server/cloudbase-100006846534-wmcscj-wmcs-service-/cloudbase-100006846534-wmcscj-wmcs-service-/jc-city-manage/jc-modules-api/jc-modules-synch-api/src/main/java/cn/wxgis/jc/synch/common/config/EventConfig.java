package cn.wxgis.jc.synch.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "event.config")
public class EventConfig {

    /**
     * 是否开启
     */
    private boolean enable;

    /**
     * 访问地址
     */
    private String addr;

    /**
     * 默认账号
     */
    private String name;

    /**
     * 默认密码
     */
    private String password;

    /**
     * 事件地址
     */
    private String eventUrl;

    /**
     * 图片访问地址
     */
    private String minioUrl;
}
